package com.cap.sfm.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.databinding.ItemContainerListlisttts2Binding;
import com.cap.sfm.databinding.ItemContainerListlistttsBinding;
import com.cap.sfm.databinding.ItemContainerTtsBinding;
import com.cap.sfm.listener.listTextclicklistener;

import java.util.List;

public class ListListTTSAdapter extends RecyclerView.Adapter<ListListTTSAdapter.MyLibraryViewHolder>{

    private final List<ListTTS> listTTS;
    private final listTextclicklistener textclicklistener;

    public ListListTTSAdapter(List<ListTTS> listTTS, listTextclicklistener Listtext) {
        this.listTTS = listTTS;
        this.textclicklistener = Listtext;
    }


    @NonNull
    @Override
    public MyLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerListlisttts2Binding itemContainerListlistttsBinding = ItemContainerListlisttts2Binding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyLibraryViewHolder(itemContainerListlistttsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLibraryViewHolder holder, int position) {
    holder.setData(listTTS.get(position));
    }

    @Override
    public int getItemCount() {
        return listTTS.size();
    }

    class MyLibraryViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerListlisttts2Binding binding;

        public MyLibraryViewHolder(ItemContainerListlisttts2Binding itemContainerTtsBinding) {
            super(itemContainerTtsBinding.getRoot());
            this.binding = itemContainerTtsBinding;
        }
        void setData(ListTTS listTTS)
        {
            binding.textname.setText(listTTS.getTextCollection());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textclicklistener.onClickListTTSClicked(listTTS);
                }
            });
        }
    }
}
