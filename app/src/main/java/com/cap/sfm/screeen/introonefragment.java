package com.cap.sfm.screeen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.cap.sfm.R;
import com.cap.sfm.databinding.IntroscreenoneBinding;

public class introonefragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        IntroscreenoneBinding binding = IntroscreenoneBinding.inflate(inflater,container,false);
        ViewPager2 viewPager = getActivity().findViewById(R.id.viewPager);
        binding.next.setOnClickListener(v -> {viewPager.setCurrentItem(1);});
        return binding.getRoot();
    }
}
