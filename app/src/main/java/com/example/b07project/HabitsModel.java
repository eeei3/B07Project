package com.example.b07project;

public class HabitsModel {
    String habitName;
    int image;
    boolean switchChecked;

    public HabitsModel(String habitName, int image, boolean switchChecked) {
        this.habitName = habitName;
        this.image = image;
        this.switchChecked = switchChecked;
    }

    public String getHabitName() {
        return habitName;
    }

    public int getImage() {
        return image;
    }

    public boolean isSwitchChecked() {
        return switchChecked;
    }

    public void setSwitchChecked(boolean switchChecked) {
        this.switchChecked = switchChecked;
    }
}
