package com.example.b07project;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

/**
 * FirebaseSurvey class is concerned with interacting with the firebase when filling out the survey.
 */
public class FirebaseSurvey {
    final private DatabaseReference dbworker;
    final private String userID;
    final private SurveyActivity view;

    /**
     * A constructor for FirebaseSurvey.
     *
     * @param view the SurveyActivity activity to get data from the user.
     */
    public FirebaseSurvey(SurveyActivity view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07project-b43b0-default-rtdb.firebaseio.com/");
        this.dbworker = db.getReference();
        this.view = view;
    }

    /**
     * writeResult method writes the result of the survey to the firebase.
     *
     * @param Transportation transportation
     * @param Food food
     * @param Housing housing
     * @param Consumption consumptions
     * @param totalEmissions total emissions
     * @param location location
     */
    public void writeResult(double Transportation, double Food, double Housing,
                            double Consumption, double totalEmissions, String location) {
        // Logging the values to verify they are not null
        Log.d("ServerCommunicator", "Transportation Emission: " + Transportation);
        Log.d("ServerCommunicator", "Food Emission: " + Food);
        Log.d("ServerCommunicator", "Housing Emission: " + Housing);
        Log.d("ServerCommunicator", "Consumption Emission: " + Consumption);
        Log.d("ServerCommunicator", "Total Emission: " + totalEmissions);
        Log.d("ServerCommunicator", "Location: " + location);


        // Reference to the user's node in the database
        DatabaseReference userRef = dbworker.child("users").child(userID);
        DatabaseReference locationRef = userRef.child("location");
        locationRef.setValue(location)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("ServerCommunicator", "Location saved successfully.");
                    } else {
                        Log.e("ServerCommunicator", "Error saving location.");
                    }
                });

        // Setting the values for each emission category
        userRef.child("annualEmissions").child("transportationEmission").setValue(Transportation)
                .addOnFailureListener(e -> Log.e("ServerCommunicator", "Error saving transportationEmission: ", e));

        userRef.child("annualEmissions").child("foodEmissions").setValue(Food)
                .addOnFailureListener(e -> Log.e("ServerCommunicator", "Error saving foodEmissions: ", e));

        userRef.child("annualEmissions").child("housingEmissions").setValue(Housing)
                .addOnFailureListener(e -> Log.e("ServerCommunicator", "Error saving housingEmissions: ", e));

        userRef.child("annualEmissions").child("consumptionEmissions").setValue(Consumption)
                .addOnFailureListener(e -> Log.e("ServerCommunicator", "Error saving consumptionEmissions: ", e));

        userRef.child("annualEmissions").child("totalEmission").setValue(totalEmissions)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("ServerCommunicator", "Total emission saved successfully.");
                        // Trigger success callback if all data is saved successfully
                        view.success();
                    } else {
                        Log.e("ServerCommunicator", "Error saving totalEmission.");
                        // Trigger failure callback if there was an issue saving the data
                        view.failure();
                    }
                });
    }
}