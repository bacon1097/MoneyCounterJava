package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    static Stage window;
    private double x, y;
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
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - x);
            window.setY(event.getScreenY() - y);
            window.setOpacity(0.8f);
        });
        root.setOnDragDone( event -> window.setOpacity(1.0f));
        root.setOnMouseReleased( event -> window.setOpacity(1.0f));
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();
    }
    public static void closeProgram() {
        boolean flag = ConfirmBox.display("Quit","Are you sure you want to quit?");
        if (flag) {
            FileStuff.saveInfo();
            window.close();
        }
    }
    public static void main(String[] args) { launch(args); }
}