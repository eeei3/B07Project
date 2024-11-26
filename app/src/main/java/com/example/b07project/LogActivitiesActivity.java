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
    TextView titleTextView;

    TextView inputDate;
    Button buttonSave;

    // Transportation Details
    CheckBox checkboxDriveVehicle, checkboxPublicTransport, checkboxCyclingWalking, checkboxFlight;
    LinearLayout vehicleDetailsLayout, publicTransportLayout, cyclingWalkingLayout, flightLayout;
    EditText inputDistanceDriving, inputTimeSpent, inputDistanceWalking, inputNumFlights;
    Spinner spinnerVehicleType;
    Spinner spinnerTransportType, spinnerFlightType;

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

        // Initialize LinearLayout for Vehicle Details
        vehicleDetailsLayout = findViewById(R.id.vehicle_details_layout);
        publicTransportLayout = findViewById(R.id.public_transport_layout);
        cyclingWalkingLayout = findViewById(R.id.cycling_walking_layout);
        flightLayout = findViewById(R.id.flight_layout);

        // Initialize EditText and Spinner for Transportation Activities Details
        inputDistanceDriving = findViewById(R.id.input_distance_driving);
        inputTimeSpent = findViewById(R.id.input_time_spent);
        inputDistanceWalking = findViewById(R.id.input_distance_walking);
        inputNumFlights = findViewById(R.id.input_num_flights);
        spinnerVehicleType = findViewById(R.id.spinner_vehicle_type);
        spinnerTransportType = findViewById(R.id.spinner_transport_type);
        spinnerFlightType = findViewById(R.id.spinner_flight_type);

        // spinner vehicle types set up
        ArrayAdapter<CharSequence> vehicle_types_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.vehicle_types, // Your string array resource
                android.R.layout.simple_spinner_dropdown_item // Default layout for spinner items
        );

        // Specify the layout to use for dropdown menu
        vehicle_types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleType.setAdapter(vehicle_types_adapter);

        // Set an item selected listener for the spinner
        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Set the default option to "gasoline"
                int defaultPosition = 0;
                parent.setSelection(defaultPosition);

                // Optional: Notify the user
                Toast.makeText(parent.getContext(), "No selection made. Defaulting to 'gasoline'.", Toast.LENGTH_SHORT).show();

                // Perform any action with the default value
                String selectedVehicle = parent.getItemAtPosition(defaultPosition).toString();
            }
        });

        // spinner transportation set up
        ArrayAdapter<CharSequence> transport_type_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.transport_types, // Your string array resource
                android.R.layout.simple_spinner_dropdown_item // Default layout for spinner items
        );

        // Specify the layout to use for dropdown menu
        transport_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransportType.setAdapter(transport_type_adapter);

        // Set an item selected listener for the spinner
        spinnerTransportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "No selection made. Please select a transportation type", Toast.LENGTH_SHORT).show();
            }

        });

        // spinner flight set up
        ArrayAdapter<CharSequence> flight_type_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.flight_types, // Your string array resource
                android.R.layout.simple_spinner_dropdown_item // Default layout for spinner items
        );

        // Specify the layout to use for dropdown menu
        flight_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFlightType.setAdapter(flight_type_adapter);

        // Set an item selected listener for the spinner
        spinnerFlightType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "No selection made. Please select a flight type", Toast.LENGTH_SHORT).show();
            }

        });

        // Initialize Food Consumption Activities UI elements
        checkboxMeal = findViewById(R.id.checkbox_meal);
        mealLayout = findViewById(R.id.meal_layout);
        inputServings = findViewById(R.id.input_servings);
        spinnerMealType = findViewById(R.id.spinner_meal_type);

        // spinner meal type set up
        ArrayAdapter<CharSequence> meal_types_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.meal_types,
                android.R.layout.simple_spinner_dropdown_item
        );

        meal_types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealType.setAdapter(meal_types_adapter);

        // Set an item selected listener for the spinner
        spinnerMealType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "No selection made. Please select a meal type", Toast.LENGTH_SHORT).show();
            }

        });


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

        // spinner Device set up
        ArrayAdapter<CharSequence> electronics_type_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.electronics_types,
                android.R.layout.simple_spinner_dropdown_item
        );

        electronics_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeviceType.setAdapter(electronics_type_adapter);

        // Set an item selected listener for the spinner
        spinnerDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "No selection made. Please select a device type", Toast.LENGTH_SHORT).show();
            }

        });

        // spinner purchase type set up
        ArrayAdapter<CharSequence> purchase_type_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.other_purchase_types,
                android.R.layout.simple_spinner_dropdown_item
        );

        purchase_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPurchaseType.setAdapter(purchase_type_adapter);

        // Set an item selected listener for the spinner
        spinnerPurchaseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "No selection made. Please select a purchase type", Toast.LENGTH_SHORT).show();
            }

        });

        // Initialize Energy Bills Section UI elements
        checkboxEnergyBills = findViewById(R.id.checkbox_energy_bills);
        energyBillsLayout = findViewById(R.id.energy_bills_layout);
        inputBillAmount = findViewById(R.id.input_bill_amount);
        spinnerBillType = findViewById(R.id.spinner_bill_type);

        // spinner bill type set up
        ArrayAdapter<CharSequence> bill_type_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.energy_bill_types,
                android.R.layout.simple_spinner_dropdown_item
        );

        bill_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBillType.setAdapter(bill_type_adapter);

        // Set an item selected listener for the spinner
        spinnerBillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(LogActivitiesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "No selection made. Please select a bill type", Toast.LENGTH_SHORT).show();
            }

        });
        //set up the total for each activities
        final double[] TotalEmission = new double[3];

        //Set up the variables to collect the input for Drive Personal Vehicle
        final double[] DriveVehicleEmission = new double[1];//need for fb-> for eco tracker home screen
        String inputDistanceText = inputDistanceDriving.getText().toString();
        double inputDistance = Double.parseDouble(inputDistanceText);//need for fb -> detail activity screen
        String inputVehicleType = spinnerVehicleType.getSelectedItem().toString();//need for fb -> -> detail activity screen

        //Set up the variables to collect the input for Take Public Transportation
        final double[] PublicTransportEmission = new double[1]; //need for fb -> for eco tracker home screen
        String inputTransportType = spinnerTransportType.getSelectedItem().toString();
        double TimeSpent = Double.parseDouble(inputTimeSpent.getText().toString());

        //Set up the variables to collect the input for Cycling or Walking
        final double[] CyclingEmission = new double[1];
        String inputTransportationType = spinnerTransportType.getSelectedItem().toString();
        double inputCyclingDistance = Double.parseDouble(inputDistanceWalking.getText().toString());

        //Set up the variables to collect the input for Flight (Short-Haul or Long-Haul)
        final double[] FlightEmission = new double[1];
        String inputNumFlightsText = inputNumFlights.getText().toString();
        int inputNumFlights = Integer.parseInt(inputNumFlightsText);
        String inputFlightType = spinnerFlightType.getSelectedItem().toString();

        //Set up the variables to collect the input for Meal
        final double[] MealEmission = new double[1];
        String inputMealType = spinnerMealType.getSelectedItem().toString();
        String inputServingsText = inputServings.getText().toString();
        int inputServings = Integer.parseInt(inputServingsText);

        //set up the variables to collect the input for Buy new clothes
        final double[] ClothesEmission = new double[1];
        String inputNumClothesText = inputNumClothes.getText().toString();
        int inputNumClothes = Integer.parseInt(inputNumClothesText);

        //set up the variables to collect the input for Buy electronics
        final double[] ElectronicsEmission = new double[1];
        String inputDeviceType = spinnerDeviceType.getSelectedItem().toString();
        String inputNumDevicesText = inputNumDevices.getText().toString();
        int inputNumDevices = Integer.parseInt(inputNumDevicesText);

        //set up the variables to collect the input for Other Purchases
        final double[] OtherPurchasesEmission = new double[1];
        String inputPurchaseType = spinnerPurchaseType.getSelectedItem().toString();
        String inputNumOtherPurchasesText = inputNumOtherPurchases.getText().toString();
        int inputNumOtherPurchases = Integer.parseInt(inputNumOtherPurchasesText);

        //set up the variables to collect the input for Energy Bills
        final double[] EnergyBills = new double[1];
        String inputBillType = spinnerBillType.getSelectedItem().toString();
        String inputBillAmountText = inputBillAmount.getText().toString();
        double inputBillAmount = Double.parseDouble(inputBillAmountText);


        // Set OnCheckedChangeListener for each checkbox to toggle visibility of the corresponding layout
        checkboxDriveVehicle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                vehicleDetailsLayout.setVisibility(View.VISIBLE);
                if(inputDistanceDriving.getText() != null) {
                    if(inputVehicleType.equals("Gasoline")) {
                        DriveVehicleEmission[0] = 0.24 * inputDistance;
                    }
                    else if(inputVehicleType.equals("Diesel")) {
                        DriveVehicleEmission[0] = 0.27 * inputDistance;
                    }
                    else if(inputVehicleType.equals("Electric")) {
                        DriveVehicleEmission[0] = 0.05 * inputDistance;
                    }
                    else if(inputVehicleType.equals("Hybrid")) {
                        DriveVehicleEmission[0] = 0.16 * inputDistance;
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
                if(inputTransportType.equals("Bus")) {
                    PublicTransportEmission[0] = 1 * TimeSpent;
                }
                else if(inputTransportType.equals("Train")) {
                    PublicTransportEmission[0] = 5 * TimeSpent;
                }
                else if(inputTransportType.equals("Subway")) {
                    PublicTransportEmission[0] = 2 * TimeSpent;
                }
            } else {
                publicTransportLayout.setVisibility(View.GONE);
            }
        });

        checkboxCyclingWalking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cyclingWalkingLayout.setVisibility(View.VISIBLE);
                CyclingEmission[0] = 0.0;//0.0 any way, referring to a qna on piazza, no emissions at all for cycling or walking
            } else {
                cyclingWalkingLayout.setVisibility(View.GONE);
            }
        });

        checkboxFlight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                flightLayout.setVisibility(View.VISIBLE);
                if(inputFlightType.equals("Short-haul(less than 1500 km)")) {
                    FlightEmission[0] = 225 * inputNumFlights;
                }
                else if(inputFlightType.equals("Long-haul(more than 1500 km)")) {
                    FlightEmission[0] = 825 * inputNumFlights;
                }
            } else {
                flightLayout.setVisibility(View.GONE);
            }
        });

        checkboxMeal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mealLayout.setVisibility(View.VISIBLE);
                if(inputMealType.equals("Beef")) {
                    MealEmission[0] = 10 * inputServings;
                }
                else if(inputMealType.equals("Pork")) {
                    MealEmission[0] = 5 * inputServings;
                }
                else if(inputMealType.equals("Chicken")) {
                    MealEmission[0] = 3 * inputServings;
                }
                else if(inputMealType.equals("Fish")) {
                    MealEmission[0] = 2 * inputServings;
                }
                else if(inputMealType.equals("Plant Based")) {
                    MealEmission[0] = inputServings;
                }
            } else {
                mealLayout.setVisibility(View.GONE);
            }
        });

        checkboxClothes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                clothesLayout.setVisibility(View.VISIBLE);
                ClothesEmission[0] = 25 * inputNumClothes;
            } else {
                clothesLayout.setVisibility(View.GONE);
            }
        });

        checkboxElectronics.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                electronicsLayout.setVisibility(View.VISIBLE);
                if(inputDeviceType.equals("Phone")) {
                    ElectronicsEmission[0] = 70 * inputNumDevices;
                }
                else if(inputDeviceType.equals("Laptop")) {
                    ElectronicsEmission[0] = 400 * inputNumDevices;
                }
                else if(inputDeviceType.equals("TV")) {
                    ElectronicsEmission[0] = 600 * inputNumDevices;
                }
            } else {
                electronicsLayout.setVisibility(View.GONE);
            }
        });

        checkboxOtherPurchases.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                otherPurchasesLayout.setVisibility(View.VISIBLE);
                if(inputPurchaseType.equals("Furniture")) {
                    OtherPurchasesEmission[0] = 250 * inputNumOtherPurchases;
                }
                else if(inputPurchaseType.equals("Appliances")) {
                    OtherPurchasesEmission[0] = 800 * inputNumOtherPurchases;
                }
                else if(inputPurchaseType.equals("Books")) {
                    OtherPurchasesEmission[0] = 5 * inputNumOtherPurchases;
                }
            } else {
                otherPurchasesLayout.setVisibility(View.GONE);
            }
        });

        checkboxEnergyBills.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                energyBillsLayout.setVisibility(View.VISIBLE);
                EnergyBills[0] = inputBillAmount; //input here will be the money value of the bill anyway
            } else {
                energyBillsLayout.setVisibility(View.GONE);
            }
        });

        TotalEmission[0] = DriveVehicleEmission[0] + PublicTransportEmission[0] + CyclingEmission[0] + FlightEmission[0];//sum of all the emissions for transpo activitiy
        TotalEmission[1] = MealEmission[0];//sum of all the emissions for meal activity
        TotalEmission[2] = ClothesEmission[0] + ElectronicsEmission[0] + OtherPurchasesEmission[0];//sum of all the emissions for shopping activity
    }
}
