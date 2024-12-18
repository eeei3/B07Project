package com.example.b07project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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

    /**
     * Initializes UI components and calls updateLineChart, UpdatePieChart, updateComparisonText
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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
        BottomNavigationView navi = findViewById(R.id.bottomview);
        navi.setSelectedItemId(R.id.ecogauge);

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
                updatePieChart.updateChartForTimePeriod("Daily");
                updateLineChart.updateLineChartForTimePeriod("Daily");
                customizeLegendFont(pieChart);
                customizeLabelFont(pieChart);
            }
        });


    }

    /**
     * Get User ID
     * @return user from fireBase
     */
    public static String initializeFirebaseUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getUid();
    }

    /**
     * Custom Font for PieChart
     * @param pieChart UI pieChart
     */
    private void customizeLegendFont(PieChart pieChart) {
        // Get the legend from the pie chart
        Legend legend = pieChart.getLegend();

        // Load the custom font from res/font
        Typeface customTypeface = ResourcesCompat.getFont(EcoGauge.this, R.font.garet);

        // Set the custom font to the legend
        if (customTypeface != null) {
            legend.setTypeface(customTypeface);
        }
    }

    /**
     * Custom Label for PieChart
     * @param pieChart UI PieChart
     */
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

    /**
     * Add Menu
     * @param menu The options menu in which you place your items.
     *
     * @return true is menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    /**
     * Handles what is clicked on menu
     * @param item The menu item that was selected.
     *
     * @return true if something is selected in menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ecotracker) {
            Intent intent = new Intent(EcoGauge.this, EcoTrackerHomeActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.habit) {
            // Handle ecotracker action'
            Intent intent = new Intent(EcoGauge.this, HabitsMenu.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}