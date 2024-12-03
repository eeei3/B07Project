package com.example.b07project;


/**
 * SuccessListener - Class representing the results of our asynchronous calls to Firebase auth
 *
 * @see AsyncComms
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
}
