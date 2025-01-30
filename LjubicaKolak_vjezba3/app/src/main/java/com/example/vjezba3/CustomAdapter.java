package com.example.vjezba3;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    List<Note> localNotes;
    private OnItemClickListener onItemClickListener;
    

    public CustomAdapter(List<Note> notes, OnItemClickListener onItemClickListener) {
        this.localNotes = notes;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_holder, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(localNotes.get(position).name);
        holder.description.setText(localNotes.get(position).description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localNotes.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
