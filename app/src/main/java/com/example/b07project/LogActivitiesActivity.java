
package com.example.b07project;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * LogActivitiesActivity class is responsible for letting the user log their activities.
 *
 */
public class LogActivitiesActivity extends AppCompatActivity {
    private String selectedDay;
    private TextView inputDate;
    private Button buttonSave;
    //transportation details
    private CheckBox checkboxDriveVehicle, checkboxPublicTransport,
            checkboxCyclingWalking, checkboxFlight;
    private LinearLayout vehicleDetailsLayout, publicTransportLayout,
            cyclingWalkingLayout, flightLayout;
    private EditText inputDistanceDriving;
    private EditText inputTimeSpent;
    private EditText inputNumFlights;
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
    private String vehicleType;
    private String transportType;
    private String flightType;
    private String mealType;
    private String deviceType;
    private String purchaseType;
    private double distanceDriven;
    private double cyclingTime;
    private int numFlights, numClothes, numDevices, numPurchases, numServings;

    /**
     * onCreate method is responsible for binding together the UI components and showing the view.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the selected date
        Intent intent = getIntent();
        selectedDay = intent.getStringExtra("selectedDate");

        setContentView(R.layout.log_activities_page);

        initializeUIComponents();

        inputDate.setText(selectedDay);

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

    /**
     * initializeUIComponents method is responsible for initializing the needed UI components.
     *
     */
    private void initializeUIComponents() {
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

    /**
     * setupSpinner method sets up the Spinner for the user to choose between options.
     *
     * @param spinner spinner to be set
     * @param arrayResId the array ID
     */
    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                R.layout.spinner2
        );
        adapter.setDropDownViewResource(R.layout.spinner2);
        spinner.setAdapter(adapter);
    }


    /**
     * setupCheckbox method sets up Checkbox for user to see drop down menu.
     *
     * @param checkbox checkbox to be set
     * @param layout layout to enable visibility
     */
    private void setupCheckbox(CheckBox checkbox, LinearLayout layout) {
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Show or hide the associated input layout based on the checkbox state
            layout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    /**
     * calculateShoppingEmissions calculates the vehicle emissions produced by the user.
     *
     * @return the calculated vehicle emissions from the user.
     */
    private double calculateVehicleEmission() {
        double emission;

        switch (vehicleType) {
            case "Gasoline":
                emission = 0.24 * distanceDriven;
                break;
            case "Diesel":
                emission = 0.27 * distanceDriven;
                break;
            case "Electric":
                emission = 0.05 * distanceDriven;
                break;
            case "Hybrid":
                emission = 0.16 * distanceDriven;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the public transport emissions produced by the user.
     *
     * @return the calculated public transport emissions from the user.
     */
    private double calculatePublicTransportEmission() {
        double emission;

        switch (transportType) {
            case "Bus":
                emission = 0.18 * cyclingTime;
                break;
            case "Train":
                emission = 0.04 * cyclingTime;
                break;
            case "Subway":
                emission = 0.03 * cyclingTime;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the flight emissions produced by the user.
     *
     * @return the calculated flight emissions from the user.
     */
    private double calculateFlightEmission() {
        double emission;

        switch (flightType) {
            case "Short-haul(less than 1500 km)":
                emission = numFlights * 225;
                break;
            case "Long-haul(more than 1500 km)":
                emission = numFlights * 825;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the meal emissions produced by the user.
     *
     * @return the calculated meal emissions from the user.
     */
    private double calculateMealEmission() {
        double emission;

        switch (mealType) {
            case "Beef":
                emission = 10 * numServings;
                break;
            case "Pork":
                emission = 5 * numServings;
                break;
            case "Chicken":
                emission = 3 * numServings;
                break;
            case "Fish":
                emission = 2 * numServings;
                break;
            case "Plant Based":
                emission = numServings;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the clothes emissions produced by the user.
     *
     * @return the calculated clothes emissions from the user.
     */
    private double calculateClothesEmission(){
        if (numClothes >= 1) {
            return numClothes * 25;
        }
        return 0.0;
    }

    /**
     * calculateShoppingEmissions calculates the electronics emissions produced by the user.
     *
     * @return the calculated electronics emissions from the user.
     */
    private double calculateElectronicsEmission() {
        double emission;

        switch (deviceType) {
            case "Phone":
                emission = 250 * numDevices;
                break;
            case "Laptop":
                emission = 400 * numDevices;
                break;
            case "TV":
                emission = 600 * numDevices;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the other purchases' emissions produced by the user.
     *
     * @return the calculated other purchases' emissions from the user.
     */
    private double calculateOtherPurchasesEmission() {
        double emission;

        switch (purchaseType) {
            case "Furniture":
                emission = 250 * numPurchases;
                break;
            case "Appliance":
                emission = 800 * numPurchases;
                break;
            case "Book":
                emission = 5 * numPurchases;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }


    /**
     * parseDouble method tries to parse a double from an EditText.
     *
     * @param editText the EditText to be parse
     * @return the parsed double
     */
    private double parseDouble(EditText editText) {
        String text = editText.getText().toString();
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    double totalTranspo;
    double totalFood;
    double totalShopping;

    /**
     * calculateTransportationEmissions calculates the total emissions from modes of
     * transportations taken by the user.
     */
    private void calculateTransportationEmissions() {
        if (isInputVisible(vehicleDetailsLayout)) {
            totalTranspo += calculateVehicleEmission();
        }
        if (isInputVisible(publicTransportLayout)) {
            totalTranspo += calculatePublicTransportEmission();
        }
        if (isInputVisible(flightLayout)) {
            totalTranspo += calculateFlightEmission();
        }
    }

    /**
     * calculateFoodEmissions calculates the food emissions produced by the user.
     *
     */
    private void calculateFoodEmissions() {
        if (isInputVisible(mealLayout)) {
            totalFood += calculateMealEmission();
        }
    }

    /**
     * calculateShoppingEmissions calculates the shopping emissions produced by the user.
     *
     */
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

    /**
     * isInputVisible method checks if a layout is visible to the user.
     *
     * @param layout the layout to inspect visibility
     * @return true if layout is visible, false otherwise
     */
    private boolean isInputVisible(View layout) {
        return layout.getVisibility() == View.VISIBLE;
    }

    /**
     * saveData method saves the logged activities by the user to the firebase.
     *
     */
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

        distanceDriven = parseDouble(inputDistanceDriving);
        transportType = spinnerTransportType.getSelectedItem().toString();
        cyclingTime = parseDouble(inputTimeSpent);
        flightType = spinnerFlightType.getSelectedItem().toString();
        mealType = spinnerMealType.getSelectedItem().toString();
        deviceType = spinnerDeviceType.getSelectedItem().toString();
        purchaseType = spinnerPurchaseType.getSelectedItem().toString();
        double billAmount = parseDouble(inputBillAmount);
        String billType = spinnerBillType.getSelectedItem().toString();

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
                billAmount,             // e.g., inputBillAmount
                billType                // e.g., "Electricity" or "Gas"
        );

        //create a calculatedEmissions object using the total emissions data

        UserEmissionData.CalculatedEmissions calculatedEmissions =
                new UserEmissionData.CalculatedEmissions(totalTranspo, totalFood, totalShopping);

        //create the UserEmissionData object with raw inputs and calculated emissions
        UserEmissionData userEmissionData = new UserEmissionData(rawInputs, calculatedEmissions);

        //pass the database reference
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator(database);

        // get the selected date
        Intent intentDate = getIntent();
        selectedDay = intentDate.getStringExtra("selectedDate");

        //then save the emission data to Firebase
        databaseCommunicator.saveUserEmissionData(selectedDay, userEmissionData, LogActivitiesActivity.this);

        //show the success message
        Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();

        //after we save the data to the database, send it to the DetailPageActivity
        Intent intent = new Intent(LogActivitiesActivity.this, DetailPageActivity.class);

        // Pass collected data to the next Activity as extras
        intent.putExtra("vehicleType", rawInputs.getVehicleType());
        intent.putExtra("distanceDriven", rawInputs.getDistanceDriven());
        intent.putExtra("numFlights", rawInputs.getNumFlights());
        intent.putExtra("mealType", rawInputs.getMealType());
        intent.putExtra("numClothes", rawInputs.getNumClothes());
        intent.putExtra("numServings", rawInputs.getNumServings());
        intent.putExtra("cyclingTime", rawInputs.getCyclingTime());
        intent.putExtra("deviceType", rawInputs.getDeviceType());
        intent.putExtra("numDevices", rawInputs.getNumDevices());
        intent.putExtra("purchaseType", rawInputs.getPurchaseType());
        intent.putExtra("numPurchases", rawInputs.getNumOtherPurchases());
        intent.putExtra("BillAmount", rawInputs.getBillAmount());
        intent.putExtra("BillType", rawInputs.getBillType());
        intent.putExtra("totalEmissions", totalTranspo + totalFood + totalShopping);

        // Start the EcoTrackerHomeActivity
        Intent homeIntent = new Intent(this, EcoTrackerHomeActivity.class);
        startActivity(homeIntent);
    }
}