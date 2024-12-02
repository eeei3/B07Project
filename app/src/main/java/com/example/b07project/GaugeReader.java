package com.example.b07project;

import static java.security.AccessController.getContext;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GaugeReader extends EcoGauge {

    public PieChart pieChart; // Declare the PieChart view

    /**
     * Updates the chart based on the selected time period.
     *
     * @param timePeriod The selected time period.
     */
    public void updateChartForTimePeriod(String timePeriod) {
        if (timePeriod == null) return;
        switch (timePeriod) {
            case "Daily":
                updateForDaily();
                break;
            case "Monthly":
                updateForMonthly();
                break;
            case "Yearly":
                updateForYearly();
                break;
            default:
                updateForDaily();
                break;
        }
    }

    /**
     * Updates the chart for daily
     */
    public void updateForDaily() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for daily emissions
        DatabaseReference dailyRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        // Get current date in the required format (e.g., "yyyy-MM-dd")
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date(currentTimeMillis));
        Log.d("Date", "Today's Date: " + today);

        dailyRef.child(today).child("calculatedEmissions").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                // Retrieve emissions values
                                double totalTranspo = snapshot.child("totalTranspo").getValue(Double.class) != null
                                        ? snapshot.child("totalTranspo").getValue(Double.class) : 0;
                                double totalFood = snapshot.child("totalFood").getValue(Double.class) != null
                                        ? snapshot.child("totalFood").getValue(Double.class) : 0;
                                double totalShopping = snapshot.child("totalShopping").getValue(Double.class) != null
                                        ? snapshot.child("totalShopping").getValue(Double.class) : 0;
                                double totalEmission = snapshot.child("totalEmission").getValue(Double.class) != null
                                        ? snapshot.child("totalEmission").getValue(Double.class) : 0;

                                // Log values for debugging
                                Log.d("DailyUpdate", "Total Transportation: " + totalTranspo);
                                Log.d("DailyUpdate", "Total Food: " + totalFood);
                                Log.d("DailyUpdate", "Total Shopping: " + totalShopping);
                                Log.d("DailyUpdate", "Total Emission: " + totalEmission);

                                totalEmissionsText.setText(String.format("Daily Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                                // Update pie chart with MPAndroidChart
                                updatePieChart(totalTranspo, totalFood, totalShopping);
                            } else {
                                System.out.print("No data for today");
                            }
                        } else {
                            Toast.makeText(GaugeReader.this, "Failed to fetch daily data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Updates the pie chart
     *
     * @param totalTranspo The total transportation emissions.
     * @param totalFood The total food consumption emissions.
     * @param totalShopping The total shopping emissions.
     */
    public void updatePieChart(double totalTranspo, double totalFood, double totalShopping) {

        // Create a list of PieEntry objects
        String transportationColorHex = "#AD80C4"; //Purple
        String foodColorHex = "#81BAC5"; //Darker Blue
        String shoppingColorHex = "#A1C6CA"; //Lighter Blue

        int[] colors = new int[]{Color.parseColor(transportationColorHex), Color.parseColor(foodColorHex), Color.parseColor(shoppingColorHex),};
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) totalTranspo, ""));
        entries.add(new PieEntry((float) totalFood, ""));
        entries.add(new PieEntry((float) totalShopping, ""));

        // Set data for the pie chart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors); // Set pie slice colors
        PieData data = new PieData(dataSet);
        pieChart.setDrawHoleEnabled(false);
        dataSet.setValueTextSize(12f);

        // Customizing the Legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true); // Enable legend
        legend.setTextSize(12f); // Customize the text size of the legend
        legend.setFormSize(12f); // Customize the size of the square icon in the legend

        // Set custom entries for the legend
        ArrayList<LegendEntry> customLegendEntries = new ArrayList<>();
        customLegendEntries.add(new LegendEntry("Transportation", Legend.LegendForm.CIRCLE, 14f, 14f, null, Color.parseColor(transportationColorHex)));
        customLegendEntries.add(new LegendEntry("Food", Legend.LegendForm.CIRCLE, 14f, 14f, null, Color.parseColor(foodColorHex)));
        customLegendEntries.add(new LegendEntry("Shopping", Legend.LegendForm.CIRCLE, 14f, 14f, null, Color.parseColor(shoppingColorHex)));

        legend.setCustom(customLegendEntries); // Set the custom legend entries

        // Set data and other properties
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart
    }

    /**
     * Updates the chart for monthly
     */

    public void updateForMonthly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for emissions over the last 30 days
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        emissionsRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            double transportationEmissions = 0;
                            double foodEmissions = 0;
                            double shoppingEmissions = 0;

                            if (snapshot.exists()) {
                                // Get current date and calculate the date 30 days ago
                                long currentTime = System.currentTimeMillis();
                                long thirtyDaysAgo = currentTime - TimeUnit.DAYS.toMillis(30);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the same format as your stored dates

                                // Loop through the snapshot for the past data entries
                                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                    String date = dateSnapshot.getKey();
                                    if (date != null) {
                                        try {
                                            // Parse the stored date
                                            Date entryDate = dateFormat.parse(date);
                                            if (entryDate != null && entryDate.getTime() >= thirtyDaysAgo) {
                                                // Add emissions data only if the entry is within the last 30 days
                                                DataSnapshot calculatedEmissions = dateSnapshot.child("calculatedEmissions");
                                                transportationEmissions += calculatedEmissions.child("totalTranspo").getValue(Double.class) != null
                                                        ? calculatedEmissions.child("totalTranspo").getValue(Double.class) : 0;
                                                foodEmissions += calculatedEmissions.child("totalFood").getValue(Double.class) != null
                                                        ? calculatedEmissions.child("totalFood").getValue(Double.class) : 0;
                                                shoppingEmissions += calculatedEmissions.child("totalShopping").getValue(Double.class) != null
                                                        ? calculatedEmissions.child("totalShopping").getValue(Double.class) : 0;
                                            }
                                        } catch (Exception e) {
                                            Log.e("DateParsing", "Failed to parse date: " + date, e);
                                        }
                                    }
                                }
                            }

                            // If no data exists for the last 30 days, assume zero emissions
                            if (transportationEmissions == 0 && foodEmissions == 0 && shoppingEmissions == 0) {
                                Log.d("30DayUpdate", "No data available for the last 30 days. Assuming zero emissions.");
                            }

                            // Total emissions calculation
                            double totalEmission = transportationEmissions + foodEmissions + shoppingEmissions;

                            // Log values for debugging
                            Log.d("30DayUpdate", "Transportation: " + transportationEmissions);
                            Log.d("30DayUpdate", "Food: " + foodEmissions);
                            Log.d("30DayUpdate", "Shopping: " + shoppingEmissions);
                            Log.d("30DayUpdate", "Total Emission: " + totalEmission);

                            totalEmissionsText.setText(String.format("Monthly Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                            // Update Pie Chart
                            updatePieChart(transportationEmissions, foodEmissions, shoppingEmissions);
                        } else {
                            Toast.makeText(GaugeReader.this, "Failed to fetch data for the last 30 days.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Updates the chart for yearly
     */
    public void updateForYearly() {
        EcoGauge temp = new EcoGauge();
        String userId = temp.initializeFirebaseUser();

        // Firebase reference for emissions over the last 365 days
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");

        emissionsRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            double transportationEmissions = 0;
                            double foodEmissions = 0;
                            double shoppingEmissions = 0;

                            if (snapshot.exists()) {
                                // Loop through the snapshot for the past 365 days
                                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                    String date = dateSnapshot.getKey();
                                    if (date != null) {
                                        DataSnapshot calculatedEmissions = dateSnapshot.child("calculatedEmissions");
                                        transportationEmissions += calculatedEmissions.child("totalTranspo").getValue(Double.class) != null
                                                ? calculatedEmissions.child("totalTranspo").getValue(Double.class) : 0;
                                        foodEmissions += calculatedEmissions.child("totalFood").getValue(Double.class) != null
                                                ? calculatedEmissions.child("totalFood").getValue(Double.class) : 0;
                                        shoppingEmissions += calculatedEmissions.child("totalShopping").getValue(Double.class) != null
                                                ? calculatedEmissions.child("totalShopping").getValue(Double.class) : 0;
                                    }
                                }
                            }

                            // If no data exists at all, assume zero emissions
                            if (transportationEmissions == 0 && foodEmissions == 0 && shoppingEmissions == 0) {
                                Log.d("365DayUpdate", "No data available for the last 365 days. Assuming zero emissions.");
                            }

                            // Total emissions calculation
                            double totalEmission = transportationEmissions + foodEmissions + shoppingEmissions;

                            // Log values for debugging
                            Log.d("365DayUpdate", "Transportation: " + transportationEmissions);
                            Log.d("365DayUpdate", "Food: " + foodEmissions);
                            Log.d("365DayUpdate", "Shopping: " + shoppingEmissions);
                            Log.d("365DayUpdate", "Total Emission: " + totalEmission);


                            totalEmissionsText.setText(String.format("Yearly Carbon Footprint: %.2f tons of CO₂e", totalEmission));

                            // Update Pie Chart
                            updatePieChart(transportationEmissions, foodEmissions, shoppingEmissions);
                        } else {
                            Toast.makeText(GaugeReader.this, "Failed to fetch data for the last 365 days.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Fetch the total emissions of the last seven days and display them on the line chart
     */
    /**
     * Update the chart with data for the last 7 days
     */
    public void updateLastSevenDaysChart() {
        long startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7); // 7 days ago
        long endTimestamp = System.currentTimeMillis(); // Current time


        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, endTimestamp, new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray) {
                updateChartWithData(emissionsArray, dateArray);
            }
        }, 7); // Pass 7 for 7 days
    }

    /**
     * Update the chart with data for the last 30 days
     */
    public void updateLastThirtyDaysChart() {
        long startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30); // 30 days ago
        long endTimestamp = System.currentTimeMillis(); // Current time


        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, endTimestamp, new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray) {
                updateChartWithData(emissionsArray, dateArray);
            }
        }, 30); // Pass 30 for 30 days
    }


    /**
     * Update the chart with data for the last 365 days
     */
    public void updateLastThreeSixtyFiveDaysChart() {
        long startTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365); // 365 days ago
        long endTimestamp = System.currentTimeMillis(); // Current time


        String userId = initializeFirebaseUser();
        fetchEmissionsData(userId, startTimestamp, endTimestamp, new EmissionsCallback() {
            @Override
            public void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray) {
                updateChartWithData(emissionsArray, dateArray);
            }
        }, 365); // Pass 365 for 365 days
    }

    /**
     * Fetch emissions data from Firebase for the given time range
     * @param userId the user ID
     * @param startTimestamp the start time
     * @param endTimestamp the end time
     * @param callback the callback to handle the fetched data
     */
    private void fetchEmissionsData(String userId, long startTimestamp, long endTimestamp, final EmissionsCallback callback, int numberOfDays) {
        DatabaseReference emissionsRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId)
                .child("ecotracker");


        emissionsRef.orderByKey()
                .startAt(DatesForDataBase.getFormattedDate(startTimestamp))
                .endAt(DatesForDataBase.getFormattedDate(endTimestamp))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();


                            // Dynamically allocate arrays for emissions data and dates based on the number of days
                            double[] emissionsArray = new double[numberOfDays];
                            String[] dateArray = new String[numberOfDays];


                            int index = 0;
                            for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                DataSnapshot emissionsSnapshot = dateSnapshot.child("calculatedEmissions");


                                Double emission = emissionsSnapshot.child("totalEmissions").getValue(Double.class);
                                if (emission == null) {
                                    Log.w("Emissions", "Emission data is null for date: " + dateSnapshot.getKey());
                                    emissionsArray[index] = 0; // Default value
                                } else {
                                    emissionsArray[index] = emission;
                                }


                                // Store date
                                String date = dateSnapshot.getKey(); // Assuming the date is stored as the key
                                dateArray[index] = date;


                                index++;
                                if (index == numberOfDays) break; // Limit to the required number of days
                            }


                            // Pass the data to the callback
                            callback.onEmissionsDataFetched(emissionsArray, dateArray);
                        } else {
                            Log.e("Emissions", "Error fetching data: " + task.getException());
                            Toast.makeText(GaugeReader.this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Interface for the emissions callback
     */
    public interface EmissionsCallback {
        void onEmissionsDataFetched(double[] emissionsArray, String[] dateArray);
    }

    /**
     * Update the chart with the fetched emissions data
     * @param emissionsArray the emissions data
     * @param dateArray the corresponding dates
     */
    private void updateChartWithData(double[] emissionsArray, String[] dateArray) {
        // Prepare the data for the chart
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < emissionsArray.length; i++) {
            entries.add(new Entry(i, (float) emissionsArray[i])); // Adding day-wise emission data
        }

        // Set up the data set and chart
        LineDataSet dataSet = new LineDataSet(entries, "Total Emissions (kg CO2)"); // Emissions data
        LineData lineData = new LineData(dataSet);

        System.out.println("Line Data: ");
        for (int i = 0; i < lineData.getDataSetCount(); i++) {
            LineDataSet set = (LineDataSet) lineData.getDataSetByIndex(i);
            System.out.println("DataSet " + i + " (" + set.getLabel() + "): ");
            for (Entry entry : set.getValues()) {
                System.out.println("Entry: Day " + entry.getX() + ", Emission: " + entry.getY() + " kg CO2");
            }
        }
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }
}