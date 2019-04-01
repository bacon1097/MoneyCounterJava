package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private AnchorPane settingsScene;
    @FXML
    private Slider wageSlider;
    @FXML
    private Label wageLabel;
    @FXML
    private Label wageDescription;
    @FXML
    private Label weeklyLabel;
    @FXML
    private Label monthlyLabel;
    @FXML
    private TextField wageInput;
    @FXML
    private ImageView confirmImage;
    @FXML
    private Label wageLabel1;
    @FXML
    private Label wageDescription1;
    @FXML
    private TableView<DebitStuff> debitTable;
    @FXML
    private TableColumn<DebitStuff, String> debitColumn;
    @FXML
    private ImageView debitMinusImage;
    @FXML
    private ImageView debitPlusImage;
    @FXML
    private TextField debitInput;
    @FXML
    private AnchorPane mainScene;
    @FXML
    private Label moneyDisplay;
    @FXML
    private ImageView coinImage;
    @FXML
    private ImageView minusImage;
    @FXML
    private ImageView plusImage;
    @FXML
    private Label moneySaveDisplay;
    @FXML
    private ImageView piggyImage;
    @FXML
    private ImageView resetImage;
    @FXML
    private ImageView saveImage;
    @FXML
    private ImageView testingImage;
    @FXML
    private TextField moneyInput;
    @FXML
    private AnchorPane tabLayout;
    @FXML
    private ImageView closeImage;
    @FXML
    private ImageView walletImage;
    @FXML
    private ImageView settingsImage;
    @FXML
    private ImageView paidImage;
    @FXML
    private Label daysSincePayDayLabel;
    @FXML
    private Label spendingDailyLabel;
    @FXML
    private AnchorPane mainParent;

    public void settingsImageEnter() { colorChange(settingsImage); }
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
    public void confirmImageEnter() { colorChange(confirmImage); }
    public void confirmImageLeave() { colorChangeBack(confirmImage); }
    public void debitMinusEnter() { colorChange(debitMinusImage); }
    public void debitMinusLeave() { colorChangeBack(debitMinusImage); }
    public void debitPlusEnter() { colorChange(debitPlusImage); }
    public void debitPlusLeave() { colorChangeBack(debitPlusImage); }
    public void paidEnter() { colorChange(paidImage); }
    public void paidLeave() { colorChangeBack(paidImage); }
    public void saveData() { FileStuff.saveInfo(); }
    public void closeProgram() { Main.closeProgram(); }

    public void initialize() {
        debitColumn.setCellValueFactory(new PropertyValueFactory<DebitStuff, String>("debit"));
    }
    public void debitPlusImageClick() {
        MoneyStuff.setDailySpending(spendingDailyLabel);
    }
    public void setWage() {
        WageStuff.setWage(Float.parseFloat(wageInput.getText()));
        MoneyStuff.setDailySpending(spendingDailyLabel);
    }
    public void setValue() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            MoneyStuff.setValue(moneyDisplay, Float.parseFloat(moneyInput.getText()));
        }
    }
    public void addMoney() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            MoneyStuff.addMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
        }
    }
    public void subtractMoney() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            MoneyStuff.subtractMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
        }
    }
    public void colorChange(ImageView image) {
        image.setOpacity(1);
        image.setScaleX(1.3);
        image.setScaleY(1.3);
        image.setScaleZ(1.3);
        DropShadow shadow = new DropShadow();
        shadow.setWidth(5);
        shadow.setHeight(5);
        shadow.setRadius(4);
        shadow.setColor(Color.valueOf("BLACK"));
        shadow.blurTypeProperty().setValue(BlurType.GAUSSIAN);
        image.setEffect(shadow);
    }
    public void colorChangeBack(ImageView image) {
        image.setOpacity(0.5);
        image.setScaleX(1);
        image.setScaleY(1);
        image.setScaleZ(1);
        image.setEffect(null);
    }
    public void settingsImageClick() {
        settingsScene.setVisible(true);
        for (Node element : settingsScene.getChildren()) {
            element.setVisible(true);
        }
        mainScene.setVisible(false);
        for (Node element : mainScene.getChildren()) {
            element.setVisible(false);
        }
    }
    public void walletImageClick() {
        settingsScene.setVisible(false);
        for (Node element : settingsScene.getChildren()) {
            element.setVisible(false);
        }
        mainScene.setVisible(true);
        for (Node element : mainScene.getChildren()) {
            element.setVisible(true);
        }
    }
    public void paidImageClick() {
        MoneyStuff.paid(moneyDisplay);
    }
    public void testImage() {
        System.out.println(MoneyStuff.calculateSavings());
        //System.out.println(WageStuff.getDailySpending());
    }
}