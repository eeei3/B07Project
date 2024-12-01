package com.example.b07project;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {

    public PieChart pieChart;
    public TextView transportation, foodConsumption, shopping, totalEmissionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);

        // Initialize UI components
        pieChart = findViewById(R.id.piechart);
        transportation = findViewById(R.id.transportation);
        foodConsumption = findViewById(R.id.foodConsumption);
        shopping = findViewById(R.id.shopping);
        totalEmissionsText = findViewById(R.id.totalEmissionsText);

        // Initialize GaugeReader and pass UI elements
        GaugeReader gaugeReader = new GaugeReader();
        gaugeReader.pieChart = pieChart;
        gaugeReader.transportation = transportation;
        gaugeReader.foodConsumption = foodConsumption;
        gaugeReader.shopping = shopping;
        gaugeReader.totalEmissionsText = totalEmissionsText;

        // Example: Update chart for daily data
        gaugeReader.updateChartForTimePeriod("Monthly");
    }

    public static String initializeFirebaseUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return "USER_ID";  // Return the actual user ID from Firebase
        } else {
            return "USER_ID";  // Fallback if no user is logged in
        }
    }
}