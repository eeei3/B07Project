package com.example.b07project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<HabitsNewModel> habitsModels = new ArrayList<>();
    ArrayList<HabitsNewModel> filteredHabitsModels = new ArrayList<>();

    int[] habitsImages = {R.drawable.habits_bringownbag, R.drawable.habits_cycling,
            R.drawable.habits_lessshoping, R.drawable.habits_limitmeat,
            R.drawable.habits_localfood, R.drawable.habits_publictrans,
            R.drawable.habits_recycle, R.drawable.habits_turnlightsoff,
            R.drawable.habits_walking, R.drawable.habits_washcold};

    String[] habitsCategories = {"Consumption", "Transportation", "Consumption",
            "Food", "Food", "Transportation", "Energy", "Energy",
            "Transportation", "Energy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habits_main_page);

        RecyclerView recyclerView = findViewById(R.id.habit_list);
        ChipGroup filterChips = findViewById(R.id.filter_chip_group);

        setUpHabitModels();
        HabitsNewAdapter adapter = new HabitsNewAdapter(this, habitsModels);
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

            filterHabitsByChips(checkedCategories, adapter);
        });

    }

    private void setUpHabitModels(){
        String[] habitsNames = getResources().getStringArray(R.array.habits_list);
        String[] habitsImpacts = getResources().getStringArray(R.array.habits_impacts);
        String[] habitsDesc = getResources().getStringArray(R.array.habits_desc);
        String[] habitsImpactsDesc = getResources().getStringArray(R.array.habits_impact_desc);

        for (int i = 0; i < habitsNames.length; i++) {
            habitsModels.add(new HabitsNewModel(habitsNames[i], habitsImages[i],
                    habitsImpacts[i], habitsCategories[i],
                    habitsDesc[i], habitsImpactsDesc[i]));
        }
    }

    private void filterHabitsByChips(ArrayList<String> checkedCategories, HabitsNewAdapter adapter) {
        filteredHabitsModels.clear();

        if (checkedCategories.isEmpty()) {
            adapter.setHabitsModels(habitsModels);
            return;
        }
        for (int i = 0; i < habitsModels.size(); i++) {
            if (checkedCategories.contains(habitsModels.get(i).getCategory())) {
                filteredHabitsModels.add(habitsModels.get(i));
            }
        }

        adapter.setHabitsModels(filteredHabitsModels);
    }
}