package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static String answer;
    public static String display(String title, String message, String type) {

        //Setting the stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //Focus windows which stops you from doing things
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(200);
        window.setResizable(false);

        //Label
        Label label = new Label();
        label.setText(message);

        //Yes button
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = "true";
            window.close();
        });

        //No button
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer = "false";
            window.close();
        });

        //Setting layout
        VBox layout1 = new VBox(10);
        if (type.equals("close")) {
            //Yes without saving button
            Button noSaveButton = new Button("Yes, without saving");
            noSaveButton.setOnAction(e -> {
                answer = "nosave";
                window.close();
            });
            layout1.getChildren().addAll(label, yesButton, noButton, noSaveButton);
        }
        else {
            layout1.getChildren().addAll(label, yesButton, noButton);
        }

        layout1.setAlignment(Pos.CENTER);

        //Setting scene. Show and pause the code
        Scene scene = new Scene(layout1);
        window.setScene(scene);
        window.showAndWait();

        //Returning the answer at the end of this script
        return answer;
    }
}