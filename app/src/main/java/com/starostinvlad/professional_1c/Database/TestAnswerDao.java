package com.starostinvlad.professional_1c.Database;

import com.starostinvlad.professional_1c.Models.TestAnswers;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface TestAnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TestAnswers answer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<TestAnswers> answers);

    @Query("SELECT * FROM testanswers")
    Single<List<TestAnswers>> getAnswers();

    @Update
    void update(TestAnswers answer);

    @Delete
    void delete(TestAnswers answer);

    @Query("DELETE FROM testanswers")
    void deleteAll();
}
