package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyStuffTest {
    private MoneyStuff val = new MoneyStuff();
    @Test
    void getAmountAtPayDay() {
        val.setAmountAtPayDay(Float.parseFloat("999.99"));
        assertTrue(String.valueOf(val.getAmountAtPayDay()).matches("\\d+\\.\\d\\d"));
    }

    @Test
    void validateInput() {
        assertTrue(val.validateInput("999.99"));
    }
}