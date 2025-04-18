package kr.ac.kopo.bmi_calculator;

public enum BmiCategory {
    UNDERWEIGHT(0f, 18.5f),
    NORMAL(18.5f, 23f),
    OVERWEIGHT(23f, 25f),
    OBESE(25f, 30f),
    SEVERELYOBESE(30f, Float.MAX_VALUE);

    private final double min;
    private final double max;

    BmiCategory(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(double bmi) {
        return bmi >= min && bmi < max;
    }

    public static BmiCategory fromBmi(double bmi) {
        for (BmiCategory category : values()) {
            if (category.contains(bmi)) {
                return category;
            }
        }
        return null; // or throw exception
    }
}
