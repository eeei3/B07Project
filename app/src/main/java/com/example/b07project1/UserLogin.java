package com.example.b07project1;


import com.google.firebase.auth.FirebaseAuth;

public class UserLogin{
    private String email;
    private String passwd;

    final FirebaseAuth mAuth;

    public UserLogin(String email, String passwd, FirebaseAuth mAuth) {
        this.email = email;
        this.passwd = passwd;
        this.mAuth = mAuth;
    }


    public boolean BeginAuthenticate() {
        AuthenticationModule auth = new AuthenticationModule(mAuth);
        return auth.Authenticate_caller(this.email, this.passwd);
    }


}
