package com.example.b07project;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UpdateLineChart extends EcoGauge {

    public void updateLineChartForTimePeriod(String timePeriod) {
        if (timePeriod == null) return;
        switch (timePeriod) {
            case "Daily":
                updateLastSevenDaysChart();
                break;
            case "Monthly":
                updateLastThirtyDaysChart();
                break;
            case "Yearly":
                updateLastThreeSixtyFiveDaysChart();
                break;
            default:
                updateLastSevenDaysChart();
                break;
        }
    }

    /**
     * Fetch the total emissions of the last seven days and display them on the line chart
     */
    /**
     * Update the chart with data for the last 7 days
     */
    public void updateLastSevenDaysChart() {
        long startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7); // 7 days ago
        long endTimestamp = System.currentTimeMillis(); // Current time


        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, endTimestamp, new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray) {
                updateChartWithData(emissionsArray, dateArray);
            }
        }, 7); // Pass 7 for 7 days
    }

    /**
     * Update the chart with data for the last 30 days
     */
    public void updateLastThirtyDaysChart() {
        long startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30); // 30 days ago
        long endTimestamp = System.currentTimeMillis(); // Current time


        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, endTimestamp, new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray) {
                updateChartWithData(emissionsArray, dateArray);
            }
        }, 30); // Pass 30 for 30 days
    }


    /**
     * Update the chart with data for the last 365 days
     */
    public void updateLastThreeSixtyFiveDaysChart() {
        long startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365); // 365 days ago
        long endTimestamp = System.currentTimeMillis(); // Current time


        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, endTimestamp, new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray) {
                updateChartWithData(emissionsArray, dateArray);
            }
        }, 365); // Pass 365 for 365 days
    }

    /**
     * Fetch emissions data from Firebase for the given time range
     * @param userId the user ID
     * @param startTimestamp the start time
     * @param endTimestamp the end time
     * @param callback the callback to handle the fetched data
     */
    private void fetchEmissionsData(String userId, long startTimestamp, long endTimestamp, final EmissionsCallback callback, int numberOfDays) {
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


                            // Dynamically allocate arrays for emissions data and dates based on the number of days
                            double[] emissionsArray = new double[numberOfDays];
                            String[] dateArray = new String[numberOfDays];


                            int index = 0;
                            for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                DataSnapshot emissionsSnapshot = dateSnapshot.child("calculatedEmissions");


                                Double emission = emissionsSnapshot.child("totalEmission").getValue(Double.class);
                                if (emission == null) {
                                    Log.w("Emissions", "Emission data is null for date: " + dateSnapshot.getKey());
                                    emissionsArray[index] = 0; // Default value
                                } else {
                                    emissionsArray[index] = emission;
                                }


                                // Store date
                                String date = dateSnapshot.getKey(); // Assuming the date is stored as the key
                                dateArray[index] = date;


                                index++;
                                if (index == numberOfDays) break; // Limit to the required number of days
                            }


                            // Pass the data to the callback
                            callback.onEmissionsDataFetched(emissionsArray, dateArray);
                        } else {
                            Log.e("Emissions", "Error fetching data: " + task.getException());
                            Toast.makeText(UpdateLineChart.this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Interface for the emissions callback
     */
    public interface EmissionsCallback {
        void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray);
    }

    /**
     * Update the chart with the fetched emissions data
     * @param emissionsArray the emissions data
     * @param dateArray the corresponding dates
     */
    private void updateChartWithData(double[] emissionsArray, String[] dateArray) {
        String transportationColorHex = "#AD80C4";
        String foodColorHex = "#81BAC5"; //Darker Blue
        String shoppingColorHex = "#A1C6CA"; //Lighter Blue

        // Prepare the data for the chart
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < emissionsArray.length; i++) {
            entries.add(new Entry(i, (float) emissionsArray[i])); // Adding day-wise emission data
        }

        // Set up the data set and chart
        LineDataSet dataSet = new LineDataSet(entries, "Total Emissions (kg CO2)"); // Emissions data
        LineData lineData = new LineData(dataSet);

        dataSet.setColor(Color.parseColor(foodColorHex));
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(true);
        dataSet.setCircleColor(Color.parseColor(transportationColorHex));
        dataSet.setCircleRadius(5f);

        System.out.println("Line Data: ");
        for (int i = 0; i < lineData.getDataSetCount(); i++) {
            LineDataSet set = (LineDataSet) lineData.getDataSetByIndex(i);
            System.out.println("DataSet " + i + " (" + set.getLabel() + "): ");
            for (Entry entry : set.getValues()) {
                System.out.println("Entry: Day " + entry.getX() + ", Emission: " + entry.getY() + " kg CO2");
            }
        }
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }
}