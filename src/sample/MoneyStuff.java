package sample;

import javafx.scene.control.Label;

public class MoneyStuff {
    static float amountAtPayDay;
    public static void setValue(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(String.valueOf(moneyInput));
    }

    public static String getValue() {
        Label moneyDisplay = (Label)Main.window.getScene().lookup("#moneyDisplay");
        System.out.println("Money is: " + moneyDisplay.getText());
        return moneyDisplay.getText();
    }

    public static void addMoney(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(String.format("%.2f", Math.floor((Float.parseFloat(moneyDisplay.getText()) + moneyInput) * 100) / 100));
        System.out.println("New Value is: " + moneyDisplay.getText());
    }

    public static void subtractMoney(Label moneyDisplay, float moneyInput) {
        moneyDisplay.setText(String.format("%.2f", Math.floor((Float.parseFloat(moneyDisplay.getText()) - moneyInput) * 100) / 100));
        System.out.println("New Value is: " + moneyDisplay.getText());
    }
    public static boolean validateInput(String value) {
        boolean flag;
        if (value != null) {
            try {
                Float.parseFloat(value);
                flag = true;
            } catch (NumberFormatException e) {
                flag = false;
                System.out.println("*validateInput* Input is not a valid float");
            }
        }
        else {
            flag = false;
            System.out.println("*validateInput* Nothing Inputted");
        }
        return flag;
    }
    public static void paid(Label moneyDisplay) {
        moneyDisplay.setText(String.format("%.2f" , Float.parseFloat(moneyDisplay.getText()) + WageStuff.getWage()));
        amountAtPayDay = Float.parseFloat(moneyDisplay.getText());
        System.out.println("Been paid: " + String.format("%.2f", WageStuff.getWage()));
        WageStuff.setPayDay(DateInfo.getDate());
    }
    public static void setAmountAtPayDay(float value) {
        amountAtPayDay = Float.parseFloat(String.valueOf(Math.floor(value * 100) / 100));
    }
    public static float getAmountAtPayDay() {
        return (amountAtPayDay);
    }
    /*
    Main savings calculations
     */
    public static void setCalculateSavings(Label label, String value) {
        float dailySpending = WageStuff.getDailySpending();
        int daysSince = DateInfo.daysSince();
        float currentAmount = Float.parseFloat(value);
        float money = (dailySpending * (daysSince + 1)) - (amountAtPayDay - currentAmount);
        label.setText(String.format("%.2f", Math.floor(money * 100) / 100));
    }
    public static void setDailySpending(Label dailySpendingLabel) {
        System.out.println("Daily Spending Value is: " + String.format("%.2f", Math.floor(WageStuff.getDailySpending() * 100) / 100));
        dailySpendingLabel.setText(String.format("%.2f", Math.floor(WageStuff.getDailySpending() * 100) / 100));
    }
}