package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FirstTimeSetup {
    public static List display(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //Focus windows which stops you from doing things
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(200);
        window.setResizable(false);

        List list = new ArrayList();

        //First label
        Label label1 = new Label("Money Owned Currently: ");
        TextField textField1 = new TextField();
        textField1.setPromptText("0000");

        HBox hBox1 = new HBox(10);
        hBox1.getChildren().addAll(label1, textField1);

        //Second Label
        Label label2 = new Label("Date paid: ");
        TextField textField2 = new TextField();
        textField2.setPromptText("dd-MM-YYYY");

        HBox hBox2 = new HBox(10);
        hBox2.getChildren().addAll(label2, textField2);

        //Third Label
        Label label3 = new Label("Current Wage (Month): ");
        TextField textField3 = new TextField();
        textField3.setPromptText("0000");

        HBox hBox3 = new HBox(10);
        hBox3.getChildren().addAll(label3, textField3);

        //Fourth Label
        Label label4 = new Label("Amount immediately after being paid: ");
        TextField textField4 = new TextField();
        textField4.setPromptText("0000");

        HBox hBox4 = new HBox(10);
        hBox4.getChildren().addAll(label4, textField4);


        //Submit button
        Button button = new Button("Submit");
        button.setOnAction(e -> {
            list.add(textField1.getText());
            list.add(textField2.getText());
            list.add(textField3.getText());
            list.add(textField4.getText());
            window.close();
        });

        //Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        //Layout
        VBox vBoxMain = new VBox(10);
        vBoxMain.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, button, closeButton);

        window.setScene(new Scene(vBoxMain));
        window.showAndWait();
        return list;
    }
}
