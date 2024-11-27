package com.example.b07project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;

/**
 * HabitsMenu class contains fields and methods related to the Habits activity
 */
public class HabitsMenu extends AppCompatActivity implements OnHabitUpdatedListener{
    public static ArrayList<HabitsModel> habitsModels = new ArrayList<>();
    public static ArrayList<HabitsModel> filteredHabitsModels = new ArrayList<>();
    public static ArrayList<HabitsModel> userHabitsModels = new ArrayList<>();
    public static final boolean[] currentMenu = {false}; // false for all habits, true for user's habits, final bcs android studio complains

    @SuppressLint("StaticFieldLeak")
    private static HabitsAdapter adapter;

    int[] habitsImages = {R.drawable.habits_bringownbag, R.drawable.habits_cycling,
            R.drawable.habits_lessshoping, R.drawable.habits_limitmeat,
            R.drawable.habits_localfood, R.drawable.habits_publictrans,
            R.drawable.habits_recycle, R.drawable.habits_turnlightsoff,
            R.drawable.habits_walking, R.drawable.habits_washcold};


    /** To be ran when HabitsMenu is created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habits_main_page);

        // get all interactive components from habits_main_page.xml
        Button allHabits = findViewById(R.id.allHabitsButton);
        Button usersHabits = findViewById(R.id.userHabitsButton);
        RecyclerView recyclerView = findViewById(R.id.habit_list);
        ChipGroup filterChips = findViewById(R.id.filter_chip_group);
        ImageView filterTool = findViewById(R.id.filter_icon);
        SearchView searchTool = findViewById(R.id.search_tool);

        // set default look for some interactive components
        searchTool.clearFocus();
        int planetzeColour3 = ContextCompat.getColor(this, R.color.planetze_colour_3);
        int planetzeColour4 = ContextCompat.getColor(this, R.color.planetze_colour_4);
        allHabits.setBackgroundColor(planetzeColour3);
        usersHabits.setBackgroundColor(planetzeColour4);

        // set up the RecyclerView
        setUpHabitModels();
        adapter = new HabitsAdapter(this, habitsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // define behaviour for when the "Your Habits" button is clicked
        usersHabits.setOnClickListener(v -> {
            // switch to the User's Habits page if not already on it
            if (!currentMenu[0]) {
                filterChips.clearCheck();
                usersHabits.setBackgroundColor(planetzeColour3);
                allHabits.setBackgroundColor(planetzeColour4);
                currentMenu[0] = true;
                setUserArrayForAdapter();
            }
        });

        // define behaviour for when the "All Habits" button is clicked
        allHabits.setOnClickListener(v -> {
            // switch to the All Habits page if not already in it
            if (currentMenu[0]) {
                filterChips.clearCheck();
                allHabits.setBackgroundColor(planetzeColour3);
                usersHabits.setBackgroundColor(planetzeColour4);
                currentMenu[0] = false;
                setOriginalArrayForAdapter();
            }
        });

        // define behaviour for when one or more categorical buttons is/are clicked
        filterChips.setOnCheckedStateChangeListener((group, checkedIds) -> {
            ArrayList<String> checkedCategories = new ArrayList<>();
            // get the categories chose
            for (Integer chipId : checkedIds) {
                Chip chip = findViewById(chipId);
                if (chip != null) {
                    String chipTxt = chip.getText().toString().trim();
                    checkedCategories.add(chipTxt);
                }
            }
            // filter the RecyclerView based on categories selected
            filterHabits(checkedCategories, new ArrayList<>());
        });

        // define behaviour for when the filter icon is clicked
        filterTool.setOnClickListener(v -> {
            // reset the RecyclerView (i.e. removing any Chips selected)
            if (currentMenu[0]) {
                setUserArrayForAdapter();
            } else {
                setOriginalArrayForAdapter();
            }
            filterChips.clearCheck();
            // load dialog to display filter options
            HabitsFilterDialogFragment filterDialog = new HabitsFilterDialogFragment();
            filterDialog.show(getSupportFragmentManager(), "filter_dialog");
        });


        // define behaviour for when there's interactions with the search bar
        searchTool.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Called when the user submits the query. This could be due to a key press on the
             * keyboard or due to pressing a submit button.
             * The listener can override the standard behavior by returning true
             * to indicate that it has handled the submit request. Otherwise return false to
             * let the SearchView handle the submission by launching any associated intent.
             *
             * @param query the query text that is to be submitted
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText the new content of the query text field.
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                // Note: uncheck all Chips and previous filters (no idea how to implement otherwise)
                filterChips.clearCheck();
                filterBySearch(newText);
                return true;
            }
        });
    }

    /**
     * Method to set up the set of all HabitsModel.
     *
     */
    private void setUpHabitModels(){
        String[] habitsNames = getResources().getStringArray(R.array.habits_list);
        String[] habitsCategories = getResources().getStringArray(R.array.habits_categories);
        String[] habitsImpacts = getResources().getStringArray(R.array.habits_impacts);
        String[] habitsDesc = getResources().getStringArray(R.array.habits_desc);
        String[] habitsImpactsDesc = getResources().getStringArray(R.array.habits_impact_desc);

        for (int i = 0; i < habitsNames.length; i++) {
            habitsModels.add(new HabitsModel(habitsNames[i], habitsImages[i],
                    habitsImpacts[i], habitsCategories[i],
                    habitsDesc[i], habitsImpactsDesc[i]));
        }
    }

