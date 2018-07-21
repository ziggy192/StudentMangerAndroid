package com.example.admin.managerstundent.Entity;

import java.io.Serializable;

public class SlotRequestedModel implements Serializable {
    private ClassDetail classDetail;
    private String state;


    public SlotRequestedModel(ClassDetail classDetail, String state) {
        this.classDetail = classDetail;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ClassDetail getClassDetail() {
        return classDetail;
    }

    public void setClassDetail(ClassDetail classDetail) {
        this.classDetail = classDetail;
    }

    @Override
    public String toString() {
        return "SlotRequestedModel{" +
                "classDetail=" + classDetail +
                ", state='" + state + '\'' +
                '}';
    }

    public static SlotRequestedModel getDefaultModel() {
        return new SlotRequestedModel(
                new ClassDetail(
                        1
                        , "Enlish"
                )
                , ACCEPTED_STATE
        );
    }

    public static final String WAITING_STATE = "waiting";
    public static final String ACCEPTED_STATE = "accepted";
    public static final String DENIED_STATE = "denied";


}
