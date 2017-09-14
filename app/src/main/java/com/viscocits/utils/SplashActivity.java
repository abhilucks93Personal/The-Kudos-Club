package com.viscocits.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.viscocits.R;
import com.viscocits.login.LoginActivity;
import com.viscocits.navigation.MainActivity;


/**
 * Created by abhi on 17/04/17.
 */

public class SplashActivity extends Activity {


    Animation animationFadeIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.setStatusBarTranslucent(this, true);

        setContentView(R.layout.activity_splash);

        ImageView image = (ImageView) findViewById(R.id.image);

        initAnimation(image);

    }

    private void initAnimation(final View view) {
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        view.startAnimation(animationFadeIn);
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
                view.setVisibility(View.VISIBLE);

                Intent intent;
                if (Utility.getPreferences(SplashActivity.this, Constants.keyLoginCheck, false))
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                else
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
