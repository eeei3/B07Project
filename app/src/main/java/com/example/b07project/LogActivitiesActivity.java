
package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogActivitiesActivity extends AppCompatActivity {

    //ui elements
    private TextView titleTextView, inputDate;
    private Button buttonSave;

    //transporation details
    private CheckBox checkboxDriveVehicle, checkboxPublicTransport, checkboxCyclingWalking, checkboxFlight;
    private LinearLayout vehicleDetailsLayout, publicTransportLayout, cyclingWalkingLayout, flightLayout;
    private EditText inputDistanceDriving, inputTimeSpent, inputDistanceWalking, inputNumFlights;
    private Spinner spinnerVehicleType, spinnerTransportType, spinnerFlightType;

    //food consumption details
    private CheckBox checkboxMeal;
    private LinearLayout mealLayout;
    private EditText inputServings;
    private Spinner spinnerMealType;

    //consumption and shopping details
    private CheckBox checkboxClothes, checkboxElectronics, checkboxOtherPurchases;
    private LinearLayout clothesLayout, electronicsLayout, otherPurchasesLayout;
    private EditText inputNumClothes, inputNumDevices, inputNumOtherPurchases;
    private Spinner spinnerDeviceType, spinnerPurchaseType;

    //energy bills section
    private CheckBox checkboxEnergyBills;
    private LinearLayout energyBillsLayout;
    private Spinner spinnerBillType;
    private EditText inputBillAmount;

    //global variables
    private String vehicleType, transportType, flightType, mealType, deviceType, purchaseType, BillType;
    private double distanceDriven, cyclingTime, walkingCyclingDistance, BillAmount;
    private int numFlights, numClothes, numDevices, numPurchases, numServings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activities_page);

        initializeUIComponents();

        setupSpinner(spinnerVehicleType, R.array.vehicle_types);
        setupSpinner(spinnerTransportType, R.array.transport_types);
        setupSpinner(spinnerFlightType, R.array.flight_types);
        setupSpinner(spinnerMealType, R.array.meal_types);
        setupSpinner(spinnerDeviceType, R.array.electronics_types);
        setupSpinner(spinnerPurchaseType, R.array.other_purchase_types);
        setupSpinner(spinnerBillType, R.array.energy_bill_types);

        setupCheckbox(checkboxDriveVehicle, vehicleDetailsLayout);
        setupCheckbox(checkboxPublicTransport, publicTransportLayout);
        setupCheckbox(checkboxCyclingWalking, cyclingWalkingLayout);
        setupCheckbox(checkboxFlight, flightLayout);
        setupCheckbox(checkboxMeal, mealLayout);
        setupCheckbox(checkboxClothes, clothesLayout);
        setupCheckbox(checkboxElectronics, electronicsLayout);
        setupCheckbox(checkboxOtherPurchases, otherPurchasesLayout);
        setupCheckbox(checkboxEnergyBills, energyBillsLayout);

        buttonSave.setOnClickListener(v -> saveData());

    }

    //initialize UI components by matching to front end IDs
    private void initializeUIComponents() {
        titleTextView = findViewById(R.id.title_text_view);
        inputDate = findViewById(R.id.input_date);
        buttonSave = findViewById(R.id.button_save);

        //transportation UI
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
        spinnerFlightType = findViewById(R.id.spinner_flight_type);

        //food UI
        checkboxMeal = findViewById(R.id.checkbox_meal);
        mealLayout = findViewById(R.id.meal_layout);
        inputServings = findViewById(R.id.input_servings);
        spinnerMealType = findViewById(R.id.spinner_meal_type);

        //consumption and shopping UI
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

        //energy Bills UI
        checkboxEnergyBills = findViewById(R.id.checkbox_energy_bills);
        energyBillsLayout = findViewById(R.id.energy_bills_layout);
        inputBillAmount = findViewById(R.id.input_bill_amount);
        spinnerBillType = findViewById(R.id.spinner_bill_type);
    }

    //spinner setup for user to choose between options
    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //checkbox set up for toggle down
    private void setupCheckbox(CheckBox checkbox, LinearLayout layout) {
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Show or hide the associated input layout based on the checkbox state
            layout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    //emission calculations
    private double calculateVehicleEmission() {
        double emission;

        if ("Gasoline".equals(vehicleType)) {
            emission = 0.24 * distanceDriven;
        } else if ("Diesel".equals(vehicleType)) {
            emission = 0.27 * distanceDriven;
        } else if ("Electric".equals(vehicleType)) {
            emission = 0.05 * distanceDriven;
        } else if ("Hybrid".equals(vehicleType)) {
            emission = 0.16 * distanceDriven;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculatePublicTransportEmission() {

        double emission = 0.0;

        // Calculate emission using if-else logic
        if (transportType.equals("Bus")) {
            emission = 0.18 * cyclingTime;
        } else if (transportType.equals("Train")) {
            emission = 0.04 * cyclingTime;
        } else if (transportType.equals("Subway")) {
            emission = 0.03 * cyclingTime;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculateCyclingEmission() {
        double emission = 0.0;
        return emission;
    }

    private double calculateFlightEmission() {
        double emission = 0.0;

        if (flightType.equals("Short-haul(less than 1500 km)")) {
            emission = numFlights * 225;
        } else if (flightType.equals("Long-haul(more than 1500 km)")) {
            emission = numFlights * 825;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculateMealEmission() {
        double emission = 0.0;

        if (mealType.equals("Beef")) {
            emission = 10 * numServings;
        } else if (mealType.equals("Pork")) {
            emission = 5 * numServings;
        } else if (mealType.equals("Chicken")) {
            emission = 3* numServings;
        } else if (mealType.equals("Fish")) {
            emission = 2 * numServings;
        } else if (mealType.equals("Plant Based")) {
            emission = numServings;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculateClothesEmission(){
        double emission = 0.0;

        if (numClothes >= 1) {
            return numClothes * 25;
        }
        return emission;
    }

    private double calculateElectronicsEmission() {
        double emission = 0.0;

        if (deviceType.equals("Phone")) {
            emission = 250 * numDevices;
        } else if (deviceType.equals("Laptop")) {
            emission = 400 * numDevices;
        } else if (deviceType.equals("TV")) {
            emission = 600 * numDevices;
        } else {
            emission = 0.0;
        }

        return emission;
    }

    private double calculateOtherPurchasesEmission() {
        double emission = 0.0;

        if (purchaseType.equals("Furniture")) {
            emission = 250 * numPurchases;
        } else if (purchaseType.equals("Appliance")) {
            emission = 800 * numPurchases;
        } else if (purchaseType.equals("Book")) {
            emission = 5 * numPurchases;
        } else {
            emission = 0.0;
        }
        return emission;
    }


    private double parseDouble(EditText editText) {
        String text = editText.getText().toString();
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    double totalTranspo = 0.0;
    double totalFood = 0.0;
    double totalShopping = 0.0;

    private void calculateTransportationEmissions() {

        if (isInputVisible(vehicleDetailsLayout)) {
            totalTranspo += calculateVehicleEmission();
        }

        if (isInputVisible(publicTransportLayout)) {
            totalTranspo += calculatePublicTransportEmission();
        }

        if (isInputVisible(cyclingWalkingLayout)) {
            totalTranspo += calculateCyclingEmission();
        }

        if (isInputVisible(flightLayout)) {
            totalTranspo += calculateFlightEmission();
        }
    }

    private void calculateFoodEmissions() {
        if (isInputVisible(mealLayout)) {
            totalFood += calculateMealEmission();
        }
    }

    private void calculateShoppingEmissions() {
        if (isInputVisible(clothesLayout)) {
            totalShopping += calculateClothesEmission();
        }

        if (isInputVisible(electronicsLayout)) {
            totalShopping += calculateElectronicsEmission();
        }

        if (isInputVisible(otherPurchasesLayout)) {
            totalShopping += calculateOtherPurchasesEmission();
        }

    }

    private boolean isInputVisible(View layout) {
        return layout.getVisibility() == View.VISIBLE;
    }

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveData() {
        try {
            vehicleType = spinnerVehicleType.getSelectedItem().toString();
        } catch (NumberFormatException e) {
            vehicleType = "";
        }

        try {
            numFlights = Integer.parseInt(inputNumFlights.getText().toString());
        } catch (NumberFormatException e) {
            numFlights = 0;
        }
        try {
            numServings = Integer.parseInt(inputServings.getText().toString());
        } catch (NumberFormatException e) {
            numServings = 0;
        }
        try {
            numClothes = Integer.parseInt(inputNumClothes.getText().toString());
        } catch (NumberFormatException e) {
            numClothes = 0;
        }
        try {
            numDevices = Integer.parseInt(inputNumDevices.getText().toString());
        } catch (NumberFormatException e) {
            numDevices = 0;
        }
        try {
            numPurchases = Integer.parseInt(inputNumOtherPurchases.getText().toString());
        } catch (NumberFormatException e) {
            numPurchases = 0;
        }
        try {

        } catch (NumberFormatException e) {
            numFlights = 0;
        }
        distanceDriven = parseDouble(inputDistanceDriving);
        transportType = spinnerTransportType.getSelectedItem().toString();
        cyclingTime = parseDouble(inputTimeSpent);
        walkingCyclingDistance = parseDouble(inputDistanceWalking);
        flightType = spinnerFlightType.getSelectedItem().toString();
        mealType = spinnerMealType.getSelectedItem().toString();
        deviceType = spinnerDeviceType.getSelectedItem().toString();
        purchaseType = spinnerPurchaseType.getSelectedItem().toString();
        BillAmount = parseDouble(inputBillAmount);
        BillType = spinnerBillType.getSelectedItem().toString();

        calculateTransportationEmissions();
        calculateFoodEmissions();
        calculateShoppingEmissions();

        //create a RawInputs object using the input values
        UserEmissionData.RawInputs rawInputs = new UserEmissionData.RawInputs(
                distanceDriven,         // e.g., inputDistance
                vehicleType,            // e.g., "Car"
                transportType,          // e.g., "Bus" or "Train"
                cyclingTime,            // e.g., inputCyclingDistance (or time spent cycling, depending on your data)
                numFlights,             // e.g., inputNumFlights
                flightType,             // e.g., "Commercial"
                mealType,               // e.g., "Vegetarian"
                numServings,            // e.g., inputServings
                numClothes,             // e.g., inputNumClothes
                deviceType,             // e.g., "Electronics" or "Phone", etc.
                numDevices,             // e.g., inputNumDevices
                purchaseType,           // e.g., "Other" or "Furniture", etc.
                numPurchases,           // e.g., inputNumOtherPurchases
                BillAmount,             // e.g., inputBillAmount
                BillType                // e.g., "Electricity" or "Gas"
        );

        //create a calculatedEmissions object using the total emissions data

        UserEmissionData.CalculatedEmissions calculatedEmissions = new UserEmissionData.CalculatedEmissions(
                totalTranspo, totalFood, totalShopping);

        //create the UserEmissionData object with raw inputs and calculated emissions
        UserEmissionData userEmissionData = new UserEmissionData(rawInputs, calculatedEmissions);

        //pass the database reference
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator(database);

        //take user id and date
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        String userId = String.valueOf(mauth.getUid());
        long selectedDate = System.currentTimeMillis();

        //then save the emission data to Firebase
        databaseCommunicator.saveUserEmissionData(userId, selectedDate, userEmissionData);

        Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show(); //currently not reaching this
    }
}