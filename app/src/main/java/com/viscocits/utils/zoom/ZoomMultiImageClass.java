package com.viscocits.utils.zoom;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.utils.Utility;
import com.viscocits.utils.zoom.settings.SettingsController;
import com.viscocits.utils.zoom.settings.SettingsMenu;

import java.util.ArrayList;

public class ZoomMultiImageClass extends Activity {

    private TextView[] dots;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private LinearLayout llDotsOfferImages;
    private final SettingsMenu settingsMenu = new SettingsMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.setStatusBarTranslucent(this, false);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zoom_multi_image);

        Bundle bundle = getIntent().getExtras();
        int pos = 0;
        if (bundle != null) {
            pos = bundle.getInt("pos", 0);
            imageUrls = bundle.getStringArrayList("urls");
        }


        ViewPager vpZoomImages = findViewById(R.id.vp_offer_images);
        vpZoomImages.setAdapter(new ZoomMultiImagePagerAdapter(ZoomMultiImageClass.this, vpZoomImages, imageUrls, getSettingsController()));
        // vpZoomImages.requestDisallowInterceptTouchEvent(true);

        llDotsOfferImages = findViewById(R.id.ll_dots);
        ImageView back_btn = findViewById(R.id.back_btn);

        vpZoomImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        addBottomDots(0);

        vpZoomImages.setCurrentItem(pos, true);


        back_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                        }
                                    }
        );
    }

    protected SettingsController getSettingsController() {
        return settingsMenu;
    }


    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[imageUrls.size()];

        llDotsOfferImages.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorGrey));
            llDotsOfferImages.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }
}
