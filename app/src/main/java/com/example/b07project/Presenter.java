package com.example.b07project;


public class Presenter {
    LoginPresenter.PresenterViewPipe listener;

    /**
     * PresenterViewPipe - Interface representing the communication pipe (listener) between
     * Presenter and View
     */
    public interface PresenterViewPipe {
        // This is the event that we fire when operation has been completed
        void onObjectReady(AsyncAuthComms betweener);
    }

    /**
     * setViewPipe - Set's PresenterViewPipe to permit the Presenter to communicate with View
     * @param listener - The listener that connects the Presenter with the View
     */
    public void setViewPipe(PresenterViewPipe listener) {
        this.listener = listener;
    }

}
