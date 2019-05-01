package sample;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;

public class Controller {
    @FXML
    private AnchorPane settingsScene;
    @FXML
    private AnchorPane investScene;
    @FXML
    private PieChart pieChart1;
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
    private Label perMonthLabel;
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
    private ImageView investImage;
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
    private ImageView debitPaidImage;
    @FXML
    private Label daysSincePayDayLabel;
    @FXML
    private Label spendingDailyLabel;
    @FXML
    private AnchorPane mainParent;
    @FXML
    private Label wageDisplayLabel;
    @FXML
    private Label differenceDisplayLabel;

    public void initialize() {
        mainScene.setCacheShape(true);
        tabLayout.setCacheShape(true);
        if (FileStuff.fileExists(FileStuff.fileName)) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Bitcoin", 100),
                    new PieChart.Data("Litecoin", 50));
            pieChart1.setData(pieChartData);
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
                String wageSliderValue = FileStuff.getInfo(6);

                MoneyStuff.setAmountAtPayDay(Float.parseFloat(amountAtPayDay));
                System.out.println("Amount at payday is: " + MoneyStuff.getAmountAtPayDay());

                moneyDisplay.setText(String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));       //Setting the money owned value
                System.out.println("New Value is: " + String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));

                WageStuff.setPayDay(paydayDate);       //Setting the payday date
                System.out.println("New payday date is: " + WageStuff.getPayDay());

                WageStuff.setWage(Float.parseFloat(currentWage), wageDisplayLabel);      //Setting the wage
                System.out.println("New wage is: " + WageStuff.getWage());

                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));     //Setting the days since being paid

                if (wageSliderValue != null && (wageSliderValue.equals("Monthly") || wageSliderValue.equals("Weekly"))) {
                    System.out.println("New slider value: " + wageSliderValue);
                    WageStuff.setWageSlider(wageSlider, perMonthLabel, wageSliderValue);       //Setting the slider for monthly or weekly
                }
                else{
                    WageStuff.setWageSlider(wageSlider, perMonthLabel, "Monthly");
                    System.out.println("Setting wage slider to: Monthly");
                }

                animate(mainScene, "right", 1, "load");
                animate(tabLayout,"left", 1, "load");
            }
            catch (NumberFormatException | NullPointerException e) {
                System.out.println("Error in the data file");
            }
        }
        else {
            if (ConfirmBox.display("Setup?", "Would you like to configure?", "setup").equals("true")) {
                List list = FirstTimeSetup.display("Configuration");

                //Setting values from input
                if (!String.valueOf(list.get(0)).isEmpty()) {
                    try {
                        MoneyStuff.setValue(moneyDisplay, Float.parseFloat(String.valueOf(list.get(0))));      //Setting the main value
                    }
                    catch (NumberFormatException e) {
                        System.out.println("*initialize* Cannot parse value as a float");
                    }
                }
                System.out.println("New Value is: " + moneyDisplay.getText());

                if (!String.valueOf(list.get(1)).isEmpty()) {
                    WageStuff.setPayDay(String.valueOf(list.get(1)));       //Setting the date paid
                }
                else {
                    WageStuff.setPayDay("01-01-2019");
                }
                System.out.println("New payday date is: " + WageStuff.getPayDay());

                if (!String.valueOf(list.get(2)).isEmpty()) {
                    WageStuff.setWage(Float.parseFloat(String.valueOf(list.get(2))), wageDisplayLabel);     //Setting the wage
                }
                System.out.println("New wage is: " + WageStuff.getWage());

                if (!String.valueOf(list.get(3)).isEmpty()) {
                    MoneyStuff.setAmountAtPayDay(Float.parseFloat(String.valueOf(list.get(3))));        //Setting the amount at payday
                }
                System.out.println("New amount at payday is:" + MoneyStuff.getAmountAtPayDay());

                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));
                WageStuff.setWageSlider(wageSlider, perMonthLabel, "Monthly");

                animate(mainScene, "right", 1, "load");
                animate(tabLayout,"left", 1, "load");
            }
            else {
                System.out.println("New Value is: " + moneyDisplay.getText());

                WageStuff.setWage(0.00f, wageDisplayLabel);
                System.out.println("New wage is: " + WageStuff.getWage());

                WageStuff.setPayDay("01-01-2019");
                System.out.println("New payday date is: " + WageStuff.getPayDay());

                MoneyStuff.setAmountAtPayDay(Float.parseFloat("0"));        //Setting the amount at payday
                System.out.println("New amount at payday is:" + MoneyStuff.getAmountAtPayDay());

                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));
                WageStuff.setWageSlider(wageSlider, perMonthLabel, "Monthly");

                animate(mainScene, "right", 1, "load");
                animate(tabLayout,"left", 1, "load");
            }
        }
        //Set the savings
        refreshData();
        try {
            wageSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (newValue.floatValue() == 0 || newValue.floatValue() == 1) {
                        System.out.println(newValue);
                        wageSliderChange();
                    }
                }
            });
            moneyDisplay.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    refreshData();
                }
            });
            wageDisplayLabel.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    refreshData();
                }
            });
        }
        catch (NullPointerException e) {
            System.out.println("*initialize* Check listeners, NullPointerException");
        }
    }
    private void colorChange(ImageView image) {
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
    private void colorChangeBack(ImageView image) {
        image.setOpacity(0.5);
        image.setScaleX(1);
        image.setScaleY(1);
        image.setScaleZ(1);
        image.setEffect(null);
    }
    private void releaseClickAnimate(ImageView image) {
        double time = 20;
        AudioClip noise = new AudioClip(getClass().getResource("/sample/resources/sounds/clickNoise.wav").toString());
        noise.setVolume(0.1);
        noise.play();
        KeyValue kv = new KeyValue(image.scaleXProperty(), 1.0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(time), kv);
        KeyValue kv1 = new KeyValue(image.scaleYProperty(), 1.0, Interpolator.EASE_IN);
        KeyFrame kf1 = new KeyFrame(Duration.millis(time), kv1);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(kf, kf1);
        timeline.play();
    }
    private void clickAnimate(ImageView image) {
        double time = 20;
        KeyValue kv = new KeyValue(image.scaleXProperty(), 0.8, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(time), kv);
        KeyValue kv1 = new KeyValue(image.scaleYProperty(), 0.8, Interpolator.EASE_IN);
        KeyFrame kf1 = new KeyFrame(Duration.millis(time), kv1);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(kf, kf1);
        timeline.play();
    }
    private void animate(AnchorPane scene, String direction, double time, String type) {
        switch (type) {
            case "load":
                scene.opacityProperty().set(0);
                if (direction.equals("left")) {
                    scene.translateXProperty().set(scene.getPrefWidth() + scene.getLayoutX());
                } else if (direction.equals("right")) {
                    scene.translateXProperty().set(-scene.getPrefWidth() - scene.getLayoutX());
                } else {
                    System.out.println("Direction doesn't exist");
                }
                KeyValue kv = new KeyValue(scene.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(time), kv);
                KeyValue kv1 = new KeyValue(scene.opacityProperty(), 1.0f);
                KeyFrame kf1 = new KeyFrame(Duration.seconds(time), kv1);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().addAll(kf, kf1);
                timeline.play();
                break;
            case "switch":
                if (direction.equals("left") || direction.equals("right")) {
                    if (!scene.isVisible()) {
                        scene.setVisible(true);
                        if (direction.equals("left")) {
                            scene.translateXProperty().set(scene.getPrefWidth() + scene.getLayoutX());
                        }
                        else {
                            scene.translateXProperty().set(-scene.getPrefWidth() - scene.getLayoutX());
                        }
                        KeyValue kv2 = new KeyValue(scene.translateXProperty(), 0, Interpolator.EASE_IN);
                        KeyFrame kf2 = new KeyFrame(Duration.seconds(time), kv2);
                        Timeline timeline1 = new Timeline();
                        timeline1.getKeyFrames().add(kf2);
                        timeline1.play();
                    }
                } else if (direction.equals("backLeft") || direction.equals("backRight")) {
                    switchAnimate(scene, direction, time);
                }
                break;
            case "close":
                switchAnimate(scene, direction, time);
                KeyValue kv3 = new KeyValue(scene.opacityProperty(), 0.0f);
                KeyFrame kf3 = new KeyFrame(Duration.seconds(time), kv3);
                Timeline timeline2 = new Timeline();
                timeline2.getKeyFrames().add(kf3);
                timeline2.setOnFinished(e -> Main.closeProgram());
                timeline2.play();
                break;
            default:
                System.out.println("Type doesn't exist: " + type);
                break;
        }
    }
    private void switchAnimate(AnchorPane scene, String direction, double time) {
        scene.translateXProperty().set(15);
        if (direction.equals("backLeft")) {
            KeyValue kv = new KeyValue(scene.translateXProperty(), scene.getPrefWidth() + scene.getLayoutX(), Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(time), kv);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(e -> scene.setVisible(false));
            timeline.play();
        } else if (direction.equals("backRight")) {
            KeyValue kv = new KeyValue(scene.translateXProperty(), -scene.getPrefWidth() - scene.getLayoutX(), Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(time), kv);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(e -> scene.setVisible(false));
            timeline.play();
        }
    }
    public void settingsImagePress() { clickAnimate(settingsImage); }
    public void closeImagePress() { clickAnimate(closeImage); }
    public void walletImagePress() { clickAnimate(walletImage); }
    public void minusImagePress() { clickAnimate(minusImage); }
    public void plusImagePress() { clickAnimate(plusImage); }
    public void resetImagePress() { clickAnimate(resetImage); }
    public void saveImagePress() { clickAnimate(saveImage); }
    public void confirmImagePress() { clickAnimate(confirmImage); }
    public void debitMinusPress() { clickAnimate(debitMinusImage); }
    public void debitPlusPress() { clickAnimate(debitPlusImage); }
    public void paidPress() { clickAnimate(paidImage); }
    public void debitImagePress() { clickAnimate(debitPaidImage); }
    public void investImagePress() { clickAnimate(investImage); }

    public void settingsImageEnter() { colorChange(settingsImage); }
    public void closeImageEnter() { colorChange(closeImage); }
    public void walletImageEnter() { colorChange(walletImage); }
    public void minusImageEnter() { colorChange(minusImage); }
    public void plusImageEnter() { colorChange(plusImage); }
    public void resetImageEnter() { colorChange(resetImage); }
    public void saveImageEnter() { colorChange(saveImage); }
    public void confirmImageEnter() { colorChange(confirmImage); }
    public void debitMinusEnter() { colorChange(debitMinusImage); }
    public void debitPlusEnter() { colorChange(debitPlusImage); }
    public void paidEnter() { colorChange(paidImage); }
    public void debitEnter() { colorChange(debitPaidImage); }
    public void investImageEnter() { colorChange(investImage); }

    public void settingsImageLeave() { colorChangeBack(settingsImage); }
    public void closeImageLeave() { colorChangeBack(closeImage); }
    public void walletImageLeave() { colorChangeBack(walletImage); }
    public void minusImageLeave() { colorChangeBack(minusImage); }
    public void plusImageLeave() { colorChangeBack(plusImage); }
    public void resetImageLeave() { colorChangeBack(resetImage); }
    public void saveImageLeave() { colorChangeBack(saveImage); }
    public void confirmImageLeave() { colorChangeBack(confirmImage); }
    public void debitMinusLeave() { colorChangeBack(debitMinusImage); }
    public void debitPlusLeave() { colorChangeBack(debitPlusImage); }
    public void paidLeave() { colorChangeBack(paidImage); }
    public void debitLeave() { colorChangeBack(debitPaidImage); }
    public void investImageLeave() { colorChangeBack(investImage); }
    public void saveImageClick() {
        releaseClickAnimate(saveImage);
        FileStuff.saveInfo();
    }

    public void confirmImageClick() {
        releaseClickAnimate(confirmImage);
        WageStuff.setWage(Float.parseFloat(wageInput.getText()), wageDisplayLabel);
    }
    public void debitPlusImageClick() {
        if (MoneyStuff.validateInput(debitInput.getText())) {
            releaseClickAnimate(debitPlusImage);
            DebitStuff.addDebit(debitList, debitInput.getText());
            refreshData();
        }
    }
    public void debitMinusImageClick() {
        releaseClickAnimate(debitMinusImage);
        DebitStuff.deleteDebit(debitList);
        refreshData();
    }
    public void resetImageClick() {
        releaseClickAnimate(resetImage);
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            refreshMoney("set");
        }
    }
    public void addMoney() {
        releaseClickAnimate(plusImage);
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            refreshMoney("plus");
        }
    }
    public void subtractMoney() {
        releaseClickAnimate(minusImage);
        if (MoneyStuff.validateInput(moneyInput.getText())) {
            refreshMoney("minus");
        }
    }
    /*
    Main calculation of savings.
     */
    private void refreshData() {
        MoneyStuff.setDailySpending(spendingDailyLabel);
        MoneyStuff.setCalculateSavings(moneySaveDisplay, moneyDisplay.getText());
    }
    private void refreshMoney(String operation) {
        float valueBefore = Float.parseFloat(moneyDisplay.getText());
        FadeTransition f = new FadeTransition();
        double t = new Duration(250).toMillis();
        f.setToValue(0);
        f.setDuration(Duration.millis(t));
        f.setNode(moneyDisplay);
        f.setOnFinished(e -> {
            switch (operation) {
                case "plus":
                    MoneyStuff.addMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
                    break;
                case "minus":
                    MoneyStuff.subtractMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
                    break;
                case "set":
                    MoneyStuff.setValue(moneyDisplay, Float.parseFloat(moneyInput.getText()));
                    break;
            }
            differenceDisplayLabel.setText(String.valueOf(Math.floor((Float.parseFloat(differenceDisplayLabel.getText()) - (valueBefore - Float.parseFloat(moneyDisplay.getText()))) * 100) / 100));      //Setting the difference calculated when changes are made.
            refreshData();

            FadeTransition f2 = new FadeTransition();
            f2.setToValue(0.5);
            f2.setDuration(Duration.millis(t));
            f2.setNode(moneyDisplay);

            FadeTransition f3 = new FadeTransition();
            f3.setToValue(0.5);
            f3.setDuration(Duration.millis(t));
            f3.setNode(moneySaveDisplay);

            FadeTransition f5 = new FadeTransition();
            f5.setToValue(0.5);
            f5.setDuration(Duration.millis(t));
            f5.setNode(differenceDisplayLabel);

            differenceDisplayLabel.setTranslateY(-10);
            TranslateTransition t6 = new TranslateTransition();
            t6.setToY(0);
            t6.setDuration(Duration.millis(t - 50));
            t6.setNode(differenceDisplayLabel);

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
            p.getChildren().setAll(f2, f3, t3, t4, f5, t6);
            p.play();
        });
        FadeTransition f1 = new FadeTransition();
        f1.setToValue(0);
        f1.setDuration(Duration.millis(t));
        f1.setNode(moneySaveDisplay);

        FadeTransition f4 = new FadeTransition();
        f4.setToValue(0);
        f4.setDuration(Duration.millis(t));
        f4.setNode(differenceDisplayLabel);

        TranslateTransition t1 = new TranslateTransition();
        t1.setToY(10);
        t1.setDuration(Duration.millis(t - 50));
        t1.setNode(moneyDisplay);

        TranslateTransition t2 = new TranslateTransition();
        t2.setToY(10);
        t2.setDuration(Duration.millis(t - 50));
        t2.setNode(moneySaveDisplay);

        TranslateTransition t5 = new TranslateTransition();
        t5.setToY(10);
        t5.setDuration(Duration.millis(t - 50));
        t5.setNode(differenceDisplayLabel);

        ParallelTransition p = new ParallelTransition();
        p.getChildren().setAll(f, f1, t1, t2, f4, t5);
        p.play();
    }
    public void walletImageClick() {
        releaseClickAnimate(walletImage);
        animate(settingsScene, "backLeft", 0.25, "switch");
        animate(mainScene, "right", 0.25, "switch");
        animate(investScene, "backLeft", 0.25, "switch");
        refreshData();
    }
    public void settingsImageClick() {
        releaseClickAnimate(settingsImage);
        if (mainScene.isVisible()) {
            animate(settingsScene, "left", 0.25, "switch");
            animate(mainScene, "backRight", 0.25, "switch");
        }
        else if (investScene.isVisible()) {
            animate(settingsScene, "right", 0.25, "switch");
            animate(investScene, "backLeft", 0.25, "switch");
        }
        refreshData();

    }
    public void investImageClick() {
        releaseClickAnimate(investImage);
        animate(settingsScene, "backRight", 0.25, "switch");
        animate(mainScene, "backRight", 0.25, "switch");
        animate(investScene, "left", 0.25, "switch");
        refreshData();
    }
    public void closeProgram() {
        releaseClickAnimate(closeImage);
        String result = ConfirmBox.display("Quit","Are you sure you want to quit?", "close");
        if (result.equals("true") || result.equals("nosave")) {
            animate(settingsScene, "backRight", 1, "close");
            animate(investScene, "backRight", 1, "close");
            animate(mainScene, "backRight", 1, "close");
            animate(tabLayout, "backLeft", 1, "close");
        }
    }
    public void paidImageClick() {
        releaseClickAnimate(paidImage);
        MoneyStuff.paid(moneyDisplay);
        refreshData();
    }
    public void debitImageClick() {
        releaseClickAnimate(debitPaidImage);
        float item = Float.parseFloat(debitList.getSelectionModel().getSelectedItem().toString());
        MoneyStuff.subtractMoney(moneyDisplay, item);
        MoneyStuff.setAmountAtPayDay(MoneyStuff.amountAtPayDay - item);
        System.out.println("Debit Paid: " + item);
    }
    private void wageSliderChange() {
        String timePeriod;
        if (wageSlider.getValue() == 1) {
            timePeriod = "Monthly";
        }
        else if (wageSlider.getValue() == 0) {
            timePeriod = "Weekly";
        }
        else {
            timePeriod = null;
            System.out.println("*wageSliderChange* Not a valid slider option");
        }
        if (timePeriod != null) {
            WageStuff.setWageSlider(wageSlider, perMonthLabel, timePeriod);
        }
    }
}