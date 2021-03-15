package com.starostinvlad.teamof8testingapp;

import com.starostinvlad.teamof8testingapp.Models.Question;
import com.starostinvlad.teamof8testingapp.Models.QuestionWithAnswers;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface QuestionDao {


    @Transaction
    @Query("SELECT * FROM question WHERE themeId = :id")
    Single<List<QuestionWithAnswers>> getQuestionsByThemeId(long id);

    @Transaction
    @Query("SELECT * FROM question WHERE themeId = :id ORDER BY RANDOM() LIMIT 14")
    Single<List<QuestionWithAnswers>> getRandomQuestionsByThemeId(long id);

    @Transaction
    @Query("SELECT * FROM question ORDER BY RANDOM() LIMIT 14")
    Single<List<QuestionWithAnswers>> getRandomQuestions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<Question> questions);

    @Query("SELECT * FROM question")
    Single<List<Question>> getQuestions();

    @Update
    void update(Question question);

    @Delete
    void delete(Question question);

    @Transaction
    @Query("SELECT " +
            "COUNT(CASE WHEN lastAnswerIndex=correctAnswer THEN 1 END) AS correctCount," +
            "COUNT(*) AS totalCount" +
            " FROM question")
    Single<Counter> getCorrectAndTotalQuestionsCount();

    @Transaction
    @Query("SELECT " +
            "COUNT(CASE WHEN lastAnswerIndex=correctAnswer THEN 1 END) AS correctCount," +
            "COUNT(*) AS totalCount" +
            " FROM question WHERE themeId=:themeId")
    Single<Counter> getCorrectAndTotalQuestionsCountByThemeId(int themeId);

    @Query("DELETE FROM question")
    void deleteAll();
}
