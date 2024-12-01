package com.example.b07project;


import android.util.Log;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class GaugeReader extends EcoGauge {

    private PieChart pieChart;

    public GaugeReader(PieChart pieChart) {
        this.pieChart = pieChart;
    }

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

    private void updateForDaily() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();
        FirebaseHelper firebaseHelper = new FirebaseHelper(userId);

        String today = getCurrentDate();
        firebaseHelper.getDataForDate(today, new FirebaseDataCallback() {
            @Override
            public void onDataReceived(Map<String, Double> emissionsData) {
                double totalTranspo = emissionsData.getOrDefault("totalTranspo", 0.0);
                double totalFood = emissionsData.getOrDefault("totalFood", 0.0);
                double totalShopping = emissionsData.getOrDefault("totalShopping", 0.0);
                double totalEmission = emissionsData.getOrDefault("totalEmission", 0.0);

                // Update UI with the received data
                updateUI(totalTranspo, totalFood, totalShopping, totalEmission);

                // Update pie chart
                ChartHelper.updatePieChart(pieChart, totalTranspo, totalFood, totalShopping);
            }

            @Override
            public void onDataNotFound() {
                Log.d("DailyUpdate", "No data for today");
            }

            @Override
            public void onDataFetchFailed() {
                Toast.makeText(GaugeReader.this, "Failed to fetch daily data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateForMonthly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();
        FirebaseHelper firebaseHelper = new FirebaseHelper(userId);

        String currentMonth = getCurrentMonth();
        firebaseHelper.getDataForMonth(currentMonth, new FirebaseDataCallback() {
            @Override
            public void onDataReceived(Map<String, Double> emissionsData) {
                double totalTranspo = emissionsData.getOrDefault("totalTranspo", 0.0);
                double totalFood = emissionsData.getOrDefault("totalFood", 0.0);
                double totalShopping = emissionsData.getOrDefault("totalShopping", 0.0);
                double totalEmission = emissionsData.getOrDefault("totalEmission", 0.0);

                // Update UI with the received data
                updateUI(totalTranspo, totalFood, totalShopping, totalEmission);

                // Update pie chart
                ChartHelper.updatePieChart(pieChart, totalTranspo, totalFood, totalShopping);
            }

            @Override
            public void onDataNotFound() {
                Log.d("MonthlyUpdate", "No data for this month");
            }

            @Override
            public void onDataFetchFailed() {
                Toast.makeText(GaugeReader.this, "Failed to fetch monthly data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateForYearly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();
        FirebaseHelper firebaseHelper = new FirebaseHelper(userId);

        String currentYear = getCurrentYear();
        firebaseHelper.getDataForYear(currentYear, new FirebaseDataCallback() {
            @Override
            public void onDataReceived(Map<String, Double> emissionsData) {
                double totalTranspo = emissionsData.getOrDefault("totalTranspo", 0.0);
                double totalFood = emissionsData.getOrDefault("totalFood", 0.0);
                double totalShopping = emissionsData.getOrDefault("totalShopping", 0.0);
                double totalEmission = emissionsData.getOrDefault("totalEmission", 0.0);

                // Update UI with the received data
                updateUI(totalTranspo, totalFood, totalShopping, totalEmission);

                // Update pie chart
                ChartHelper.updatePieChart(pieChart, totalTranspo, totalFood, totalShopping);
            }

            @Override
            public void onDataNotFound() {
                Log.d("YearlyUpdate", "No data for this year");
            }

            @Override
            public void onDataFetchFailed() {
                Toast.makeText(GaugeReader.this, "Failed to fetch yearly data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentDate() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(currentTimeMillis));
    }

    private String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }

    private String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    private void updateUI(double totalTranspo, double totalFood, double totalShopping, double totalEmission) {
        transportation.setText(Integer.toString((int) totalTranspo));
        foodConsumption.setText(Integer.toString((int) totalFood));
        shopping.setText(Integer.toString((int) totalShopping));
        totalEmissionsText.setText(String.format("Daily Carbon Footprint: %.2f tons of CO₂e", totalEmission));
    }
}