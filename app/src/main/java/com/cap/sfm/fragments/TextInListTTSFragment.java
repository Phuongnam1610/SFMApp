package com.cap.sfm.fragments;

import static com.cap.sfm.fragments.Tab2Fragment.sendTextToSpeak;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.cap.sfm.Adapters.AlltextdownloadedAdapter;
import com.cap.sfm.Adapters.TextInListTTSAdapter;
import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Database.ListTTSTextCrossRef;
import com.cap.sfm.MainActivity;
import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.VarColumnGridLayoutManager;
import com.cap.sfm.databinding.DialogacpBinding;
import com.cap.sfm.databinding.Dialogprogress2Binding;
import com.cap.sfm.databinding.Dialogsettingbutton2Binding;
import com.cap.sfm.databinding.DialogtextdownloadedBinding;
import com.cap.sfm.databinding.FragmentTextinListTTSBinding;
import com.cap.sfm.listener.alltextListener;

import java.io.File;
import java.util.ArrayList;

public class TextInListTTSFragment extends Fragment {
    FragmentTextinListTTSBinding binding;
    ArrayList<Text> textList;
    int listTTSid;
    TextInListTTSAdapter textInListTTSAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        binding = FragmentTextinListTTSBinding.inflate(getLayoutInflater(), container, false);

        VarColumnGridLayoutManager varColumnGridLayoutManager = new VarColumnGridLayoutManager(getActivity().getApplicationContext(),350);

        binding.rcvAllText.setLayoutManager(varColumnGridLayoutManager);
        if (AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTSWithTexts(listTTSid).texts.size() > 0) {
            textList.addAll(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTSWithTexts(listTTSid).texts);
        }
        textInListTTSAdapter = new TextInListTTSAdapter(textList, new alltextListener() {
            @Override
            public void onAllTextClicked(Text text) {
                text.setSaved(1);
                Dialog a = new Dialog(getContext());
                a.setContentView(R.layout.dialogprogress);
                a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                sendTextToSpeak(text, getActivity().getApplicationContext(),a);
            }

            @Override
            public void onLongTextClicked(Text text, int pos) {



            }
        });
        binding.rcvAllText.setAdapter(textInListTTSAdapter);

