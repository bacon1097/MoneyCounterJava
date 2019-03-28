package sample;

import javafx.scene.control.TableView;

public class DebitStuff {
    public static void addDebit(TableView table, String value) {
        table.getItems().add(value);
        System.out.println("Added direct debit: " + value);
    }
}