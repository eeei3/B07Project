package com.example.b07project;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GaugeReader extends EcoGauge {

    public PieChart pieChart; // Declare the PieChart view

    /**
     * Updates the chart based on the selected time period.
     *
     * @param timePeriod The selected time period.
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
     * Updates the chart for daily
     */
    public void updateForDaily() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for daily emissions
        DatabaseReference dailyRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        // Get current date in the required format (e.g., "yyyy-MM-dd")
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date(currentTimeMillis));
        Log.d("Date", "Today's Date: " + today);

        dailyRef.child(today).child("calculatedEmissions").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                // Retrieve emissions values
                                double totalTranspo = snapshot.child("totalTranspo").getValue(Double.class) != null
                                        ? snapshot.child("totalTranspo").getValue(Double.class) : 0;
                                double totalFood = snapshot.child("totalFood").getValue(Double.class) != null
                                        ? snapshot.child("totalFood").getValue(Double.class) : 0;
                                double totalShopping = snapshot.child("totalShopping").getValue(Double.class) != null
                                        ? snapshot.child("totalShopping").getValue(Double.class) : 0;
                                double totalEmission = snapshot.child("totalEmission").getValue(Double.class) != null
                                        ? snapshot.child("totalEmission").getValue(Double.class) : 0;

                                // Log values for debugging
                                Log.d("DailyUpdate", "Total Transportation: " + totalTranspo);
                                Log.d("DailyUpdate", "Total Food: " + totalFood);
                                Log.d("DailyUpdate", "Total Shopping: " + totalShopping);
                                Log.d("DailyUpdate", "Total Emission: " + totalEmission);

                                // Update UI
                                transportation.setText(Integer.toString((int) totalTranspo));
                                foodConsumption.setText(Integer.toString((int) totalFood));
                                shopping.setText(Integer.toString((int) totalShopping));

                                totalEmissionsText.setText(String.format("Daily Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                                // Update pie chart with MPAndroidChart
                                updatePieChart(totalTranspo, totalFood, totalShopping);
                            } else {
                                System.out.print("No data for today");
                            }
                        } else {
                            Toast.makeText(GaugeReader.this, "Failed to fetch daily data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Updates the pie chart
     *
     * @param totalTranspo The total transportation emissions.
     * @param totalFood The total food consumption emissions.
     * @param totalShopping The total shopping emissions.
     */
    public void updatePieChart(double totalTranspo, double totalFood, double totalShopping) {
        // Create a list of PieEntry objects
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) totalTranspo, "Transportation"));
        entries.add(new PieEntry((float) totalFood, "Food Consumption"));
        entries.add(new PieEntry((float) totalShopping, "Shopping"));

        // Set data for the pie chart
        PieDataSet dataSet = new PieDataSet(entries, "Emissions");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Set pie slice colors
        PieData data = new PieData(dataSet);

        // Set data and other properties
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart
    }

    /**
     * Updates the chart for monthly
     */

    public void updateForMonthly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for emissions over the last 30 days
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        emissionsRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            double transportationEmissions = 0;
                            double foodEmissions = 0;
                            double shoppingEmissions = 0;

                            if (snapshot.exists()) {
                                // Get current date and calculate the date 30 days ago
                                long currentTime = System.currentTimeMillis();
                                long thirtyDaysAgo = currentTime - TimeUnit.DAYS.toMillis(30);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the same format as your stored dates

                                // Loop through the snapshot for the past data entries
                                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                    String date = dateSnapshot.getKey();
                                    if (date != null) {
                                        try {
                                            // Parse the stored date
                                            Date entryDate = dateFormat.parse(date);
                                            if (entryDate != null && entryDate.getTime() >= thirtyDaysAgo) {
                                                // Add emissions data only if the entry is within the last 30 days
                                                DataSnapshot calculatedEmissions = dateSnapshot.child("calculatedEmissions");
                                                transportationEmissions += calculatedEmissions.child("totalTranspo").getValue(Double.class) != null
                                                        ? calculatedEmissions.child("totalTranspo").getValue(Double.class) : 0;
                                                foodEmissions += calculatedEmissions.child("totalFood").getValue(Double.class) != null
                                                        ? calculatedEmissions.child("totalFood").getValue(Double.class) : 0;
                                                shoppingEmissions += calculatedEmissions.child("totalShopping").getValue(Double.class) != null
                                                        ? calculatedEmissions.child("totalShopping").getValue(Double.class) : 0;
                                            }
                                        } catch (Exception e) {
                                            Log.e("DateParsing", "Failed to parse date: " + date, e);
                                        }
                                    }
                                }
                            }

                            // If no data exists for the last 30 days, assume zero emissions
                            if (transportationEmissions == 0 && foodEmissions == 0 && shoppingEmissions == 0) {
                                Log.d("30DayUpdate", "No data available for the last 30 days. Assuming zero emissions.");
                            }

                            // Total emissions calculation
                            double totalEmission = transportationEmissions + foodEmissions + shoppingEmissions;

                            // Log values for debugging
                            Log.d("30DayUpdate", "Transportation: " + transportationEmissions);
                            Log.d("30DayUpdate", "Food: " + foodEmissions);
                            Log.d("30DayUpdate", "Shopping: " + shoppingEmissions);
                            Log.d("30DayUpdate", "Total Emission: " + totalEmission);

                            // Update the UI
                            transportation.setText(Integer.toString((int) transportationEmissions));
                            foodConsumption.setText(Integer.toString((int) foodEmissions));
                            shopping.setText(Integer.toString((int) shoppingEmissions));

                            totalEmissionsText.setText(String.format("Total Carbon Footprint (Last 30 Days): %.2f tons of CO₂e", totalEmission));

                            // Update Pie Chart
                            updatePieChart(transportationEmissions, foodEmissions, shoppingEmissions);
                        } else {
                            Toast.makeText(GaugeReader.this, "Failed to fetch data for the last 30 days.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Updates the chart for yearly
     */
    public void updateForYearly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for emissions over the last 365 days
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        emissionsRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            double transportationEmissions = 0;
                            double foodEmissions = 0;
                            double shoppingEmissions = 0;

                            if (snapshot.exists()) {
                                // Loop through the snapshot for the past 365 days
                                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                    String date = dateSnapshot.getKey();
                                    if (date != null) {
                                        DataSnapshot calculatedEmissions = dateSnapshot.child("calculatedEmissions");
                                        transportationEmissions += calculatedEmissions.child("totalTranspo").getValue(Double.class) != null
                                                ? calculatedEmissions.child("totalTranspo").getValue(Double.class) : 0;
                                        foodEmissions += calculatedEmissions.child("totalFood").getValue(Double.class) != null
                                                ? calculatedEmissions.child("totalFood").getValue(Double.class) : 0;
                                        shoppingEmissions += calculatedEmissions.child("totalShopping").getValue(Double.class) != null
                                                ? calculatedEmissions.child("totalShopping").getValue(Double.class) : 0;
                                    }
                                }
                            }

                            // If no data exists at all, assume zero emissions
                            if (transportationEmissions == 0 && foodEmissions == 0 && shoppingEmissions == 0) {
                                Log.d("365DayUpdate", "No data available for the last 365 days. Assuming zero emissions.");
                            }

                            // Total emissions calculation
                            double totalEmission = transportationEmissions + foodEmissions + shoppingEmissions;

                            // Log values for debugging
                            Log.d("365DayUpdate", "Transportation: " + transportationEmissions);
                            Log.d("365DayUpdate", "Food: " + foodEmissions);
                            Log.d("365DayUpdate", "Shopping: " + shoppingEmissions);
                            Log.d("365DayUpdate", "Total Emission: " + totalEmission);

                            // Update the UI
                            transportation.setText(Integer.toString((int) transportationEmissions));
                            foodConsumption.setText(Integer.toString((int) foodEmissions));
                            shopping.setText(Integer.toString((int) shoppingEmissions));

                            totalEmissionsText.setText(String.format("Total Carbon Footprint (Last 365 Days): %.2f tons of CO₂e", totalEmission));

                            // Update Pie Chart
                            updatePieChart(transportationEmissions, foodEmissions, shoppingEmissions);
                        } else {
                            Toast.makeText(GaugeReader.this, "Failed to fetch data for the last 365 days.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}