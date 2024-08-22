package com.cap.sfm.Models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "text")
public class Text implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int textid;
    private String text;
    @NonNull
    private int position;
    @NonNull
    private int saved;


    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public int getTextid() {
        return textid;
    }

    public void setTextid(int textid) {
        this.textid = textid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }




}
