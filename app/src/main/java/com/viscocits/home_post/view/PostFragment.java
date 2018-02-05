package com.viscocits.home_post.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.home_post.controller.PostAdapter;
import com.viscocits.home_post.interfac.PostActionListener;
import com.viscocits.home_post.model.ModelResponseCountryFilterList;
import com.viscocits.home_post.model.ModelResponseCountryFilterListData;
import com.viscocits.home_post.model.ModelResponseEngagementFilterList;
import com.viscocits.home_post.model.ModelResponseEngagementFilterListData;
import com.viscocits.home_post.model.getPostResponse.ModelResponseWallPost;
import com.viscocits.home_post.model.postModels.ModelPostComments;
import com.viscocits.home_post.model.postModels.ModelPostMergedData;
import com.viscocits.retrofit.RetrofitApi;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import java.util.ArrayList;


/**
 * Created by ng on 2/12/2017.
 */
public class PostFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RetrofitApi.ResponseListener, PostActionListener {
    TextView tvNoRecordFound;
    RecyclerView rvPosts;

    private ArrayList<ModelPostMergedData> filteredPostDatas;
    PostAdapter mAdapterPosts;
    Spinner spinnerEngagement, spinnerCountry;
    private int pageNum = 1;
    private int engagementId = -1, countryId = -1;
    ArrayList<ModelResponseEngagementFilterListData> engagementList = new ArrayList<>();
    ArrayList<ModelResponseCountryFilterListData> countryList = new ArrayList<>();
    ProgressBar progressBar, progressBarPagination;
    private ArrayAdapter<ModelResponseEngagementFilterListData> engagementAdapter;
    private ArrayAdapter<ModelResponseCountryFilterListData> countryAdapter;
    private boolean loading;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout activity_main_swipe_refresh_layout;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

