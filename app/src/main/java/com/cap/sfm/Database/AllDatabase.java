package com.cap.sfm.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Dao;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.cap.sfm.Models.Conversation;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;

import java.util.List;


@Database(entities = {Text.class,ListTTS.class,ListTTSTextCrossRef.class,ConversationTextCrossRef.class, Conversation.class},version = 1)
public abstract class AllDatabase extends RoomDatabase {
    public abstract TextDao textDao();
    private static AllDatabase instance;
    public static synchronized AllDatabase getDatabase(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),AllDatabase.class,"alldatabase.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }





}

