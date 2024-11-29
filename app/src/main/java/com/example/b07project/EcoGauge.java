package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.ValueLineChart;

/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {


    // UI components
    private TextView comparisonText,yourEmissionsNumber, GlobalEmissions;
    private Spinner timeSpinner;
    private ValueLineChart chart;  // Changed to ValueLineChart


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);


        initializeUI(); //set the vas
        setupTimeSpinners(); //set the timeSpinner

        // Get the selected time period from Spinner
        String time = (String) timeSpinner.getSelectedItem();
        if (time == null) {
            time = "default_time";
        }

        // Safely retrieve data from the Intent
        Intent intent = getIntent();
        String location;
        if (intent == null) {
            location = "unknown location";
        } else {
            location = intent.getStringExtra("location");
        }

        double totalEmissions;
        if (intent == null) {
            totalEmissions = 0;
        } else {
            totalEmissions = intent.getDoubleExtra("totalEmissions", 0);
        }

        // Update the comparison text
        ComparisonText comparisonTextObj = new ComparisonText(comparisonText, yourEmissionsNumber, GlobalEmissions);
        comparisonTextObj.updateComparisonText(location, totalEmissions);

        // Update the LineChart
        LineChart lineChart = new LineChart(chart); // Pass the ValueLineChart to the LineChart class
        lineChart.getDataForChart(); // Update the line chart
    }

    /**
     * Initializes UI components.
     */
    private void initializeUI() {
        timeSpinner = findViewById(R.id.timeSpinner);
        yourEmissionsNumber = findViewById(R.id.yourEmissionsNumber);
        GlobalEmissions = findViewById(R.id.GlobalEmissions);
        chart = findViewById(R.id.chart);
        comparisonText = findViewById(R.id.comparisonText);
    }


    /**
     * Configures spinners with adapters and listeners.
     */
    private void setupTimeSpinners() {
            String[] categories = getResources().getStringArray(R.array.timeValues);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
            timeSpinner.setAdapter(adapter);
    }
}
