package com.example.vjezba2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Repository> dataList;
    private Context context;

    public CustomAdapter(Context context,List<Repository> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        TextView txtTitle;
        TextView txtTitle2;
        ImageView avatarImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.textView2);
            txtTitle2 = mView.findViewById(R.id.textView3);
            avatarImage = mView.findViewById(R.id.imageView);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout, parent, false);
        return new CustomViewHolder(view);
    }
    public void setDataList(List<Repository> newDataList) {
        this.dataList = newDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getName());
        holder.txtTitle2.setText(dataList.get(position).getStargazers_count().toString());

        Glide.with(context)
                .load(dataList.get(position).getOwner().getAvatar_url())
                .override(350, 350)
                .into(holder.avatarImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}