package com.example.b07project;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.TimeUnit;

public class GaugeReader extends EcoGauge {

    /**
     * Main Method to update charts based on given data
     * @param timePeriod is the selected time
     */
    public void updateChartForTimePeriod(String timePeriod) {
        if (timePeriod == null) return;
        switch (timePeriod) {
            case "Daily":
                updateForDaily();
                break;
            case "Monthly":
                updateForMonthly();
                break;
            case "Yearly":
                updateForYearly();
                break;
            default:
                updateForDaily();
                break;
        }
    }

    /**
     * Method to get data from firebase
     * @param userId is the userId that user logged in with
     * @param startTimestamp is starting time we want to fetch data from
     * @param endTimestamp is the ending time till we want the data
     * @param callback
     */
    private void fetchEmissionsData(String userId, long startTimestamp, long endTimestamp, final EmissionsCallback callback) {
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        // Querying for the records between the provided start and end timestamps
        emissionsRef.orderByKey()
                .startAt(DatesForDataBase.getFormattedDate(startTimestamp))
                .endAt(DatesForDataBase.getFormattedDate(endTimestamp))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            double totalTranspo = 0, totalFood = 0, totalShopping = 0, totalEmission = 0;

                            // Iterating over the snapshot data for each day
                            for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                // Access the "calculatedEmissions" node within each date node
                                DataSnapshot emissionsSnapshot = dateSnapshot.child("calculatedEmissions");

                                // Access individual emission fields within "calculatedEmissions"
                                Double transpo = emissionsSnapshot.child("totalTranspo").getValue(Double.class);
                                Double food = emissionsSnapshot.child("totalFood").getValue(Double.class);
                                Double shopping = emissionsSnapshot.child("totalShopping").getValue(Double.class);
                                Double emission = emissionsSnapshot.child("totalEmission").getValue(Double.class);

                                // Summing the emission data, ensuring null values are handled
                                totalTranspo += transpo != null ? transpo : 0;
                                totalFood += food != null ? food : 0;
                                totalShopping += shopping != null ? shopping : 0;
                                totalEmission += emission != null ? emission : 0;
                            }

                            // Returning the result through the callback
                            callback.onEmissionsDataFetched(totalTranspo, totalFood, totalShopping, totalEmission);
                        } else {
                            Log.e("Emissions", "Error fetching data");
                            Toast.makeText(GaugeReader.this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Method to update the chart for daily emissions
     */
    public void updateForDaily() {
        String userId = initializeFirebaseUser();

        fetchEmissionsData(userId, System.currentTimeMillis(), System.currentTimeMillis(), new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double totalTranspo, double totalFood, double totalShopping, double totalEmission) {
                UpdatePieChart chart = new UpdatePieChart();
                chart.updateUI(totalTranspo, totalFood, totalShopping, totalEmission);
                chart.updatePieChart(totalTranspo, totalFood, totalShopping);
            }
        });
    }

    /**
     * Method to update the chart for monthly emissions
     */
    public void updateForMonthly() {
        String userId = initializeFirebaseUser();
        long thirtyDaysAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30);

        fetchEmissionsData(userId, thirtyDaysAgo, System.currentTimeMillis(), new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double totalTranspo, double totalFood, double totalShopping, double totalEmission) {
                UpdatePieChart chart = new UpdatePieChart();
                chart.updateUI(totalTranspo, totalFood, totalShopping, totalEmission);
                chart.updatePieChart(totalTranspo, totalFood, totalShopping);
            }
        });
    }

    /**
     * Method to update the chart for yearly emissions
     */
    public void updateForYearly() {
        String userId = initializeFirebaseUser();
        long oneYearAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365);

        fetchEmissionsData(userId, oneYearAgo, System.currentTimeMillis(), new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double totalTranspo, double totalFood, double totalShopping, double totalEmission) {
                UpdatePieChart chart = new UpdatePieChart();
                chart.updateUI(totalTranspo, totalFood, totalShopping, totalEmission);
                chart.updatePieChart(totalTranspo, totalFood, totalShopping);
            }
        });
    }

    // Callback interface for emissions data
    public interface EmissionsCallback {
        void onEmissionsDataFetched(double totalTranspo, double totalFood, double totalShopping, double totalEmission);
    }
}
