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
    public static final Subject[] subjects = {
            new Subject(1, "Enlish"),
            new Subject(2, "Japanese"),
            new Subject(3, "Math"),
            new Subject(4, "Physics")};



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

    public static final NotificationModel[] notifications = {
            new NotificationModel(1, "15 minutes to next class", "You should hurry up")
            , new NotificationModel(2, "15 minutes to next class", "You should hurry up",true)
            , new NotificationModel(3, "15 minutes to next class", "You should hurry up")
            , new NotificationModel(4, "15 minutes to next class", "You should hurry up",true)
    };
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

    public static List<NotificationModel> getNotificationList() {
        return Arrays.asList(notifications);
    }


    public static void updateNotification(NotificationModel model) {
        // update by ID of model
    }



}
