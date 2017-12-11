package com.viscocits.home_post.model;

/**
 * Created by abhi on 05/08/17.
 */

public class ModelCommentsData {

    private String userImage;

    private String userName;

    private String commentMsg;

    private String commentTime;

    public ModelCommentsData(String userImage, String userName, String commentMsg, String commentTime) {
        this.userImage = userImage;
        this.userName = userName;
        this.commentMsg = commentMsg;
        this.commentTime = commentTime;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
