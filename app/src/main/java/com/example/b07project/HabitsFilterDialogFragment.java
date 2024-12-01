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
import java.util.ArrayList;

/**
 * HabitsFilterDialogFragment is a DialogFragment used for filtering habits based on categories
 * and impact levels.
 *
 * @see HabitsMenu
 */
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

    /**
     * Called when the fragment is visible to the user and actively running. Customizes the
     * appearance of the dialog by adjusting its window size to match the parent width and wrap
     * the content height.
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    /**
     * Creates the view for the filter dialog, inflating the layout and initializing UI components.
     * This method sets up the interaction logic for the filter options, enabling the user to
     * filter habits based on selected criteria.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return                   The view of the dialog.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.habits_filter_menu, container, false);

        // find all interactive components of the view
        CheckBox allCategories = view.findViewById(R.id.checkBoxAllCategories);
        transportation = view.findViewById(R.id.checkBoxTransportation);
        energy = view.findViewById(R.id.checkBoxEnergy);
        consumption = view.findViewById(R.id.checkBoxConsumption);
        food = view.findViewById(R.id.checkBoxFood);
        CheckBox allImpacts = view.findViewById(R.id.checkBoxAllImpacts);
        lowImpact = view.findViewById(R.id.checkBoxLowImpact);
        mediumImpact = view.findViewById(R.id.checkBoxMediumImpact);
        highImpact = view.findViewById(R.id.checkBoxHighImpact);
        Button filterButton = view.findViewById(R.id.buttonFilter);

        // if the "Categories" checkbox is selected, display all of its "children" options
        allCategories.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;

            transportation.setVisibility(visibility);
            energy.setVisibility(visibility);
            consumption.setVisibility(visibility);
            food.setVisibility(visibility);
        });

        // if the "Impact on C02 Emission" checkbox is selected, display all of its "children" options
        allImpacts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;

            lowImpact.setVisibility(visibility);
            mediumImpact.setVisibility(visibility);
            highImpact.setVisibility(visibility);
        });

        // if the "Transportation" checkbox is selected, add to the set of checked categories
        transportation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(transportation.getText().toString().trim());
            }
        });

        // if the "Energy" checkbox is selected, add to the set of checked categories
        energy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(energy.getText().toString().trim());
            }
        });

        // if the "Consumption" checkbox is selected, add to the set of checked categories
        consumption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(consumption.getText().toString().trim());
            }
        });

        // if the "Food" checkbox is selected, add to the set of checked categories
        food.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedCategories.add(food.getText().toString().trim());
            }
        });

        // if the "Low" checkbox is selected, add to the set of checked impacts
        lowImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
           if (isChecked) {
               checkedImpacts.add(lowImpact.getText().toString().trim());
           }
        });

        // if the "Medium" checkbox is selected, add to the set of checked impacts
        mediumImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedImpacts.add(mediumImpact.getText().toString().trim());
            }
        });

        // if the "High" checkbox is selected, add to the set of checked impacts
        highImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedImpacts.add(highImpact.getText().toString().trim());
            }
        });

        // if the "Filter" button is clicked, filter the RecyclerView's adapter's dataset
        filterButton.setOnClickListener(v -> {
            HabitsMenu.filterHabits(checkedCategories, checkedImpacts);
            dismiss();
        });

        return view;
    }
}
