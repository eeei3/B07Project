package com.example.b07project;

/**
 * Class representing the Presenter portion of the Login module
 *
 * @see LoginFragment
 * @see FirebaseAuthHandler
 */
public class LoginPresenter {
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

    /**
     * LoginPresenter - constructor that initializes the view and model
     * Note: this constructor is used for testing.
     *
     * @param lView the view component of the login
     * @param model the model component of the login
     */
    public LoginPresenter(LoginFragment lView, FirebaseAuthHandler model) {
        super();
        this.lView = lView;
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
     * setPasswd - sets the password the user wishes to use to login with
     * @param passwd - the user's account password
     */
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
        model.setModelPipe(mp1 -> {
            AsyncAuthComms plug = (AsyncAuthComms) mp1;
            if (plug.res) {
                lView.success();
            }
            else {
                lView.failure();
            }
        });
        // Tell Model to login
        model.login(this.email, this.passwd, mp);
    }
}
