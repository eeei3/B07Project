package com.example.b07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Dialog;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

public class HabitsDialogFragment extends DialogFragment {
    private static final String argHabitName = "habit_name";
    private static final String argHabitDesc = "habit_description";
    private static final String argHabitImage = "habit_image";

    public static HabitsDialogFragment newInstance(String habitName, String habitDesc, int habitImage) {
        HabitsDialogFragment fragment = new HabitsDialogFragment();
        Bundle args = new Bundle();
        args.putString(argHabitName, habitName);
        args.putString(argHabitDesc, habitDesc);
        args.putInt(argHabitImage, habitImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        // Check if the window is available
        if (window != null) {
            // Set the dialog size using LayoutParams
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.habits_item_details, container, false);

        // Get the arguments passed to the fragment
        String habitName = getArguments().getString(argHabitName);
        String habitDescription = getArguments().getString(argHabitDesc);
        int habitImage = getArguments().getInt(argHabitImage);

        // Find views and set the data
        TextView habitNameTextView = view.findViewById(R.id.habit_name);
        TextView habitDescriptionTextView = view.findViewById(R.id.habit_description);
        ImageView habitImageView = view.findViewById(R.id.habit_image);

        habitNameTextView.setText(habitName);
        habitDescriptionTextView.setText(habitDescription);
        habitImageView.setImageResource(habitImage);

        return view;
    }
}
