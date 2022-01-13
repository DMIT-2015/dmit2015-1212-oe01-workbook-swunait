package ca.nait.dmit.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BMITest {

    @Test
    void bmiShouldBeUnderweight() {
        BMI bmi1 = new BMI();
        bmi1.setWeight(100);
        bmi1.setHeight(66);
        assertEquals(16.1, bmi1.bmi(), 0.05);
        assertEquals("underweight", bmi1.bmiCategory());
    }

    @Test
    void bmiCategory() {
    }
}