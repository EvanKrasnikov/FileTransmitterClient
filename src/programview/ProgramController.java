package programview;

import client.Sender;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

public class ProgramController extends Sender {
    @FXML
    private JFXButton add;
    @FXML
    private JFXHamburger settings;
    @FXML
    private JFXTextArea textArea;

    public ProgramController(SocketChannel channel){
        super(channel);
    }

    public void add(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        textArea.clear();
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        super.sendFile(files);
    }

    @FXML
    private void settings(ActionEvent event){
        //todo
    }
}
