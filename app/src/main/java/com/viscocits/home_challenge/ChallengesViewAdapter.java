package com.viscocits.home_challenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viscocits.R;

import java.util.List;

public class ChallengesViewAdapter extends RecyclerView.Adapter<ChallengesViewAdapter.ChallengesViewHolders> {

    private List<ModelAddChallengeData> itemList;
    private Context context;

    public ChallengesViewAdapter(Context context, List<ModelAddChallengeData> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ChallengesViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_challenge, null);
        ChallengesViewHolders rcv = new ChallengesViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ChallengesViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    class ChallengesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView countryName;
        ImageView countryPhoto;
        LinearLayout llImageBg;

        ChallengesViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
            llImageBg = (LinearLayout) itemView.findViewById(R.id.ll_image_bg);
            llImageBg.setTag(R.drawable.layout_bg_challenges_item_normal);
        }

        @Override
        public void onClick(View view) {

            int resId = (Integer) llImageBg.getTag();

            if (resId == R.drawable.layout_bg_challenges_item_selected) {
                llImageBg.setBackground(context.getResources().getDrawable(R.drawable.layout_bg_challenges_item_normal));
                llImageBg.setTag(R.drawable.layout_bg_challenges_item_normal);
            } else {
                llImageBg.setBackground(context.getResources().getDrawable(R.drawable.layout_bg_challenges_item_selected));
                llImageBg.setTag(R.drawable.layout_bg_challenges_item_selected);
            }

            //  Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}