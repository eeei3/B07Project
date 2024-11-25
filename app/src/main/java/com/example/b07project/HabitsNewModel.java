package com.example.b07project;

public class HabitsNewModel {
    String habitName;
    int image;
    String impact;
    String category;

    public HabitsNewModel(String habitName, int image, String impact, String category) {
        this.habitName = habitName;
        this.image = image;
        this.impact = impact;
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
