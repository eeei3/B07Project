package com.example.b07project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<HabitsModel> habitsModels = new ArrayList<>();
    public static ArrayList<HabitsModel> filteredHabitsModels = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    public static HabitsAdapter adapter;

    int[] habitsImages = {R.drawable.habits_bringownbag, R.drawable.habits_cycling,
            R.drawable.habits_lessshoping, R.drawable.habits_limitmeat,
            R.drawable.habits_localfood, R.drawable.habits_publictrans,
            R.drawable.habits_recycle, R.drawable.habits_turnlightsoff,
            R.drawable.habits_walking, R.drawable.habits_washcold};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habits_main_page);

        RecyclerView recyclerView = findViewById(R.id.habit_list);
        ChipGroup filterChips = findViewById(R.id.filter_chip_group);
        ImageView filterTool = findViewById(R.id.filter_icon);
        ImageView searchTool = findViewById(R.id.search_icon);

        setUpHabitModels();
        adapter = new HabitsAdapter(this, habitsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
}