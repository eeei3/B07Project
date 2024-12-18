package com.example.b07project;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
    public Button logActivity;
    private EditText userHabitNumDaysInt;
    // Note for Back-End
    // this information, "progress", is deleted after dialog is closed.
    // needs to get this user's info from outside the class. (note this is for a single specific habit)
    // same for field "goal"

    /**
     * UserHabitsProgressDialogFragment - Creates a new instance of UserHabitsProgressDialogFragment
     * @param habitName - the name of the habit who's dialog box is being instantiated
     * @return - the dialog box's fragment
     */
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
//        userHabitProgressBar.setProgress(3);
        userHabitNumDaysInt = view.findViewById(R.id.userHabitNumDaysInt);
        logActivity = view.findViewById(R.id.logActivityButton);

        // find the corresponding HabitsModel in the User's page that the dialog is displaying progress about
        Goal currHabitModel = null;
        for (int i = 0; i < HabitsMenu.userGoals.size(); i++) {
            assert getArguments() != null;
            if (HabitsMenu.userGoals.get(i).getName().equals(getArguments().getString(argHabitName))) {
                currHabitModel = HabitsMenu.userGoals.get(i);
            }
        }
        final Goal habit = currHabitModel;





        // set the max length and current progress for the ProgressBar and the EditText
//        setProgressBar();
//        setTextNumDays();
        HabitsMenu.presenter.progdiafrag = this;

        // this should update both the fields progress and aim of HabitsMenu
        if (habit != null) {
            HabitsMenu.presenter.userGetProg(habit.getName());
        }
        // NOTE FOR BACK-END:
        // this is for logging activities, when finished, i.e. progress = goal
        // if user decides to not renew, i.e. the "No" option, then u can remove it from the firebase
        // if user does renew, from the way I've done, u can simply reset the old goal to new goal, and progress to 0
        // if the "Log Activity" button is clicked, increment both the Progressbar and EditText
        logActivity.setOnClickListener(v -> {

            if (habit.prog < HabitsMenu.aim - 1) {
                logActivity.setVisibility(View.GONE);
                // if goal is not reached,
                HabitsMenu.presenter.userSetProg(habit); // increment by one day???
//                setProgressBar();
//                setTextNumDays();

                // dismissing the dialog will delete the "progress", need to get the field "progress" elsewhere
                // dismiss()
                Toast.makeText(requireContext(), "Activity successfully logged!", Toast.LENGTH_SHORT).show();

            } else {
                // if goal is reached,
                // launch a confirmation dialog on whether to set a new goal or not
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Congrats! You've finished your goal! Would you like to set a new goal?")
                        .setTitle("Confirm")
                        .setPositiveButton("No", (dialog, id) -> {
                            // Handle the "No" action
                            HabitsMenu.presenter.userDeleteGoal(habit, getContext(), 0);
                            dismiss();


                            // remove habit and notify the adapter to remove the item at the position
                            int pos = HabitsMenu.userGoals.indexOf(habit);
                            HabitsMenu.userGoals.remove(habit);
                            HabitsMenu.adapter.notifyItemRemoved(pos);
                        })
                        .setNegativeButton("Yes", (dialog, id) -> {
                            // Handle the "Yes" action
                            dismiss();
                            // have the user set a new goal for the same habit
                            HabitsSetGoalsDialogFragment fragment = HabitsSetGoalsDialogFragment.newInstance(habit.getHabitDesc());
                            fragment.show(getParentFragmentManager(), "SetGoalsDialog");

                            // remove habit and notify the adapter to remove the item at the position
                            int pos = HabitsMenu.userGoals.indexOf(habit);
                            HabitsMenu.userGoals.remove(habit);
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
     */
    public void setProgressBar(int prog) {
        userHabitProgressBar.setMax(HabitsMenu.aim);
        Log.d("Progress", "Setting progress: " + prog + " and aim is " + HabitsMenu.aim);
        if (userHabitProgressBar == null) {
            Log.d("Progress", "progress bar is null");
        }
        userHabitProgressBar.setProgress(prog);
    }

    /**
     * Updates the text field to display the current progress value in days.
     */
    public void setTextNumDays(int prog) {
        userHabitNumDaysInt.setText(String.valueOf(prog));
    }
}
