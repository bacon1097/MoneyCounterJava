package sample;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

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
    private ImageView debitPaid;
    @FXML
    private Label daysSincePayDayLabel;
    @FXML
    private Label spendingDailyLabel;
    @FXML
    private AnchorPane mainParent;
    @FXML
    private Label wageDisplayLabel;

    public void initialize() {
        mainScene.setCacheShape(true);
        tabLayout.setCacheShape(true);
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
                WageStuff.setWage(Float.parseFloat(currentWage), wageDisplayLabel);      //Setting the wage
                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));

                //Set the savings
                MoneyStuff.setDailySpending(spendingDailyLabel);
                MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());

                System.out.println("Amount at payday is: " + MoneyStuff.getAmountAtPayDay());
                System.out.println("New Value is: " + String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));
                System.out.println("Daily Spending Value is: " + String.format("%.2f", Math.floor(WageStuff.getDailySpending() * 100) / 100));

                animate(mainScene, "right", 1, "load");
                animate(tabLayout,"left", 1, "load");
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
                WageStuff.setWage(Float.parseFloat(String.valueOf(list.get(2))), wageDisplayLabel);
                MoneyStuff.setAmountAtPayDay(Float.parseFloat(String.valueOf(list.get(3))));
                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));

                //Set the savings
                MoneyStuff.setDailySpending(spendingDailyLabel);
                MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
            }
            else {
                System.out.println("New Value is: " + moneyDisplay.getText());
                WageStuff.setWage(0.00f, wageDisplayLabel);
                WageStuff.setPayDay("01-01-2019");

                animate(mainScene, "right", 1, "load");
                animate(tabLayout,"left", 1, "load");
            }
        }
    }
    private void animate(AnchorPane scene, String direction, double s, String type) {
        double translateFromValue = scene.getLayoutX();
        double translateToValue = 0;
        if (direction.equals("right")) {        //Setting the direction of the translation
            translateFromValue = -600;      //Setting the appropriate translation variales depending on the direction of translation
            translateToValue = 615;
        }
        else if (direction.equals("left")) {
            translateFromValue = 630;
            translateToValue = -615;
        }
        else if (direction.equals("backLeft") || direction.equals("backRight")) {       //These 2 values are used when switching pages
            translateToValue = 0;       //Value is 0 because the scene needs to be set back to its original position
        }
        else {
            System.out.println("*animate* Animation doesn't exist");        //Validation checking
        }
        scene.setLayoutX(translateFromValue);       //Ensure that the scene is at its original position
        TranslateTransition t = new TranslateTransition(Duration.seconds(s), scene);
        t.setToX(translateToValue);
        if (type.equals("load")) {      //If loading then translate inwards with a fade in
            scene.setOpacity(0);        //Start the scene with 0 opacity
            FadeTransition f = new FadeTransition(Duration.seconds(s), scene);
            f.setToValue(1);        //Animate the opacity to 1
            ParallelTransition p = new ParallelTransition();
            p.getChildren().setAll(f, t);
            p.play();
        }
        else if (type.equals("close")) {        //If closing then reverse the load animation
            FadeTransition f = new FadeTransition(Duration.seconds(s), scene);      //Add a fade out transition
            f.setToValue(0);        //Setting the opacity level to 0
            ParallelTransition p = new ParallelTransition();        //Adding the transitions into a singular transition
            p.getChildren().setAll(f, t);
            if (scene.getId().equals(tabLayout.getId())) {
                p.setOnFinished(e -> Main.closeProgram());      //Close the program after the transition
            }
            p.play();
        }
        else if (type.equals("switch")) {        //If switching pane then keep the appropriate scene visible and translate
            t.setOnFinished(e -> {      //Make sure that the scene switching out disappears after the animation
                if (direction.equals("right") || direction.equals("left")) {
                    if (!scene.getId().equals(mainScene.getId())) {      //Checking what scene is being switched out
                        mainScene.setVisible(false);        //Disable the visibility of the parent
                    } else if (!scene.getId().equals(settingsScene.getId())) {
                        settingsScene.setVisible(false);
                    }
                    else {
                        System.out.println("*animate* scene doesn't exist");        //Validation checking
                    }
                }
            });
            if (direction.equals("right") || direction.equals("left")) {        //If the direction of the scene is not sliding out of view then set the scene as visible
                scene.setVisible(true);
                for (Node element : scene.getChildren()) {
                    element.setVisible(true);
                }
            }
            t.play();       //Play the animation
        }
        else {
            System.out.println("*animate* animation doesn't exist");        //Validation checking
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
    public void debitEnter() { colorChange(debitPaid); }
    public void debitLeave() { colorChangeBack(debitPaid); }
    public void saveData() { FileStuff.saveInfo(); }
    public void closeProgram() {
        if (ConfirmBox.display("Quit","Are you sure you want to quit?")) {
            closer();
        }
    }

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
        WageStuff.setWage(Float.parseFloat(wageInput.getText()), wageDisplayLabel);
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    public void setValue() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            refreshMoney("set");
        }
    }
    public void addMoney() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            refreshMoney("plus");
        }
    }
    public void subtractMoney() {
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            refreshMoney("minus");
        }
    }
    private void refreshMoney(String operation) {
        FadeTransition f = new FadeTransition();
        double t = new Duration(250).toMillis();
        f.setToValue(0);
        f.setDuration(Duration.millis(t));
        f.setNode(moneyDisplay);
        f.setOnFinished(e -> {
            if (operation.equals("plus")) {
                MoneyStuff.addMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            } else if (operation.equals("minus")) {
                MoneyStuff.subtractMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            } else if (operation.equals("set")) {
                MoneyStuff.setValue(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            }
            MoneyStuff.setDailySpending(spendingDailyLabel);
            MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
            FadeTransition f2 = new FadeTransition();
            f2.setToValue(0.5);
            f2.setDuration(Duration.millis(t));
            f2.setNode(moneyDisplay);
            FadeTransition f3 = new FadeTransition();
            f3.setToValue(0.5);
            f3.setDuration(Duration.millis(t));
            f3.setNode(moneySaveDisplay);
            moneyDisplay.setTranslateY(-10);
            TranslateTransition t3 = new TranslateTransition();
            t3.setToY(0);
            t3.setDuration(Duration.millis(t - 50));
            t3.setNode(moneyDisplay);
            moneySaveDisplay.setTranslateY(-10);
            TranslateTransition t4 = new TranslateTransition();
            t4.setToY(0);
            t4.setDuration(Duration.millis(t - 50));
            t4.setNode(moneySaveDisplay);
            ParallelTransition p = new ParallelTransition();
            p.getChildren().setAll(f2, f3, t3, t4);
            p.play();
        });
        FadeTransition f1 = new FadeTransition();
        f1.setToValue(0);
        f1.setDuration(Duration.millis(t));
        f1.setNode(moneySaveDisplay);
        TranslateTransition t1 = new TranslateTransition();
        t1.setToY(10);
        t1.setDuration(Duration.millis(t - 50));
        t1.setNode(moneyDisplay);
        TranslateTransition t2 = new TranslateTransition();
        t2.setToY(10);
        t2.setDuration(Duration.millis(t - 50));
        t2.setNode(moneySaveDisplay);
        ParallelTransition p = new ParallelTransition();
        p.getChildren().setAll(f, f1, t1, t2);
        p.play();
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
        animate(mainScene, "backRight", 0.25, "switch");
        animate(settingsScene, "left", 0.25, "switch");
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());

    }
    public void walletImageClick() {
        animate(settingsScene, "backLeft", 0.25, "switch");
        animate(mainScene, "right", 0.25, "switch");
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    public void closer() {
        animate(settingsScene, "backRight", 1, "close");
        animate(mainScene, "backRight", 1, "close");
        animate(tabLayout, "backLeft", 1, "close");
    }
    public void paidImageClick() {
        MoneyStuff.paid(moneyDisplay);
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    public void debitImageClick() {
        float item = Float.parseFloat(debitList.getSelectionModel().getSelectedItem().toString());
        MoneyStuff.subtractMoney(moneyDisplay, item);
        MoneyStuff.setAmountAtPayDay(MoneyStuff.amountAtPayDay - item);
        System.out.println("Debit Paid: " + String.valueOf(item));
    }
}