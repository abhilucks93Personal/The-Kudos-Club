package com.viscocits.support;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.utils.Constants;


/**
 * Created by ng on 2/12/2017.
 */
public class SupportFragment extends Fragment implements View.OnClickListener {
    TextView tvProgrammeOverview, tvProgrammeTC, tvFAQ, tv_contact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        findViewById(view);

        return view;
    }

    private void findViewById(View view) {
        tvProgrammeOverview = (TextView) view.findViewById(R.id.tv_programme_overview);
        tvProgrammeOverview.setOnClickListener(this);

        tvProgrammeTC = (TextView) view.findViewById(R.id.tv_programme_t_c);
        tvProgrammeTC.setOnClickListener(this);

        tvFAQ = (TextView) view.findViewById(R.id.tv_faq);
        tvFAQ.setOnClickListener(this);

        tv_contact = (TextView) view.findViewById(R.id.tv_contact);
        tv_contact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_programme_overview:
                startActivity(new Intent(getActivity(), SupportContentActivity.class)
                        .putExtra("content", Constants.PROGRAMME_OVERVIEW_CONTENT)
                        .putExtra("title", "Programme Overview"));
                break;

            case R.id.tv_programme_t_c:
                startActivity(new Intent(getActivity(), SupportContentActivity.class)
                        .putExtra("content", Constants.PROGRAMME_T_C_CONTENT)
                        .putExtra("title", "Programme T & C"));
                break;

            case R.id.tv_faq:
                startActivity(new Intent(getActivity(), SupportContentActivity.class)
                        .putExtra("content", Constants.FAQ)
                        .putExtra("title", "FAQ"));
                break;

            case R.id.tv_contact:
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
                break;
        }
    }

}
