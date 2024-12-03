package com.example.b07project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import kotlin.text.UStringsKt;

public class DatabaseCommunicator {

    final DatabaseReference database;
    public Context context;
    String data;
    UserEmissionData.RawInputs raw;
    UserEmissionData.CalculatedEmissions calc;
    int rawiter;
    int calciter;

    Waiter waiter;

    public interface Waiter {
        // This is the event that we fire when operation has been completed
        void onObjectReady();
    }

    public DatabaseCommunicator(DatabaseReference db) {
        this.database = db;
        data = "";
        this.rawiter = 0;
        this.calciter = 0;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public void serverCalcEmissionReader(String selectedDay) {
        this.calc = new UserEmissionData.CalculatedEmissions();
        DatabaseReference userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("calculatedEmissions")
                .child("totalTranspo");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalEmission(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter >= 4) {
                        waiter.onObjectReady();
                    }
                    calciter++;
                    // This is where you should put how View should handle the data.


                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("calculatedEmissions")
                .child("totalFood");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalFood(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter >= 4) {
                        waiter.onObjectReady();
                    }
                    calciter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("calculatedEmissions")
                .child("totalShopping");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalShopping(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter >= 4) {
                        waiter.onObjectReady();
                    }
                    calciter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("calculatedEmissions")
                .child("totalEmission");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalEmission(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter >= 4) {
                        waiter.onObjectReady();
                    }
                    calciter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
    }

    public void serverRawInputReader(String selectedDay) {
        this.raw = new UserEmissionData.RawInputs();
        DatabaseReference userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("distanceDriven");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getValue() == null) {
                        System.out.println("task returns null");
                    }
                    try {
                        raw.setDistanceDriven(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setDistanceDriven(2);
                    }


                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("vehicleType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setVehicleType(String.valueOf(task.getResult().getValue()));
                    if (raw.getVehicleType() != null) {
                        System.out.println("able to grab vehicle type");
                        System.out.println(raw.getVehicleType());
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    System.out.println("not able to grab vehicle type");
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("transportType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setTransportType(String.valueOf(task.getResult().getValue()));
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("cyclingTime");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setCyclingTime(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setCyclingTime(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("numFlights");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setNumFlights(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    }
                    catch (NumberFormatException e) {
                        raw.setNumFlights(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("flightType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setFlightType(String.valueOf(task.getResult().getValue()));
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("mealType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setMealType(String.valueOf(task.getResult().getValue()));
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("numServings");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setNumServings(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setNumServings(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("numClothes");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setNumClothes(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setNumClothes(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("deviceType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setDeviceType(String.valueOf(task.getResult().getValue()));
                    if (rawiter>= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("numDevices");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setNumDevices(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setNumDevices(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("purchaseType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setPurchaseType(String.valueOf(task.getResult().getValue()));
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("numOtherPurchases");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setNumOtherPurchases(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setNumOtherPurchases(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("billAmount");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        raw.setBillAmount(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    } catch (NumberFormatException e) {
                        raw.setBillAmount(0);
                    }
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });
        userRef = database.child("users")
                .child(EcoTrackerHomeActivity.userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay))
                .child("rawInputs")
                .child("billType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setBillType(String.valueOf(task.getResult().getValue()));
                    if (rawiter >= 14) {
                        waiter.onObjectReady();
                    }
                    rawiter++;
                    // This is where you should put how View should handle the data.
                }
                else {
                    throw new RuntimeException("Database read error");
                }
            }
        });

    }

    /**
     * Method to save user emission data (raw inputs & calculated emissions)
     * Data is stored under the user ID and date
     * @param selectedDay the selected date
     * @param data the emission data (raw inputs & calculated emissions)
     */
    public void saveUserEmissionData(String selectedDay, UserEmissionData data) {

        DatabaseReference userRef = database.child("users").child(EcoTrackerHomeActivity.userId);

        DatabaseReference dateRef = userRef.child("ecotracker").child(String.valueOf(selectedDay));

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
                    Toast.makeText(context.getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error saving data: " + e.getMessage());
                    Toast.makeText(context.getApplicationContext(), "Error saving data.", Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Method to update the emission data (both raw inputs and recalculated emissions) - used later in editing
     * for a specific user and selected date.
     * @param selectedDay The date for which the data is to be updated
     * @param data The updated emission data
     */
    public void updateEmissionData(String selectedDay, UserEmissionData data) {

        DatabaseReference userRef = database.child("users").child(EcoTrackerHomeActivity.userId);

        DatabaseReference dateRef = userRef.child("emissions").child(String.valueOf(selectedDay));

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
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Firebase", "User emission data updated successfully for user: " + EcoTrackerHomeActivity.userId + " on date: " + selectedDay);
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
