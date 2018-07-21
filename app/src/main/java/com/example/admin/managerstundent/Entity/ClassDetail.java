package com.example.admin.managerstundent.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassDetail implements Serializable {


    private int classId;
    private String className;
    private String subjectName;
    private String teacherName;
    private String time;
    private List<String> daysOfWeek;

    public ClassDetail(int classId, String className, String subjectName, String teacherName, String time, List<String> daysOfWeek) {
        this.classId = classId;
        this.className = className;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.time = time;
        this.daysOfWeek = daysOfWeek;
    }

    public ClassDetail(int classId, String className) {
        this(classId, className, "English", "NghiaLQ", "15:00PM - 17:00PM"
                , Arrays.asList(
                        new String[]{
                                "Monday"
                                , "Wednesday"
                                , "Friday"
                        }
                )
        );
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getDaysOfWeekString() {
        String result ="";
        for (int i = 0; i < daysOfWeek.size(); i++) {
            String dayOfWeek = daysOfWeek.get(i);
            result += dayOfWeek;
            if (i < daysOfWeek.size() - 1) {
                result += ", ";

            }
        }
        return result;
    }

    public void setDaysOfWeek(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void addDayOfWeek(String dayOfWeek) {
        this.daysOfWeek.add(dayOfWeek);
    }
}


