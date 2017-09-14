package com.viscocits.home_post;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viscocits.R;
import com.viscocits.other.CircleTransform;
import com.viscocits.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Activity context;
    private List<ModelPostData> postDataList;
    private int mLayoutResourceId;

    private int selectedPostType = 0, selectedPostCountry = 0;

    public void refreshData(int selectedPostType, int selectedPostCountry) {
        this.selectedPostType = selectedPostType;
        this.selectedPostCountry = selectedPostCountry;
        //   notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDuration, tvContent1, tvContent2, tvLike, tvLikeCount, tvComments;
        ImageView ivProfile, ivLogo, ivChallenge;
        RecyclerView rvComments;
        LinearLayout llLiked;
        View convertView;


        MyViewHolder(View view) {
            super(view);
            convertView = view;

            ivProfile = (ImageView) view.findViewById(R.id.iv_profile);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            ivLogo = (ImageView) view.findViewById(R.id.iv_type_logo);
            ivChallenge = (ImageView) view.findViewById(R.id.iv_main_image);
            tvContent1 = (TextView) view.findViewById(R.id.tv_content1);
            tvContent2 = (TextView) view.findViewById(R.id.tv_content2);

            tvLike = (TextView) view.findViewById(R.id.tv_like);
            llLiked = (LinearLayout) view.findViewById(R.id.ll_liked);
            tvLikeCount = (TextView) view.findViewById(R.id.tv_like_count);
            tvComments = (TextView) view.findViewById(R.id.tv_comments);

            rvComments = (RecyclerView) view.findViewById(R.id.rv_comments);
        }
    }


    public PostAdapter(Activity context, int mLayoutResourceId, List<ModelPostData> postDataList) {
        this.postDataList = postDataList;
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
        final ModelPostData postData = postDataList.get(position);


        holder.tvName.setText(postData.getName());

        holder.tvDuration.setText(postData.getPostTime());
        holder.tvDuration.setBackgroundColor(Utility.getPostColor(context, postData.getType()));

        SpannableString strContent1
                = new SpannableString(postData.getName() + " has completed a challenge - ");
        strContent1.setSpan(new StyleSpan(Typeface.BOLD), 0, postData.getName().length(), 0);
        holder.tvContent1.setText(strContent1);

        holder.tvContent2.setText(postData.getChallenge());

        Glide.with(context).load(postData.getProfileImage())
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.image_thumb)
                .error(R.drawable.image_thumb)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivProfile);

        Glide.with(context).load(postData.getChallengeImage())
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.color.color_light_grey)
                .error(R.color.color_light_grey)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivChallenge);

        holder.ivLogo.setImageDrawable(Utility.getPostLogo(context, postData.getType()));


        if (postData.getLikeCount().equals("0") || postData.getLikeCount().equals("")) {
            holder.tvLike.setVisibility(View.VISIBLE);
            holder.llLiked.setVisibility(View.GONE);
        } else {
            holder.tvLike.setVisibility(View.GONE);
            holder.llLiked.setVisibility(View.VISIBLE);
            holder.tvLikeCount.setText(postData.getLikeCount());
        }

        if (postData.getComments().size() > 0)
            holder.tvComments.setText("" + postData.getComments().size() + " Comment(s)");

        ArrayList<ModelCommentsData> commentDatas = new ArrayList<>();
        commentDatas.addAll(postData.getComments());
        CommentsAdapter mAdapterComments = new CommentsAdapter(context, R.layout.item_comments, commentDatas);
        holder.rvComments.setAdapter(mAdapterComments);


        holder.tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataList.get(position).setLikeCount("1");
                notifyItemChanged(position);
            }
        });

        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }


}