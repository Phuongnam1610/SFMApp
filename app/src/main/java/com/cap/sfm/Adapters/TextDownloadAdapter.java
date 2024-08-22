package com.cap.sfm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cap.sfm.Models.Text;
import com.cap.sfm.databinding.ItemContainerTextBinding;
import com.cap.sfm.listener.alltextListener;

import java.util.List;

public class TextDownloadAdapter extends RecyclerView.Adapter<TextDownloadAdapter.AllTextViewHolder> {

    private final List<Text> listText;
    private final alltextListener alltextListener;

    public TextDownloadAdapter(List<Text> listText, alltextListener alltextListener) {
        this.listText = listText;
        this.alltextListener=alltextListener;
    }

    @NonNull
    @Override
    public AllTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerTextBinding itemContainerTextBinding = ItemContainerTextBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AllTextViewHolder(itemContainerTextBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTextViewHolder holder, int position) {
    holder.setData(listText.get(position));
    }

    @Override
    public int getItemCount() {
        return listText.size();
    }

    public class AllTextViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerTextBinding binding;


        public AllTextViewHolder(ItemContainerTextBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(Text text)
        {
            binding.text.setText(text.getText());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alltextListener.onAllTextClicked(text);
                }
            });
        }
    }

}
