package com.example.b07project;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.List;


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
    private TextView totalEmissionsText, shopping, transportation, comparisonText,
            foodConsumption, energyUse, yourEmissionsNumber, GlobalEmissions;
    private Spinner timeSpinner;
    LineChartView chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eco_gauge);


        initializeUI(); //set the vas
        setupTimeSpinners(); //set the timeSpinner

        // get the timeSpinner val
        Object selectedItem = timeSpinner.getSelectedItem();
        String time = (String) selectedItem;

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
        LineChart lineChart = new LineChart(chart); // Pass the chart from EcoGauge to LineChart
        lineChart.updateLineChart(); // Now calling it on the instance

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
