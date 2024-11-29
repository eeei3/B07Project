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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;


public class RegisterUserFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPass, editTextConfirmPass;
    private Button buttonRegisterUser;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;
    private static final String TAG = "Register User";


    //initializes the registration screen
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
        buttonRegisterUser = view.findViewById(R.id.buttonRegisterUser);
        ImageButton imageButtonBack = view.findViewById(R.id.backButtonSignUp);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EntryActivity.class);
                startActivity(intent);
            }
        });

        //click listener for register button
        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        return view;

    }

    //create a registerUser method
    //create a user using mAuth
    //put user in database
    private void registerUser() {

        //gets email and password from input fields

        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String confirmPass = editTextConfirmPass.getText().toString().trim();

        //validate info (if fields are complete, if email is valid, if pass matches confirmpass, if user already exists)
        if (validateInfo(name, email, password, confirmPass)) {

            //create a user using mAuth
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //update the user's name
                                updateUserName(user, name); //update the username
                                sendEmailVerification(user); //send email
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

        //add user to the firebase database

    }

    private void updateUserName(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Log.d(TAG, "User is signed in: " + user.getEmail());

            startActivity(new Intent(getActivity(),MainActivity.class)); //not sure why "this" instead of getactivity() is not working

        } else {

            Log.d(TAG, "User is not signed in.");
            Toast.makeText(getActivity(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


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

        //implement method similar to deleteItemByTitle --> run through the data in firebase

//        boolean userExists = false;
//        //if user already exists by signing them in
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> { //bc firebase operations are asynchronous (running on a different thread)
//                    if (task.isSuccessful()) { //if they are able to sign in
//                        Toast.makeText(getContext(), "User is already registered, please log in", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                userExists = true;
//        //if all info is valid and user does not exist, return true

        return true;
    }

    private void sendEmailVerification(FirebaseUser user) {

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
