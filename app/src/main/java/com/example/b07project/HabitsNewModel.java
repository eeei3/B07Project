package com.example.b07project;

public class HabitsNewModel {
    String habitName;
    String impact;
    int image;

    public HabitsNewModel(String habitName, int image, String impact) {
        this.habitName = habitName;
        this.image = image;
        this.impact = impact;
    }


    public String getHabitName() {
        return habitName;
    }

    public String getImpact() {
        return impact;
    }

    public int getImage() {
        return image;
    }
}
