package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        window.getIcons().add(new Image(getClass().getResourceAsStream("images/money-bag.png")));
        window.initStyle(StageStyle.TRANSPARENT);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Scene scene = new Scene(root);
        root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            window.setX(e.getScreenX() - x);
            window.setY(e.getScreenY() - y);
            window.setOpacity(0.8);
        });
        root.setOnDragDone( e -> window.setOpacity(1));
        root.setOnMouseReleased( e -> window.setOpacity(1));
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