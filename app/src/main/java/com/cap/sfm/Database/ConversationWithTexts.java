package com.cap.sfm.Database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.cap.sfm.Models.Conversation;
import com.cap.sfm.Models.Text;

import java.util.List;

public class ConversationWithTexts {
    @Embedded
    public Conversation conversation;
    @Relation(parentColumn = "conversationid",
            entityColumn = "textid",
            associateBy = @Junction(ConversationTextCrossRef.class)
                )
    public List<Text> Texts;
}
