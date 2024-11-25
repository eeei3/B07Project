package com.example.b07project;

import java.util.HashSet;

// We use this object to simply keep track of success or failure
public class SuccessListener {
    boolean success;

    public SuccessListener() {
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
