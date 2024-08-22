package com.cap.sfm.Database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;

import java.util.List;

public class ListTTSWithTexts {
    @Embedded
    public ListTTS listTTS;
    @Relation(
            parentColumn = "listTTSid",
            entityColumn = "textid",
            associateBy = @Junction(ListTTSTextCrossRef.class)
    )
    public List<Text> texts;
}