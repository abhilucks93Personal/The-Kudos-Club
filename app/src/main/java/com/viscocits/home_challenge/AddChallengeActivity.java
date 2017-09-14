package com.viscocits.home_challenge;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viscocits.R;
import com.viscocits.navigation.MainActivity;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class AddChallengeActivity extends AppCompatActivity implements View.OnClickListener {

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    RecyclerView recyclerView;
    RelativeLayout rlInfo;
    int challengeTag;
    TextView tvSubTitle, tvAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.layout_bg_toolbar);
        getSupportActionBar().setBackgroundDrawable(d);

        findViewById();

        initUi();


    }

    private void initUi() {

        challengeTag = getIntent().getIntExtra("tag", -1);

        if (challengeTag == -1) {
            finish();
        } else if (challengeTag == 0) {
            setTitle("Ownership");
            tvSubTitle.setText("We are all captains of this ship.");
            //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_ownership));
        } else if (challengeTag == 1) {
            setTitle("Dynamic");
            tvSubTitle.setText("We never stop raising the bar.");
            //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_dynamic));
        } else if (challengeTag == 2) {
            setTitle("Collaboration");
            tvSubTitle.setText("We play the same game.");
            //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_collaboration));
        } else if (challengeTag == 3) {
            setTitle("Transparency");
            tvSubTitle.setText("Say things as they are.");
            //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_transparency));
        }


        recyclerView.setHasFixedSize(true);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        List<ModelAddChallengeData> data = getListItemData();

        ChallengesViewAdapter rcAdapter = new ChallengesViewAdapter(this, data);
        recyclerView.setAdapter(rcAdapter);
    }

    private void findViewById() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvSubTitle = (TextView) findViewById(R.id.tv_sub_title);
        rlInfo = (RelativeLayout) findViewById(R.id.rl_info);
        rlInfo.setOnClickListener(this);
        tvAdd = (TextView) findViewById(R.id.tv_add_to_dashboard);
        tvAdd.setOnClickListener(this);
    }

    private List<ModelAddChallengeData> getListItemData() {

        List<ModelAddChallengeData> listViewItems = new ArrayList<>();
        listViewItems.add(new ModelAddChallengeData("Organise a surprise for your team", R.drawable.challenge_logo_enabled));
        listViewItems.add(new ModelAddChallengeData("Give an insight into your internal career in GVC and motivate others.", R.drawable.challenge_logo_enabled));
        listViewItems.add(new ModelAddChallengeData("Organize a birthday thingy for a team member.", R.drawable.challenge_logo_enabled));
        listViewItems.add(new ModelAddChallengeData("Think up an idea and present it to your team", R.drawable.challenge_logo_enabled));
        listViewItems.add(new ModelAddChallengeData("Make a list of your responsibilities or projects you are working on and make it visible on your work desk/avinity profile.", R.drawable.challenge_logo_enabled));
        listViewItems.add(new ModelAddChallengeData("Present your hobby to a colleague", R.drawable.challenge_logo_enabled));

        return listViewItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        if (challengeTag == 0) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_ownership));
        } else if (challengeTag == 1) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_dynamic));
        } else if (challengeTag == 2) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_collaboration));
        } else if (challengeTag == 3) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logo_transparency));
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void showChallengeInfoDialog(Activity activity) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_recognize_info, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog infoDialog = dialogBuilder.create();
        infoDialog.show();

        TextView tvContent = (TextView) dialogView.findViewById(R.id.dialog_content);
        tvContent.setText(activity.getResources().getString(R.string.challenge_info));

        TextView tvSubmit = (TextView) dialogView.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_info:

                showChallengeInfoDialog(AddChallengeActivity.this);
                break;

            case R.id.tv_add_to_dashboard:

                Utility.showToast(AddChallengeActivity.this, Constants.MSG_UNDER_CONSTRUCTION);
                break;
        }
    }
}
