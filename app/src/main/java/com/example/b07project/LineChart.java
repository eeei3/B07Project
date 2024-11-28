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
        // Create different series for daily, monthly, and annual emissions
        List<ValueLineSeries> seriesList = new ArrayList<>();

        ValueLineSeries dailySeries = new ValueLineSeries();
        dailySeries.setColor(Color.RED);  // Set the color of the daily emissions line
        ValueLineSeries monthlySeries = new ValueLineSeries();
        monthlySeries.setColor(Color.GREEN);  // Set the color of the monthly emissions line
        ValueLineSeries annualSeries = new ValueLineSeries();
        annualSeries.setColor(Color.BLUE);  // Set the color of the annual emissions line

        // Get the current time and calculate start and end of day, month, and year
        long time = System.currentTimeMillis();
        long startOfDay = DatesForDataBase.getStartOfDay(time);
        long endOfDay = DatesForDataBase.getEndOfDay(time);
        long startOfMonth = DatesForDataBase.getStartOfMonth(time);
        long endOfMonth = DatesForDataBase.getEndOfMonth(time);

        final int[] completedQueries = {0};  // Array to track the completion of all queries

        // Query to get emissions data for the current day
        userRef.child("ecotracker")
                .orderByKey()
                .startAt(String.valueOf(startOfDay))  // Filter data from the start of the day
                .endAt(String.valueOf(endOfDay))  // Filter data until the end of the day
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalDailyEmissions = 0;  // Variable to hold total emissions for the day
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double totalEmission = snapshot.child("totalEmission").getValue(Double.class);
                            if (totalEmission != null) totalDailyEmissions += totalEmission;  // Sum emissions for the day
                        }
                        dailySeries.addPoint(new ValueLinePoint((float) totalDailyEmissions));  // Add the data point to the daily series
                    }
                    completedQueries[0]++;  // Increment the completed queries counter
                    // If all queries are completed, update the chart with the new data
                    if (completedQueries[0] == 3) {
                        updateLineChart(seriesList, dailySeries, monthlySeries, annualSeries);
                    }
                });

        // Query to get emissions data for the current month
        userRef.child("ecotracker")
                .orderByKey()
                .startAt(String.valueOf(startOfMonth))  // Filter data from the start of the month
                .endAt(String.valueOf(endOfMonth))  // Filter data until the end of the month
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalMonthlyEmissions = 0;  // Variable to hold total emissions for the month
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double totalEmission = snapshot.child("totalEmission").getValue(Double.class);
                            if (totalEmission != null) totalMonthlyEmissions += totalEmission;  // Sum emissions for the month
                        }
                        monthlySeries.addPoint(new ValueLinePoint((float) totalMonthlyEmissions));  // Add the data point to the monthly series
                    }
                    completedQueries[0]++;  // Increment the completed queries counter
                    // If all queries are completed, update the chart with the new data
                    if (completedQueries[0] == 3) {
                        updateLineChart(seriesList, dailySeries, monthlySeries, annualSeries);
                    }
                });

        // Query to get emissions data for the current year (annual emissions)
        userRef.child("annualEmissions")
                .child("totalEmission")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Double totalEmission = task.getResult().getValue(Double.class);
                        if (totalEmission != null) {
                            annualSeries.addPoint(new ValueLinePoint(totalEmission.floatValue()));  // Add the data point to the annual series
                        }
                    }
                    completedQueries[0]++;  // Increment the completed queries counter
                    // If all queries are completed, update the chart with the new data
                    if (completedQueries[0] == 3) {
                        updateLineChart(seriesList, dailySeries, monthlySeries, annualSeries);
                    }
                });
    }

    /*
     * Updates the line chart by adding the series for daily, monthly, and annual emissions.
     * This method is called when all calls for data are done
     */
    private void updateLineChart(List<ValueLineSeries> seriesList, ValueLineSeries dailySeries,
                                 ValueLineSeries monthlySeries, ValueLineSeries annualSeries) {
        // Clear any existing series and add the new series for daily, monthly, and annual data
        seriesList.clear();
        seriesList.add(dailySeries);
        seriesList.add(monthlySeries);
        seriesList.add(annualSeries);

        // Make the chart visible and display the series
        chart.setVisibility(View.VISIBLE);
        chart.setShowIndicator(true);  // Show indicators on the chart
        chart.addSeries(dailySeries);  // Add the daily series to the chart
        chart.addSeries(monthlySeries);  // Add the monthly series to the chart
        chart.addSeries(annualSeries);  // Add the annual series to the chart
    }
}
