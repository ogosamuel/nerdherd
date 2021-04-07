package com.example.nerdherd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    ArrayList<Reply> replies;

    public ReplyAdapter(ArrayList<Reply> replies){
        this.replies = replies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        View layout;

        public ViewHolder(View view){
            super(view);
            content = view.findViewById(R.id.reply_content);
            layout = view.findViewById(R.id.reply_list_layout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_reply, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ViewHolder holder, int position) {
        Reply targetReply = replies.get(position);
        holder.content.setText(targetReply.getContent());
        holder.content.setTextColor(0xFF000000 + Integer.parseInt("00212F3C",16));
        if (position % 2 == 0)
            holder.layout.setBackgroundColor(0xFF000000 + Integer.parseInt("005DADE2",16));
        else
            holder.layout.setBackgroundColor(0xFF000000 + Integer.parseInt("003498DB",16));
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }
}