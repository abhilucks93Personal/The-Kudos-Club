package com.viscocits.home_post.model.postModels;

public class ModelPostImages {

        private int PostId;
        private String ContentType;
        private String CreatedBy;
        private String CreatedDate;
        private String ImagePath;
        private String UrlLink;
        private String UserActEvidence_Id;
        private String UserActivity_Id;

    public ModelPostImages(int postId, String contentType, String createdBy, String createdDate, String imagePath, String urlLink, String userActEvidence_Id, String userActivity_Id) {
        PostId = postId;
        ContentType = contentType;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        ImagePath = imagePath;
        UrlLink = urlLink;
        UserActEvidence_Id = userActEvidence_Id;
        UserActivity_Id = userActivity_Id;
    }

    public int getPostId() {
            return PostId;
        }

        public void setPostId(int postId) {
            PostId = postId;
        }

        public String getContentType() {
            return ContentType;
        }

        public void setContentType(String contentType) {
            ContentType = contentType;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            CreatedDate = createdDate;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public String getUrlLink() {
            return UrlLink;
        }

        public void setUrlLink(String urlLink) {
            UrlLink = urlLink;
        }

        public String getUserActEvidence_Id() {
            return UserActEvidence_Id;
        }

        public void setUserActEvidence_Id(String userActEvidence_Id) {
            UserActEvidence_Id = userActEvidence_Id;
        }

        public String getUserActivity_Id() {
            return UserActivity_Id;
        }

        public void setUserActivity_Id(String userActivity_Id) {
            UserActivity_Id = userActivity_Id;
        }
    }