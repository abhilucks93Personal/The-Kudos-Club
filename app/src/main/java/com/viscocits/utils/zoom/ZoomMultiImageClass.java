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

import java.util.ArrayList;

public class ZoomMultiImageClass extends Activity {

    private ImageView back_btn;
    private ZoomMultiImagePagerAdapter zoomMultiImagePagerAdapter;
    private ViewPager vpZoomImages;
    private TextView[] dots;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private LinearLayout llDotsOfferImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.setStatusBarTranslucent(this, false);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zoom_multi_image);

        Bundle bundle = getIntent().getExtras();
        int pos = 0;
        if (bundle != null) {
            pos = bundle.getInt("pos", 0);
            imageUrls  = bundle.getStringArrayList("urls");
        }


        vpZoomImages = (ViewPager) findViewById(R.id.vp_offer_images);
        llDotsOfferImages = (LinearLayout) findViewById(R.id.ll_dots);
        back_btn = (ImageView) findViewById(R.id.back_btn);


        zoomMultiImagePagerAdapter = new ZoomMultiImagePagerAdapter(ZoomMultiImageClass.this, imageUrls );
        vpZoomImages.setAdapter(zoomMultiImagePagerAdapter);

        vpZoomImages.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


       // addBottomDots(0);

        vpZoomImages.setCurrentItem(pos, true);


        back_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                        }
                                    }
        );
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[imageUrls .size()];

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