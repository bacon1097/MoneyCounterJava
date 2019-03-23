package sample;

import javafx.util.StringConverter;

import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.*;
import java.util.Objects;
import java.util.logging.SimpleFormatter;

public class DateInfo {
    static String fileName = "C:\\ProgramData\\MoneyCounter\\MoneyData.info";
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");       //Formats the date
        GregorianCalendar calendar = new GregorianCalendar();
        String myDay = format.format(calendar.getTime());
        System.out.println("Date is: " + myDay);
        return myDay;
    }

    public static boolean isDifDay() {
        boolean flag = false;
        String date = "23";

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            try {
                System.out.println(br.readLine());
                System.out.println(date);
                String fileDate = String.valueOf(br.lines());
                System.out.println(fileDate);
            }
            catch (IOException e) {
                System.out.println("No text in file");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

        if (flag) {
            System.out.println("Date is different");
        }
        else {
            System.out.println("Date is not different");
        }

        return flag;
    }

    public static void closing() {      //Saving date into a file
        String date = getDate();
        try {
            FileWriter fr = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fr);
            pw.println(date);
            System.out.println("Date saved");
            pw.close();
        }
        catch (IOException e) {
            System.out.println("File not found: " + fileName);
        }

    }
}