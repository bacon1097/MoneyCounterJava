package sample;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

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
    public static void setWageSlider(Slider slider, String val) {
        if (val.equals("Monthly") || val.equals("Weekly")) {
            wagePeriod = val;
            if (val.equals("Monthly")) {
                slider.setValue(1);
            }
            else if (val.equals("Weekly")) {
                slider.setValue(0);
            }
            System.out.println("Setting wage period to: " + val);
        }
        else {
            System.out.println("*setWageSlider* Invalid wage slider option");
        }
    }
    public static float getDailySpending() {
        boolean flag = false;
        String payDayDay = "";
        for (char c : getPayDay().toCharArray()) {
            if (!String.valueOf(c).equals("-")) {
                payDayDay = payDayDay.concat(String.valueOf(c));
            }
            else {
                break;
            }
        }
        String currentDay = "";
        for (char c : DateInfo.getDate().toCharArray()) {
            if (!String.valueOf(c).equals("-")) {
                currentDay = currentDay.concat(String.valueOf(c));
            }
            else {
                break;
            }
        }
        if (Integer.parseInt(currentDay) < Integer.parseInt(payDayDay)) {
            flag = true;
        }
        if (flag) {
            return (getWage() - DebitStuff.getDebitsTotal()) / DateInfo.getDaysInMonth("0" + String.valueOf(Integer.parseInt(DateInfo.getMonth()) - 1));
        }
        else {
            return (getWage() - DebitStuff.getDebitsTotal()) / DateInfo.getDaysInMonth(DateInfo.getMonth());
        }
    }
}