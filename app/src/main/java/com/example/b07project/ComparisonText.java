package com.example.b07project;

import android.util.Log;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class ComparisonText {
    private TextView comparisonText;
    private TextView yourEmissionsNumber;
    private TextView globalEmissions;

    public ComparisonText(TextView comparisonText, TextView yourEmissionsNumber, TextView globalEmissions) {
        this.comparisonText = comparisonText;
        this.yourEmissionsNumber = yourEmissionsNumber;
        this.globalEmissions = globalEmissions;
    }

    // Method to fetch emissions for a specific date and update the UI
    public void updateComparisonText(String location, String date) {
        fetchEmissionsForDay(date, new FirebaseCallback() {
            @Override
            public void onCallback(Double dailyEmissions) {
                if (dailyEmissions != null) {
                    // Proceed with updating the UI after fetching emissions
                    int i = Arrays.asList(EmissionsData.countries).indexOf(location);
                    if (i == -1) {
                        comparisonText.setText("Location not found in emissions data.");
                        return;
                    }

                    double nationalAverage = EmissionsData.globalAverages[i];
                    double nationalComparison = dailyEmissions - nationalAverage;

                    // Update UI components
                    yourEmissionsNumber.setText(String.format("%.2f", dailyEmissions));
                    globalEmissions.setText(String.format("%.2f", nationalAverage));

                    // Update comparison text
                    if (nationalComparison < 0) {
                        comparisonText.setText(String.format("Your emissions are %.2f lower than the national average", Math.abs(nationalComparison)));
                    } else if (nationalComparison == 0) {
                        comparisonText.setText("Your emissions are the same as the national average");
                    } else {
                        comparisonText.setText(String.format("Your emissions are %.2f higher than the national average", nationalComparison));
                    }
                } else {
                    comparisonText.setText("Could not fetch emissions data for the selected date.");
                }
            }
        });
    }

    // Method to fetch emissions data for a specific date from Firebase
    private void fetchEmissionsForDay(String date, FirebaseCallback callback) {
        EcoGauge temp = new EcoGauge(); // Assuming EcoGauge provides user initialization
        String userId = temp.initializeFirebaseUser();

        DatabaseReference dayRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker").child(date)
                .child("calculated emissions").child("totalEmissions");

        dayRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        Double totalEmissions = snapshot.getValue(Double.class);
                        callback.onCallback(totalEmissions);
                    } else {
                        Log.d("Firebase", "No emissions data found for the date: " + date);
                        callback.onCallback(null);
                    }
                } else {
                    Log.e("Firebase", "Failed to fetch data for date: " + date, task.getException());
                    callback.onCallback(null);
                }
            }
        });
    }

    // Interface for Firebase callback
    public interface FirebaseCallback {
        void onCallback(Double dailyEmissions);
    }
}
