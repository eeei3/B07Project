package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private TextView titleTextView, inputDate;
    private Button buttonSave;

    // Transportation Details
    private CheckBox checkboxDriveVehicle, checkboxPublicTransport, checkboxCyclingWalking, checkboxFlight;
    private LinearLayout vehicleDetailsLayout, publicTransportLayout, cyclingWalkingLayout, flightLayout;
    private EditText inputDistanceDriving, inputTimeSpent, inputDistanceWalking, inputNumFlights;
    private Spinner spinnerVehicleType, spinnerTransportType, spinnerFlightType;

    // Food Consumption Activities
    private CheckBox checkboxMeal;
    private LinearLayout mealLayout;
    private EditText inputServings;
    private Spinner spinnerMealType;

    // Consumption and Shopping Activities
    private CheckBox checkboxClothes, checkboxElectronics, checkboxOtherPurchases;
    private LinearLayout clothesLayout, electronicsLayout, otherPurchasesLayout;
    private EditText inputNumClothes, inputNumDevices, inputNumOtherPurchases;
    private Spinner spinnerDeviceType, spinnerPurchaseType;

    // Energy Bills Section
    private CheckBox checkboxEnergyBills;
    private LinearLayout energyBillsLayout;
    private Spinner spinnerBillType;
    private EditText inputBillAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activities_page);

        // Initialize UI components
        initializeUIComponents();

        // Set up spinners
        setupSpinner(spinnerVehicleType, R.array.vehicle_types);
        setupSpinner(spinnerTransportType, R.array.transport_types);
        setupSpinner(spinnerFlightType, R.array.flight_types);
        setupSpinner(spinnerMealType, R.array.meal_types);
        setupSpinner(spinnerDeviceType, R.array.electronics_types);
        setupSpinner(spinnerPurchaseType, R.array.other_purchase_types);
        setupSpinner(spinnerBillType, R.array.energy_bill_types);

        // Set up checkboxes
        setupCheckbox(checkboxDriveVehicle, vehicleDetailsLayout);
        setupCheckbox(checkboxPublicTransport, publicTransportLayout);
        setupCheckbox(checkboxCyclingWalking, cyclingWalkingLayout);
        setupCheckbox(checkboxFlight, flightLayout);
        setupCheckbox(checkboxMeal, mealLayout);
        setupCheckbox(checkboxClothes, clothesLayout);
        setupCheckbox(checkboxElectronics, electronicsLayout);
        setupCheckbox(checkboxOtherPurchases, otherPurchasesLayout);
        setupCheckbox(checkboxEnergyBills, energyBillsLayout);
    }

    //Initializes all UI components by binding them to their corresponding views.
    private void initializeUIComponents() {
        titleTextView = findViewById(R.id.title_text_view);
        inputDate = findViewById(R.id.input_date);
        buttonSave = findViewById(R.id.button_save);

        // Transportation UI
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

        // Food UI
        checkboxMeal = findViewById(R.id.checkbox_meal);
        mealLayout = findViewById(R.id.meal_layout);
        inputServings = findViewById(R.id.input_servings);
        spinnerMealType = findViewById(R.id.spinner_meal_type);

        // Consumption and Shopping UI
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

        // Energy Bills UI
        checkboxEnergyBills = findViewById(R.id.checkbox_energy_bills);
        energyBillsLayout = findViewById(R.id.energy_bills_layout);
        inputBillAmount = findViewById(R.id.input_bill_amount);
        spinnerBillType = findViewById(R.id.spinner_bill_type);
    }

    //Sets up a spinner with the provided array resource.
    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //Sets up a checkbox to toggle visibility and perform a calculation when checked.
    private void setupCheckbox(CheckBox checkbox, LinearLayout layout) {
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Show or hide the associated input layout based on the checkbox state
            layout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    //Initialize the variables
    String vehicleType = spinnerVehicleType.getSelectedItem().toString();
    double distanceDriven = parseDouble(inputDistanceDriving);
    //Calculates emissions based on the selected vehicle type and distance driven.
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

    // Get selected transport type from a dropdown or other input
    String transportType = spinnerTransportType.getSelectedItem().toString();
    double cyclingTime = parseDouble(inputTimeSpent);
    private double calculatePublicTransportEmission() {

        double emission = 0.0;

        // Calculate emission using if-else logic
        if (transportType.equals("Bus")) {
            emission = 0.18 * cyclingTime; // Example emission factor for buses
        } else if (transportType.equals("Train")) {
            emission = 0.04 * cyclingTime; // Example emission factor for trains
        } else if (transportType.equals("Subway")) {
            emission = 0.03 * cyclingTime; // Example emission factor for subways
        } else {
            emission = 0.0; // Default case if transport type is unknown
        }
        return emission;
    }

    double walkingcyclingdistance = parseDouble(inputDistanceWalking); // Distance in kilometers
    private double calculateCyclingEmission() {
        double emission = 0.0;

        return emission;
    }

    int numFlights = Integer.parseInt(inputNumFlights.getText().toString());
    String flightType = spinnerFlightType.getSelectedItem().toString();
    private double calculateFlightEmission() {
        double emission = 0.0;

        if (flightType.equals("Short-haul(less than 1500 km)")) {
            emission = numFlights * 225;
        } else if (flightType.equals("Long-haul(more than 1500 km)")) {
            emission = numFlights * 825;
        } else {
            emission = 0.0; // Default case if flight type is unrecognized
        }
        return emission; // Return the calculated emission
    }

    String mealType = spinnerMealType.getSelectedItem().toString();
    int numServings = Integer.parseInt(inputServings.getText().toString());
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
            emission = 0.0; // Default case if meal type is unrecognized
        }
        return emission; // Return the calculated emission for the meal
    }

    int numClothes = Integer.parseInt(inputNumClothes.getText().toString());
    private double calculateClothesEmission(){
        double emission = 0.0;

        if (numClothes >= 1) {
            return numClothes * 25;
        }
        return emission; // Return the calculated emission for the clothing
    }

    String deviceType = spinnerDeviceType.getSelectedItem().toString();
    int numDevices = Integer.parseInt(inputNumDevices.getText().toString());
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

    String purchaseType = spinnerPurchaseType.getSelectedItem().toString();
    int numPurchases = Integer.parseInt(inputNumOtherPurchases.getText().toString());
    private double calculateOtherPurchasesEmission() {
        double emission = 0.0;

        if (purchaseType.equals("Furniture")) {
            emission = 250 * numPurchases;
        } else if (purchaseType.equals("Appliance")) {
            emission = 800 * numPurchases;
        } else if (purchaseType.equals("Book")) {
            emission = 5 * numPurchases;
        } else {
            emission = 0.0; // Default case if the purchase type is unrecognized
        }
        return emission; // Return the calculated emission for the purchase
    }

    Double BillAmount = parseDouble(inputBillAmount);
    String BillType = spinnerBillType.getSelectedItem().toString();

    private double parseDouble(EditText editText) {
        String text = editText.getText().toString();
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    UserEmissionData.RawInputs rawInputs = new UserEmissionData.RawInputs(
            inputDistance, "Car", timeSpent, inputCyclingDistance, inputNumFlights, "Commercial", "Vegetarian", inputServings,
            inputNumClothes, "Electronics", inputNumDevices, "Other", inputNumOtherPurchases
    );

    UserEmissionData.CalculatedEmissions calculatedEmissions = new UserEmissionData.CalculatedEmissions(
            transportationEmissions, foodEmissions, shoppingEmissions, energyBillsEmissions, System.currentTimeMillis()
    );


    private void setupSaveButton(Button saveButton) {
        saveButton.setOnClickListener(v -> {
            // Collect inputs and calculate category-wise totals




            // Display the calculated totals
            displayToast("Transportation Emission: " + totalTranspo +
                    "\nFood Emission: " + totalFood +
                    "\nShopping Emission: " + totalShopping);
        });
    }
    double totalTranspo = 0.0;
    double totalFood = 0.0;
    double totalShopping = 0.0;

    private void calculateTransportationEmissions() {

        // Collect and calculate Vehicle Emissions
        if (isInputVisible(vehicleDetailsLayout)) {
            totalTranspo += calculateVehicleEmission();
        }

        // Collect and calculate Public Transport Emissions
        if (isInputVisible(publicTransportLayout)) {
            totalTranspo += calculatePublicTransportEmission();
        }

        // Collect and calculate Cycling Emissions
        if (isInputVisible(cyclingWalkingLayout)) {
            totalTranspo += calculateCyclingEmission();
        }

        // Collect and calculate Flight Emissions
        if (isInputVisible(flightLayout)) {
            totalTranspo += calculateFlightEmission();
        }
    }

    private void calculateFoodEmissions() {
        // Collect and calculate Meal Emissions
        if (isInputVisible(mealLayout)) {
            totalFood += calculateMealEmission();
        }
    }

    private void calculateShoppingEmissions() {
        // Collect and calculate Clothes Emissions
        if (isInputVisible(clothesLayout)) {
            totalShopping += calculateClothesEmission();
        }

        // Collect and calculate Electronics Emissions
        if (isInputVisible(electronicsLayout)) {
            totalShopping += calculateElectronicsEmission();
        }

        // Collect and calculate Other Purchases Emissions
        if (isInputVisible(otherPurchasesLayout)) {
            totalShopping += calculateOtherPurchasesEmission();
        }

    }

    // Helper method to check layout visibility
    private boolean isInputVisible(View layout) {
        return layout.getVisibility() == View.VISIBLE;
    }

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
