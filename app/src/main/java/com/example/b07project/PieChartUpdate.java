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

import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PieChartUpdate extends EcoGauge {

    /**
     * Updates the chart based on the selected time period.
     *
     * @param timePeriod The selected time period.
     */
    protected void updateChartForTimePeriod(String timePeriod) {
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
     * Updates the chart based for daily
     */
    private void updateForDaily() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for daily emissions
        DatabaseReference dailyRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        // Get current date in the required format (e.g., "yyyy-MM-dd")
        long currentTimeMillis = System.currentTimeMillis();
        String today = DatesForDataBase.formatDate(currentTimeMillis);

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
                                        ? snapshot.child("totalShopping").getValue(Double.class) : 0;

                                // Update UI
                                transportation.setText(Integer.toString((int) totalTranspo));
                                foodConsumption.setText(Integer.toString((int) totalFood));
                                shopping.setText(Integer.toString((int) totalShopping));

                                totalEmissionsText.setText(String.format("Daily Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                                // Update pie chart
                                pieChart.clearChart();
                                pieChart.addPieSlice(new PieModel("Transportation", (int) totalTranspo, Color.parseColor("#FFA726")));
                                pieChart.addPieSlice(new PieModel("Food Consumption", (int) totalFood, Color.parseColor("#66BB6A")));
                                pieChart.addPieSlice(new PieModel("Shopping", (int) totalShopping, Color.parseColor("#EF5350")));
                            } else {
                                Toast.makeText(PieChartUpdate.this, "No data for today.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PieChartUpdate.this, "Failed to fetch daily data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Updates the chart based for monthly
     */
    private void updateForMonthly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for monthly emissions
        DatabaseReference monthlyRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        // Get the first day of the current month and today's date
        Calendar calendar = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        String firstDayOfMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        monthlyRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        double totalTranspo = 0;
                        double totalFood = 0;
                        double totalShopping = 0;
                        double totalEmission = 0;

                        // Iterate through the dates within the current month
                        for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                            String date = dateSnapshot.getKey();
                            if (date != null && date.compareTo(firstDayOfMonth) >= 0 && date.compareTo(today) <= 0) {
                                DataSnapshot calculatedEmissions = dateSnapshot.child("calculatedEmissions");
                                totalTranspo += calculatedEmissions.child("totalTranspo").getValue(Double.class) != null
                                        ? calculatedEmissions.child("totalTranspo").getValue(Double.class) : 0;
                                totalFood += calculatedEmissions.child("totalFood").getValue(Double.class) != null
                                        ? calculatedEmissions.child("totalFood").getValue(Double.class) : 0;
                                totalShopping += calculatedEmissions.child("totalShopping").getValue(Double.class) != null
                                        ? calculatedEmissions.child("totalShopping").getValue(Double.class) : 0;
                                totalEmission += calculatedEmissions.child("totalEmission").getValue(Double.class) != null
                                        ? calculatedEmissions.child("totalEmission").getValue(Double.class) : 0;
                            }
                        }

                        // Update UI
                        transportation.setText(Integer.toString((int) totalTranspo));
                        foodConsumption.setText(Integer.toString((int) totalFood));
                        shopping.setText(Integer.toString((int) totalShopping));
                        totalEmissionsText.setText(String.format("Monthly Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                        // Update pie chart
                        pieChart.clearChart();
                        pieChart.addPieSlice(new PieModel("Transportation", (int) totalTranspo, Color.parseColor("#FFA726")));
                        pieChart.addPieSlice(new PieModel("Food Consumption", (int) totalFood, Color.parseColor("#66BB6A")));
                        pieChart.addPieSlice(new PieModel("Shopping", (int) totalShopping, Color.parseColor("#EF5350")));
                    } else {
                        Toast.makeText(PieChartUpdate.this, "No data available for this month.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PieChartUpdate.this, "Failed to fetch monthly data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Updates the chart based for yearly
     */
    private void updateForYearly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for yearly emissions
        DatabaseReference yearlyRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("annualEmissions");

        yearlyRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                // Retrieve emissions values
                                double transportationEmissions = snapshot.child("transportationEmissions").getValue(Double.class) != null
                                        ? snapshot.child("transportationEmissions").getValue(Double.class) : 0;
                                double foodEmissions = snapshot.child("foodEmissions").getValue(Double.class) != null
                                        ? snapshot.child("foodEmissions").getValue(Double.class) : 0;
                                double housingEmissions = snapshot.child("housingEmissions").getValue(Double.class) != null
                                        ? snapshot.child("housingEmissions").getValue(Double.class) : 0;
                                double consumptionEmissions = snapshot.child("consumptionEmissions").getValue(Double.class) != null
                                        ? snapshot.child("consumptionEmissions").getValue(Double.class) : 0;
                                double totalEmission = snapshot.child("totalEmissions").getValue(Double.class) != null
                                        ? snapshot.child("totalEmissions").getValue(Double.class) : 0;

                                // Update UI
                                transportation.setText(Integer.toString((int) transportationEmissions));
                                foodConsumption.setText(Integer.toString((int) foodEmissions));
                                shopping.setText(Integer.toString((int) housingEmissions));
                                energyUse.setText(Integer.toString((int) consumptionEmissions));
                                totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                                // Update pie chart
                                pieChart.clearChart();
                                pieChart.addPieSlice(new PieModel("Transportation", (int) transportationEmissions, Color.parseColor("#FFA726")));
                                pieChart.addPieSlice(new PieModel("Food Consumption", (int) foodEmissions, Color.parseColor("#66BB6A")));
                                pieChart.addPieSlice(new PieModel("Housing", (int) housingEmissions, Color.parseColor("#EF5350")));
                                pieChart.addPieSlice(new PieModel("Consumption", (int) consumptionEmissions, Color.parseColor("#29B6F6")));
                            } else {
                                Toast.makeText(PieChartUpdate.this, "No yearly data found.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PieChartUpdate.this, "Failed to fetch yearly data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
