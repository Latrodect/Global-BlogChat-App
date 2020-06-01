package com.blogchatapp.blogapp;

import com.google.firebase.database.ServerValue;

public class Chat {
    private String uid;
    private String uname;
    private String chat;

    public Chat(String uid, String uname, String chat, String uimg) {
        this.uid = uid;
        this.uname = uname;
        this.chat = chat;
        this.uimg = uimg;
        timestamp = ServerValue.TIMESTAMP;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    private String uimg;
    private Object timestamp;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Chat(String uid, String uname, String chat) {
        this.uid = uid;
        this.uname = uname;
        this.chat = chat;
    }

    public Chat(String uid, String uname, String chat, Object timestamp) {
        this.uid = uid;
        this.uname = uname;
        this.chat = chat;
        this.timestamp = timestamp;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public Chat() {
    }
}
