package com.viscocits.home_recognize.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponseUsersList {
    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("StatusMsg")
    private String statusMsg;

    @SerializedName("Data")
    private ArrayList<ModelResponseUsersListData> data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public ArrayList<ModelResponseUsersListData> getData() {
        return data;
    }

    public void setData(ArrayList<ModelResponseUsersListData> data) {
        this.data = data;
    }
}
