package com.example.ahmedb2.calcounter.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ahmedb2.calcounter.Fragments.Daily;
import com.example.ahmedb2.calcounter.Fragments.Exercise;
import com.example.ahmedb2.calcounter.Fragments.FoodItem;
import com.example.ahmedb2.calcounter.Fragments.Goal;
import com.example.ahmedb2.calcounter.Fragments.HeartRate;
import com.example.ahmedb2.calcounter.Fragments.Weekly;

/**
 * Created by payal on 4/30/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Food", "Exercise", "Heart Rate", "Goal", "Daily", "Weekly"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FoodItem();
            case 1:
                return new Exercise();
            case 2:
                return new HeartRate();
            case 3:
                return new Goal();
            case 4:
                return new Daily();
            case 5:
                return new Weekly();
        }
        return new FoodItem();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
