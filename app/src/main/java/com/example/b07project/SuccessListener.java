package com.example.b07project;

/**
 * SuccessListener - Class representing the results of our asynchronous calls to Firebase auth
 */
public class SuccessListener {
    boolean success;
    int value;

    /**
     * SuccessListener - Default constructor, sets success to false
     */
    public SuccessListener() {
        this.success = false;
    }

    /**
     * setSuccess - Modify the success field
     * @param success - The new value of success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * setValue - Modify the value field
     * @param value - The new value of value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
