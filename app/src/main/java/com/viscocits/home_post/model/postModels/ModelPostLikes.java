package com.viscocits.home_post.model.postModels;

public class ModelPostLikes {

    private int PostId;
    private String Liked;
    private String LikedBy;
    private String LikedDate;
    private String LikeId;

    public ModelPostLikes(int postId, String liked, String likedBy, String likedDate, String likeId) {
        PostId = postId;
        Liked = liked;
        LikedBy = likedBy;
        LikedDate = likedDate;
        LikeId = likeId;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getLiked() {
        return Liked;
    }

    public void setLiked(String liked) {
        Liked = liked;
    }

    public String getLikedBy() {
        return LikedBy;
    }

    public void setLikedBy(String likedBy) {
        LikedBy = likedBy;
    }

    public String getLikedDate() {
        return LikedDate;
    }

    public void setLikedDate(String likedDate) {
        LikedDate = likedDate;
    }

    public String getLikeId() {
        return LikeId;
    }

    public void setLikeId(String likeId) {
        LikeId = likeId;
    }
}