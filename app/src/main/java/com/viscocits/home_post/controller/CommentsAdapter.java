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
import com.viscocits.R;
import com.viscocits.home_post.model.ModelCommentsData;
import com.viscocits.home_post.model.postModels.ModelPostComments;
import com.viscocits.other.CircleTransform;
import com.viscocits.utils.Utility;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private Activity context;
    private List<ModelPostComments> commentsDataList;
    private int mLayoutResourceId;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvComment, tvDuration;
        ImageView ivProfile;
        View convertView;


        MyViewHolder(View view) {
            super(view);
            convertView = view;

            ivProfile = (ImageView) view.findViewById(R.id.iv_profile);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvComment = (TextView) view.findViewById(R.id.tv_comment);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);

        }
    }


    public CommentsAdapter(Activity context, int mLayoutResourceId, List<ModelPostComments> commentsDataList) {
        this.commentsDataList = commentsDataList;
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
        final ModelPostComments commentsData = commentsDataList.get(position);


        holder.tvName.setText(commentsData.getName());

        holder.tvComment.setText(commentsData.getMessage());

        holder.tvDuration.setText(Utility.getFormattedPostDate(commentsData.getCommentedDate()));


        Glide.with(context).load(commentsData.getAvatarExt())
                .crossFade()
                .thumbnail(0.5f)
                .error(R.drawable.image_thumb)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivProfile);

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
        return commentsDataList.size();
    }


}