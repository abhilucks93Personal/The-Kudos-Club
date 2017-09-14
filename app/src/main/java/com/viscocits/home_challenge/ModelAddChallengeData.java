package com.viscocits.home_challenge;

/**
 * Created by abhi on 20/08/17.
 */

class ModelAddChallengeData {

    private String name;

    private int photo;

    public ModelAddChallengeData(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
