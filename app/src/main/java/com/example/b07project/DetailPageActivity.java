package com.example.b07project;

// Import required packages
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

/**
 * DetailPageActivity is responsible for managing and displaying a detailed page where the user
 * can input and view their data for the activities - transportation, food consumption, shopping,
 * and energy bills. The user can modify the data through input fields, checkboxes, and spinners,
 * and the app calculates the CO2 emissions for each activity based on the entered information.
 *
 * The activity also allows the user to edit the information and save it to Firebase. After the
 * the user clicks Save, the information is used to recalculate emissions and is updated on Firebase.
 * The input fields and checkboxes are dynamically shown or hidden based on user selection.
 *
 * Once the data is saved, the app transitions back to the EcoTracker Home Screen.
 *
 * @see DatabaseCommunicator
 * @see EcoTrackerCalculations
 * @see UserEmissionData
 * @see FirebaseDatabase
 */

public class DetailPageActivity extends AppCompatActivity {

    // date and userID
    private String selectedDay;

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

        // get the selected date
        Intent intent = getIntent();
        selectedDay = intent.getStringExtra("selectedDate");

        setContentView(R.layout.activity_detail_page);

        // Initialize UI components
        initializeUIComponents();


        inputDate.setText(selectedDay);

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
            //Save data to Firebase
            saveDataToFirebase();

            // Start the EcoTrackerHomeActivity
            Intent homeIntent = new Intent(this, EcoTrackerHomeActivity.class);
            startActivity(homeIntent);

