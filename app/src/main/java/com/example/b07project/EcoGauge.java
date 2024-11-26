package com.example.b07project;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Activity to display EcoGauge metrics, including emissions data via PieChart and LineChart.
 */
public class EcoGauge extends AppCompatActivity {


    // UI components
    private PieChart pieChart;
    private TextView totalEmissionsText, comparisonText, shopping, transportation,
            foodConsumption, energyUse, yourEmissions, yourEmissionsNumber, GlobalEmissions;
    private Spinner timeSpinner, countrySpinner;
    LineChartView chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);


        initializeUI();
        setupTimeSpinners();
    }


    /**
     * Initializes UI components.
     */
    private void initializeUI() {
        pieChart = findViewById(R.id.piechart);
        totalEmissionsText = findViewById(R.id.totalEmissionsText);
        comparisonText = findViewById(R.id.comparisonText);
        timeSpinner = findViewById(R.id.timeSpinner);
        countrySpinner = findViewById(R.id.countrySpinner);
        shopping = findViewById(R.id.shopping);
        transportation = findViewById(R.id.transportation);
        foodConsumption = findViewById(R.id.foodConsumption);
        energyUse = findViewById(R.id.energyUse);
        yourEmissions = findViewById(R.id.yourEmissions);
        yourEmissionsNumber = findViewById(R.id.yourEmissionsNumber);
        GlobalEmissions = findViewById(R.id.GlobalEmissions);
        chart = findViewById(R.id.chart);
    }


    /**
     * Configures spinners with adapters and listeners.
     */
    private void setupTimeSpinners() {
            String[] categories = getResources().getStringArray(R.array.timeValues);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
            Spinner locationSpinner = findViewById(R.id.timeSpinner);
            timeSpinner.setAdapter(adapter);
    }

    /**
     * Updates the chart based on the selected time period.
     *
     * @param timePeriod The selected time period.
     */
    private void updateChartForTimePeriod(String timePeriod) {
        switch (timePeriod) {
            case "Daily":
                // Update chart with daily data
                break;
            case "Monthly":
                // Update chart with monthly data
                break;
            case "Yearly":
                // Retrieve the total emissions passed from the SurveyActivity
                double totalEmissions = getIntent().getDoubleExtra("totalEmissions", 0)/1000;
                double transportationEmissions = getIntent().getDoubleExtra("transportationEmissions", 0);
                double foodEmissions = getIntent().getDoubleExtra("foodEmissions", 0);
                double housingEmissions = getIntent().getDoubleExtra("housingEmissions", 0);
                double consumptionEmissions = getIntent().getDoubleExtra("consumptionEmissions", 0);

                //set the text
                totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmissions));

                // Set the percentage of emissions to pie chart
                transportation.setText(Integer.toString((int) transportationEmissions));
                foodConsumption.setText(Integer.toString((int) foodEmissions));
                shopping.setText(Integer.toString((int) housingEmissions));
                energyUse.setText(Integer.toString((int) consumptionEmissions));


                // Set the data and color to the pie chart
                pieChart.addPieSlice(new PieModel("Transportation",Integer.parseInt(transportation.getText().toString()),
                        Color.parseColor("#FFA726")));


                pieChart.addPieSlice(new PieModel("Food Consumption",Integer.parseInt(foodConsumption.getText().toString()),
                        Color.parseColor("#66BB6A")));


                pieChart.addPieSlice(new PieModel("Shopping",Integer.parseInt(shopping.getText().toString()),
                        Color.parseColor("#EF5350")));


                pieChart.addPieSlice(new PieModel("Energy Use",Integer.parseInt(energyUse.getText().toString()),
                        Color.parseColor("#29B6F6")));
                break;
            default:
                //
                break;
        }
    }


    /**
     * Updates the comparison TextView.
     *
     * @param comparison The comparison string.
     */
    private void updateComparisonText(String comparison) {
        String location = getIntent().getStringExtra("location");

        // Placeholder: Assume these are the national and global target emissions
        int i = Arrays.asList(EmissionsData.countries).indexOf(location);
        double nationalAverage = EmissionsData.globalAverages[i]; // Example: National average emissions in tons
        // Calculate comparison to national average
        double totalEmissions = getIntent().getDoubleExtra("totalEmissions", 0)/1000;
        double nationalComparison = ((totalEmissions) / nationalAverage) * 100;
        yourEmissionsNumber.setText(String.format("%.2f", nationalAverage));
        GlobalEmissions.setText(String.format("%.2f", nationalAverage));
    }

    /**
     * Updates the LineChart to show emissions trend (daily, weekly, monthly)
     */
    private void updateLineChart() {
        double totalEmissions = getIntent().getDoubleExtra("totalEmissions", 0)/1000;
        //do same for month and day

        //array for points
        List<PointValue> values = new ArrayList<>();
        values.add(new PointValue(0, (float)totalEmissions));
    }
}
