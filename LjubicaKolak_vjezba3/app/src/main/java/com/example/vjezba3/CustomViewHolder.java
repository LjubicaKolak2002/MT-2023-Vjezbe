package com.example.vjezba3;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView name, description;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        this.name = (TextView)itemView.findViewById(R.id.nameViewHolder);
        this.description = (TextView)itemView.findViewById(R.id.descViewHolder);
    }
}
