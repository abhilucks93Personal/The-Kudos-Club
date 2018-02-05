package com.viscocits.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.viscocits.R;
import com.viscocits.home_recognize.model.ModelResponseKudosPoints;
import com.viscocits.home_recognize.model.ModelResponseLogin;
import com.viscocits.home_recognize.model.ModelResponseLoginData;
import com.viscocits.navigation.MainActivity;
import com.viscocits.retrofit.RetrofitApi;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * Created by abhishekagarwal on 1/30/17.
 */
public class LoginActivity extends Activity implements View.OnClickListener, RetrofitApi.ResponseListener {


    private LinearLayout llLogin;
    private Animation animationFadeIn;
    TextView tvLogin;
    private EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.setStatusBarTranslucent(this, true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_screen);

        findViewById();

        initAnimation();

        llLogin.setVisibility(View.VISIBLE);


    }

    private void initAnimation() {
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                llLogin.setVisibility(View.VISIBLE);
            }
        });
    }


    private void findViewById() {
        llLogin = findViewById(R.id.ll_login);

        etUserName = findViewById(R.id.et_user_name);

        etPassword = findViewById(R.id.et_password);

        tvLogin = findViewById(R.id.tv_login);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:

                String strUserName = etUserName.getText().toString().trim();
                String strPassword = etPassword.getText().toString().trim();

                if (strUserName.length() <= 3 || strPassword.length() <= 3) {
                    Utility.showToast(LoginActivity.this, "Please enter valid credentials.");
                } else {
                    RetrofitApi.getInstance().logIn(LoginActivity.this, this, strUserName, strPassword);
                }


                break;

        }
    }


    @Override
    public void _onCompleted() {

    }

    @Override
    public void _onError(Throwable e) {
    }

    @Override
    public void _onNext(Object obj) {
        if (obj instanceof ModelResponseLogin) {
            ModelResponseLogin modelResponseLogin = (ModelResponseLogin) obj;
            switch (modelResponseLogin.getStatusCode()) {
                case Constants.STATUS_CODE_SUCCESS:
                    ModelResponseLoginData modelResponseLoginData = modelResponseLogin.getData();
                    Utility.addPreferences(LoginActivity.this, Constants.keyUserId, "" + modelResponseLoginData.getUser_Id());
                    Utility.addPreferences(LoginActivity.this, Constants.keyLoginCheck, true);
                    startActivity(new Intent(this, MainActivity.class));
                    finishAffinity();
                    break;

                case "404":
                    Utility.showToast(LoginActivity.this, "User dose not exist.");
                    break;

                case "402":
                    Utility.showToast(LoginActivity.this, "Incorrect Password.");
                    break;
            }
        }
    }
}
