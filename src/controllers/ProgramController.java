package controllers;

import client.File;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable{
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private JFXTreeTableView<File> tableView;
    @FXML
    private JFXButton addFiles;
    @FXML
    private JFXHamburger humburger;
    @FXML
    private BorderPane borderPane;
    @FXML
    private void add(ActionEvent event) throws IOException{
        //todo
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<File, String> nameColumn = new JFXTreeTableColumn<>("Name");
        JFXTreeTableColumn<File, String> sizeColumn = new JFXTreeTableColumn<>("Size");
        JFXTreeTableColumn<File, String> editionTimeColumn = new JFXTreeTableColumn<>("Last change");

        nameColumn.setPrefWidth(300);
        sizeColumn.setPrefWidth(100);
        editionTimeColumn.setPrefWidth(200);

        // Get value from property of File class
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Name"));
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Size"));
        editionTimeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Time"));

        tableView.getColumns().addAll(nameColumn, sizeColumn, editionTimeColumn);

        TreeItem<File> itemRoot = new TreeItem<>();

        for (File f: getFileList()){
            itemRoot.getChildren().add(new TreeItem<>(f));
        }

        tableView.setRoot(itemRoot);
        tableView.setShowRoot(false);
        tableView.setEditable(false);
    }

    private ObservableList<File> getFileList() {
        ObservableList<File> users = FXCollections.observableArrayList();
        users.add(new File("Computer Department", "23","CD 1"));
        users.add(new File("Sales Department", "22","Employee 1"));
        users.add(new File("Sales Department", "22","Employee 2"));
        users.add(new File("Sales Department", "25","Employee 4"));
        users.add(new File("Sales Department", "25","Employee 5"));
        users.add(new File("IT Department", "42","ID 2"));
        users.add(new File("HR Department", "22","HR 1"));
        users.add(new File("HR Department", "22","HR 2"));
        return users;
    }

}
