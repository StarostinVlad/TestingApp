package com.starostinvlad.professional_1c.Database;


import com.starostinvlad.professional_1c.Models.Theme;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface ThemeDao {



    @Query("SELECT * FROM theme WHERE id = :id")
    Single<Theme> getByThemeId(long id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Theme> themes);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Theme theme);

    @Update
    void update(Theme theme);

    @Delete
    void delete(Theme theme);

    @Query("SELECT * FROM theme ORDER BY title ASC")
    Single<List<Theme>> getThemes();

    @Query("SELECT * FROM theme ORDER BY RANDOM() LIMIT 1")
    Single<Theme> getRandomTheme();

}