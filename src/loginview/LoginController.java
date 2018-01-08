package loginview;

import client.Client;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.annotation.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public class LoginController extends Client{
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

    public void loginAction(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/programview/programwindow.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //FXMLLoader loader = new FXMLLoader();
        //loader.getController();

        //Node node = loader.load(getClass().getResourceAsStream("/programview/programwindow.fxml"));

        if (loginField.getText().equals("") || passField.getText().equals("")){
            System.out.println("Login and password fields are not filled");
        } else {
            super.sendMessage("/login " + loginField.getText() + " " + passField.getText());

            if (super.isAuthentified) {
                if (rememberLoginCheckBox.isSelected()) rememberLogin();
                System.out.println("Welcome! " + loginField.getText());
                stage.hide();
                stage.setScene(scene);
                stage.show();
            } else {
                loginField.clear();
                passField.clear();
                System.out.println("Sorry, invalid credentials");
            }
        }
    }

    public void registerAction(ActionEvent event) throws IOException{
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

    private void rememberLogin(){
        //todo
    }
}
