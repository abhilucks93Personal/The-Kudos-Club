package com.viscocits.home_post.model.getPostResponse;

/**
 * Created by abhi on 05/08/17.
 */

public class ModelResponseWallPostData {

    private String AvatarExt;
    private Integer Client_Id;
    private Integer COMMENTCOUNT;
    private String Country_id;
    private String Email;
    private String Engagement_Id;
    private String GroupCountryIDs;
    private Integer InvolvementCOUNT;
    private Boolean IsActive;
    private Boolean IsGrouped;
    private Integer LIKECOUNT;
    private Boolean LIKEDBYUSER;
    private String Message;
    private String Name;
    private Integer PostedBy;
    private String PostedDate;
    private Integer PostId;
    private String PostType;
    private Integer Reward_Id;
    private Boolean SPAMEDBYUSER;
    private Integer SuggestedChallenge;
    private String UserName;

    //  Array 2
    private String CommentedBy;
    private String CommentedDate;
    private String CommentId;
    private String UpdatedBy;
    private String UpdatedDate;


    //  Array 3
    private String Liked;
    private String LikedBy;
    private String LikedDate;
    private String LikeId;

    // Array 4
    private String ContentType;
    private String CreatedBy;
    private String CreatedDate;
    private String ImagePath;
    private String UrlLink;
    private String UserActEvidence_Id;
    private String UserActivity_Id;

    // Array 5
    private String RecogImage_Id;
    private String Recognition_Id;
    private String UploadedBy;
    private String UploadedDate;


    public String getAvatarExt() {
        return AvatarExt;
    }

    public void setAvatarExt(String avatarExt) {
        AvatarExt = avatarExt;
    }

    public Integer getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(Integer client_Id) {
        Client_Id = client_Id;
    }

    public Integer getCOMMENTCOUNT() {
        return COMMENTCOUNT;
    }

    public void setCOMMENTCOUNT(Integer COMMENTCOUNT) {
        this.COMMENTCOUNT = COMMENTCOUNT;
    }

    public String getCountry_id() {
        return Country_id;
    }

    public void setCountry_id(String country_id) {
        Country_id = country_id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEngagement_Id() {
        return Engagement_Id;
    }

    public void setEngagement_Id(String engagement_Id) {
        Engagement_Id = engagement_Id;
    }

    public String getGroupCountryIDs() {
        return GroupCountryIDs;
    }

    public void setGroupCountryIDs(String groupCountryIDs) {
        GroupCountryIDs = groupCountryIDs;
    }

    public Integer getInvolvementCOUNT() {
        return InvolvementCOUNT;
    }

    public void setInvolvementCOUNT(Integer involvementCOUNT) {
        InvolvementCOUNT = involvementCOUNT;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Boolean getGrouped() {
        return IsGrouped;
    }

    public void setGrouped(Boolean grouped) {
        IsGrouped = grouped;
    }

    public Integer getLIKECOUNT() {
        return LIKECOUNT;
    }

    public void setLIKECOUNT(Integer LIKECOUNT) {
        this.LIKECOUNT = LIKECOUNT;
    }

    public Boolean getLIKEDBYUSER() {
        return LIKEDBYUSER;
    }

    public void setLIKEDBYUSER(Boolean LIKEDBYUSER) {
        this.LIKEDBYUSER = LIKEDBYUSER;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(Integer postedBy) {
        PostedBy = postedBy;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }

    public Integer getPostId() {
        return PostId;
    }

    public void setPostId(Integer postId) {
        PostId = postId;
    }

    public String getPostType() {
        return PostType;
    }

    public void setPostType(String postType) {
        PostType = postType;
    }

    public Integer getReward_Id() {
        return Reward_Id;
    }

    public void setReward_Id(Integer reward_Id) {
        Reward_Id = reward_Id;
    }

    public Boolean getSPAMEDBYUSER() {
        return SPAMEDBYUSER;
    }

    public void setSPAMEDBYUSER(Boolean SPAMEDBYUSER) {
        this.SPAMEDBYUSER = SPAMEDBYUSER;
    }

    public Integer getSuggestedChallenge() {
        return SuggestedChallenge;
    }

    public void setSuggestedChallenge(Integer suggestedChallenge) {
        SuggestedChallenge = suggestedChallenge;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
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
}
