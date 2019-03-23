package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;
import java.util.GregorianCalendar;
import javafx.scene.effect.ColorAdjust;

public class Controller {
    @FXML
    private AnchorPane settingsScene;
    @FXML
    private AnchorPane mainScene;
    @FXML
    private Label moneyDisplay;
    @FXML
    private TextField moneyInput;
    @FXML
    private Button subtractButton;
    @FXML
    private Button addButton;
    @FXML
    private Button newValButton;
    @FXML
    private AnchorPane tabLayout;
    @FXML
    private ImageView closeImage;
    @FXML
    private ImageView walletImage;
    @FXML
    private ImageView settingsImage;
    @FXML
    private ImageView minusImage;
    @FXML
    private ImageView plusImage;
    @FXML
    private ImageView resetImage;
    @FXML
    private ImageView saveImage;

    public void setValue() {
        Boolean flag = validateInput(moneyInput.getText());
        if (flag) {
            moneyDisplay.setText(moneyInput.getText());
        }
    }

    public void addMoney() {
        Boolean flag = validateInput(moneyInput.getText());
        if (flag) {
            moneyDisplay.setText(plus(moneyDisplay.getText(), moneyInput.getText()));
        }
    }

    public void subtractMoney() {
        Boolean flag = validateInput(moneyInput.getText());
        if (flag) {
            moneyDisplay.setText(minus(moneyDisplay.getText(), moneyInput.getText()));
        }
    }

    public String minus(String val1, String val2) {
        return String.valueOf(Float.parseFloat(val1) - Float.parseFloat(val2));
    }

    public String plus(String val1, String val2) {
        return String.valueOf(Float.parseFloat(val1) + Float.parseFloat(val2));
    }

    public boolean validateInput(String value) {
        Boolean flag = new Boolean(true);
        try {
            Float.parseFloat(value);
            flag = true;
        }
        catch (NumberFormatException e){
            flag = false;
            System.out.println("Input is not a valid float");
        }
        if (value.equals(null)) {
            flag = false;
            System.out.println("Nothing Inputted");
        }
        return flag;
    }

    public void getDate() {
        DateInfo.getDate();
    }

    public void closeProgram() {
        boolean flag = ConfirmBox.display("Quit?","Are you sure you want to quit?");
        if (flag) {
            Stage stage = (Stage) closeImage.getScene().getWindow();
            stage.close();
        }
    }

    public void settingsImageEnter() {
        colorChange(settingsImage);
    }
    public void settingsImageLeave() { colorChangeBack(settingsImage); }
    public void closeImageEnter() {
        colorChange(closeImage);
    }
    public void closeImageLeave() {
        colorChangeBack(closeImage);
    }
    public void walletImageEnter() { colorChange(walletImage); }
    public void walletImageLeave() { colorChangeBack(walletImage); }
    public void minusImageEnter() {
        colorChange(minusImage);
    }
    public void minusImageLeave() {
        colorChangeBack(minusImage);
    }
    public void plusImageEnter() {
        colorChange(plusImage);
    }
    public void plusImageLeave() { colorChangeBack(plusImage); }
    public void resetImageEnter() {
        colorChange(resetImage);
    }
    public void resetImageLeave() { colorChangeBack(resetImage); }
    public void saveImageEnter() {
        colorChange(saveImage);
    }
    public void saveImageLeave() { colorChangeBack(saveImage); }

    public void colorChange(ImageView image) {
        image.setOpacity(1);
        image.setScaleX(1.3);
        image.setScaleY(1.3);
        image.setScaleZ(1.3);
    }

    public void colorChangeBack(ImageView image) {
        image.setOpacity(0.5);
        image.setScaleX(1);
        image.setScaleY(1);
        image.setScaleZ(1);
    }

    public void saveData() {
        FileWriting.saveInfo();
    }
}