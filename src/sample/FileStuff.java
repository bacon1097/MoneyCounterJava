package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileStuff {
    private static String directory = System.getProperty("user.home") + "\\AppData\\Local\\MoneyCounter";
    static String fileName = directory + "\\MoneyCounter.info";
    public static void saveInfo() {
        System.out.println("Saving info");
        if (!fileExists(directory)) {
            File dir = new File(directory);
            dir.mkdirs();
        }
        if (!fileExists(fileName)) {
            try {
                File file = new File(fileName);
                file.createNewFile();
            }
            catch (IOException e){
                System.out.println("*saveInfo* Could not create file");
            }
        }
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName, false));
            pw.println(MoneyStuff.getValue());
            pw.println(DateInfo.getDate());
            pw.println(WageStuff.getPayDay());
            pw.println(WageStuff.getWage());
            pw.println(DebitStuff.getDebits());
            pw.println(MoneyStuff.getAmountAtPayDay());
            pw.println(WageStuff.getWageSlider());
            pw.close();
            System.out.println("Saved Data");
        }
        catch (IOException e) {
            System.out.println("*saveInfo* File not found: " + fileName);
        }
    }
    public static boolean fileExists(String name) {
        File file = new File(name);
        return file.exists();
    }
    public static String getInfo(int line) {
        String string = "";
        try {
            string = Files.readAllLines(Paths.get(fileName)).get(line);
        }
        catch (IOException e) {
            System.out.println("*getInfo* Cant get line from file");
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("*getInfo* Index out of bounds");
        }
        return string;
    }
}