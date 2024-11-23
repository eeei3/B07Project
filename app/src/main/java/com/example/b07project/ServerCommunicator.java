package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * This is the Model portion of the Login Module. It handles login and password reset requests
 */
class ServerCommunicator extends Fragment {
    final FirebaseAuth mAuth;
    ModelPresenterPipe listener;


    /**
     * ModelPresenterPipe - Interface representing the communication pipe (listener) between Model
     * and Presenter
     */
    public interface ModelPresenterPipe {
        void onObjectReady(SuccessListener betweener);
    }


    /**
     * ServerCommunicator - Default constructor, gets Firebase Authentication instance
     * and sets listener to null
     */
    public ServerCommunicator() {
        this.mAuth = FirebaseAuth.getInstance();
        this.listener = null;
    }


    /**
     * setModelPipe - To permit communications between Model and Presenter
     * @param listener - How we notify the Presenter that the results are ready
     */
    public void setModelPipe(ModelPresenterPipe listener) {
        this.listener = listener;
    }


    /**
     * login - For when the user attempts to log in
     * @param email - The user's email
     * @param passwd - The user's entered password
     * @param watcher - How we notify the Presenter that the results are ready
     */
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


    /**
     * reset_passwd - For when the user wants to reset their password.
     * @param email - The user's email
     * @param watcher - How we notify the Presenter that the results are ready
     */
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
