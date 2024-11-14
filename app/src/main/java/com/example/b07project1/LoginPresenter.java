package com.example.b07project1;

import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    final private String email;
    final private String passwd;


    public LoginPresenter(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }


    public void BeginAuthenticate(ServerCommunicator socket, SuccessListener watcher) {
        socket.login(this.email, this.passwd, watcher);
    }

    public void BeginReset(ServerCommunicator socket, SuccessListener watcher) {
        socket.reset_passwd(email, watcher);
    }


}
