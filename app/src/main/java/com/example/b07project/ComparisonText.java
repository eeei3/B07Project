package com.example.b07project;

import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Arrays;

/**
 *
 * ComparisonText class containing methods relating updating the comparison text in EcoGauge
 */
public class ComparisonText {

    final TextView totalEmissions, nationalEmissions, globalEmissions;
    final TextView comparisonNationalText, comparisonGlobalText;

    /**
     * ComparisonText - Default Constructor that sets totalEmissions, globalEmissions, nationalEmissions,
     *                  comparisonGlobalText, comparisonNationalText
     * @param comparisonNationalText - TextView of the user's national data
     * @param comparisonGlobalText - TextView of the global data
     * @param totalEmissions - TextView of the user's total Emissions
     * @param globalEmissions - TextView of the user's global Emissions
     * @param nationalEmissions - TextView of the user's national Emissions
     */
    public ComparisonText(TextView comparisonGlobalText, TextView comparisonNationalText,
                          TextView totalEmissions, TextView globalEmissions, TextView nationalEmissions) {
        this.totalEmissions = totalEmissions;
        this.globalEmissions = globalEmissions;
        this.nationalEmissions = nationalEmissions;
        this.comparisonGlobalText = comparisonGlobalText;
        this.comparisonNationalText = comparisonNationalText;
    }

    /**
     * updateComparisonText - Updates the text on Eco Gauge page by calling fetchUserData and getting
     *                        data from the firebase
     */
    public void updateComparisonText() {
        fetchUserData(new UserDataCallback() {
            @Override
            public void onUserDataFetched(String userLocation, Double dailyEmissions) {
                Log.e("Firebase", "" + userLocation);
                Log.e("Firebase", "" + dailyEmissions);
                if (userLocation == null || dailyEmissions == null) {
                    Log.e("ComparisonText", "Error fetching user data.");
                    totalEmissions.setText("Error retrieving data");
                    return;
                }

                // Fetch the index for the given location
                int locationIndex = Arrays.asList(EmissionsData.countries).indexOf(userLocation);
                if (locationIndex == -1) {
                    nationalEmissions.setText("Location not found in emissions data.");
                    return;
                }

                // Calculate comparisons
                double nationalAverage = EmissionsData.globalAverages[locationIndex];
                double nationalComparison = dailyEmissions/1000 - nationalAverage;
                double globalAverage = 4.68; // Example global average
                double globalComparison = dailyEmissions/1000 - globalAverage;

                // Update the TextViews
                globalEmissions.setText(String.format("%.2f", globalAverage));
                nationalEmissions.setText(String.format("%.2f", nationalAverage));
                totalEmissions.setText(String.format("%.2f", dailyEmissions/1000));

                comparisonNationalText.setText(globalComparison >= 0
                        ? String.format("You are %.2f above the national average.", nationalComparison)
                        : String.format("You are %.2f below the national average.", Math.abs(nationalComparison)));

                comparisonGlobalText.setText(globalComparison >= 0
                        ? String.format("You are %.2f above the global average.", globalComparison)
                        : String.format("You are %.2f below the global average.", Math.abs(globalComparison)));
            }
        });
    }

    /**
     * fetchUserData - Gets users total annualEmissions and location from the firebase
     */
    private void fetchUserData(UserDataCallback callback) {
        EcoGauge temp = new EcoGauge(); // Assuming EcoGauge provides user initialization
        String userId = temp.initializeFirebaseUser();

        DatabaseReference annualEmissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("annualEmissions").child("totalEmission");

        DatabaseReference locationRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("location");

        // Fetch emissions and location data
        annualEmissionsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Double dailyEmissions = task.getResult().getValue(Double.class);
                    locationRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                String userLocation = task.getResult().getValue(String.class);
                                callback.onUserDataFetched(userLocation, dailyEmissions);
                            } else {
                                Log.e("Firebase", "Failed to fetch location", task.getException());
                                callback.onUserDataFetched(null, null);
                            }
                        }
                    });
                } else {
                    Log.e("Firebase", "Failed to fetch emissions", task.getException());
                    callback.onUserDataFetched(null, null);
                }
            }
        });
    }

    // Callback interface for combining location and emissions data
    public interface UserDataCallback {
        void onUserDataFetched(String userLocation, Double dailyEmissions);
    }
}