package com.example.b07project1;
// import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

class AuthenticationModule{

    final FirebaseAuth mAuth;

    public AuthenticationModule(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }
    public boolean authenticate_Caller(String email, String passwd) {
        ServerCommunicator socket = new ServerCommunicator(mAuth);
        return socket.attempt(email, passwd);
    }
}
