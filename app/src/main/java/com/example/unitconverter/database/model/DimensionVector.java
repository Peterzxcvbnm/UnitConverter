package com.example.unitconverter.database.model;

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

        v.amountOfSubstance = Short.parseShort(s.substring(1, indexOfE - 1));
        v.electricCurrent = Short.parseShort(s.substring(indexOfE + 1, indexOfL - indexOfE - 1));
        v.length = Short.parseShort(s.substring(indexOfL + 1, indexOfI - indexOfL - 1));
        v.luminousIntensity = Short.parseShort(s.substring(indexOfI + 1, indexOfM - indexOfI - 1));
        v.mass = Short.parseShort(s.substring(indexOfM + 1, indexOfH - indexOfM - 1));
        v.temperature = Short.parseShort(s.substring(indexOfH + 1, indexOfT - indexOfH - 1));
        v.time = Short.parseShort(s.substring(indexOfT + 1, indexOfD - indexOfT - 1));
        v.dimensionless = Short.parseShort(s.substring(indexOfD + 1));

        return v;
    }
}
