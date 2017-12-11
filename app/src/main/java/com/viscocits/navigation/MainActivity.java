package com.viscocits.navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viscocits.R;
import com.viscocits.home.HomeFragment;
import com.viscocits.login.LoginActivity;
import com.viscocits.other.CircleTransform;
import com.viscocits.sample.BlankFragment;
import com.viscocits.support.SupportFragment;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtDesignation;
    private Toolbar toolbar;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "http://wallpapercave.com/wp/f5elcfO.jpg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "TAG_HOME";
    private static final String TAG_PROFILE = "TAG_PROFILE";
    private static final String TAG_TEAM_MEMBER = "TAG_TEAM_MEMBER";
    private static final String TAG_SEARCH_TEAM = "TAG_SEARCH_TEAM";
    private static final String TAG_NOTICE = "TAG_NOTICE";
    private static final String TAG_VIDEO_VAULT = "TAG_VIDEO_VAULT";
    private static final String TAG_NOMINATED = "TAG_NOMINATED";
    private static final String TAG_RECOGNITION = "TAG_RECOGNITION";
    private static final String TAG_SUPPORT = "TAG_SUPPORT";
    public static String CURRENT_TAG = TAG_HOME;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private TextView tvNavTitle;
    LinearLayout llSearchView;
    String[] arrayTitles;
    private EditText etSearchView;
    private ImageView ivNotifications;
    private TextView txtLastLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.setStatusBarTranslucent(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utility.addPreferences(this, Constants.keyUserId, "216");
        Utility.addPreferences(this, Constants.keyUniqueDeviceId, "uniqueDeviceId");
        Utility.addPreferences(this, Constants.keyDeviceId, "DeviceId");

        findViewById();

        initUi(savedInstanceState);


    }


    private void findViewById() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        tvNavTitle = (TextView) findViewById(R.id.nav_title);
        llSearchView = (LinearLayout) findViewById(R.id.ll_searchView);
        etSearchView = (EditText) findViewById(R.id.et_search_user);
        etSearchView.setOnClickListener(this);

        ivNotifications = (ImageView) findViewById(R.id.iv_notifications);
        ivNotifications.setOnClickListener(this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtDesignation = (TextView) navHeader.findViewById(R.id.designation);
        txtLastLogin = (TextView) navHeader.findViewById(R.id.last_login);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
    }

    private void initUi(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mHandler = new Handler();

        // load nav menu header data
        loadNavHeader();

        arrayTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void loadNavHeader() {
        // name, website
        txtName.setText("Anshul Jha");
        txtDesignation.setText("Technical United Kingdom");
        txtLastLogin.setText("Last Login : 09 Aug 2017");
        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        //   navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void loadHomeFragment() {

        selectNavMenu();


        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }


        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                setNavTitle();
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.postDelayed(mPendingRunnable, 100);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {

        Fragment fragment;
        switch (navItemIndex) {
            case 0:
                // home

                tvNavTitle.setVisibility(View.GONE);
                llSearchView.setVisibility(View.VISIBLE);
                fragment = new HomeFragment();
                break;

            case 8:
                // support
                tvNavTitle.setVisibility(View.VISIBLE);
                llSearchView.setVisibility(View.GONE);
                fragment = new SupportFragment();
                break;


            default:
                tvNavTitle.setVisibility(View.VISIBLE);
                llSearchView.setVisibility(View.GONE);

                fragment = new BlankFragment();
                break;
        }

        return fragment;
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    case R.id.nav_my_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

                    case R.id.nav_team_member:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TEAM_MEMBER;
                        break;

                    case R.id.nav_search_team:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SEARCH_TEAM;
                        break;

                    case R.id.nav_notice:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_NOTICE;
                        break;

                    case R.id.nav_video_vault:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_VIDEO_VAULT;
                        break;

                    case R.id.nav_most_nominated:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_NOMINATED;
                        break;

                    case R.id.nav_recent_recognition:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_RECOGNITION;
                        break;

                    case R.id.nav_support:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_SUPPORT;
                        break;

                    case R.id.nav_logout:
                        showLogoutDialog();
                        break;


                }

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.sidebar_navigation, getTheme());

        actionBarDrawerToggle.setHomeAsUpIndicator(drawable);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setNavTitle() {
        tvNavTitle.setText(arrayTitles[navItemIndex]);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
        if (homeFragment != null && homeFragment.isVisible()) {
            if (!homeFragment.shouldScroll()) {
                super.onBackPressed();
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.hideKeyboard(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.et_search_user:

                Utility.showToast(MainActivity.this, Constants.MSG_UNDER_CONSTRUCTION);

                break;

            case R.id.iv_notifications:

                Utility.showToast(MainActivity.this, Constants.MSG_UNDER_CONSTRUCTION);

                break;
        }
    }

    private void showLogoutDialog() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to Logout ?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utility.addPreferences(MainActivity.this, Constants.keyLoginCheck, false);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("anim", false);
                        startActivity(intent);
                        dialog.dismiss();
                        finishAffinity();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
