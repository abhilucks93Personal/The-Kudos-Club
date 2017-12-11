package com.viscocits.home_post.model;

import com.google.gson.annotations.SerializedName;
import com.viscocits.home_post.model.getPostResponse.ModelResponseWallPostData;

import java.util.ArrayList;

/**
 * Created by abhi on 26/11/17.
 */

public class ModelResponseCommon {

    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("StatusMsg")
    private String statusMsg;


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

}
