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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class UserHabitsProgressDialogFragment extends DialogFragment {

    private ProgressBar userHabitProgressBar;
    private EditText userHabitNumDaysInt;
    private int progress;
    // this information is deleted after dialog is closed,
    // needs to get this field from outside the class. (note for back-end)

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancedState) {
        View view = inflater.inflate(R.layout.user_habits_details, container, false);

        userHabitProgressBar = view.findViewById(R.id.userHabitProgressBar);
        userHabitNumDaysInt = view.findViewById(R.id.userHabitNumDaysInt);
        Button logActivity = view.findViewById(R.id.logActivityButton);


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

    private void setProgressBar() {
        userHabitProgressBar.setProgress(progress);
    }

    private void setTextNumDays() {
        userHabitNumDaysInt.setText(String.valueOf(progress));
    }
}
