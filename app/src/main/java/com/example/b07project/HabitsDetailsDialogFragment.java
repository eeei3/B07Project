package com.example.b07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Dialog;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class HabitsDetailsDialogFragment extends DialogFragment {
    private static final String argHabitDesc = "habit_desc";
    private static final String argImpactDesc = "impact_desc";
    private static final String argHabitImage = "habit_image";

    public static HabitsDetailsDialogFragment newInstance(String habitName, String habitDesc, int habitImage) {
        HabitsDetailsDialogFragment fragment = new HabitsDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString(argHabitDesc, habitName);
        args.putString(argImpactDesc, habitDesc);
        args.putInt(argHabitImage, habitImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            Window window = dialog.getWindow();
            // Check if the window is available
            if (window != null) {
                // Set the dialog size using LayoutParams
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.habits_item_details, container, false);

        // Get the arguments passed to the fragment
        String habitDesc = null;
        if (getArguments() != null) {
            habitDesc = getArguments().getString(argHabitDesc);
        }
        String impactDesc = null;
        if (getArguments() != null) {
            impactDesc = getArguments().getString(argImpactDesc);
        }
        int habitImage = 0;
        if (getArguments() != null) {
            habitImage = getArguments().getInt(argHabitImage);
        }

        // Find views and set the data
        TextView habitDescTextView = view.findViewById(R.id.habit_name);
        TextView impactDescTextView = view.findViewById(R.id.habit_description);
        ImageView habitImageView = view.findViewById(R.id.habit_image);
        Button habitAdopt = view.findViewById(R.id.positive_button);

        habitDescTextView.setText(habitDesc);
        impactDescTextView.setText(impactDesc);
        habitImageView.setImageResource(habitImage);

        // get current HabitsModel
        HabitsModel currModel = null;
        for (int i = 0; i < HabitsMenu.habitsModels.size(); i++) {
            if (HabitsMenu.habitsModels.get(i).getHabitDesc().equals(habitDesc)) {
                currModel = HabitsMenu.habitsModels.get(i);
            }
        }
        final HabitsModel habit = currModel;

        habitAdopt.setOnClickListener(v -> {
            if (HabitsMenu.userHabitsModels.contains(habit)) {
                Toast.makeText(requireContext(), "Habit already added.", Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
                HabitsSetGoalsDialogFragment setGoalsDialog = HabitsSetGoalsDialogFragment.newInstance(getArguments().getString(argHabitDesc));
                setGoalsDialog.show(getParentFragmentManager(), "SetGoalsDialog");

                // Notify adapter to update color
                if (getActivity() instanceof OnHabitUpdatedListener) {
                    ((OnHabitUpdatedListener) getActivity()).onHabitUpdated(habit);
                }
            }
        });

        return view;
    }
}
