package com.example.admin.managerstundent.DTO;

public class StudentDTO {
    private Integer id;
    private String url;
    private String name;
    private String birthDay;
    private String phoneNumber;
    private String parentsPhone;
    private boolean isMale;
    private boolean paid;

    public StudentDTO(Integer id, String url, String name, String birthDay, String phoneNumber, String parentsPhone, boolean isMale, boolean paid) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.parentsPhone = parentsPhone;
        this.isMale = isMale;
        this.paid = paid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String age) {
        this.birthDay = birthDay;
    }


    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setParentsPhone(String parentsPhone) {
        this.parentsPhone = parentsPhone;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getParentsPhone() {
        return parentsPhone;
    }
}
