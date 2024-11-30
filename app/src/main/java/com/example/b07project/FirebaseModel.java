package com.example.b07project;

import android.util.Log;

import java.util.Collections;
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

    public Goal(String name, int prog, String impact, String category, String habitDesc, String impactDesc, int image) {
        this.name = name;
        this.prog = prog;
        this.impact = impact;
        this.category = category;
        this.habitDesc = habitDesc;
        this.impactDesc = impactDesc;
        this.image = image;
    }
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


    public String getName() {
        return name;
    }

    public long getProg() {
        return prog;
    }

    public String getImpact() {
        return impact;
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

    public int getImage() {
        return image;
    }
}


/**
 * How the Database will work
 * Habitslist - (this is a list of habits the user can choose to pick up)
 *      - Transportation
 *          - Transportation habits
 *      - Food
 *      - Energy
 *      - Consumption
 * userhabits - (This will contain the users, what habits they have chosen and their progress)
 *      - User John Doe
 *          - Consumption
 *              - Use less paper
 *                  - 80%
 *          - Transportation
 *              - Don't use the jetpack
 *                  - 20%
 */

/**
 * GeneralServerCommunicator - The Model portion of the Habit Suggestion Module
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
     * Get the list of goals available on the Database
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
     * Get the list of goals the user is currently working on
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
     * Set a new goal for the user
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
     * Get the progress of a goal that the user is working on
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
     * Set the progress of a goal that the user is working on
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