        binding.inputtext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(binding.inputtext.getText().length()>0){
                    if ( (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        // Perform action on key press
                        if (textInListTTSAdapter.listText.size() == 0) {
                            binding.lotties.setVisibility(View.VISIBLE);
                            binding.lottie.playAnimation();
                        } else {
                            binding.rcvAllText.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }}
                else {

                    binding.lotties.setVisibility(View.GONE);
                    binding.rcvAllText.setVisibility(View.VISIBLE);

                }
                return false;
            }
        });
        binding.inputtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (textInListTTSAdapter != null) {
                    textInListTTSAdapter.getFilter().filter(s.toString());
                    if(textInListTTSAdapter.listText.size()!=0)
                    {
                        binding.rcvAllText.setVisibility(View.VISIBLE);
                        binding.lotties.setVisibility(View.GONE);
                    }
                    else
                    {   binding.rcvAllText.setVisibility(View.GONE);
                        binding.lotties.setVisibility(View.VISIBLE);

                        binding.lottie.playAnimation();

                    }

                }
                        }

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.inputtext.getText().length()==0)
                {
                    binding.rcvAllText.setVisibility(View.VISIBLE);
                    binding.lotties.setVisibility(View.GONE);

                }


            }
        });
        binding.setting.setOnClickListener(v -> {
            Dialog a = new Dialog(getContext());
            Dialogsettingbutton2Binding binding1 = Dialogsettingbutton2Binding.inflate(inflater);
            a.setContentView(binding1.getRoot());
            a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            binding1.cat.setOnClickListener(v1->a.dismiss());
            binding1.deleteText.setOnClickListener(v1 -> {
                a.dismiss();
                textInListTTSAdapter.checkboxvisibility = true;
                textInListTTSAdapter.notifyDataSetChanged();
                binding.image.setVisibility(View.VISIBLE);
                binding.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < textInListTTSAdapter.selection.size(); i++) {
                            AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteTextInListTTS(listTTSid,textInListTTSAdapter.selection.get(i).getTextid());
                            textList.remove(textInListTTSAdapter.selection.get(i));
                        }
                        textInListTTSAdapter.notifyItemRangeRemoved(0,textInListTTSAdapter.listText.size());
                        textInListTTSAdapter.selection.clear();
                        textInListTTSAdapter.checkboxvisibility = false;
                        textInListTTSAdapter.notifyDataSetChanged();
                        binding.image.setVisibility(View.GONE);
                    }
                });
            });

            binding1.addtext.setOnClickListener(v1 -> {
                Dialog textdownload = new Dialog(getContext());
                DialogtextdownloadedBinding dialogtextdownloadedBinding = DialogtextdownloadedBinding.inflate(inflater);
                textdownload.setContentView(dialogtextdownloadedBinding.getRoot());
                ArrayList<Text> allText = (ArrayList<Text>) MainActivity.getSortPosition(getActivity().getApplicationContext());
                ArrayList<Text> textdownloaded = new ArrayList<>();
                for (int i = 0; i < allText.size(); i++) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/" + allText.get(i).getTextid() + ".mp3");
                    if (file.exists()) {
                        textdownloaded.add(allText.get(i));
                    }
                }
                AlltextdownloadedAdapter alltextdownloadedAdapter = new AlltextdownloadedAdapter(textdownloaded, new alltextListener() {
                    @Override
                    public void onAllTextClicked(Text text) {

                        ListTTSTextCrossRef listTTSTextCrossRef = new ListTTSTextCrossRef();
                        listTTSTextCrossRef.listTTSid = listTTSid;
                        listTTSTextCrossRef.textid = text.getTextid();

                        if (checkListTTSCrossRefExists(listTTSTextCrossRef.listTTSid, listTTSTextCrossRef.textid) == false) {
                            AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().insertListTTSTextref(listTTSTextCrossRef);
                            textList.add(text);
                            textInListTTSAdapter.notifyItemInserted(textList.size());
                        }

                    }

                    @Override
                    public void onLongTextClicked(Text text, int pos) {

                    }
                });
                dialogtextdownloadedBinding.rcv.setAdapter(alltextdownloadedAdapter);
                dialogtextdownloadedBinding.inputtext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        alltextdownloadedAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                textdownload.show();

            });
            binding1.deleteConversation.setOnClickListener(v1 -> {
                Dialog acp = new Dialog(getContext());
                acp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                DialogacpBinding dialogacpBinding = DialogacpBinding.inflate(inflater);
                dialogacpBinding.image.setOnClickListener(v2 -> {
                    if(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTS().size()>0){
                        AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteListTTSCrossRef(listTTSid);
                        AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteListTTS(listTTSid);
                    }

                    Dialog lottie = new Dialog(getContext());
                    Dialogprogress2Binding dialogprogress2Binding = Dialogprogress2Binding.inflate(inflater);
                    lottie.setContentView(dialogprogress2Binding.getRoot());
                    lottie.setCancelable(true);
                    lottie.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    lottie.show();
                    acp.hide();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lottie.show();

                        }
                    }, 200);

                    dialogprogress2Binding.acp.playAnimation();
                    dialogprogress2Binding.acp.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                            NavController navController = navHostFragment.getNavController();
                            navController.navigate(R.id.action_textInListTTSFragment_to_tab1Fragment);
                            lottie.dismiss();
                            acp.dismiss();
                            a.dismiss();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                });
                acp.setContentView(dialogacpBinding.getRoot());

                acp.show();
            });
            a.show();


        });
        return binding.getRoot();
    }

    private void init() {
        textList = new ArrayList<>();
        listTTSid = getArguments().getInt("listTTSid");

    }

    private boolean checkListTTSCrossRefExists(int listTTSid, int textid) {
        ListTTSTextCrossRef list = AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().checkListTtsTextCrossRef(listTTSid, textid);
        return list != null;
    }
}
