package com.viscocits.support;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.navigation.MainActivity;
import com.viscocits.utils.Utility;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable d = getResources().getDrawable(R.drawable.layout_bg_toolbar);
        getSupportActionBar().setBackgroundDrawable(d);

        setTitle("Contact Us");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Utility.hideKeyboard(ContactUsActivity.this);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
