package com.example.cs4322.Favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4322.R;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private ArrayList<FavoriteItem> list;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        public TextView t1;
        public TextView t2;
        public TextView t3;
        public Button delete;

        public FavoritesViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            t1 = itemView.findViewById(R.id.titleText);
            t2 = itemView.findViewById(R.id.AuthorName);
            t3 = itemView.findViewById(R.id.ISBNnumber);
            delete = itemView.findViewById(R.id.deleteBtn);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onDelete(position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public FavoritesAdapter(ArrayList<FavoriteItem> favoriteList) {
        list = favoriteList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        FavoritesViewHolder fvh = new FavoritesViewHolder(v, clickListener);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        FavoriteItem current = list.get(position);

        holder.t1.setText(current.getText1());
        holder.t2.setText(current.getText2());
        holder.t3.setText(current.getText3());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
