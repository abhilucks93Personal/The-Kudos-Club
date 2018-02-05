package com.viscocits.home_recognize.model;

/**
 * Created by abhishekagarwal on 1/25/18.
 */

public class ModelResponseRecognitionImage {

    private String StatusCode;
    private String StatusMsg;
    private String Data;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusMsg() {
        return StatusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        StatusMsg = statusMsg;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
