package com.viscocits.home_post.model.postModels;

import java.util.ArrayList;

/**
 * Created by abhi on 05/08/17.
 */

public class ModelPostMergedData {

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
    private int PostId;
    private String PostType;
    private Integer Reward_Id;
    private Boolean SPAMEDBYUSER;
    private Integer SuggestedChallenge;
    private String UserName;

    //  Array 2
    public ArrayList<ModelPostComments> commentsList;


    //  Array 3
    public ArrayList<ModelPostLikes> likesList;


    // Array 4
    public ArrayList<ModelPostImages> postImagesList;


    // Array 5
    public ArrayList<ModelPostRecognizeImages> recognizeImagesList;


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

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
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

    public ArrayList<ModelPostComments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(ArrayList<ModelPostComments> commentsList) {
        this.commentsList = commentsList;
    }

    public ArrayList<ModelPostLikes> getLikesList() {
        return likesList;
    }

    public void setLikesList(ArrayList<ModelPostLikes> likesList) {
        this.likesList = likesList;
    }

    public ArrayList<ModelPostImages> getPostImagesList() {
        return postImagesList;
    }

    public void setPostImagesList(ArrayList<ModelPostImages> postImagesList) {
        this.postImagesList = postImagesList;
    }

    public ArrayList<ModelPostRecognizeImages> getRecognizeImagesList() {
        return recognizeImagesList;
    }

    public void setRecognizeImagesList(ArrayList<ModelPostRecognizeImages> recognizeImagesList) {
        this.recognizeImagesList = recognizeImagesList;
    }


}
