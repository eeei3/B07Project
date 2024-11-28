package com.example.b07project;

import android.graphics.Color;
import android.util.Log;
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

public class LineChart {
    private ValueLineChart chart;
    private FirebaseUser user;
    private DatabaseReference userRef;

    public LineChart(ValueLineChart chart) {
        this.chart = chart;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();  // Get the current authenticated user
        String userId = user != null ? user.getUid() : "";
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    public void updateLineChart() {
        // Create separate lists to hold data points for daily, monthly, and annual emissions
        List<ValueLineSeries> seriesList = new ArrayList<>();

        // Create the ValueLineSeries for each data type
        ValueLineSeries dailySeries = new ValueLineSeries();
        dailySeries.setColor(Color.RED);
        ValueLineSeries monthlySeries = new ValueLineSeries();
        monthlySeries.setColor(Color.GREEN);
        ValueLineSeries annualSeries = new ValueLineSeries();
        annualSeries.setColor(Color.BLUE);

        long currentTimeMillis = System.currentTimeMillis();
        long startOfDay = DatesForDataBase.getStartOfDay(currentTimeMillis);
        long endOfDay = DatesForDataBase.getEndOfDay(currentTimeMillis);
        long startOfMonth = DatesForDataBase.getStartOfMonth(currentTimeMillis);
        long endOfMonth = DatesForDataBase.getEndOfMonth(currentTimeMillis);
        long startOfYear = DatesForDataBase.getStartOfYear(currentTimeMillis);
        long endOfYear = DatesForDataBase.getEndOfYear(currentTimeMillis);

        final int[] completedQueries = {0};

        // Query for daily emissions
        userRef.child("ecotracker")
                .orderByKey()
                .startAt(String.valueOf(startOfDay))
                .endAt(String.valueOf(endOfDay))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalDailyEmissions = 0;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double totalEmission = snapshot.child("totalEmission").getValue(Double.class);
                            if (totalEmission != null) totalDailyEmissions += totalEmission;
                        }
                        dailySeries.addPoint(new ValueLinePoint((float) totalDailyEmissions));  // X: startOfDay, Y: totalDailyEmissions
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(seriesList, dailySeries, monthlySeries, annualSeries);
                    }
                });

        // Query for monthly emissions
        userRef.child("ecotracker")
                .orderByKey()
                .startAt(String.valueOf(startOfMonth))
                .endAt(String.valueOf(endOfMonth))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalMonthlyEmissions = 0;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double totalEmission = snapshot.child("totalEmission").getValue(Double.class);
                            if (totalEmission != null) totalMonthlyEmissions += totalEmission;
                        }
                        monthlySeries.addPoint(new ValueLinePoint((float) totalMonthlyEmissions));  // X: startOfMonth, Y: totalMonthlyEmissions
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(seriesList, dailySeries, monthlySeries, annualSeries);
                    }
                });

        // Query for annual emissions
        userRef.child("annualEmissions")
                .child("totalEmission")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Double totalEmission = task.getResult().getValue(Double.class);
                        if (totalEmission != null) {
                            annualSeries.addPoint(new ValueLinePoint(totalEmission.floatValue()));  // X: startOfYear, Y: totalEmission
                        }
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(seriesList, dailySeries, monthlySeries, annualSeries);
                    }
                });
    }

    private void updateLineChart(List<ValueLineSeries> seriesList, ValueLineSeries dailySeries,
                                 ValueLineSeries monthlySeries, ValueLineSeries annualSeries) {
        // Add the series to the list
        seriesList.clear();
        seriesList.add(dailySeries);
        seriesList.add(monthlySeries);
        seriesList.add(annualSeries);

        // Set the series on the chart
        chart.setVisibility(View.VISIBLE);  // Make sure chart is visible after data is ready
        chart.setShowIndicator(true);
        chart.addSeries(dailySeries);
        chart.addSeries(monthlySeries);
        chart.addSeries(annualSeries);
    }
}
