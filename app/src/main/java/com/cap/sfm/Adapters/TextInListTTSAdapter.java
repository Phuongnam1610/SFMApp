package com.cap.sfm.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cap.sfm.Database.AllDatabase;
import com.cap.sfm.Models.Text;
import com.cap.sfm.R;
import com.cap.sfm.databinding.ItemContainerTextBinding;
import com.cap.sfm.listener.alltextListener;
import com.cap.sfm.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class TextInListTTSAdapter extends RecyclerView.Adapter<TextInListTTSAdapter.AllTextViewHolder> implements Filterable {

    public  List<Text> listText;
    private final alltextListener alltextListener;
    public boolean checkboxvisibility = false;
    public List<Text> listTextFilter;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();



    public ArrayList<Text> selection;

    public TextInListTTSAdapter(List<Text> listText, alltextListener alltextListener) {
        this.listText = listText;
        this.alltextListener = alltextListener;
        this.listTextFilter = new ArrayList<>(listText);
        this.selection= new ArrayList<Text>();

    }


    @NonNull
    @Override
    public AllTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerTextBinding itemContainerTextBinding = ItemContainerTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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


    }

    @Override
    public int getItemCount() {
        return listText.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Text> filteredList = new ArrayList<>();
            if (constraint.length()==0||constraint.toString().isEmpty()) {
                filteredList.addAll(listTextFilter);
            } else {
                for (Text text : listTextFilter) {
                    if (text.getText().toLowerCase().contains(constraint.toString().toLowerCase()) || Constants.covertToString(text.getText()).toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(text);
                    }

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listText.clear();
            listText.addAll((List) results.values);
            notifyDataSetChanged();


        }
    };


    public class AllTextViewHolder extends RecyclerView.ViewHolder {

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



        void setData(Text text) {
//            if (//condition for visibility) {
//            binding.checkbox.setVisibility(View.VISIBLE);
//        } else
//
//        {
//            binding.checkbox.setVisibility(View.GONE);
//        }

            binding.text.setText(text.getText());

            binding.getRoot().setOnClickListener(v -> alltextListener.onAllTextClicked(text));
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            alltextListener.onLongTextClicked(text, getAdapterPosition());
                            return false;
                        }
                    });
        }
        void bind(int position) {
            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {
                binding.checkbox.setChecked(false);}
            else {
                binding.checkbox.setChecked(true);
            }}

    }

}
