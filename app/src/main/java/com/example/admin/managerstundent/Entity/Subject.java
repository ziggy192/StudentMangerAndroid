package com.example.admin.managerstundent.Entity;

import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("SubjectId")
    int subjectId;
    @SerializedName("SubjectName")
    String subjectName;

    public Subject(int subjectId,String subjectName) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
