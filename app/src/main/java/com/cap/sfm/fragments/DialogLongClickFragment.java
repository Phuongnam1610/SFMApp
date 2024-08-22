package com.cap.sfm.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.cap.sfm.Adapters.ListListTTSAdapter;
import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Database.ListTTSTextCrossRef;
import com.cap.sfm.MainActivity;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.cap.sfm.Network.ApiClient;
import com.cap.sfm.Network.ApiService;
import com.cap.sfm.R;
import com.cap.sfm.databinding.DialoglistlistttsBinding;
import com.cap.sfm.databinding.DialoglongclicktextBinding;
import com.cap.sfm.listener.listTextclicklistener;
import com.cap.sfm.utilities.Constants;
import com.cap.sfm.utilities.PreferenceManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogLongClickFragment extends DialogFragment {

    private DialoglongclicktextBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Text text = (Text) getArguments().getSerializable("text");
        binding = DialoglongclicktextBinding.inflate(inflater,container,false);
        setCancelable(true);

        return binding.getRoot();
    }




}
