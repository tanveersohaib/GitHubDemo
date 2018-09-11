package com.example.sohaibtanveer.githubdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PathRecyclerViewAdapter extends RecyclerView.Adapter<PathRecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<String> items;
    private PathClickListener clickListener;

    PathRecyclerViewAdapter(ArrayList<String> items){
        this.items = items;
    }

    public void setClickListener(PathClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView pathName;

        CustomViewHolder(final View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            pathName = (TextView) itemView.findViewById(R.id.pathName);
        }

        @Override
        public void onClick(View v) {
            clickListener.onclick(v,items.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.path_layout, parent, false);
        return new PathRecyclerViewAdapter.CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.pathName.setText(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(String newPath){
        items.add(newPath);
        this.notifyDataSetChanged();
    }

    public void removeItem(String pathName){
        int index = items.indexOf(pathName);
        int size = items.size();
        for(int i = size - 1; i >=index;i--){
            items.remove(i);
        }
        this.notifyDataSetChanged();
    }

}
