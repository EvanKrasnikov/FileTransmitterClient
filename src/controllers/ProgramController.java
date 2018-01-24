package controllers;

import client.FileEntry;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramController implements Initializable{
    private TreeItem<FileEntry> itemRoot;

    @FXML
    private AnchorPane menuPanel;
    @FXML
    private JFXTreeTableView<FileEntry> tableView;
    @FXML
    private JFXButton addFiles;
    @FXML
    private JFXHamburger humburger;
    @FXML
    private BorderPane borderpane;
    @FXML
    private void add(ActionEvent event) throws IOException{
        FileChooser chooser = new FileChooser();
        List<File> files = chooser.showOpenMultipleDialog(tableView.getScene().getWindow());

        for (File f: files){
            FileEntry fileEntry = new FileEntry(f.getName(),getFormattedSizeOfFile(f.length()),getFormattedDate(f.lastModified()));
            TreeItem<FileEntry> item = new TreeItem<>(fileEntry);
            itemRoot.getChildren().add(item);
            System.out.println(fileEntry.getFileName() + " " + fileEntry.getSize() + " " + fileEntry.getEditionTime());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<FileEntry, String> nameColumn = new JFXTreeTableColumn<>("Name");
        JFXTreeTableColumn<FileEntry, String> sizeColumn = new JFXTreeTableColumn<>("Size");
        JFXTreeTableColumn<FileEntry, String> editionTimeColumn = new JFXTreeTableColumn<>("Last change");

        nameColumn.setPrefWidth(300);
        sizeColumn.setPrefWidth(100);
        editionTimeColumn.setPrefWidth(200);

        // Get value from property of FileEntry class
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Name"));
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Size"));
        editionTimeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("Time"));

        tableView.getColumns().addAll(nameColumn, sizeColumn, editionTimeColumn);

        itemRoot = new TreeItem<>();
        tableView.setRoot(itemRoot);
        tableView.setShowRoot(false);
        tableView.setEditable(false);
    }

    private String getFormattedSizeOfFile(long size){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;

        if (size > sizeMb) return decimalFormat.format(size / sizeMb) + " Mb";
        else if (size > sizeGb) return decimalFormat.format(size / sizeGb) + " Gb";
        return decimalFormat.format(size / sizeKb) + " Kb";
    }

    private String getFormattedDate(long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }
}
