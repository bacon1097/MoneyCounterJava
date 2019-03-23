package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Money Counter");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();
    }

    public void closeProgram() {
        boolean flag = ConfirmBox.display("Quit?","Are you sure you want to quit?");
        if (flag) {
            DateInfo.isDifDay();
            DateInfo.closing();
            window.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}