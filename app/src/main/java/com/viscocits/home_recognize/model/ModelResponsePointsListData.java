package com.viscocits.home_recognize.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponsePointsListData {

    private int Value_Id;
    private String ValueTitle;
    private String ValueDesc;
    private int Client_Id;
    private String CreatedDate;
    private int CreatedBy;
    private boolean IsActive;

    public int getValue_Id() {
        return Value_Id;
    }

    public void setValue_Id(int value_Id) {
        Value_Id = value_Id;
    }

    public String getValueTitle() {
        return ValueTitle;
    }

    public void setValueTitle(String valueTitle) {
        ValueTitle = valueTitle;
    }

    public String getValueDesc() {
        return ValueDesc;
    }

    public void setValueDesc(String valueDesc) {
        ValueDesc = valueDesc;
    }

    public int getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(int client_Id) {
        Client_Id = client_Id;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }












}
