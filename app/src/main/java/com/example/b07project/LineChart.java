package com.example.b07project;

import android.graphics.Color;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;

/*
 * LineChart class works with Firebase to retrieve emissions data for the day, month, and year
 * and updates a ValueLineChart accordingly to visualize emissions data.
 */
public class LineChart {
    private ValueLineChart chart;  // Chart object for displaying the emissions data
    private FirebaseUser user;  // FirebaseUser object for current authenticated user
    private DatabaseReference userRef;  // Reference to the user's data in Firebase

    /*
     * Constructor initializes the chart and sets up Firebase references based on the current user.
     */
    public LineChart(ValueLineChart chart) {
        this.chart = chart;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();  // Get the current authenticated user

        // Check if the user is authenticated, otherwise, set userId as empty string
        String userId;
        if (user != null) {
            userId = user.getUid();  // Get the UID of the authenticated user
        } else {
            userId = "";  // If no user is authenticated, set userId as an empty string
        }

        // Set the reference to the user's data in Firebase
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    /*
     * Retrieves data for daily, monthly, and annual emissions from Firebase and updates the chart.
     */
    public void getDataForChart() {
        // Create series for daily, monthly, and yearly emissions
        ValueLineSeries dailySeries = new ValueLineSeries();
        dailySeries.setColor(Color.RED);
        ValueLineSeries monthlySeries = new ValueLineSeries();
        monthlySeries.setColor(Color.GREEN);
        ValueLineSeries annualSeries = new ValueLineSeries();
        annualSeries.setColor(Color.BLUE);

        long currentTime = System.currentTimeMillis();
        int completedQueries = 0;

        // --- Daily Emissions: Last 7 Days ---
        for (int i = 0; i < 7; i++) {
            long startOfDay = DatesForDataBase.getStartOfDay(currentTime - i * 24 * 60 * 60 * 1000); // Go back one day each iteration
            long endOfDay = DatesForDataBase.getEndOfDay(currentTime - i * 24 * 60 * 60 * 1000);

            userRef.child("ecotracker")
                    .orderByKey()
                    .startAt(String.valueOf(startOfDay))
                    .endAt(String.valueOf(endOfDay))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            double dailyEmissions = 0;
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                Double emission = snapshot.child("totalEmission").getValue(Double.class);
                                if (emission != null) dailyEmissions += emission;
                            }
                            dailySeries.addPoint(new ValueLinePoint(DatesForDataBase.formatDate(startOfDay), (float) dailyEmissions));
                        }
                    });
        }

        // --- Monthly Emissions: From Login Date to End of Month ---
        long startOfMonth = DatesForDataBase.getStartOfMonth(currentTime);
        long endOfMonth = DatesForDataBase.getEndOfMonth(currentTime);

        for (long day = startOfMonth; day <= endOfMonth; day += 24 * 60 * 60 * 1000) {
            long startOfDay = DatesForDataBase.getStartOfDay(day);
            long endOfDay = DatesForDataBase.getEndOfDay(day);

            userRef.child("ecotracker")
                    .orderByKey()
                    .startAt(String.valueOf(startOfDay))
                    .endAt(String.valueOf(endOfDay))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            double dailyEmissions = 0;
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                Double emission = snapshot.child("totalEmission").getValue(Double.class);
                                if (emission != null) dailyEmissions += emission;
                            }
                            monthlySeries.addPoint(new ValueLinePoint(DatesForDataBase.formatDate(startOfDay), (float) dailyEmissions));
                        }
                    });
        }

        // --- Yearly Emissions: Last 5 Years ---
        for (int i = 0; i < 5; i++) {
            long startOfYear = DatesForDataBase.getStartOfYear(currentTime - i * 365L * 24 * 60 * 60 * 1000); // Go back one year each iteration
            long endOfYear = DatesForDataBase.getEndOfYear(currentTime - i * 365L * 24 * 60 * 60 * 1000);

            int finalI = i;
            userRef.child("ecotracker")
                    .orderByKey()
                    .startAt(String.valueOf(startOfYear))
                    .endAt(String.valueOf(endOfYear))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            double yearlyEmissions = 0;
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                Double emission = snapshot.child("totalEmission").getValue(Double.class);
                                if (emission != null) yearlyEmissions += emission;
                            }
                            annualSeries.addPoint(new ValueLinePoint(String.valueOf(2024 - finalI), (float) yearlyEmissions)); // Label by year
                        }
                    });
        }

        // After all queries, update the chart
        updateLineChart(dailySeries, monthlySeries, annualSeries);
    }


    /*
     * Updates the line chart by adding the series for daily, monthly, and annual emissions.
     * This method is called when all calls for data are done
     */
    private void updateLineChart(ValueLineSeries dailySeries, ValueLineSeries monthlySeries, ValueLineSeries annualSeries) {
        // Clear existing data to refresh the chart
        chart.clearChart();

        // Add each series to the chart
        if (!dailySeries.getSeries().isEmpty()) {
            chart.addSeries(dailySeries); // Add daily emissions line
        }

        if (!monthlySeries.getSeries().isEmpty()) {
            chart.addSeries(monthlySeries); // Add monthly emissions line
        }

        if (!annualSeries.getSeries().isEmpty()) {
            chart.addSeries(annualSeries); // Add yearly emissions line
        }

        // Refresh the chart to display the new data
        chart.startAnimation();
    }

}
