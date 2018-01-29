package com.viscocits.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.viscocits.R;

public class GlideHelper {

    private GlideHelper() {
    }

    /**
     * Loads thumbnail.
     */
    public static void loadThumb(ImageView image, int thumbId) {
        // We don't want Glide to crop or resize our image
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL)
                .dontTransform();

        Glide.with(image)
                .load(thumbId)
                .apply(options)
                .into(image);
    }

    /**
     * Loads thumbnail and then replaces it with full image.
     */
    public static void loadFull(ImageView image, int imageId, int thumbId) {
        // We don't want Glide to crop or resize our image
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL)
                .dontTransform();

        final RequestBuilder<Drawable> thumbRequest = Glide.with(image)
                .load(thumbId)
                .apply(options);

        Glide.with(image)
                .load(imageId)
                .apply(options)
                .thumbnail(thumbRequest)
                .into(image);
    }

    public static void loadUrlFull(ImageView image, final ProgressBar progressBar, @Nullable String url) {
        // We don't want Glide to crop or resize our image
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.color.colorBlack)
                .dontTransform();


        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        RequestBuilder<Drawable> requestBuilder = Glide.with(image)
                .load(url);

        requestBuilder
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("imagee", "success");
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .load(url)
                .apply(options)
                .into(image);
    }

    public static void clear(ImageView view) {
        // Clearing current Glide request (if any)
        Glide.with(view).clear(view);
        // Cleaning up resources
        view.setImageDrawable(null);
    }

}
