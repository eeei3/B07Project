package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


class ServerCommunicator extends Fragment {
    final FirebaseAuth mAuth;
    ModelPresenterPipe listener;

//    This is our listener that communicates with LoginPresenter
    public interface ModelPresenterPipe {
        void onObjectReady(SuccessListener betweener);
    }


    public ServerCommunicator() {
        this.mAuth = FirebaseAuth.getInstance();
        this.listener = null;
    }


    // Tell's Model which listener from Presenter to notify when operation is completed
    public void setModelPipe(ModelPresenterPipe listener) {
        this.listener = listener;
    }

    // Model Login Method
    void login(String email, String passwd, SuccessListener watcher) {
        Task<AuthResult> task =  mAuth.signInWithEmailAndPassword(email, passwd);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                watcher.setSuccess(task.isSuccessful());
                listener.onObjectReady(watcher);
            }
            }
        );
    }

    // Model Password Reset Method
    void reset_passwd(String email, SuccessListener watcher) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            watcher.setSuccess(task.isSuccessful());
                            listener.onObjectReady(watcher);
                    }
                });
    }
}
