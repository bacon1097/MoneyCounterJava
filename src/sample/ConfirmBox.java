package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static boolean answer;
    public static boolean display(String title, String message) {
        //Setting the stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //Focus windows which stops you from doing things
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(200);

        //Label
        Label label = new Label();
        label.setText(message);

        //Yes button
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        //No button
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        //Setting layout
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label, yesButton, noButton);
        layout1.setAlignment(Pos.CENTER);

        //Setting scene. Show and pause the code
        window.setScene(new Scene(layout1));
        window.showAndWait();

        //Returning the answer at the end of this script
        return answer;
    }
}
