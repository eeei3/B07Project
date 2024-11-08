package com.example.b07project1;

public class User {
    private String email;
    private String passwd;

    public User(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public User() {
        this.email = null;
        this.passwd = null;
    }

    String getPasswd() {
        return this.passwd;
    }

}
