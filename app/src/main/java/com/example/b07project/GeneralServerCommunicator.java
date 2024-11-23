package com.example.b07project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        dbworker.child("habitslist").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    // Fetch the values we want

                }
                else {
                    // Return an error

                }
            }
        });
    }

    /**
     * Get the list of goals the user is currently working on
     */
    void getGoals(SuccessListener watcher) {
        dbworker.child("userhabits").child(userid).child("habits").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    // Fetch the values we want

                }
                else {
                    // Return an error

                }
            }
        });
    }

    /**
     * Set a new goal for the user
     */
    void setGoals(SuccessListener watcher) {
//        dbworker.child("habitslist").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Fetch the values we want
//
//                }
//                else {
//                    // Return an error
//
//                }
//            }
//        });
    }

    /**
     * Get the progress of a goal that the user is working on
     */
    void getProg(String goal, SuccessListener watcher) {
        dbworker.child("userhabits").child(userid).child("habits").child(goal)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    // Fetch the values we want

                }
                else {
                    // Return an error

                }
            }
        });
    }

    /**
     * Set the progress of a goal that the user is working on
     */
    void setProg(String goal, SuccessListener watcher) {
//        dbworker.child("userhabits").child(userid).child("habits").child(goal)
//                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Fetch the values we want
//
//                }
//                else {
//                    // Return an error
//
//                }
//            }
//        });
    }

    public static GeneralServerCommunicator createInstance(String userid) {
        if (serverCommunicator == null)
            serverCommunicator = new GeneralServerCommunicator(userid);
        return serverCommunicator;
    }

}
