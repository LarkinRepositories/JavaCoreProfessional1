package ClientGeekBrainsLogic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        Parent root = FXMLLoader.load(getClass().getResource("UI/Fxml/Login.fxml"));
//        primaryStage.setTitle("zChat pre alpha");
//        primaryStage.setScene(new Scene(root, 600, 400));
//        primaryStage.setResizable(false);
//        primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientGeekBrainsLogic/UI/Fxml/ChatWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
