package com.example.unitconverter.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.unitconverter.database.DAOs.UnitDao;
import com.example.unitconverter.database.model.Unit;

@Database(entities = {Unit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnitDao unitDao();

}
