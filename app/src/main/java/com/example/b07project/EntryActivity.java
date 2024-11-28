package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * EntryActivity class containing methods relating to the main menu of the application
 */
public class EntryActivity extends AppCompatActivity {

    /**
     * onCreate - Method run when EntryActivity is created
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * loadFragment - Method for handling the loading of a fragment
     * @param fragment - fragment to load
     */
    private void loadFragment(Fragment fragment) {
        // Use the correct FragmentManager for activities
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Optional transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // R.id.fragment_container is your container's ID
        transaction.replace(R.id.fragment_container, fragment);
        // Optional: allows fragment to be popped back
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
