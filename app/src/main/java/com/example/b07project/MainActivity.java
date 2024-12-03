package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * MainActivity serves as the entry point for the EcoTracker app.
 *
 * Checks whether a user is logged in or not. If the user is logged in,
 * directly starts the EcoTrackerHomeActivity. If not, it loads a LoginFragment
 * where the user can log in.
 *
 * @see EcoTrackerHomeActivity
 * @see LoginFragment
 */

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_upd);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // checks if activity is being started for the first time and loads the LoginFragment
        if (savedInstanceState == null) {
            loadFragment(new LoginFragment());

        } else {
            // if the user is logged in, launch EcoTrackerHomeActivity
            Log.d("MainActivity", "User logged in: " + user.getUid());

            Intent intent = new Intent(MainActivity.this, EcoTrackerHomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * loadFragment - Method for handling the loading of a fragment
     * @param fragment - fragment to load
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}