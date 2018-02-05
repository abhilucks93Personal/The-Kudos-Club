package com.viscocits.home_recognize.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abhi on 05/02/18.
 */

public class ModelResponseLogin {

    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("StatusMsg")
    private String statusMsg;

    @SerializedName("Data")
    private ModelResponseLoginData data;

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

    public ModelResponseLoginData getData() {
        return data;
    }

    public void setData(ModelResponseLoginData data) {
        this.data = data;
    }
}
