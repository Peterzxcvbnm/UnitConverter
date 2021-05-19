package com.example.unitconverter.database.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Unit {
    public String unitName;
    @PrimaryKey
    public String systemName;
    public String description;
    public String symbol;
    public double conversionMultiplier;
    public double conversionOffset;

    public List<String> quantityKinds = new ArrayList<>();
    @Embedded
    public DimensionVector DimensionVector;

    public Unit() {
    }

    public Unit(String unitName, String systemName, String description, String symbol, double conversionMultiplier, double conversionOffset, List<String> quantityKinds, com.example.unitconverter.database.model.DimensionVector dimensionVector) {
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

    public List<String> getQuantityKinds() {
        return quantityKinds;
    }

    public DimensionVector getDimensionVector() {
        return DimensionVector;
    }

    public String getUnitName() {
        return unitName;
    }
}
