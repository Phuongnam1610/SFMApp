package com.cap.sfm.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cap.sfm.Adapters.AlltextdownloadedAdapter;
import com.cap.sfm.MainActivity;
import com.cap.sfm.Models.Text;
import com.cap.sfm.databinding.DialogtextdownloadedBinding;
import com.cap.sfm.listener.alltextListener;

import java.io.File;
import java.util.ArrayList;

public class DialogAllTextDownloadedFragment extends DialogFragment {
    DialogtextdownloadedBinding binding;
    AlltextdownloadedAdapter alltextdownloadedAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogtextdownloadedBinding.inflate(inflater,container,false);
        ArrayList<Text> allText = (ArrayList<Text>) MainActivity.getSortPosition(getActivity().getApplicationContext());
        ArrayList<Text> textdownloaded = new ArrayList<>();
        for (int i = 0; i < allText.size(); i++) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/"+allText.get(i).getTextid()+".mp3");
            if (file.exists()) {
                textdownloaded.add(allText.get(i));
            }}
        alltextdownloadedAdapter = new AlltextdownloadedAdapter(textdownloaded, new alltextListener() {
            @Override
            public void onAllTextClicked(Text text) {

            }

            @Override
            public void onLongTextClicked(Text text,int pos) {

            }
        });
        binding.rcv.setAdapter(alltextdownloadedAdapter);
        return binding.getRoot();
    }
}
