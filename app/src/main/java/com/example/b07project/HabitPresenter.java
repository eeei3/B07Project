package com.example.b07project;

public class HabitPresenter {
    GeneralServerCommunicator presenterToModel;
    String userid;

    public HabitPresenter(String userid) {
        this.userid = userid;
        presenterToModel = GeneralServerCommunicator.createInstance(userid);
    }
}
