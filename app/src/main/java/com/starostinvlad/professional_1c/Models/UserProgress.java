package com.starostinvlad.professional_1c.Models;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class UserProgress implements Serializable {
    private long totalCount;
    private long correctCount;
    @Ignore
    private int answersPerExam;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(long correctCount) {
        this.correctCount = correctCount;
    }

    @NonNull
    @Override
    public String toString() {
        return correctCount + "/" + totalCount;
    }

    public int getAnswersPerExam() {
        return answersPerExam;
    }

    public void setAnswersPerExam(int answersPerExam) {
        this.answersPerExam = answersPerExam;
    }
}
