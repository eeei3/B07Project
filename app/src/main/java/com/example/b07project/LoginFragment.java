package com.example.b07project;

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

import java.util.Objects;
//import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private EditText editTextUserEmail, editTextUserPassword;

    LoginPresenter presenter;

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

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCredentials();
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ForgotPasswordFragment());
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send back to home screen
            }
        });

        return view;
    }

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
        // Create object to hold if operation is successful or not
        SuccessListener watcher = new SuccessListener();
        // Create Listener to check if password reset successful or not.
        presenter.setViewPipe(new LoginPresenter.PresenterViewPipe() {
            @Override
            public void onObjectReady(SuccessListener watcher) {
                if (watcher.success) {
                    success();
                }
                else {
                    failure();
                }
            }
        });
        // Log in user
        presenter.beginAuthenticate(watcher);

        /*
        UserLogin user = new UserLogin(email, password, DatabaseUrl);

        if (user.BeginAuthenticate()) {
            // change to dashboard layout?
        } else {
            Toast.makeText(getContext(),"Invalid email address or password", Toast.LENGTH_SHORT).show();
        }
        */
    }

    public void success() {
        Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
        // Send back to whatever screen is appropriate
    }

    public void failure() {
        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }

    public String getEmail() {
        return editTextUserEmail.getText().toString().trim();
    }

    public String getPassword() {
        return editTextUserPassword.getText().toString().trim();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}