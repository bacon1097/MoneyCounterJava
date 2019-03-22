package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private AnchorPane settingsScene;

    @FXML
    private AnchorPane tabLayout;

    @FXML
    private ImageView walletImage;

    @FXML
    private ImageView settingsImage;

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
}