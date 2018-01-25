package controllers;

import client.Client;
import client.format.FileEntry;

import client.sync.Sender;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static client.format.Format.getFormattedDate;
import static client.format.Format.getFormattedSizeOfFile;

public class ProgramController extends Client implements Initializable{
    private TreeItem<FileEntry> itemRoot = new TreeItem<>();
    private List<File> prepList = new ArrayList<>();

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
        tableView.setRoot(itemRoot);
        tableView.setShowRoot(false);
        tableView.setEditable(false);

        FileChooser chooser = new FileChooser();
        List<File> files = chooser.showOpenMultipleDialog(tableView.getScene().getWindow());
        prepList.addAll(files);

        for (File f: files){
            FileEntry fileEntry = new FileEntry(f.getName(),getFormattedSizeOfFile(f.length()),getFormattedDate(f.lastModified()));
            TreeItem<FileEntry> item = new TreeItem<>(fileEntry);
            itemRoot.getChildren().add(item);
            System.out.println(fileEntry.getFileName() + " " + fileEntry.getSize() + " " + fileEntry.getEditionTime());
            System.out.println(itemRoot.getChildren().toString());
        }
    }

    @FXML
    private void send(){ // вызов метода передачи файлов
        Sender sender = new Sender(super.getChannel());
        sender.sendFile(prepList);
    }

    @FXML
    private void showMenu(MouseEvent event){
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(humburger);
        burgerTask.setRate(-1);
        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // загрузка столбцов
        tableView.getColumns().addAll(getNameColumn(),getSizeColumn(), getEditionTimeColumn());
    }

    private static TreeTableColumn<FileEntry, String> getNameColumn(){
        JFXTreeTableColumn<FileEntry, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Name"));
        nameColumn.setPrefWidth(300);
        return nameColumn;
    }

    private static TreeTableColumn<FileEntry, String> getSizeColumn(){
        JFXTreeTableColumn<FileEntry, String> sizeColumn = new JFXTreeTableColumn<>("Size");
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Size"));
        sizeColumn.setPrefWidth(100);
        return sizeColumn;
    }

    private static TreeTableColumn<FileEntry, String> getEditionTimeColumn(){
        JFXTreeTableColumn<FileEntry, String> editionTimeColumn = new JFXTreeTableColumn<>("Last change");
        editionTimeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Time"));
        editionTimeColumn.setPrefWidth(200);
        return editionTimeColumn;
    }
}
