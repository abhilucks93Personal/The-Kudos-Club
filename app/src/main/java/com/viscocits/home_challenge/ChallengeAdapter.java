package com.viscocits.home_challenge;

import android.app.Activity;

import com.getbase.floatingactionbutton.FloatingActionButton;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import java.util.List;


public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.MyViewHolder> {

    private final int count;
    private Activity context;
    private List<ModelChallengeData> challengeDataList;
    private int mLayoutResourceId;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvAddEvidence;
        ImageView ivType;
        FloatingActionButton fab;
        LinearLayout llMain;
        View convertView;


        MyViewHolder(View view) {
            super(view);
            convertView = view;

            fab = (FloatingActionButton) view.findViewById(R.id.action_dummy);
            llMain = (LinearLayout) view.findViewById(R.id.ll_main);
            ivType = (ImageView) view.findViewById(R.id.iv_type);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvAddEvidence = (TextView) view.findViewById(R.id.tv_add_evidence);
        }
    }


    public ChallengeAdapter(Activity context, int mLayoutResourceId, List<ModelChallengeData> postDataList, int count) {
        this.challengeDataList = postDataList;
        this.mLayoutResourceId = mLayoutResourceId;
        this.context = context;
        this.count = count;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResourceId, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // final ModelPostData postData = postDataList.get(position);

        if (position == (count - 1)) {
            holder.fab.setVisibility(View.INVISIBLE);
        } else {
            holder.fab.setVisibility(View.GONE);
        }

        if (position == 1) {
            holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeTDynamic));
            holder.ivType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_person_black_24dp));
            holder.tvContent.setText("Make a video of yourself showing you are dynamic and post it on Avinity.");
        } else {
            if (position == 2) {
                holder.tvContent.setText("Organize a surprize for your team");
            }
            holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeOwnership));
            holder.ivType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_group_black_24dp));
        }


        holder.tvAddEvidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showToast(context, Constants.MSG_UNDER_CONSTRUCTION);
            }
        });

    }

    @Override
    public int getItemCount() {
        // return postDataList.size();
        return count;
    }


}