package com.cap.sfm.screeen;

import static com.cap.sfm.MainActivity.insertListTTS;
import static com.cap.sfm.MainActivity.insertText;
import static com.cap.sfm.fragments.Tab2Fragment.mediaPlayer;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
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

import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.databinding.IntroscreenfourBinding;
import com.cap.sfm.databinding.IntroscreenoneBinding;
import com.cap.sfm.utilities.Constants;
import com.cap.sfm.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class introfourfragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        IntroscreenfourBinding binding = IntroscreenfourBinding.inflate(inflater,container,false);
        binding.next.setOnClickListener(v -> {
            PreferenceManager preferenceManager = new PreferenceManager(getActivity().getApplicationContext());

                ListTTS listTTS = new ListTTS();
                listTTS.setTextCollection("Tạo mới");
                Resources resources = getActivity().getApplicationContext().getResources();
                Uri a =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.an) + '/' + resources.getResourceTypeName(R.drawable.an) + '/' + resources.getResourceEntryName(R.drawable.an) );
                listTTS.setImage(a.toString());
                ListTTS listTTS1 = new ListTTS();
                listTTS1.setTextCollection("Xã Giao");
                Uri b =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.xagiao) + '/' + resources.getResourceTypeName(R.drawable.xagiao) + '/' + resources.getResourceEntryName(R.drawable.xagiao) );
                listTTS1.setImage(b.toString());
                ListTTS listTTS2 = new ListTTS();
                Uri c =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.muasam) + '/' + resources.getResourceTypeName(R.drawable.muasam) + '/' + resources.getResourceEntryName(R.drawable.muasam) );
                listTTS2.setTextCollection("Mua Sắm");
                listTTS2.setImage(c.toString());
                ListTTS listTTS3 = new ListTTS();
                Uri d =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.hoctap) + '/' + resources.getResourceTypeName(R.drawable.hoctap) + '/' + resources.getResourceEntryName(R.drawable.hoctap) );
                listTTS3.setImage(d.toString());
                listTTS3.setTextCollection("Học Tập");
                insertListTTS(listTTS,getActivity().getApplicationContext());
                insertListTTS(listTTS1,getActivity().getApplicationContext());
                insertListTTS(listTTS2,getActivity().getApplicationContext());
                insertListTTS(listTTS3,getActivity().getApplicationContext());
                //AllText
                Text text1= new Text();
                text1.setText("Gặp lại bạn thật tốt quá.");
                text1.setPosition(0);
                Text text2= new Text();
                text2.setText("Bạn ơi, bạn ăn tối chưa? Mình cùng đi ăn tối đi?");
                text2.setPosition(1);
                Text text3=new Text();
                text3.setText("Con mời ông bà ăn cơm, con mời các bác ăn cơm, con mời cô chú ăn cơm, con mời bố mẹ ăn cơm, em mời anh chị ăn cơm, mời các cháu ăn cơm!");
                text3.setPosition(2);
                insertText(text1,getActivity().getApplicationContext());
                insertText(text2,getActivity().getApplicationContext());
                insertText(text3,getActivity().getApplicationContext());



            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
            NavController navController = navHostFragment.getNavController();


            if (navHostFragment != null) {

                navController.navigate(R.id.action_viewpagerFragment_to_tab0Fragment);
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setVisibility(View.VISIBLE);

            }
        });
        return binding.getRoot();    }
}
