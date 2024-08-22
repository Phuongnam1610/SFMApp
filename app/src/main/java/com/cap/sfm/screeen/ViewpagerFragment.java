package com.cap.sfm.screeen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cap.sfm.databinding.FragmentViewPagerBinding;
import com.cap.sfm.fragments.Tab3Fragment;

import java.util.ArrayList;

public class ViewpagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentViewPagerBinding binding = FragmentViewPagerBinding.inflate(inflater,container,false);
        ArrayList<Fragment> list =new ArrayList<>();
        list.add(new introonefragment());
        list.add(new introtwofragment());
        list.add(new introthreefragment());
        list.add(new introfourfragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(list,getActivity().getSupportFragmentManager(),getLifecycle());
        binding.viewPager.setAdapter(viewPagerAdapter);

        return binding.getRoot();
    }
}
