package com.cap.sfm.screeen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
ArrayList<Fragment> list;
FragmentManager fm;
Lifecycle lifecycle;


    public ViewPagerAdapter(ArrayList<Fragment> list,FragmentManager fm,Lifecycle lifecycle) {
        super(fm,lifecycle);
        this.fm = fm;
        this.list = list;
        this.lifecycle=lifecycle;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
