package com.example.b07project;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.ThreadFactory;

public class SurveyActivity extends AppCompatActivity {
    private RadioGroup CarOwnership, CarUsage, CarMiles, PublicTransport, PublicTransportUse,
            ShortFlights, LongFlights, Diet, BeefConsumption, PorkConsumption,
            ChickenConsumption, FishConsumption, FoodWaste, Housing, HousingPeople,
            HousingSize, Energy, Bill, EnergyWater, Renewable, Clothes, Thrift,
            Electronic, Recycle;
    private Button btnSubmit;
    private LinearLayout groupCarQuestionsLayout, dietQuestionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_survey);

        CarOwnership = findViewById(R.id.radioGroupCarOwnership);
        CarUsage = findViewById(R.id.radioGroupCarUsage);
        CarMiles = findViewById(R.id.radioGroupCarMiles);
        PublicTransport = findViewById(R.id.ptUse);
        btnSubmit = findViewById(R.id.submitButton);
        groupCarQuestionsLayout = findViewById(R.id.carQuestionsLayout);
        PublicTransportUse = findViewById(R.id.radioptTime);
        ShortFlights = findViewById(R.id.radioShortFlights);
        LongFlights = findViewById(R.id.radioLongFlights);
        Diet = findViewById(R.id.radioDiet);
        dietQuestionsLayout = findViewById(R.id.dietQuestionsLayout);
        BeefConsumption = findViewById(R.id.radioBeefConsumption);
        PorkConsumption = findViewById(R.id.radioPorkConsumption);
        ChickenConsumption = findViewById(R.id.radioChickenConsumption);
        FishConsumption = findViewById(R.id.radioFishConsumption);
        FoodWaste = findViewById(R.id.radioFoodWaste);
        Housing = findViewById(R.id.radioHousing);
        HousingPeople = findViewById(R.id.radioHousingPeople);
        HousingSize = findViewById(R.id.radioHousingSize);
        Energy = findViewById(R.id.radioEnergy);
        Bill = findViewById(R.id.radioBill);
        EnergyWater = findViewById(R.id.radioEnergyWater);
        Renewable = findViewById(R.id.radioRenewable);
        Clothes = findViewById(R.id.radioClothes);
        Thrift = findViewById(R.id.radioThrift);
        Electronic = findViewById(R.id.radioElectronic);
        Recycle = findViewById(R.id.radioRecycle);

        String[] categories = getResources().getStringArray(R.array.location_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        Spinner locationSpinner = findViewById(R.id.locationSpinner);
        locationSpinner.setAdapter(adapter);

        // Set a listener on the CarOwnership RadioGroup to show/hide additional questions
        CarOwnership.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Show the additional questions if "I own a car" or "I lease a car" is selected
                if (checkedId == R.id.radioYes) {
                    groupCarQuestionsLayout.setVisibility(View.VISIBLE); // Show the hidden questions
                    makeChildrenVisible(groupCarQuestionsLayout);
                } else if (checkedId == R.id.radioNo) {
                    groupCarQuestionsLayout.setVisibility(View.GONE); // Hide the hidden questions
                    makeChildrenGone(groupCarQuestionsLayout);
                }
            }
        });
        Diet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMeatBased) {
                    // Show the additional questions for a meat-based diet
                    dietQuestionsLayout.setVisibility(View.VISIBLE);
                    makeChildrenVisible(dietQuestionsLayout);
                } else {
                    // Hide the additional questions
                    dietQuestionsLayout.setVisibility(View.GONE);
                    makeChildrenGone(dietQuestionsLayout);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                double transportationEmissions = 0;
                double foodEmissions = 0;
                double housingEmissions = 0;
                double consumptionEmissions = 0;
                double totalEmissions = 0;
                RadioGroup[] allRadioGroups = {CarOwnership, CarUsage, CarMiles, PublicTransport,
                        PublicTransportUse, ShortFlights, LongFlights, Diet,
                        BeefConsumption, PorkConsumption, ChickenConsumption,
                        FishConsumption, FoodWaste, Housing, HousingPeople,
                        HousingSize, Energy, Bill, EnergyWater, Renewable,
                        Clothes, Thrift, Electronic, Recycle};

                // Check if a radio button is selected
                if (!areAllQuestionsAnswered(allRadioGroups) || locationSpinner.getSelectedItem().equals("Select a Country")) {
                    Toast.makeText(SurveyActivity.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                } else {
                    if (!locationSpinner.getSelectedItem().toString().equals(("Select a Country"))){
                    // Create an instance of EmissionsCalculator
                    // Now, call the emissions calculation method
                    EmissionsCalculator emissionsCalculator = new EmissionsCalculator();

                    // Calculate the transportation emissions (you can adjust method signature if necessary)
                    transportationEmissions = emissionsCalculator.getTransportationEmissions(SelectedOption(CarOwnership), SelectedOption(CarUsage), SelectedOption(CarMiles), SelectedOption(PublicTransport), SelectedOption(PublicTransportUse), SelectedOption(ShortFlights), SelectedOption(LongFlights));
                    foodEmissions = emissionsCalculator.getFoodEmissions(SelectedOption(Diet), SelectedOption(BeefConsumption), SelectedOption(PorkConsumption), SelectedOption(ChickenConsumption), SelectedOption(FishConsumption), SelectedOption(FoodWaste));
                    housingEmissions = emissionsCalculator.getHousingEmissions(SelectedOption(Housing), SelectedOption(HousingSize), SelectedOption(HousingPeople), SelectedOption(Energy), SelectedOption(EnergyWater), SelectedOption(Bill), SelectedOption(Renewable));
                    consumptionEmissions = emissionsCalculator.getConsumptionEmissions(SelectedOption(Clothes), SelectedOption(Thrift), SelectedOption(Electronic), SelectedOption(Recycle));
                    totalEmissions = transportationEmissions + foodEmissions + housingEmissions + consumptionEmissions;
                    user.totalEmissions = totalEmissions;
                    //carbon.footprint = CalculateCarOwnership(SelectedOption(CarOwnership)) + CalculatePublicTransportation(SelectedOption(PublicTransport), SelectedOption(PublicTransportUse)) + CalculateShortFlight(SelectedOption(ShortFlights)) + CalculateLongFlight(SelectedOption(LongFlights));
                    //Toast.makeText(SurveyActivity.this, "Carbon Footprint: " + totalEmissions, Toast.LENGTH_SHORT).show();
                    }
                }

                // Pass the emissions data via Intent
                Intent intent = new Intent(SurveyActivity.this, ResultsActivity.class);
                intent.putExtra("totalEmissions", totalEmissions);
                intent.putExtra("transportationEmissions", transportationEmissions);
                intent.putExtra("foodEmissions", foodEmissions);
                intent.putExtra("housingEmissions", housingEmissions);
                intent.putExtra("consumptionEmissions", consumptionEmissions);

                // Optionally, pass other details
                String location = SelectedOption(locationSpinner);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }

    private String SelectedOption(View view) {
        if (view instanceof RadioGroup) {
            // Handle RadioGroup selection
            RadioGroup radioGroup = (RadioGroup) view;
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                return null; // No option selected
            }

            RadioButton selectedRadioButton = findViewById(selectedId); // 'this' is implied
            return selectedRadioButton.getText().toString();
        }
        else if (view instanceof Spinner) {
            // Handle Spinner selection
            Spinner spinner = (Spinner) view;
            Object selectedItem = spinner.getSelectedItem();

            if (selectedItem == null) {
                return null; // No item selected
            }

            return selectedItem.toString();
        }

        return null; // Return null if neither RadioGroup nor Spinner
    }

    private static boolean areAllQuestionsAnswered(RadioGroup[] radioGroups) {
        for (RadioGroup group : radioGroups) {
            // Print the current visibility state of the group
            System.out.print("Group ID: " + group.getResources().getResourceEntryName(group.getId())
                    + " Visibility: " + (group.getVisibility() == View.VISIBLE ? "VISIBLE" : "GONE") + "\n");

            // Check if the group is visible and if no option has been selected
            if (group.getVisibility() == View.VISIBLE) {
                int checkedId = group.getCheckedRadioButtonId();
                System.out.print("Checked ID: " + checkedId + "\n");

                if (checkedId == -1) {
                    // Print the unanswered question for visible groups only
                    System.out.print("Unanswered question: " + group.getResources().getResourceEntryName(group.getId()) + "\n");
                    return false; // At least one visible question is not answered
                }
            }
        }
        return true; // All visible questions answered
    }
    public void makeChildrenVisible(ViewGroup parent) {
        if (parent == null) return;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            child.setVisibility(View.VISIBLE);
        }
    }
    private void makeChildrenGone(ViewGroup parent) {
        if (parent == null) return;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            child.setVisibility(View.GONE);
        }
    }


}
