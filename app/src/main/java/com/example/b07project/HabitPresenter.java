package com.example.b07project;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * Class representing the Presenter portion of the Habit Suggestion Module
 */
public class HabitPresenter {
    FirebaseModel model;

    /**
     * HabitPresenter - Default Constructor that sets userid and creates an instance of
     * GeneralServerCommunicator to permit communication between Presenter and View
     */
    public HabitPresenter() {
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        this.model = FirebaseModel.createInstance(
                String.valueOf(mauth.getCurrentUser()));
    }

    /**
     * searchByName -  Lets the user search potential goals by it's name
     * @param filter - The filter the user wishes to apply to the list
     * @param pv - How we notify the View that the results are ready
     */
    public void searchByName(String filter, AsyncDBComms pv) {
        // mp is the listener that we use to tell if the Model operation succeeded
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                pv.setResult(true);
                for (Goal g: plug.usergoals) {
                    if (Objects.equals(g.name, filter)) {
                        pv.listgoals.add(g.name);
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
     * @param pv - How we notify the View that the results are ready
     */
    public void searchByCategory(String filter, AsyncDBComms pv) {
        // mp is the listener that we use to tell if the Model operation succeeded
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                pv.setResult(true);
                for (Goal g: plug.usergoals) {
                    if (g.types.contains(filter)) {
                        pv.listgoals.add(g.name);
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
     * @param pv - How we notify the View that the results are ready
     */
    public void searchByImpact(String filter, AsyncDBComms pv) {
        // mp is the listener that we use to tell if the Model operation succeeded
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                pv.setResult(true);
                for (Goal g: plug.usergoals) {
                    pv.listgoals.add(g.name);
                }
//                listener.onObjectReady(pv);
            }
        });
        this.model.getListGoals(mp);
    }

    /**
     * userAddGoal updates the goals that the user has active
     * @param goal - The goal the user wishes to work towards
     * @param pv - How we notify the View that the results are ready
     */
    public void userAddGoal(String goal, AsyncDBComms pv) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                pv.setResult(mp.res);
//                listener.onObjectReady(pv);
            }
        });
        this.model.setGoals(goal, mp);
    }

    /**
     * userGetGoal gets the list of goals that the user has active
     * @param filter - If the user chooses to filter the results
     * @param pv - How we notify the View that the results are ready
     */
    public void userGetGoal(String filter, AsyncDBComms pv) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                if (mp.res) {
                    for (Goal g : mp.usergoals) {
                        if ((filter != null) || (Objects.equals(filter, g.name))) {
                            pv.usergoals.add(g);
                        }
                    }
                }
                pv.setResult(mp.res);
            }
        });
        this.model.getGoals(mp);
    }

    /**
     * userSetProg, updates the user's progress towards a goal
     * @param goal - The goal the user wishes to update
     * @param prog - The user's new progress
     * @param pv - How we notify the View that the results are ready
     */
    public void userSetProg(String goal, int prog, AsyncDBComms pv) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                pv.setResult(mp.res);
            }
        });
        this.model.setProg(goal, prog, mp);
    }

    /**
     * userGetProg, fetches the user's progress towards a goal
     * @param goal - The goal whose progress the user wishes to fetch
     * @param pv - How we notify the View that the results are ready
     */
    public void userGetProg(String goal, AsyncDBComms pv) {
        AsyncDBComms mp = new AsyncDBComms();
        this.model.setModelPipe(new FirebaseAuthHandler.ModelPresenterPipe() {
            @Override
            public void onObjectReady(AsyncComms betweener) {
                AsyncDBComms plug = (AsyncDBComms) betweener;
                if (plug.res) {
                    pv.setResult(plug.res);
                    pv.setValue(plug.value);
                }
                else {
                    pv.setResult(plug.res);
                }
            }
        });
        this.model.getProg(goal, mp);
    }
}
