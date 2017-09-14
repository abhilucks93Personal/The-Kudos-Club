package com.viscocits.home;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.viscocits.home_challenge.ChallengeFragment;
import com.viscocits.home_kudos.KudosBankFragment;
import com.viscocits.home_post.PostFragment;
import com.viscocits.home_recognize.RecognizeFragment;
import com.viscocits.sample.BlankFragment;


/**
 * Created by Pawan on 3/9/2017.
 */

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount = 4;
    private final SparseArray<Fragment> registeredFragments = new SparseArray<>();

    Fragment frag;


    //Constructor to the class
    public ProfilePagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs

        switch (position) {

            case 0:

                return new PostFragment();

            case 1:

                return new RecognizeFragment();

            case 2:

                return new ChallengeFragment();

            case 3:

                return new KudosBankFragment();


            default:
                return null;
        }
    }

    //Overridden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "Post";

            case 1:
                return "Recognize";

            case 2:
                return "Challenges";

            case 3:
                return "Kudos Bank";

            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public SparseArray<Fragment> getRegisteredFragments() {
        return registeredFragments;
    }
}