package com.example.mireaproject;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<File> files;
    private OnFileClickListener listener;

    public interface OnFileClickListener {
        void onFileClick(File file);
    }

    public FileAdapter(List<File> files, OnFileClickListener listener) {
        this.files = files;
        this.listener = listener;
    }

    public void updateData(List<File> newFiles) {
        this.files = newFiles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        File file = files.get(position);
        holder.textView.setText(file.getName());
        holder.itemView.setOnClickListener(v -> listener.onFileClick(file));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(android.R.id.text1);
        }
    }
}
