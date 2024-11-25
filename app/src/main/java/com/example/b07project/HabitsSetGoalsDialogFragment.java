package com.example.b07project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.materialswitch.MaterialSwitch;

public class HabitsSetGoalsDialogFragment extends DialogFragment {
    private EditText timesEditText;
    private SeekBar timesSeekBar;
    private MaterialSwitch reminderSwitch;
    private TimePicker timePicker;
    private Spinner frequencySpinner;
    private View reminderOptionsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_goals_user, container, false);

        timesEditText = view.findViewById(R.id.times_edit_text);
        timesSeekBar = view.findViewById(R.id.times_seek_bar);
        reminderSwitch = view.findViewById(R.id.reminder_switch);
        timePicker = view.findViewById(R.id.time_habit_reminder);
        frequencySpinner = view.findViewById(R.id.habit_frequency);
        Button saveButton = view.findViewById(R.id.save_button);
        reminderOptionsContainer = view.findViewById(R.id.reminder_options_container);

        // Set default values
        timesSeekBar.setProgress(1);
        timesSeekBar.setMax(365);
        timesEditText.setText("1");
        timePicker.setMinute(0);
        timePicker.setHour(7);

        // reminder options are hidden
        reminderOptionsContainer.setVisibility(View.GONE);

        // toggle visibility of reminder options if switch is flipped
        reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> reminderOptionsContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE));


        // Synchronize SeekBar with EditText
        timesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!timesEditText.getText().toString().isEmpty()) {
                    timesEditText.setText(String.valueOf(progress));
                    timesEditText.setSelection(timesEditText.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Synchronize EditText with SeekBar
        timesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    int value = Integer.parseInt(s.toString());
                    timesSeekBar.setProgress(value); // Update SeekBar to match EditText
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Save button
        saveButton.setOnClickListener(v -> {

            // store information in databases and such...

            int quantity = Integer.parseInt(timesEditText.getText().toString().trim());
            boolean remindersEnabled = reminderSwitch.isChecked();
            String reminderTime = null;
            String frequency = null;

            if (remindersEnabled) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                reminderTime = String.format("%d:%d", hour, minute);
                frequency = frequencySpinner.getSelectedItem().toString();
            }


            dismiss(); // Close the dialog
        });

        return view;
    }
}
