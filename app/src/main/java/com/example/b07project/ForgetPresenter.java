package com.example.b07project;

/**
 * ForgetPresenter is the presenter handling the interactions between the firebase and the view
 * regarding forgetting passwords.
 */
public class ForgetPresenter {
    private String email;
    ForgotPasswordFragment fView;
    FirebaseAuthHandler model;

    /**
     * ForgetPresenter - default constructor that set's the fView field so that the presenter
     * may interact with the View.
     * @param fView - the reference to the View
     */
    public ForgetPresenter(ForgotPasswordFragment fView) {
        super();
        this.fView = fView;
        this.model = new FirebaseAuthHandler();
    }

    /**
     * ForgetPresenter - default constructor that set's the fView field so that the presenter
     * may interact with the View.
     * @param fView - the reference to the View
     */
    public ForgetPresenter(ForgotPasswordFragment fView, FirebaseAuthHandler model) {
        super();
        this.fView = fView;
        this.model = model;
    }

    /**
     * setEmail - sets the email the user wishes to login to
     * @param email - the user's account email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * beginReset - Initiates the Password Reset process, communicates with Model
     */
    public void beginReset() {
        // An object between the Model and Presenter to track outcome of operation
        AsyncAuthComms mp = new AsyncAuthComms();
        // Create a Listener for the Model's operation
        model.setModelPipe(mp1 -> {
            AsyncAuthComms plug = (AsyncAuthComms) mp1;
            if (plug.res) {
                fView.success();
            }
            else {
                fView.failure();
            }
        });
        // Tell Model to send reset email
        model.resetPasswd(this.email, mp);
    }
}
