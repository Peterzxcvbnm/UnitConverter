package com.example.unitconverter.database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.unitconverter.database.model.Unit;

import java.util.List;

@Dao
public interface UnitDao {
    @Query("SELECT * FROM unit")
    List<Unit> getAll();

    @Query("SELECT count(*) FROM unit")
    int unitCount();

    @Query("SELECT * FROM unit WHERE systemName = :systemName")
    Unit get(String systemName);

    @Insert
    void insertAll(Unit... units);
}
