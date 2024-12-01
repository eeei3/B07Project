package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;

/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {

    // UI components
    TextView comparisonText;
    TextView yourEmissionsNumber;
    TextView GlobalEmissions;
    Spinner timeSpinner;
     ValueLineChart chart;  // Changed to ValueLineChart
     FirebaseUser user;  // FirebaseUser object for current authenticated user
     TextView totalEmissionsText;
     TextView transportation;
     TextView foodConsumption;
     TextView energyUse;
     TextView shopping;
     PieChart pieChart;

    private FirebaseAuth auth;  // FirebaseAuth as instance variable for dependency injection

    // Constructor to allow dependency injection for FirebaseAuth
    public EcoGauge(FirebaseAuth auth) {
        this.auth = auth;
    }

    // Default constructor (for normal use in the app)
    public EcoGauge() {
        this.auth = FirebaseAuth.getInstance();  // Use default FirebaseAuth instance if no injection is needed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);

        initializeUI(); // Set the UI components
        setupTimeSpinners(); // Set up the timeSpinner

        // Get the selected time period from Spinner
        String time = (String) timeSpinner.getSelectedItem();
        if (time == null) {
            time = "default_time";
        }

        // Safely retrieve data from the Intent
        Intent intent = getIntent();
        String location = (intent != null) ? intent.getStringExtra("location") : "unknown location";

        // Get current date in the required format (e.g., "yyyy-MM-dd")
        String today = DatesForDataBase.formatDate(System.currentTimeMillis());

        // Update the comparison text
        ComparisonText comparisonTextObj = new ComparisonText(comparisonText, yourEmissionsNumber, GlobalEmissions);
        comparisonTextObj.updateComparisonText(location, today);

        // Update the LineChart
        String timePeriod = getIntent().getStringExtra("timePeriod");
        if (timePeriod != null) {
            LineChartDisplay lineChart = new LineChartDisplay(chart); // Pass the ValueLineChart to the LineChart class
            lineChart.updateLineChart(timePeriod); // Update the line chart
        } else {
            Log.e("PieChartUpdate", "No time period provided in the Intent");
            Toast.makeText(this, "Error: Time period is missing.", Toast.LENGTH_SHORT).show();
        }

        // Update the PieChart
        if (timePeriod != null) {
            PieChartUpdate pieChart1 = new PieChartUpdate();
            pieChart1.updateChartForTimePeriod(timePeriod);
        } else {
            Log.e("PieChartUpdate", "No time period provided in the Intent");
            Toast.makeText(this, "Error: Time period is missing.", Toast.LENGTH_SHORT).show();
        }
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
        transportation = findViewById(R.id.transportation);
        foodConsumption = findViewById(R.id.foodConsumption);
        shopping = findViewById(R.id.shopping);
        energyUse = findViewById(R.id.energyUse);
        pieChart = findViewById(R.id.piechart);
        totalEmissionsText = findViewById(R.id.totalEmissionsText);
    }

    /**
     * Configures spinners with adapters and listeners.
     */
    private void setupTimeSpinners() {
        String[] categories = getResources().getStringArray(R.array.timeValues);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        timeSpinner.setAdapter(adapter);
    }

    /**
     * Initializes firebase user
     */
    String initializeFirebaseUser() {
        FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user

        // Check if the user is authenticated, otherwise, set userId as an empty string
        String userId = (user != null) ? user.getUid() : "";
        return userId;
    }
}
