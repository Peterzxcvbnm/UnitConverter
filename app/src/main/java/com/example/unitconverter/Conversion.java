package com.example.unitconverter;

import com.example.unitconverter.database.model.Unit;

public class Conversion {
    private double fromValue = 0;
    private double toValue = 0;
    private Unit fromUnit;
    private Unit toUnit;

    public void setFromValue(double fromValue) {
        this.fromValue = fromValue;
        RecalculateValues();
    }

    public double getFromValue() {
        return fromValue;
    }

    public double getToValue() {
        return toValue;
    }

    public Unit getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(Unit fromUnit) {
        this.fromUnit = fromUnit;

        RecalculateValues();
    }

    private void RecalculateValues() {
        if(fromUnit == null || toUnit == null) return;
        toValue = ((fromValue * fromUnit.getConversionMultiplier() + fromUnit.getConversionOffset()) - toUnit.getConversionOffset()) / toUnit.getConversionMultiplier();
    }

    public Unit getToUnit() {
        return toUnit;
    }

    public void setToUnit(Unit toUnit) {
        this.toUnit = toUnit;

        RecalculateValues();
    }
}
