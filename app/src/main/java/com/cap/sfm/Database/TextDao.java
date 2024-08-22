package com.cap.sfm.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverter;
import androidx.room.Update;


import com.cap.sfm.Models.Conversation;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.Models.Text;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface TextDao {
    @Transaction
    @Query("SELECT * FROM ListTTS where listTTSid=:listTTSid")
    public ListTTSWithTexts getListTTSWithTexts(int listTTSid);

    @Transaction
    @Query("SELECT * FROM Conversation where conversationid=:conversationid")
    public ConversationWithTexts getListConversationWithTexts(int conversationid);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertListTTSTextref(ListTTSTextCrossRef listTTSTextCrossRef);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void inserConversation(Conversation conversation);

    @Query("SELECT * FROM Conversation")
    public List<Conversation> getListConversation();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertConversationTextCrossRef(ConversationTextCrossRef conversationTextCrossRef);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertText(Text text);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertListTTS(ListTTS listTTS);

    @Query("UPDATE text SET position=position+1 WHERE position>=0")
    public void updatePosition();




    @Query("UPDATE text SET position=position-1 WHERE position>=:pos")
    public void updatePosition2(int pos);

    @Query("SELECT * FROM text")
    public List<Text> getListText();

    @Query("SELECT * FROM ListTTS")
     List<ListTTS> getListTTS();

    @Update
    public void updateText(Text text);

    @Query("SELECT * FROM text ORDER BY position")
    public List<Text> getSortPosition();

    @Query("SELECT*FROM ConversationTextCrossRef where conversationid=:conversationid and textid=:textid")
    public ConversationTextCrossRef checkConversationTextCrossRef(int conversationid,int textid);

    @Query("SELECT*FROM listttstextcrossref where listTTSid=:listTTSid and textid=:textid")
    public ListTTSTextCrossRef checkListTtsTextCrossRef(int listTTSid,int textid);

    @Query("DELETE FROM ConversationTextCrossRef where conversationid=:conversationid")
    public void deleteAllTextConversation(int conversationid);

    @Query("DELETE FROM ListTTSTextCrossRef where listTTSid=:listTTSid and textid=:textid")
    public void deleteTextInListTTS(int listTTSid, int textid);

    @Query("DELETE FROM ConversationTextCrossRef where conversationid=:conversationid and textid=:textid")
    public void deleteTextInConversationCrossRef(int conversationid, int textid);

    @Query("SELECT * FROM Conversation where conversationid=:listTTSid")
    public Conversation getConversation(int listTTSid);

    @Query("DELETE FROM Conversation WHERE conversationid = :conversationid")
    public void deleteConversation(int conversationid);

    @Query("DELETE FROM text WHERE textid = :textid")
    public void deleteTex(int textid);

    @Query("DELETE FROM ConversationTextCrossRef WHERE conversationid = :conversationid")
    public void deleteConversationCrossRef(int conversationid);

    @Query("DELETE FROM ListTTSTextCrossRef WHERE listTTSid = :listTTSid")
    public void deleteListTTSCrossRef(int listTTSid);

    @Query("DELETE FROM ListTTS WHERE listTTSid = :listTTSid")
    public void deleteListTTS(int listTTSid);







}