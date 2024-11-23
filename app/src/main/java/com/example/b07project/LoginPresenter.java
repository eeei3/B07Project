package com.example.b07project;

/**
 * Class representing the Presenter portion of the Login module
 */
public class LoginPresenter {
    final private String email;
    final private String passwd;

    PresenterViewPipe listener;


    /**
     * PresenterViewPipe - Interface representing the communication pipe (listener) between
     * Presenter and View
     */
    public interface PresenterViewPipe {
        // This is the event that we fire when operation has been completed
        void onObjectReady(SuccessListener betweener);
    }

    /**
     * LoginPresenter - Constructor for when the user attempts to login, thus an email
     * and password are required
     * @param email - The email of the user that wants to login
     * @param passwd - The password of the user that wants to login
     */
    public LoginPresenter(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    /**
     * LoginPresenter - Constructor for when the user attempts to reset their password, thus only
     * an email is required
     * @param email - The email of the user that wants to reset their account password
     */
    public LoginPresenter(String email) {
        this.email = email;
        this.passwd = null;
    }


    /**
     * setViewPipe - Set's PresenterViewPipe to permit the Presenter to communicate with View
     * @param listener - The listener that connects the Presenter with the View
     */
    public void setViewPipe(PresenterViewPipe listener) {
        this.listener = listener;
    }


    /**
     * beginAuthenticate - Initiates the Authentication process, communicates with Model
     * @param pv - The SuccessListener that allows the Presenter to pass its results to the View
     */
    public void beginAuthenticate(SuccessListener pv) {
        // Create a ServerCommunicator (Model) object
        ServerCommunicator socket = new ServerCommunicator();
        // An object between the Model and Presenter to track outcome of operation
        SuccessListener mp = new SuccessListener();
        // Create a Listener for the Model's operation
        socket.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener mp) {
                // Notify object that track's Presenter's outcome about Presenter's outcome
                pv.setSuccess(mp.success);
                // Fire event to notify View that Presenter has passed it's info and is done
                listener.onObjectReady(pv);
            }
        });
        // Tell Model to login
        socket.login(this.email, this.passwd, mp);
    }


    /**
     * beginReset - Initiates the Password Reset process, communicates with Model
     * @param pv - The SuccessListener that allows the Presenter to pass its results to the View
     */
    public void beginReset(SuccessListener pv) {
        // Create a ServerCommunicator (Model) object
        ServerCommunicator socket = new ServerCommunicator();
        // An object between the Model and Presenter to track outcome of operation
        SuccessListener mp = new SuccessListener();
        // Create a Listener for the Model's operation
        socket.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener mp) {
                // Notify object that track's Presenter's outcome about Presenter's outcome
                pv.setSuccess(mp.success);
                // Fire event to notify View that Presenter has passed it's info and is done
                listener.onObjectReady(pv);
            }
        });
        // Tell Model to send reset email
        socket.reset_passwd(this.email, mp);
    }
}
