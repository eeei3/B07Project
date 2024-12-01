package com.example.b07project;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLineSeries;

/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {

    public PieChart pieChart;
    public LineChart lineChart;
    public TextView transportation, foodConsumption, shopping, totalEmissionsText;
    TextView totalEmissions,
            nationalEmissions,
            globalEmissions;
    TextView comparisonNationalText, comparisonGlobalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);

        // Initialize UI components
        pieChart = findViewById(R.id.piechart);
        lineChart = findViewById(R.id.chart);
        totalEmissionsText = findViewById(R.id.totalEmissionsText);
        totalEmissions = findViewById(R.id.yourEmissionsNumber);
        nationalEmissions = findViewById(R.id.NationalEmissions);
        globalEmissions = findViewById(R.id.GlobalEmissions);
        comparisonGlobalText = findViewById(R.id.comparisonGlobalText);
        comparisonNationalText = findViewById(R.id.comparisonNationalText);

        String[] categories = getResources().getStringArray(R.array.timeValues);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        Spinner timeSpinner = findViewById(R.id.timeSpinner);
        timeSpinner.setAdapter(adapter);

        // Initialize GaugeReader and pass UI elements
        UpdateLineChart updateLineChart = new UpdateLineChart();
        UpdatePieChart updatePieChart = new UpdatePieChart();
        ComparisonText updateComparisonText = new ComparisonText(comparisonGlobalText, comparisonNationalText, totalEmissions, globalEmissions, nationalEmissions);
        updatePieChart.pieChart = pieChart;
        updateLineChart.lineChart = lineChart;
        updatePieChart.transportation = transportation;
        updatePieChart.foodConsumption = foodConsumption;
        updatePieChart.shopping = shopping;
        updatePieChart.totalEmissionsText = totalEmissionsText;
        updateLineChart.totalEmissionsText = totalEmissionsText;

        updateComparisonText.updateComparisonText();
        // Example: Update chart for daily data
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTimePeriod = (String) parent.getItemAtPosition(position);

                // Update the pie chart based on the selected time period
                updatePieChart.updateChartForTimePeriod(selectedTimePeriod);
                updateLineChart.updateLineChartForTimePeriod(selectedTimePeriod);
                customizeLegendFont(pieChart);
                customizeLabelFont(pieChart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when no item is selected (optional)
            }
        });


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

    private void customizeLabelFont(PieChart pieChart) {
        // Load the custom font from res/font
        Typeface customTypeface = ResourcesCompat.getFont(this, R.font.garet);

        // Apply the custom font to the entry labels
        if (customTypeface != null) {
            pieChart.setEntryLabelTypeface(customTypeface);
        }

        // Optional: Customize label text size and color
        pieChart.setEntryLabelTextSize(8f); // Adjust text size
        pieChart.setEntryLabelColor(Color.WHITE); // Adjust text color
    }

}