package sample;

import javafx.scene.control.ListView;

import java.util.Iterator;

public class DebitStuff {
    private static String debits = "";
    public static void addDebit(ListView list, String input) {
        if (input != null) {
            try {
                list.getItems().addAll(Float.parseFloat(input));
                setDebits(list);
            } catch (NumberFormatException e) {
                System.out.println("*addDebit* could not parse string into float");
            }
        }
    }
    public static String getDebits() {
        System.out.println("Direct Debits: " + debits);
        return debits;
    }
    public static float getDebitsTotal() {
        System.out.println("Direct Debits: " + debits);
        String temp = "";
        float total = 0.00f;
        for (char c : debits.toCharArray()) {
            if (!String.valueOf(c).equals(",")) {
                temp = temp.concat(String.valueOf(c));
            }
            else {
                total += Float.parseFloat(temp);
                temp = "";
            }
        }
        return total;
    }
    public static void deleteDebit(ListView list) {
        list.getItems().remove(list.getSelectionModel().getSelectedIndex());
        setDebits(list);
    }
    private static void setDebits(ListView list) {
        debits = "";
        for (Iterator<String> iteration = list.getItems().listIterator(); iteration.hasNext();) {
            String val = String.valueOf(iteration.next());
            debits = debits.concat(val + ",");
        }
        System.out.println("New Direct Debit List: " + debits);
    }
}