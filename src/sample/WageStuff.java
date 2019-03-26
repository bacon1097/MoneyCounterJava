package sample;

public class WageStuff {
    static float wage;
    static String myPayDay;
    public static void setWage(float input) {
        wage = input;
        System.out.println("New Wage is: " + wage);
    }
    public static float getWage() {
        System.out.println("Wage is: " + wage);
        return wage;
    }
    public static void setPayDay(String date) {
        myPayDay = date;
        System.out.println("Set payday to: " + myPayDay);
    }
    public static String getPayDay() {
        return myPayDay;
    }
}