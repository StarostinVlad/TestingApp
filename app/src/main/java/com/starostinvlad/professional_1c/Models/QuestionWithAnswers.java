package com.starostinvlad.professional_1c.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Relation;

public class QuestionWithAnswers implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "themeId")
    private long themeId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;

    @Ignore
    private Integer answerIndex;

    private Integer correctAnswer;
    @SerializedName("answers")
    @Expose
    @Relation(parentColumn = "id", entityColumn = "questionId", entity = Answer.class)
    private List<Answer> answers;

    public void fillCorrectAnswerToQuestion() {
        if (answers
                .stream()
                .anyMatch(Answer::isCorrect))
            correctAnswer =
                    answers
                            .stream()
                            .filter(Answer::isCorrect)
                            .findFirst()
                            .get()
                            .getSortPosition();
    }

    public Integer getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        this.answerIndex = answerIndex;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
