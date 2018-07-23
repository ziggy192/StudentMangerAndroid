package com.example.admin.managerstundent.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassDetail implements Serializable {

    public static final String DEFAULT_ROOM_NAME = "R101";
    @SerializedName("ClassDetailId")
    private int classId;
    @SerializedName("ClassName")
    private String className;

    @SerializedName("SubjectName")
    private String subjectName;

    @SerializedName("TeacherName")
    private String teacherName;

    @SerializedName("RoomName")
    private String roomName;

    @SerializedName("TimeSlotModels")
    List<TimeSlotModel> timeSlotModelList;


    public ClassDetail(int classId, String className, String subjectName, String teacherName, String roomName, List<TimeSlotModel> timeSlotModelList) {
        this.classId = classId;
        this.className = className;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.roomName = roomName;
        this.timeSlotModelList = timeSlotModelList;
    }


    public ClassDetail(int classId, String className) {
        this(classId, className
                , "English"
                , "NghiaLQ"
                , DEFAULT_ROOM_NAME
                , Arrays.asList(
                        new TimeSlotModel("15:00PM - 17:00PM","Monday")
                        ,new TimeSlotModel("15:00PM - 17:00PM","Wednesday")
                        ,new TimeSlotModel("15:00PM - 17:00PM","Friday")
                )
        );
    }

    @Override
    public String toString() {
        return "ClassDetail{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", timeSlotModelList=" + timeSlotModelList +
                '}';
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


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    public static String getDefaultRoomName() {
        return DEFAULT_ROOM_NAME;
    }

    public List<TimeSlotModel> getTimeSlotModelList() {
        return timeSlotModelList;
    }

    public void setTimeSlotModelList(List<TimeSlotModel> timeSlotModelList) {
        this.timeSlotModelList = timeSlotModelList;
    }


}


