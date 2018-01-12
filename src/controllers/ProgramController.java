package controllers;

import client.File;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable{
    @FXML
    private AnchorPane menuPanel;
    @FXML
    private JFXTreeTableView tableView;
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
        //tableView = new JFXTreeTableView();
        borderPane = new BorderPane();
        JFXTreeTableColumn<File, String> nameColumn = new JFXTreeTableColumn<>("Name");
        JFXTreeTableColumn<File, String> sizeColumn = new JFXTreeTableColumn<>("Size");
        JFXTreeTableColumn<File, String> editionTimeColumn = new JFXTreeTableColumn<>("Last change");

        nameColumn.setPrefWidth(300);
        sizeColumn.setPrefWidth(100);
        editionTimeColumn.setPrefWidth(200);

        // Get value from property of File class
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<File, String> param) ->{
            if(nameColumn.validateValue(param)) return param.getValue().getValue().fileNameProperty();
            else return nameColumn.getComputedValue(param);
        });
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<File, String> param) ->{
            if(nameColumn.validateValue(param)) return param.getValue().getValue().sizeProperty();
            else return nameColumn.getComputedValue(param);
        });
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<File, String> param) ->{
            if(nameColumn.validateValue(param)) return param.getValue().getValue().editionTimeProperty();
            else return nameColumn.getComputedValue(param);
        });

        ObservableList<File> users = FXCollections.observableArrayList();
        users.add(new File("Computer Department", "23","CD 1"));
        users.add(new File("Sales Department", "22","Employee 1"));
        users.add(new File("Sales Department", "22","Employee 2"));
        users.add(new File("Sales Department", "25","Employee 4"));
        users.add(new File("Sales Department", "25","Employee 5"));
        users.add(new File("IT Department", "42","ID 2"));
        users.add(new File("HR Department", "22","HR 1"));
        users.add(new File("HR Department", "22","HR 2"));

        final TreeItem<File> item = new RecursiveTreeItem<File>(users, RecursiveTreeObject::getChildren);
        JFXTreeTableView<File> tableView = new JFXTreeTableView<File>();
        tableView.setShowRoot(false);
        tableView.setEditable(false);

        tableView.getColumns().setAll(nameColumn, sizeColumn, editionTimeColumn);

        JFXTextField filterField = new JFXTextField();
        filterField.textProperty().addListener((o,oldVal,newVal)->{
            tableView.setPredicate(user -> user.getValue().getFileName().contains(newVal)
                    || user.getValue().getSize().contains(newVal)
                    || user.getValue().getEditionTime().contains(newVal));
        });

        Label size = new Label();
        size.textProperty().bind(Bindings.createStringBinding(()->tableView.getCurrentItemsCount()+"",
                tableView.currentItemsCountProperty()));
        //borderPane.setTop(new AnchorPane());
       // borderPane.setCenter(tableView);
        //StackPane root = new StackPane();
        //root.setPadding(new Insets(5));
        //borderPane.getChildren().add(tableView);

        Stage stage = new Stage();
        stage.setTitle("TableView (o7planning.org)");

        stage.setScene(new Scene(borderPane, 600, 400));
        stage.show();

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
