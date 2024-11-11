package com.example.b07project1;


import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.concurrent.TimeUnit;


class ServerCommunicator extends Fragment {
    //final private String url;
    final FirebaseAuth mAuth;

    public ServerCommunicator(){
        //url = "https://b07projecttest-default-rtdb.firebaseio.com/";
        this.mAuth = FirebaseAuth.getInstance();
    }

    boolean login(String email, String passwd) {
        final boolean[] ret = {false, true};
        int a = 0;
        Task<AuthResult> task =  mAuth.signInWithEmailAndPassword(email, passwd);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ret[0] = task.isSuccessful();
            }
        });
        return true;
    }

    FirebaseUser attempt(String email, String passwd) {
        if (login(email, passwd)) {
            return mAuth.getCurrentUser();
        }
        return null;


//        mAuth.signInWithEmailAndPassword(email, passwd)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        ret[1] = false;
//                        ret[0] = task.isSuccessful();
//                    }
//                });
//                .addOnCompleteListener(task -> {
//                    ret[1] = false;
//            ret[0] = task.isSuccessful();
//        });
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

    }

    int reset_passwd(String email) {
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