    /**
     * Method to change the RecyclerView adapter's dataset to filteredHabitsModels.
     *
     */
    private static void setFilteredArrayForAdapter() {
        adapter.setHabitsModels(filteredHabitsModels);
    }

    /**
     * Method to change the RecyclerView adapter's dataset to usersHabitsModels.
     *
     */
    private static void setUserArrayForAdapter() {
        adapter.setHabitsModels(userHabitsModels);
    }

    /**
     * Method to change the RecyclerView adapter's dataset to habitsModels (the full set of habits).
     *
     */
    private static void setOriginalArrayForAdapter() {
        adapter.setHabitsModels(habitsModels);
    }

    /**
     * Method to filter the dataset for the RecyclerView adapter via categories and/or
     * levels of impacts selected.
     *
     * @param checkedCategories the set of all categories selected.
     * @param checkedImpacts the set of all levels of impacts selected.
     */
    public static void filterHabits(ArrayList<String> checkedCategories,
                                            ArrayList<String> checkedImpacts) {
        filteredHabitsModels.clear();
        ArrayList<HabitsModel> toBeFilteredHabitsModels = currentMenu[0] ? userHabitsModels : habitsModels;

        if (checkedCategories.isEmpty() && checkedImpacts.isEmpty()) {
            // if no filters are selected, simply restore the original dataset
            if (currentMenu[0]) {
                setUserArrayForAdapter();
            } else {
                setOriginalArrayForAdapter();
            }
            return;
        } else if (checkedImpacts.isEmpty()) {
            // if no levels of impacts are selected, filter based on categories
            for (int i = 0; i < toBeFilteredHabitsModels.size(); i++) {
                if (checkedCategories.contains(toBeFilteredHabitsModels.get(i).getCategory())) {
                    filteredHabitsModels.add(toBeFilteredHabitsModels.get(i));
                }
            }
        } else if (checkedCategories.isEmpty()) {
            // if no categories selected, filter based on levels of impacts
            for (int i = 0; i < toBeFilteredHabitsModels.size(); i++) {
                if (checkedImpacts.contains(toBeFilteredHabitsModels.get(i).getImpact())) {
                    filteredHabitsModels.add(toBeFilteredHabitsModels.get(i));
                }
            }
        } else {
            // if both are selected, keep only the habits with a checked category AND a checked level of impact
            for (int i = 0; i < toBeFilteredHabitsModels.size(); i++) {
                if (checkedCategories.contains(toBeFilteredHabitsModels.get(i).getCategory())
                        && checkedImpacts.contains(toBeFilteredHabitsModels.get(i).getImpact())) {
                    filteredHabitsModels.add(toBeFilteredHabitsModels.get(i));
                }
            }
        }
        setFilteredArrayForAdapter();
    }

    /**
     * Method to filter the dataset for the RecyclerView's adapter based on user's query
     * in the search bar.
     *
     * @param newText text queried by the user.
     */
    public void filterBySearch(String newText) {
        filteredHabitsModels.clear();
        ArrayList<HabitsModel> toBeFilteredHabitsModels = currentMenu[0] ? userHabitsModels : habitsModels;

        // keep the habit if either the its name, desc, or impact desc contains the queried text newText
        for (HabitsModel habit : toBeFilteredHabitsModels) {
            if (habit.getHabitName().toLowerCase().contains(newText.toLowerCase())
                    || habit.getHabitDesc().toLowerCase().contains(newText.toLowerCase())
                    || habit.getImpactDesc().toLowerCase().contains(newText.toLowerCase())) {
                filteredHabitsModels.add(habit);
            }
        }
        setFilteredArrayForAdapter();
    }

    /**
     * Method to notify the adapter if the habit is adopted by a user and thus to
     * have its background colour change to signify that adoption.
     *
     * @param habit the HabitsModel to be considered for a background colour update.
     */
    @Override
    public void onHabitUpdated(HabitsModel habit) {
        int position = habitsModels.indexOf(habit);
        if (position != -1) {
            // Update the background colour for the corresponding habit
            adapter.notifyItemChanged(position);
        }
    }
}