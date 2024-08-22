package com.cap.sfm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cap.sfm.Models.Text;
import com.cap.sfm.databinding.ItemContainerListlistttsBinding;
import com.cap.sfm.listener.alltextListener;
import com.cap.sfm.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class AlltextdownloadedAdapter  extends RecyclerView.Adapter<AlltextdownloadedAdapter.MyLibraryViewHolder> implements Filterable{

    private final List<Text> textList;
    private final alltextListener textclicklistener;
    private List<Text> listTextFilter;


    public AlltextdownloadedAdapter(List<Text> textList, alltextListener  Listtext) {
        this.textList = textList;
        this.textclicklistener = Listtext;
        this.listTextFilter = new ArrayList<>(textList);
    }


    @NonNull
    @Override
    public MyLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerListlistttsBinding itemContainerListlistttsBinding = ItemContainerListlistttsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyLibraryViewHolder(itemContainerListlistttsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLibraryViewHolder holder, int position) {
    holder.setData(textList.get(position));
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
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
                textList.clear();
                textList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    class MyLibraryViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerListlistttsBinding binding;

        public MyLibraryViewHolder(ItemContainerListlistttsBinding itemContainerTtsBinding) {
            super(itemContainerTtsBinding.getRoot());
            this.binding = itemContainerTtsBinding;
        }
        void setData(Text text)
        {
            binding.textname.setText(text.getText());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textclicklistener.onAllTextClicked(text);
                }
            });
        }
    }
}
