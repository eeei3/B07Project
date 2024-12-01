package com.example.b07project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private DatabaseReference databaseReference;

    // Constructor initializing the database reference for the specific user
    public FirebaseHelper(String userId) {
        this.databaseReference = FirebaseDatabase.getInstance()
                .getReference("users").child(userId).child("ecotracker");
    }

    // Fetch data for a specific date
    public void getDataForDate(String date, FirebaseDataCallback callback) {
        databaseReference.child(date).child("calculatedEmissions")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            // Create a map to hold emission data
                            Map<String, Double> emissionsData = new HashMap<>();
                            emissionsData.put("totalTranspo", snapshot.child("totalTranspo").getValue(Double.class));
                            emissionsData.put("totalFood", snapshot.child("totalFood").getValue(Double.class));
                            emissionsData.put("totalShopping", snapshot.child("totalShopping").getValue(Double.class));
                            emissionsData.put("totalEmission", snapshot.child("totalEmission").getValue(Double.class));
                            callback.onDataReceived(emissionsData);
                        } else {
                            callback.onDataNotFound();
                        }
                    } else {
                        callback.onDataFetchFailed();
                    }
                });
    }

    // Fetch data for a specific month
    public void getDataForMonth(String month, FirebaseDataCallback callback) {
        databaseReference.child("monthly").child(month).child("calculatedEmissions")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            Map<String, Double> emissionsData = new HashMap<>();
                            emissionsData.put("totalTranspo", snapshot.child("totalTranspo").getValue(Double.class));
                            emissionsData.put("totalFood", snapshot.child("totalFood").getValue(Double.class));
                            emissionsData.put("totalShopping", snapshot.child("totalShopping").getValue(Double.class));
                            emissionsData.put("totalEmission", snapshot.child("totalEmission").getValue(Double.class));
                            callback.onDataReceived(emissionsData);
                        } else {
                            callback.onDataNotFound();
                        }
                    } else {
                        callback.onDataFetchFailed();
                    }
                });
    }

    // Fetch data for a specific year
    public void getDataForYear(String year, FirebaseDataCallback callback) {
        databaseReference.child("yearly").child(year).child("calculatedEmissions")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            Map<String, Double> emissionsData = new HashMap<>();
                            emissionsData.put("totalTranspo", snapshot.child("totalTranspo").getValue(Double.class));
                            emissionsData.put("totalFood", snapshot.child("totalFood").getValue(Double.class));
                            emissionsData.put("totalShopping", snapshot.child("totalShopping").getValue(Double.class));
                            emissionsData.put("totalEmission", snapshot.child("totalEmission").getValue(Double.class));
                            callback.onDataReceived(emissionsData);
                        } else {
                            callback.onDataNotFound();
                        }
                    } else {
                        callback.onDataFetchFailed();
                    }
                });
    }
}
