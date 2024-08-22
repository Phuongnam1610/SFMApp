package com.cap.sfm.screeen;

import static com.cap.sfm.fragments.Tab2Fragment.mediaPlayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cap.sfm.R;
import com.cap.sfm.databinding.FragmentSplashBinding;
import com.cap.sfm.utilities.Constants;
import com.cap.sfm.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class splashfragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSplashBinding binding = FragmentSplashBinding.inflate(inflater,container,false);
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        if(onSplashfinish()==false){
            mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.doraemon);
            mediaPlayer.start();
     }

        new Handler().postDelayed(() -> {
             if(onSplashfinish()==true)
             {
                 if (navHostFragment != null) {

                     navController.navigate(R.id.action_splashfragment_to_tab0Fragment);
                     BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                     bottomNavigationView.setVisibility(View.VISIBLE);

                     // Setup NavigationUI here

                 }
             }
             else
             {
                 if (navHostFragment != null) {

                     navController.navigate(R.id.action_splashfragment_to_viewpagerFragment);
                     // Setup NavigationUI here




                 }
             }


         },2000);

        return binding.getRoot();

    }

private boolean onSplashfinish(){
        PreferenceManager preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        return preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN);
}
}
