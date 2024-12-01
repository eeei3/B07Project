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
//import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment{
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
                Intent intent = new Intent(getActivity(), EntryActivity.class);
                startActivity(intent);
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
        // Log in user
        presenter.beginAuthenticate();
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