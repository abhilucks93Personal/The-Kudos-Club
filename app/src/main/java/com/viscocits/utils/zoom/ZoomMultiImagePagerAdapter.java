package com.viscocits.utils.zoom;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.viscocits.R;
import com.viscocits.utils.GlideHelper;
import com.viscocits.utils.zoom.settings.SettingsController;

import java.util.ArrayList;

class ZoomMultiImagePagerAdapter extends RecyclePagerAdapter<ZoomMultiImagePagerAdapter.ViewHolder> {

    private final ViewPager viewPager;
    private ArrayList<String> image_arraylist;
    private final SettingsController settingsController;
    Activity activity;

    ZoomMultiImagePagerAdapter(Activity activity, ViewPager pager, ArrayList<String> image_arraylist, SettingsController listener) {
        this.activity = activity;
        this.viewPager = pager;
        this.image_arraylist = image_arraylist;
        this.settingsController = listener;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {

        View layoutView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_zoom_image_item, null);
        return new ZoomMultiImagePagerAdapter.ViewHolder(layoutView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Applying settings from toolbar menu, see BaseExampleActivity

        // Applying custom settings
        holder.image.getController().getSettings()
                .setMaxZoom(6f)
                .setDoubleTapZoom(3f);

        // Enabling smooth scrolling when image panning turns into ViewPager scrolling.
        // Otherwise ViewPager scrolling will only be possible when image is in zoomed out state.
        holder.image.getController().enableScrollInViewPager(viewPager);
        holder.image.setBackgroundColor(activity.getResources().getColor(R.color.colorBlack));

        settingsController.apply(holder.image);

        GlideHelper.loadUrlFull(holder.image, holder.progressBar, image_arraylist.get(position));
    }

    @Override
    public void onRecycleViewHolder(@NonNull ViewHolder holder) {
        GlideHelper.clear(holder.image);
    }

    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final GestureImageView image;
        ProgressBar progressBar;

        ViewHolder(View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progressLoader);

        }
    }

}
