package com.viscocits.home_recognize;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;


/**
 * Created by ng on 2/12/2017.
 */
public class RecognizeFragment extends Fragment implements View.OnClickListener {
    TextView tvNoRecordFound, tvSubmit;
    private AutoCompleteTextView actSearch;
    private RelativeLayout rl_info;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recognize, container, false);

        findViewById(view);

        initUi();

        return view;
    }

    private void findViewById(View view) {

        actSearch = (AutoCompleteTextView) view.findViewById(R.id.act_search);
        tvSubmit = (TextView) view.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(this);
        rl_info = (RelativeLayout) view.findViewById(R.id.rl_info);
        rl_info.setOnClickListener(this);

    }

    private void initUi() {

        String[] countries = getResources().getStringArray(R.array.users_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_list_item_1, countries);
        actSearch.setAdapter(adapter);
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

}
