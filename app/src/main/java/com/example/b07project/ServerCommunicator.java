package com.example.b07project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServerCommunicator {
    final private FirebaseDatabase db;
    final private DatabaseReference dbworker;
    final private String userID;
    final private SurveyActivity view;


    public ServerCommunicator(SurveyActivity view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.userID = mAuth.getCurrentUser().getUid();
        this.db = FirebaseDatabase.getInstance("https://b07project-b43b0-default-rtdb.firebaseio.com/");
        this.dbworker = db.getReference();
        this.view = view;
    }

    public void writeResult(double Transportation, double Food, double Housing, double Consumption, double totalEmissions) {
        // Reference to the user's node in the database
        DatabaseReference userRef = dbworker.child("users").child(userID);

        // Setting the values for each emission category directly
        userRef.child("annualEmissions").child("transportationEmission").setValue(Transportation);
        userRef.child("annualEmissions").child("foodEmissions").setValue(Food);
        userRef.child("annualEmissions").child("housingEmissions").setValue(Housing);
        userRef.child("annualEmissions").child("consumptionEmissions").setValue(Consumption);
        userRef.child("annualEmissions").child("totalEmission").setValue(totalEmissions);


        // Adding a listener to check if the data was saved successfully
        userRef.child("annualEmissions").child("totalEmission").setValue(totalEmissions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Successfully saved the emission data
                            view.success();
                        } else {
                            // Failed to save the data
                            view.failure();
                        }
                    }
                });
    }

}
