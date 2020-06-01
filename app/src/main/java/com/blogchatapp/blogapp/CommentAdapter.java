package com.blogchatapp.blogapp;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogchatapp.blogapp.R;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mData;

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_comment,parent,false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        Glide.with(mContext).load(mData.get(position).getUimg()).into(holder.img);
        holder.CommentProf.setText(mData.get(position).getUname());
        holder.CommentDesc.setText(mData.get(position).getContent());
        holder.CommentDate.setText(timestampToString((long)mData.get(position).getTimestamp()));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }










    public class CommentViewHolder extends RecyclerView.ViewHolder{


        CircleImageView img;
        TextView CommentProf,CommentDesc,CommentDate;

        public CommentViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.CommitProf);
            CommentProf =itemView.findViewById(R.id.ComNam);
            CommentDate = itemView.findViewById(R.id.ComDat);
            CommentDesc = itemView.findViewById(R.id.CommitDesc);
        }
    }private String timestampToString(long time){
        Calendar calander =Calendar.getInstance(Locale.ENGLISH);
        calander.setTimeInMillis(time);
        String date  = DateFormat.format("hh:mm ", calander).toString();
        String aut = " Publish Date in ";
        return aut + date  ;
    }
}
