package com.example.b07project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

/**
 * UserHabitsProgressDialogFragment is a DialogFragment for logging and tracking progress
 * on a user's habit.
 *
 */
public class UserHabitsProgressDialogFragment extends DialogFragment {
    private final static String argHabitName = "habit_name";

    private ProgressBar userHabitProgressBar;
    private EditText userHabitNumDaysInt;

    private int goal = 5;
    private int progress;
    // Note for Back-End
    // this information, "progress", is deleted after dialog is closed.
    // needs to get this user's info from outside the class. (note this is for a single specific habit)
    // same for field "goal"

    public static UserHabitsProgressDialogFragment newInstance(String habitName) {
        UserHabitsProgressDialogFragment fragment = new UserHabitsProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(argHabitName, habitName);
        fragment.setArguments(args);
        return fragment;
    }

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
     * Creates the view for the progress dialog, inflating the layout and initializing UI
     * components, allowing users to log activity and increment progress for a specific habit.
     *
     * @param inflater            The LayoutInflater object that can be used to inflate
     *                            any views in the fragment,
     * @param container           If non-null, this is the parent view that the fragment's
     *                            UI should be attached to.  The fragment should not add the view itself,
     *                            but this can be used to generate the LayoutParams of the view.
     * @param savedInstancedState If non-null, this fragment is being re-constructed
     *                            from a previous saved state as given here.
     * @return                    The view of the dialog.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancedState) {
        View view = inflater.inflate(R.layout.user_habits_details, container, false);

        // find the core components of the view
        userHabitProgressBar = view.findViewById(R.id.userHabitProgressBar);
        userHabitNumDaysInt = view.findViewById(R.id.userHabitNumDaysInt);
        Button logActivity = view.findViewById(R.id.logActivityButton);

        // set the goal for the ProgressBar
        userHabitProgressBar.setMax(goal);

        // NOTE FOR BACK-END:
        // this is for logging activities, when finished, i.e. progress = goal
        // if user decides to not renew, i.e. the "No" option, then u can remove it from the firebase
        // if user does renew, from the way I've done, u can simply reset the old goal to new goal, and progress to 0
        // if the "Log Activity" button is clicked, increment both the Progressbar and EditText
        logActivity.setOnClickListener(v -> {
            if (progress < goal) {
                // if goal is not reached, simply increment the progress
                progress++;
                setProgressBar();
                setTextNumDays();

                // dismissing the dialog will delete the "progress", need to get the field "progress" elsewhere
                // dismiss()
                Toast.makeText(requireContext(), "Activity successfully logged!", Toast.LENGTH_SHORT).show();

            } else {
                // if goal is reached,
                // find the corresponding HabitsModel in the User's page that the dialog is displaying progress about
                HabitsModel currHabitModel = null;
                for (int i = 0; i < HabitsMenu.userHabitsModels.size(); i++) {
                    assert getArguments() != null;
                    if (HabitsMenu.userHabitsModels.get(i).getHabitName().equals(getArguments().getString(argHabitName))) {
                        currHabitModel = HabitsMenu.userHabitsModels.get(i);
                    }
                }
                final HabitsModel habit = currHabitModel;

                // launch a confirmation dialog on whether to set a new goal or not
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Congrats! You've finished your goal! Would you like to set a new goal?")
                        .setTitle("Confirm")
                        .setPositiveButton("No", (dialog, id) -> {
                            // Handle the "No" action
                            dismiss();
                            Toast.makeText(requireContext(), "Goal Finished!",
                                    Toast.LENGTH_SHORT).show();

                            // remove habit and notify the adapter to remove the item at the position
                            int pos = HabitsMenu.userHabitsModels.indexOf(habit);
                            HabitsMenu.userHabitsModels.remove(habit);
                            HabitsMenu.adapter.notifyItemRemoved(pos);
                        })
                        .setNegativeButton("Yes", (dialog, id) -> {
                            // Handle the "Yes" action
                            dismiss();
                            assert habit != null;
                            // have the user set a new goal for the same habit
                            HabitsSetGoalsDialogFragment fragment = HabitsSetGoalsDialogFragment.newInstance(habit.getHabitDesc());
                            fragment.show(getParentFragmentManager(), "SetGoalsDialog");

                            // remove habit and notify the adapter to remove the item at the position
                            int pos = HabitsMenu.userHabitsModels.indexOf(habit);
                            HabitsMenu.userHabitsModels.remove(habit);
                            HabitsMenu.adapter.notifyItemRemoved(pos);
                        });
                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    /**
     * Updates the progress bar to reflect the current value of the progress field.
     *
     */
    private void setProgressBar() {
        userHabitProgressBar.setProgress(progress);
    }

    /**
     * Updates the text field to display the current progress value in days.
     *
     */
    private void setTextNumDays() {
        userHabitNumDaysInt.setText(String.valueOf(progress));
    }
}