            // Disable editing and toggle buttons
            enableEditing(false);
            buttonSave.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.VISIBLE);
        });
        //once database communicator gets all the required data

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseCommunicator model = new DatabaseCommunicator(database);
        model.setWaiter(new DatabaseCommunicator.Waiter() {
            @Override
            public void onObjectReady() {
                // get the raw inputs from the firebase
                // Input all of the frontend updates that have to happen here

                System.out.println("hello");
                System.out.println(model.raw.getDistanceDriven());


                // Set spinner
                // Input the vehicle type from database to spinner
                inputDistanceDriving.setText(String.valueOf(model.raw.getDistanceDriven()));
                setSpinnerSelection(spinnerVehicleType, model.raw.getVehicleType());

                // Public transport
                setSpinnerSelection(spinnerTransportType, model.raw.getTransportType());
                inputTimeSpent.setText(String.valueOf(model.raw.getPubtransportTime()));

                // Cycling or walking
                inputDistanceWalking.setText(String.valueOf(model.raw.getCyclingTime()));

                // Flight
                inputNumFlights.setText(String.valueOf(model.raw.getNumFlights()));
                setSpinnerSelection(spinnerFlightType, model.raw.getFlightType());

                // Meal
                setSpinnerSelection(spinnerMealType, model.raw.getMealType());
                inputServings.setText(String.valueOf(model.raw.getNumServings()));

                // Buy new clothes
                inputNumClothes.setText(String.valueOf(model.raw.getNumClothes()));

                // Devices
                setSpinnerSelection(spinnerDeviceType, model.raw.getDeviceType());
                inputNumDevices.setText(String.valueOf(model.raw.getNumDevices()));

                // Other purchases
                setSpinnerSelection(spinnerPurchaseType, model.raw.getPurchaseType());
                inputNumOtherPurchases.setText(String.valueOf(model.raw.getNumOtherPurchases()));

                // Energy bills
                inputBillAmount.setText(String.valueOf(model.raw.getBillAmount()));
                setSpinnerSelection(spinnerBillType, model.raw.getBillType());
            }
        });
        model.serverRawInputReader(selectedDay);
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

    // Helper method to setup a spinner with values from a string array resource
    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                R.layout.spinner2
        );
        adapter.setDropDownViewResource(R.layout.spinner2);
        spinner.setAdapter(adapter);
    }

    // Helper method to set up a checkbox to toggle the visibility of its associated layout
    private void setupCheckbox(CheckBox checkbox, LinearLayout layout) {
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) ->
                layout.setVisibility(isChecked ? View.VISIBLE : View.GONE)
        );
        layout.setVisibility(checkbox.isChecked() ? View.VISIBLE : View.GONE);
    }

    // Helper method to enable or disable editing mode on UI components
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

    private double parseDouble(EditText editText) {
        String text = editText.getText().toString();
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private boolean isInputVisible(View layout) {
        return layout.getVisibility() == View.VISIBLE;
    }

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * saveDataToFirebase collects all the user inputs from the various UI elements (e.g., spinners, text fields)
     * and uses them to create a new `UserEmissionData` object. The data is then saved to Firebase under
     * a specified date. The method also performs necessary data parsing and exception handling when converting
     * user input (e.g., integers, doubles) and calculates the environmental impact based on the input.
     *
     * The following categories are processed: transportation, food, shopping, and energy bills. Each category's
     * emissions are calculated using the `EcoTrackerCalculations` class, and the total emissions are stored
     * in the `UserEmissionData.CalculatedEmissions` object.
     *
     * @see EcoTrackerCalculations
     * @see UserEmissionData
     * @see DatabaseCommunicator
     * @see FirebaseDatabase
     */
    private void saveDataToFirebase() {
        // variables to hold user input data
        String vehicleType;
        double distanceDriven;
        String transportType;
        double cyclingTime;
        int numFlights;
        String flightType;
        String mealType;
        int numServings;
        int numClothes;
        int numDevices;
        int numPurchases;
        String deviceType;
        String purchaseType;
        double BillAmount;
        String BillType;
        double pubtransTime;

        vehicleType = spinnerVehicleType.getSelectedItem().toString();
        distanceDriven = parseDouble(inputDistanceDriving);
        cyclingTime = parseDouble(inputDistanceWalking);
        pubtransTime = parseDouble(inputTimeSpent);
        try {
            numFlights = Integer.parseInt(inputNumFlights.getText().toString());
        }
        catch (NumberFormatException e) {
            numFlights = 0;
        }
        try {
            numServings = Integer.parseInt(inputServings.getText().toString());
        }
        catch (NumberFormatException e) {
            numServings = 0;
        }
        try {
            numClothes = Integer.parseInt(inputNumClothes.getText().toString());
        }
        catch (NumberFormatException e) {
            numClothes = 0;
        }
        try {
            numDevices = Integer.parseInt(inputNumDevices.getText().toString());
        }
        catch (NumberFormatException e) {
            numDevices = 0;
        }
        try {
            numPurchases = Integer.parseInt(inputNumOtherPurchases.getText().toString());
        }
        catch (NumberFormatException e) {
            numPurchases = 0;
        }
        BillAmount = parseDouble(inputBillAmount);
        flightType = spinnerFlightType.getSelectedItem().toString();
        transportType = spinnerTransportType.getSelectedItem().toString();
        mealType = spinnerMealType.getSelectedItem().toString();
        deviceType = spinnerDeviceType.getSelectedItem().toString();
        purchaseType = spinnerPurchaseType.getSelectedItem().toString();
        BillType = spinnerBillType.getSelectedItem().toString();

        EcoTrackerCalculations calculator = new EcoTrackerCalculations(vehicleType,
                distanceDriven,
                transportType,
                pubtransTime,
                cyclingTime,
                numFlights,
                flightType,
                mealType,
                numServings,
                numClothes,
                numDevices,
                numPurchases,
                deviceType,
                purchaseType);
        // these methods currently belong in LogActivitiesActivity
        // for now, just duplicate the calculations that will occur
        double totalTranspo = calculator.calculateTransportationEmissions();
        double totalFood = calculator.calculateFoodEmissions();
        double totalShopping = calculator.calculateShoppingEmissions();

        // recreate RawInputs and CalculatedEmissions objects
        UserEmissionData.RawInputs rawInputs = new UserEmissionData.RawInputs(
                distanceDriven, vehicleType, transportType, pubtransTime, cyclingTime, numFlights, flightType, mealType, numServings,
                numClothes, deviceType, numDevices, purchaseType, numPurchases, BillAmount, BillType);

        UserEmissionData.CalculatedEmissions calculatedEmissions = new UserEmissionData.CalculatedEmissions(
                totalTranspo, totalFood, totalShopping);

        UserEmissionData userEmissionData = new UserEmissionData(rawInputs, calculatedEmissions);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseCommunicator presenter = new DatabaseCommunicator(database);

        // get the selected date
        Intent intent = getIntent();
        selectedDay = intent.getStringExtra("selectedDate");

        presenter.saveUserEmissionData(selectedDay, userEmissionData, DetailPageActivity.this);
    }

    /**
     * setSpinnerSelection - sets the selected value in a `Spinner` based on the provided value. It searches through
     * the spinner's adapter to find the needed item and sets it as the selected item. If a matching
     * value is found, it updates the spinner's selection accordingly.
     *
     * @param spinner The `Spinner` whose selection will be updated.
     * @param value The value to set as the selected item in the spinner.
     */
    private void setSpinnerSelection(Spinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                Log.e("shit" + i, adapter.getItem(i).toString());
                if (value.equals(adapter.getItem(i).toString())) {
                    spinner.setSelection(i);
                    return;
                }
            }
        }
    }


}