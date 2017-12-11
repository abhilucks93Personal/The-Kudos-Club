package com.viscocits.home_post.model.postModels;

public class ModelPostComments {

    private int PostId;
    private String CommentedBy;
    private String CommentedDate;
    private String CommentId;
    private String UpdatedBy;
    private String UpdatedDate;
    private String AvatarExt;
    private String Name;
    private String Message;

    public ModelPostComments(int postId, String commentedBy, String commentedDate, String commentId, String updatedBy, String updatedDate, String avatarExt, String name, String message) {
        PostId = postId;
        CommentedBy = commentedBy;
        CommentedDate = commentedDate;
        CommentId = commentId;
        UpdatedBy = updatedBy;
        UpdatedDate = updatedDate;
        AvatarExt = avatarExt;
        Name = name;
        Message = message;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getCommentedBy() {
        return CommentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        CommentedBy = commentedBy;
    }

    public String getCommentedDate() {
        return CommentedDate;
    }

    public void setCommentedDate(String commentedDate) {
        CommentedDate = commentedDate;
    }

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getAvatarExt() {
        return AvatarExt;
    }

    public void setAvatarExt(String avatarExt) {
        AvatarExt = avatarExt;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}