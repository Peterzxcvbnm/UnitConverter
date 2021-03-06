package com.example.unitconverter.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Collections;
import java.util.List;

public class DatabaseInitializer implements Initializer<DataParser> {
    @NonNull
    @Override
    public DataParser create(@NonNull Context context) {
        Log.i("DB", "Initializind dataParser");
        DataParser dataParser = new DataParser();
        dataParser.SeedDatabase(context);
        return dataParser;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
