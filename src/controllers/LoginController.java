package controllers;

import client.Client;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController extends Client {
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXCheckBox rememberLoginCheckBox;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passField;

    @FXML
    private void loginAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;

        if (event.getSource() == loginButton){
            stage = (Stage) loginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/resources/Program.fxml"));

            if (loginField.getText().equals("") || passField.getText().equals("")){
                System.out.println("Login and password fields are not filled");
            } else {
                super.sendMessage("/login " + loginField.getText() + " " + passField.getText());

                if (super.isAuthentified) {
                    if (rememberLoginCheckBox.isSelected()) rememberLogin();
                    System.out.println("Welcome! " + loginField.getText());
                    stage.hide();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    loginField.clear();
                    passField.clear();
                    System.out.println("Sorry, invalid credentials");
                }
            }
        }


    }

    @FXML
    private void registerAction(ActionEvent event) throws IOException{
        loginButton.setText("Register");
        registerButton.setVisible(false);

        if (loginField.getText().equals("") || passField.getText().equals("")){
            System.out.println("Login and password fields are not filled");
        } else {
            if (rememberLoginCheckBox.isSelected()) rememberLogin();
            super.sendMessage("/register " + loginField.getText() + " " + passField.getText());
            System.out.println("Welcome! " + loginField.getText());
        }
    }

    @FXML
    private void rememberLogin(){
        //todo
    }
}
