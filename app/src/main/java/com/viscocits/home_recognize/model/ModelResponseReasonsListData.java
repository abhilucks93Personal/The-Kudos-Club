package com.viscocits.home_recognize.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponseReasonsListData {


    private int Reason_Id;
    private String ReasonTitle;
    private String RReasonDesc;
    private String RewardTime;
    private int Client_Id;
    private String CreatedDate;
    private int CreatedBy;
    private boolean IsActive;

    public int getReason_Id() {
        return Reason_Id;
    }

    public void setReason_Id(int reason_Id) {
        Reason_Id = reason_Id;
    }

    public String getReasonTitle() {
        return ReasonTitle;
    }

    public void setReasonTitle(String reasonTitle) {
        ReasonTitle = reasonTitle;
    }

    public String getRReasonDesc() {
        return RReasonDesc;
    }

    public void setRReasonDesc(String RReasonDesc) {
        this.RReasonDesc = RReasonDesc;
    }

    public String getRewardTime() {
        return RewardTime;
    }

    public void setRewardTime(String rewardTime) {
        RewardTime = rewardTime;
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


    @Override
    public String toString() {
        return ReasonTitle;
    }
}
