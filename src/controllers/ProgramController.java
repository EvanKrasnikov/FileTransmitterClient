package controllers;

import client.Sender;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

public class ProgramController{
    @FXML
    private Button addFiles;
    @FXML
    private JFXHamburger settings;

    @FXML
    private void add(ActionEvent event) throws IOException{
        //todo
    }

    @FXML
    private void sett(MouseEvent event){
        //todo
    }
}
