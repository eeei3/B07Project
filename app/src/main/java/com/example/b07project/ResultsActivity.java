package com.example.b07project;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    // TextViews for displaying the results
    TextView totalEmissionsTextView, transportationTextView, foodTextView, housingTextView, consumptionTextView;
    TextView comparisonTextView, globalTargetComparisonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        totalEmissionsTextView = findViewById(R.id.totalEmissionsTextView);
        transportationTextView = findViewById(R.id.transportationTextView);
        foodTextView = findViewById(R.id.foodTextView);
        housingTextView = findViewById(R.id.housingTextView);
        consumptionTextView = findViewById(R.id.consumptionTextView);
        comparisonTextView = findViewById(R.id.comparisonTextView);
        globalTargetComparisonTextView = findViewById(R.id.globalTargetComparisonTextView);

        // Retrieve the total emissions passed from the SurveyActivity
        double totalEmissions = getIntent().getDoubleExtra("totalEmissions", 0)/1000;
        double transportationEmissions = getIntent().getDoubleExtra("transportationEmissions", 0);
        double foodEmissions = getIntent().getDoubleExtra("foodEmissions", 0);
        double housingEmissions = getIntent().getDoubleExtra("housingEmissions", 0);
        double consumptionEmissions = getIntent().getDoubleExtra("consumptionEmissions", 0);
        String location = getIntent().getStringExtra("location");

        // Display the total carbon footprint
        totalEmissionsTextView.setText(String.format("Total Carbon Footprint: %.2f tons of CO₂e", totalEmissions));
        transportationTextView.setText(String.format("Transportation: %.2f tons of CO₂e", transportationEmissions));
        foodTextView.setText(String.format("Food: %.2f tons of CO₂e", foodEmissions));
        housingTextView.setText(String.format("Housing: %.2f tons of CO₂e", housingEmissions));
        consumptionTextView.setText(String.format("Comsumption: %.2f tons of CO₂e", consumptionEmissions));

        // Placeholder: Assume these are the national and global target emissions
        int i = Arrays.asList(EmissionsData.countries).indexOf(location);
        double nationalAverage = EmissionsData.globalAverages[i]; // Example: National average emissions in tons
        // Calculate comparison to national average
        double nationalComparison = ((totalEmissions) / nationalAverage) * 100;
        comparisonTextView.setText(String.format("Your carbon footprint is %.2f%% of the national average.", nationalComparison));

        // Calculate comparison to global target
        double globalComparison = ((totalEmissions - 2) / 2) * 100;
        globalTargetComparisonTextView.setText(String.format("You are %.2f%% above the global target.", globalComparison));

        // If needed, also display individual breakdowns for categories like food, transportation, etc.
    }
}
