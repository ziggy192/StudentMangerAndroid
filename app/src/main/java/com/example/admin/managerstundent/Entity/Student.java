package com.example.admin.managerstundent.Entity;

import android.widget.TextView;

import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.example.admin.managerstundent.Constant.Constant.PARENT_PHONE_NUMBER_KEY;
import static com.example.admin.managerstundent.Constant.Constant.PHONE_NUMBER_KEY;

public class Student implements Serializable{


    @SerializedName("Id")
	private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("ParentsPhoneNumber")
    private String parentsPhoneNumber;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @SerializedName("IsMale")
    private boolean isMale;
    @SerializedName("IsPaid")
    private boolean isPaid;

    public Student(int id, String name, String phoneNumber, String parentsPhoneNumber, String dateOfBirth, boolean isMale, boolean isPaid) {
        this.id  = id;
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
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
