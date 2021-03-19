package com.starostinvlad.professional_1c.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TestAnswers {
    @PrimaryKey(autoGenerate = true)
    int id;
    int countCorrectAnswers;

    public TestAnswers(int countCorrectAnswers) {
        this.countCorrectAnswers = countCorrectAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public void setCountCorrectAnswers(int countCorrectAnswers) {
        this.countCorrectAnswers = countCorrectAnswers;
    }
//    List<QuestionWithAnswers> questionWithAnswersList;
}
