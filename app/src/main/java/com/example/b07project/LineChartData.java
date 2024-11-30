package com.example.b07project;

import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;


/*
 * LineChart class works with Firebase to retrieve emissions data for the day, month, and year
 * and updates a ValueLineChart accordingly to visualize emissions data.
 */
public class LineChartData {
    private DatabaseReference userRef;

    /*
     * Constructor initializes the chart and sets up Firebase references based on the current user.
     */
    public LineChartData() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    public ValueLineSeries getDailyDataForChart() {
        ValueLineSeries dailySeries = new ValueLineSeries();
        dailySeries.setColor(Color.RED);

        long currentTime = System.currentTimeMillis();

        // Daily Emissions: Last 7 Days
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
                                Double emission = snapshot.child("calculated emissions/totalEmission").getValue(Double.class);
                                if (emission != null) dailyEmissions += emission;
                            }
                            dailySeries.addPoint(new ValueLinePoint(DatesForDataBase.formatDate(startOfDay), (float) dailyEmissions));
                        }
                    });
        }
        return dailySeries;
    }

    public ValueLineSeries getMonthlyDataForChart() {
        ValueLineSeries monthlySeries = new ValueLineSeries();
        monthlySeries.setColor(Color.GREEN);
        long currentTime = System.currentTimeMillis();

        // Monthly Emissions: From Login Date to End of Month
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
                                Double emission = snapshot.child("calculated emissions/totalEmission").getValue(Double.class);
                                if (emission != null) dailyEmissions += emission;
                            }
                            monthlySeries.addPoint(new ValueLinePoint(DatesForDataBase.formatDate(startOfDay), (float) dailyEmissions));
                        }
                    });
        }
        return monthlySeries;
    }

    public ValueLineSeries getYearlyDataForChart() {
        ValueLineSeries annualSeries = new ValueLineSeries();
        annualSeries.setColor(Color.BLUE);

        long currentTime = System.currentTimeMillis();

        // Get the start of the year and today's date
        long startOfYear = DatesForDataBase.getStartOfYear(currentTime);
        long currentDay = currentTime; // Use the current time for today's date

        // Iterate from January 1st of this year to the current day
        for (long day = startOfYear; day <= currentDay; day += 24 * 60 * 60 * 1000) {
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

                            // Sum the emissions for the current day, if available
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                Double emission = snapshot.child("calculated emissions/totalEmission").getValue(Double.class);
                                if (emission != null) {
                                    dailyEmissions += emission;
                                }
                            }
                            // Get the formatted date for the x-axis label
                            String dayLabel = DatesForDataBase.formatDate(startOfDay);

                            // Add the daily emissions value to the series
                            annualSeries.addPoint(new ValueLinePoint(dayLabel, (float) dailyEmissions));
                        }
                    });
        }

        return annualSeries;
    }

}

