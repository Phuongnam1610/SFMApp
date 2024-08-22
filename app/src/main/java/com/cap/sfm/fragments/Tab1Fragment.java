package com.cap.sfm.fragments;
import static androidx.navigation.fragment.FragmentKt.findNavController;
import static com.cap.sfm.MainActivity.insertListTTS;
import static com.cap.sfm.MainActivity.insertText;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.cap.sfm.Adapters.MyLibraryAdapter;
import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Database.ListTTSTextCrossRef;
import com.cap.sfm.GridLayoutManager1;
import com.cap.sfm.MainActivity;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.VarColumnGridLayoutManager;
import com.cap.sfm.databinding.DialogcreateBinding;
import com.cap.sfm.databinding.Tab1fragmentBinding;
import com.cap.sfm.listener.listTextclicklistener;
import com.cap.sfm.utilities.Constants;
import com.cap.sfm.utilities.PreferenceManager;
import com.cap.sfm.utilities.Utility;

import java.util.ArrayList;
public class Tab1Fragment extends Fragment {

    private Tab1fragmentBinding binding;
    private PreferenceManager preferenceManager;
    ArrayList<ListTTS> myLibrary;
    MyLibraryAdapter myLibraryAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Tab1fragmentBinding.inflate(getLayoutInflater(),container,false);
        Transition transition = new Fade();
        transition.setDuration(500);
        transition.addTarget(binding.scrollview);
        TransitionManager.beginDelayedTransition(container, transition);
        binding.scrollview.setVisibility(View.VISIBLE);
//        binding.scrollview.animate()
//                .alpha(1f)
//                .setDuration(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        binding.scrollview.setVisibility(View.VISIBLE);
//                    }
//                });

        myLibrary = new ArrayList<>();

        if(MainActivity.getListTTS(getActivity().getApplicationContext()).size()>0&&MainActivity.getListTTS(getActivity().getApplicationContext())!=null)
        {
            myLibrary.addAll(MainActivity.getListTTS(getActivity().getApplicationContext()));
        }
        if(myLibrary.size()>0)
        {
             myLibraryAdapter = new MyLibraryAdapter(myLibrary, new listTextclicklistener() {
                @Override
                public void onClickListTTSClicked(ListTTS listTTS) {
                    Log.d("list",String.valueOf(listTTS.getListTTSid()));
                    if(listTTS.getListTTSid()==1)
                    {
                        Dialog a = new Dialog(getContext());
                        DialogcreateBinding binding1 = DialogcreateBinding.inflate(inflater);
                        binding1.okey.setOnClickListener(v -> {
                            if(binding1.textname.getText().length()>0) {
                                ListTTS listTTS1 = new ListTTS();
                                Resources resources = getActivity().getApplicationContext().getResources();
                                Uri b =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.lumeow6_01) + '/' + resources.getResourceTypeName(R.drawable.lumeow6_01) + '/' + resources.getResourceEntryName(R.drawable.lumeow6_01) );
                                listTTS1.setImage(b.toString());
                                listTTS1.setTextCollection(binding1.textname.getText().toString());
                                AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().insertListTTS(listTTS1);

                                myLibrary.add(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTS().get(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTS().size()-1));
                                myLibraryAdapter.notifyItemInserted(myLibrary.size()+1);
                           binding1.okey.playAnimation();
                           binding1.okey.addAnimatorListener(new Animator.AnimatorListener() {
                               @Override
                               public void onAnimationStart(Animator animation) {

                               }

                               @Override
                               public void onAnimationEnd(Animator animation) {
                                   a.dismiss();

                               }

                               @Override
                               public void onAnimationCancel(Animator animation) {

                               }

                               @Override
                               public void onAnimationRepeat(Animator animation) {

                               }
                           });
                            }
                        });
                        a.setContentView(binding1.getRoot());
                        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        a.show();

                    }
                    else{
                    Bundle bundle = new Bundle();
                    Log.d("hoaid",String.valueOf(listTTS.getListTTSid()));
                    bundle.putInt("listTTSid", listTTS.getListTTSid());
                    // Set Fragmentclass Arguments
                        TextInListTTSFragment textInListTTSFragment = new TextInListTTSFragment();
                        textInListTTSFragment.setArguments(bundle);

                        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                        if (navHostFragment != null) {

                            NavController navController = navHostFragment.getNavController();
                            navController.navigate(R.id.action_tab1Fragment_to_textInListTTSFragment,bundle);
                            // Setup NavigationUI here

                        }


//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,textInListTTSFragment).addToBackStack(null).commit()
                    ;}

                }
            },getActivity());
            VarColumnGridLayoutManager varColumnGridLayoutManager = new VarColumnGridLayoutManager(getActivity().getApplicationContext(),350);

            binding.rcvLibrary.setLayoutManager(varColumnGridLayoutManager);
            binding.rcvLibrary.setAdapter(myLibraryAdapter);
        }

        return binding.getRoot();
    }


}
