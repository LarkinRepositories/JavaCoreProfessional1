package Client.UI.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private boolean isAuthorized;
    private String nickname = "nickname";

    @FXML
    private TextArea inputMessageArea;
    @FXML
    private TextFlow emojiList;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button logoutButton;
    @FXML
    private VBox chatBox;


    public void connect()  {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        out.writeUTF("/req");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (true) {
                        try {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                String[] tokens = str.split(" ");
                                nickname = tokens[1];
                                messageArea.clear();
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    while (true) {
                        try {
                            String str = in.readUTF();
                            Label label;
                            VBox vBox = new VBox();
                            String[] tokens = str.split(" ");
                            if (tokens[0].substring(0, tokens[0].length() - 1).equalsIgnoreCase(nickname)) {
                                vBox.setAlignment(Pos.TOP_RIGHT);
                                label = new Label(tokens[1] + "\n");
                            } else {
                                vBox.setAlignment(Pos.TOP_LEFT);
                                label = new Label(str + "\n");
                            }
                            vBox.getChildren().add(label);
                            Platform.runLater(() -> chatBox.getChildren().add(vBox));
                            if (str.equals("/serverClosed")) {
                                //setAuthorized(false);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
        System.out.println(this.nickname);
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
        connect();
        System.out.println(nickname);
//        try {
//            String str = in.readUTF();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        for (Node text: emojiList.getChildren()) {
            text.setOnMouseClicked(event -> {
            inputMessageArea.setText(inputMessageArea.getText()+" "+((Text)text).getText());
            emojiList.setVisible(false);
            });
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
