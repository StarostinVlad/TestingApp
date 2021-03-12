package com.starostinvlad.teamof8testingapp;

import com.starostinvlad.teamof8testingapp.Models.Section;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;

@Dao
public interface SectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Section section);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<Section> sections);

    @Query("SELECT * FROM section")
    Observable<List<Section>> getSections();

    @Update
    void update(Section section);

    @Delete
    void delete(Section section);
}
