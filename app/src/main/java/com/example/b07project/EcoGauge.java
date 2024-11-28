package com.example.b07project;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.android.gms.tasks.Task;

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
                // Retrieve the total emissions passed from the EcoTracker
                double totalTranspo = getIntent().getDoubleExtra("totalTranspo", 0)/1000;
                double totalFood = getIntent().getDoubleExtra("totalFood", 0);
                double totalShopping = getIntent().getDoubleExtra("totalShopping", 0);
                double totalEmission1 = getIntent().getDoubleExtra("totalEmission", 0);

                totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission1));

                // Set the percentage of emissions to pie chart
                transportation.setText(Integer.toString((int) totalTranspo));
                foodConsumption.setText(Integer.toString((int) totalFood));
                shopping.setText(Integer.toString((int) totalShopping));


                // Set the data and color to the pie chart
                pieChart.addPieSlice(new PieModel("Transportation",Integer.parseInt(transportation.getText().toString()),
                        Color.parseColor("#FFA726")));


                pieChart.addPieSlice(new PieModel("Food Consumption",Integer.parseInt(foodConsumption.getText().toString()),
                        Color.parseColor("#66BB6A")));


                pieChart.addPieSlice(new PieModel("Shopping",Integer.parseInt(shopping.getText().toString()),
                        Color.parseColor("#EF5350")));


                break;
            case "Monthly":
                double totalEmission2 = getIntent().getDoubleExtra("totalEmission", 0); // This could be for the entire month

                totalEmissionsText.setText(String.format("Monthly Carbon Footprint: %.2f tons of CO₂e", totalEmission2));

                // Initialize the Firebase database reference
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user
                String userId = user.getUid();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("emissions");

                // Get current time and calculate the start and end of the current month
                long currentTimeMillis = System.currentTimeMillis();
                long startOfMonth = DatesForDataBase.getStartOfMonth(currentTimeMillis); // Method to calculate the start of the current month
                long endOfMonth = DatesForDataBase.etEndOfMonth(currentTimeMillis); // Method to calculate the end of the current month

                // Query data from Firebase for the current month
                databaseRef.orderByKey().startAt(String.valueOf(startOfMonth)).endAt(String.valueOf(endOfMonth)).get()
                        .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    double totalTranspo = 0;
                                    double totalFood = 0;
                                    double totalShopping = 0;

                                    // Aggregate data for the entire month
                                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                        // Assuming the data structure has fields like "transportation", "food", "shopping"
                                        Double transpoEmission = snapshot.child("transportation").getValue(Double.class);
                                        Double foodEmission = snapshot.child("foodConsumption").getValue(Double.class);
                                        Double shoppingEmission = snapshot.child("shopping").getValue(Double.class);

                                        // Add the emissions to the respective totals (account for null values)
                                        if (transpoEmission != null) totalTranspo += transpoEmission;
                                        if (foodEmission != null) totalFood += foodEmission;
                                        if (shoppingEmission != null) totalShopping += shoppingEmission;
                                    }

                                    // Set the percentage of emissions to pie chart
                                    transportation.setText(Integer.toString((int)totalTranspo));
                                    foodConsumption.setText(Integer.toString((int) totalFood));
                                    shopping.setText(Integer.toString((int)totalShopping));

                                    // Set the data and colors for the pie chart
                                    pieChart.clearChart(); // Clear previous data before adding new slices

                                    // Add pie slices for each category
                                    pieChart.addPieSlice(new PieModel("Transportation", (int) totalTranspo, Color.parseColor("#FFA726")));
                                    pieChart.addPieSlice(new PieModel("Food Consumption", (int) totalFood, Color.parseColor("#66BB6A")));
                                    pieChart.addPieSlice(new PieModel("Shopping", (int) totalShopping, Color.parseColor("#EF5350")));
                                } else {
                                    // Handle the error if the Firebase query fails
                                    Toast.makeText(EcoGauge.this, "Failed to load monthly data.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case "Yearly":
                // Retrieve the total emissions passed from the SurveyActivity
                double totalEmission3 = getIntent().getDoubleExtra("totalEmissions", 0);
                double transportationEmissions = getIntent().getDoubleExtra("transportationEmissions", 0);
                double foodEmissions = getIntent().getDoubleExtra("foodEmissions", 0);
                double housingEmissions = getIntent().getDoubleExtra("housingEmissions", 0);
                double consumptionEmissions = getIntent().getDoubleExtra("consumptionEmissions", 0);

                //set the text
                totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission3));

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
        double totalEmissions = getIntent().getDoubleExtra("totalEmissions", 0);
        double nationalComparison = ((totalEmissions) / nationalAverage) * 100;
        yourEmissionsNumber.setText(String.format("%.2f", nationalAverage));
        GlobalEmissions.setText(String.format("%.2f", nationalAverage));
    }

    /**
     * Updates the LineChart to show emissions trend (daily, weekly, monthly)
     */
    private void updateLineChart() {
        // Create separate lists to hold data points for daily, monthly, and annual emissions
        List<PointValue> dailyValues = new ArrayList<>();
        List<PointValue> monthlyValues = new ArrayList<>();
        List<PointValue> annualValues = new ArrayList<>();

        // Initialize the Firebase database reference
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user
        String userId = user.getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("emissions");

        // Get current time and calculate the start and end of the current day, month, and year
        long currentTimeMillis = System.currentTimeMillis();
        long startOfDay = DatesForDataBase.getStartOfDay(currentTimeMillis);  // Method to calculate the start of the current day
        long endOfDay = DatesForDataBase.getEndOfDay(currentTimeMillis);      // Method to calculate the end of the current day
        long startOfMonth = DatesForDataBase.getStartOfMonth(currentTimeMillis); // Start of the current month
        long endOfMonth = DatesForDataBase.getEndOfMonth(currentTimeMillis); // End of the current month
        long startOfYear = DatesForDataBase.getStartOfYear(currentTimeMillis); // Start of the current year
        long endOfYear = DatesForDataBase.getEndOfYear(currentTimeMillis);   // End of the current year

        // Variable to track completion of all queries
        final int[] completedQueries = {0};

        // Query data from Firebase for the current day
        databaseRef.orderByKey().startAt(String.valueOf(startOfDay)).endAt(String.valueOf(endOfDay)).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalDailyEmissions = 0;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double totalEmission = snapshot.child("totalEmission").getValue(Double.class);
                            if (totalEmission != null) totalDailyEmissions += totalEmission;
                        }
                        // Add the daily emissions as a single point
                        dailyValues.add(new PointValue(0, (float) totalDailyEmissions));
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(dailyValues, monthlyValues, annualValues); // Call update when all data is collected
                    }
                });

        // Query data from Firebase for the current month
        databaseRef.orderByKey().startAt(String.valueOf(startOfMonth)).endAt(String.valueOf(endOfMonth)).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalMonthlyEmissions = 0;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double totalEmission = snapshot.child("totalEmission").getValue(Double.class);
                            if (totalEmission != null) totalMonthlyEmissions += totalEmission;
                        }
                        // Add the monthly emissions as a single point
                        monthlyValues.add(new PointValue(1, (float) totalMonthlyEmissions));
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(dailyValues, monthlyValues, annualValues); // Call update when all data is collected
                    }
                });

        // Query data from Firebase for the current year
        databaseRef.orderByKey().startAt(String.valueOf(startOfYear)).endAt(String.valueOf(endOfYear)).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalAnnualEmissions = 0;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Double transpoEmission = snapshot.child("transpoEmission").getValue(Double.class);
                            Double foodEmission = snapshot.child("foodEmission").getValue(Double.class);
                            Double shoppingEmission = snapshot.child("shoppingEmission").getValue(Double.class);

                            if (transpoEmission != null) totalAnnualEmissions += transpoEmission;
                            if (foodEmission != null) totalAnnualEmissions += foodEmission;
                            if (shoppingEmission != null) totalAnnualEmissions += shoppingEmission;
                        }
                        // Add the annual emissions as a single point
                        annualValues.add(new PointValue(2, (float) totalAnnualEmissions));
                    }
                    completedQueries[0]++;
                    if (completedQueries[0] == 3) {
                        updateLineChart(dailyValues, monthlyValues, annualValues); // Call update when all data is collected
                    }
                });
    }

    private void updateLineChart(List<PointValue> dailyValues, List<PointValue> monthlyValues, List<PointValue> annualValues) {
        // Create lines for each data series (daily, monthly, annual)
        Line dailyLine = new Line(dailyValues).setColor(Color.RED).setCubic(true); // Red line for daily emissions
        Line monthlyLine = new Line(monthlyValues).setColor(Color.GREEN).setCubic(true); // Green line for monthly emissions
        Line annualLine = new Line(annualValues).setColor(Color.BLUE).setCubic(true); // Blue line for annual emissions

        // Add the lines to a list
        List<Line> lines = new ArrayList<>();
        lines.add(dailyLine);
        lines.add(monthlyLine);
        lines.add(annualLine);

        // Create LineChartData and set it on the chart
        LineChartData data = new LineChartData();
        data.setLines(lines);
        chart.setLineChartData(data);
    }

}
