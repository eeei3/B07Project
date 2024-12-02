package com.example.b07project;

import android.graphics.Color;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LineChartData {
    private DatabaseReference userRef;

    // Constructor initializes the chart and sets up Firebase references based on the current user.
    public LineChartData() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    // Method to get daily data for the last 7 days
    public ValueLineSeries getDailyDataForChart() {
        ValueLineSeries dailySeries = new ValueLineSeries();
        dailySeries.setColor(Color.RED);

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Fetch emissions data for the last 7 days
        for (int i = 0; i < 7; i++) {
            long startOfDay = currentTime - (i * TimeUnit.DAYS.toMillis(1));
            String formattedDate = dateFormat.format(new Date(startOfDay));

            userRef.child("ecotracker").child(formattedDate).child("calculatedEmissions")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            double dailyEmissions = 0;
                            DataSnapshot snapshot = task.getResult();

                            // Loop through the child data and sum emissions values
                            for (DataSnapshot emissionSnapshot : snapshot.getChildren()) {
                                Double emission = emissionSnapshot.getValue(Double.class);
                                if (emission != null) {
                                    dailyEmissions += emission;
                                }
                            }
                            // Add the data point to the chart
                            dailySeries.addPoint(new ValueLinePoint(formattedDate, (float) dailyEmissions));
                        } else {
                            Log.e("DailyData", "Failed to fetch data for date: " + formattedDate);
                        }
                    });
        }

        return dailySeries;
    }

    // Method to get monthly data for the current month
    public ValueLineSeries getMonthlyDataForChart() {
        ValueLineSeries monthlySeries = new ValueLineSeries();
        monthlySeries.setColor(Color.GREEN);

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Get the first day of the current month
        long startOfMonth = DatesForDataBase.getStartOfMonth(currentTime);
        long endOfMonth = DatesForDataBase.getEndOfMonth(currentTime);

        // Loop through the days of the current month and fetch emissions data
        for (long day = startOfMonth; day <= endOfMonth; day += TimeUnit.DAYS.toMillis(1)) {
            String formattedDate = dateFormat.format(new Date(day));

            userRef.child("ecotracker").child(formattedDate).child("calculatedEmissions")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            double dailyEmissions = 0;
                            DataSnapshot snapshot = task.getResult();

                            // Loop through the emissions data for each date
                            for (DataSnapshot emissionSnapshot : snapshot.getChildren()) {
                                Double emission = emissionSnapshot.getValue(Double.class);
                                if (emission != null) {
                                    dailyEmissions += emission;
                                }
                            }
                            // Add the data point to the chart
                            monthlySeries.addPoint(new ValueLinePoint(formattedDate, (float) dailyEmissions));
                        } else {
                            Log.e("MonthlyData", "Failed to fetch data for date: " + formattedDate);
                        }
                    });
        }

        return monthlySeries;
    }

    // Method to get yearly data for the current year
    public ValueLineSeries getYearlyDataForChart() {
        ValueLineSeries annualSeries = new ValueLineSeries();
        annualSeries.setColor(Color.BLUE);

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Get the start of the year
        long startOfYear = DatesForDataBase.getStartOfYear(currentTime);
        long endOfYear = DatesForDataBase.getEndOfYear(currentTime);

        // Loop through the days of the current year and fetch emissions data
        for (long day = startOfYear; day <= endOfYear; day += TimeUnit.DAYS.toMillis(1)) {
            String formattedDate = dateFormat.format(new Date(day));

            userRef.child("ecotracker").child(formattedDate).child("calculatedEmissions")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            double dailyEmissions = 0;
                            DataSnapshot snapshot = task.getResult();

                            // Loop through the emissions data for each date
                            for (DataSnapshot emissionSnapshot : snapshot.getChildren()) {
                                Double emission = emissionSnapshot.getValue(Double.class);
                                if (emission != null) {
                                    dailyEmissions += emission;
                                }
                            }
                            // Add the data point to the chart
                            annualSeries.addPoint(new ValueLinePoint(formattedDate, (float) dailyEmissions));
                        } else {
                            Log.e("YearlyData", "Failed to fetch data for date: " + formattedDate);
                        }
                    });
        }

        return annualSeries;
    }
}