package com.cap.sfm.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.databinding.ItemContainerTextBinding;
import com.cap.sfm.listener.alltextListener;

import java.util.ArrayList;
import java.util.List;

public class Tab0TextAdapter extends RecyclerView.Adapter<Tab0TextAdapter.AllTextViewHolder> {

    public final List<Text> listText;
    private final alltextListener alltextListener;
    public boolean checkboxvisibility = false;
    public ArrayList<Text> selection;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();


    public Tab0TextAdapter(List<Text> listText, alltextListener alltextListener) {
        this.listText = listText;
        this.alltextListener=alltextListener;
        this.selection = new ArrayList<>();

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
        holder.binding.cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.animationnha));
        if (checkboxvisibility == true) {
         holder.   binding.checkbox.setVisibility(View.VISIBLE);

        } else {
            itemStateArray.clear();

            holder.    binding.checkbox.setChecked(false);
            holder.    binding.checkbox.setVisibility(View.GONE);}
        holder.bind(position);
//        holder.binding.checkbox.setChecked(checkStates[position]);
//        holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckBox c = (CheckBox) v;
//                checkStates[holder.getAdapterPosition()] = c.isChecked();
//                for (int i = 0; i < checkStates.length; i++) {
//                    Log.d("qua13",String.valueOf(checkStates[i]));
//
//                }
//
//            }
//        });

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

            binding.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (!itemStateArray.get(adapterPosition, false)) {
                        selection.add(listText.get(adapterPosition));
                        Log.d("qua15",String.valueOf(selection.size()));
                        binding.checkbox.setChecked(true);
                        itemStateArray.put(adapterPosition, true);
                    }
                    else  {
                        selection.remove(listText.get(adapterPosition));
                        binding.checkbox.setChecked(false);
                        itemStateArray.put(adapterPosition, false);
                    }

                } });




        }
        void bind(int position) {
            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {
                binding.checkbox.setChecked(false);}
            else {
                binding.checkbox.setChecked(true);
            }}


        void setData(Text text) {



            binding.text.setText(text.getText());
            binding.getRoot().setOnClickListener(v -> alltextListener.onAllTextClicked(text));
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    alltextListener.onLongTextClicked(text,getAdapterPosition());
                    return false;
                }
            });
        }
    }




}
