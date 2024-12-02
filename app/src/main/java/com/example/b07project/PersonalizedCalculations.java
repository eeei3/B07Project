package com.example.b07project;

import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedHashSet;


/**
 * PersonalizedCalculations - Recommends the user new habits based on their previous activity.
 */
public class PersonalizedCalculations {
    LinkedHashSet<Goal> goals;
    LinkedHashSet<Goal> available;
    LinkedHashSet<String> goalsNames;
    double transportScore;
    double foodScore;
    double consumptionScore;
    double electricScore;
    HashMap<Double, String> keys;
    ArrayList<Double> scores;


    /**
     * PersonalizedCalculations - Default constructor for PersonalizedCalculations
     */
    public PersonalizedCalculations() {
        transportScore = 0;
        foodScore = 0;
        consumptionScore = 0;
        electricScore = 0;
        keys = new HashMap<>();
        scores = new ArrayList<>();
    }

    /**
     * prepare - A method that is run to setup the goalsNames field
     */
    public void prepare() {
        LinkedHashSet<String> temp_goals = new LinkedHashSet<>();
        for (Goal g: goals) {
            temp_goals.add(g.name);
        }
        for (Goal g: available) {
            if (temp_goals.contains(g.name)) {
                goalsNames.add(g.name);
            }
        }
    }

    /**
     * calculateScore - Calculates the user's score in each category, higher score means the user
     * has been more active in that category.
     */
    public void calculateScore() {
        for (Goal g: goals) {
            if (g.category.equals("Transport")) {
                switch (g.impact) {
                    case "High":
                        transportScore += 2.5;
                        break;
                    case "Medium":
                        transportScore += 1;
                        break;
                    case "Low":
                        transportScore += 0.6;
                        break;
                }
            }
            if (g.category.equals("Consumption")) {
                switch (g.impact) {
                    case "High":
                        consumptionScore += 2.5;
                        break;
                    case "Medium":
                        consumptionScore += 1;
                        break;
                    case "Low":
                        consumptionScore += 0.6;
                        break;
                }
            }
            if (g.category.equals("Energy")) {
                switch (g.impact) {
                    case "High":
                        electricScore += 2.5;
                        break;
                    case "Medium":
                        electricScore += 1;
                        break;
                    case "Low":
                        electricScore += 0.6;
                        break;
                }
            }
            if (g.category.equals("Food")) {
                switch (g.impact) {
                    case "High":
                        foodScore += 2.5;
                        break;
                    case "Medium":
                        foodScore += 1;
                        break;
                    case "Low":
                        foodScore += 0.6;
                        break;
                }
            }
        }
        this.keys = new HashMap<>();
        this.scores = new ArrayList<>();
        keys.put(foodScore, "Food");
        scores.add(foodScore);
        keys.put(electricScore, "Energy");
        scores.add(electricScore);
        keys.put(consumptionScore, "Consumption");
        scores.add(consumptionScore);
        keys.put(transportScore, "Transportation");
        scores.add(transportScore);
        Collections.sort(scores);
    }

    /**
     * calculateRecommendation - Recommends the user a habit from a category they have been doing
     * most
     * @return - The goal recommended to the user
     */
    public Goal calculateRecommendation() {
        for (int i = 3; i >= 0; i--) {
            for (Goal g : available) {
                if (g.category.equals(keys.get(scores.get(i)))) {
                    return g;
                }
            }
        }
        return null;
    }

    /**
     * calculateNew - Recommends the user a habit from a category that the user has done the least
     * in
     * @return - The goal recommended to the user
     */
    public Goal calculateNew() {
        for (int i = 0; i <= 3; i++) {
            for (Goal g : available) {
                if (g.category.equals(keys.get(scores.get(i)))) {
                    return g;
                }
            }
        }
        return null;
    }
}
