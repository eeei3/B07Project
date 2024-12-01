package com.example.b07project;

public class EcoTrackerCalculations {

    String vehicleType;
    double distanceDriven;
    String transportType;
    double cyclingTime;
    int numFlights;
    String flightType;
    String mealType;
    int numServings;
    int numClothes;
    int numDevices;
    int numPurchases;
    String deviceType;
    String purchaseType;

    public EcoTrackerCalculations(String vehicleType,
                                  double distanceDriven,
                                  String transportType,
                                  double cyclingTime,
                                  int numFlights,
                                  String flightType,
                                  String mealType,
                                  int numServings,
                                  int numClothes,
                                  int numDevices,
                                  int numPurchases,
                                  String deviceType,
                                  String purchaseType){
        this.vehicleType = vehicleType;
        this.distanceDriven = distanceDriven;
        this.transportType = transportType;
        this.cyclingTime = cyclingTime;
        this.numFlights = numFlights;
        this.flightType = flightType;
        this.mealType = mealType;
        this.numServings = numServings;
        this.numClothes = numClothes;
        this.numDevices = numDevices;
        this.numPurchases = numPurchases;
        this.deviceType = deviceType;
        this.purchaseType = purchaseType;

    }


    // Should probably be moved too
    public double calculateTransportationEmissions() {
        double res = 0.0;
        res += calculateVehicleEmission();
        res += calculatePublicTransportEmission();
        res += calculateCyclingEmission();
        res += calculateFlightEmission();
        return res;
    }

    // This should probably be moved
    public double calculateFoodEmissions() {
        double res = 0.0;
        res += calculateMealEmission();
        return res;
    }

    // THis should probably be moved
    public double calculateShoppingEmissions() {
        double res = 0.0;
        res += calculateClothesEmission();
        res += calculateElectronicsEmission();
        res += calculateOtherPurchasesEmission();
        return res;
    }

    //copy pasted from logactivitiesactivity: emission calculations - will refactor later on
    private double calculateVehicleEmission() {
        double emission;

        if ("Gasoline".equals(vehicleType)) {
            emission = 0.24 * distanceDriven;
        } else if ("Diesel".equals(vehicleType)) {
            emission = 0.27 * distanceDriven;
        } else if ("Electric".equals(vehicleType)) {
            emission = 0.05 * distanceDriven;
        } else if ("Hybrid".equals(vehicleType)) {
            emission = 0.16 * distanceDriven;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculateMealEmission() {
        double emission = 0.0;

        if (mealType.equals("Beef")) {
            emission = 10 * numServings;
        } else if (mealType.equals("Pork")) {
            emission = 5 * numServings;
        } else if (mealType.equals("Chicken")) {
            emission = 3* numServings;
        } else if (mealType.equals("Fish")) {
            emission = 2 * numServings;
        } else if (mealType.equals("Plant Based")) {
            emission = numServings;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculatePublicTransportEmission() {

        double emission = 0.0;

        // Calculate emission using if-else logic
        if (transportType.equals("Bus")) {
            emission = 0.18 * cyclingTime;
        } else if (transportType.equals("Train")) {
            emission = 0.04 * cyclingTime;
        } else if (transportType.equals("Subway")) {
            emission = 0.03 * cyclingTime;
        } else {
            emission = 0.0;
        }
        return emission;
    }

    private double calculateCyclingEmission() {
        double emission = 0.0;
        return emission;
    }

    private double calculateFlightEmission() {
        double emission = 0.0;

        if (flightType.equals("Short-haul(less than 1500 km)")) {
            emission = numFlights * 225;
        } else if (flightType.equals("Long-haul(more than 1500 km)")) {
            emission = numFlights * 825;
        } else {
            emission = 0.0;
        }
        return emission;
    }



    private double calculateClothesEmission(){
        double emission = 0.0;

        if (numClothes >= 1) {
            return numClothes * 25;
        }
        return emission;
    }

    private double calculateElectronicsEmission() {
        double emission = 0.0;

        if (deviceType.equals("Phone")) {
            emission = 250 * numDevices;
        } else if (deviceType.equals("Laptop")) {
            emission = 400 * numDevices;
        } else if (deviceType.equals("TV")) {
            emission = 600 * numDevices;
        } else {
            emission = 0.0;
        }

        return emission;
    }

    private double calculateOtherPurchasesEmission() {
        double emission = 0.0;
        if (purchaseType.equals("Furniture")) {
            emission = 250 * numPurchases;
        } else if (purchaseType.equals("Appliance")) {
            emission = 800 * numPurchases;
        } else if (purchaseType.equals("Book")) {
            emission = 5 * numPurchases;
        } else {
            emission = 0.0;
        }
        return emission;
    }
}
