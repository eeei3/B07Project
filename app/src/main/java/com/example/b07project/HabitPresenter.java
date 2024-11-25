package com.example.b07project;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * Class representing the Presenter portion of the Habit Suggestion Module
 */
public class HabitPresenter {
    GeneralServerCommunicator model;
//    HabitPresenter.PresenterViewPipe listener;
    //View view;

    /**
     * PresenterViewPipe - Interface representing the communication pipe (listener) between
     * Presenter and View
     */
//    public interface PresenterViewPipe {
//        void onObjectReady(SuccessListener listener);
//    }

    /**
     * HabitPresenter - Default Constructor that sets userid and creates an instance of
     * GeneralServerCommunicator to permit communication between Presenter and View
     */
    public HabitPresenter() {
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        this.model = GeneralServerCommunicator.createInstance(
                String.valueOf(mauth.getCurrentUser()));
    }

    /**
     * setViewPipe - Set's setViewPipe to permit the Model to communicate with Presenter
     * @param listener
     */
//    public void setViewPipe(HabitPresenter.PresenterViewPipe listener) {
//        this.listener = listener;
//    }

    /**
     * searchByName -  Lets the user search potential goals by it's name
     * @param filter - The filter the user wishes to apply to the list
     * @param pv - How we notify the View that the results are ready
     */
    public void searchByName(String filter, SuccessListener pv) {
        // mp is the listener that we use to tell if the Model operation succeeded
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                pv.setSuccess(true);
                for (Goal g: betweener.usergoals) {
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
    public void searchByCategory(String filter, SuccessListener pv) {
        // mp is the listener that we use to tell if the Model operation succeeded
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                pv.setSuccess(true);
                for (Goal g: betweener.usergoals) {
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
    public void searchByImpact(String filter, SuccessListener pv) {
        // mp is the listener that we use to tell if the Model operation succeeded
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                pv.setSuccess(true);
                for (Goal g: betweener.usergoals) {
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
    public void userAddGoal(String goal, SuccessListener pv) {
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                pv.setSuccess(mp.success);
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
    public void userGetGoal(String filter, SuccessListener pv) {
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                if (mp.success) {
                    for (Goal g : mp.usergoals) {
                        if ((filter != null) || (Objects.equals(filter, g.name))) {
                            pv.usergoals.add(g);
                        }
                    }
                }
                pv.setSuccess(mp.success);
//                listener.onObjectReady(pv);

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
    public void userSetProg(String goal, int prog, SuccessListener pv) {
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                pv.setSuccess(mp.success);
//                listener.onObjectReady(pv);
            }
        });
        this.model.setProg(goal, prog, mp);
    }

    /**
     * userGetProg, fetches the user's progress towards a goal
     * @param goal - The goal whose progress the user wishes to fetch
     * @param pv - How we notify the View that the results are ready
     */
    public void userGetProg(String goal, SuccessListener pv) {
        SuccessListener mp = new SuccessListener();
        this.model.setModelPipe(new ServerCommunicator.ModelPresenterPipe() {
            @Override
            public void onObjectReady(SuccessListener betweener) {
                if (mp.success) {
                    pv.setSuccess(mp.success);
                    pv.setValue(mp.value);
                }
                else {
                    pv.setSuccess(mp.success);
                }
//                listener.onObjectReady(pv);
            }
        });
        this.model.getProg(goal, mp);
    }
}
