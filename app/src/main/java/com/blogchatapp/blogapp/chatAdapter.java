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


public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ChatViewHolder> {

    private Context mContext;
    private List<Chat> mData;

    public chatAdapter(Context mContext, List<Chat> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public chatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_chat,parent,false);
        return new ChatViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.ChatViewHolder holder, int position) {


        Glide.with(mContext).load(mData.get(position).getUimg()).into(holder.img);
        holder.chatName.setText(mData.get(position).getUname());
        holder.chatDesc.setText(mData.get(position).getChat());
        holder.chatDate.setText(timestampToString((long)mData.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView chatName,chatDesc,chatDate;

        public ChatViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.chatİmg);
            chatName = itemView.findViewById(R.id.chat_name);
            chatDesc = itemView.findViewById(R.id.chat_text);
            chatDate = itemView.findViewById(R.id.chat_Date);


        }
    }private String timestampToString(long time){
        Calendar calander =Calendar.getInstance(Locale.ENGLISH);
        calander.setTimeInMillis(time);
        String date  = DateFormat.format("hh:mm ", calander).toString();
        String aut = " Publish Date in ";
        return aut + date  ;
    }
}
