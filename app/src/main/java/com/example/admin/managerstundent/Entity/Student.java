package com.example.admin.managerstundent.Entity;

import android.widget.TextView;

import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.R;

import java.io.Serializable;

import static com.example.admin.managerstundent.Constant.Constant.PARENT_PHONE_NUMBER_KEY;
import static com.example.admin.managerstundent.Constant.Constant.PHONE_NUMBER_KEY;

public class Student implements Serializable{

    private String name;
    private String phoneNumber;
    private String parentsPhoneNumber;
    private String dateOfBirth;
    private boolean isMale;
    private boolean isPaid;

    public Student(String name, String phoneNumber, String parentsPhoneNumber, String dateOfBirth, boolean isMale, boolean isPaid) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.parentsPhoneNumber = parentsPhoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.isMale = isMale;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", parentsPhoneNumber='" + parentsPhoneNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", isMale=" + isMale +
                ", isPaid=" + isPaid +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getParentsPhoneNumber() {
        return parentsPhoneNumber;
    }

    public void setParentsPhoneNumber(String parentsPhoneNumber) {
        this.parentsPhoneNumber = parentsPhoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
