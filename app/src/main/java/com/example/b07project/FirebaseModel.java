package com.example.b07project;

import android.util.Log;
import java.util.HashSet;
import java.util.LinkedHashSet;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * FirebaseModel - Module in charge of communications with Firebase
 */
public class FirebaseModel extends Model {
    public static int counter = 0;
    final private DatabaseReference dbworker;
    private final String userid;
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
     */
    public FirebaseModel() {
        this.userid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance("https://b07project-b43b0-default-rtdb.firebaseio.com/");
        dbworker = FirebaseDatabase.getInstance().getReference();
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
                    HashSet<Goal> res = new HashSet<>();
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
                LinkedHashSet<Goal> res = new LinkedHashSet<>();
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
        for (int i = 0 ; i < habitsImages.length; i++) {
            if (goal.image == habitsImages[i]) {
                image_index = i;
                break;
            }
        }
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("prog").setValue(0)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("aim").setValue(goal.aim)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("habitDesc").setValue(goal.habitDesc)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("category").setValue(goal.category)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("image").setValue(image_index)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("impact").setValue(goal.impact)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
        dbworker.child("users").child(userid).child("habits").child(goal.name).child("impactDesc").setValue(goal.impactDesc)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (completed[0] >= 7) {
                            watcher.setResult(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            completed[0] = completed[0] + 1;
                        }
                    }
                    else {
                        listener.onObjectReady(watcher);
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
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Fetch the values we want
                        watcher.values.add(Integer.parseInt(String.valueOf(task.getResult().child("prog").getValue())));
                        watcher.values.add(Integer.parseInt(String.valueOf(task.getResult().child("aim").getValue())));
                        watcher.setResult(true);
                        listener.onObjectReady(watcher);
                    }
                    else {
                        // Return an error
                        watcher.setResult(false);
                        listener.onObjectReady(watcher);
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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        watcher.setResult(true);
                        listener.onObjectReady(watcher);
                    }
                    else {
                        listener.onObjectReady(watcher);
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
                .setValue(null).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        watcher.setResult(true);
                        listener.onObjectReady(watcher);
                    }
                    else {
                        listener.onObjectReady(watcher);
                    }
                });
    }
}
