package com.example.b07project;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaugeReader {

    private DatabaseReference databaseReference;
    private static final int DAILY_LIMIT = 1;
    private static final int MONTHLY_LIMIT = 30;
    private static final int YEARLY_LIMIT = 365;

    public GaugeReader(String userId) {
        // Initialize Firebase Database reference for a specific user
        this.databaseReference = FirebaseDatabase.getInstance()
                .getReference("users") // The parent node where user data is stored
                .child(userId); // Add the userId to the path
    }
    public void getMonthlyEmissionsPie(FirebaseCallback callback) {
        final float[] monthlyEmissionsArray = {0, 0, 0};  // Default array with 3 sections: consumption, transportation, food

        // Query Firebase to get the latest 30 records
        databaseReference.limitToLast(30)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // If data exists for the last 30 entries
                        if (dataSnapshot.exists()) {
                            float totalConsumption = 0, totalTransportation = 0, totalFood = 0;

                            // Loop through all the records from the past 30 days
                            for (DataSnapshot record : dataSnapshot.getChildren()) {
                                // Get the emissions data for each category
                                Float consumption = ((Number) record.child("consumption").getValue()).floatValue();
                                Float transportation = ((Number) record.child("transportation").getValue()).floatValue();
                                Float food = ((Number) record.child("food").getValue()).floatValue();

                                // Sum up the emissions for each category over the last 30 entries
                                if (consumption != null) totalConsumption += consumption;
                                if (transportation != null) totalTransportation += transportation;
                                if (food != null) totalFood += food;
                            }

                            // Assign the summed values to the return array
                            monthlyEmissionsArray[0] = totalConsumption;
                            monthlyEmissionsArray[1] = totalTransportation;
                            monthlyEmissionsArray[2] = totalFood;

                            // Return the data via callback
                            callback.onSuccess(monthlyEmissionsArray);
                        } else {
                            // No data found for the last 30 entries
                            callback.onFailure("No data available for the past 30 days.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error (e.g., network issues, Firebase issues)
                        callback.onFailure("Failed to retrieve data: " + databaseError.getMessage());
                    }
                });
    }

    public void getDailyEmissionsPie(FirebaseCallback callback) {
        final float[] dailyEmissionsArray = {0, 0, 0};  // Default array with 3 sections: consumption, transportation, food
        long todayStart = getStartOfDay(System.currentTimeMillis());

        // Query Firebase to get the emissions data for today
        databaseReference.orderByChild("createdAt").startAt(todayStart)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // If data exists for today
                        if (dataSnapshot.exists()) {
                            float consumption = 0, transportation = 0, food = 0;

                            // Loop through all records for today
                            for (DataSnapshot record : dataSnapshot.getChildren()) {
                                // Get each section's emissions data
                                Float recordConsumption = ((Number) record.child("consumption").getValue()).floatValue();
                                Float recordTransportation = ((Number) record.child("transportation").getValue()).floatValue();
                                Float recordFood = ((Number) record.child("food").getValue()).floatValue();

                                // Add up the emissions for each category
                                if (recordConsumption != null) consumption += recordConsumption;
                                if (recordTransportation != null) transportation += recordTransportation;
                                if (recordFood != null) food += recordFood;
                            }

                            // Assign to the return array
                            dailyEmissionsArray[0] = consumption;
                            dailyEmissionsArray[1] = transportation;
                            dailyEmissionsArray[2] = food;
                            callback.onSuccess(dailyEmissionsArray);
                        } else {
                            // No data found for today
                            callback.onFailure("No survey completed for today. Please complete your survey.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error (e.g., network issues, Firebase issues)
                        callback.onFailure("Failed to retrieve data: " + databaseError.getMessage());
                    }
                });
    }

    public void getYearlyEmissionsPie(FirebaseCallback callback) {
        // Get the current timestamp and calculate the start of the year (365 days ago)
        long oneYearAgo = System.currentTimeMillis() - (365L * 24 * 60 * 60 * 1000); // 365 days in milliseconds

        // Query Firebase for records created in the last 365 days
        databaseReference.orderByChild("createdAt").startAt(oneYearAgo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Initialize an array to hold the total emissions for each category: Consumption, Transportation, Food
                        float[] yearlyEmissions = {0f, 0f, 0f}; // {Consumption, Transportation, Food}

                        // Check if there is any data returned from Firebase
                        if (dataSnapshot.exists()) {
                            // Loop through each record returned from Firebase
                            for (DataSnapshot record : dataSnapshot.getChildren()) {
                                // Fetch emissions data for each category (assuming data structure has "consumption", "transportation", "food")
                                float consumption = ((Number) record.child("consumption").getValue()).floatValue();
                                float transportation = ((Number) record.child("transportation").getValue()).floatValue();
                                float food = ((Number) record.child("food").getValue()).floatValue();

                                // Add the values to the corresponding categories in the emissions array
                                yearlyEmissions[0] += consumption;
                                yearlyEmissions[1] += transportation;
                                yearlyEmissions[2] += food;
                            }

                            // After processing all records, return the emissions array via the callback
                            callback.onSuccess(yearlyEmissions);
                        } else {
                            // No data for the past year
                            callback.onFailure("No data available for the past year.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle Firebase database error
                        callback.onFailure("Failed to retrieve data: " + databaseError.getMessage());
                    }
                });
    }

    // Helper method to get the start of the current day (midnight in UTC)
    private long getStartOfDay(long timestamp) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    // Helper method to notify the user
    private void notifyUser(String message) {
        System.out.println(message); // Replace with actual notification logic, such as a Toast
    }

    public interface FirebaseCallback {
        void onSuccess(float[] EmissionsArray);
        void onFailure(String errorMessage);
    }
}