package com.example.b07project;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
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
        totalEmissionsText = findViewById(R.id.totalEmissionsText);

        String[] categories = getResources().getStringArray(R.array.timeValues);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        Spinner timeSpinner = findViewById(R.id.timeSpinner);
        timeSpinner.setAdapter(adapter);

        // Initialize GaugeReader and pass UI elements
        GaugeReader gaugeReader = new GaugeReader();
        gaugeReader.pieChart = pieChart;
        gaugeReader.transportation = transportation;
        gaugeReader.foodConsumption = foodConsumption;
        gaugeReader.shopping = shopping;
        gaugeReader.totalEmissionsText = totalEmissionsText;

        // Example: Update chart for daily data
        gaugeReader.updateChartForTimePeriod("Monthly");

        customizeLegendFont(pieChart);
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

    private void customizeLegendFont(PieChart pieChart) {
        // Get the legend from the pie chart
        Legend legend = pieChart.getLegend();

        // Load the custom font from res/font
        Typeface customTypeface = ResourcesCompat.getFont(this, R.font.garet);

        // Set the custom font to the legend
        if (customTypeface != null) {
            legend.setTypeface(customTypeface);
        }
    }
}