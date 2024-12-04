package com.example.b07project;

/**
 * EcoTrackerCalculations class is responsible for calculating the carbon emissions
 * associated with various activities that contribute to a user's carbon footprint.
 * The class provides methods to calculate emissions from:
 * - Transportation: including vehicle usage, public transport, cycling, and flights.
 * - Food consumption: based on the type and number of meals.
 * - Shopping: emissions from clothing, electronics, and other purchases.
 *
 */
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

    /**
     * Constructor for EcoTrackerCalculations that initializes all of its fields
     *
     * @param vehicleType the type of vehicle used
     * @param distanceDriven the distance driven
     * @param transportType the mode of transport
     * @param cyclingTime the time cycled
     * @param numFlights the number of flights taken
     * @param flightType the type of flight taken
     * @param mealType the type of meals had
     * @param numServings the number of servings had
     * @param numClothes the number of clothes bought
     * @param numDevices the number of devices bought
     * @param numPurchases the number of other purchases
     * @param deviceType the type of device used/bought
     * @param purchaseType the type of purchase
     */
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


    /**
     * calculateTransportationEmissions calculates the total emissions from modes of
     * transportations taken by the user.
     *
     * @return the calculated emissions from modes of transportations taken by user.
     */
    public double calculateTransportationEmissions() {
        double res = 0.0;
        res += calculateVehicleEmission();
        res += calculatePublicTransportEmission();
        res += calculateFlightEmission();
        return res;
    }

    /**
     * calculateFoodEmissions calculates the food emissions produced by the user.
     *
     * @return the calculated food emissions from the user.
     */
    public double calculateFoodEmissions() {
        double res = 0.0;
        res += calculateMealEmission();
        return res;
    }


    /**
     * calculateShoppingEmissions calculates the shopping emissions produced by the user.
     *
     * @return the calculated shopping emissions from the user.
     */
    public double calculateShoppingEmissions() {
        double res = 0.0;
        res += calculateClothesEmission();
        res += calculateElectronicsEmission();
        res += calculateOtherPurchasesEmission();
        return res;
    }

    /**
     * calculateShoppingEmissions calculates the vehicle emissions produced by the user.
     *
     * @return the calculated vehicle emissions from the user.
     */
    private double calculateVehicleEmission() {
        double emission;

        switch (vehicleType) {
            case "Gasoline":
                emission = 0.24 * distanceDriven;
                break;
            case "Diesel":
                emission = 0.27 * distanceDriven;
                break;
            case "Electric":
                emission = 0.05 * distanceDriven;
                break;
            case "Hybrid":
                emission = 0.16 * distanceDriven;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the meal emissions produced by the user.
     *
     * @return the calculated meal emissions from the user.
     */
    private double calculateMealEmission() {
        double emission;

        switch (mealType) {
            case "Beef":
                emission = 10 * numServings;
                break;
            case "Pork":
                emission = 5 * numServings;
                break;
            case "Chicken":
                emission = 3 * numServings;
                break;
            case "Fish":
                emission = 2 * numServings;
                break;
            case "Plant Based":
                emission = numServings;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the public transport emissions produced by the user.
     *
     * @return the calculated public transport emissions from the user.
     */
    private double calculatePublicTransportEmission() {
        double emission;

        switch (transportType) {
            case "Bus":
                emission = 0.18 * cyclingTime;
                break;
            case "Train":
                emission = 0.04 * cyclingTime;
                break;
            case "Subway":
                emission = 0.03 * cyclingTime;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the flight emissions produced by the user.
     *
     * @return the calculated flight emissions from the user.
     */
    private double calculateFlightEmission() {
        double emission;

        switch (flightType) {
            case "Short-haul(less than 1500 km)":
                emission = numFlights * 225;
                break;
            case "Long-haul(more than 1500 km)":
                emission = numFlights * 825;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }


    /**
     * calculateShoppingEmissions calculates the clothes emissions produced by the user.
     *
     * @return the calculated clothes emissions from the user.
     */
    private double calculateClothesEmission(){
        if (numClothes >= 1) {
            return numClothes * 25;
        }
        return 0.0;
    }

    /**
     * calculateShoppingEmissions calculates the electronics emissions produced by the user.
     *
     * @return the calculated electronics emissions from the user.
     */
    private double calculateElectronicsEmission() {
        double emission;

        switch (deviceType) {
            case "Phone":
                emission = 250 * numDevices;
                break;
            case "Laptop":
                emission = 400 * numDevices;
                break;
            case "TV":
                emission = 600 * numDevices;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }

    /**
     * calculateShoppingEmissions calculates the other purchases' emissions produced by the user.
     *
     * @return the calculated other purchases' emissions from the user.     */
    private double calculateOtherPurchasesEmission() {
        double emission;

        switch (purchaseType) {
            case "Furniture":
                emission = 250 * numPurchases;
                break;
            case "Appliance":
                emission = 800 * numPurchases;
                break;
            case "Book":
                emission = 5 * numPurchases;
                break;
            default:
                emission = 0.0;
                break;
        }
        return emission;
    }
}
