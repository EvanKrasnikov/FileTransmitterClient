package controllers;

import client.Client;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Format.getFormattedDate;
import static utils.Format.getFormattedSizeOfFile;

public class ProgramController extends Client implements Initializable{
    private HamburgerSlideCloseTransition burgerTask;
    private ObservableList<FileEntry> entries;

    @FXML
    private AnchorPane menuPanel;
    @FXML
    private JFXTreeTableView<FileEntry> tableView;
    @FXML
    private JFXButton addFiles;
    @FXML
    private JFXButton sendFiles;
    @FXML
    private JFXHamburger humburger;
    @FXML
    private BorderPane borderpane;

    @FXML
    private void add(ActionEvent event) throws IOException{ // добавление файлов в таблицу для отображения
        FileChooser chooser = new FileChooser();
        List<File> files = chooser.showOpenMultipleDialog(tableView.getScene().getWindow());
        listToTableView(files);
    }

    private void listToTableView(List<File> files){
        for (File f: files)
            entries.add(new FileEntry(f.getName(),getFormattedSizeOfFile(f.length()),getFormattedDate(f.lastModified())));

        final TreeItem<FileEntry> root = new RecursiveTreeItem<>(entries, RecursiveTreeObject::getChildren);

        tableView.setRoot(root);
        tableView.setShowRoot(false);
        tableView.setEditable(false);
    }

    @FXML
    private void send(){ // вызов метода передачи файлов
        //sender.sendFile(prepList);
    }

    @FXML
    private void showMenu(MouseEvent event){
        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // загрузка столбцов
        tableView.getColumns().setAll(getNameColumn(),getSizeColumn(), getEditionTimeColumn());
        entries = FXCollections.observableArrayList();
        burgerTask = new HamburgerSlideCloseTransition(humburger);
        burgerTask.setRate(-1);
    }

    private static TreeTableColumn<FileEntry, String> getNameColumn(){
        JFXTreeTableColumn<FileEntry, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getFileNameProperty());
        nameColumn.setPrefWidth(300);
        return nameColumn;
    }

    private static TreeTableColumn<FileEntry, String> getSizeColumn(){
        JFXTreeTableColumn<FileEntry, String> sizeColumn = new JFXTreeTableColumn<>("Size");
        sizeColumn.setCellValueFactory(param -> param.getValue().getValue().getSizeProperty());
        sizeColumn.setPrefWidth(100);
        return sizeColumn;
    }

    private static TreeTableColumn<FileEntry, String> getEditionTimeColumn(){
        JFXTreeTableColumn<FileEntry, String> editionTimeColumn = new JFXTreeTableColumn<>("Last change");
        editionTimeColumn.setCellValueFactory(param -> param.getValue().getValue().getEditionTimeProperty());
        editionTimeColumn.setPrefWidth(200);
        return editionTimeColumn;
    }

    @FXML
    private void handleDragOver(DragEvent event){
        if (event.getDragboard().hasFiles())
            event.acceptTransferModes(TransferMode.ANY);
    }

    @FXML
    private void handleDrop(DragEvent event){
        listToTableView(event.getDragboard().getFiles());
    }
}
