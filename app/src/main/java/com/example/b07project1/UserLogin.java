package com.example.b07project1;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin{
    final private String email;
    final private String passwd;

//    final FirebaseAuth mAuth;

    public UserLogin(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
//        this.mAuth = mAuth;
    }


    public FirebaseUser BeginAuthenticate() {
        //AuthenticationModule auth = new AuthenticationModule(mAuth);
        //return auth.authenticate_Caller(this.email, this.passwd);
        //FirebaseUser res = null;
        ServerCommunicator socket = new ServerCommunicator();
        return socket.attempt(this.email, this.passwd);
    }


}
