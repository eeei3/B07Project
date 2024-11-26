package com.example.b07project;

/**
 * SuccessListener - Class representing the results of our asynchronous calls to Firebase auth
 */
public class SuccessListener {
    boolean success;
    int value;
    HashSet<Goal> usergoals;
    HashSet<String> listgoals;

    /**
     * SuccessListener - Default constructor, sets success to false
     */
    public SuccessListener() {
        this.success = false;
        this.usergoals = new HashSet<Goal>();
        this.listgoals = new HashSet<String>();
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

    public void setUsergoals(HashSet<Goal> value) {this.usergoals = value;}
    public void setListgoals(HashSet<String> value) {this.listgoals = value;}
}
