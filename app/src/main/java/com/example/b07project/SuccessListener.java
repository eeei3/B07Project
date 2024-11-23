package com.example.b07project;

import java.util.HashSet;

// We use this object to simply keep track of success or failure
public class SuccessListener {
    boolean success;
    int value;
    HashSet<Goal> usergoals;
    HashSet<String> listgoals;

    public SuccessListener() {
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setUsergoals(HashSet<Goal> value) {this.usergoals = value;}
    public void setListgoals(HashSet<String> value) {this.listgoals = value;}
}
