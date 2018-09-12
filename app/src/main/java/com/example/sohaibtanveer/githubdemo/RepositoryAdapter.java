package com.example.sohaibtanveer.githubdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.CustomViewHolder> {

        private List<Item> dataList;
        private Context context;
        private ItemClickListener clickListener;

        public RepositoryAdapter(Context context,List<Item> dataList){
            this.context = context;
            this.dataList = dataList;
        }

        public void setClickListener(ItemClickListener itemClickListener){
            this.clickListener = itemClickListener;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public final View mView;
            ImageView avatar;
            TextView name;
            TextView star;
            TextView fork;
            TextView updatedAt;

            CustomViewHolder(final View itemView) {
                super(itemView);
                mView = itemView;
                mView.setOnClickListener(this);

                name = mView.findViewById(R.id.name);
                star = mView.findViewById(R.id.star);
                avatar = mView.findViewById(R.id.avatar);
                fork = mView.findViewById(R.id.fork);
                updatedAt = mView.findViewById(R.id.updatedAt);
            }

            @Override
            public void onClick(View v) {
                clickListener.onClick(v,dataList.get(getAdapterPosition()));
            }
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.repositor_view, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            Picasso.get().load(dataList.get(position).getOwner().getAvatarUrl()).resize(160,160).into(holder.avatar);
            holder.name.setText(dataList.get(position).getFullName());
            holder.star.setText(dataList.get(position).getStargazersCount().toString());
            holder.fork.setText(dataList.get(position).getForksCount().toString());
            holder.updatedAt.setText(dataList.get(position).getUpdatedAt());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void loadData(List<Item> data){
            dataList = data;
            this.notifyDataSetChanged();
        }
}
