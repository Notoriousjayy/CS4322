package com.example.cs4322.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4322.R;

import java.util.ArrayList;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>    {
    private ArrayList<BookItem> list;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;

        public ResultsViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            t1 = itemView.findViewById(R.id.titleText);
            t2 = itemView.findViewById(R.id.ISBNnumber);
            t3 = itemView.findViewById(R.id.AuthorName);

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

    public ResultsAdapter(ArrayList<BookItem> resultsList) {
        list = resultsList;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_result, parent, false);
        ResultsViewHolder rvh = new ResultsViewHolder(v, clickListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        BookItem current = list.get(position);

        holder.t1.setText(current.getTitle());
        holder.t2.setText(current.getISBN());
        holder.t3.setText(current.getAuthor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
