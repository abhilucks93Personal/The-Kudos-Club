package com.viscocits.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.viscocits.home_post.model.ModelResponseAddComment;
import com.viscocits.home_post.model.ModelResponseCommon;
import com.viscocits.home_post.model.ModelResponseCountryFilterList;
import com.viscocits.home_post.model.ModelResponseEngagementFilterList;
import com.viscocits.home_post.model.getPostResponse.ModelResponseWallPost;
import com.viscocits.home_post.view.PostFragment;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abhishekagarwal on 3/21/17.
 */

public class RetrofitApi {

    private ProgressDialog mProgressDialog;
    private static RetrofitApi retrofitApi = null;
    private ResponseListener mlistener_response;


    public static RetrofitApi getInstance() {

        if (retrofitApi != null)
            return retrofitApi;
        else
            return new RetrofitApi();
    }


    public interface ResponseListener {

        void _onCompleted();

        void _onError(Throwable e);

        void _onNext(Object obj);


    }


    // --------------------- Retrofit Api Methods ----------------------------------------------------------


    public void getEngagementFilterList(final Activity activity,
                                        final ResponseListener _mlistener_response) {

        this.mlistener_response = _mlistener_response;

        RetrofitClient.getClient().getEngagementFilterListApi(Constants.CLIENT_ID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseEngagementFilterList>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Utility.showToast(activity, "failed");
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseEngagementFilterList engagementFilterList) {

                        mlistener_response._onNext(engagementFilterList);

                    }

                });
    }


    public void getCountryFilterList(final Activity activity,
                                     final ResponseListener _mlistener_response) {

        this.mlistener_response = _mlistener_response;

        RetrofitClient.getClient().getCountryFilterListApi(Constants.CLIENT_ID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseCountryFilterList>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed");
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseCountryFilterList countryFilterList) {
                        mlistener_response._onNext(countryFilterList);

                    }

                });
    }


    public void getWallPostData(final Activity activity,
                                final ResponseListener _mlistener_response,
                                int engagementIds,
                                int countryId,
                                int pageSize,
                                int pageNumber) {

        this.mlistener_response = _mlistener_response;

        String userId = Utility.getPreferences(activity, Constants.keyUserId);
        String uniqueDeviceId = Utility.getPreferences(activity, Constants.keyUniqueDeviceId);
        String deviceId = Utility.getPreferences(activity, Constants.keyDeviceId);



        RetrofitClient.getClient().getWallPostData(uniqueDeviceId,
                deviceId,
                userId,
                Constants.CLIENT_ID,
                engagementIds,
                countryId,
                pageSize,
                pageNumber,
                Constants.UserImgPath,
                Constants.defaultAvatar,
                Constants.UserActivityImgPath,
                Constants.RecognitionImgPath,
                Constants.IdeaImgPath).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseWallPost>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed");
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseWallPost responseWallPost) {
                        mlistener_response._onNext(responseWallPost);

                    }

                });
    }


    public void likePostApi(final Activity activity,
                            final ResponseListener _mlistener_response,
                            int postId,
                            String action) {

        this.mlistener_response = _mlistener_response;
        String userId = Utility.getPreferences(activity, Constants.keyUserId);
        mProgressDialog = new ProgressDialog(activity);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();
        }

        RetrofitClient.getClient().likePostApi(userId,
                postId,
                action).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseCommon>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Utility.showToast(activity, "failed");
                        mlistener_response._onError(e);
                    }

                    @Override
                    public void onNext(ModelResponseCommon responseCommon) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(responseCommon);

                    }

                });
    }

    public void spamPostApi(final Activity activity,
                            final ResponseListener _mlistener_response,
                            int postId,
                            String message,
                            int clientId) {

        this.mlistener_response = _mlistener_response;
        String userId = Utility.getPreferences(activity, Constants.keyUserId);
        mProgressDialog = new ProgressDialog(activity);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();
        }

        RetrofitClient.getClient().spamPostApi(userId,
                postId,
                message,
                clientId).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseCommon>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Utility.showToast(activity, "failed");
                        mlistener_response._onError(e);
                    }

                    @Override
                    public void onNext(ModelResponseCommon responseCommon) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(responseCommon);

                    }

                });
    }

    public void commentPostApi(final Activity activity,
                               final ResponseListener _mlistener_response,
                               int postId,
                               String commentText) {

        this.mlistener_response = _mlistener_response;

        String userId = Utility.getPreferences(activity, Constants.keyUserId);

        mProgressDialog = new ProgressDialog(activity);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();
        }

        RetrofitClient.getClient().commentPostApi(userId,
                postId,
                commentText,
                Constants.UserImgPath,
                Constants.defaultAvatar).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseAddComment>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Utility.showToast(activity, "failed");
                        mlistener_response._onError(e);
                    }

                    @Override
                    public void onNext(ModelResponseAddComment responseAddComment) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(responseAddComment);

                    }

                });
    }


}