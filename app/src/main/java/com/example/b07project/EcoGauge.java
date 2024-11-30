package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.eazegraph.lib.charts.ValueLineChart;

/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {


    // UI components
    private TextView comparisonText,yourEmissionsNumber, GlobalEmissions;
    private Spinner timeSpinner;
    private ValueLineChart chart;  // Changed to ValueLineChart
    private static FirebaseUser user;  // FirebaseUser object for current authenticated user


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
        LineChartDisplay lineChart = new LineChartDisplay(chart); // Pass the ValueLineChart to the LineChart class
        lineChart.updateLineChart(); // Update the line chart

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
            timeSpinner.setAdapter(adapter);;
    }

    /**
     * intialize firebase user
     */
    String initializeFirebaseUser(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();  // Get the current authenticated user

        // Check if the user is authenticated, otherwise, set userId as empty string
        String userId;
        if (user != null) {
            userId = user.getUid();  // Get the UID of the authenticated user
        } else {
            userId = "";  // If no user is authenticated, set userId as an empty string
        }
        return userId;
    }
}
