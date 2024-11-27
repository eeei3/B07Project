package com.example.b07project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServerCommunicator {
    final private FirebaseDatabase db;
    final private DatabaseReference dbworker;
    final private String userID;
    final private SurveyActivity view;


    public ServerCommunicator(SurveyActivity view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.userID = String.valueOf(mAuth.getCurrentUser());
        this.db = FirebaseDatabase.getInstance("https://b07project-b43b0-default-rtdb.firebaseio.com/");
        this.dbworker = db.getReference();
        this.view = view;
    }

    public void writeResult(int result) {
        dbworker.child("users").child(userID).child("emissions").setValue(result)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            view.success();
                        }
                        else {
                            view.failure();
                        }
                    }
                });
    }
}
