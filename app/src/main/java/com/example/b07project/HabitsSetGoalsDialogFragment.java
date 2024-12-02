package com.example.b07project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.materialswitch.MaterialSwitch;


/**
 * HabitsSetGoalsDialogFragment is a DialogFragment that allows users to set goals for a specific
 * habit. This fragment is used to gather user input related to goal setting for a habit and update
 * the corresponding data.
 *
 * @see HabitsMenu
 * @see OnHabitUpdatedListener
 */
public class HabitsSetGoalsDialogFragment extends DialogFragment {
    private static final String argHabitDesc = "habit_name_set_goals";
    private EditText timesEditText;
    private SeekBar timesSeekBar;
    private View reminderOptionsContainer;

    /**
     * Creates a new instance of HabitsSetGoalsDialogFragment with the specified habit description.
     *
     * @param habitDesc The description of the habit for which the goals are being set.
     * @return A new instance of the fragment.
     */
    public static HabitsSetGoalsDialogFragment newInstance(String habitDesc) {
        HabitsSetGoalsDialogFragment fragment = new HabitsSetGoalsDialogFragment();
        Bundle args = new Bundle();
        args.putString(argHabitDesc, habitDesc);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Inflates the fragment's layout and initializes the user interface elements. Sets up
     * listeners for the UI elements to update values accordingly.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return                   The view for this fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_goals_user, container, false);

        // find all interactive/core components of this view
        timesEditText = view.findViewById(R.id.times_edit_text);
        timesSeekBar = view.findViewById(R.id.times_seek_bar);
        TimePicker timePicker = view.findViewById(R.id.time_habit_reminder);
        Button saveButton = view.findViewById(R.id.save_button);
        reminderOptionsContainer = view.findViewById(R.id.reminder_options_container);

        // Set default values and visibility to certain components
        timesSeekBar.setProgress(1);
        timesSeekBar.setMax(365);
        timesEditText.setText("1");
        timePicker.setMinute(0);
        timePicker.setHour(7);
        reminderOptionsContainer.setVisibility(View.GONE);

        // define behaviour for when the SeekBar is updated
        timesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Called when the progress level of the SeekBar is changed, either by the user or
             * programmatically. Synchronizes the value of the SeekBar with the EditText field
             * to ensure consistency.
             *
             * @param seekBar  The SeekBar whose progress has changed
             * @param progress The current progress level. This will be in the range min..max where min
             *                 and max were set by {@link ProgressBar#setMin(int)} and
             *                 {@link ProgressBar#setMax(int)}, respectively. (The default values for
             *                 min is 0 and max is 100.)
             * @param fromUser True if the progress change was initiated by the user.
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // sync timesSeekbar with timesEditText
                if (!timesEditText.getText().toString().isEmpty()) {
                    timesEditText.setText(String.valueOf(progress));
                }
            }

            /**
             * Called when the user starts touching the SeekBar. No action taken.
             *
             * @param seekBar The SeekBar in which the touch gesture began
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            /**
             * Called when the user stops touching the SeekBar. No action taken.
             *
             * @param seekBar The SeekBar in which the touch gesture began
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // define behaviour for when the EditText is updated
        timesEditText.addTextChangedListener(new TextWatcher() {
            /**
             * Called before the text is changed in the EditText. No action taken.
             *
             * @param s     The text before the change.
             * @param start The starting position of the change.
             * @param count The number of characters that are about to change.
             * @param after The number of characters that will replace the existing text.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            /**
             * Called as the text in the EditText is being changed. Synchronizes the value of the
             * EditText with the SeekBar to ensure consistency.
             *
             * @param s      The text after the change.
             * @param start  The starting position of the change.
             * @param before The number of characters that were replaced.
             * @param count  The number of new characters added.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // sync timesSeekbar with timesEditText
                if (!s.toString().isEmpty()) {
                    int value = Integer.parseInt(s.toString());
                    timesSeekBar.setProgress(value);
                }
            }

            /**
             * Called after the text in the EditText has been changed. No action taken.
             *
             * @param s The Editable containing the new text.
             */
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // define behaviour for when the "Save" button is clicked
        saveButton.setOnClickListener(v -> {


            // extracted data to store information in databases and such...
            String goal = timesEditText.getText().toString().trim();

            // find the corresponding HabitsModel that the dialog is currently setting goals for
            // and add the habit to the set of the user's habits
            Goal habit;
            for (int i = 0; i < HabitsMenu.allGoals.size(); i++) {
                habit = HabitsMenu.allGoals.get(i);

                assert getArguments() != null;
                if (habit.getHabitDesc().equals(getArguments().getString(argHabitDesc))) {
                    HabitsMenu.userGoals.add(habit);

                    // Notify the adapter that the habit has been updated
                    if (getActivity() instanceof OnHabitUpdatedListener) {
                        ((OnHabitUpdatedListener) getActivity()).onHabitUpdated(habit);
                    }

                    HabitsMenu.presenter.userAddGoal(habit, goal);

                    // close the dialog
                    success();
                    break;
                }
            }

        });

        return view;
    }

    /**
     * success - Notify the user they successfully added a new goal
     */
    public void success() {
        Toast.makeText(requireContext(), "New Goal Added!",
                Toast.LENGTH_SHORT).show();
        dismiss();
    }

    /**
     * failure - Notify the user adding a new goal was unsuccessful
     */
    public void failure() {
        Toast.makeText(requireContext(), "Failed to Add New Goal",
                Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
