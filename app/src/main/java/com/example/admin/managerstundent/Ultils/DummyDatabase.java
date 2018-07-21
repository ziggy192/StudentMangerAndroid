package com.example.admin.managerstundent.Ultils;

import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
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

    private static final SlotRequestedModel[] slotRequestedModel = {

            new SlotRequestedModel(classDetails[0], SlotRequestedModel.WAITING_STATE)
            , new SlotRequestedModel(classDetails[1], SlotRequestedModel.WAITING_STATE)
            , new SlotRequestedModel(classDetails[2], SlotRequestedModel.ACCEPTED_STATE)
            , new SlotRequestedModel(classDetails[3], SlotRequestedModel.DENIED_STATE)

    };
    public static List<ClassDetail> getClassList(Subject subject) {
        return Arrays.asList(classDetails);
    }

    public static List<SlotRequestedModel> getSlotRequestedModels() {
        return Arrays.asList(slotRequestedModel);
    }

}
