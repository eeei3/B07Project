package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class HabitsFilterDialogFragment extends DialogFragment {

    public ArrayList<String> checkedCategories = new ArrayList<>();
    public ArrayList<String> checkedImpacts = new ArrayList<>();
    private CheckBox transportation;
    private CheckBox energy;
    private CheckBox consumption;
    private CheckBox food;
    private CheckBox lowImpact;
    private CheckBox mediumImpact;
    private CheckBox highImpact;
    private Button filterButton;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            Window window = dialog.getWindow();
            // Check if the window is available
            if (window != null) {
                // Set the dialog size using LayoutParams
                window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.habits_filter_menu, container, false);

        CheckBox allCategories = view.findViewById(R.id.checkBoxAllCategories);
        transportation = view.findViewById(R.id.checkBoxTransportation);
        energy = view.findViewById(R.id.checkBoxEnergy);
        consumption = view.findViewById(R.id.checkBoxConsumption);
        food = view.findViewById(R.id.checkBoxFood);
        CheckBox allImpacts = view.findViewById(R.id.checkBoxAllImpacts);
        lowImpact = view.findViewById(R.id.checkBoxLowImpact);
        mediumImpact = view.findViewById(R.id.checkBoxMediumImpact);
        highImpact = view.findViewById(R.id.checkBoxHighImpact);
        filterButton = view.findViewById(R.id.buttonFilter);

        allCategories.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;

            transportation.setVisibility(visibility);
            energy.setVisibility(visibility);
            consumption.setVisibility(visibility);
            food.setVisibility(visibility);
        });

        allImpacts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;

            lowImpact.setVisibility(visibility);
            mediumImpact.setVisibility(visibility);
            highImpact.setVisibility(visibility);
        });

        transportation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(transportation.getText().toString().trim());
            }
        });

        energy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(energy.getText().toString().trim());
            }
        });

        consumption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(consumption.getText().toString().trim());
            }
        });

        food.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(food.getText().toString().trim());
            }
        });

        lowImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
           if (isChecked) {
               checkedImpacts.add(lowImpact.getText().toString().trim());
           }
        });

        mediumImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedImpacts.add(mediumImpact.getText().toString().trim());
            }
        });

        highImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedImpacts.add(highImpact.getText().toString().trim());
            }
        });

        filterButton.setOnClickListener(v -> {
            MainActivity.filterHabits(checkedCategories, checkedImpacts);
            dismiss();
        });


        return view;
    }
}
