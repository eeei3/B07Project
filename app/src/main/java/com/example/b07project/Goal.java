package com.example.b07project;


import android.util.Log;
import java.util.HashSet;
import java.util.LinkedHashSet;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


// TOMMY notes - changed the field HashSet<String> types to just String category.
// - On 2nd thought, im combining Goal and HabitsModel cuz they practically have the same purpose

/**
 * Goal - Class representing the Goal object for storing information about user goals
 */
class Goal {
    String name;
    int prog;
    int aim;
    String impact;
    String category;
    String habitDesc;
    String impactDesc;
    int image;

    /**
     * Goal - Constructor that takes the goal's name and progress, intended for reading user goals
     * @param name - Name of the goal the program is fetching
     * @param prog - Progress of the goal
     */
    public Goal(String name, int prog) {
        this.name = name;
        this.prog = prog;
    }

    /**
     * Goal - Constructor for representing habits from the list of available habits on Firebase
     * @param name - Habit's name
     * @param prog - Habit's progress
     * @param impact - Level of impact
     * @param category - Category of impact
     * @param habitDesc - Description of habit
     * @param impactDesc - Description of impact
     * @param image - Image's index in hashset
     */
    public Goal(String name, int prog, String impact, String category, String habitDesc, String impactDesc, int image) {
        this.name = name;
        this.prog = prog;
        this.impact = impact;
        this.category = category;
        this.habitDesc = habitDesc;
        this.impactDesc = impactDesc;
        this.image = image;
    }

    /**
     * Goal - Constructor for representing habits that the program is holding in memory
     * @param name - Habit's name
     * @param aim - Habit's duration goal
     * @param prog - Habit's progress
     * @param impact - Level of impact
     * @param category - Category of impact
     * @param habitDesc - Description of habit
     * @param impactDesc - Description of impact
     * @param image - Image's index in hashset
     */
    public Goal(String name, int prog, int aim, String impact, String category, String habitDesc, String impactDesc, int image) {
        this.name = name;
        this.prog = prog;
        this.aim = aim;
        this.impact = impact;
        this.category = category;
        this.habitDesc = habitDesc;
        this.impactDesc = impactDesc;
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check if both objects are the same
        if (obj == null || getClass() != obj.getClass()) return false;  // Ensure it's the same class
        Goal goal = (Goal) obj;  // Cast the object to a Goal instance
        return name.equals(goal.name) && category.equals(goal.category);  // Compare name and category
    }

    // Override hashCode() to generate a consistent hash code based on name and category
    @Override
    public int hashCode() {
        return name.hashCode() + category.hashCode();  // Generate hash based on name and category
    }

    /**
     * getName - return the habit's name
     * @return - return the habit's name
     */
    public String getName() {
        return name;
    }

    /**
     * getName - return the habit's progress
     * @return - return the habit's progress
     */
    public int getProg() {
        return prog;
    }

    /**
     * getName - return the habit's impact
     * @return - return the habit's impact
     */
    public String getImpact() {
        return impact;
    }

    /**
     * getName - return the habit's category
     * @return - return the habit's category
     */
    public String getCategory() {
        return category;
    }

    /**
     * getName - return the habit's description
     * @return - return the habit's description
     */
    public String getHabitDesc() {
        return habitDesc;
    }

    /**
     * getName - return the habit's impact description
     * @return - return the habit's impact description
     */
    public String getImpactDesc() {
        return impactDesc;
    }

    /**
     * getImage - return the habit's image index
     * @return - return the habit's image index
     */
    public int getImage() {
        return image;
    }
}

