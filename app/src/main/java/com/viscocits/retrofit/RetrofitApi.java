package com.viscocits.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.viscocits.home_post.model.ModelResponseAddComment;
import com.viscocits.home_post.model.ModelResponseCommon;
import com.viscocits.home_post.model.ModelResponseCountryFilterList;
import com.viscocits.home_post.model.ModelResponseEngagementFilterList;
import com.viscocits.home_post.model.getPostResponse.ModelResponseWallPost;
import com.viscocits.home_recognize.RecognizeFragment;
import com.viscocits.home_recognize.model.ModelResponseKudosPoints;
import com.viscocits.home_recognize.model.ModelResponseLogin;
import com.viscocits.home_recognize.model.ModelResponsePointsList;
import com.viscocits.home_recognize.model.ModelResponseReasonsList;
import com.viscocits.home_recognize.model.ModelResponseRecognitionImage;
import com.viscocits.home_recognize.model.ModelResponseRecognitionSubmit;
import com.viscocits.home_recognize.model.ModelResponseUsersList;
import com.viscocits.home_recognize.model.ModelResponseValuesList;
import com.viscocits.login.LoginActivity;
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

                        Utility.showToast(activity, "failed\n" + e.getMessage());
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
                        Utility.showToast(activity, "failed\n" + e.getMessage());
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
                        Utility.showToast(activity, "failed\n" + e.getMessage());
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
                        Utility.showToast(activity, "failed\n" + e.getMessage());
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
                        Utility.showToast(activity, "failed\n" + e.getMessage());
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
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);
                    }

                    @Override
                    public void onNext(ModelResponseAddComment responseAddComment) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(responseAddComment);

                    }

                });
    }

    public void getKudosPoints(final Activity activity,
                               final ResponseListener _mlistener_response) {
        this.mlistener_response = _mlistener_response;

        String userId = Utility.getPreferences(activity, Constants.keyUserId);

        RetrofitClient.getClient().getKudosPoints(Constants.CLIENT_ID, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseKudosPoints>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseKudosPoints modelResponseKudosPoints) {
                        mlistener_response._onNext(modelResponseKudosPoints);

                    }

                });
    }

    public void getUsersList(final Activity activity,
                             final ResponseListener _mlistener_response) {
        this.mlistener_response = _mlistener_response;

        String userId = Utility.getPreferences(activity, Constants.keyUserId);

        RetrofitClient.getClient().getUsers(Constants.CLIENT_ID, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseUsersList>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseUsersList modelResponseUsersList) {
                        mlistener_response._onNext(modelResponseUsersList);

                    }

                });
    }

    public void getReasonsList(final Activity activity,
                               final ResponseListener _mlistener_response) {
        this.mlistener_response = _mlistener_response;

        RetrofitClient.getClient().getReasonsList(Constants.CLIENT_ID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseReasonsList>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseReasonsList modelResponseReasonsList) {
                        mlistener_response._onNext(modelResponseReasonsList);

                    }

                });
    }

    public void getValuesList(final Activity activity,
                              final ResponseListener _mlistener_response) {
        this.mlistener_response = _mlistener_response;

        RetrofitClient.getClient().getValuesList(Constants.CLIENT_ID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseValuesList>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseValuesList modelResponseValuesList) {
                        mlistener_response._onNext(modelResponseValuesList);

                    }

                });
    }

    public void getPointsList(final Activity activity,
                              final ResponseListener _mlistener_response) {
        this.mlistener_response = _mlistener_response;

        RetrofitClient.getClient().getPointsList(Constants.CLIENT_ID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponsePointsList>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponsePointsList modelResponsePointsList) {
                        mlistener_response._onNext(modelResponsePointsList);

                    }

                });
    }

    public void submitRecognition(final Activity activity,
                                  final ResponseListener _mlistener_response,
                                  int userId,
                                  int reasonId,
                                  int rewardId,
                                  String valueTitle,
                                  String supportingText) {


        this.mlistener_response = _mlistener_response;

        String mUserId = Utility.getPreferences(activity, Constants.keyUserId);


        mProgressDialog = new ProgressDialog(activity);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();
        }

        RetrofitClient.getClient().submitRecognition(userId,
                reasonId,
                rewardId,
                supportingText,
                true,
                Constants.CLIENT_ID,
                mUserId,
                valueTitle
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseRecognitionSubmit>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseRecognitionSubmit modelResponseRecognitionSubmit) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(modelResponseRecognitionSubmit);

                    }

                });


    }

    public void logIn(final Activity activity,
                      final ResponseListener _mlistener_response,
                      String strUserName,
                      String strPassword) {
        this.mlistener_response = _mlistener_response;

        mProgressDialog = new ProgressDialog(activity);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();
        }

        RetrofitClient.getClient().logIn(strUserName,
                strPassword,
                Constants.CLIENT_ID
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseLogin>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseLogin modelResponseLogin) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(modelResponseLogin);

                    }

                });

    }

    public void uploadImageRecognition(final Activity activity,
                                       final ResponseListener _mlistener_response,
                                       String encodedImage, long recognitionId) {

        this.mlistener_response = _mlistener_response;

        mProgressDialog = new ProgressDialog(activity);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Uploading image...");
            mProgressDialog.show();
        }
        String name = "";
        String mUserId = Utility.getPreferences(activity, Constants.keyUserId);
        RetrofitClient.getClient().uploadImageRecognition(
                Constants.CLIENT_NAME,
                name,
                recognitionId,
                mUserId,
                Constants.CLIENT_ID,
                encodedImage,
                Constants.MainImageUploadPath,
                Constants.RecogImageUploadPath
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelResponseRecognitionImage>() {
                    @Override
                    public void onCompleted() {
                        mlistener_response._onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Utility.showToast(activity, "failed\n" + e.getMessage());
                        mlistener_response._onError(e);

                    }

                    @Override
                    public void onNext(ModelResponseRecognitionImage modelResponseRecognitionImage) {
                        mProgressDialog.dismiss();
                        mlistener_response._onNext(modelResponseRecognitionImage);

                    }

                });
    }

}