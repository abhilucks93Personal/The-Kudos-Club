package com.viscocits.home_post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.utils.Utility;

import java.util.ArrayList;


/**
 * Created by ng on 2/12/2017.
 */
public class PostFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView tvNoRecordFound;
    RecyclerView rvPosts;
    ArrayList<ModelPostData> completePostDatas;

    ArrayList<ModelPostData> filteredPostDatas;

    PostAdapter mAdapterPosts;
    Spinner spinnerType, spinnerCountry;
    int selectedPostType = 0, selectedPostCountry = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        findViewById(view);

        initUi();

        return view;
    }

    private void findViewById(View view) {

        tvNoRecordFound = (TextView) view.findViewById(R.id.tv_no_record_found);
        rvPosts = (RecyclerView) view.findViewById(R.id.rv_posts);

        spinnerType = (Spinner) view.findViewById(R.id.spinner_post_type);
        spinnerType.setTag("type");
        spinnerType.setOnItemSelectedListener(this);
        spinnerCountry = (Spinner) view.findViewById(R.id.spinner_post_country);
        spinnerCountry.setTag("country");
        spinnerCountry.setOnItemSelectedListener(this);

    }

    private void initUi() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(mLayoutManager);
        completePostDatas = new ArrayList<>();
        completePostDatas.addAll(Utility.getPostDummyData());

        filteredPostDatas = new ArrayList<>();
        filteredPostDatas.addAll(completePostDatas);
        mAdapterPosts = new PostAdapter(getActivity(), R.layout.item_post, filteredPostDatas);
        rvPosts.setAdapter(mAdapterPosts);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String tag = (String) parent.getTag();
        switch (tag) {

            case "type":
                selectedPostType = position;
                mAdapterPosts.refreshData(selectedPostType, selectedPostCountry);
                filteredPostDatas.clear();
                filteredPostDatas.addAll(Utility.getFIlteredPost(completePostDatas, "" + selectedPostType, "" + selectedPostCountry));
                mAdapterPosts.notifyDataSetChanged();
                break;

            case "country":
                selectedPostCountry = position;
                mAdapterPosts.refreshData(selectedPostType, selectedPostCountry);
                filteredPostDatas.clear();
                filteredPostDatas.addAll(Utility.getFIlteredPost(completePostDatas, "" + selectedPostType, "" + selectedPostCountry));
                mAdapterPosts.notifyDataSetChanged();
                break;

        }

        if (filteredPostDatas.size() == 0) {
            tvNoRecordFound.setVisibility(View.VISIBLE);
        } else {
            tvNoRecordFound.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public boolean shouldScroll() {

      /*  LinearLayoutManager layoutManager = (LinearLayoutManager) rvPosts
                .getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);

        if (!isLastItemDisplaying(rvPosts))
            rvPosts.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rvPosts.smoothScrollToPosition(0);

                }
            }, 1000);
*/
        return false;
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == 0)
                return true;
        }
        return false;
    }
}
