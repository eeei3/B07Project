package com.example.b07project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<HabitsNewModel> habitsModels = new ArrayList<>();

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


        setUpHabitModels();
        HabitsNewAdapter adapter = new HabitsNewAdapter(this, habitsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setUpHabitModels(){
        String[] habitsNames = getResources().getStringArray(R.array.habits_list);
        String[] habitsImpacts = getResources().getStringArray(R.array.habits_impacts);

        for (int i = 0; i < habitsNames.length; i++) {
            habitsModels.add(new HabitsNewModel(habitsNames[i], habitsImages[i], habitsImpacts[i]));
        }
    }
}