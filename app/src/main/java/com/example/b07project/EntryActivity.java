package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EntryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_page);  // Link the XML layout to the activity

        Button logInButton = (Button) findViewById(R.id.LogInButton);

        logInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadFragment(new LoginFragment());
            }
        });

        Button signUpButton = (Button) findViewById(R.id.SignInButton);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadFragment(new RegisterUserFragment());            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // Use the correct FragmentManager for activities
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);  // Optional transition
        transaction.replace(R.id.fragment_container, fragment);  // R.id.fragment_container is your container's ID
        transaction.addToBackStack(null);  // Optional: allows fragment to be popped back
        transaction.commit();
    }
}


