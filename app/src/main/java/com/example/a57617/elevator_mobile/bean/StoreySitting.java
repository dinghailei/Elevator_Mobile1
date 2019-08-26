package com.example.a57617.elevator_mobile.bean;

import java.io.Serializable;

public class StoreySitting implements Serializable {
    private String time;//设置电梯的时间
    private String destination;//目标楼层
    private String date;//设置每周几执行该设置
    private boolean isAvailable;//是否打开
    private boolean isEditing;//是否为正在编辑

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
