package com.example.b07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.materialswitch.MaterialSwitch;

public class SetGoalsDialogFragment extends DialogFragment {
    private NumberPicker numberPicker;
    private MaterialSwitch reminderSwitch;
    private TimePicker timePicker;
    private Spinner frequencySpinner;
    private Button saveButton;
    private View reminderOptionsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_goals_user, container, false);

        numberPicker = view.findViewById(R.id.habit_quantity);
        reminderSwitch = view.findViewById(R.id.reminder_switch);
        timePicker = view.findViewById(R.id.time_habit_reminder);
        frequencySpinner = view.findViewById(R.id.habit_frequency);
        saveButton = view.findViewById(R.id.save_button);
        reminderOptionsContainer = view.findViewById(R.id.reminder_options_container);

        // Set min and max (weeks) for a habit
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(52);

        // reminder options are hidden
        reminderOptionsContainer.setVisibility(View.GONE);

        // toggle visibility of reminder options if switch is flipped
        reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> reminderOptionsContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        // Save button
        saveButton.setOnClickListener(v -> {
            int quantity = numberPicker.getValue();
            boolean remindersEnabled = reminderSwitch.isChecked();
            String reminderTime = null;
            String frequency = null;

            if (remindersEnabled) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                reminderTime = String.format("%02d:%02d", hour, minute);
                frequency = frequencySpinner.getSelectedItem().toString();
            }

            dismiss(); // Close the dialog
        });

        return view;
    }
}
