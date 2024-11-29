package com.example.b07project;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * Class representing the Presenter portion of the Habit Suggestion Module
 */
public class HabitPresenter {
    FirebaseModel model;
    HabitsMenu view;

    Model.ModelPresenterPipe listener;

    // TOMMY Notes - added field HabitsMenu view and initialize the view in constructor
    /**
     * HabitPresenter - Default Constructor that sets userid and creates an instance of
     * GeneralServerCommunicator to permit communication between Presenter and View
     */
    public HabitPresenter(HabitsMenu view) {
        this.view = view;
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        this.model = FirebaseModel.createInstance(
                String.valueOf(mauth.getCurrentUser()));
    }

    /**
     * searchByName -  Lets the user search potential goals by it's name
     * @param filter - The filter the user wishes to apply to the list
     */
    public void searchByName(String filter) {
        // mp is the listener that we use to tell if the Model operation succeeded
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                for (Goal g: plug.usergoals) {
                    if (Objects.equals(g.name, filter)) {

                    }
                }
//                listener.onObjectReady(pv);
            }
        });
        this.model.getListGoals(mp);
    }

    /**
     * searchByCategory lets the user search potential goals by it's category
     * @param filter - The filter the user wishes to apply to the list
     */
    public void searchByCategory(String filter) {
        // mp is the listener that we use to tell if the Model operation succeeded
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                for (Goal g: plug.usergoals) {
                    if (g.category.contains(filter)) {
                    }
                }
//                listener.onObjectReady(pv);
            }
        });
        this.model.getListGoals(mp);
    }

    /**
     * searchByImpact lets the user search potential goals by it's impact
     * @param filter - The filter the user wishes to apply to the list
     */
    public void searchByImpact(String filter) {
        // mp is the listener that we use to tell if the Model operation succeeded
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                for (Goal g: plug.usergoals) {
                }
//                listener.onObjectReady(pv);
            }
        });
        this.model.getListGoals(mp);
    }

    /**
     * userAddGoal updates the goals that the user has active
     * @param goal - The goal the user wishes to work towards
     */
    public void userAddGoal(String goal) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
            }
        });
        this.model.setGoals(goal, mp);
    }


    // TOMMY notes
    // this method should be responsible for populating userGoals in the view
    /**
     * userGetGoal gets the list of goals that the user has active
     *
     */
    public void userGetGoal() {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                if (mp.res) {
                    view.userGoals.addAll(mp.usergoals);
                }
            }
        });
        this.model.getGoals(mp);
    }

    /**
     * userSetProg, updates the user's progress towards a goal
     * @param goal - The goal the user wishes to update
     * @param prog - The user's new progress
     */
    public void userSetProg(String goal, int prog) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
            }
        });
        this.model.setProg(goal, prog, mp);
    }

    // TOMMY Notes - will need to change a few other functions/classes to also yield the aim (i.e. the no. of days to complete)
    /**
     * userGetProg, fetches the user's progress towards a goal
     * @param goal - The goal whose progress the user wishes to fetch
     */
    public void userGetProg(String goal) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                if (plug.res) {
                    // update the progress and aim in the view for the specified goal

                    view.progress = (int) plug.value;
                    // view.aim = plug.???
                }
                else {
                }
            }
        });
        this.model.getProg(goal, mp);
    }
}
