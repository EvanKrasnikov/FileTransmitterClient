package controllers;

import client.Client;
import client.FileSync;
import utils.FileEntry;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import utils.Messages;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Format.getFormattedDate;
import static utils.Format.getFormattedSizeOfFile;

public class ProgramController extends Client implements Initializable{
    private Tab localFilesTab;
    private Tab remoteFilesTab;
    private JFXTreeTableView<FileEntry> localFilesTable;
    private JFXTreeTableView<FileEntry> remoteFilesTable;
    private HamburgerSlideCloseTransition burgerTask;
    private ObservableList<FileEntry> entries;

    @FXML
    private AnchorPane menuPanel;
    @FXML
    private JFXButton addFiles;
    @FXML
    private JFXButton sendFiles;
    @FXML
    private JFXButton downloadFiles;
    @FXML
    private JFXHamburger humburger;
    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private void add(ActionEvent event) throws IOException{ // добавление файлов в таблицу для отображения
        FileChooser chooser = new FileChooser();
        List<File> files = chooser.showOpenMultipleDialog(localFilesTable.getScene().getWindow());
        listToTableView(files);
    }

    private void listToTableView(List<File> files){
        for (File f: files)
            entries.add(new FileEntry(f.getName(),getFormattedSizeOfFile(f.length()),getFormattedDate(f.lastModified())));

        final TreeItem<FileEntry> root = new RecursiveTreeItem<>(entries, RecursiveTreeObject::getChildren);

        localFilesTable.setRoot(root);
        localFilesTable.setShowRoot(false);
        localFilesTable.setEditable(false);
    }

    @FXML
    private void download(){ // скачивание выделенных файлов
        ObservableList<TreeItem<FileEntry>> selectedItems = remoteFilesTable.getSelectionModel().getSelectedItems();
        String s = "";

        for (TreeItem<FileEntry> entry: selectedItems) s = " " + entry.toString();

        sendMessage((Messages.GET_FILES + s).getBytes());
    }

    @FXML
    private void send(){ // выделение файлов и передача путей на отправку
        List<Path> list = new ArrayList<>();
        ObservableList<TreeItem<FileEntry>> selectedItems = localFilesTable.getSelectionModel().getSelectedItems();

        for (TreeItem<FileEntry> entry: selectedItems){
            Path path = Paths.get(entry.getValue().getFileNameProperty().toString());
            list.add(path);
        }

        new FileSync().sendFile(list);
    }

    @FXML
    private void showMenu(MouseEvent event){
        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // загрузка столбцов
        localFilesTable.getColumns().setAll(getNameColumn(),getSizeColumn(), getEditionTimeColumn());
        localFilesTable.addEventHandler(DragEvent.DRAG_OVER, this::handleDragOver);
        localFilesTable.addEventHandler(DragEvent.DRAG_DROPPED, this::handleDrop);
        remoteFilesTable.getColumns().setAll(getNameColumn(),getSizeColumn(), getEditionTimeColumn());
        loadTabs();
        entries = FXCollections.observableArrayList();
        burgerTask = new HamburgerSlideCloseTransition(humburger);
        burgerTask.setRate(-1);
    }

    private void loadTabs(){
        tabPane = new JFXTabPane();
        tabPane.setPrefSize(600, 350);

        localFilesTab = new Tab();
        localFilesTab.setText("Local Files");
        localFilesTab.setContent(localFilesTable);

        remoteFilesTab = new Tab();
        remoteFilesTab.setText("Remote Files");
        remoteFilesTab.setContent(remoteFilesTable);

        tabPane.getTabs().addAll(localFilesTab,remoteFilesTab);
    }

    private static TreeTableColumn<FileEntry, String> getNameColumn(){
        JFXTreeTableColumn<FileEntry, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getFileNameProperty());
        nameColumn.setPrefWidth(290);
        return nameColumn;
    }

    private static TreeTableColumn<FileEntry, String> getSizeColumn(){
        JFXTreeTableColumn<FileEntry, String> sizeColumn = new JFXTreeTableColumn<>("Size");
        sizeColumn.setCellValueFactory(param -> param.getValue().getValue().getSizeProperty());
        sizeColumn.setPrefWidth(110);
        return sizeColumn;
    }

    private static TreeTableColumn<FileEntry, String> getEditionTimeColumn(){
        JFXTreeTableColumn<FileEntry, String> editionTimeColumn = new JFXTreeTableColumn<>("Last change");
        editionTimeColumn.setCellValueFactory(param -> param.getValue().getValue().getEditionTimeProperty());
        editionTimeColumn.setPrefWidth(200);
        return editionTimeColumn;
    }

    private void handleDragOver(DragEvent event){
        if (event.getDragboard().hasFiles())
            event.acceptTransferModes(TransferMode.ANY);
    }

    private void handleDrop(DragEvent event){
        listToTableView(event.getDragboard().getFiles());
    }
}
