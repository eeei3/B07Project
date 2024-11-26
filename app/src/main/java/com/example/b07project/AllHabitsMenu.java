package com.example.b07project;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

public class AllHabitsMenu extends AppCompatActivity {

    public static ArrayList<HabitsModel> habitsModels = new ArrayList<>();
    public static ArrayList<HabitsModel> filteredHabitsModels = new ArrayList<>();
    public static ArrayList<HabitsModel> userHabitsModels = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private static HabitsAdapter adapter;

    int[] habitsImages = {R.drawable.habits_bringownbag, R.drawable.habits_cycling,
            R.drawable.habits_lessshoping, R.drawable.habits_limitmeat,
            R.drawable.habits_localfood, R.drawable.habits_publictrans,
            R.drawable.habits_recycle, R.drawable.habits_turnlightsoff,
            R.drawable.habits_walking, R.drawable.habits_washcold};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habits_main_page);

        Button allHabits = findViewById(R.id.allHabitsButton);
        Button usersHabits = findViewById(R.id.userHabitsButton);
        RecyclerView recyclerView = findViewById(R.id.habit_list);
        ChipGroup filterChips = findViewById(R.id.filter_chip_group);
        ImageView filterTool = findViewById(R.id.filter_icon);
        SearchView searchTool = findViewById(R.id.search_tool);
        searchTool.clearFocus();

        int planetzeColour3 = ContextCompat.getColor(this, R.color.planetze_colour_3);
        int planetzeColour4 = ContextCompat.getColor(this, R.color.planetze_colour_4);
        allHabits.setBackgroundColor(planetzeColour3);
        usersHabits.setBackgroundColor(planetzeColour4);
        final int[] currentState = {0}; // 0 for all habits, 1 for user's habits, final bcs android studio complains

        setUpHabitModels();
        adapter = new HabitsAdapter(this, habitsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        usersHabits.setOnClickListener(v -> {
            if (currentState[0] == 0) {
                    usersHabits.setBackgroundColor(planetzeColour3);
                    allHabits.setBackgroundColor(planetzeColour4);
                    currentState[0] = 1;
                    setUserArrayForAdapter();
                }
        });

        allHabits.setOnClickListener(v -> {
            if (currentState[0] == 1) {
                allHabits.setBackgroundColor(planetzeColour3);
                usersHabits.setBackgroundColor(planetzeColour4);
                currentState[0] = 0;
                setOriginalArrayForAdapter();
            }
        });

        filterChips.setOnCheckedStateChangeListener((group, checkedIds) -> {
            ArrayList<String> checkedCategories = new ArrayList<>();

            for (Integer chipId : checkedIds) {
                Chip chip = findViewById(chipId);
                if (chip != null) {
                    String chipTxt = chip.getText().toString().trim();
                    checkedCategories.add(chipTxt);
                }
            }
            filterHabits(checkedCategories, new ArrayList<>());
        });

        filterTool.setOnClickListener(v -> {
            setOriginalArrayForAdapter();
            HabitsFilterDialogFragment filterDialog = new HabitsFilterDialogFragment();
            filterDialog.show(getSupportFragmentManager(), "filter_dialog");
            filterChips.clearCheck();
        });


        // searching will uncheck all Chips and previous filters (don't know how
        // to implement otherwise)
        searchTool.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterChips.clearCheck();
                filterBySearch(newText);
                return true;
            }
        });
    }

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

    private static void setFilteredArrayForAdapter() {
        adapter.setHabitsModels(filteredHabitsModels);
    }

    private static void setUserArrayForAdapter() {
        adapter.setHabitsModels(userHabitsModels);
    }

    private static void setOriginalArrayForAdapter() {
        adapter.setHabitsModels(habitsModels);
    }

    public static void filterHabits(ArrayList<String> checkedCategories,
                                            ArrayList<String> checkedImpacts) {
        filteredHabitsModels.clear();

        if (checkedCategories.isEmpty() && checkedImpacts.isEmpty()) {
            setOriginalArrayForAdapter();
            return;
        } else if (checkedImpacts.isEmpty()) {
            for (int i = 0; i < habitsModels.size(); i++) {
                if (checkedCategories.contains(habitsModels.get(i).getCategory())) {
                    filteredHabitsModels.add(habitsModels.get(i));
                }
            }
        } else if (checkedCategories.isEmpty()) {
            for (int i = 0; i < habitsModels.size(); i++) {
                if (checkedImpacts.contains(habitsModels.get(i).getImpact())) {
                    filteredHabitsModels.add(habitsModels.get(i));
                }
            }
        } else {
            for (int i = 0; i < habitsModels.size(); i++) {
                if (checkedCategories.contains(habitsModels.get(i).getCategory())
                        && checkedImpacts.contains(habitsModels.get(i).getImpact())) {
                    filteredHabitsModels.add(habitsModels.get(i));
                }
            }
        }
        setFilteredArrayForAdapter();
    }

    public void filterBySearch(String newText) {
        filteredHabitsModels.clear();

        for (HabitsModel habit : habitsModels) {
            if (habit.getHabitName().toLowerCase().contains(newText.toLowerCase())
                    || habit.getHabitDesc().toLowerCase().contains(newText.toLowerCase())) {
                filteredHabitsModels.add(habit);
            }
        }
        setFilteredArrayForAdapter();
    }
}