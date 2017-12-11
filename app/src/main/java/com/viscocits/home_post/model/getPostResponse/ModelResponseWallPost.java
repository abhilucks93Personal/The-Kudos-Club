package com.viscocits.home_post.model.getPostResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abhi on 26/11/17.
 */

public class ModelResponseWallPost {

    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("StatusMsg")
    private String statusMsg;

    @SerializedName("Data")
    private ArrayList<ArrayList<ModelResponseWallPostData>> data;

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

    public ArrayList<ArrayList<ModelResponseWallPostData>> getData() {
        return data;
    }

    public void setData(ArrayList<ArrayList<ModelResponseWallPostData>> data) {
        this.data = data;
    }
}
