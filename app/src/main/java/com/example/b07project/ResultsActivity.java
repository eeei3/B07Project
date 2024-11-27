package com.example.b07project;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ResultsActivity extends AppCompatActivity {

    // TextViews for displaying the results
    TextView totalEmissionsTextView, transportationTextView, foodTextView, housingTextView, consumptionTextView;

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

        // Get the userID (assuming you passed it in the Intent)
        String userID = getIntent().getStringExtra("userID");

        // Firebase reference to the user's data
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userID).child("annualEmissions");

        // Fetch data from Firebase
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve values from Firebase
                    Double totalEmission = dataSnapshot.child("totalEmission").getValue(Double.class);
                    Double transportationEmission = dataSnapshot.child("transportationEmissions").getValue(Double.class);
                    Double foodEmission = dataSnapshot.child("foodEmissions").getValue(Double.class);
                    Double housingEmission = dataSnapshot.child("housingEmissions").getValue(Double.class);
                    Double consumptionEmission = dataSnapshot.child("consumptionEmissions").getValue(Double.class);

                    // Check if each value is null and assign default values if necessary
                    totalEmission = (totalEmission != null) ? totalEmission : 0.0;
                    transportationEmission = (transportationEmission != null) ? transportationEmission : 0.0;
                    foodEmission = (foodEmission != null) ? foodEmission : 0.0;
                    housingEmission = (housingEmission != null) ? housingEmission : 0.0;
                    consumptionEmission = (consumptionEmission != null) ? consumptionEmission : 0.0;

                    // Update TextViews with Firebase data
                    totalEmissionsTextView.setText(String.format("Total: %.2f tons CO₂e", totalEmission));
                    transportationTextView.setText(String.format("Transportation: %.2f tons CO₂e", transportationEmission));
                    foodTextView.setText(String.format("Food: %.2f tons CO₂e", foodEmission));
                    housingTextView.setText(String.format("Housing: %.2f tons CO₂e", housingEmission));
                    consumptionTextView.setText(String.format("Consumption: %.2f tons CO₂e", consumptionEmission));
                } else {
                    Toast.makeText(ResultsActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResultsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
