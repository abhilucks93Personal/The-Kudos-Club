package com.viscocits.home_post.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.viscocits.R;
import com.viscocits.home_post.model.postModels.ModelPostComments;
import com.viscocits.other.CircleTransform;
import com.viscocits.utils.Utility;

import java.util.List;


public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private Activity context;
    private List<String> imageUrls;
    private int mLayoutResourceId;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        View convertView;


        MyViewHolder(View view) {
            super(view);
            convertView = view;

            ivImage = (ImageView) view.findViewById(R.id.iv_image);


        }
    }


    public ImageListAdapter(Activity context, int mLayoutResourceId, List<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.mLayoutResourceId = mLayoutResourceId;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResourceId, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String imageUrl = imageUrls.get(position);


        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.color.colorBlack)
                .into(holder.ivImage);

        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // holder.tvRequest.setText("Request\nAccepted");
            }
        });

    }

    @Override
    public int getItemCount() {
        // return commentsDataList.size();
        return imageUrls.size();
    }


}