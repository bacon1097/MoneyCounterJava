package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DateInfo {
    static String fileName = "C:\\ProgramData\\MoneyCounter\\MoneyData.info";
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
        String myMonth = format.format(calendar.getTime());
        return myMonth;
    }
    public static int getDaysInMonth(String month) {
        if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            return 31;
        }
        else if (month.equals("02")) {
            return 28;
        }
        else{
            return 30;
        }
    }
    public static boolean isDifDay() {
        String string;
        boolean flag = false;
        try {
            string = Files.readAllLines(Paths.get(fileName)).get(1);
            if (string.equals("26-03-2019")) {
                System.out.println("Date is not different");
                flag = false;
            }
            else {
                System.out.println("Date is different");
                flag = true;
            }
        }
        catch (IOException e) {
            System.out.println("*isDifDay* File not found: " + fileName);
        }
        return flag;
    }
    public static int daysSince() {
        StringBuffer paydayString = new StringBuffer();
        StringBuffer date = new StringBuffer();
        String payday = WageStuff.getPayDay();
        for (char c : payday.toCharArray()) {
            if (String.valueOf(c).equals("-") ) {
                break;
            }
            else {
                paydayString.append(c);
            }
        }
        for (char c : DateInfo.getDate().toCharArray()) {
            if (String.valueOf(c).equals("-")) {
                break;
            }
            else {
                date.append(c);
            }
        }
        int days = Integer.parseInt(date.toString()) - Integer.parseInt(paydayString.toString());
        System.out.println("Days since payday: " + days);
        return days;
    }
}