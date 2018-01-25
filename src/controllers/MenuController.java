package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private JFXButton showLocalFiles;
    @FXML
    private JFXButton showServerFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
