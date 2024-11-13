package com.example.b07project1;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin{
    final private String email;
    final private String passwd;


    public UserLogin(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }


    public FirebaseUser BeginAuthenticate() {
        ServerCommunicator socket = new ServerCommunicator();
        return socket.attempt(this.email, this.passwd);
    }


}
