package com.viscocits.home_recognize.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponseValuesListData {

    private int Reward_Id;
    private String RewardTitle;
    private String RewardDesc;
    private String RewardTime;
    private int Client_Id;
    private String CreatedDate;
    private int CreatedBy;
    private boolean IsActive;

    public int getReward_Id() {
        return Reward_Id;
    }

    public void setReward_Id(int reward_Id) {
        Reward_Id = reward_Id;
    }

    public String getRewardTitle() {
        return RewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        RewardTitle = rewardTitle;
    }

    public String getRewardDesc() {
        return RewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        RewardDesc = rewardDesc;
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
        return RewardTitle;
    }
}
