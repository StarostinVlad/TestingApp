package com.starostinvlad.professional_1c.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Theme implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "sectionId")
    private long sectionId;
    @Ignore
    private UserProgress userProgress;

    public UserProgress getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(UserProgress userProgress) {
        this.userProgress = userProgress;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("theme: %s, id: %d", title, id);
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
}