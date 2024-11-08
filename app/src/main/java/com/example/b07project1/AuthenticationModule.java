package com.example.b07project1;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

class AuthenticationModule extends Fragment {

    final FirebaseAuth mAuth;

    public AuthenticationModule(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }
    public boolean Authenticate_caller(String email, String passwd) {
        ServerCommunicator socket = new ServerCommunicator(mAuth);
        return socket.attempt(email, passwd);
    }
}
