package com.example.b07project;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseCommunicator databaseCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_upd);

        //initialize the Firebase Auth and DatabaseReference
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        //instantiate a databaseCommunicator
        //databaseCommunicator = new DatabaseCommunicator(database);

        //get the current user logged in
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            Log.d("MainActivity", "User not logged in");
            if (savedInstanceState == null) {
                loadFragment(new EcoTrackerHomeFragment());  //if user is not logged in, load the loginfragment
            }
        } else {
            Log.d("MainActivity", "User logged in: " + user.getUid());

            if (savedInstanceState == null) {
                loadFragment(new EcoTrackerHomeFragment());  //otherwise, load the EcoTrackerHomeFragment
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        Log.d("MainActivity", "Loading fragment: " + fragment.getClass().getSimpleName());
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
