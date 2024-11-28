package com.example.b07project;
import android.graphics.Color;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LineChart {
    private LineChartView chart;
    private FirebaseUser user;
    private DatabaseReference userRef;

    public LineChart(LineChartView chart) {
        this.chart = chart;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();  // Get the current authenticated user
        String userId = user.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    public void updateLineChart() {
        // Create separate lists to hold data points for daily, monthly, and annual emissions
        List<PointValue> dailyValues = new ArrayList<>();
        List<PointValue> monthlyValues = new ArrayList<>();
        List<PointValue> annualValues = new ArrayList<>();

        // Get current time and calculate the start and end of the current day, month, and year
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
                        dailyValues.add(new PointValue(0, (float) totalDailyEmissions));
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(dailyValues, monthlyValues, annualValues);
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
                        monthlyValues.add(new PointValue(1, (float) totalMonthlyEmissions));
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(dailyValues, monthlyValues, annualValues);
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
                            annualValues.add(new PointValue(2, totalEmission.floatValue()));
                        }
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(dailyValues, monthlyValues, annualValues);
                    }
                });
    }

    private void updateLineChart(List<PointValue> dailyValues, List<PointValue> monthlyValues, List<PointValue> annualValues) {
        // Create lines for each data series (daily, monthly, annual)
        Line dailyLine = new Line(dailyValues).setColor(Color.RED).setCubic(true);
        Line monthlyLine = new Line(monthlyValues).setColor(Color.GREEN).setCubic(true);
        Line annualLine = new Line(annualValues).setColor(Color.BLUE).setCubic(true);

        // Add the lines to a list
        List<Line> lines = new ArrayList<>();
        lines.add(dailyLine);
        lines.add(monthlyLine);
        lines.add(annualLine);

        // Create LineChartData and set it on the chart
        LineChartData data = new LineChartData();
        data.setLines(lines);
        chart.setLineChartData(data);
    }
}
