package ca.nait.dmit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BMI {

    private double weight;
    private double height;

    public double bmi() {
        return 703 * weight / Math.pow(height,2);
    }

    public String bmiCategory() {
        return "";
    }
}
