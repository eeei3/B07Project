package com.example.b07project;

import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedHashSet;


public class PersonalizedCalculations {
    LinkedHashSet<Goal> goals;
    LinkedHashSet<Goal> available;
    LinkedHashSet<String> goalsNames;
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

    private void calculateScore() {
        float res = 0.0F;
        for (Goal g: goals) {
            if (g.category.equals("Transport")) {
                if (g.impact.equals("High")) {
                    transportScore += 2.5;
                }
                else if (g.impact.equals("Medium")) {
                    transportScore += 1;
                }
                else if (g.impact.equals("Low")) {
                    transportScore += 0.6;
                }
            }
            if (g.category.equals("Consumption")) {
                if (g.impact.equals("High")) {
                    consumptionScore += 2.5;
                }
                else if (g.impact.equals("Medium")) {
                    consumptionScore += 1;
                }
                else if (g.impact.equals("Low")) {
                    consumptionScore += 0.6;
                }
            }
            if (g.category.equals("Energy")) {
                if (g.impact.equals("High")) {
                    electricScore += 2.5;
                }
                else if (g.impact.equals("Medium")) {
                    electricScore += 1;
                }
                else if (g.impact.equals("Low")) {
                    electricScore += 0.6;
                }
            }
            if (g.category.equals("Food")) {
                if (g.impact.equals("High")) {
                    foodScore += 2.5;
                }
                else if (g.impact.equals("Medium")) {
                    foodScore += 1;
                }
                else if (g.impact.equals("Low")) {
                    foodScore += 0.6;
                }
            }
        }
    }

    public Goal calculateRecommendation() {
        HashMap<Double, String> keys = new HashMap<>();
        ArrayList<Double> scores = new ArrayList<>();
        keys.put(foodScore, "Food");
        scores.add(foodScore);
        keys.put(electricScore, "Electric");
        scores.add(electricScore);
        keys.put(consumptionScore, "Consumption");
        scores.add(consumptionScore);
        keys.put(transportScore, "Transport");
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
        keys.put(foodScore, "Food");
        scores.add(foodScore);
        keys.put(electricScore, "Electric");
        scores.add(electricScore);
        keys.put(consumptionScore, "Consumption");
        scores.add(consumptionScore);
        keys.put(transportScore, "Transport");
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
