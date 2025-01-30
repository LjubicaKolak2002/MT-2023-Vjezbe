package com.example.vjezba4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.content.Context;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Course> dataList;
    private Context context;
    public CustomAdapter(Context context, List<Course> dataList ){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        TextView txtName;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtName = mView.findViewById(R.id.textView2);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_view_holder, parent, false);
        return new CustomViewHolder(view);
    }
    public void setDataList(List<Course> newDataList) {
        this.dataList = newDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course currentCourse = dataList.get(position);
        holder.txtName.setText(currentCourse.getIme());

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}