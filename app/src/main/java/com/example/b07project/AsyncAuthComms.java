package com.example.b07project;


/**
 * SuccessListener - Class representing the results of our asynchronous calls to Firebase auth
 */
public class AsyncAuthComms extends AsyncComms {
    int value;

    /**
     * SuccessListener - Default constructor, sets success to false
     */
    public AsyncAuthComms() {
        super();
        this.value = 0;
    }

    /**
     * setValue - Modify the value field
     * @param value - The new value of value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
