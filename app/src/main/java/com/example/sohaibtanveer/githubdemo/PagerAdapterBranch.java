package com.example.sohaibtanveer.githubdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterBranch extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterBranch(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BranchesFragment tab1 = new BranchesFragment();
                return tab1;
            case 1:
                TagsFragment tab2 = new TagsFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
