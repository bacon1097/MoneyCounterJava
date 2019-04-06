package sample;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DateInfo {
    static String fileName = "./MoneyData.info";
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
        if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            System.out.println("Days In Month " + month + ": 31");
            return 31;
        }
        else if (month.equals("02")) {
            System.out.println("Days In Month " + month + ": 28");
            return 28;
        }
        else if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")){
            System.out.println("Days In Month " + month + ": 30");
            return 30;
        }
        else{
            System.out.println("*getDaysInMonth* Could not get days in month specified: " + month);
            return 0;
        }
    }
    //In progress
//    public static boolean isDifDay() {
//        String string;
//        boolean flag = false;
//        try {
//            string = Files.readAllLines(Paths.get(fileName)).get(1);
//            if (string.equals(getDate())) {
//                System.out.println("Date is not different");
//                flag = false;
//            }
//            else {
//                System.out.println("Date is different");
//                flag = true;
//            }
//        }
//        catch (IOException e) {
//            System.out.println("*isDifDay* File not found: " + fileName);
//        }
//        return flag;
//    }
    public static int daysSince() {
        StringBuffer paydayStringDay = new StringBuffer();
        StringBuffer paydayStringMonth = new StringBuffer();
        StringBuffer dateDay = new StringBuffer();
        StringBuffer dateMonth = new StringBuffer();
        String payday = WageStuff.getPayDay();
        String dateNow = DateInfo.getDate();
        for (int i = 0; i < payday.toCharArray().length; i++) {
            if (i == 0 || i == 1) {
                paydayStringDay.append(payday.toCharArray()[i]);
            }
            else if (i == 3 || i == 4) {
                paydayStringMonth.append(payday.toCharArray()[i]);
            }
            else {
                continue;
            }
        }
        for (int i = 0; i < dateNow.toCharArray().length; i++) {
            if (i == 0 || i == 1) {
                dateDay.append(dateNow.toCharArray()[i]);
            } else if (i == 3 || i == 4) {
                dateMonth.append(dateNow.toCharArray()[i]);
            } else {
                continue;
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