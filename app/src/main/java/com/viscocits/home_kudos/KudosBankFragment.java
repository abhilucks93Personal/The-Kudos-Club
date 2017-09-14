package com.viscocits.home_kudos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;


/**
 * Created by ng on 2/12/2017.
 */
public class KudosBankFragment extends Fragment implements View.OnClickListener {
    LinearLayout llMore;
    RelativeLayout rlMore;
    TextView tvMyKudos, tvTeamKudos, tvGlobalKudos, tvDetails, tvPointsAwarded;
    TextView tvTitle, tvContent;
    View viewBarRecognize, viewBarOwnership, viewBarDynamic, viewBarCollaboration, viewBarTransparency;
    LinearLayout llBarRecognize, llBarOwnership, llBarDynamic, llBarCollaboration, llBarTransparency;
    TextView tvBarRecognize, tvBarOwnership, tvBarDynamic, tvBarCollaboration, tvBarTransparency, tvEngagement, tvExceptionalPoints;
    int graphMultiplyConstant = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kudos_bank, container, false);

        findViewById(view);

        initGraphView();

        return view;
    }

    private void initGraphView() {
        llBarRecognize.setLayoutParams(new LinearLayout.LayoutParams(30 * graphMultiplyConstant, 104 * graphMultiplyConstant));
        llBarOwnership.setLayoutParams(new LinearLayout.LayoutParams(30 * graphMultiplyConstant, 104 * graphMultiplyConstant));
        llBarDynamic.setLayoutParams(new LinearLayout.LayoutParams(30 * graphMultiplyConstant, 104 * graphMultiplyConstant));
        llBarCollaboration.setLayoutParams(new LinearLayout.LayoutParams(30 * graphMultiplyConstant, 104 * graphMultiplyConstant));
        llBarTransparency.setLayoutParams(new LinearLayout.LayoutParams(30 * graphMultiplyConstant, 104 * graphMultiplyConstant));

        tvMyKudos.performClick();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAnimation() {

        int centerX = llMore.getRight();
        int centerY = llMore.getTop();
        int startRadius = 0;
        int endRadius = (int) Math
                .hypot(llMore.getWidth(), llMore.getHeight());

        if (llMore.getVisibility() == View.VISIBLE) {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(llMore, centerX, centerY, endRadius, startRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    llMore.setVisibility(View.GONE);
                }
            });
            anim.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(llMore, centerX, centerY, startRadius, endRadius);
            llMore.setVisibility(View.VISIBLE);
            anim.start();
        }


    }

    private void findViewById(View view) {

        tvMyKudos = (TextView) view.findViewById(R.id.tv_my_kudos);
        tvMyKudos.setOnClickListener(this);
        tvTeamKudos = (TextView) view.findViewById(R.id.tv_team_kudos);
        tvTeamKudos.setOnClickListener(this);
        tvGlobalKudos = (TextView) view.findViewById(R.id.tv_global_kudos);
        tvGlobalKudos.setOnClickListener(this);
        tvDetails = (TextView) view.findViewById(R.id.tv_details);
        tvDetails.setOnClickListener(this);
        tvPointsAwarded = (TextView) view.findViewById(R.id.tv_points_awarded);
        tvPointsAwarded.setOnClickListener(this);

        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);

        llMore = (LinearLayout) view.findViewById(R.id.ll_more_view);
        rlMore = (RelativeLayout) view.findViewById(R.id.rl_more);
        rlMore.setOnClickListener(this);

        viewBarRecognize = view.findViewById(R.id.view_bar_recognize);
        viewBarOwnership = view.findViewById(R.id.view_bar_ownership);
        viewBarDynamic = view.findViewById(R.id.view_bar_dynamic);
        viewBarCollaboration = view.findViewById(R.id.view_bar_collaboration);
        viewBarTransparency = view.findViewById(R.id.view_bar_transparency);

        llBarRecognize = (LinearLayout) view.findViewById(R.id.ll_bar_recognize);
        llBarOwnership = (LinearLayout) view.findViewById(R.id.ll_bar_ownership);
        llBarDynamic = (LinearLayout) view.findViewById(R.id.ll_bar_dynamic);
        llBarCollaboration = (LinearLayout) view.findViewById(R.id.ll_bar_collaboration);
        llBarTransparency = (LinearLayout) view.findViewById(R.id.ll_bar_transparency);

        tvBarRecognize = (TextView) view.findViewById(R.id.tv_bar_recognize);
        tvBarOwnership = (TextView) view.findViewById(R.id.tv_bar_ownership);
        tvBarDynamic = (TextView) view.findViewById(R.id.tv_bar_dynamic);
        tvBarCollaboration = (TextView) view.findViewById(R.id.tv_bar_collaboration);
        tvBarTransparency = (TextView) view.findViewById(R.id.tv_bar_transparency);

        tvEngagement = (TextView) view.findViewById(R.id.tv_engagement);
        tvExceptionalPoints = (TextView) view.findViewById(R.id.tv_exceptional_points);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_more:
                setAnimation();
                break;

            case R.id.tv_my_kudos:
                tvTitle.setText("My Kudos");
                tvContent.setText(getResources().getString(R.string.content_my_kudos));
                tvEngagement.setText("My overall engagement : 8.59%");
                tvExceptionalPoints.setText("Exceptional Points Earned : 0");
                setGraphData(6.25, 18.75, 0, 0, 0);
                break;

            case R.id.tv_team_kudos:
                tvTitle.setText("Team Kudos");
                tvContent.setText(getResources().getString(R.string.content_team_kudos));
                tvEngagement.setText("Team overall engagement : 19.28%");
                tvExceptionalPoints.setText("");
                setGraphData(16.67, 27.67, 35.83, 14.17, 7.92);
                break;

            case R.id.tv_global_kudos:
                tvTitle.setText("Global Kudos");
                tvEngagement.setText("Company overall engagement : 0.27%");
                tvExceptionalPoints.setText("");
                setGraphData(0, 0, 0, 0, 0);
                tvContent.setText("");
                break;

            case R.id.tv_details:
                Utility.showToast(getActivity(), Constants.MSG_UNDER_CONSTRUCTION);
                break;

            case R.id.tv_points_awarded:
                Utility.showToast(getActivity(), Constants.MSG_UNDER_CONSTRUCTION);
                break;
        }
    }

    private void setGraphData(double valueRecognize, double valueOwnership, double valueDynamic, double valueCollaboration, double valueTransparency) {
        ViewGroup.LayoutParams paramsRecognize = viewBarRecognize.getLayoutParams();
        paramsRecognize.height = (int) valueRecognize * graphMultiplyConstant;
        viewBarRecognize.requestLayout();
        tvBarRecognize.setText("" + valueRecognize + " %");

        ViewGroup.LayoutParams paramsOwnership = viewBarOwnership.getLayoutParams();
        paramsOwnership.height = (int) valueOwnership * graphMultiplyConstant;
        viewBarOwnership.requestLayout();
        tvBarOwnership.setText("" + valueOwnership + " %");

        ViewGroup.LayoutParams paramsDynamic = viewBarDynamic.getLayoutParams();
        paramsDynamic.height = (int) valueDynamic * graphMultiplyConstant;
        viewBarDynamic.requestLayout();
        tvBarDynamic.setText("" + valueDynamic + " %");

        ViewGroup.LayoutParams paramsCollaboration = viewBarCollaboration.getLayoutParams();
        paramsCollaboration.height = (int) valueCollaboration * graphMultiplyConstant;
        viewBarCollaboration.requestLayout();
        tvBarCollaboration.setText("" + valueCollaboration + " %");

        ViewGroup.LayoutParams paramsTransparency = viewBarTransparency.getLayoutParams();
        paramsTransparency.height = (int) valueTransparency * graphMultiplyConstant;
        viewBarTransparency.requestLayout();
        tvBarTransparency.setText("" + valueTransparency + " %");

    }

}
