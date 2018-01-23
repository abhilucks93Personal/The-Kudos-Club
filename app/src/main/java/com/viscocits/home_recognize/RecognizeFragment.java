package com.viscocits.home_recognize;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.home_recognize.model.ModelResponseKudosPoints;
import com.viscocits.home_recognize.model.ModelResponseKudosPointsData;
import com.viscocits.home_recognize.model.ModelResponsePointsList;
import com.viscocits.home_recognize.model.ModelResponsePointsListData;
import com.viscocits.home_recognize.model.ModelResponseReasonsList;
import com.viscocits.home_recognize.model.ModelResponseReasonsListData;
import com.viscocits.home_recognize.model.ModelResponseUsersList;
import com.viscocits.home_recognize.model.ModelResponseUsersListData;
import com.viscocits.home_recognize.model.ModelResponseValuesList;
import com.viscocits.home_recognize.model.ModelResponseValuesListData;
import com.viscocits.retrofit.RetrofitApi;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import java.util.ArrayList;


/**
 * Created by ng on 2/12/2017.
 */
public class RecognizeFragment extends Fragment implements View.OnClickListener, RetrofitApi.ResponseListener {
    TextView tvNoRecordFound, tvSubmit, tvKudosPoints;
    private AutoCompleteTextView actSearch;
    private RelativeLayout rl_info;
    private ProgressBar progressBar;
    private LinearLayout llMain;
    private ArrayList<ModelResponseReasonsListData> modelResponseReasonsListData;
    private ArrayList<ModelResponseValuesListData> modelResponseValuesListData;
    private ArrayList<ModelResponsePointsListData> modelResponsePointsListData;
    private Spinner spinnerReasons;
    private LinearLayout llValues;
    private RadioGroup rgPoints;
    private ArrayAdapter<ModelResponseReasonsListData> reasonAdapter;
    ArrayList<String> selectedValues = new ArrayList<>();
    private ModelResponseKudosPointsData modelResponseKudosPointsData;
    private ArrayList<ModelResponseUsersListData> modelResponseUsersListData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recognize, container, false);

        findViewById(view);

        initUi();

        return view;
    }

    private void findViewById(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        llMain = (LinearLayout) view.findViewById(R.id.ll_main);

        actSearch = (AutoCompleteTextView) view.findViewById(R.id.act_search);
        tvSubmit = (TextView) view.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(this);
        rl_info = (RelativeLayout) view.findViewById(R.id.rl_info);
        rl_info.setOnClickListener(this);

        spinnerReasons = (Spinner) view.findViewById(R.id.spinner_reasons);
        llValues = (LinearLayout) view.findViewById(R.id.ll_value);
        rgPoints = (RadioGroup) view.findViewById(R.id.rg_points);

        tvKudosPoints = (TextView) view.findViewById(R.id.tv_kudos_points);

    }

    private void initUi() {

        Utility.addPreferences(getActivity(), Constants.P_KEY_USER_ID, "11");

        getData();
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);

        getKudosPointsData();

    }

    private void getKudosPointsData() {
        RetrofitApi.getInstance().getKudosPoints(getActivity(), this);
    }

    private void getUsersListData() {
        RetrofitApi.getInstance().getUsersList(getActivity(), this);
    }

    private void getReasonsListData() {
        RetrofitApi.getInstance().getReasonsList(getActivity(), this);
    }

    private void getValuesListData() {
        RetrofitApi.getInstance().getValuesList(getActivity(), this);
    }

    private void getPointsListData() {
        RetrofitApi.getInstance().getPointsList(getActivity(), this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.rl_info:

                showRecognizeInfoDialog(getActivity());

                break;

            case R.id.tv_submit:
                Utility.showToast(getActivity(), Constants.MSG_UNDER_CONSTRUCTION);
                break;
        }
    }


    public static void showRecognizeInfoDialog(Activity activity) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_recognize_info, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog infoDialog = dialogBuilder.create();
        infoDialog.show();

        TextView tvSubmit = (TextView) dialogView.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });

    }

    @Override
    public void _onCompleted() {
       // progressBar.setVisibility(View.GONE);
    }

    @Override
    public void _onError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void _onNext(Object obj) {
        if (obj instanceof ModelResponseKudosPoints) {
            ModelResponseKudosPoints modelResponseKudosPoints = (ModelResponseKudosPoints) obj;
            if (modelResponseKudosPoints.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseKudosPointsData = modelResponseKudosPoints.getData();
                setKudosPointsData();
                getUsersListData();
            }
        } else if (obj instanceof ModelResponseUsersList) {
            ModelResponseUsersList modelResponseUsersList = (ModelResponseUsersList) obj;
            if (modelResponseUsersList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseUsersListData = modelResponseUsersList.getData();
                setUsersData();
                getReasonsListData();
            }
        } else if (obj instanceof ModelResponseReasonsList) {
            ModelResponseReasonsList modelResponseReasonsList = (ModelResponseReasonsList) obj;
            if (modelResponseReasonsList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseReasonsListData = modelResponseReasonsList.getData();
                setReasonsData();
                getValuesListData();
            }
        } else if (obj instanceof ModelResponseValuesList) {
            ModelResponseValuesList modelResponseValuesList = (ModelResponseValuesList) obj;
            if (modelResponseValuesList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseValuesListData = modelResponseValuesList.getData();
                setValuesData();
                getPointsListData();
            }
        } else if (obj instanceof ModelResponsePointsList) {
            ModelResponsePointsList modelResponsePointsList = (ModelResponsePointsList) obj;
            if (modelResponsePointsList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponsePointsListData = modelResponsePointsList.getData();
                setPointsData();
                progressBar.setVisibility(View.GONE);
                llMain.setVisibility(View.VISIBLE);
            }
        }
    }



    private void setKudosPointsData() {
        int kudosPoints = modelResponseKudosPointsData.getRecoRecivedBalanceTime() + modelResponseKudosPointsData.getInnovationRecivedBalanceTime();
        tvKudosPoints.setText("" + kudosPoints + " Points");
    }

    private void setUsersData() {

        ArrayAdapter<ModelResponseUsersListData> adapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_list_item_1, modelResponseUsersListData);
        actSearch.setAdapter(adapter);
    }

    int selectedPointId;

    private void setPointsData() {
        RadioGroup.LayoutParams rprms;
        int index = 0;
        for (final ModelResponsePointsListData listData : modelResponsePointsListData) {

            AppCompatRadioButton radioButton = new AppCompatRadioButton(getActivity());

            if (index == 0) {
                radioButton.setChecked(true);
            }
            radioButton.setId(index);
            radioButton.setHighlightColor(getResources().getColor(R.color.tab_background_selected));
            radioButton.setText(listData.getValueTitle());
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        selectedPointId = listData.getValue_Id();
                }
            });
            rprms = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.WRAP_CONTENT);
            rprms.weight = 1;
            rgPoints.addView(radioButton, rprms);
            index++;
        }
    }


    private void setValuesData() {
        int index = 0;
        for (final ModelResponseValuesListData listData : modelResponseValuesListData) {

            AppCompatCheckBox checkBox = new AppCompatCheckBox(getActivity());
            checkBox.setId(index);
            checkBox.setHighlightColor(getResources().getColor(R.color.tab_background_selected));
            checkBox.setText(listData.getRewardTitle());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        selectedValues.add(String.valueOf(listData.getReward_Id()));
                    else
                        selectedValues.remove(String.valueOf(listData.getReward_Id()));
                }
            });
            llValues.addView(checkBox);
            index++;
        }

    }

    private void setReasonsData() {
        reasonAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, modelResponseReasonsListData);
        spinnerReasons.setAdapter(reasonAdapter);
    }
}
