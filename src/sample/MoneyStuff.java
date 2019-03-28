package sample;

import javafx.scene.control.Label;

import java.awt.font.NumericShaper;

import static java.lang.Math.floor;

public class MoneyStuff {
    public static void setValue(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(String.valueOf(moneyInput));
    }

    public static String getValue() {
        Label moneyDisplay = (Label)Main.window.getScene().lookup("#moneyDisplay");
        System.out.println("Money is: " + moneyDisplay.getText());
        return moneyDisplay.getText();
    }

    public static void addMoney(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(plus(Float.parseFloat(moneyDisplay.getText()), moneyInput));
        System.out.println("New Value is: " + moneyDisplay.getText());
    }

    public static void subtractMoney(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(minus(Float.parseFloat(moneyDisplay.getText()), moneyInput));
        System.out.println("New Value is: " + moneyDisplay.getText());
    }

    public static boolean validateInput(String value) {
        boolean flag;
        try {
            Float.parseFloat(value);
            flag = true;
        }
        catch (NumberFormatException e){
            flag = false;
            System.out.println("*validateInput* Input is not a valid float");
        }
        if (value.equals(null)) {
            flag = false;
            System.out.println("*validateInput* Nothing Inputted");
        }
        return flag;
    }
    public static void paid(Label moneyDisplay) {
        moneyDisplay.setText(String.format("%.02f" , Float.parseFloat(moneyDisplay.getText()) + WageStuff.getWage()));
        System.out.println("Been paid: " + String.format("%.02f", WageStuff.getWage()));
        WageStuff.setPayDay(DateInfo.getDate());
    }
    public static String minus(float val1, float val2) {
        return String.format("%.02f", val1 - val2);
    }
    public static String plus(float val1, float val2) {
        return String.format("%.02f", val1 + val2);
    }
    public static float calculateSavings() {
        float money = 0.00f;
        try {
            money = Float.parseFloat(getValue()) - Float.parseFloat(FileStuff.getInfo(3));      //Money owned takeaway wage
        }
        catch (NumberFormatException e) {
            System.out.println("*calculateSavings* Number format exception");
        }
        float shouldSpend = calculateShouldSpend();
        return shouldSpend - money;
    }
    private static float calculateShouldSpend() {
        float shouldSpend = Float.parseFloat(String.valueOf(floor(WageStuff.getDailySpending() * DateInfo.daysSince() * 100) /100));
        return shouldSpend;
    }
    public static void setDailySpending(Label dailySpendingLabel) {
        dailySpendingLabel.setText(String.format("%.02f", WageStuff.getDailySpending()));
        System.out.println("Daily Spending Value is: " + String.format("%.02f", WageStuff.getDailySpending()));
    }
}