package com.blogchatapp.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blogchatapp.blogapp.R;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatPage extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    EditText Chat;
    FloatingActionButton Send;
    CircleImageView send_İmg;
    String uİd;
    RecyclerView RecChat;
    List<Chat> listChat;
    chatAdapter chatPageAdap;
    static Uri cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        Chat = findViewById(R.id.ChatWriteText);
        Send = findViewById(R.id.ChatShareButton);
        send_İmg = findViewById(R.id.chat_img);
        RecChat = findViewById(R.id.recChat);

        mAuth =FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        cr = currentUser.getPhotoUrl();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uİd = currentUser.getUid();
                DatabaseReference chatReferance = firebaseDatabase.getReference("Chat").push();
                String Send_Chat = Chat.getText().toString();
                String uname =currentUser.getDisplayName();
                String uimg = currentUser.getPhotoUrl().toString();

                Chat ch = new Chat(uİd,uname,Send_Chat,uimg);
                chatReferance.setValue(ch);
                Chat.setText("");

            }
        });
        Glide.with(this).load(cr).into(send_İmg);
        iniRec();
    }private void iniRec() {

        RecChat.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference CommentRef = firebaseDatabase.getReference("Chat");
        CommentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listChat = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()){
                    Chat chatt = snap.getValue(com.blogchatapp.blogapp.Chat.class);
                    listChat.add(chatt);
                }
                chatPageAdap = new chatAdapter(getApplicationContext(),listChat);
                RecChat.setAdapter(chatPageAdap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
