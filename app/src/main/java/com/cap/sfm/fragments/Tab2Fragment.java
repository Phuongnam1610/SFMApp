package com.cap.sfm.fragments;

import static android.content.Context.DOWNLOAD_SERVICE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cap.sfm.Adapters.AllTextAdapter;
import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.MainActivity;

import com.cap.sfm.Models.Text;
import com.cap.sfm.Network.ApiClient;
import com.cap.sfm.Network.ApiService;
import com.cap.sfm.R;
import com.cap.sfm.VarColumnGridLayoutManager;
import com.cap.sfm.databinding.DialoginfoBinding;
import com.cap.sfm.databinding.DialoglongclicktextBinding;
import com.cap.sfm.databinding.Tab2fragmentBinding;
import com.cap.sfm.listener.alltextListener;
import com.cap.sfm.utilities.Constants;
import com.cap.sfm.utilities.PreferenceManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab2Fragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    PreferenceManager preferenceManager;
    public Tab2fragmentBinding binding;
    ArrayList<Text> allText;
    public AllTextAdapter allTextAdapter;
    long downloadid;


    String description;
    int position;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Tab2fragmentBinding.inflate(getLayoutInflater(), container, false);

        init();
        loadAllText();
        if (allText.size() > 0) {
            allTextAdapter = new AllTextAdapter(allText, new alltextListener() {
                @Override
                public void onAllTextClicked(Text text) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/" + text.getTextid() + ".mp3");

                    if (file.exists()) {
                        text.setSaved(1);
                    }
                    Dialog a = new Dialog(getContext());
                    a.setContentView(R.layout.dialogprogress);
                    a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    sendTextToSpeak(text, getActivity().getApplicationContext(),a);
                }

                @Override
                public void onLongTextClicked(Text text, int pos) {
                    Dialog a = new Dialog(getContext());
                    DialoglongclicktextBinding binding1 = DialoglongclicktextBinding.inflate(inflater);
                    a.setContentView(binding1.getRoot());
                    a.show();
                    binding1.information.setOnClickListener(v -> {
                        Dialog b = new Dialog(getContext());
                        DialoginfoBinding dialoginfoBinding = DialoginfoBinding.inflate(inflater);
                        b.setContentView(dialoginfoBinding.getRoot());
                        dialoginfoBinding.textinfo.setText(text.getText());
                        if(text.getSaved()==1)
                        {
                            dialoginfoBinding.downloadinfo.setText("Đã tải xuống");
                        }
                        else
                        {
                            dialoginfoBinding.downloadinfo.setText("Chưa tải xuống");

                        }                        b.show();
                    });
                    binding1.listTTS.setOnClickListener(v ->
                    {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/" + text.getTextid() + ".mp3");
                        if (!file.exists()) {
                            Toast.makeText(getActivity().getApplicationContext(), "Vui lòng tải xuống lời thoại trước", Toast.LENGTH_SHORT).show();
                        } else if (file.exists()) {
                            DialogShowListListTTS dialogShowListListTTS = new DialogShowListListTTS();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            Bundle b = new Bundle();
                            b.putSerializable("text", text);
                            dialogShowListListTTS.setArguments(b);
                            dialogShowListListTTS.show(fm, null);
                        }
                    });
                    binding1.deleteText.setOnClickListener(v -> {
                                a.dismiss();
                                allTextAdapter.checkboxvisibility = true;
                                allTextAdapter.notifyDataSetChanged();
                                binding.image.setVisibility(View.VISIBLE);
                                binding.image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("qua1","ban vua an vao anh");
                                        Log.d("qua1",String.valueOf(allTextAdapter.selection.size()));

                                        for (int i = 0; i < allTextAdapter.selection.size(); i++) {

                                            Log.d("qua1",String.valueOf(allTextAdapter.selection.get(i)));
                                            AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().deleteTex(allTextAdapter.selection.get(i).getTextid());
                                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/" + allTextAdapter.selection.get(i).getTextid() + ".mp3");
                                            file.delete();
                                            allText.remove(allTextAdapter.selection.get(i));
                                            allTextAdapter.listText.remove(allTextAdapter.selection.get(i));
                                            allTextAdapter.listTextFilter.remove(allTextAdapter.selection.get(i));
                                        }
                                        allTextAdapter.selection.clear();
                                        allTextAdapter.checkboxvisibility = false;
                                        allTextAdapter.notifyDataSetChanged();
                                        binding.image.setVisibility(View.GONE);
                                    }
                                });
                            });
                    binding1.downloadText.setOnClickListener(v -> {
                        downloadText(text, getActivity().getApplicationContext());
                        position = pos;
                        a.dismiss();

                    });

                }
            });
            binding.rcvAllText.setAdapter(allTextAdapter);
            allTextAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT);

        }

        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
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
                    if (allTextAdapter.listText.size() == 0) {
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

                if (allTextAdapter != null) {
                    allTextAdapter.getFilter().filter(s.toString());
                    if(allTextAdapter.listText.size()!=0)
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

        binding.lottie.setOnClickListener(v -> {
            if(binding.inputtext.getText().length()>0){
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Text text = new Text();
                text.setText(binding.inputtext.getText().toString());
                AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().insertText(text);
                Text t = AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListText().get(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListText().size()-1);
allTextAdapter.listTextFilter.add(t);
allText.add(t);
//                allTextAdapter.listText.add(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListText().get(AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListText().size()-1));
            allTextAdapter.notifyItemInserted(allText.size());
            binding.lotties.setVisibility(View.GONE);
            binding.rcvAllText.setVisibility(View.VISIBLE);
            }
                    });
        return binding.getRoot();
    }

    private void init() {
        allText = new ArrayList<>();
//        GridLayoutManager1 gridLayoutManager = new GridLayoutManager1(getActivity().getApplicationContext(), mNoOfColumns, 1, false);
        VarColumnGridLayoutManager varColumnGridLayoutManager = new VarColumnGridLayoutManager(getActivity().getApplicationContext(),350);
        binding.rcvAllText.setLayoutManager(varColumnGridLayoutManager);
    }

    private void loadAllText() {
        if (MainActivity.getAllText(getActivity().getApplicationContext()).size() > 0 && MainActivity.getAllText(getActivity().getApplicationContext()) != null) {
            allText.addAll((ArrayList<Text>) AllDatabase.getDatabase(getActivity().getApplicationContext()).textDao().getListText());
            for (int i = 0; i < allText.size(); i++) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/" + allText.get(i).getTextid() + ".mp3");
                if (file.exists()) {
                    Text t = allText.get(i);
                    t.setSaved(1);
                    allText.set(i, t);
                }
            }
        }
        for (int i = 0; i < allText.size(); i++) {
            Text t = allText.get(i);
            Log.d("conha",String.valueOf(t.getText()));

        }

    }

    private void downloadText(Text text, Context context) {
        PreferenceManager preferenceManager;
        String voice;
        float speed;
        String apikey;
        preferenceManager = new PreferenceManager(context);
        voice = preferenceManager.getVoice(Constants.KEY_VOICE_NAME);
        speed = preferenceManager.getFloat(Constants.KEY_SPEED);
        apikey = preferenceManager.getString(Constants.KEY_APIKEY);
        ApiClient.getClient().create(ApiService.class).sendText(apikey, speed, voice, text.getText()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            JSONObject jsonResult = new JSONObject(response.body());
                            checkPer(text.getSaved(), context, jsonResult.getString("async"), text.getText(), text.getText(), text.getTextid());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                } else {
                    Log.d("code", String.valueOf(response.code()));
                    Toast.makeText(context, "Đã xảy ra lỗi tải xuống, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(context, "Đã xảy ra lỗi tải xuống, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkPer(int saved, Context context, String audioUrl, String title, String description, int id) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (saved == 0) {

                    ApiClient.getClient().create(ApiService.class).downloadFileWithDynamicUrlSync(audioUrl).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {

                                boolean writtenToDisk = writeResponseBodyToDisk(response.body(),id);
                                if(writtenToDisk==true)
                                {
                                    if (allTextAdapter != null) {
                                        try {
                                            Text text = allText.get(position);
                                            text.setSaved(1);
                                            allText.set(position, text);
                                            allTextAdapter.notifyDataSetChanged();
                                        }
                                        catch (IndexOutOfBoundsException e)
                                        {
                                            e.printStackTrace();

                                        }
                                    }
                                    Toast.makeText(getActivity().getApplicationContext(), description + " hoàn tất tải xuống", Toast.LENGTH_SHORT).show();

                                }                                Log.d(TAG, "file download was a success? " + writtenToDisk);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), description + " tải xuống thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), description + " tải xuống thất bại", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
                else {
                    Toast.makeText(context, "Toast Text đã được download", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(context, "chưa được cấp quyền", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private int getDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadid);
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            int desIndex = cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION);
            description = cursor.getString(desIndex);
            return status;
        }
        return DownloadManager.ERROR_UNKNOWN;

    }

    public static MediaPlayer mediaPlayer;

    public static void sendTextToSpeak(Text text, Context context, Dialog a)
    {

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
//        a.setAnimation(R.raw.meowloading)
//                .setDialogBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))
//                .setAutoPlayAnimation(true)
//
//                .setMessage("Loading...")
//                .setMessageColor(Color.parseColor("#f92345"));
        a.setCanceledOnTouchOutside(true);
        a.setOnCancelListener(dialog ->{ if(mediaPlayer!=null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } });

        a.show();
        if (text.getSaved() == 0) {
            {
                PreferenceManager preferenceManager;
                String voice;
                float speed;
                String apikey;
                preferenceManager = new PreferenceManager(context);
                voice = preferenceManager.getVoice(Constants.KEY_VOICE_NAME);
                speed = preferenceManager.getFloat(Constants.KEY_SPEED);
                apikey = preferenceManager.getString(Constants.KEY_APIKEY);
                String audioUrl2 = "https://translate.google.com/translate_tts?ie=UTF-8&q=" + text.getText() + "&tl" + "=vi&total=1&idx=0&client=tw-ob&ttsspeed=1";
                ApiClient.getClient().create(ApiService.class).sendText(apikey, speed, voice, text.getText())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {

                                        try {
                                            JSONObject jsonResult = new JSONObject(response.body());
                                            String audioUrl = jsonResult.getString("async");
//                                            String audioUrl = "https://file01.fpt.ai/text2speech-v5/short/2022-08-20/f6b2a2769d2df29b397aedc3bc0f4221.mp3";

                                            new Thread(){

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    super.run();
                                                    try {
                                                        URL url = new URL(audioUrl);
                                                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                                        con.setRequestMethod("HEAD");
                                                        con.connect();
                                                        Log.i("huc", "con.getResponseCode() IS : " + con.getResponseCode());
                                                        if(con.getResponseCode()==200)
                                                        {
                                                            if (mediaPlayer == null) {
                                                                mediaPlayer = new MediaPlayer();
                                                            }
                                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                                            try {
                                                                mediaPlayer.reset();
                                                                mediaPlayer.setDataSource(audioUrl);
                                                                mediaPlayer.prepare();
                                                                mediaPlayer.start();

                                                            } catch (IOException e) {

                                                            } catch (IllegalStateException ex) {
                                                            }
                                                            mediaPlayer.setOnCompletionListener(mp -> a.dismiss()
                                                            );
                                                        }else
                                                        {
                                                            if (mediaPlayer == null) {
                                                                mediaPlayer = new MediaPlayer();
                                                            }
                                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                                            try {
                                                                mediaPlayer.reset();
                                                                mediaPlayer.setDataSource(audioUrl2);
                                                                mediaPlayer.prepare();
                                                                mediaPlayer.start();

                                                            } catch (IOException e) {

                                                            } catch (IllegalStateException ex) {
                                                            }
                                                            mediaPlayer.setOnCompletionListener(mp -> a.dismiss()
                                                            );
                                                        }
                                                        if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                                                            Log.i("huc", "Sucess");
                                                        }

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.i("huc", "fail");
                                                    }
                                                }

                                            }.start();




                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    if (mediaPlayer == null) {
                                        mediaPlayer = new MediaPlayer();
                                    }
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                    try {
                                        mediaPlayer.reset();
                                        mediaPlayer.setDataSource(audioUrl2);
                                        mediaPlayer.prepare();
                                        mediaPlayer.start();

                                    } catch (IOException e) {

                                    } catch (IllegalStateException ex) {
                                    }
                                    mediaPlayer.setOnCompletionListener(mp -> a.dismiss()
                                    );


                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                t.printStackTrace();
                                if (mediaPlayer == null) {
                                    mediaPlayer = new MediaPlayer();
                                }
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                try {
                                    mediaPlayer.reset();
                                    mediaPlayer.setDataSource(audioUrl2);
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();

                                } catch (IOException e) {

                                } catch (IllegalStateException ex) {
                                }
                                mediaPlayer.setOnCompletionListener(mp -> a.dismiss()
                                );
                            }
                        });

            }
        } else {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/audio_files/" + text.getTextid() + ".mp3"));

            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp ->
            a.dismiss());


        }
    }
    private boolean writeResponseBodyToDisk(ResponseBody body,int id) {
        try {
            // todo change the file location/name according to your needs
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cap.sfm/files/" + "audio_files/" + id + ".mp3");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
