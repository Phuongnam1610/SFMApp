package com.cap.sfm.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Conversation")
public class Conversation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int conversationid;
    private String textConversation;

    public int getConversationid() {
        return conversationid;
    }

    public void setConversationid(int id) {
        this.conversationid = id;
    }

    public String getTextConversation() {
        return textConversation;
    }

    public void setTextConversation(String textConversation) {
        this.textConversation = textConversation;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getTextConversation();
    }
}
