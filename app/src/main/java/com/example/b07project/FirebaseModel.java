package com.example.b07project;

import java.util.HashSet;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Goal - Class representing the Goal object for storing information about user goals
 */
class Goal {
    String name;
    int prog;
    HashSet<String> types;

    /**
     * Goal - Constructor that takes the goal's name, intended for creating new user goal
     *
     * @param name  - Name of the goal the user is starting
     * @param types - Categories the goal belongs to
     */
    public Goal(String name, HashSet<String> types) {
        this.name = name;
        this.prog = 0;
        this.types = new HashSet<>();
    }

    /**
     * Goal - Constructor that takes the goal's name and progress, intended for reading user goals
     * @param name - Name of the goal the program is fetching
     * @param prog - Progress of the goal
     */
    public Goal(String name, int prog) {
        this.name = name;
        this.prog = prog;
        this.types = new HashSet<>();
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

final public class FirebaseModel extends Model {
    private static FirebaseModel serverCommunicator;
    final private FirebaseDatabase db;
    final private DatabaseReference dbworker;
    private String userid;

    FirebaseAuthHandler.ModelPresenterPipe listener;

    /**
     * GeneralServerCommunicator - Constructor, creates a reference to the Firebase Database,
     * follows Singleton SOLID design
     * @param userid - the id of the user
     */
    private FirebaseModel(String userid) {
        this.userid = userid;
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
                HashSet<String> res = new HashSet<String>();
                for (DataSnapshot goals: snapshot.getChildren()) {
                    res.add(goals.getKey());
                }
                watcher.setResult(true);
                watcher.setListgoals(res);
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
     * Get the list of goals the user is currently working on
     */
    void getGoals(AsyncDBComms watcher) {
        Query usergoals = dbworker.child("users").child(userid);
        usergoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashSet<Goal> res = new HashSet<Goal>();
                for (DataSnapshot goals: snapshot.getChildren()) {
                    try {
                        int prog = goals.getValue(Integer.class);
                        Goal temp = new Goal(goals.getKey(), prog);
                        res.add(temp);
                    }
                    catch (NullPointerException ex) {
                        Goal temp = new Goal(goals.getKey(), 0);
                        res.add(temp);
                    }
                }
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
    void setGoals(String goalname, AsyncDBComms watcher) {
        dbworker.child("users").child(userid).setValue(new Goal(goalname, 0))
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
     * Get the progress of a goal that the user is working on
     */
    void getProg(String goal, AsyncDBComms watcher) {
        dbworker.child("users").child(userid).child("habits").child(goal)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Fetch the values we want
                            watcher.value = Integer.parseInt((String) task.getResult().getValue());
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
        dbworker.child("users").child(userid).child(goalname).setValue(prog)
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

    public static FirebaseModel createInstance(String userid) {
        if (serverCommunicator == null)
            serverCommunicator = new FirebaseModel(userid);
        return serverCommunicator;
    }

}
