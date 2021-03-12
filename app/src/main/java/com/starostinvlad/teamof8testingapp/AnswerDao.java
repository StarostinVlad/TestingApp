package com.starostinvlad.teamof8testingapp;

import com.starostinvlad.teamof8testingapp.Models.Answer;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;

@Dao
public interface AnswerDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Answer answer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<Answer> answers);

    @Query("SELECT * FROM answer")
    Observable<List<Answer>> getAnswers();

    @Update
    void update(Answer answer);

    @Delete
    void delete(Answer answer);
}
