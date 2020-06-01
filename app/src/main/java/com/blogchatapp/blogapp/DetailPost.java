package com.blogchatapp.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogchatapp.blogapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPost extends AppCompatActivity {
private ImageView PostImage;
private CircleImageView AuthProfile,CommitProfile,SendCommentProf;
private TextView HeaderPost,DescriptionPost,Datatime;
private EditText AddCommit;
private Button ShareCommitButton;
String mPost_key =null;
    DatabaseReference mDataBase;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RecComm;
    CommentAdapter CommentAdap;
    List<Comment> listComment;
    static String COMMENT_KEY ="Comment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        mDataBase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mPost_key =getIntent().getExtras().getString("blog_id");

        mAuth =FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        RecComm = findViewById(R.id.RecyCom);
        PostImage = findViewById(R.id.imageViewİnt);
        AuthProfile = findViewById(R.id.profileAutİmg);
        CommitProfile = findViewById(R.id.profileCommitİmg);
        Datatime = findViewById(R.id.Datetimeİnt);
        DescriptionPost = findViewById(R.id.Descriptionİnt);
        HeaderPost = findViewById(R.id.Headerİnt);
        SendCommentProf = (CircleImageView) findViewById(R.id.CommitProf) ;
        AddCommit = findViewById(R.id.Commitİnt);
        ShareCommitButton = findViewById(R.id.ShareCommitİnt);

        firebaseDatabase = FirebaseDatabase.getInstance();


        ShareCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference commitReference = firebaseDatabase.getReference(COMMENT_KEY).child(mPost_key).push();
                String comment_content = AddCommit.getText().toString();
                String uid = currentUser.getUid();
                String uname =currentUser.getDisplayName();
                String uimg = currentUser.getPhotoUrl().toString();
                Comment comment = new Comment(comment_content,uid,uimg,uname);

                commitReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        showMessage("Comment Add Successfulyy.");
                        AddCommit.setText("");

                    }
                });
            }
        });

        mDataBase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String post_Header = (String) dataSnapshot.child("header").getValue();
                String post_Entry = (String) dataSnapshot.child("entry").getValue();
                String post_ProfileImage = (String) dataSnapshot.child("prof").getValue();
                String post_PostImage = (String) dataSnapshot.child("image").getValue();
                long timestamp = (long) dataSnapshot.child("timestamp").getValue();
                Glide.with(DetailPost.this).load(post_ProfileImage).into(AuthProfile);
                Glide.with(DetailPost.this).load(post_PostImage).into(PostImage);
                Glide.with(DetailPost.this).load(currentUser.getPhotoUrl()).into(CommitProfile);
                String timeStr = timestampToString(timestamp);
                HeaderPost.setText(post_Header);
                DescriptionPost.setText(post_Entry);
                Datatime.setText(timeStr);
                FirebaseDatabase firebaseDatabase;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        iniRec();
    }

    private void iniRec() {

        RecComm.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference CommentRef = firebaseDatabase.getReference(COMMENT_KEY).child(mPost_key);
        CommentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()){
                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment);
                }
                CommentAdap = new CommentAdapter(getApplicationContext(),listComment);
                RecComm.setAdapter(CommentAdap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showMessage(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();


    }private String timestampToString(long time){
        Calendar calander =Calendar.getInstance(Locale.ENGLISH);
        calander.setTimeInMillis(time);
        String date  = DateFormat.format("in dd.MM.yyyy ", calander).toString();
        String aut = " and Author";
        return date + aut ;
    }



}
