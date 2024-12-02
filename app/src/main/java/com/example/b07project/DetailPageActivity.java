package com.example.b07project;

// Import required packages
import static java.lang.Double.parseDouble;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.HashMap;

public class DetailPageActivity extends AppCompatActivity {

    //main functionalities:
    //inflates the activity_detail_page
    //display all of the current information from database to the textviews and spinners


    // General UI elements
    private TextView titleTextView, inputDate;
    private Button buttonSave, buttonEdit;

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
        setContentView(R.layout.activity_detail_page);

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

        // Initially disable inputs and hide Save button
        enableEditing(false);
        buttonSave.setVisibility(View.GONE);

        // Edit button logic
        buttonEdit.setOnClickListener(v -> {
            enableEditing(true);
            buttonEdit.setVisibility(View.GONE);
            buttonSave.setVisibility(View.VISIBLE);
        });

        // Save button logic
        buttonSave.setOnClickListener(v -> {
            // Save data to Firebase
            //saveDataToFirebase(); //not sure how to implement this with firebase

            // Disable editing and toggle buttons
            enableEditing(false);
            buttonSave.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.VISIBLE);
        });

    }

    private void initializeUIComponents() {
        titleTextView = findViewById(R.id.title_text_view);
        inputDate = findViewById(R.id.input_date);
        buttonSave = findViewById(R.id.button_save);
        buttonEdit = findViewById(R.id.button_edit);

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

    // Sets up a spinner with the provided array resource
    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // Sets up a checkbox to toggle the visibility of its associated layout
    private void setupCheckbox(CheckBox checkbox, LinearLayout layout) {
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) ->
                layout.setVisibility(isChecked ? View.VISIBLE : View.GONE)
        );
        layout.setVisibility(checkbox.isChecked() ? View.VISIBLE : View.GONE);
    }

    // Enable or disable editing for all input fields and spinners
    private void enableEditing(boolean isEnabled) {
        inputDate.setEnabled(isEnabled);
        inputDistanceDriving.setEnabled(isEnabled);
        inputTimeSpent.setEnabled(isEnabled);
        inputDistanceWalking.setEnabled(isEnabled);
        inputNumFlights.setEnabled(isEnabled);
        inputServings.setEnabled(isEnabled);
        inputNumClothes.setEnabled(isEnabled);
        inputNumDevices.setEnabled(isEnabled);
        inputNumOtherPurchases.setEnabled(isEnabled);
        inputBillAmount.setEnabled(isEnabled);

        spinnerVehicleType.setEnabled(isEnabled);
        spinnerTransportType.setEnabled(isEnabled);
        spinnerFlightType.setEnabled(isEnabled);
        spinnerMealType.setEnabled(isEnabled);
        spinnerDeviceType.setEnabled(isEnabled);
        spinnerPurchaseType.setEnabled(isEnabled);
        spinnerBillType.setEnabled(isEnabled);
    }

    //judy will change this to just reflect the user's selected date
    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date and set it on the TextView
                    String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    inputDate.setText(formattedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }


    //copy pasted from logactivitiesactivity: emission calculations - will refactor later on
    private double calculateVehicleEmission() {
        double emission;
        /*
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
        */
        return 0;
    }

    private double calculatePublicTransportEmission() {

        double emission = 0.0;

        return emission;
    }

    private double calculateCyclingEmission() {
        double emission = 0.0;
        return emission;
    }

    private double calculateFlightEmission() {
        double emission = 0.0;

        return emission;
    }

    private double calculateMealEmission() {
        double emission = 0.0;


        return emission;
    }

    private double calculateClothesEmission(){
        double emission = 0.0;

        return emission;
    }

    private double calculateElectronicsEmission() {
        double emission = 0.0;


        return emission;
    }

    private double calculateOtherPurchasesEmission() {
        double emission = 0.0;

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

    //when user clicks save --> calculate the emissions (similar to how LogActivitiesActivity does it)
    //after the user edits whatever they want to edit
    //update the rawinput and the calculatedemissions of the user
    //call the UserEmissionData --> pass the new rawinput and pass the new calculated emissions
    //update the firebase database

    private void saveDataToFirebase() {

        String vehicleType = spinnerVehicleType.getSelectedItem().toString();
        double distanceDriven = parseDouble(inputDistanceDriving);
        double cyclingTime = parseDouble(inputTimeSpent);
        double walkingCyclingDistance = parseDouble(inputDistanceWalking);
        int numFlights = Integer.parseInt(inputNumFlights.getText().toString());
        int numServings = Integer.parseInt(inputServings.getText().toString());
        int numClothes = Integer.parseInt(inputNumClothes.getText().toString());
        int numDevices = Integer.parseInt(inputNumDevices.getText().toString());
        int numPurchases = Integer.parseInt(inputNumOtherPurchases.getText().toString());
        double BillAmount = parseDouble(inputBillAmount);
        String flightType = spinnerFlightType.getSelectedItem().toString();
        String transportType = spinnerTransportType.getSelectedItem().toString();
        String mealType = spinnerMealType.getSelectedItem().toString();
        String deviceType = spinnerDeviceType.getSelectedItem().toString();
        String purchaseType = spinnerPurchaseType.getSelectedItem().toString();
        String BillType = spinnerBillType.getSelectedItem().toString();

        // these methods currently belong in LogActivitiesActivity
        // for now, just duplicate the calculations that will occur
        //double totalTranspo = calculateTransportationEmissions(vehicleType, distanceDriven, transportType, cyclingTime, numFlights, flightType);
       // double totalFood = calculateFoodEmissions(mealType, numServings);
       // double totalShopping = calculateShoppingEmissions(numClothes, numDevices, numPurchases, deviceType, purchaseType);

        // recreate RawInputs and CalculatedEmissions objects
        UserEmissionData.RawInputs rawInputs = new UserEmissionData.RawInputs(
                distanceDriven, vehicleType, transportType, cyclingTime, numFlights, flightType, mealType, numServings,
                numClothes, deviceType, numDevices, purchaseType, numPurchases, BillAmount, BillType);

        UserEmissionData.CalculatedEmissions calculatedEmissions = new UserEmissionData.CalculatedEmissions(
                totalTranspo, totalFood, totalShopping);

        UserEmissionData userEmissionData = new UserEmissionData(rawInputs, calculatedEmissions);

        // will review and fix
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        String userId = mauth.getUid();
        long selectedDate = System.currentTimeMillis();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator(database, DetailPageActivity.this);
        //databaseCommunicator.saveUserEmissionData(userId, selectedDate, user);

    }

}