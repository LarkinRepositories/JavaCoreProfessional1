package ClientGeekBrainsLogic.UI.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatWindow implements Initializable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8189;
    @FXML
    private TextArea inputMessageArea;
    @FXML
    private TextFlow emojiList;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane loginPanel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInBtn;
    @FXML
    private VBox chatBox;
    private boolean isAuthorized;
    private String nickname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            loginPanel.setVisible(true);
            loginPanel.setManaged(true);
        } else {
            loginPanel.setVisible(false);
            loginPanel.setManaged(false);
            loginField.setVisible(false);
            passwordField.setVisible(false);
            signInBtn.setVisible(false);
        }
    }

    @FXML
    void emojiAction(ActionEvent event) {
        if(emojiList.isVisible()){

            emojiList.setVisible(false);
        }else {
            emojiList.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        for (Node text: emojiList.getChildren()) {
            text.setOnMouseClicked(event -> {
            inputMessageArea.setText(inputMessageArea.getText()+" "+((Text)text).getText());
            emojiList.setVisible(false);
            });
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                String[] tokens = str.split(" ");
                                nickname = tokens[1];
                                setAuthorized(true);
                                messageArea.clear();
                                break;
                            } else {
                                setAuthorized(false);
                                messageArea.appendText("Incorrect login/password!");
                            }
                        }
                        while (true) {
                            String str = in.readUTF();
                            //messageArea.appendText(str +"\n");
                            //Label label = new Label(str + "\n");
                            if(str.startsWith("/nickChanged")) {
                                String[] tokens = str.split(" ");
                                nickname = tokens[1];
                            }
                            Label label;
                            VBox vBox = new VBox();
                            String[] tokens = str.split(" ");
                            if (tokens[0].substring(0, tokens[0].length()-1).equalsIgnoreCase(nickname)) {
                                vBox.setAlignment(Pos.TOP_RIGHT);
                                label = new Label(tokens[1]+ "\n");
                            }
                            else {
                                vBox.setAlignment(Pos.TOP_LEFT);
                                label = new Label(str + "\n");
                            }
                            vBox.getChildren().add(label);
                            Platform.runLater(() -> chatBox.getChildren().add(vBox));
                            if (str.equals("/serverClosed")) {
                               setAuthorized(false);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void login() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        String username = loginField.getText();
        String password = passwordField.getText();
        try {
            out.writeUTF("/auth "+username+" "+password);
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendMsg(ActionEvent e) {
        //messageArea.appendText(inputMessageArea.getText()+"\n");
        try {
            out.writeUTF(inputMessageArea.getText());
            inputMessageArea.clear();
            inputMessageArea.requestFocus();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent e) {
        ((Stage)logoutButton.getScene().getWindow()).close();
    }
}
