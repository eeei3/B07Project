package com.example.b07project;

public class ForgetPresenter extends Presenter{
    private String email;
    ForgotPasswordFragment fView;
    FirebaseAuthHandler model;


    public ForgetPresenter(ForgotPasswordFragment fView) {
        super();
        this.fView = fView;
        this.model = new FirebaseAuthHandler();
    }

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
        model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms mp) {
                AsyncAuthComms plug = (AsyncAuthComms) mp;
                if (plug.res) {
                    fView.success();
                }
                else {
                    fView.failure();
                }
            }
        });
        // Tell Model to send reset email
        model.resetPasswd(this.email, mp);
    }
}
