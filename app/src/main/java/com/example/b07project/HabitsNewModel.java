package com.example.b07project;

public class HabitsNewModel {
    String habitName;
    int image;
    String impact;
    String category;
    String habitDesc;
    String impactDesc;

    public HabitsNewModel(String habitName, int image, String impact, String category, String habitDesc, String impactDesc) {
        this.habitName = habitName;
        this.image = image;
        this.impact = impact;
        this.category = category;
        this.habitDesc = habitDesc;
        this.impactDesc = impactDesc;
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

    public String getHabitDesc() {
        return habitDesc;
    }

    public String getImpactDesc() {
        return impactDesc;
    }
}
