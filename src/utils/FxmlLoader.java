package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlLoader {
    public void loadFXML(Node node, String fxmlPath){
        Stage stage;
        Parent root = null;

        //проверка загрузки FXML
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error on FXML loading!");
        }

        stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
