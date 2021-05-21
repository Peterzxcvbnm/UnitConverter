package com.example.unitconverter.database.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Unit {
    private String unitName;
    @NonNull
    @PrimaryKey
    private String systemName;
    private String description;
    private String symbol;
    private double conversionMultiplier;
    private double conversionOffset;

    private String quantityKinds;
    @Embedded
    private DimensionVector DimensionVector;

    public Unit() {
    }

    public Unit(String unitName, String systemName, String description, String symbol, double conversionMultiplier, double conversionOffset, String quantityKinds, com.example.unitconverter.database.model.DimensionVector dimensionVector) {
        this.unitName = unitName;
        this.systemName = systemName;
        this.description = description;
        this.symbol = symbol;
        this.conversionMultiplier = conversionMultiplier;
        this.conversionOffset = conversionOffset;
        this.quantityKinds = quantityKinds;
        DimensionVector = dimensionVector;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getConversionMultiplier() {
        return conversionMultiplier;
    }

    public double getConversionOffset() {
        return conversionOffset;
    }

    public String getQuantityKinds() {
        return quantityKinds;
    }

    public List<String> getSplitQuantityKinds() {

        return quantityKinds == null ? new ArrayList<>() : Arrays.asList(quantityKinds.split(","));
    }

    public DimensionVector getDimensionVector() {
        return DimensionVector;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setConversionMultiplier(double conversionMultiplier) {
        this.conversionMultiplier = conversionMultiplier;
    }

    public void setConversionOffset(double conversionOffset) {
        this.conversionOffset = conversionOffset;
    }

    public void setQuantityKinds(String quantityKinds) {
        this.quantityKinds = quantityKinds;
    }

    public void setDimensionVector(com.example.unitconverter.database.model.DimensionVector dimensionVector) {
        DimensionVector = dimensionVector;
    }

    @Override
    public String toString() {
        return unitName;
    }
}
