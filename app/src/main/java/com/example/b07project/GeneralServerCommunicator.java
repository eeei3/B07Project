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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

class Goal {
    String name;
    int prog;
    HashSet<String> types;

    public Goal(String name) {
        this.name = name;
        this.prog = 0;
        this.types = new HashSet<String>();
    }

    public Goal(String name, int prog) {
        this.name = name;
        this.prog = prog;
        this.types = new HashSet<String>();
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

final public class GeneralServerCommunicator {
    private static GeneralServerCommunicator serverCommunicator;
    private FirebaseDatabase db;
    private DatabaseReference dbworker;
    private String userid;

    ServerCommunicator.ModelPresenterPipe listener;

    /**
     * A listener exclusively for Model-Presenter communications
     */
    public interface ModelPresenterPipe {
        void onObjectReady(SuccessListener betweener);
    }

    private GeneralServerCommunicator(String userid) {
        this.userid = userid;
        db = FirebaseDatabase.getInstance("https://b07project-b43b0-default-rtdb.firebaseio.com/");
        dbworker = db.getInstance().getReference();
    }

    // Tell's Model which listener from Presenter to notify when operation is completed
    public void setModelPipe(ServerCommunicator.ModelPresenterPipe listener) {
        this.listener = listener;
    }

    /**
     * Get the list of goals available on the Database
     */
    void getListGoals(SuccessListener watcher) {
        Query listgoals = dbworker.child("habitslist");
        listgoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashSet<String> res = new HashSet<String>();
                for (DataSnapshot goals: snapshot.getChildren()) {
                    res.add(goals.getKey());
                }
                watcher.setSuccess(true);
                watcher.setListgoals(res);
                listener.onObjectReady(watcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                watcher.setSuccess(false);
                listener.onObjectReady(watcher);
            }
        });
    }

    /**
     * Get the list of goals the user is currently working on
     */
    void getGoals(SuccessListener watcher) {
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
                        Goal temp = new Goal(goals.getKey());
                        res.add(temp);
                    }
                }
                watcher.setSuccess(true);
                watcher.setUsergoals(res);
                listener.onObjectReady(watcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                watcher.setSuccess(false);
                listener.onObjectReady(watcher);
            }
        });
    }

    /**
     * Set a new goal for the user
     */
    void setGoals(String goalname, SuccessListener watcher) {
        dbworker.child("users").child(userid).setValue(new Goal(goalname))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            watcher.setSuccess(true);
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
    void getProg(String goal, SuccessListener watcher) {
        dbworker.child("users").child(userid).child("habits").child(goal)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Fetch the values we want
                            watcher.value = Integer.parseInt((String) task.getResult().getValue());
                            watcher.setSuccess(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            // Return an error
                            watcher.setSuccess(false);
                            listener.onObjectReady(watcher);
                        }
                    }
                });
    }

    /**
     * Set the progress of a goal that the user is working on
     */
    void setProg(String goalname, int prog, SuccessListener watcher) {
        dbworker.child("users").child(userid).child(goalname).setValue(prog)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            watcher.setSuccess(true);
                            listener.onObjectReady(watcher);
                        }
                        else {
                            listener.onObjectReady(watcher);
                        }
                    }
                });
    }

    public static GeneralServerCommunicator createInstance(String userid) {
        if (serverCommunicator == null)
            serverCommunicator = new GeneralServerCommunicator(userid);
        return serverCommunicator;
    }

}
