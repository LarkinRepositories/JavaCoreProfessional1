package Client.UI.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class CreateNewAccount {
    @FXML
    private TextField loginField;
    @FXML
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text alreadyHave;
    @FXML
    private Button createBtn;
    @FXML
    private Button exitBtn;

    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataOutputStream out;


    @FXML
    private void createNew() {
        if (socket == null || socket.isClosed()) {
            connect();
        }

        String login = loginField.getText();
        String nickname = nicknameField.getText();
        String password = passwordField.getText();

        try {
            out.writeUTF(String.format("/new %s %s %s", login, nickname, password));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void alreadyHave() throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Fxml/Login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.getScene().setFill(Color.TRANSPARENT);
        //ChatWindow chatWindow = fxmlLoader.getController();
        ((Stage) passwordField.getScene().getWindow()).close();
        ((Stage) createBtn.getScene().getWindow()).close();
        stage.show();
    }

    private void connect() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
