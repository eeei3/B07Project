package com.example.b07project1;


import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


class ServerCommunicator{
    //final private String url;
    final FirebaseAuth mAuth;

    public ServerCommunicator(FirebaseAuth mAuth){
        //url = "https://b07projecttest-default-rtdb.firebaseio.com/";
        this.mAuth = mAuth;
    }

    boolean attempt(String email, String passwd) {
        final boolean[] ret = {false};
        mAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(task-> {
            ret[0] = task.isSuccessful();
        });
//                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            //updateUI(user);
//                            ret[0] = true;
//                        }
//                        else {
//                            //updateUI(null);
//                            ret[0] = false;
//                        }
//                    }
//                });
        return ret[0];
    }

    int rest_passwd(String email) {
        final int [] res = {1};
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            res[0] = 0;
                        }
                    }
                });
        return res[0];
    }

//    private void updateUI(FirebaseUser user) {
//
//    }

//    User fetch(String email, String passwd) {
//        final User [] data = {
//                new User()
//        };
//        final DatabaseReference user = FirebaseDatabase.getInstance(url).getReference("users");
//        user.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                data[0] = snapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//        return data[0];
//        //        user.child(this.email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
////            @Override
////            public void onComplete(@NonNull Task<DataSnapshot> task) {
////                if (!task.isSuccessful()) {
////                    Log.e("test", "Failed to retrieve user", task.getException());
////                } else {
////                    Log.i("test", task.getResult().getValue().toString());
////                }
////            }
////
////        });
//    }
}
