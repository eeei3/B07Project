package com.example.b07project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

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

    public void serverCalcEmissionReader(Long selectedDate) {
        this.calc = new UserEmissionData.CalculatedEmissions();
        DatabaseReference userRef = database.child("users")
                                            .child("ecotracker")
                                            .child(String.valueOf(selectedDate))
                                            .child("rawInputs")
                                            .child("totalTranspo");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalEmission(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter <= 4) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("totalFood");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalFood(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter <= 4) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("totalShopping");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalShopping(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter <= 4) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("totalTranspo");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    calc.setTotalEmission(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (calciter <= 4) {
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

    public void serverRawInputReader(Long selectedDate) {
        this.raw = new UserEmissionData.RawInputs();
        DatabaseReference userRef = database.child("users")
                                            .child("ecotracker")
                                            .child(String.valueOf(selectedDate))
                                            .child("calculatedEmissions")
                                            .child("");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setDistanceDriven(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("vehicleType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setVehicleType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("transportType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setTransportType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("cyclingTime");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setCyclingTime(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("numFLights");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setNumFlights(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("flightType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setFlightType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("mealType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setMealType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("numServings");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setNumServings(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("numClothes");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setNumClothes(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("deviceType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setDeviceType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("numDevices");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setNumDevices(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("purchaseType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setPurchaseType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("numOtherPurchases");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setNumOtherPurchases(Integer.parseInt(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("billAmount");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setBillAmount(Double.parseDouble(String.valueOf(task.getResult().getValue())));
                    if (rawiter <= 15) {
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
                .child("ecotracker")
                .child(String.valueOf(selectedDate))
                .child("rawInputs")
                .child("billType");
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    raw.setBillType(String.valueOf(task.getResult().getValue()));
                    if (rawiter <= 15) {
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
     * @param userId the id of the user
     * @param selectedDate the selected date
     * @param data the emission data (raw inputs & calculated emissions)
     */
    public void saveUserEmissionData(String userId, long selectedDate, UserEmissionData data) {

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
