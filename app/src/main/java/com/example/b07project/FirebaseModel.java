package com.example.b07project;

import android.util.Log;
import java.util.HashSet;
import java.util.LinkedHashSet;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
     * Goal - Constructor that takes the goal's name, intended for creating new user goal
     *
     * @param name  - Name of the goal the user is starting
     * @param category - Categories the goal belongs to
     */
    public Goal(String name, String category) {
        this.name = name;
        this.prog = 0;
        this.category = category;
    }

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


/**
 * FirebaseModel - Module in charge of communications with Firebase
 */
public class FirebaseModel extends Model {
    public static int counter = 0;
    private static FirebaseModel serverCommunicator;
    final private FirebaseDatabase db;
    final private DatabaseReference dbworker;
    private String userid;
    ModelPresenterPipe listener;
    int[] habitsImages = {R.drawable.habits_bringownbag, R.drawable.habits_cycling,
            R.drawable.habits_lessshoping, R.drawable.habits_limitmeat,
            R.drawable.habits_localfood, R.drawable.habits_publictrans,
            R.drawable.habits_recycle, R.drawable.habits_turnlightsoff,
            R.drawable.habits_walking, R.drawable.habits_washcold};

    /**
     * setModelPipe - To permit communications between Model and Presenter
     * @param listener - How we notify the Presenter that the results are ready
     */
    public void setModelPipe(ModelPresenterPipe listener) {
        this.listener = listener;
    }

    /**
     * GeneralServerCommunicator - Constructor, creates a reference to the Firebase Database,
     * follows Singleton SOLID design
     * @param userid - the id of the user
     */
    public FirebaseModel(String userid) {
//        this.userid = userid;  //This is the production line
        this.userid = "1111111"; // This is for testing
        db = FirebaseDatabase.getInstance("https://b07project-b43b0-default-rtdb.firebaseio.com/");
        dbworker = db.getInstance().getReference();
    }

    /**
     * Get the list of habits available on the Database
     * @param watcher - Listener to notify HabitPresenter about result
     */
    void getListGoals(AsyncDBComms watcher) {
        Query listgoals = dbworker.child("habitslist");
        listgoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (counter == 1) {
                    HashSet<Goal> res = new HashSet<Goal>();
                    for (DataSnapshot goals : snapshot.getChildren()) {
                        res.add(new Goal(goals.getKey(),
                                0,
                                String.valueOf(goals.child("impact").getValue()),
                                String.valueOf(goals.child("category").getValue()),
                                String.valueOf(goals.child("habitDesc").getValue()),
                                String.valueOf(goals.child("impactDesc").getValue()),
                                habitsImages[Integer.parseInt(String.valueOf(goals.child("image").getValue()))]
                        ));
                    }
                    counter = 0;
                    watcher.setResult(true);
                    watcher.setListgoals(res);
                    listener.onObjectReady(watcher);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                watcher.setResult(false);
                listener.onObjectReady(watcher);
            }
        });
    }

    /**
     * getGoals - Get the list of habit the user is currently working on
     * @param watcher - Listener to notify HabitPresenter about result
     */
    void getGoals(AsyncDBComms watcher) {
        Query usergoals = dbworker.child("users").child(userid).child("habits");
        usergoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedHashSet<Goal> res = new LinkedHashSet<Goal>();
                for (DataSnapshot goals: snapshot.getChildren()) {
                    try {
                        Log.e("fuck90", String.valueOf(goals.child("image").getValue()));
                        try {
                            res.add(new Goal(goals.getKey(),
                                    0,
                                    0,
                                    String.valueOf(goals.child("impact").getValue()),
                                    String.valueOf(goals.child("category").getValue()),
                                    String.valueOf(goals.child("habitDesc").getValue()),
                                    String.valueOf(goals.child("impactDesc").getValue()),
                                    habitsImages[Integer.parseInt(String.valueOf(goals.child("image").getValue()))]
                            ));
                        }
                        catch (NumberFormatException e) {
                            res.add(new Goal(goals.getKey(),
                                    0,
                                    0,
                                    String.valueOf(goals.child("impact").getValue()),
                                    String.valueOf(goals.child("category").getValue()),
                                    String.valueOf(goals.child("habitDesc").getValue()),
                                    String.valueOf(goals.child("impactDesc").getValue()),
                                    habitsImages[0]
                            ));
                        }
                    }
                    catch (NullPointerException ex) {
                        Goal temp = new Goal(goals.getKey(), 0);
                        res.add(temp);
                    }
                }
                HabitsMenu.userGoals.clear();
                watcher.setResult(true);
                watcher.setUsergoals(res);
                listener.onObjectReady(watcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                watcher.setResult(false);
                listener.onObjectReady(watcher);
            }
        });
    }

    /**
     * setGoals - Set a new habit for the user
     * @param goal - habit the user wants to pick up
     * @param watcher - Listener to notify HabitPresenter about result
     */
    void setGoals(Goal goal, AsyncDBComms watcher) {
        final int[] completed = {0};
        int image_index = 0;
//        Goal goalobj = new Goal(goal, 0);
        for (int i = 0 ; i < habitsImages.length; i++) {
            if (goal.image == habitsImages[i]) {
                image_index = i;
                break;
            }
        }
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("prog").setValue(0)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("aim").setValue(goal.aim)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("habitDesc").setValue(goal.habitDesc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("category").setValue(goal.category)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("image").setValue(image_index)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("impact").setValue(goal.impact)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("impactDesc").setValue(goal.impactDesc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (completed[0] >= 7) {
                                watcher.setResult(true);
                                listener.onObjectReady(watcher);
//                                counter = 1;
                            }
                            else {
                                completed[0] = completed[0] + 1;
                            }
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
    }

    /**
     * getProg - Get the progress of a habit that the user is working on
     * @param goal - Habit the user wants to get progress of
     * @param watcher - Listener to notify HabitPresenter about result
     */
    void getProg(String goal, AsyncDBComms watcher) {
        dbworker.child("users").child(userid).child("habits").child(goal)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Fetch the values we want
                            watcher.values.add(Integer.parseInt(String.valueOf(task.getResult().child("prog").getValue())));
                            watcher.values.add(Integer.parseInt(String.valueOf(task.getResult().child("aim").getValue())));
//                            watcher.value = (long) task.getResult().getValue();
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            // Return an error
                            watcher.setResult(false);
                            listener.onObjectReady(watcher);
                        }
                    }
                });
    }

    /**
     * setProg - Set the progress of a habit that the user is working on
     * @param goalname - Habit name
     * @param prog - New progress
     * @param watcher - Listener to notify HabitPresenter about result
     */
    void setProg(String goalname, int prog, AsyncDBComms watcher) {
        dbworker.child("users").child(userid).child("habits").child(goalname).child("prog").setValue(prog)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
    }

    /**
     * deleteGoal - Delete an active habit from database
     * @param goalname - Habit being deleted
     * @param watcher - Listener to notify HabitPresenter about result
     */
    void deleteGoal(String goalname, AsyncDBComms watcher) {
        dbworker.child("users").child(userid).child("habits").child(goalname)
                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    watcher.setResult(true);
                    listener.onObjectReady(watcher);
                }
                else {
                    listener.onObjectReady(watcher);
                }
            }
        });
    }

}
