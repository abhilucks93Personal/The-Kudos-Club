package com.viscocits.support;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.viscocits.R;

public class SupportContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable d = getResources().getDrawable(R.drawable.layout_bg_toolbar);
        getSupportActionBar().setBackgroundDrawable(d);

        TextView textView = (TextView) findViewById(R.id.tv_content);

        String title = getIntent().getStringExtra("title");
        if (title != null)
            setTitle(title);
        else
            setTitle("Support");

        String content = getIntent().getStringExtra("content");
        if (content != null)
            textView.setText(Html.fromHtml(content));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
