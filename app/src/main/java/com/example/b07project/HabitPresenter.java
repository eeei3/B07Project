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
 *
 * @see HabitsMenu
 * @see UserHabitsProgressDialogFragment
 * @see Goal
 */
public class HabitPresenter {
//    FirebaseModel model;
    HabitsMenu view;
    UserHabitsProgressDialogFragment progdiafrag;
    FirebaseAuth mauth;
    HashSet<Goal> suggested;

    /**
     * HabitPresenter - Default Constructor that sets userid and creates an instance of
     * GeneralServerCommunicator to permit communication between Presenter and View
     */
    public HabitPresenter(HabitsMenu view) {
        this.view = view;
        this.mauth = FirebaseAuth.getInstance();
        suggested  = new HashSet<>();
    }

    /**
     * personalSuggestions - Method for suggesting to the user two new habits, one is a habit from
     * a category that the user has been doing most, and one that the user has been doing the least
     */
    public void personalSuggestions() {
        if (HabitsMenu.userGoals == null || HabitsMenu.userGoals.isEmpty()) {
            System.out.println("userGoals is null");
        }
        if (HabitsMenu.allGoals == null || HabitsMenu.allGoals.isEmpty()) {
            System.out.println("allGoals is null");
        }

        LinkedHashSet<Goal> temp = new LinkedHashSet<>(HabitsMenu.userGoals);
        LinkedHashSet<Goal> temp1 = new LinkedHashSet<>();
        PersonalizedCalculations calculator = new PersonalizedCalculations();

        if (HabitsMenu.userGoals.contains(null)) {
            System.out.println("userGoals contains null");
        }
        if (HabitsMenu.allGoals.contains(null)) {
            System.out.println("allGoals contains null");
        }

        calculator.goals = temp;
        for (int i = 0; i < HabitsMenu.allGoals.size(); i++) {
            if (!HabitsMenu.userGoals.contains(HabitsMenu.allGoals.get(i))) {
                temp1.add(HabitsMenu.allGoals.get(i));
            }
        }
        calculator.available = temp1;
        System.out.println(temp1.size());
        System.out.println(temp.size());
        calculator.prepare();
        calculator.calculateScore();

        System.out.println("HELLO");
        Goal recomd = calculator.calculateRecommendation();
        Goal newToTry = calculator.calculateNew();

        if (recomd == null && newToTry == null) {
            return;
        } else if (recomd != null && newToTry != null) {
            if (recomd.equals(newToTry)) {
                HabitsMenu.recommendedGoals.add(recomd);
            } else {
                HabitsMenu.recommendedGoals.add(recomd);
                HabitsMenu.recommendedGoals.add(newToTry);
            }
        } else HabitsMenu.recommendedGoals.add(Objects.requireNonNullElse(recomd, newToTry));

        System.out.println(HabitsMenu.recommendedGoals.size());

        if (calculator.calculateRecommendation() == null) {
            System.out.println("Recommended NULL 1");
        }
        if (calculator.calculateNew() == null) {
            System.out.println("Recommended NULL 2");
        }
    }

    /**
     * userAddGoal updates the goals that the user has active
     * @param goal - The goal the user wishes to work towards
     */
    public void userAddGoal(Goal goal, String aim) {
        FirebaseModel.counter = 0;
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel();
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                FirebaseModel.counter = 1;
            }
        });
        goal.aim = Integer.parseInt(aim);
        model.setGoals(goal, mp);
    }

    /**
     * userGetGoal gets the list of goals that the user has active
     *
     */
    public void userGetGoal() {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel();
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                if (mp.res) {
                    HabitsMenu.userGoals.addAll(mp.usergoals);
                }
            }
        });
        model.getGoals(mp);
    }

    /**
     * userSetProg, updates the user's progress towards a goal
     */
    public void userSetProg(Goal habit) {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel();
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                Log.e("fuck3", String.valueOf(habit.prog));
                HabitsMenu.progress++;
                Log.e("fuck4", String.valueOf(habit.prog));
                progdiafrag.setTextNumDays(HabitsMenu.progress);
                progdiafrag.setProgressBar(HabitsMenu.progress);
            }
        });
        Log.e("fuck1", String.valueOf(habit.prog));
        model.setProg(habit.name, HabitsMenu.progress + 1, mp);
        Log.e("fuck2", String.valueOf(habit.prog));
    }

    /**
     * userGetProg, fetches the user's progress towards a goal
     * @param goal - The goal whose progress the user wishes to fetch
     */
    public void userGetProg(String goal) {
        AsyncDBComms mp = new AsyncDBComms();
        FirebaseModel model = new FirebaseModel();
        model.setModelPipe(new Model.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                if (plug.res) {
                    // update the progress and aim in the view for the specified goal
                    HabitsMenu.progress = (int) plug.values.get(0);
                    HabitsMenu.aim = (int) plug.values.get(1);
                    progdiafrag.setProgressBar(HabitsMenu.progress);
                    progdiafrag.setTextNumDays(HabitsMenu.progress);
                }
                else {
                    HabitsMenu.progress = 0;
                    HabitsMenu.aim = 1;
                    progdiafrag.setProgressBar(HabitsMenu.progress);
                    progdiafrag.setTextNumDays(HabitsMenu.progress);
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
        FirebaseModel model = new FirebaseModel();
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
        FirebaseModel model = new FirebaseModel();
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
