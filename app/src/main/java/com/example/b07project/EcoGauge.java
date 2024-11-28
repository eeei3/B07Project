package com.example.b07project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;

/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {


    // UI components
    private PieChart pieChart;
    private TextView totalEmissionsText, shopping, transportation, comparisonText,
            foodConsumption, energyUse, yourEmissionsNumber, GlobalEmissions;
    private Spinner timeSpinner;
    private ValueLineChart chart;  // Changed to ValueLineChart


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);


        initializeUI(); //set the vas
        setupTimeSpinners(); //set the timeSpinner

        // get the timeSpinner val
        String time = (String) timeSpinner.getSelectedItem();

        // Get updated chart using PieChartUpdate class and text
        PieChartUpdate chartUpdate = new PieChartUpdate(totalEmissionsText, transportation, foodConsumption, shopping, energyUse, pieChart);
        chartUpdate.updateChartForTimePeriod(time);
        chartUpdate.updateTotalEmissionsText(time);

        //Get updated ComparisonText
        String location = getIntent().getStringExtra("location");
        double totalEmissions = getIntent().getDoubleExtra("totalEmissions", 0);

        ComparisonText comparisonTextObj = new ComparisonText(comparisonText, yourEmissionsNumber, GlobalEmissions);
        comparisonTextObj.updateComparisonText(location, totalEmissions);

        //Get updated Linechart
        LineChart lineChart = new LineChart(chart); // Pass the ValueLineChart to the LineChart class
        lineChart.updateLineChart(); // Update the line chart

    }

    /**
     * Initializes UI components.
     */
    private void initializeUI() {
        pieChart = findViewById(R.id.piechart);
        totalEmissionsText = findViewById(R.id.totalEmissionsText);
        timeSpinner = findViewById(R.id.timeSpinner);
        shopping = findViewById(R.id.shopping);
        transportation = findViewById(R.id.transportation);
        foodConsumption = findViewById(R.id.foodConsumption);
        energyUse = findViewById(R.id.energyUse);
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
