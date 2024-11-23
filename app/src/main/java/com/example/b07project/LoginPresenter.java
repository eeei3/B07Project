package com.example.b07project;

public class LoginPresenter {
    final private String email;
    final private String passwd;
    // ServerCommunicator socket = new ServerCommunicator();
//        SuccessListener watcher = new SuccessListener();
//        socket.setListener(new ServerCommunicator.outcomeListener() {
//            @Override
//            public void onObjectReady(SuccessListener watcher) {
//                Toast.makeText(getContext(), "Fired", Toast.LENGTH_SHORT).show();
//                if (watcher.success) {
//                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
////        mAuth.addAuthStateListener(mAuthListener);
//        LoginPresenter auth = new LoginPresenter(email, password);
//        auth.BeginAuthenticate(socket, watcher);
    PresenterViewPipe listener;

    //    This is our listener that communicates with the View
    //    (LoginFragment and ForgotPasswordFragment)
    public interface PresenterViewPipe {
        // This is the event that we fire when operation has been completed
        void onObjectReady(SuccessListener betweener);
    }

    public LoginPresenter(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }
    public LoginPresenter(String email) {
        this.email = email;
        this.passwd = null;
    }

    // Tell's Presenter which listener from View to notify when operation is completed
    public void setViewPipe(PresenterViewPipe listener) {
        this.listener = listener;
    }


    // Presenter Log-in Method
    public void BeginAuthenticate(SuccessListener pv) {
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

    // Presenter Password Reset Method
    public void BeginReset(SuccessListener pv) {
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
