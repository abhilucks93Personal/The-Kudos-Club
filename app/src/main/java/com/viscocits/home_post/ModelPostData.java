package com.viscocits.home_post;

import java.util.ArrayList;

/**
 * Created by abhi on 05/08/17.
 */

public class ModelPostData {

    private String name;

    private String postTime;

    private String profileImage;

    private String type;

    private String challenge;

    private String challengeImage;

    private String likeCount;

    private ArrayList<ModelCommentsData> comments;

    public ModelPostData(String name, String postTime, String profileImage, String type, String challenge, String challengeImage, String likeCount, ArrayList<ModelCommentsData> comments) {
        this.name = name;
        this.postTime = postTime;
        this.profileImage = profileImage;
        this.type = type;
        this.challenge = challenge;
        this.challengeImage = challengeImage;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getChallengeImage() {
        return challengeImage;
    }

    public void setChallengeImage(String challengeImage) {
        this.challengeImage = challengeImage;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public ArrayList<ModelCommentsData> getComments() {
        return comments;
    }

    public void setComments(ArrayList<ModelCommentsData> comments) {
        this.comments = comments;
    }
}
