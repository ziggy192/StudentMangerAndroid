package com.example.admin.managerstundent.Entity;

public class Subject {

    int subjectId;
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
