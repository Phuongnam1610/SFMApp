package com.cap.sfm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.cap.sfm.Network.ApiClient;
import com.cap.sfm.Network.ApiService;
import com.cap.sfm.databinding.ActivityMainBinding;
import com.cap.sfm.databinding.Tab3fragmentBinding;
import com.cap.sfm.fragments.Tab0Fragment;
import com.cap.sfm.fragments.Tab1Fragment;
import com.cap.sfm.fragments.Tab2Fragment;
import com.cap.sfm.fragments.Tab3Fragment;
import com.cap.sfm.utilities.Constants;
import com.cap.sfm.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    PreferenceManager preferenceManager;
    Tab0Fragment tab0Fragment = new Tab0Fragment();
    Tab1Fragment tab1Fragment = new Tab1Fragment();
    Tab2Fragment tab2Fragment = new Tab2Fragment();
    Fragment activefragment = tab0Fragment;
    boolean doubleBackToExitPressedOnce = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());


        preferenceManager = new PreferenceManager(getApplicationContext());

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(android.R.anim.fade_in)
                .setExitAnim(android.R.anim.fade_in)
                .setPopEnterAnim(android.R.anim.fade_in)
                .setPopExitAnim(android.R.anim.fade_in)
                .build();
//
//        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case (R.id.tab0Fragment1):
//getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,tab0Fragment,"3").addToBackStack("3").commit();
//return true;
//                    case (R.id.tab1Fragment1):
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,tab1Fragment,"2").addToBackStack("2").commit();
//                            return  true;
//                    case (R.id.tab2Fragment1):
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,tab1Fragment,"1").addToBackStack("1").commit();
//
//                        return true;
//                }
//                return false;
//            }
//        });
        findViewById(R.id.bottomsheet).setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
            dialog.getWindow().setWindowAnimations(com.google.android.material.R.style.Animation_Design_BottomSheetDialog);
            Tab3fragmentBinding binding1 = Tab3fragmentBinding.inflate(getLayoutInflater());
            dialog.setContentView(binding1.getRoot());
            binding1.apikey.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    Log.d("hoa",String.valueOf(keyCode));
                        if ( (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            Log.d("hoa","ban vua an enter");
                        closeKeyboard();
                            // Perform action on key press

                        }

                    return false;}
            });
            binding1.okey.setOnClickListener(v1 -> {
                Log.d("ban","ban vua an okey");

                if (binding1.apikey.getText().length() > 0) {

                    String apikey = binding1.apikey.getText().toString();
                    ApiClient.getClient().create(ApiService.class).sendText(apikey, 0, "banmai", "ApiKey").enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 401) {
                                Toast.makeText(MainActivity.this, "Sai apikey", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Đúng apikey", Toast.LENGTH_SHORT).show();

                                preferenceManager.putString(Constants.KEY_APIKEY, apikey);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                }
            });
            ArrayList<String> voice = new ArrayList<>();
            voice.add("Bạn Mai Nữ Miền Bắc");
            voice.add("Thu Minh Nữ Miền Bắc");
            voice.add("Mỹ An Nữ Miền Trung");
            voice.add("Gia Huy Nam Miền Trung");
            voice.add("Ngọc Lam Nữ Miền Trung");
            voice.add("Lê Minh Nam Miền Bắc");
            voice.add("Minh Quang Nam Miền Nam");
            voice.add("Lan Nhi Nữ Miền Nam");
            ArrayAdapter<String> voiceadapter = new ArrayAdapter<String>(this, R.layout.spinneritem, voice);
            binding1.voice.setAdapter(voiceadapter);
            if(preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("banmai"))
            {
                binding1.voice.setSelection(0);
            }
            else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("thuminh"))
            {
                binding1.voice.setSelection(1);


            }
            else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("myan"))
            {
                binding1.voice.setSelection(2);


            }
            else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("giahuy"))
            {
                binding1.voice.setSelection(3);


            }
            else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("ngoclam"))
            {
                binding1.voice.setSelection(4);


            }
            else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("leminh"))
            {
                binding1.voice.setSelection(5);


            } else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("minhquang"))
            {
                binding1.voice.setSelection(6);


            }
            else if (preferenceManager.getVoice(Constants.KEY_VOICE_NAME).equals("lannhi"))
            {
                binding1.voice.setSelection(7);


            }

            binding1.voice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "banmai");
                        binding1.sex.setImageResource(R.drawable.woman);
                    } else if (position == 1) {
                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "thuminh");
                        binding1.sex.setImageResource(R.drawable.woman);
                    }
                    else if (position == 2) {

                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "myan");
                        binding1.sex.setImageResource(R.drawable.woman);
                    }
                    else if (position == 3) {

                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "giahuy");
                        binding1.sex.setImageResource(R.drawable.man);
                    }
                    else if (position == 4) {
                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "ngoclam");
                        binding1.sex.setImageResource(R.drawable.woman);

                    }
                    else if (position == 5) {
                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "leminh");
                        binding1.sex.setImageResource(R.drawable.man);

                    }
                    else if (position == 6) {
                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "minhquang");
                        binding1.sex.setImageResource(R.drawable.man);

                    }
                    else if (position == 7) {
                        preferenceManager.putString(Constants.KEY_VOICE_NAME, "lannhi");
                        binding1.sex.setImageResource(R.drawable.woman);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding1.catlot.setSpeed(preferenceManager.getFloat(Constants.KEY_SPEED)+3);

            binding1.seekBarSpeed.setProgress((int) (preferenceManager.getFloat(Constants.KEY_SPEED)/0.5));
            binding1.seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {


                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    float speed = (float) (seekBar.getProgress()*0.5);
                    preferenceManager.putFloat(Constants.KEY_SPEED,speed);
                    Log.d("conha0",String.valueOf(seekBar.getProgress()));
                    Log.d("conha",String.valueOf(speed));
                    binding1.catlot.setSpeed(speed+3);



                }
            });
            dialog.show();
        });


    }





    public static void insertListTTS(ListTTS listTTS, Context context) {
        AllDatabase.getDatabase(context).textDao().insertListTTS(listTTS);
    }

    public static ArrayList<ListTTS> getListTTS(Context context) {
        return (ArrayList<ListTTS>) AllDatabase.getDatabase(context).textDao().getListTTS();
    }

    public static void insertText(Text text, Context context) {
        AllDatabase.getDatabase(context).textDao().insertText(text);
    }

    public static ArrayList<Text> getAllText(Context context) {
        return (ArrayList<Text>) AllDatabase.getDatabase(context).textDao().getListText();
    }




    public static List<Text> getSortPosition(Context context) {
        return AllDatabase.getDatabase(context).textDao().getSortPosition();
    }


    private void closeKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }}