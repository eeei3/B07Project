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

/**
 * HabitsDetailsDialogFragment class is a DialogFragment that displays detailed information
 * about a specific habit.
 *
 * @see HabitsModel
 * @see HabitsMenu
 * @see HabitsSetGoalsDialogFragment
 * @see OnHabitUpdatedListener
 */
public class HabitsDetailsDialogFragment extends DialogFragment {
    private static final String argHabitDesc = "habit_desc";
    private static final String argImpactDesc = "impact_desc";
    private static final String argHabitImage = "habit_image";

    /**
     * Creates a new instance of the HabitsDetailsDialogFragment with the provided
     * habit details.
     *
     * @param habitDesc   A description of the habit
     * @param impactDesc  A description of the habit's impact.
     * @param habitImage  A resource ID for the image representing the habit.
     * @return A new instance of HabitsDetailsDialogFragment containing the provided
     *         habit details.
     */
    public static HabitsDetailsDialogFragment newInstance(String habitDesc,
                                                          String impactDesc,
                                                          int habitImage) {
        HabitsDetailsDialogFragment fragment = new HabitsDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString(argHabitDesc, habitDesc);
        args.putString(argImpactDesc, impactDesc);
        args.putInt(argHabitImage, habitImage);
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
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    /**
     * Called to have the fragment instantiate its UI view. This method inflates the layout for
     * the fragment, retrieves data, and sets up the UI elements.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return                   The view for the fragment's UI.
     */
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

        // Find the views components
        TextView habitDescTextView = view.findViewById(R.id.habit_name);
        TextView impactDescTextView = view.findViewById(R.id.habit_description);
        ImageView habitImageView = view.findViewById(R.id.habit_image);
        Button habitAdopt = view.findViewById(R.id.positive_button);

        // Set the data to the components
        habitDescTextView.setText(habitDesc);
        impactDescTextView.setText(impactDesc);
        habitImageView.setImageResource(habitImage);

        // get the current HabitsModel that the dialog is displaying details about
        HabitsModel currModel = null;
        for (int i = 0; i < HabitsMenu.habitsModels.size(); i++) {
            if (HabitsMenu.habitsModels.get(i).getHabitDesc().equals(habitDesc)) {
                currModel = HabitsMenu.habitsModels.get(i);
            }
        }
        final HabitsModel habit = currModel;

        // define behaviour for when the "Adopt Habit" button is clicked
        habitAdopt.setOnClickListener(v -> {
            if (HabitsMenu.userHabitsModels.contains(habit)) {
                // handle when habit had already been adopted
                Toast.makeText(requireContext(), "Habit already added.", Toast.LENGTH_SHORT).show();
            } else {
                // if habit not yet adopted, launch a dialog for the user to set their goals
                dismiss();
                HabitsSetGoalsDialogFragment setGoalsDialog = HabitsSetGoalsDialogFragment.newInstance(getArguments().getString(argHabitDesc));
                setGoalsDialog.show(getParentFragmentManager(), "SetGoalsDialog");

                // notify the activity that the habit has been updated by calling onHabitUpdated
                if (getActivity() instanceof OnHabitUpdatedListener) {
                    ((OnHabitUpdatedListener) getActivity()).onHabitUpdated(habit);
                }
            }
        });

        return view;
    }
}
