package com.example.b07project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;

// Merged HabitsModel into Goal so renamed a lot of fields to better reflect that

/**
 * HabitsMenu class contains fields and methods related to the Habits activity
 *
 * @see HabitsAdapter
 * @see HabitsDetailsDialogFragment
 * @see HabitsSetGoalsDialogFragment
 * @see HabitsFilterDialogFragment
 * @see UserHabitsProgressDialogFragment
 * @see OnHabitUpdatedListener
 */
public class HabitsMenu extends AppCompatActivity implements OnHabitUpdatedListener{
    public static ArrayList<Goal> allGoals = new ArrayList<>();
    public static ArrayList<Goal> filteredGoals = new ArrayList<>();
    public static ArrayList<Goal> userGoals = new ArrayList<>();

    public static ArrayList<Goal> recommendedGoals = new ArrayList<>();
    // !!! NOTE: userHabitsModels needs to be replaced with the user's habits pulled from the firebase
    public static final int[] currentMenu = {0}; // 0 for all habits, 1 for user's habits, 2 for recommended habits

    // tommy - static in order to be reached from various other dialog fragments classes
    public static HabitPresenter presenter;
    public static int progress;

    public static int aim;

    @SuppressLint("StaticFieldLeak")
    public static HabitsAdapter adapter;




    /** To be ran when HabitsMenu is created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseModel.counter = 1;
        setContentView(R.layout.habits_main_page);

        // initialize the presenter
        presenter = new HabitPresenter(this);


        // get all interactive components from habits_main_page.xml
        Button allHabits = findViewById(R.id.allHabitsButton);
        Button usersHabits = findViewById(R.id.userHabitsButton);
        RecyclerView recyclerView = findViewById(R.id.habit_list);
        ChipGroup filterChips = findViewById(R.id.filter_chip_group);
        ImageView filterTool = findViewById(R.id.filter_icon);
        SearchView searchTool = findViewById(R.id.search_tool);
        CardView recommendHabitsCard = findViewById(R.id.card_recomd);

        // set default look for some interactive components
        searchTool.clearFocus();
        int planetzeColour3 = ContextCompat.getColor(this, R.color.planetze_colour_3);
        int planetzeColour4 = ContextCompat.getColor(this, R.color.planetze_colour_4);
        allHabits.setBackgroundColor(planetzeColour3);
        usersHabits.setBackgroundColor(planetzeColour4);

        // set up the RecyclerView and its adapter
        setUpAllGoals();
//        FirebaseModel.counter = 0;
        adapter = new HabitsAdapter(this, allGoals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // tommy - get the user's goals which initializes the field userHabitsModels
        presenter.userGetGoal();

        // define behaviour for when the "Your Habits" button is clicked
        usersHabits.setOnClickListener(v -> {
            // switch to the User's Habits page if not already on it
            if (currentMenu[0] != 1) {
                HabitsMenu.presenter.userGetGoal();
//                Log.e("fuck9999", String.valueOf(userGoals.size()));
                filterChips.clearCheck();
                usersHabits.setBackgroundColor(planetzeColour3);
                allHabits.setBackgroundColor(planetzeColour4);
                recommendHabitsCard.setCardBackgroundColor(planetzeColour4);
                currentMenu[0] = 1;
                setUserArrayForAdapter();
            }
        });

        // define behaviour for when the "All Habits" button is clicked
        allHabits.setOnClickListener(v -> {
            // switch to the All Habits page if not already in it
            if (currentMenu[0] != 0) {
//                userGoals = new ArrayList<>();
                filterChips.clearCheck();
                allHabits.setBackgroundColor(planetzeColour3);
                usersHabits.setBackgroundColor(planetzeColour4);
                recommendHabitsCard.setCardBackgroundColor(planetzeColour4);
                currentMenu[0] = 0;
                setOriginalArrayForAdapter();
            }
        });

        recommendHabitsCard.setOnClickListener(v -> {
            if (currentMenu [0] != 2) {
                filterChips.clearCheck();
                recommendedGoals.clear();
                presenter.personalSuggestions();
                recommendHabitsCard.setCardBackgroundColor(planetzeColour3);
                allHabits.setBackgroundColor(planetzeColour4);
                usersHabits.setBackgroundColor(planetzeColour4);
                currentMenu[0] = 2;
                setRecommendedArrayForAdapter();
            }
        });

        // define behaviour for when one or more categorical buttons is/are clicked
        filterChips.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (currentMenu[0] == 2) {
                currentMenu[0] = 0;
                allHabits.setBackgroundColor(planetzeColour3);
                recommendHabitsCard.setCardBackgroundColor(planetzeColour4);
            }

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
            // exit recommend habits if on it
            // reset the RecyclerView (i.e. removing any Chips selected)
            if (currentMenu[0] == 2) {
                currentMenu[0] = 0;
                allHabits.setBackgroundColor(planetzeColour3);
                recommendHabitsCard.setCardBackgroundColor(planetzeColour4);
                setOriginalArrayForAdapter();
            } else {
                setUserArrayForAdapter();
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
                if (currentMenu[0] == 2) {
                    currentMenu[0] = 0;
                    allHabits.setBackgroundColor(planetzeColour3);
                    recommendHabitsCard.setCardBackgroundColor(planetzeColour4);
                }
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
    private void setUpAllGoals(){
        HabitsMenu.presenter.getAllHabits();
//        String[] habitsNames = getResources().getStringArray(R.array.habits_list);
//        String[] habitsCategories = getResources().getStringArray(R.array.habits_categories);
//        String[] habitsImpacts = getResources().getStringArray(R.array.habits_impacts);
//        String[] habitsDesc = getResources().getStringArray(R.array.habits_desc);
//        String[] habitsImpactsDesc = getResources().getStringArray(R.array.habits_impact_desc);
//
//        for (int i = 0; i < habitsNames.length; i++) {
//            allGoals.add(new Goal(habitsNames[i], 0,
//                    habitsImpacts[i], habitsCategories[i],
//                    habitsDesc[i], habitsImpactsDesc[i], habitsImages[i]));
//        }
    }

    /**
     * Method to change the RecyclerView adapter's dataset to filteredHabitsModels.
     *
     */
    private static void setFilteredArrayForAdapter() {
        adapter.setHabitsModels(filteredGoals);
    }

