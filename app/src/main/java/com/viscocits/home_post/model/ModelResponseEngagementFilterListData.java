package com.viscocits.home_post.model;

/**
 * Created by abhi on 07/12/17.
 */

public class ModelResponseEngagementFilterListData {

    private int Engagement_Id;
    private String EngagementTitle;
    private String EngagementDesc;
    private int Client_Id;
    private String CreatedDate;
    private int CreatedBy;
    private boolean IsActive;
    private String DisplayTitle;

    public int getEngagement_Id() {
        return Engagement_Id;
    }

    public void setEngagement_Id(int engagement_Id) {
        Engagement_Id = engagement_Id;
    }

    public String getEngagementTitle() {
        return EngagementTitle;
    }

    public void setEngagementTitle(String engagementTitle) {
        EngagementTitle = engagementTitle;
    }

    public String getEngagementDesc() {
        return EngagementDesc;
    }

    public void setEngagementDesc(String engagementDesc) {
        EngagementDesc = engagementDesc;
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

    public String getDisplayTitle() {
        return DisplayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        DisplayTitle = displayTitle;
    }

    public String toString() {
        return DisplayTitle;
    }
}
