package com.example.b07project;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


import java.util.Arrays;

public class PieChartUpdate extends AppCompatActivity {
    private TextView totalEmissionsText;
    private TextView transportation;
    private TextView foodConsumption;
    private TextView energyUse;
    private TextView shopping;
    private PieChart pieChart;

    public static final String EXTRA_TIME_PERIOD = "timePeriod";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);

        // Initialize UI components
        transportation = findViewById(R.id.transportation);
        foodConsumption = findViewById(R.id.foodConsumption);
        shopping = findViewById(R.id.shopping);
        energyUse = findViewById(R.id.energyUse);
        pieChart = findViewById(R.id.piechart);
        totalEmissionsText = findViewById(R.id.totalEmissionsText);

        // Retrieve the time period and update the chart
        String timePeriod = getIntent().getStringExtra(EXTRA_TIME_PERIOD);
        if (timePeriod != null) {
            updateChartForTimePeriod(timePeriod);
            updateTotalEmissionsText(timePeriod);
        } else {
            Log.e("PieChartUpdate", "No time period provided in the Intent");
            Toast.makeText(this, "Error: Time period is missing.", Toast.LENGTH_SHORT).show();
        }
    }


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
            case "Select a time period":
                updateForDaily();
                break;
        }
    }

    /**
     * Updates the chart based for daily
     */
    private void updateForDaily() {
        if (getIntent() == null) {
            Log.e("PieChartUpdate", "Intent is null");
            Toast.makeText(this, "Error: No data provided.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the total emissions passed from the EcoTracker
        double totalTranspo = getIntent().getDoubleExtra("totalTranspo", 0);
        double totalFood = getIntent().getDoubleExtra("totalFood", 0);
        double totalShopping = getIntent().getDoubleExtra("totalShopping", 0);
        // Set the percentage of emissions to pie chart
        transportation.setText(Integer.toString((int) totalTranspo));
        foodConsumption.setText(Integer.toString((int) totalFood));
        shopping.setText(Integer.toString((int) totalShopping));


        // Set the data and color to the pie chart
        pieChart.addPieSlice(new PieModel("Transportation", (int) totalTranspo, Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Food Consumption", (int) totalFood, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Shopping", (int) totalShopping, Color.parseColor("#EF5350")));
    }

    /**
     * Updates the chart based for monthly
     */
    private void updateForMonthly() {
        // Check if the user is authenticated, otherwise, set userId as empty string
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser(); // Check if the user is authenticated, otherwise, set userId as empty string

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("ecotracker");

        // Get current time and calculate the start and end of the current month
        long currentTimeMillis = System.currentTimeMillis();
        long startOfMonth = DatesForDataBase.getStartOfMonth(currentTimeMillis); // Method to calculate the start of the current month
        long endOfMonth = DatesForDataBase.getEndOfMonth(currentTimeMillis); // Method to calculate the end of the current month

        // Query data from Firebase for the current month
        databaseRef.orderByKey().startAt(String.valueOf(startOfMonth)).endAt(String.valueOf(endOfMonth)).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalTranspo = 0;
                            double totalFood = 0;
                            double totalShopping = 0;

                            // Aggregate data for the entire month
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                // Assuming the data structure has fields like "transportation", "food", "shopping"
                                Double transpoEmission = snapshot.child("transportation").getValue(Double.class);
                                Double foodEmission = snapshot.child("foodConsumption").getValue(Double.class);
                                Double shoppingEmission = snapshot.child("shopping").getValue(Double.class);

                                // Add the emissions to the respective totals (account for null values)
                                if (transpoEmission != null) totalTranspo += transpoEmission;
                                if (foodEmission != null) totalFood += foodEmission;
                                if (shoppingEmission != null) totalShopping += shoppingEmission;
                            }

                            // Set the percentage of emissions to pie chart
                            transportation.setText(Integer.toString((int)totalTranspo));
                            foodConsumption.setText(Integer.toString((int) totalFood));
                            shopping.setText(Integer.toString((int)totalShopping));

                            // Set the data and colors for the pie chart
                            pieChart.clearChart(); // Clear previous data before adding new slices

                            // Add pie slices for each category
                            pieChart.addPieSlice(new PieModel("Transportation", (int) totalTranspo, Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Food Consumption", (int) totalFood, Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Shopping", (int) totalShopping, Color.parseColor("#EF5350")));
                        } else {
                            // Handle the error if the Firebase query fails
                            Toast.makeText(PieChartUpdate.this, "Failed to load monthly data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Updates the chart based for yearly
     */
    private void updateForYearly() {
        if (getIntent() == null) {
            Log.e("PieChartUpdate", "Intent is null");
            Toast.makeText(this, "Error: No data provided.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Retrieve the total emissions passed from the SurveyActivity
        double transportationEmissions = getIntent().getDoubleExtra("transportationEmissions", 0);
        double foodEmissions = getIntent().getDoubleExtra("foodEmissions", 0);
        double housingEmissions = getIntent().getDoubleExtra("housingEmissions", 0);
        double consumptionEmissions = getIntent().getDoubleExtra("consumptionEmissions", 0);

        // Set the percentage of emissions to pie chart
        transportation.setText(Integer.toString((int) transportationEmissions));
        foodConsumption.setText(Integer.toString((int) foodEmissions));
        shopping.setText(Integer.toString((int) housingEmissions));
        energyUse.setText(Integer.toString((int) consumptionEmissions));


        // Set the data and color to the pie chart
        pieChart.addPieSlice(new PieModel("Transportation",Integer.parseInt(transportation.getText().toString()),
                Color.parseColor("#FFA726")));

        pieChart.addPieSlice(new PieModel("Food Consumption",Integer.parseInt(foodConsumption.getText().toString()),
                Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Shopping",Integer.parseInt(shopping.getText().toString()),
                Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Energy Use",Integer.parseInt(energyUse.getText().toString()),
                Color.parseColor("#29B6F6")));
    }

    /**
     * Updates the comparison totalEmissionsText
     *
     * @param timePeriod The comparison string.
     */
    void updateTotalEmissionsText(String timePeriod) {
       switch (timePeriod){
           case "Daily":
               double totalEmission1 = getIntent().getDoubleExtra("totalEmission", 0);
               totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission1));
               break;
           case "Monthly":
               double totalEmission2 = getIntent().getDoubleExtra("totalEmission", 0); // This could be for the entire month
               totalEmissionsText.setText(String.format("Monthly Carbon Footprint: %.2f tons of CO₂e", totalEmission2));
               break;
           case "Yearly":
               double totalEmission3 = getIntent().getDoubleExtra("totalEmissions", 0);
               totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission3));
       }
    }
}
