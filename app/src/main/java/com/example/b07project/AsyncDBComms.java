package com.example.b07project;

import java.util.HashSet;

/**
 * SuccessListener - Class representing the results of our asynchronous calls to Firebase auth
 */
public class AsyncDBComms extends AsyncComms {
    int value;
    HashSet<Goal> usergoals;
    HashSet<String> listgoals;

    /**
     * SuccessListener - Default constructor, sets success to false
     */
    public AsyncDBComms() {
        super();
        this.usergoals = new HashSet<Goal>();
        this.listgoals = new HashSet<String>();
    }

    /**
     * setValue - Modify the value field
     * @param value - The new value of value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * setUsergoals - sets the usergoals field to contain the appropriate user's goal
     * @param value - the goals
     */
    public void setUsergoals(HashSet<Goal> value) {this.usergoals = value;}

    /**
     * setListgoals - sets the list of goals to keep track of
     * @param value - the goals' names
     */
    public void setListgoals(HashSet<String> value) {this.listgoals = value;}
}
