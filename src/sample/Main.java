package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    static Stage window;
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

        if (FileStuff.fileExists()) {
            loadUp(scene);
        }
        else {
            setup(scene);
        }
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();
    }
    private void setup(Scene scene) {
        Label moneyDisplay = (Label)scene.lookup("#moneyDisplay");
        System.out.println("New Value is: " + moneyDisplay.getText());
        WageStuff.setWage(0.00f);
        WageStuff.setPayDay("01-01-2000");
    }
    private void loadUp(Scene scene) {
        Label moneyDisplay = (Label)scene.lookup("#moneyDisplay");
        Label daysSincePayDay = (Label)scene.lookup("#daysSincePayDayLabel");
        try {
            moneyDisplay.setText(String.format("%.02f", Float.parseFloat(FileStuff.getInfo(0))));       //Setting the money owned value
            System.out.println("New Value is: " + moneyDisplay.getText());
            WageStuff.setPayDay(FileStuff.getInfo(2));       //Setting the payday date
            WageStuff.setWage(Float.parseFloat(FileStuff.getInfo(3)));      //Setting the wage
            daysSincePayDay.setText(String.valueOf(DateInfo.daysSince()));
        }
        catch (NumberFormatException e) {
            System.out.println("Error in the data file");
        }
    }
    public static void closeProgram() {
        boolean flag = ConfirmBox.display("Quit","Are you sure you want to quit?");
        if (flag) {
            FileStuff.saveInfo();
            window.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}