package com.starostinvlad.professional_1c.Models;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question implements Serializable {
    @PrimaryKey
    private Integer id;
    @ColumnInfo(name = "themeId")
    private long themeId;
    private String title;
    private String image;
    private Integer lastAnswerIndex;
    private Integer correctAnswer;

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getLastAnswerIndex() {
        return lastAnswerIndex;
    }

    public void setLastAnswerIndex(Integer position) {
        this.lastAnswerIndex = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
