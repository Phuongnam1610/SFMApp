package com.cap.sfm.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cap.sfm.Models.ListTTS;
import com.cap.sfm.databinding.ItemContainerTtsBinding;
import com.cap.sfm.listener.listTextclicklistener;

import java.util.List;

public class MyLibraryAdapter extends RecyclerView.Adapter<MyLibraryAdapter.MyLibraryViewHolder>{

    private final List<ListTTS> listTTS;
    private final listTextclicklistener textclicklistener;
    private Context context;

    public MyLibraryAdapter(List<ListTTS> listTTS, listTextclicklistener Listtext,Context context) {
        this.context=context;
        this.listTTS = listTTS;
        this.textclicklistener = Listtext;
    }


    @NonNull
    @Override
    public MyLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerTtsBinding itemContainerTtsBinding = ItemContainerTtsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyLibraryViewHolder(itemContainerTtsBinding);
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

        private final ItemContainerTtsBinding binding;

        public MyLibraryViewHolder(ItemContainerTtsBinding itemContainerTtsBinding) {
            super(itemContainerTtsBinding.getRoot());
            binding = itemContainerTtsBinding;
        }
        void setData(ListTTS listTTS)
        {
//            binding.imageAction.setImageURI(Uri.parse(listTTS.getImage()));

            Glide.with(context)
                    .load(Uri.parse(listTTS.getImage()))
                    .into(binding.imageAction);
            binding.textNameLib.setText(listTTS.getTextCollection());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textclicklistener.onClickListTTSClicked(listTTS);
                }
            });
        }
    }
}
