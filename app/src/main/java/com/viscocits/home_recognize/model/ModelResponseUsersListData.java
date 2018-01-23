package com.viscocits.home_recognize.model;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponseUsersListData {

    private long value;
    private String label;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