    /**
     * Method to change the RecyclerView adapter's dataset to usersHabitsModels.
     *
     */
    public static void setUserArrayForAdapter() {
        adapter.setHabitsModels(userGoals);
    }

    /**
     * Method to change the RecyclerView adapter's dataset to habitsModels (the full set of habits).
     *
     */
    static void setOriginalArrayForAdapter() {
        adapter.setHabitsModels(allGoals);
    }

    static void setRecommendedArrayForAdapter() {
        adapter.setHabitsModels(recommendedGoals);
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
        filteredGoals.clear();
        ArrayList<Goal> toBeFilteredHabitsModels;
        if (currentMenu[0] == 0) {
            toBeFilteredHabitsModels = allGoals;
        } else {
            toBeFilteredHabitsModels = userGoals;
        }

        if (checkedCategories.isEmpty() && checkedImpacts.isEmpty()) {
            // if no filters are selected, simply restore the original dataset
            if (currentMenu[0] == 1) {
                setUserArrayForAdapter();
            } else {
                setOriginalArrayForAdapter();
            }
            return;
        } else if (checkedImpacts.isEmpty()) {
            // if no levels of impacts are selected, filter based on categories
            for (int i = 0; i < toBeFilteredHabitsModels.size(); i++) {
                if (checkedCategories.contains(toBeFilteredHabitsModels.get(i).getCategory())) {
                    filteredGoals.add(toBeFilteredHabitsModels.get(i));
                }
            }
        } else if (checkedCategories.isEmpty()) {
            // if no categories selected, filter based on levels of impacts
            for (int i = 0; i < toBeFilteredHabitsModels.size(); i++) {
                if (checkedImpacts.contains(toBeFilteredHabitsModels.get(i).getImpact())) {
                    filteredGoals.add(toBeFilteredHabitsModels.get(i));
                }
            }
        } else {
            // if both are selected, keep only the habits with a checked category AND a checked level of impact
            for (int i = 0; i < toBeFilteredHabitsModels.size(); i++) {
                if (checkedCategories.contains(toBeFilteredHabitsModels.get(i).getCategory())
                        && checkedImpacts.contains(toBeFilteredHabitsModels.get(i).getImpact())) {
                    filteredGoals.add(toBeFilteredHabitsModels.get(i));
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
        filteredGoals.clear();
        ArrayList<Goal> toBeFilteredHabitsModels;
        if (currentMenu[0] == 0) {
            toBeFilteredHabitsModels = allGoals;
        } else {
            toBeFilteredHabitsModels = userGoals;
        }

        // keep the habit if either the its name, desc, or impact desc contains the queried text newText
        for (Goal habit : toBeFilteredHabitsModels) {
            if (habit.getName().toLowerCase().contains(newText.toLowerCase())
                    || habit.getHabitDesc().toLowerCase().contains(newText.toLowerCase())
                    || habit.getImpactDesc().toLowerCase().contains(newText.toLowerCase())) {
                filteredGoals.add(habit);
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
    public void onHabitUpdated(Goal habit) {
        int position1 = allGoals.indexOf(habit);
        int position2 = recommendedGoals.indexOf(habit);
        if (position1 != -1) {
            // Update the background colour for the corresponding habit
            adapter.notifyItemChanged(position1);
            //adapter.notifyDataSetChanged();
        }
        if (position2 != -1) {
            adapter.notifyItemChanged(position2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ecogauge) {
            Intent intent = new Intent(HabitsMenu.this, EcoGauge.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.ecotracker) {
            // Handle ecotracker action
            Intent intent = new Intent(HabitsMenu.this, EcoTrackerHomeFragment.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}