package com.viscocits.home_recognize.model;

/**
 * Created by abhishekagarwal on 1/25/18.
 */

public class ModelResponseRecognitionSubmit {

    private String StatusCode;
    private String StatusMsg;
    private long Data;

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

    public long getData() {
        return Data;
    }

    public void setData(long data) {
        Data = data;
    }
}
