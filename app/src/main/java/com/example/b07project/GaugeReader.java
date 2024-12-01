package com.example.b07project;

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
     * Main method to update charts based on the selected time period
     * @param timePeriod is the time (daily, monthly, yearly)
     */
    public void updateChartForTimePeriod(String timePeriod) {
        if (timePeriod == null) return;

        long startTimestamp = 0;
        switch (timePeriod) {
            case "Daily":
                startTimestamp = System.currentTimeMillis();
                break;
            case "Monthly":
                startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30);
                break;
            case "Yearly":
                startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365);
                break;
            default:
                startTimestamp = System.currentTimeMillis();
                break;
        }

        updateChart(startTimestamp);
    }

    /**
     * Generic method to fetch emissions data
     * @param startTimestamp the time we want the function to start fetching the data
     */
    private void updateChart(long startTimestamp) {
        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, System.currentTimeMillis(), new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double totalTranspo, double totalFood, double totalShopping, double totalEmission) {
                UpdatePieChart chart = new UpdatePieChart();
                chart.updateUI(totalTranspo, totalFood, totalShopping, totalEmission);
                chart.updatePieChart(totalTranspo, totalFood, totalShopping);
            }
        });
    }

    /**
     * Fetch emissions data from Firebase
     * @param userId is the Id user logged in with
     * @param startTimestamp the start time
     * @param endTimestamp the end time
     * @param callback
     */
    private void fetchEmissionsData(String userId, long startTimestamp, long endTimestamp, final EmissionsCallback callback) {
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

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

                            for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                DataSnapshot emissionsSnapshot = dateSnapshot.child("calculatedEmissions");

                                Double transpo = emissionsSnapshot.child("totalTranspo").getValue(Double.class);
                                Double food = emissionsSnapshot.child("totalFood").getValue(Double.class);
                                Double shopping = emissionsSnapshot.child("totalShopping").getValue(Double.class);
                                Double emission = emissionsSnapshot.child("totalEmission").getValue(Double.class);

                                totalTranspo += transpo != null ? transpo : 0;
                                totalFood += food != null ? food : 0;
                                totalShopping += shopping != null ? shopping : 0;
                                totalEmission += emission != null ? emission : 0;
                            }

                            callback.onEmissionsDataFetched(totalTranspo, totalFood, totalShopping, totalEmission);
                        } else {
                            Log.e("Emissions", "Error fetching data: " + task.getException());
                            Toast.makeText(GaugeReader.this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public interface EmissionsCallback {
        void onEmissionsDataFetched(double totalTranspo, double totalFood, double totalShopping, double totalEmission);
    }
}
