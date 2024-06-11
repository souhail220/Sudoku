package com.example.sudoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RV_adapter extends RecyclerView.Adapter<RV_adapter.MyViewHolder> {

    Context context;
    ArrayList<RecycleModule> recycleModules;
    public RV_adapter(Context context, ArrayList<RecycleModule> recycleModules){
        this.context = context;
        this.recycleModules = recycleModules;
    }
    @NonNull
    @Override
    public RV_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving look to our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row,parent,false);
        return new RV_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RV_adapter.MyViewHolder holder, int position) {
        // assigning values to the views we created in the recycle_view_row layout file*
        // based on the position of the recycler view
        holder.userName.setText(recycleModules.get(position).getUserName());
        holder.userScore.setText(recycleModules.get(position).getScore());
        //holder.imageView.setImageResource(recycleModules.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return recycleModules.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from the recycler_view_row layout file
        // kinda look like  in the onCreate method
        ImageView imageView;
        TextView userName,userScore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_username);
            userScore = itemView.findViewById(R.id.user_score);

        }
    }
}
