package com.cap.sfm.fragments;

import static com.cap.sfm.fragments.Tab2Fragment.sendTextToSpeak;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.cap.sfm.Adapters.AlltextdownloadedAdapter;
import com.cap.sfm.Adapters.ListListTTSAdapter;
import com.cap.sfm.Adapters.Tab0TextAdapter;
import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Database.ConversationTextCrossRef;
import com.cap.sfm.MainActivity;
import com.cap.sfm.Models.Conversation;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.VarColumnGridLayoutManager;
import com.cap.sfm.databinding.DialogacpBinding;
import com.cap.sfm.databinding.DialogaddtextBinding;
import com.cap.sfm.databinding.DialogcreateBinding;
import com.cap.sfm.databinding.DialoglistlistttsBinding;
import com.cap.sfm.databinding.Dialogprogress2Binding;
import com.cap.sfm.databinding.DialogsettingbuttonBinding;
import com.cap.sfm.databinding.DialogtextdownloadedBinding;
import com.cap.sfm.databinding.Tab0fragmentBinding;
import com.cap.sfm.listener.alltextListener;
import com.cap.sfm.listener.listTextclicklistener;
import com.cap.sfm.utilities.PreferenceManager;

import java.io.File;
import java.util.ArrayList;

public class Tab0Fragment extends Fragment {
    Tab0fragmentBinding binding;
    ArrayList<Conversation> listconversation;
    ArrayAdapter<Conversation> arrayAdapter;
    Tab0TextAdapter tab0TextAdapter;
    ArrayList<Text> b;
    PreferenceManager preferenceManager;
    Conversation selected;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Tab0fragmentBinding.inflate(inflater, container, false);

        init();
        loadSpinner();

