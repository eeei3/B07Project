package com.example.b07project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;


/**
 * Class representing the Presenter portion of the Habit Suggestion Module
 */
public class HabitPresenter {
//    FirebaseModel model;
    HabitsMenu view;
    UserHabitsProgressDialogFragment progdiafrag;
    FirebaseAuth mauth;

    HabitsSetGoalsDialogFragment godiagrag;
    Model.ModelPresenterPipe listener;
    HashSet<Goal> suggested;


    // TOMMY Notes - added field HabitsMenu view and initialize the view in constructor
    /**
     * HabitPresenter - Default Constructor that sets userid and creates an instance of
     * GeneralServerCommunicator to permit communication between Presenter and View
     */
    public HabitPresenter(HabitsMenu view) {
        this.view = view;
        this.mauth = FirebaseAuth.getInstance();
//        this.model = new FirebaseModel(
//                String.valueOf(mauth.getCurrentUser()));
        suggested  = new HashSet<>();
    }

    /**
     * personalSuggestions - Method for suggesting to the user two new habits, one is a habit from
     * a category that the user has been doing most, and one that the user has been doing the least
     */
    public void personalSuggestions() {
        AsyncDBComms mp = new AsyncDBComms();
        LinkedHashSet<Goal> temp = new LinkedHashSet<>();
        LinkedHashSet<Goal> temp1 = new LinkedHashSet<>();
        FirebaseModel model = new FirebaseModel(
                String.valueOf(mauth.getCurrentUser()));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            int toggle = 0;
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                if (toggle == 0) {
                    temp.addAll(plug.usergoals);
                    toggle += 1;
                }
                else {
                    temp1.addAll(plug.listgoals);
                    PersonalizedCalculations calculator = new PersonalizedCalculations();
                    if (temp.size() > temp1.size()) {
                        calculator.goals = temp1;
                        calculator.available = temp;
                    }
                    else {
                        calculator.goals = temp;
                        calculator.available = temp1;
                    }
                    calculator.prepare();

                    suggested.add(calculator.calculateNew());
                    suggested.add(calculator.calculateRecommendation());
                }
//                listener.onObjectReady(pv);
            }
        });
        model.getListGoals(mp);
        model.getGoals(mp);

    }

    /**
     * userAddGoal updates the goals that the user has active
     * @param goal - The goal the user wishes to work towards
     */
    public void userAddGoal(Goal goal, String aim) {
        FirebaseModel.counter = 0;
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel(
                String.valueOf(mauth.getCurrentUser()));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                FirebaseModel.counter = 1;
            }
        });
        goal.aim = Integer.parseInt(aim);
        model.setGoals(goal, mp);
    }

    // TOMMY notes
    // this method should be responsible for populating userGoals in the view
    /**
     * userGetGoal gets the list of goals that the user has active
     *
     */
    public void userGetGoal() {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel(
                String.valueOf(mauth.getCurrentUser()));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                if (mp.res) {
                    view.userGoals.addAll(mp.usergoals);
                }
//                model.setModelPipe(null);
            }
        });
        model.getGoals(mp);
    }

    /**
     * userSetProg, updates the user's progress towards a goal
     */
    public void userSetProg(Goal habit) {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel(
                String.valueOf(mauth.getCurrentUser()));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                Log.e("fuck3", String.valueOf(habit.prog));
                habit.prog = habit.prog + 1;
                Log.e("fuck4", String.valueOf(habit.prog));
                AsyncDBComms plug = (AsyncDBComms) betweener;
//                view.progress = prog;
//                UserHabitsProgressDialogFragment.logActivity.setEnabled(true);
//                userGetProg(goal);
                progdiafrag.setTextNumDays(habit.prog);
                progdiafrag.setProgressBar(habit.prog);
//                model.setModelPipe(null);
            }
        });
        Log.e("fuck1", String.valueOf(habit.prog));
        model.setProg(habit.name, habit.prog + 1, mp);
        Log.e("fuck2", String.valueOf(habit.prog));
    }

    // TOMMY Notes - will need to change a few other functions/classes to also yield the aim (i.e. the no. of days to complete)
    /**
     * userGetProg, fetches the user's progress towards a goal
     * @param goal - The goal whose progress the user wishes to fetch
     */
    public void userGetProg(String goal) {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel(
                String.valueOf(mauth.getCurrentUser()));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                if (plug.res) {
//                    Log.e("fuck", String.valueOf(plug.res));
                    // update the progress and aim in the view for the specified goal
                    view.progress = (int) plug.values.get(0);
                    view.aim = (int) plug.values.get(1);
//                    progdiafrag.setProgressBar();
//                    progdiafrag.setProgressBar();
                    progdiafrag.setTextNumDays((int)plug.values.get(0));
//                    model.setModelPipe(null);
                }
                else {
                }
            }
        });
        model.getProg(goal, mp);
    }

    /**
     * userDeleteGoal - Method for the user to delete a goal from the database
     * @param goal - The habit the user wishes to delete
     * @param con - The Context for the method to make its Toast
     * @param toggle - The context in which the method is called, either being
     *               a. A habit has been completed
     *               b. The user wants to drop a habit
     */
    public void userDeleteGoal(String goal, Context con, int toggle) {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel(
                String.valueOf(mauth.getUid()));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                if (plug.res) {
                    if (toggle == 0) {
                        Toast.makeText(con, "Goal Finished!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (toggle == 1) {
                        HabitsMenu.userGoals.remove(goal);
                        HabitsMenu.setUserArrayForAdapter();
                        Toast.makeText(con, "Habit Removed", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                }
            }
        });
        model.deleteGoal(goal, mp);
    }

    /**
     * getAllHabits - Get all habits available (not just the ones the user has active)
     */
    public void getAllHabits() {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel( (String.valueOf(mauth.getUid())));
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                HabitsMenu.allGoals.addAll(plug.listgoals);
                HabitsMenu.setOriginalArrayForAdapter();
            }
        });
        model.getListGoals(mp);
    }
}
