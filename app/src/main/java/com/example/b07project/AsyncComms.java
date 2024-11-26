package com.example.b07project;


public abstract class AsyncComms {
    boolean res;

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
