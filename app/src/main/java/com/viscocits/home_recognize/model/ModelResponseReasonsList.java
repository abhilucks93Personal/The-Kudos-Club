package com.viscocits.home_recognize.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponseReasonsList {

    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("StatusMsg")
    private String statusMsg;

    @SerializedName("Data")
    private ArrayList<ModelResponseReasonsListData> data;


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

    public ArrayList<ModelResponseReasonsListData> getData() {
        return data;
    }

    public void setData(ArrayList<ModelResponseReasonsListData> data) {
        this.data = data;
    }
}
