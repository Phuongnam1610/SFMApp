package com.cap.sfm.Database;


import androidx.room.Entity;

@Entity(primaryKeys = {"listTTSid", "textid"})
    public class ListTTSTextCrossRef {
        public int listTTSid;
        public int textid;
    }


