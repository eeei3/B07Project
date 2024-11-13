package com.example.b07project1;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private EditText editTextUserEmail, editTextUserPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_screen_upd, container, false);

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

    private void verifyCredentials() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = editTextUserEmail.getText().toString().trim();
        String password = editTextUserPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            private FirebaseUser user;
            boolean signed_in = false;

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if ((mAuth.getCurrentUser() != null) && (signed_in == false)) {
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    signed_in = true;
                }
                else if (signed_in == true) {
                    ;
                }
                else {
                    Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        UserLogin auth = new UserLogin(email, password);
//        FirebaseUser user = null;
        auth.BeginAuthenticate();
        FirebaseAuth.getInstance().signOut();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}