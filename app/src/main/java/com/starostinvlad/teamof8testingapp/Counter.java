package com.starostinvlad.teamof8testingapp;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class Counter {
    private long totalCount;
    private long correctCount;
    @Ignore
    private int answersPerExam;

    public long getTotalCount() {
        return totalCount;
    }

    void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCorrectCount() {
        return correctCount;
    }

    void setCorrectCount(long correctCount) {
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
