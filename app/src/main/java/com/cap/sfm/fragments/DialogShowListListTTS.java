package com.cap.sfm.fragments;

import android.animation.Animator;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cap.sfm.Adapters.ListListTTSAdapter;
import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Database.ListTTSTextCrossRef;
import com.cap.sfm.MainActivity;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.databinding.DialogcreateBinding;
import com.cap.sfm.databinding.DialoglistlistttsBinding;
import com.cap.sfm.databinding.DialoglongclicktextBinding;
import com.cap.sfm.listener.listTextclicklistener;

import java.util.ArrayList;

public class DialogShowListListTTS extends DialogLongClickFragment{
    private DialoglistlistttsBinding binding;
    ListListTTSAdapter listListTTSAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArrayList<ListTTS> list = new ArrayList<>();
        if(MainActivity.getListTTS(getActivity().getApplicationContext()).size()>0)
        {
            list.addAll(MainActivity.getListTTS(getActivity().getApplicationContext()));
        }
        binding = DialoglistlistttsBinding.inflate(inflater,container,false);
         listListTTSAdapter = new ListListTTSAdapter(list, new listTextclicklistener() {
                @Override
                public void onClickListTTSClicked(ListTTS listTTS) {
                    Log.d("hoa", String.valueOf(listTTS.getListTTSid()));
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
                                list.add(MainActivity.getListTTS(getActivity().getApplicationContext()).get(MainActivity.getListTTS(getActivity().getApplicationContext()).size()-1));
                                listListTTSAdapter.notifyItemInserted(MainActivity.getListTTS(getActivity().getApplicationContext()).size()+1);
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
                    else {
                      Dialog textinlist = new Dialog(getContext());

                    }
                }
            });
            binding.rcv.setAdapter(listListTTSAdapter);
            setCancelable(true);
        return binding.getRoot();
    }
}
