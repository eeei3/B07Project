package com.example.b07project;

// We use this object to simply keep track of success or failure
public class SuccessListener {
    boolean success;
    int value;

    public SuccessListener() {
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
