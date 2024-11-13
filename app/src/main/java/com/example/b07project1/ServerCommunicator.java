package com.example.b07project1;




import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;


class ServerCommunicator extends Fragment {
    final FirebaseAuth mAuth;
    int res;
    boolean successful;
    private EmailListener listener;

    public interface EmailListener {
        public void onObjectReady(MailMan betweener);
    }


    public ServerCommunicator(){
        this.mAuth = FirebaseAuth.getInstance();
        this.res = 1;
        this.successful = false;
        this.listener = null;
    }


    public void setEmailListener(EmailListener listener) {
        this.listener = listener;
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

    }

    int reset_passwd(String email, MailMan watcher) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            ServerCommunicator.this.res = 0;
                            watcher.setSuccess(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            watcher.setSuccess(false);
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        Log.e("Res Tag", "value of res is:" + this.res);
        return res;
    }
}
