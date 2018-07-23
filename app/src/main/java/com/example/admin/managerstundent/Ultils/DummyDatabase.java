package com.example.admin.managerstundent.Ultils;

import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.NotificationModel;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.Entity.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DummyDatabase {
    public static List<Subject> subjects = Arrays.asList(new Subject[]{
            new Subject(1, "Enlish"),
            new Subject(2, "Japanese"),
            new Subject(3, "Math"),
            new Subject(4, "Physics")});



    private static final ClassDetail[] classDetails = {
            new ClassDetail(1, "English 1")
            , new ClassDetail(2, "English 2")
            , new ClassDetail(3, "English 3")
            , new ClassDetail(4, "English 4")
            , new ClassDetail(5, "English 5")

    };


    private static List<ClassDetail> classDetailsToMakeTimetable = new ArrayList<>();
    private static final SlotRequestedModel[] slotRequestedModel = {

            new SlotRequestedModel(classDetails[0], SlotRequestedModel.WAITING_STATE)
            , new SlotRequestedModel(classDetails[1], SlotRequestedModel.WAITING_STATE)
            , new SlotRequestedModel(classDetails[2], SlotRequestedModel.ACCEPTED_STATE)
            , new SlotRequestedModel(classDetails[3], SlotRequestedModel.DENIED_STATE)

    };
    public static final ArrayList<NotificationModel> notifications = new ArrayList<NotificationModel>();

    public static void initDummyDatabase() {
        notifications.add( new NotificationModel(1, "15 minutes to next class", "You should hurry up"));
        notifications.add(new NotificationModel(2, "15 minutes to next class", "You should hurry up", true));
        notifications.add( new NotificationModel(1, "15 minutes to next class", "You should hurry up"));
        notifications.add(new NotificationModel(2, "15 minutes to next class", "You should hurry up", true));
    }
    private static Student studentProfile = new Student(
            1
            ,"Nguyen Van A"
            , "0905456483"
            , "0905167468"
            , "19/02/1997"
            , true
            , false
    );

    public static List<ClassDetail> getClassList(Subject subject) {
        return Arrays.asList(classDetails);
    }

    public static List<SlotRequestedModel> getSlotRequestedModels() {
        return Arrays.asList(slotRequestedModel);
    }

    public static Student getStudentProfile() {
        return studentProfile;
    }

    public static void setStudentProfile(Student studentProfile) {
        DummyDatabase.studentProfile = studentProfile;
    }

    public static void setClassDetails(List<ClassDetail> classDetails) {
        classDetailsToMakeTimetable = classDetails;
    }

    public static ArrayList<NotificationModel> getNotificationList() {
        return notifications;
    }

    public static void setSubjects(List<Subject> subjects) {
        DummyDatabase.subjects = subjects;
    }

    public static List<Subject> getSubjects() {
        return subjects;
    }

    public static void updateNotification(NotificationModel model) {
        // update by ID of model
        model.setRead(model.isRead());
    }


    public static List<ClassDetail> getClassDetailsToMakeTimetable() {
        return classDetailsToMakeTimetable;
    }

    public static void setClassDetailsToMakeTimetable(List<ClassDetail> classDetailsToMakeTimetable) {
        DummyDatabase.classDetailsToMakeTimetable = classDetailsToMakeTimetable;
    }







}
