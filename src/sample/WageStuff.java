package sample;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import oracle.jrockit.jfr.StringConstantPool;

import java.util.Date;

public class WageStuff {
    static float wage;
    static String myPayDay;
    static String wagePeriod;
    public static void setWage(float input, Label label) {
        wage = Float.parseFloat(String.valueOf(Math.floor(input * 100) /100));
        label.setText(String.valueOf(wage));
        System.out.println("New Wage is: " + wage);
    }
    public static float getWage() {
        System.out.println("Wage is: " + wage);
        return Float.parseFloat(String.valueOf(Math.floor(wage * 100) / 100));
    }
    public static void setPayDay(String date) {
        myPayDay = date;
        System.out.println("Set payday to: " + myPayDay);
    }
    public static String getPayDay() {
        return myPayDay;
    }
    public static String getWageSlider() {
        System.out.println("Wage period is: " + wagePeriod);
        return wagePeriod;
    }
    public static void setWageSlider(Slider slider, Label monthly, String val) {
        if (val.equals("Monthly") || val.equals("Weekly")) {
            wagePeriod = val;
            if (val.equals("Monthly")) {
                slider.setValue(1);
                monthly.setText("(Monthly)");
            }
            else if (val.equals("Weekly")) {
                slider.setValue(0);
                monthly.setText("(Weekly)");
            }
            System.out.println("Setting wage period to: " + val);
        }
        else {
            System.out.println("*setWageSlider* Invalid wage slider option");
        }
    }
    public static float getDailySpending() {
        String payDayDay = "";
        String payDayMonth = "";
        String payday = getPayDay();
        for (int i = 0; i < payday.toCharArray().length; i++) {
            if (i == 0 || i == 1) {
                payDayDay = payDayDay.concat(String.valueOf(getPayDay().toCharArray()[i]));
            }
            else if (i == 3 || i == 4){
                payDayMonth = payDayMonth.concat(String.valueOf(getPayDay().toCharArray()[i]));
            }
        }
        String currentDay = "";
        String currentMonth = "";
        String date = DateInfo.getDate();
        for (int i = 0; i < date.toCharArray().length; i++) {
            if (i == 0 || i == 1) {
                currentDay = currentDay.concat(String.valueOf(getPayDay().toCharArray()[i]));
            }
            else if (i == 3 || i == 4){
                currentMonth = currentMonth.concat(String.valueOf(getPayDay().toCharArray()[i]));
            }
        }
        System.out.println(payDayMonth);
        if (wagePeriod.equals("Monthly")) {
            if (Integer.parseInt(currentMonth) > Integer.parseInt(payDayMonth)) {
                return (getWage() - DebitStuff.getDebitsTotal()) / DateInfo.getDaysInMonth("0" + String.valueOf(Integer.parseInt(DateInfo.getMonth()) - 1));
            } else {
                return (getWage() - DebitStuff.getDebitsTotal()) / DateInfo.getDaysInMonth(DateInfo.getMonth());
            }
        }
        else if (wagePeriod.equals("Weekly")) {
            return (getWage() / 7) - ((DebitStuff.getDebitsTotal() / DateInfo.getDaysInMonth(DateInfo.getMonth())) * 7);
        }
        else {
            System.out.println("*getDailySpending* Error finding wage period");
            return 0.00f;
        }
    }
}