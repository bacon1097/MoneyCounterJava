package sample;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DateInfo {
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");       //Formats the date
        GregorianCalendar calendar = new GregorianCalendar();
        String myDay = format.format(calendar.getTime());
        System.out.println("Date is: " + myDay);
        return myDay;
    }
    public static String getMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        GregorianCalendar calendar = new GregorianCalendar();
        return format.format(calendar.getTime());
    }
    public static int getDaysInMonth(String month) {
        switch (month) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                System.out.println("Days In Month " + month + ": 31");
                return 31;
            case "02":
                System.out.println("Days In Month " + month + ": 28");
                return 28;
            case "04":
            case "06":
            case "09":
            case "11":
                System.out.println("Days In Month " + month + ": 30");
                return 30;
            default:
                System.out.println("*getDaysInMonth* Could not get days in month specified: " + month);
                return 0;
        }
    }
    public static int daysSince() {
        StringBuilder paydayStringDay = new StringBuilder();
        StringBuilder paydayStringMonth = new StringBuilder();
        StringBuilder dateDay = new StringBuilder();
        StringBuilder dateMonth = new StringBuilder();
        String payday = WageStuff.getPayDay();
        String dateNow = DateInfo.getDate();
        /*
        Reading in the payday date from the file character by character and only obtaining the days
        and the months. Do the same for the current date.
        */
        for (int i = 0; i < payday.toCharArray().length; i++) {
            switch (i) {
                case 0:
                case 1:
                    paydayStringDay.append(payday.toCharArray()[i]);
                    break;
                case 3:
                case 4:
                    paydayStringMonth.append(payday.toCharArray()[i]);
                    break;
            }
        }
        for (int i = 0; i < dateNow.toCharArray().length; i++) {
            switch (i) {
                case 0:
                case 1:
                    dateDay.append(dateNow.toCharArray()[i]);
                    break;
                case 3:
                case 4:
                    dateMonth.append(dateNow.toCharArray()[i]);
                    break;
            }
        }
        int result;
        if (Integer.parseInt(paydayStringMonth.toString()) != Integer.parseInt(dateMonth.toString())) {
            result = (Integer.parseInt(dateDay.toString()) - Integer.parseInt(paydayStringDay.toString())) + getDaysInMonth(paydayStringMonth.toString());
        }
        else {
            result = (Integer.parseInt(dateDay.toString()) - Integer.parseInt(paydayStringDay.toString()));
        }
        if (result >= getDaysInMonth(paydayStringMonth.toString())) {
            result = getDaysInMonth(paydayStringMonth.toString()) - 1;
        }
        System.out.println("Days since payday: " + result);
        return result;
    }
}