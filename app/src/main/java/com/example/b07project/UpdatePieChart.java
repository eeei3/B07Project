package com.example.b07project;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UpdatePieChart extends EcoGauge{
    public PieChart pieChart;

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

                                double totalEmission = totalTranspo + totalFood + totalShopping;

                                // Log values for debugging
                                Log.d("DailyUpdate", "Total Transportation: " + totalTranspo);
                                Log.d("DailyUpdate", "Total Food: " + totalFood);
                                Log.d("DailyUpdate", "Total Shopping: " + totalShopping);
                                Log.d("DailyUpdate", "Total Emission: " + totalEmission);

                                totalEmissionsText.setText(String.format("Daily Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                                // Update pie chart with MPAndroidChart
                                updatePieChart(totalTranspo, totalFood, totalShopping);
                            } else {
                                System.out.print("No data for today");
                            }
                        } else {
                            Toast.makeText(UpdatePieChart.this, "Failed to fetch daily data.", Toast.LENGTH_SHORT).show();
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
        String transportationColorHex = "#AD80C4"; //Purple
        String foodColorHex = "#81BAC5"; //Darker Blue
        String shoppingColorHex = "#A1C6CA"; //Lighter Blue

        int[] colors = new int[]{Color.parseColor(transportationColorHex), Color.parseColor(foodColorHex), Color.parseColor(shoppingColorHex),};
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) totalTranspo, ""));
        entries.add(new PieEntry((float) totalFood, ""));
        entries.add(new PieEntry((float) totalShopping, ""));

        // Set data for the pie chart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors); // Set pie slice colors
        PieData data = new PieData(dataSet);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getDescription().setEnabled(false);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextSize(14f); // Set text size for values
        dataSet.setValueTextColor(Color.WHITE); // Set text color for values

        // Customizing the Legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true); // Enable legend
        legend.setTextSize(12f); // Customize the text size of the legend
        legend.setFormSize(12f); // Customize the size of the square icon in the legend

        // Set custom entries for the legend
        ArrayList<LegendEntry> customLegendEntries = new ArrayList<>();

        String roundedTransport = String.format(Locale.getDefault(), "%.2f", totalTranspo);
        String roundedFood = String.format(Locale.getDefault(), "%.2f", totalFood);
        String roundedShopping = String.format(Locale.getDefault(), "%.2f", totalShopping);

        customLegendEntries.add(new LegendEntry("Transport: " + roundedTransport + "  ", Legend.LegendForm.CIRCLE, 14f, 14f, null, Color.parseColor(transportationColorHex)));
        customLegendEntries.add(new LegendEntry("Food: " + roundedFood + "  ", Legend.LegendForm.CIRCLE, 14f, 14f, null, Color.parseColor(foodColorHex)));
        customLegendEntries.add(new LegendEntry("Shopping: " + roundedShopping + "  ", Legend.LegendForm.CIRCLE, 14f, 14f, null, Color.parseColor(shoppingColorHex)));

        legend.setCustom(customLegendEntries); // Set the custom legend entries

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

                            totalEmissionsText.setText(String.format("Monthly Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                            // Update Pie Chart
                            updatePieChart(transportationEmissions, foodEmissions, shoppingEmissions);
                        } else {
                            Toast.makeText(UpdatePieChart.this, "Failed to fetch data for the last 30 days.", Toast.LENGTH_SHORT).show();
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


                            totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                            // Update Pie Chart
                            updatePieChart(transportationEmissions, foodEmissions, shoppingEmissions);
                        } else {
                            Toast.makeText(UpdatePieChart.this, "Failed to fetch data for the last 365 days.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}