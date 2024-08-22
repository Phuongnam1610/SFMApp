package com.cap.sfm.utilities;

import android.media.MediaPlayer;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Constants {
    public static final String KEY_APIKEY="api_key";
    public static final String KEY_PREFERENCE_NAME = "SFMPreference";
    public static final String KEY_SPEED="speed";
    public static final String KEY_SPEAKER_ID="speaker_id";
    public static final String KEY_IS_SIGNED_IN="isSignedIn";
    public static final String KEY_VOICE_NAME="voice";

    public static String covertToString(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}

