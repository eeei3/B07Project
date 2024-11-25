package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogActivitiesActivity extends AppCompatActivity {

    // General UI elements
    TextView titleTextView;

    TextView inputDate;
    Button buttonSave;

    // Transportation Details
    CheckBox checkboxDriveVehicle, checkboxPublicTransport, checkboxCyclingWalking, checkboxFlight;
    LinearLayout vehicleDetailsLayout, publicTransportLayout, cyclingWalkingLayout, flightLayout;
    EditText inputDistanceDriving, inputTimeSpent, inputDistanceWalking, inputNumFlights;
    Spinner spinnerVehicleType, spinnerTransportType;

    // Food Consumption Activities
    CheckBox checkboxMeal;
    LinearLayout mealLayout;
    EditText inputServings;
    Spinner spinnerMealType;

    // Consumption and Shopping Activities
    CheckBox checkboxClothes, checkboxElectronics, checkboxOtherPurchases;
    LinearLayout clothesLayout, electronicsLayout, otherPurchasesLayout;
    EditText inputNumClothes, inputNumDevices, inputNumOtherPurchases;
    Spinner spinnerDeviceType, spinnerPurchaseType;

    // Energy Bills Section
    CheckBox checkboxEnergyBills;
    LinearLayout energyBillsLayout;
    Spinner spinnerBillType;
    EditText inputBillAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activities_page);

        // Initialize General UI elements
        titleTextView = findViewById(R.id.title_text_view);
        inputDate = findViewById(R.id.input_date);
        buttonSave = findViewById(R.id.button_save);

        // Initialize Transportation Details UI elements
        checkboxDriveVehicle = findViewById(R.id.checkbox_drive_vehicle);
        checkboxPublicTransport = findViewById(R.id.checkbox_public_transport);
        checkboxCyclingWalking = findViewById(R.id.checkbox_cycling_walking);
        checkboxFlight = findViewById(R.id.checkbox_flight);

        vehicleDetailsLayout = findViewById(R.id.vehicle_details_layout);
        publicTransportLayout = findViewById(R.id.public_transport_layout);
        cyclingWalkingLayout = findViewById(R.id.cycling_walking_layout);
        flightLayout = findViewById(R.id.flight_layout);

        inputDistanceDriving = findViewById(R.id.input_distance_driving);
        inputTimeSpent = findViewById(R.id.input_time_spent);
        inputDistanceWalking = findViewById(R.id.input_distance_walking);
        inputNumFlights = findViewById(R.id.input_num_flights);
        spinnerVehicleType = findViewById(R.id.spinner_vehicle_type);
        spinnerTransportType = findViewById(R.id.spinner_transport_type);

        // Initialize Food Consumption Activities UI elements
        checkboxMeal = findViewById(R.id.checkbox_meal);
        mealLayout = findViewById(R.id.meal_layout);
        inputServings = findViewById(R.id.input_servings);
        spinnerMealType = findViewById(R.id.spinner_meal_type);

        // Initialize Consumption and Shopping Activities UI elements
        checkboxClothes = findViewById(R.id.checkbox_clothes);
        checkboxElectronics = findViewById(R.id.checkbox_electronics);
        checkboxOtherPurchases = findViewById(R.id.checkbox_other_purchases);

        clothesLayout = findViewById(R.id.clothes_layout);
        electronicsLayout = findViewById(R.id.electronics_layout);
        otherPurchasesLayout = findViewById(R.id.other_purchases_layout);

        inputNumClothes = findViewById(R.id.input_num_clothes);
        inputNumDevices = findViewById(R.id.input_num_devices);
        inputNumOtherPurchases = findViewById(R.id.input_num_other_purchases);

        spinnerDeviceType = findViewById(R.id.spinner_device_type);
        spinnerPurchaseType = findViewById(R.id.spinner_purchase_type);

        // Initialize Energy Bills Section UI elements
        checkboxEnergyBills = findViewById(R.id.checkbox_energy_bills);
        energyBillsLayout = findViewById(R.id.energy_bills_layout);
        spinnerBillType = findViewById(R.id.spinner_bill_type);
        inputBillAmount = findViewById(R.id.input_bill_amount);
        final double[] totalEmission = new double[1];

        // Set OnCheckedChangeListener for each checkbox to toggle visibility of the corresponding layout
        checkboxDriveVehicle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                vehicleDetailsLayout.setVisibility(View.VISIBLE);
                if(inputDistanceDriving.getText() != null) {
                    String inputDistanceText = inputDistanceDriving.getText().toString();
                    double inputDistance = Double.parseDouble(inputDistanceText);
                    if (spinnerVehicleType == null) {
                        totalEmission[0] = 0.18 * inputDistance;
                    }
                    else if(spinnerVehicleType.getSelectedItem().toString() == "Gasoline") {
                        totalEmission[0] = 0.24 * inputDistance;
                    }
                    else if(spinnerVehicleType.getSelectedItem().toString() == "Diesel") {
                        totalEmission[0] = 0.27 * inputDistance;
                    }
                    else if(spinnerVehicleType.getSelectedItem().toString() == "Electric") {
                        totalEmission[0] = 0.05 * inputDistance;
                    }
                    else if(spinnerVehicleType.getSelectedItem().toString() == "Hybrid") {
                        totalEmission[0] = 0.16 * inputDistance;
                    }
                }
                else if (inputDistanceDriving.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid distance", Toast.LENGTH_SHORT).show();
                }
            } else {
                vehicleDetailsLayout.setVisibility(View.GONE);
            }
        });

        checkboxPublicTransport.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                publicTransportLayout.setVisibility(View.VISIBLE);
            } else {
                publicTransportLayout.setVisibility(View.GONE);
            }
        });

        checkboxCyclingWalking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cyclingWalkingLayout.setVisibility(View.VISIBLE);
            } else {
                cyclingWalkingLayout.setVisibility(View.GONE);
            }
        });

        checkboxFlight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                flightLayout.setVisibility(View.VISIBLE);
            } else {
                flightLayout.setVisibility(View.GONE);
            }
        });

        checkboxMeal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mealLayout.setVisibility(View.VISIBLE);
            } else {
                mealLayout.setVisibility(View.GONE);
            }
        });

        checkboxClothes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                clothesLayout.setVisibility(View.VISIBLE);
            } else {
                clothesLayout.setVisibility(View.GONE);
            }
        });

        checkboxElectronics.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                electronicsLayout.setVisibility(View.VISIBLE);
            } else {
                electronicsLayout.setVisibility(View.GONE);
            }
        });

        checkboxOtherPurchases.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                otherPurchasesLayout.setVisibility(View.VISIBLE);
            } else {
                otherPurchasesLayout.setVisibility(View.GONE);
            }
        });

        checkboxEnergyBills.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                energyBillsLayout.setVisibility(View.VISIBLE);
            } else {
                energyBillsLayout.setVisibility(View.GONE);
            }
        });

        // Handle the save button click -> need to connect to the firebase
        /*buttonSave.setOnClickListener(v -> {

        });*/
    }
}