        binding.setting.setOnClickListener(v -> {
            Dialog a = new Dialog(getContext());
            DialogsettingbuttonBinding binding1 = DialogsettingbuttonBinding.inflate(inflater);
            a.setContentView(binding1.getRoot());
            a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            binding1.deleteText.setOnClickListener(v1 -> {
                a.dismiss();
                tab0TextAdapter.checkboxvisibility = true;
                tab0TextAdapter.notifyDataSetChanged();
                binding.image.setVisibility(View.VISIBLE);
                binding.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < tab0TextAdapter.selection.size(); i++) {
                            Log.d("qua12", String.valueOf(tab0TextAdapter.selection.size()));
                            AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteTextInConversationCrossRef(selected.getConversationid(), tab0TextAdapter.selection.get(i).getTextid());
                            b.remove(tab0TextAdapter.selection.get(i));
                        }
                        tab0TextAdapter.notifyItemRangeRemoved(0, tab0TextAdapter.listText.size());
                        tab0TextAdapter.selection.clear();
                        tab0TextAdapter.checkboxvisibility = false;
                        tab0TextAdapter.notifyDataSetChanged();
                        binding.image.setVisibility(View.GONE);
                    }
                });
            });
            binding1.cat.setOnClickListener(v1 -> a.dismiss());


            binding1.addtext.setOnClickListener(v1 -> {
                Dialog addtext = new Dialog(getContext());
                DialogaddtextBinding dialogaddtextBinding = DialogaddtextBinding.inflate(inflater);
                addtext.setContentView(dialogaddtextBinding.getRoot());
                dialogaddtextBinding.alltext.setOnClickListener(v2 -> {
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
                            ConversationTextCrossRef conversationTextCrossRef = new ConversationTextCrossRef();
                            conversationTextCrossRef.conversationid = selected.getConversationid();
                            conversationTextCrossRef.textid = text.getTextid();

                            if (checkConversationTextCrossrefExist(conversationTextCrossRef.conversationid, conversationTextCrossRef.textid) == false) {
                                AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().insertConversationTextCrossRef(conversationTextCrossRef);
                                b.add(text);
                                tab0TextAdapter.notifyItemInserted(b.size());
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


                dialogaddtextBinding.inlisttts.setOnClickListener(v2 -> {
                    Dialog listtts = new Dialog(getContext());
                    DialoglistlistttsBinding binding2 = DialoglistlistttsBinding.inflate(inflater);
                    listtts.setContentView(binding2.getRoot());
                    ArrayList<ListTTS> list = new ArrayList<>();
                    if (MainActivity.getListTTS(getActivity().getApplicationContext()).size() > 0) {
                        list.addAll(MainActivity.getListTTS(getActivity().getApplicationContext()));
                    }
                    Log.d("list", String.valueOf(list.size()));
                    ListListTTSAdapter listListTTSAdapter = new ListListTTSAdapter(list, new listTextclicklistener() {
                        @Override
                        public void onClickListTTSClicked(ListTTS listTTS) {
                            Dialog tinlist = new Dialog(getContext());
                            DialogtextdownloadedBinding dialogtextdownloadedBinding = DialogtextdownloadedBinding.inflate(inflater);
                            tinlist.setContentView(dialogtextdownloadedBinding.getRoot());
                            ArrayList<Text> lt = new ArrayList<>();
                            if (AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTSWithTexts(listTTS.getListTTSid()).texts.size() > 0) {
                                lt.addAll(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListTTSWithTexts(listTTS.getListTTSid()).texts);
                            }
                            AlltextdownloadedAdapter alltextdownloadedAdapter = new AlltextdownloadedAdapter(lt, new alltextListener() {
                                @Override
                                public void onAllTextClicked(Text text) {
                                    ConversationTextCrossRef conversationTextCrossRef = new ConversationTextCrossRef();
                                    conversationTextCrossRef.conversationid = selected.getConversationid();
                                    conversationTextCrossRef.textid = text.getTextid();

                                    if (checkConversationTextCrossrefExist(conversationTextCrossRef.conversationid, conversationTextCrossRef.textid) == false) {
                                        AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().insertConversationTextCrossRef(conversationTextCrossRef);
                                        b.add(text);
                                        tab0TextAdapter.notifyItemInserted(b.size());
                                    }
                                }

                                @Override
                                public void onLongTextClicked(Text text, int pos) {

                                }
                            });
                            dialogtextdownloadedBinding.rcv.setAdapter(alltextdownloadedAdapter);

                            tinlist.show();


                        }
                    });
                    binding2.rcv.setAdapter(listListTTSAdapter);
                    listtts.show();

                });
                addtext.show();

            });


            binding1.createConversation.setOnClickListener(v1 -> {
                Dialog create = new Dialog(getContext());
                DialogcreateBinding binding2 = DialogcreateBinding.inflate(inflater);
                create.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                create.setContentView(binding2.getRoot());
                binding2.okey.setOnClickListener(v2 -> {
                    if (binding2.textname.getText().length() > 0) {
                        Conversation conversation = new Conversation();
                        conversation.setTextConversation(binding2.textname.getText().toString());
                        AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().inserConversation(conversation);
                        listconversation.add(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().get(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().size() - 1));
                        arrayAdapter.setNotifyOnChange(true);
                        binding.conversation.setSelection(listconversation.size() - 1);
                        binding2.okey.playAnimation();
                        binding2.okey.addAnimatorListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                create.dismiss();

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
                create.show();

            });
            binding1.deleteConversation.setOnClickListener(v1 -> {
                Dialog acp = new Dialog(getContext());
                acp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                DialogacpBinding dialogacpBinding = DialogacpBinding.inflate(inflater);
                dialogacpBinding.image.setOnClickListener(v2 -> {
                    Log.d("hoa", String.valueOf(listconversation.size()));
                    if(selected!=null){
                        AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteConversationCrossRef(selected.getConversationid());
                        AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteConversation(selected.getConversationid());
                        listconversation.remove(binding.conversation.getSelectedItemPosition());
                        arrayAdapter.notifyDataSetChanged();
                    }
                    if(binding.conversation.getSelectedItemPosition()==0){
                        Toast.makeText(getActivity().getApplicationContext(), "Không có lời thoại nào", Toast.LENGTH_SHORT).show();
                        b.clear();
                        tab0TextAdapter.notifyDataSetChanged();
                     }

                    //                    if (listconversation.size() > 0) {
//                        if (binding.conversation.getSelectedItemPosition() == 0) {
//                            binding.conversation.setSelection(binding.conversation.getSelectedItemPosition());
//                            selected = (Conversation) binding.conversation.getSelectedItem();
//                            if (selected != null) {
//                                loadRecyclerView();
//                            }
//                            Log.d("bietroi1", String.valueOf(selected.getConversationid()));
//                            preferenceManager.putInt("spinnerconver", binding.conversation.getSelectedItemPosition());
//
//                        }
//                        else
//                        {
//                            Log.d("bietroi2", String.valueOf(selected.getConversationid()));
//
//                            if (listconversation.size() > 1) {
//                                Log.d("bietroi3", String.valueOf(selected.getConversationid()));
//
//                                binding.conversation.setSelection(binding.conversation.getSelectedItemPosition() - 1);
//                            } else if (listconversation.size() == 1) {
//                                Log.d("bietroi4", String.valueOf(selected.getConversationid()));
//
//                                b.clear();
//                                tab0TextAdapter.notifyDataSetChanged();
//                            }
//                        }
//
//                    } else {
//                        Toast.makeText(getActivity().getApplicationContext(), "Không có hội thoại nào!", Toast.LENGTH_SHORT).show();
//                    }

                    Dialog lottie = new Dialog(getContext());
                    Dialogprogress2Binding dialogprogress2Binding = Dialogprogress2Binding.inflate(inflater);
                    lottie.setContentView(dialogprogress2Binding.getRoot());
                    lottie.setCancelable(true);
                    lottie.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


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
                            lottie.dismiss();
                            acp.dismiss();
                            a.dismiss();
                            if (listconversation.size() == 0) {
                                binding.searchbar.setVisibility(View.GONE);
                                Transition transition1 = new Fade();
                                transition1.setDuration(500);
                                transition1.addTarget(binding.textcreate);
                                TransitionManager.beginDelayedTransition(container, transition1);
                                binding.textcreate.setVisibility(View.VISIBLE);
                            }
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
        binding.textcreate.setOnClickListener(v -> {
            Dialog create = new Dialog(getContext());
            DialogcreateBinding binding1 = DialogcreateBinding.inflate(inflater);
            create.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            create.setContentView(binding1.getRoot());
            binding1.okey.setOnClickListener(v1 -> {
                if (binding1.textname.getText().length() > 0) {
                    binding.searchbar.setVisibility(View.VISIBLE);

                    Conversation conversation = new Conversation();
                    conversation.setTextConversation(binding1.textname.getText().toString());
                    AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().inserConversation(conversation);
                    listconversation.add(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().get(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().size() - 1));
                    arrayAdapter.notifyDataSetChanged();
                    Log.d("bietroi", String.valueOf(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().get(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().size() - 1).getConversationid()));

                    binding1.okey.playAnimation();
                    binding1.okey.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            create.dismiss();
                            binding.textcreate.setVisibility(View.GONE);


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
            create.show();

        });


        return binding.getRoot();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        listconversation = new ArrayList<>();
        b = new ArrayList<>();
        tab0TextAdapter = new Tab0TextAdapter(b, new alltextListener() {
            @Override
            public void onAllTextClicked(Text text) {
                text.setSaved(1);
                Dialog a = new Dialog(getContext());
                a.setContentView(R.layout.dialogprogress);
                a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                sendTextToSpeak(text, getActivity().getApplicationContext(), a);
            }

            @Override
            public void onLongTextClicked(Text text, int pos) {

            }
        });
        loadList();
        if (listconversation.size() == 0) {
            binding.searchbar.setVisibility(View.GONE);
            binding.textcreate.setVisibility(View.VISIBLE);
        } else {
            binding.textcreate.setVisibility(View.GONE);
        }

        VarColumnGridLayoutManager varColumnGridLayoutManager = new VarColumnGridLayoutManager(getActivity().getApplicationContext(), 350);

        binding.recyclerView.setAdapter(tab0TextAdapter);
        binding.recyclerView.setLayoutManager(varColumnGridLayoutManager);


    }

    private void loadList() {
        listconversation.clear();
        if (AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().size() > 0) {
            Log.d("bik", String.valueOf(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation().size()));
            listconversation.addAll(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversation());
        }

    }

    private void loadSpinner() {
        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listconversation);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.conversation.setAdapter(arrayAdapter);
        if (listconversation.size() > preferenceManager.getSpinner("spinnerconver")) {
            binding.conversation.setSelection(preferenceManager.getSpinner("spinnerconver"));
            Conversation a = (Conversation) binding.conversation.getSelectedItem();
        }
        binding.conversation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("hoa", String.valueOf("ban vua selection 2"));
                selected = (Conversation) binding.conversation.getSelectedItem();
                if (selected != null) {
                    loadRecyclerView();
                }
                Log.d("bietroi1", String.valueOf(selected.getConversationid()));
                preferenceManager.putInt("spinnerconver", binding.conversation.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadRecyclerView() {
        Log.d("hoa", "ban vua selection");
        b.clear();
        if (listconversation.size() > 0) {
//                    Log.d("faie",String.valueOf(binding.conversation.getSelectedItemPosition()));
//            Log.d("bik",String.valueOf(binding.conversation.getSelectedItemPosition()));
//            Log.d("bik",String.valueOf(listconversation.get(binding.conversation.getSelectedItemPosition()).getConversationid()));

            if (AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversationWithTexts(selected.getConversationid()).Texts != null) {

                if (AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversationWithTexts(selected.getConversationid()).Texts.size() > 0) {
                    Log.d("okey3", String.valueOf(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversationWithTexts(selected.getConversationid()).Texts.size()));
                    b.addAll(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListConversationWithTexts(selected.getConversationid()).Texts);
                }

            }
        }
//        Log.d("bsize",String.valueOf(b.size()));
        tab0TextAdapter.notifyDataSetChanged();
        Log.d("oke", String.valueOf(b.size()));


    }


    public boolean checkConversationTextCrossrefExist(int conversationid, int textid) {
        ConversationTextCrossRef list = AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().checkConversationTextCrossRef(conversationid, textid);
        return list != null;
    }

}

