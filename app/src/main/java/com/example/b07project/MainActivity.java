package com.example.b07project;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

//entry point of app
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_upd);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

//        db = FirebaseDatabase.getInstance("https://registration-d6a6c-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = db.getReference("test");
//
//        //myRef.setValue("Registration Demo!");
//        myRef.child("movies").setValue("B07 Demo!");
        //checks if activity is being started for the first time
        if (savedInstanceState == null) {
            loadFragment(new LoginFragment()); //adds homefragment to the activity

        } else {
        Log.d("MainActivity", "User logged in: " + user.getUid());

        if (savedInstanceState == null) {
            loadFragment(new EcoTrackerHomeFragment());  //otherwise, load the EcoTrackerHomeFragment
        }
    }

    }

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