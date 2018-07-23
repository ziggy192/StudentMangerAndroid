package com.example.admin.managerstundent.DTO;

import com.example.admin.managerstundent.Entity.ClassDetail;
import com.google.gson.annotations.SerializedName;

public class SlotRequestPostDTO {


    @SerializedName("ClassDetailId")
    private int classDetailId;

    public SlotRequestPostDTO(int classDetailId) {
        this.classDetailId = classDetailId;
    }

    public int getClassDetailId() {
        return classDetailId;
    }

    public void setClassDetailId(int classDetailId) {
        this.classDetailId = classDetailId;
    }
}
