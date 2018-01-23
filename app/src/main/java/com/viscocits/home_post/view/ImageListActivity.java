package com.viscocits.home_post.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.viscocits.R;
import com.viscocits.home_post.controller.ImageListAdapter;
import com.viscocits.support.ContactUsActivity;
import com.viscocits.utils.Utility;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    private ArrayList<String> imageUrls = new ArrayList<>();
    RecyclerView rvImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.setStatusBarTranslucent(this, false);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_images);

        Bundle bundle = getIntent().getExtras();
        int pos = 0;
        if (bundle != null) {
            pos = bundle.getInt("pos", 0);
            imageUrls = bundle.getStringArrayList("urls");
        }

        rvImages = (RecyclerView) findViewById(R.id.rv_images);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvImages.setLayoutManager(mLayoutManager);

        ImageListAdapter imageListAdapter = new ImageListAdapter(ImageListActivity.this, R.layout.item_image, imageUrls);
        rvImages.setAdapter(imageListAdapter);
        rvImages.smoothScrollToPosition(pos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable d = getResources().getDrawable(R.drawable.layout_bg_toolbar);
        getSupportActionBar().setBackgroundDrawable(d);

        setTitle("Images");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Utility.hideKeyboard(ImageListActivity.this);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}