package com.example.b07project;

/**
 * Class representing the Presenter portion of the Login module
 */
public class LoginPresenter extends Presenter {
    private String email;
    private String passwd;

    LoginFragment lView;
    FirebaseAuthHandler model;

    /**
     * LoginPresenter - Constructor for when the user attempts to login, thus an email
     * and password are required
     */
    public LoginPresenter(LoginFragment lView) {
        super();
        this.lView = lView;
        this.model = new FirebaseAuthHandler();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * beginAuthenticate - Initiates the Authentication process, communicates with Model
     */
    public void beginAuthenticate() {
        // An object between the Model and Presenter to track outcome of operation
        AsyncAuthComms mp = new AsyncAuthComms();
        // Create a Listener for the Model's operation
        model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms mp) {
                AsyncAuthComms plug = (AsyncAuthComms) mp;
                if (plug.res) {
                    lView.success();
                }
                else {
                    lView.failure();
                }
            }
        });
        // Tell Model to login
        model.login(this.email, this.passwd, mp);
    }
}
