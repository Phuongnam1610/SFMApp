package com.cap.sfm.Network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.fpt.ai/hmi/tts/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
