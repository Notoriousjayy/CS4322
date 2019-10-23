package com.example.cs4322.Capture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4322.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>    {
    private ArrayList<BookItem> list;

    public static class ResultsViewHolder extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;

        public ResultsViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.titleText);
            t2 = itemView.findViewById(R.id.ISBNnumber);
            t3 = itemView.findViewById(R.id.AuthorName);
        }
    }

    public ResultsAdapter(ArrayList<BookItem> resultsList) {
        list = resultsList;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_result, parent, false);
        ResultsViewHolder rvh = new ResultsViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        BookItem current = list.get(position);

        holder.t1.setText(current.getText1());
        holder.t2.setText(current.getText2());
        holder.t3.setText(current.getText3());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}