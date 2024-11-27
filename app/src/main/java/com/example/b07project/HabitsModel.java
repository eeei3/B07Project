package com.example.b07project;

/**
 * HabitsModel class containing fields and methods needed to set up and modify the RecyclerView adapter
 *
 */
public class HabitsModel {
    String habitName;
    int image;
    String impact;
    String category;
    String habitDesc;
    String impactDesc;

    /**
     * Constructor for HabitsModel.
     *
     * @param habitName name of the habit.
     * @param image resource ID of the habit image.
     * @param impact level of impact of the habit.
     * @param category category of the habit.
     * @param habitDesc description of the habit.
     * @param impactDesc description of the impact of the habit.
     */
    public HabitsModel(String habitName, int image, String impact, String category, String habitDesc, String impactDesc) {
        this.habitName = habitName;
        this.image = image;
        this.impact = impact;
        this.category = category;
        this.habitDesc = habitDesc;
        this.impactDesc = impactDesc;
    }

    /**
     * Method to return the field habitName of the calling HabitsModel object.
     *
     * @return habit name of the calling HabitsModel object.
     */
    public String getHabitName() {
        return habitName;
    }

    /**
     * Method to return the field impact of the calling HabitsModel object.
     *
     * @return level of impact of the calling HabitsModel object.
     */
    public String getImpact() {
        return impact;
    }

    /**
     * Method to return the field image of the calling HabitsModel object.
     *
     * @return resource ID of the image habit of the calling HabitsModel object.
     */
    public int getImage() {
        return image;
    }

    /**
     * Method to return the field category of the calling HabitsModel object.
     *
     * @return category of the calling HabitsModel object.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Method to return the field habitDesc of the calling HabitsModel object.
     *
     * @return habit description of the calling HabitsModel object.
     */
    public String getHabitDesc() {
        return habitDesc;
    }

    /**
     * Method to return the field impactDesc of the calling HabitsModel object.
     *
     * @return impact description of the calling HabitsModel object.
     */
    public String getImpactDesc() {
        return impactDesc;
    }
}
