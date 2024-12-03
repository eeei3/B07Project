package com.example.b07project;
import android.content.Intent;import android.graphics.Color;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;


/**
 * ResultsActivity class containing methods relating to the results screen from the survey
 */
public class ResultsActivity extends AppCompatActivity {

    // TextViews for displaying the results
    TextView totalEmissionsTextView,
            transportationTextView,
            foodTextView,
            housingTextView,
            consumptionTextView;
    TextView comparisonTextView, globalTargetComparisonTextView;

    public Button btnSubmit;

    /**
     * onCreate - Method run when ResultsActivity is created
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialize the TextViews
        totalEmissionsTextView = findViewById(R.id.totalEmissionsTextView);
        transportationTextView = findViewById(R.id.transportationTextView);
        foodTextView = findViewById(R.id.foodTextView);
        housingTextView = findViewById(R.id.housingTextView);
        consumptionTextView = findViewById(R.id.consumptionTextView);
        comparisonTextView = findViewById(R.id.comparisonTextView);
        globalTargetComparisonTextView = findViewById(R.id.globalTargetComparisonTextView);
        btnSubmit = findViewById(R.id.submitButton);

        // Get the userID (assuming you passed it in the Intent)
        String userID = getIntent().getStringExtra("userID");

        // Firebase reference to the user's data (including emissions and location)
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userID);

        // Fetch data from Firebase
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the location from the user node
                    String location = dataSnapshot.child("location").getValue(String.class);

                    // Retrieve emission values from the 'annualEmissions' child node
                    Double totalEmission = dataSnapshot.child("annualEmissions").child("totalEmission").getValue(Double.class);
                    Double transportationEmission = dataSnapshot.child("annualEmissions").child("transportationEmissions").getValue(Double.class);
                    Double foodEmission = dataSnapshot.child("annualEmissions").child("foodEmissions").getValue(Double.class);
                    Double housingEmission = dataSnapshot.child("annualEmissions").child("housingEmissions").getValue(Double.class);
                    Double consumptionEmission = dataSnapshot.child("annualEmissions").child("consumptionEmissions").getValue(Double.class);

                    // Log to ensure the location value is being retrieved correctly
                    Log.d("FirebaseData", "Retrieved location: " + location);

                    // Check if each emission value is null and assign default values if necessary
                    totalEmission = (totalEmission != null) ? totalEmission : 0.0;
                    transportationEmission = (transportationEmission != null) ? transportationEmission : 0.0;
                    foodEmission = (foodEmission != null) ? foodEmission : 0.0;
                    housingEmission = (housingEmission != null) ? housingEmission : 0.0;
                    consumptionEmission = (consumptionEmission != null) ? consumptionEmission : 0.0;

                    // Update TextViews with Firebase data
                    totalEmissionsTextView.setText(String.format("Total: %.2f tons CO₂e", totalEmission / 1000));
                    transportationTextView.setText(String.format("Transportation: %.2f tons CO₂e", transportationEmission / 1000));
                    foodTextView.setText(String.format("Food: %.2f tons CO₂e", foodEmission / 1000));
                    housingTextView.setText(String.format("Housing: %.2f tons CO₂e", housingEmission / 1000));
                    consumptionTextView.setText(String.format("Consumption: %.2f tons CO₂e", consumptionEmission / 1000));
                    double globalComparison = (((totalEmission / 1000) - 2) / 2) * 100;
                    globalTargetComparisonTextView.setText(String.format("You are %.2f%% above the global target.", globalComparison));

                    // Placeholder: Assume these are the national and global target emissions
                    if (location != null) {
                        int i = Arrays.asList(EmissionsData.countries).indexOf(location);
                        if (i != -1) {
                            double nationalAverage = EmissionsData.globalAverages[i]; // Example: National average emissions in tons
                            // Calculate comparison to national average
                            double nationalComparison = ((totalEmission / 1000) / nationalAverage) * 100;
                            comparisonTextView.setText(String.format("Your carbon footprint is %.2f%% of the national average.", nationalComparison));
                        } else {
                            comparisonTextView.setText("Location data is unavailable for comparison.");
                        }
                    } else {
                        comparisonTextView.setText("Location is missing or invalid.");
                    }

                } else {
                    Toast.makeText(ResultsActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseData", "DataSnapshot is empty, no user emissions data found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResultsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseData", "DatabaseError: " + error.getMessage());
            }
        });

        // If needed, also display individual breakdowns for categories like food, transportation, etc.
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(ResultsActivity.this, EcoTrackerHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * loadFragment - Method for handling the loading of a fragment
     * @param fragment - fragment to load
     */
    private void loadFragment(Fragment fragment) {
        // Use the correct FragmentManager for activities
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Optional transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // R.id.fragment_container is your container's ID
        transaction.replace(R.id.fragment_container, fragment);
        // Optional: allows fragment to be popped back
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
