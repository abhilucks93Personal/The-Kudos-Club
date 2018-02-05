package com.viscocits.utils.crop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viscocits.R;
import com.viscocits.utils.Utility;


/**
 * Created by abhishekagarwal on 2/13/17.
 */

public class CropPreviewActivity extends Activity {


    /**
     * The image to show in the activity.
     */
    private static Bitmap mImage;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crop_result);

        ImageView imageView = findViewById(R.id.resultImageView);
        imageView = findViewById(R.id.resultImageView);
        imageView.setBackgroundResource(R.color.colorWhite);
        TextView tv_ok = findViewById(R.id.crop_preview_tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkClicked();
            }
        });
        TextView tv_cancel = findViewById(R.id.crop_preview_tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClicked();
            }
        });
        Intent intent = getIntent();
        if (mImage != null) {
            imageView.setImageBitmap(mImage);
        } else {
            imageUri = intent.getParcelableExtra("URI");
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            } else {
                Toast.makeText(this, "No image is set to show", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        releaseBitmap();
        super.onBackPressed();
    }

    private void onCancelClicked() {
        releaseBitmap();
        finish();
    }

    private void onOkClicked() {
        Intent intent = new Intent();
        if (imageUri != null) {
            intent.putExtra("URI", imageUri);
        } else {
            Uri uri = Utility.saveBitmapToDisk(this, mImage);
            intent.putExtra("URI", uri);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void releaseBitmap() {
        if (mImage != null) {
            mImage.recycle();
            mImage = null;
        }
    }
}