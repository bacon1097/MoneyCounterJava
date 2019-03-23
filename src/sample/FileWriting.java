package sample;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriting {
    static String fileName = "C:\\ProgramData\\MoneyCounter\\MoneyData.info";
    static boolean append = false;

    public static void saveInfo() {
        try {
            FileWriter fr = new FileWriter(fileName, append);
            PrintWriter pw = new PrintWriter(fr);
            pw.println("Hello");
            pw.close();
            System.out.println("Saved Data");
        }
        catch (IOException e) {
            System.out.println("File not found: " + fileName);
        }
    }
}
