package com.viscocits.home_post.model;

import com.google.gson.annotations.SerializedName;
import com.viscocits.home_post.model.postModels.ModelPostComments;

import java.util.ArrayList;

/**
 * Created by abhi on 26/11/17.
 */

public class ModelResponseAddComment {

    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("StatusMsg")
    private String statusMsg;

    @SerializedName("Data")
    private ArrayList<ModelPostComments> data;


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

    public ArrayList<ModelPostComments> getData() {
        return data;
    }

    public void setData(ArrayList<ModelPostComments> data) {
        this.data = data;
    }
}
