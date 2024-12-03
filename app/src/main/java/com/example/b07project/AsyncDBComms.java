package com.example.b07project;

import java.util.HashSet;
import java.util.ArrayList;

/**
 * SuccessListener - Class representing the results of our asynchronous calls to Firebase auth
 *
 * @see AsyncComms
 */
public class AsyncDBComms extends AsyncComms {
    final ArrayList<Object> values;
    HashSet<Goal> usergoals;
    HashSet<Goal> listgoals;

    /**
     * SuccessListener - Default constructor, sets success to false
     */
    public AsyncDBComms() {
        super();
        this.usergoals = new HashSet<>();
        this.listgoals = new HashSet<>();
        this.values = new ArrayList<>();
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
    public void setListgoals(HashSet<Goal> value) {this.listgoals = value;}
}
