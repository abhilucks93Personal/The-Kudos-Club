package com.viscocits.home_post.model.postModels;

public class ModelPostRecognizeImages {

    private int PostId;
    private String RecogImage_Id;
    private String Recognition_Id;
    private String UploadedBy;
    private String UploadedDate;
    private String ImagePath;

    public ModelPostRecognizeImages(int postId, String recogImage_Id, String recognition_Id, String uploadedBy, String uploadedDate, String imagePath) {
        PostId = postId;
        RecogImage_Id = recogImage_Id;
        Recognition_Id = recognition_Id;
        UploadedBy = uploadedBy;
        UploadedDate = uploadedDate;
        ImagePath = imagePath;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getRecogImage_Id() {
        return RecogImage_Id;
    }

    public void setRecogImage_Id(String recogImage_Id) {
        RecogImage_Id = recogImage_Id;
    }

    public String getRecognition_Id() {
        return Recognition_Id;
    }

    public void setRecognition_Id(String recognition_Id) {
        Recognition_Id = recognition_Id;
    }

    public String getUploadedBy() {
        return UploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        UploadedBy = uploadedBy;
    }

    public String getUploadedDate() {
        return UploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        UploadedDate = uploadedDate;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}