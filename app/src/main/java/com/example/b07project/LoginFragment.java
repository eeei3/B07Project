package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * LoginFragment class is responsible for binding and displaying the UI components of the login.
 *
 */
public class LoginFragment extends Fragment{
    private EditText editTextUserEmail, editTextUserPassword;
    LoginPresenter presenter;

    /**
     * onCreateView creates the login page.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return                   the view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_screen_upd, container, false);

        presenter = new LoginPresenter(this);
        editTextUserEmail = view.findViewById(R.id.txtUserEmail);
        editTextUserPassword = view.findViewById(R.id.userPassWord);
        Button buttonSignIn = view.findViewById(R.id.signIn);
        TextView textViewForgotPassword = view.findViewById(R.id.forgotPassWordLink);
        ImageButton imageButtonBack = view.findViewById(R.id.backButtonLogin);

        buttonSignIn.setOnClickListener(v -> verifyCredentials());
        textViewForgotPassword.setOnClickListener(v -> loadFragment(new ForgotPasswordFragment()));
        imageButtonBack.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        return view;
    }

    /**
     * verifyCredentials method verify if the credentials typed by the user is accurate.
     *
     */
    public void verifyCredentials() {
        // Retrieve the email the User entered
        String email = getEmail();
        // Retrieve the password the User entered
        String password = getPassword();
        // Check for invalid input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create communication channel with the Presenter
        presenter.setEmail(email);
        presenter.setPasswd(password);
        // Log in user
        presenter.beginAuthenticate();
    }

    /**
     * success method signals that the user successfully logged in.
     *
     */
    public void success() {
        Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), EcoTrackerHomeActivity.class);
        startActivity(intent);
    }

    /**
     * failure method signals that the user failed to log in.
     *
     */
    public void failure() {
        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }

    /**
     * getEmail method returns the email typed by the user
     *
     * @return the user's email
     */
    public String getEmail() {
        return editTextUserEmail.getText().toString().trim();
    }

    /**
     * getEmail method returns the password typed by the user
     *
     * @return the user's typed password
     */
    public String getPassword() {
        return editTextUserPassword.getText().toString().trim();
    }

    /**
     * loadFragment method transitions this login fragment to another.
     *
     * @param fragment the fragment to be loaded
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}