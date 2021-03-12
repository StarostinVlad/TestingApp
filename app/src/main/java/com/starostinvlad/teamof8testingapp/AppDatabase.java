package com.starostinvlad.teamof8testingapp;

import com.starostinvlad.teamof8testingapp.Models.Answer;
import com.starostinvlad.teamof8testingapp.Models.Question;
import com.starostinvlad.teamof8testingapp.Models.Section;
import com.starostinvlad.teamof8testingapp.Models.TestAnswers;
import com.starostinvlad.teamof8testingapp.Models.Theme;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Section.class, Theme.class, Question.class, Answer.class, TestAnswers.class}, version = 9, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ThemeDao themeDao();

    public abstract SectionDao sectionDao();

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();

    public abstract TestAnswerDao testAnswerDao();
}