package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.List;

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
    private ListView debitList;
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

    public void initialize() {
        if (FileStuff.fileExists()) {
            String debits = FileStuff.getInfo(4);
            String temp = "";
            for (char c : debits.toCharArray()) {
                if (!String.valueOf(c).equals(",")) {
                    temp = temp.concat(String.valueOf(c));
                }
                else {
                    DebitStuff.addDebit(debitList, temp);
                    temp = "";
                }
            }
            try {
                String mainMoney = FileStuff.getInfo(0);        //For clarity
                String paydayDate = FileStuff.getInfo(2);
                String currentWage = FileStuff.getInfo(3);
                String amountAtPayDay = FileStuff.getInfo(5);

                MoneyStuff.setAmountAtPayDay(Float.parseFloat(amountAtPayDay));
                moneyDisplay.setText(String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));       //Setting the money owned value
                WageStuff.setPayDay(paydayDate);       //Setting the payday date
                WageStuff.setWage(Float.parseFloat(currentWage));      //Setting the wage
                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));
                MoneyStuff.setDailySpending(spendingDailyLabel);
                MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());

                System.out.println("Amount at payday is: " + MoneyStuff.getAmountAtPayDay());
                System.out.println("New Value is: " + String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));
                System.out.println("Daily Spending Value is: " + String.format("%.2f", Math.floor(WageStuff.getDailySpending() * 100) / 100));

            }
            catch (NumberFormatException e) {
                System.out.println("Error in the data file");
            }
        }
        else {
            if (ConfirmBox.display("Setup?", "Would you like to configure?")) {
                List list = FirstTimeSetup.display("Configuration");

                //Setting values from input
                moneyDisplay.setText(String.valueOf(list.get(0)));
                WageStuff.setPayDay(String.valueOf(list.get(1)));
                WageStuff.setWage(Float.parseFloat(String.valueOf(list.get(2))));
                MoneyStuff.setAmountAtPayDay(Float.parseFloat(String.valueOf(list.get(3))));

                //Set the savings
                MoneyStuff.setDailySpending(spendingDailyLabel);
                MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
            }
            else {
                System.out.println("New Value is: " + moneyDisplay.getText());
                WageStuff.setWage(0.00f);
                WageStuff.setPayDay("01-01-2019");
            }
        }
    }

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

    public void debitPlusImageClick() {
        DebitStuff.addDebit(debitList, debitInput.getText());
        MoneyStuff.setDailySpending(spendingDailyLabel);
    }

    public void debitMinusImageClick() {
        DebitStuff.deleteDebit(debitList);
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    public void setWage() {
        WageStuff.setWage(Float.parseFloat(wageInput.getText()));
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    public void setValue() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            MoneyStuff.setValue(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            MoneyStuff.setDailySpending(spendingDailyLabel);
            MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
        }
    }
    public void addMoney() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            MoneyStuff.addMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            MoneyStuff.setDailySpending(spendingDailyLabel);
            MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
        }
    }
    public void subtractMoney() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            MoneyStuff.subtractMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            MoneyStuff.setDailySpending(spendingDailyLabel);
            MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
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
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    public void paidImageClick() {
        MoneyStuff.paid(moneyDisplay);
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
}