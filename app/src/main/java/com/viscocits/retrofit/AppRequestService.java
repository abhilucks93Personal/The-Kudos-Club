package com.viscocits.retrofit;


import com.viscocits.home_post.model.ModelResponseAddComment;
import com.viscocits.home_post.model.ModelResponseCommon;
import com.viscocits.home_post.model.ModelResponseCountryFilterList;
import com.viscocits.home_post.model.ModelResponseEngagementFilterList;
import com.viscocits.home_post.model.getPostResponse.ModelResponseWallPost;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * * Interface through which all the api calls will be performed
 */
public interface AppRequestService {

    @FormUrlEncoded
    @POST("GetAllEngagements")
    Observable<ModelResponseEngagementFilterList> getEngagementFilterListApi(@Field("ClientId") String clientId);

    @FormUrlEncoded
    @POST("GetAllCountries")
    Observable<ModelResponseCountryFilterList> getCountryFilterListApi(@Field("ClientId") String clientId);


    @FormUrlEncoded
    @POST("GetWallpostData")
    Observable<ModelResponseWallPost> getWallPostData(@Field("UniqueDeviceId") String uniqueDeviceId,
                                                      @Field("DeviceId") String deviceId,
                                                      @Field("UserId") String userId,
                                                      @Field("ClientId") String clientId,
                                                      @Field("EngagementIds") int engagementIds,
                                                      @Field("CountryId") int countryId,
                                                      @Field("PageSize") int pageSize,
                                                      @Field("PageNumber") int pageNumber,
                                                      @Field("UserImgPath") String userImgPath,
                                                      @Field("defaultAvatar") String defaultAvatar,
                                                      @Field("UserActivityImgPath") String userActivityImgPath,
                                                      @Field("RecognitionImgPath") String recognitionImgPath,
                                                      @Field("IdeaImgPath") String ideaImgPath);

    @FormUrlEncoded
    @POST("SaveLike")
    Observable<ModelResponseCommon> likePostApi(@Field("UserId") String userId,
                                                @Field("PostId") int postId,
                                                @Field("Action") String action);

    @FormUrlEncoded
    @POST("SaveSpam")
    Observable<ModelResponseCommon> spamPostApi(@Field("UserId") String userId,
                                                @Field("PostId") int postId,
                                                @Field("Message") String message,
                                                @Field("ClientId") int clientId);


    @FormUrlEncoded
    @POST("SaveComment")
    Observable<ModelResponseAddComment> commentPostApi(@Field("UserId") String userId,
                                                       @Field("PostId") int postId,
                                                       @Field("CommentText") String commentText,
                                                       @Field("UserImgPath") String userImgPath,
                                                       @Field("defaultAvatar") String defaultAvatar);


}