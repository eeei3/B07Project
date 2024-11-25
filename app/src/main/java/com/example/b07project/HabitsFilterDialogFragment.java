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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class HabitsFilterDialogFragment extends DialogFragment {

    private CheckBox allCategories;
    private CheckBox transportation;
    private CheckBox energy;
    private CheckBox consumption;
    private CheckBox food;
    private CheckBox allImpacts;
    private CheckBox lowImpact;
    private CheckBox mediumImpact;
    private CheckBox highImpact;

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

        allCategories = view.findViewById(R.id.checkBoxAllCategories);
        transportation = view.findViewById(R.id.checkBoxTransportation);
        energy = view.findViewById(R.id.checkBoxEnergy);
        consumption = view.findViewById(R.id.checkBoxConsumption);
        food = view.findViewById(R.id.checkBoxFood);
        allImpacts = view.findViewById(R.id.checkBoxAllImpacts);
        lowImpact = view.findViewById(R.id.checkBoxLowImpact);
        mediumImpact = view.findViewById(R.id.checkBoxMediumImpact);
        highImpact = view.findViewById(R.id.checkBoxHighImpact);

        allCategories.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int visibility = isChecked ? View.VISIBLE : View.GONE;

                transportation.setVisibility(visibility);
                energy.setVisibility(visibility);
                consumption.setVisibility(visibility);
                food.setVisibility(visibility);
            }
        });

        allImpacts.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int visibility = isChecked ? View.VISIBLE : View.GONE;

                lowImpact.setVisibility(visibility);
                mediumImpact.setVisibility(visibility);
                highImpact.setVisibility(visibility);
            }
        });


        return view;
    }
}
