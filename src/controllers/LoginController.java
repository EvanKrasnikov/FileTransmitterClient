package controllers;

import client.Client;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginController extends Client {
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXCheckBox rememberLogin;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passField;

    @FXML
    private void loginAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root = null;
        String sceneFile = "/resources/Program.fxml";
        URL url  = null;

        //проверка загрузки FXML
        try
        {
            url  = getClass().getResource( sceneFile );
            root = FXMLLoader.load( url );
            System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            System.out.println(ex.getMessage());
            throw ex;
        }

        Scene scene = new Scene(root);
        stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();




    }

    @FXML
    private void registerAction(ActionEvent event) throws IOException{
        loginButton.setText("Register");
        registerButton.setVisible(false);

        if (loginField.getText().equals("") || passField.getText().equals("")){
            System.out.println("Login and password fields are not filled");
        } else {
            if (rememberLogin.isSelected()) rememberLogin();
            super.sendMessage("/register " + loginField.getText() + " " + passField.getText());
            System.out.println("Welcome! " + loginField.getText());
        }
    }

    @FXML
    private void rememberLogin(){
        //todo
        loginField.setPromptText(loginField.getText());
        passField.setPromptText(passField.getText());
    }
}
