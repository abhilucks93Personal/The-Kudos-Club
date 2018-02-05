package com.viscocits.utils.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.viscocits.R;
import com.viscocits.utils.Utility;

/**
 * Created by abhishekagarwal on 2/14/17.
 */
public class CropActivity extends AppCompatActivity implements CropImageView.OnSetImageUriCompleteListener, CropImageView.OnCropImageCompleteListener {

    private CropImageView cropImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crop_priview);

        setTitle("");
        cropImageView = findViewById(R.id.cropImageView);
        cropImageView.setOnCropImageCompleteListener(this);
        cropImageView.setOnSetImageUriCompleteListener(this);
        Uri uri = getIntent().getParcelableExtra("uri");
        if (uri == null) {
            Toast.makeText(getApplicationContext(), "Unable to fetch image", Toast.LENGTH_SHORT).show();
            finish();
        }
        cropImageView.setImageUriAsync(uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.crop_image_menu_crop) {
            cropImageView.getCroppedImageAsync();
            return true;
        } else if (item.getItemId() == R.id.crop_image_menu_rotate_right) {
            cropImageView.rotateImage(90);
            return true;
        } else if (item.getItemId() == R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            Toast.makeText(this, "Image loaded successful", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("AIC", "Failed to load image by URI", error);
            Toast.makeText(this, "Image loading failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            Intent intent = new Intent(CropActivity.this, CropPreviewActivity.class);
            intent.putExtra("SAMPLE_SIZE", result.getSampleSize());
            if (result.getUri() != null) {
                intent.putExtra("URI", result.getUri());
            } else {
                Bitmap bitmap = cropImageView.getCropShape() == CropImageView.CropShape.OVAL ? CropImage.toOvalBitmap(result.getBitmap()) : result.getBitmap();
                Uri cropped_image_uri = Utility.saveBitmapToDisk(getApplicationContext(), bitmap);
                intent.putExtra("URI", cropped_image_uri);
            }
            startActivityForResult(intent, 400);
        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
            Toast.makeText(CropActivity.this, "Image crop failed: " + result.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getParcelableExtra("URI");
            if (imageUri != null) {
                Intent intent = new Intent();
                intent.putExtra("URI", imageUri);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "No image is set to show", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
