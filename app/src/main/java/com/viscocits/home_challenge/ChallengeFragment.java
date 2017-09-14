package com.viscocits.home_challenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.viscocits.R;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import java.util.ArrayList;


/**
 * Created by ng on 2/12/2017.
 */
public class ChallengeFragment extends Fragment implements View.OnClickListener, FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
    TextView tvNoRecordFound;
    RecyclerView rvChallenge;
    ChallengeAdapter mAdapterChallenge;
    ArrayList<ModelChallengeData> challengeDatas;
    FloatingActionsMenu floatingActionMenu;
    View viewWhiteBlur;
    FloatingActionButton actionDynamic, actionOwnership, actionCollaboration, actionTransparency, actionSuggest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);

        findViewById(view);

        initUi();

        return view;
    }

    private void findViewById(View view) {

        tvNoRecordFound = (TextView) view.findViewById(R.id.tv_no_record_found);
        rvChallenge = (RecyclerView) view.findViewById(R.id.rv_challenges);
        viewWhiteBlur = view.findViewById(R.id.view_white_blur);
        viewWhiteBlur.setOnClickListener(this);
        floatingActionMenu = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        floatingActionMenu.setOnFloatingActionsMenuUpdateListener(this);

        actionDynamic = (FloatingActionButton) view.findViewById(R.id.action_dynamic);
        actionDynamic.setOnClickListener(this);
        actionOwnership = (FloatingActionButton) view.findViewById(R.id.action_ownership);
        actionOwnership.setOnClickListener(this);
        actionCollaboration = (FloatingActionButton) view.findViewById(R.id.action_collaboration);
        actionCollaboration.setOnClickListener(this);
        actionTransparency = (FloatingActionButton) view.findViewById(R.id.action_transparency);
        actionTransparency.setOnClickListener(this);
        actionSuggest = (FloatingActionButton) view.findViewById(R.id.action_suggest);
        actionSuggest.setOnClickListener(this);

    }

    private void initUi() {

        challengeDatas = new ArrayList<>();
        mAdapterChallenge = new ChallengeAdapter(getActivity(), R.layout.item_challenge, challengeDatas, 3);
        rvChallenge.setAdapter(mAdapterChallenge);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        floatingActionMenu.collapse();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.view_white_blur:
                floatingActionMenu.collapse();
                break;

            case R.id.action_ownership:
                startActivityForResult(new Intent(getActivity(), AddChallengeActivity.class)
                        .putExtra("tag", 0), 101);
                floatingActionMenu.collapse();
                break;

            case R.id.action_dynamic:
                startActivityForResult(new Intent(getActivity(), AddChallengeActivity.class)
                        .putExtra("tag", 1), 101);
                floatingActionMenu.collapse();
                break;

            case R.id.action_collaboration:
                startActivityForResult(new Intent(getActivity(), AddChallengeActivity.class)
                        .putExtra("tag", 2), 101);
                floatingActionMenu.collapse();
                break;

            case R.id.action_transparency:
                startActivityForResult(new Intent(getActivity(), AddChallengeActivity.class)
                        .putExtra("tag", 3), 101);
                floatingActionMenu.collapse();
                break;

            case R.id.action_suggest:
                floatingActionMenu.collapse();
                Utility.showToast(getActivity(), Constants.MSG_UNDER_CONSTRUCTION);
                break;

        }
    }

    @Override
    public void onMenuExpanded() {
        viewWhiteBlur.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMenuCollapsed() {
        viewWhiteBlur.setVisibility(View.GONE);
    }
}
