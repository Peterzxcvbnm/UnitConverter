package com.example.unitconverter.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.unitconverter.database.DAOs.UnitDao;
import com.example.unitconverter.database.model.Unit;

@Database(entities = {Unit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnitDao unitDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "database-name").build();
        }
        return instance;
    }
}
