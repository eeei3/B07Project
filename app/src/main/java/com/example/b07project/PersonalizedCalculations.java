package com.example.b07project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;


public class PersonalizedCalculations {
    HashSet<Goal> goals;
    HashSet<Goal> available;
    HashSet<String> goalsNames;
    double transportScore;
    double foodScore;
    double consumptionScore;
    double electricScore;


    public PersonalizedCalculations() {
        transportScore = 0;
        foodScore = 0;
        consumptionScore = 0;
        electricScore = 0;
    }

    public void prepare() {
        HashSet<String> temp_goals = new HashSet<>();
        for (Goal g: goals) {
            temp_goals.add(g.name);
        }
        for (Goal g: available) {
            if (temp_goals.contains(g.name)) {
                goalsNames.add(g.name);
            }
        }
    }

    private void calculateScore() {
        float res = 0.0F;
        for (Goal g: goals) {
            if (g.category.equals("transport")) {
                if (g.impact.equals("high")) {
                    transportScore += 2.5;
                }
                else if (g.impact.equals("medium")) {
                    transportScore += 1;
                }
                else if (g.impact.equals("low")) {
                    transportScore += 0.6;
                }
            }
            if (g.category.equals("consumption")) {
                if (g.impact.equals("high")) {
                    consumptionScore += 2.5;
                }
                else if (g.impact.equals("medium")) {
                    consumptionScore += 1;
                }
                else if (g.impact.equals("low")) {
                    consumptionScore += 0.6;
                }
            }
            if (g.category.equals("energy")) {
                if (g.impact.equals("high")) {
                    electricScore += 2.5;
                }
                else if (g.impact.equals("medium")) {
                    electricScore += 1;
                }
                else if (g.impact.equals("low")) {
                    electricScore += 0.6;
                }
            }
            if (g.category.equals("food")) {
                if (g.impact.equals("high")) {
                    foodScore += 2.5;
                }
                else if (g.impact.equals("medium")) {
                    foodScore += 1;
                }
                else if (g.impact.equals("low")) {
                    foodScore += 0.6;
                }
            }
        }
    }

    public Goal calculateRecommendation() {
        HashMap<Double, String> keys = new HashMap<>();
        ArrayList<Double> scores = new ArrayList<>();
        keys.put(foodScore, "food");
        scores.add(foodScore);
        keys.put(electricScore, "electric");
        scores.add(electricScore);
        keys.put(consumptionScore, "consumption");
        scores.add(consumptionScore);
        keys.put(transportScore, "transport");
        scores.add(transportScore);
        Collections.sort(scores);
        for (int i = 3; i >= 0; i--) {
            for (Goal g : available) {
                if (g.category.equals(keys.get(scores.get(i)))) {
                    return g;
                }
            }
        }
        return null;
    }

    public Goal calculateNew() {
        HashMap<Double, String> keys = new HashMap<>();
        ArrayList<Double> scores = new ArrayList<>();
        keys.put(foodScore, "food");
        scores.add(foodScore);
        keys.put(electricScore, "electric");
        scores.add(electricScore);
        keys.put(consumptionScore, "consumption");
        scores.add(consumptionScore);
        keys.put(transportScore, "transport");
        scores.add(transportScore);
        Collections.sort(scores);
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