      //  boolean isPostUpdated = Utility.getPreferences(getContext(), Constants.isPostUpdated, false);
        if (isVisibleToUser &&  engagementList.size() > 0) {
            pageNum = 1;
            getWallPostData();
        }
    }

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

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBarPagination = (ProgressBar) view.findViewById(R.id.progress_bar_pagination);
        spinnerEngagement = (Spinner) view.findViewById(R.id.spinner_post_type);
        spinnerEngagement.setTag("engagement");
        spinnerEngagement.setOnItemSelectedListener(this);
        spinnerCountry = (Spinner) view.findViewById(R.id.spinner_post_country);
        spinnerCountry.setTag("country");
        spinnerCountry.setOnItemSelectedListener(this);
        activity_main_swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        activity_main_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void initUi() {

        filteredPostDatas = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvPosts.setLayoutManager(mLayoutManager);
        mAdapterPosts = new PostAdapter(getActivity(), this, R.layout.item_post, filteredPostDatas);
        rvPosts.setAdapter(mAdapterPosts);
        rvPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        pageNum++;
                        getWallPostData();
                    }
                }


            }
        });

        getEngagementFilterList();


    }

    private void getEngagementFilterList() {
        Utility.addPreferences(getActivity(), Constants.isPostUpdated, false);
        RetrofitApi.getInstance().getEngagementFilterList(getActivity(), this);
    }

    private void getCountryFilterList() {
        RetrofitApi.getInstance().getCountryFilterList(getActivity(), this);
    }

    private void getWallPostData() {
        if (pageNum == 1) {
            progressBar.setVisibility(View.VISIBLE);
            progressBarPagination.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            progressBarPagination.setVisibility(View.GONE);
        }

        RetrofitApi.getInstance().getWallPostData(getActivity(),
                this,
                engagementId,
                countryId,
                Constants.PAGE_SIZE,
                pageNum);

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

            case "engagement":
                engagementId = engagementList.get(position).getEngagement_Id();
                pageNum = 1;
                getWallPostData();
                break;

            case "country":
                countryId = countryList.get(position).getCountry_Id();
                pageNum = 1;
                getWallPostData();
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public boolean shouldScroll() {

        LinearLayoutManager layoutManager = (LinearLayoutManager) rvPosts
                .getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);

        if (!isLastItemDisplaying(rvPosts)) {
            rvPosts.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rvPosts.smoothScrollToPosition(0);

                }
            }, 1000);
            return false;
        } else
            return true;
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == 0)
                return true;
        }
        return false;
    }

    @Override
    public void _onCompleted() {
        progressBarPagination.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void _onError(Throwable e) {
        progressBarPagination.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void _onNext(Object obj) {

        if (obj instanceof ModelResponseEngagementFilterList) {
            ModelResponseEngagementFilterList engagementFilterList = (ModelResponseEngagementFilterList) obj;
            if (engagementFilterList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                updateEngagementList(engagementFilterList.getData());

            }
        } else if (obj instanceof ModelResponseCountryFilterList) {
            ModelResponseCountryFilterList countryFilterList = (ModelResponseCountryFilterList) obj;
            if (countryFilterList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                updateCountryList(countryFilterList.getData());
            }
        } else if (obj instanceof ModelResponseWallPost) {
            Utility.addPreferences(getActivity(), Constants.isPostUpdated, false);
            loading = true;
            progressBarPagination.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            ModelResponseWallPost responseWallPost = (ModelResponseWallPost) obj;
            if (responseWallPost.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {

                ArrayList<ModelPostMergedData> postDatas = new ArrayList<>();
                postDatas.addAll(Utility.getMergedPostData(responseWallPost.getData()));

                if (postDatas.size() == 0) {
                    tvNoRecordFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoRecordFound.setVisibility(View.GONE);
                }

                if (pageNum == 1)
                    filteredPostDatas.clear();


                filteredPostDatas.addAll(postDatas);
                mAdapterPosts.notifyDataSetChanged();


            }
        }
    }

    private void updateEngagementList(ArrayList<ModelResponseEngagementFilterListData> data) {

        engagementList.addAll(data);
        if (engagementList.size() > 0) {
            engagementId = engagementList.get(0).getEngagement_Id();
        }

        engagementAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, engagementList);
        spinnerEngagement.setAdapter(engagementAdapter);

        getCountryFilterList();
    }

    private void updateCountryList(ArrayList<ModelResponseCountryFilterListData> data) {

        countryList.addAll(data);
        if (countryList.size() > 0) {
            countryId = countryList.get(0).getCountry_Id();
        }

        countryAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, countryList);
        spinnerCountry.setAdapter(countryAdapter);

        getWallPostData();
    }

    @Override
    public void onPostLikeAction(int pos) {
        if (pos < filteredPostDatas.size()) {
            int likeCount = filteredPostDatas.get(pos).getLIKECOUNT();
            if (filteredPostDatas.get(pos).getLIKEDBYUSER() && likeCount > 0)
                likeCount--;
            else
                likeCount++;
            filteredPostDatas.get(pos).setLIKECOUNT(likeCount);

            filteredPostDatas.get(pos).setLIKEDBYUSER(!filteredPostDatas.get(pos).getLIKEDBYUSER());
            mAdapterPosts.notifyDataSetChanged();
        }

    }

    @Override
    public void onPostSpamAction(int pos) {
        if (pos < filteredPostDatas.size()) {
            filteredPostDatas.get(pos).setSPAMEDBYUSER(true);
            mAdapterPosts.notifyDataSetChanged();
        }
    }

    @Override
    public void onPostComment(int pos, ArrayList<ModelPostComments> modelPostComments) {
        if (pos < filteredPostDatas.size()) {
            filteredPostDatas.get(pos).setCommentsList(modelPostComments);
            filteredPostDatas.get(pos).setCOMMENTCOUNT(modelPostComments.size());
            mAdapterPosts.notifyDataSetChanged();
        }
    }

    private void refreshContent() {
     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3000);*/
        if (isAdded()) {
            activity_main_swipe_refresh_layout.setRefreshing(false);
            if (progressBar.getVisibility() == View.GONE) {
                pageNum = 1;
                getWallPostData();
            }
        }
    }


}
