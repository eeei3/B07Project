package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.AuthResult;


/**
 * RegisterUserFragment is responsible for binding and displaying the registration page.
 *
 */
public class RegisterUserFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPass, editTextConfirmPass;
    private static final String TAG = "Register User";


    /**
     * onCreateView creates the view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return                   the view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_user, container, false);

        mAuth = FirebaseAuth.getInstance();

        //reference UI elements
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPass = view.findViewById(R.id.editTextPass);
        editTextConfirmPass = view.findViewById(R.id.editTextConfirmPass);
        Button buttonRegisterUser = view.findViewById(R.id.buttonRegisterUser);
        ImageButton imageButtonBack = view.findViewById(R.id.backButtonSignUp);

        imageButtonBack.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EntryActivity.class);
            startActivity(intent);
        });

        //click listener for register button
        buttonRegisterUser.setOnClickListener(v -> registerUser());
        return view;

    }


    /**
     * registerUser method attempts to register the user into the firebase.
     *
     */
    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String confirmPass = editTextConfirmPass.getText().toString().trim();

        //validate info (if fields are complete, if email is valid, if pass matches confirmPass, if user already exists)
        if (validateInfo(name, email, password, confirmPass)) {

            //create a user using mAuth
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //update the user's name
                                if (user != null) {
                                    updateUserName(user, name); //update the username
                                    sendEmailVerification(user); //send email
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    /**
     * updateUserName method updates the user name.
     *
     * @param user the firebase user
     * @param name the name the user had typed
     */
    private void updateUserName(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated with display name.");
                            updateUI(user); // Refresh the UI with the updated user info
                        } else {
                            Log.w(TAG, "User profile update failed.", task.getException());
                        }
                    }
                });
    }

    /**
     * updateID method updates the UI if the user is signed in or not signed in.
     *
     * @param user the firebase user
     */
    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Log.d(TAG, "User is signed in: " + user.getEmail());

            startActivity(new Intent(getActivity(),SurveyActivity.class));

        } else {

            Log.d(TAG, "User is not signed in.");
            Toast.makeText(getActivity(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * validateInfo method seeks to validate the information typed by the user.
     *
     * @param name the user's name
     * @param email the user's email
     * @param password the user's typed password
     * @param confirmPass the user's typed confirmed password
     * @return true if info typed is correct, false otherwise
     */
    public boolean validateInfo(String name, String email, String password, String confirmPass) {

        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        //check if all fields are filled
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        //if email is invalid
        if (!(email.matches(EMAIL_REGEX))) {
            Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        //if confirmPass != password
        if (!(confirmPass.equals(password))) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * sendEmailVerification method sends an email verification to the user.
     *
     * @param user the firebase user
     */
    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
