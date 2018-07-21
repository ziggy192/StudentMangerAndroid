package com.example.admin.managerstundent.Entity;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    //internalId
    private int id;
    private String title;
    private String content;
    private boolean isRead;


    public NotificationModel(int id, String title, String content) {
        this(id, title, content, false);
    }

    public NotificationModel(int id, String title, String content, boolean isRead) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
