package com.cap.sfm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cap.sfm.Models.Conversation;
import com.cap.sfm.R;

public class ArrayConversationapdater extends ArrayAdapter<Conversation> {
    public ArrayConversationapdater(@NonNull Context context, int resource, @NonNull Conversation[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,parent,false);

        return super.getDropDownView(position, convertView, parent);
    }
}
