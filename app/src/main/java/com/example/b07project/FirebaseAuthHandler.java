package com.example.b07project;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * This is the Model portion of the Login Module. It handles login and password reset requests
 */
public class FirebaseAuthHandler extends Model{
    final FirebaseAuth mAuth;


    /**
     * ServerCommunicator - Default constructor, gets Firebase Authentication instance
     * and sets listener to null
     */
    public FirebaseAuthHandler() {
        this.mAuth = FirebaseAuth.getInstance();
        this.listener = null;
    }

    /**
     * login - For when the user attempts to log in
     * @param email - The user's email
     * @param passwd - The user's entered password
     * @param watcher - How we notify the Presenter that the results are ready
     */
    void login(String email, String passwd, AsyncAuthComms watcher) {
        Task<AuthResult> task =  mAuth.signInWithEmailAndPassword(email, passwd);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                watcher.setResult(task.isSuccessful());
                listener.onObjectReady(watcher);
                }
            }
        );
    }


    /**
     * resetPasswd - For when the user wants to reset their password.
     * @param email - The user's email
     * @param watcher - How we notify the Presenter that the results are ready
     */
    void resetPasswd(String email, AsyncAuthComms watcher) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            watcher.setResult(task.isSuccessful());
                            listener.onObjectReady(watcher);
                    }
                }
                );
    }
}
