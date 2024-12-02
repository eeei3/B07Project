package com.example.b07project;


/**
 * Abstract Class for when the application needs to make asynchronous function calls, this defines
 * a way for the application to communicate to its various components that the asynchronous
 * task has been completed.
 */
public abstract class AsyncComms {
    boolean res;

    /**
     * Default constructor, sets the result variable to false by default.
     */
    public AsyncComms() {
        this.res = false;
    }

    /**
     * setResult - Modify the res field
     * @param res - The new value of res
     */
    public void setResult(boolean res) {
        this.res = res;
    }

}
