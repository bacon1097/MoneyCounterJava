package sample;

import javafx.scene.control.Label;
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
        moneyDisplay.setText(String.format("%.2f", String.valueOf(Float.parseFloat(moneyDisplay.getText()) + moneyInput)));
        System.out.println("New Value is: " + moneyDisplay.getText());
    }

    public static void subtractMoney(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(String.format("%.2f", String.valueOf(Float.parseFloat(moneyDisplay.getText()) - moneyInput)));
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
        moneyDisplay.setText(String.format("%.2f" , Float.parseFloat(moneyDisplay.getText()) + WageStuff.getWage()));
        System.out.println("Been paid: " + String.format("%.2f", WageStuff.getWage()));
        WageStuff.setPayDay(DateInfo.getDate());
    }
    //In progress
    public static float calculateSavings(String value) {
        float money = 0.00f;
        try {
            money = (float)(Math.floor((Float.parseFloat(value) - DebitStuff.getDebitsTotal() - WageStuff.getWage()) * 100) / 100);      //Money owned takeaway wage
        }
        catch (NumberFormatException e) {
            System.out.println("*calculateSavings* Number format exception");
        }
        return calculateShouldSpend() - money;
    }
    private static float calculateShouldSpend() {
        float shouldSpend = Float.parseFloat(String.valueOf(floor(WageStuff.getDailySpending() * DateInfo.daysSince() * 100) /100));
        return shouldSpend;
    }
    public static void setDailySpending(Label dailySpendingLabel) {
        dailySpendingLabel.setText(String.format("%.2f", WageStuff.getDailySpending()));
        System.out.println("Daily Spending Value is: " + String.format("%.2f", WageStuff.getDailySpending()));
    }
}