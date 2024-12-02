package com.example.b07project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DatabaseCommunicator {

    private DatabaseReference database;
    private Context context;

    public DatabaseCommunicator(DatabaseReference databaseReference, Context context) {
        this.database = databaseReference;
        this.context = context;
    }

    /**
     * Method to save user emission data (raw inputs & calculated emissions)
     * Data is stored under the user ID and date
     * @param userId the id of the user
     * @param selectedDate the selected date
     * @param data the emission data (raw inputs & calculated emissions)
     */
    public void saveUserEmissionData(String userId, String selectedDate, UserEmissionData data) {

        DatabaseReference userRef = database.child("users").child(userId);

        DatabaseReference dateRef = userRef.child("ecotracker").child(String.valueOf(selectedDate));

        //rawInputs map
        DatabaseReference rawInputsRef = dateRef.child("rawInputs");
        rawInputsRef.child("distanceDriven").setValue(data.getRawInputs().getDistanceDriven());
        rawInputsRef.child("vehicleType").setValue(data.getRawInputs().getVehicleType());
        rawInputsRef.child("transportType").setValue(data.getRawInputs().getTransportType());
        rawInputsRef.child("cyclingTime").setValue(data.getRawInputs().getCyclingTime());
        rawInputsRef.child("numFlights").setValue(data.getRawInputs().getNumFlights());
        rawInputsRef.child("flightType").setValue(data.getRawInputs().getFlightType());
        rawInputsRef.child("mealType").setValue(data.getRawInputs().getMealType());
        rawInputsRef.child("numServings").setValue(data.getRawInputs().getNumServings());
        rawInputsRef.child("numClothes").setValue(data.getRawInputs().getNumClothes());
        rawInputsRef.child("deviceType").setValue(data.getRawInputs().getDeviceType());
        rawInputsRef.child("numDevices").setValue(data.getRawInputs().getNumDevices());
        rawInputsRef.child("purchaseType").setValue(data.getRawInputs().getPurchaseType());
        rawInputsRef.child("numOtherPurchases").setValue(data.getRawInputs().getNumOtherPurchases());
        rawInputsRef.child("billAmount").setValue(data.getRawInputs().getBillAmount());
        rawInputsRef.child("billType").setValue(data.getRawInputs().getBillType());

        //calculatedEmissions map
        DatabaseReference calcEmissionsRef = dateRef.child("calculatedEmissions");
        calcEmissionsRef.child("totalTranspo").setValue(data.getCalculatedEmissions().getTotalTranspo());
        calcEmissionsRef.child("totalFood").setValue(data.getCalculatedEmissions().getTotalFood());
        calcEmissionsRef.child("totalShopping").setValue(data.getCalculatedEmissions().getTotalShopping());
        calcEmissionsRef.child("totalEmission").setValue(data.getCalculatedEmissions().getTotalEmission());

        dateRef.child("createdAt").setValue(System.currentTimeMillis());

        //add listener to see if successful
        dateRef.child("createdAt").setValue(System.currentTimeMillis())
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "User emission data saved successfully!");
                    Toast.makeText(context, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error saving data: " + e.getMessage());
                    Toast.makeText(context, "Error saving data.", Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Method to update the emission data (both raw inputs and recalculated emissions) - used later in editing
     * for a specific user and selected date.
     * @param userId The ID of the user
     * @param selectedDate The date for which the data is to be updated
     * @param data The updated emission data
     */
    public void updateEmissionData(String userId, long selectedDate, UserEmissionData data) {

        DatabaseReference userRef = database.child("users").child(userId);

        DatabaseReference dateRef = userRef.child("emissions").child(String.valueOf(selectedDate));

        UserEmissionData.CalculatedEmissions newCalculatedEmissions = recalculateEmissions(data.getRawInputs());

        //update the raw inputs
        DatabaseReference rawInputsRef = dateRef.child("rawInputs");
        rawInputsRef.child("distanceDriven").setValue(data.getRawInputs().getDistanceDriven());
        rawInputsRef.child("vehicleType").setValue(data.getRawInputs().getVehicleType());
        rawInputsRef.child("transportType").setValue(data.getRawInputs().getTransportType());
        rawInputsRef.child("cyclingTime").setValue(data.getRawInputs().getCyclingTime());
        rawInputsRef.child("numFlights").setValue(data.getRawInputs().getNumFlights());
        rawInputsRef.child("flightType").setValue(data.getRawInputs().getFlightType());
        rawInputsRef.child("mealType").setValue(data.getRawInputs().getMealType());
        rawInputsRef.child("numServings").setValue(data.getRawInputs().getNumServings());
        rawInputsRef.child("numClothes").setValue(data.getRawInputs().getNumClothes());
        rawInputsRef.child("deviceType").setValue(data.getRawInputs().getDeviceType());
        rawInputsRef.child("numDevices").setValue(data.getRawInputs().getNumDevices());
        rawInputsRef.child("purchaseType").setValue(data.getRawInputs().getPurchaseType());
        rawInputsRef.child("numOtherPurchases").setValue(data.getRawInputs().getNumOtherPurchases());
        rawInputsRef.child("billAmount").setValue(data.getRawInputs().getBillAmount());
        rawInputsRef.child("billType").setValue(data.getRawInputs().getBillType());

        DatabaseReference calcEmissionsRef = dateRef.child("calculatedEmissions");
        calcEmissionsRef.child("totalTranspo").setValue(newCalculatedEmissions.getTotalTranspo());
        calcEmissionsRef.child("totalFood").setValue(newCalculatedEmissions.getTotalFood());
        calcEmissionsRef.child("totalShopping").setValue(newCalculatedEmissions.getTotalShopping());
        calcEmissionsRef.child("totalEmission").setValue(newCalculatedEmissions.getTotalEmission());

        dateRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Firebase", "User emission data updated successfully for user: " + userId + " on date: " + selectedDate);
                } else {
                    Log.e("Firebase", "Failed to update data", task.getException());
                }
            }
        });
    }

    private UserEmissionData.CalculatedEmissions recalculateEmissions(UserEmissionData.RawInputs rawInputs) {
        double distance = rawInputs.getDistanceDriven();
        String transportType = rawInputs.getVehicleType();
        double emissionRate = 2.4;

        if (transportType.equalsIgnoreCase("bus")) {
            emissionRate = 1.5;
        } else if (transportType.equalsIgnoreCase("train")) {
            emissionRate = 1.2;
        }

        double totalTranspo = distance * emissionRate;
        double totalFood = rawInputs.getNumServings() * 3.5;
        double totalShopping = rawInputs.getNumClothes() * 5.0;

        double totalEmission = totalTranspo + totalFood + totalShopping;
        return new UserEmissionData.CalculatedEmissions(totalTranspo, totalFood, totalShopping);
    }
}
