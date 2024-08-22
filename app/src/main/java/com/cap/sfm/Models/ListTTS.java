package com.cap.sfm.Models;

import android.net.Uri;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "ListTTS")
public class ListTTS {

    @PrimaryKey(autoGenerate = true)
    private int listTTSid;
    private String textCollection;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ListTTS() {
    }

    public String getTextCollection() {
        return textCollection;
    }

    public void setTextCollection(String textCollection) {
        this.textCollection = textCollection;
    }


    public int getListTTSid() {
        return listTTSid;
    }

    public void setListTTSid(int listTTSid) {
        this.listTTSid = listTTSid;
    }
}
