package com.cap.sfm.Database;

import androidx.room.Entity;

@Entity(primaryKeys = {"textid","conversationid"})
public class ConversationTextCrossRef {
    public int textid;
    public int conversationid;

}
