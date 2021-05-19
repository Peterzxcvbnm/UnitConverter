package com.example.unitconverter.database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.unitconverter.database.model.Unit;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UnitDao {
    @Query("SELECT * FROM unit")
    Single<List<Unit>> getAll();

    @Query("SELECT count(*) FROM unit")
    int unitCount();

    @Query("SELECT * FROM unit WHERE systemName = :systemName")
    Single<Unit> get(String systemName);

    @Query("SELECT quantityKinds FROM unit")
    Single<List<String>> getQuantityKinds();

    @Insert
    void insertAll(Unit... units);
}
