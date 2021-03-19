package com.starostinvlad.professional_1c.Database;

import com.starostinvlad.professional_1c.Models.Answer;
import com.starostinvlad.professional_1c.Models.Question;
import com.starostinvlad.professional_1c.Models.Section;
import com.starostinvlad.professional_1c.Models.TestAnswers;
import com.starostinvlad.professional_1c.Models.Theme;

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