package com.cap.sfm.Network;


import android.net.Uri;

import com.cap.sfm.utilities.Constants;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("v5")
    Call<String> sendText(@Header(Constants.KEY_APIKEY) String api, @Header(Constants.KEY_SPEED) float speed, @Header(Constants.KEY_VOICE_NAME) String voice , @Body() String text);


    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
