package com.example.b07project;

public class ForgetPresenter {
    private String email;

    LoginPresenter.PresenterViewPipe listener;
    ForgotPasswordFragment fView;
    ServerCommunicator model;


    public ForgetPresenter(ForgotPasswordFragment fView) {
        this.fView = fView;
        this.model = new ServerCommunicator();
    }

    /**
     * PresenterViewPipe - Interface representing the communication pipe (listener) between
     * Presenter and View
     */
    public interface PresenterViewPipe {
        // This is the event that we fire when operation has been completed
        void onObjectReady(AsyncAuthComms betweener);
    }

    /**
     * LoginPresenter - Constructor for when the user attempts to login, thus an email
     * and password are required
     */
    public ForgetPresenter() {
    }


    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * setViewPipe - Set's PresenterViewPipe to permit the Presenter to communicate with View
     * @param listener - The listener that connects the Presenter with the View
     */
    public void setViewPipe(LoginPresenter.PresenterViewPipe listener) {
        this.listener = listener;
    }


    /**
     * beginReset - Initiates the Password Reset process, communicates with Model
     * @param pv - The SuccessListener that allows the Presenter to pass its results to the View
     */
    public void beginReset(AsyncAuthComms pv) {
        // Create a ServerCommunicator (Model) object
//        ServerCommunicator socket = new ServerCommunicator();
        // An object between the Model and Presenter to track outcome of operation
        AsyncAuthComms mp = new AsyncAuthComms();
        // Create a Listener for the Model's operation
        model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncAuthComms mp) {
                if (mp.success) {
                    fView.success();
                }
                else {
                    fView.failure();
                }
//                // Notify object that track's Presenter's outcome about Presenter's outcome
//                pv.setSuccess(mp.success);
//                // Fire event to notify View that Presenter has passed it's info and is done
//                listener.onObjectReady(pv);
            }
        });
        // Tell Model to send reset email
        model.resetPasswd(this.email, mp);
    }
}
