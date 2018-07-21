package com.example.admin.managerstundent.Ultils;

import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.Subject;

import java.util.Arrays;
import java.util.List;

public class DummyDatabase {
    public static final Subject[] subjects = {
            new Subject(1,"Enlish"),
            new Subject(2,"Japanese"),
            new Subject(3,"Math"),
            new Subject(4,"Physics")};

    private static final ClassDetail[] classDetails = {
            new ClassDetail(1, "English 1")
            , new ClassDetail(2, "English 2")
            , new ClassDetail(3, "English 3")
            , new ClassDetail(4, "English 4")
            , new ClassDetail(5, "English 5")

    };

    public static List<ClassDetail> getClassList(Subject subject) {
        return Arrays.asList(classDetails);
    }

}
