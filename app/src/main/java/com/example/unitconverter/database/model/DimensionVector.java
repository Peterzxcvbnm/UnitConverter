package com.example.unitconverter.database.model;

import android.util.Log;

public class DimensionVector {

    private short amountOfSubstance;
    private short electricCurrent;
    private short length;
    private short luminousIntensity;
    private short mass;
    private short temperature;
    private short time;
    private short dimensionless;

    public DimensionVector() {
    }

    public DimensionVector(short amountOfSubstance, short electricCurrent, short length, short luminousIntensity, short mass, short temperature, short time, short dimensionless) {
        this.amountOfSubstance = amountOfSubstance;
        this.electricCurrent = electricCurrent;
        this.length = length;
        this.luminousIntensity = luminousIntensity;
        this.mass = mass;
        this.temperature = temperature;
        this.time = time;
        this.dimensionless = dimensionless;
    }

    public short getAmountOfSubstance() {
        return amountOfSubstance;
    }

    public short getElectricCurrent() {
        return electricCurrent;
    }

    public short getLength() {
        return length;
    }

    public short getLuminousIntensity() {
        return luminousIntensity;
    }

    public short getMass() {
        return mass;
    }

    public short getTemperature() {
        return temperature;
    }

    public short getTime() {
        return time;
    }

    public short getDimensionless() {
        return dimensionless;
    }

    public void setAmountOfSubstance(short amountOfSubstance) {
        this.amountOfSubstance = amountOfSubstance;
    }

    public void setElectricCurrent(short electricCurrent) {
        this.electricCurrent = electricCurrent;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public void setLuminousIntensity(short luminousIntensity) {
        this.luminousIntensity = luminousIntensity;
    }

    public void setMass(short mass) {
        this.mass = mass;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public void setTime(short time) {
        this.time = time;
    }

    public void setDimensionless(short dimensionless) {
        this.dimensionless = dimensionless;
    }

    public static DimensionVector parse(String s)
    {
        DimensionVector v = new DimensionVector();
        int indexOfE = s.indexOf("E");
        int indexOfL = s.indexOf("L");
        int indexOfI = s.indexOf("I");
        int indexOfM = s.indexOf("M");
        int indexOfH = s.indexOf("H");
        int indexOfT = s.indexOf("T");
        int indexOfD = s.indexOf("D");

        v.amountOfSubstance = Short.parseShort(s.substring(1, indexOfE));
        v.electricCurrent = Short.parseShort(s.substring(indexOfE + 1, indexOfL));
        v.length = Short.parseShort(s.substring(indexOfL + 1, indexOfI));
        v.luminousIntensity = Short.parseShort(s.substring(indexOfI + 1, indexOfM));
        v.mass = Short.parseShort(s.substring(indexOfM + 1, indexOfH));
        v.temperature = Short.parseShort(s.substring(indexOfH + 1, indexOfT));
        v.time = Short.parseShort(s.substring(indexOfT + 1, indexOfD));
        v.dimensionless = Short.parseShort(s.substring(indexOfD + 1));

        return v;
    }
}
