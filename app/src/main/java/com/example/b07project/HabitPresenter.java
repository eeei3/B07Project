package com.example.b07project;

public class HabitPresenter {
    GeneralServerCommunicator presenterToModel;
    String userid;

    public HabitPresenter(String userid) {
        this.userid = userid;
        presenterToModel = GeneralServerCommunicator.createInstance(userid);
    }

    public void searchByName(String filter) {

    }

    public void searchByCategory(String filter) {

    }

    public void searchByImpact(String filter) {

    }

    public void userAddGoal(String goal) {

    }

    public void userGetGoal(String goal) {

    }

    public void userSetProg(String goal) {

    }

    public void userGetProg(String goal) {

    }
}
