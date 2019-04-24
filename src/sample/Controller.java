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
    private ImageView debitPaid;
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
                moneyDisplay.setText(String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));       //Setting the money owned value
                WageStuff.setPayDay(paydayDate);       //Setting the payday date
                WageStuff.setWage(Float.parseFloat(currentWage), wageDisplayLabel);      //Setting the wage
                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));
                WageStuff.setWageSlider(wageSlider, perMonthLabel, wageSliderValue);       //Setting the slider for monthly or weekly

                //Set the savings
                refreshData();

                System.out.println("Amount at payday is: " + MoneyStuff.getAmountAtPayDay());
                System.out.println("New Value is: " + String.format("%.2f", Math.floor(Float.parseFloat(mainMoney) * 100) / 100));
                System.out.println("Daily Spending Value is: " + String.format("%.2f", Math.floor(WageStuff.getDailySpending() * 100) / 100));

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
                catch (NullPointerException e) {}

                animate(mainScene, "right", 1, "load");
                animate(tabLayout,"left", 1, "load");
            }
            catch (NumberFormatException e) {
                System.out.println("Error in the data file");
            }
        }
        else {
            if (ConfirmBox.display("Setup?", "Would you like to configure?", "setup").equals("true")) {
                List list = FirstTimeSetup.display("Configuration");

                //Setting values from input
                moneyDisplay.setText(String.valueOf(list.get(0)));
                WageStuff.setPayDay(String.valueOf(list.get(1)));
                WageStuff.setWage(Float.parseFloat(String.valueOf(list.get(2))), wageDisplayLabel);
                MoneyStuff.setAmountAtPayDay(Float.parseFloat(String.valueOf(list.get(3))));
                daysSincePayDayLabel.setText(String.valueOf(DateInfo.daysSince()));

                //Set the savings
                refreshData();
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
    private void animate(AnchorPane scene, String direction, double time, String type) {
        if (type.equals("load")) {
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
        }
        else if (type.equals("switch")) {
            if (direction.equals("left") || direction.equals("right")) {
                if (!scene.isVisible()) {
                    scene.setVisible(true);
                    if (direction.equals("left")) {
                        scene.translateXProperty().set(scene.getPrefWidth() + scene.getLayoutX());
                    } else if (direction.equals("right")) {
                        scene.translateXProperty().set(-scene.getPrefWidth() - scene.getLayoutX());
                    }
                    KeyValue kv = new KeyValue(scene.translateXProperty(), 0, Interpolator.EASE_IN);
                    KeyFrame kf = new KeyFrame(Duration.seconds(time), kv);
                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(kf);
                    timeline.play();
                }
            } else if (direction.equals("backLeft") || direction.equals("backRight")) {
                switchAnimate(scene, direction, time);
            }
        }
        else if (type.equals("close")) {
            switchAnimate(scene, direction, time);
            KeyValue kv = new KeyValue(scene.opacityProperty(), 0.0f);
            KeyFrame kf = new KeyFrame(Duration.seconds(time), kv);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(e -> Main.closeProgram());
            timeline.play();
        }
        else {
            System.out.println("Type doesn't exist: " + type);
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
    public void investImageEnter() { colorChange(investImage); }
    public void investImageLeave() { colorChangeBack(investImage); }
    public void saveData() { FileStuff.saveInfo(); }

    public void debitPlusImageClick() {
        DebitStuff.addDebit(debitList, debitInput.getText());
        refreshData();
    }

    public void debitMinusImageClick() {
        DebitStuff.deleteDebit(debitList);
        refreshData();
    }
    public void setWage() {
        WageStuff.setWage(Float.parseFloat(wageInput.getText()), wageDisplayLabel);
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
            if (operation.equals("plus")) {
                MoneyStuff.addMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            } else if (operation.equals("minus")) {
                MoneyStuff.subtractMoney(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            } else if (operation.equals("set")) {
                MoneyStuff.setValue(moneyDisplay, Float.parseFloat(moneyInput.getText()));
            }
            differenceDisplayLabel.setText(String.valueOf(Float.parseFloat(differenceDisplayLabel.getText()) - (valueBefore - Float.parseFloat(moneyDisplay.getText()))));      //Setting the difference calculated when changes are made.
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
    public void walletImageClick() {
        animate(settingsScene, "backLeft", 0.25, "switch");
        animate(mainScene, "right", 0.25, "switch");
        animate(investScene, "backLeft", 0.25, "switch");
        refreshData();
    }
    public void settingsImageClick() {
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
        animate(settingsScene, "backRight", 0.25, "switch");
        animate(mainScene, "backRight", 0.25, "switch");
        animate(investScene, "left", 0.25, "switch");
        refreshData();
    }
    public void closeProgram() {
        String result = ConfirmBox.display("Quit","Are you sure you want to quit?", "close");
        if (result.equals("true") || result.equals("nosave")) {
            animate(settingsScene, "backRight", 1, "close");
            animate(investScene, "backRight", 1, "close");
            animate(mainScene, "backRight", 1, "close");
            animate(tabLayout, "backLeft", 1, "close");
        }
    }
    public void paidImageClick() {
        MoneyStuff.paid(moneyDisplay);
        refreshData();
    }
    public void debitImageClick() {
        float item = Float.parseFloat(debitList.getSelectionModel().getSelectedItem().toString());
        MoneyStuff.subtractMoney(moneyDisplay, item);
        MoneyStuff.setAmountAtPayDay(MoneyStuff.amountAtPayDay - item);
        System.out.println("Debit Paid: " + String.valueOf(item));
    }
    public void wageSliderChange() {
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
        WageStuff.setWageSlider(wageSlider, perMonthLabel, timePeriod);
    }
}