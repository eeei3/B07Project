package com.example.b07project1;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswd {
    final String url;

    public ForgotPasswd(String url) {
        this.url = url;
    }

    private User fetch(String email, String passwd) {
        final User [] data = {
                new User()
        };
        final DatabaseReference user = FirebaseDatabase.getInstance(url).getReference("users");
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data[0] = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return data[0];
        //        user.child(this.email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("test", "Failed to retrieve user", task.getException());
//                } else {
//                    Log.i("test", task.getResult().getValue().toString());
//                }
//            }
//
//        });
    }
}
