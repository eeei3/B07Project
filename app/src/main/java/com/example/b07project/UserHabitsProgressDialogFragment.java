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
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

/**
 * UserHabitsProgressDialogFragment is a DialogFragment for logging and tracking progress
 * on a user's habit.
 *
 */
public class UserHabitsProgressDialogFragment extends DialogFragment {

    private ProgressBar userHabitProgressBar;
    private EditText userHabitNumDaysInt;
    private int progress;
    // Note for Back-End
    // this information, "progress", is deleted after dialog is closed.
    // needs to probably get this information from outside the class.

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

        // if the "Log Activity" button is clicked, increment both the Progressbar and EditText
        logActivity.setOnClickListener(v -> {
            progress++;
            setProgressBar();
            setTextNumDays();

            // dismissing the dialog will delete the "progress", need to get the field "progress" elsewhere
            // dismiss();
            // Toast.makeText(requireContext(), "Activity successfully logged!", Toast.LENGTH_SHORT).show();
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
