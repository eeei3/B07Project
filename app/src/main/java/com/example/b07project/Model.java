package com.example.b07project;

public abstract class Model {
    ModelPresenterPipe listener;
    BView BView;

    /**
     * ModelPresenterPipe - Interface representing the communication pipe (listener) between Model
     * and Presenter
     */
    public interface ModelPresenterPipe {
        void onObjectReady(AsyncComms betweener);
    }

    /**
     * setModelPipe - To permit communications between Model and Presenter
     * @param listener - How we notify the Presenter that the results are ready
     */
    public void setModelPipe(ModelPresenterPipe listener) {
        this.listener = listener;
    }
}
