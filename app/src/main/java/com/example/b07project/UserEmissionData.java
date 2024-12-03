package com.example.b07project;

/**
 * UserEmissionData holds both the raw input data provided by the user and the calculated emission
 * data for various activities.
 *
 * The class consists of two main components:
 * - **RawInputs**: Stores the raw data related to the user's activities such as distance driven,
 *   flights taken, meals consumed, and shopping purchases.
 * - **CalculatedEmissions**: Contains the calculated emissions for transportation, food, shopping,
 *   and the total carbon footprint.
 *
 * This class helps in tracking, packaging, and storing user input into the Firebase database
 *
 * @see RawInputs
 * @see CalculatedEmissions
 */

public class UserEmissionData {

    private RawInputs rawInputs;
    private CalculatedEmissions calculatedEmissions;

    public UserEmissionData() {
    }

    public UserEmissionData(RawInputs rawInputs, CalculatedEmissions calculatedEmissions) {
        this.rawInputs = rawInputs;
        this.calculatedEmissions = calculatedEmissions;
    }

    public RawInputs getRawInputs() {
        return rawInputs;
    }
    public void setRawInputs(RawInputs rawInputs) {
        this.rawInputs = rawInputs;
    }
    public CalculatedEmissions getCalculatedEmissions() {
        return calculatedEmissions;
    }
    public void setCalculatedEmissions(CalculatedEmissions calculatedEmissions) {
        this.calculatedEmissions = calculatedEmissions;
    }

    //created a nested class to store the rawinputs
    public static class RawInputs {
        private double distanceDriven;
        private String vehicleType;
        private String transportType;
        private double cyclingTime;
        private int numFlights;
        private String flightType;
        private String mealType;
        private int numServings;
        private int numClothes;
        private String deviceType;
        private int numDevices;
        private String purchaseType;
        private int numOtherPurchases;
        private double billAmount;
        private String billType;


        public RawInputs() {
        }

        //RawInputs constructor with the necessary paramenteres
        public RawInputs(double distanceDriven, String vehicleType, String transportType,
                         double cyclingTime, int numFlights, String flightType, String mealType,
                         int numServings, int numClothes, String deviceType, int numDevices,
                         String purchaseType, int numOtherPurchases, double billAmount, String billType) {
            this.distanceDriven = distanceDriven;
            this.vehicleType = vehicleType;
            this.transportType = transportType;
            this.cyclingTime = cyclingTime;
            this.numFlights = numFlights;
            this.flightType = flightType;
            this.mealType = mealType;
            this.numServings = numServings;
            this.numClothes = numClothes;
            this.deviceType = deviceType;
            this.numDevices = numDevices;
            this.purchaseType = purchaseType;
            this.numOtherPurchases = numOtherPurchases;
            this.billAmount = billAmount;
            this.billType = billType;
        }

        //getters and setters
        public double getDistanceDriven() { return distanceDriven; }
        public void setDistanceDriven(double distanceDriven) { this.distanceDriven = distanceDriven; }
        public String getVehicleType() { return vehicleType; }
        public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
        public String getTransportType() { return transportType; }
        public void setTransportType(String transportType) { this.transportType = transportType; }
        public double getCyclingTime() { return cyclingTime; }
        public void setCyclingTime(double cyclingTime) { this.cyclingTime = cyclingTime; }
        public int getNumFlights() { return numFlights; }
        public void setNumFlights(int numFlights) { this.numFlights = numFlights; }
        public String getFlightType() { return flightType; }
        public void setFlightType(String flightType) { this.flightType = flightType; }
        public String getMealType() { return mealType; }
        public void setMealType(String mealType) { this.mealType = mealType; }
        public int getNumServings() { return numServings; }
        public void setNumServings(int numServings) { this.numServings = numServings; }
        public int getNumClothes() { return numClothes; }
        public void setNumClothes(int numClothes) { this.numClothes = numClothes; }
        public String getDeviceType() { return deviceType; }
        public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
        public int getNumDevices() { return numDevices; }
        public void setNumDevices(int numDevices) { this.numDevices = numDevices; }
        public String getPurchaseType() { return purchaseType; }
        public void setPurchaseType(String purchaseType) { this.purchaseType = purchaseType; }
        public int getNumOtherPurchases() { return numOtherPurchases; }
        public void setNumOtherPurchases(int numOtherPurchases) { this.numOtherPurchases = numOtherPurchases; }
        public double getBillAmount() { return billAmount; }
        public void setBillAmount(double billAmount) { this.billAmount = billAmount; }
        public String getBillType() { return billType; }
        public void setBillType(String billType) { this.billType = billType; }
    }

    //created a nested class for the total emission calculations
    public static class CalculatedEmissions {
        private double totalTranspo;
        private double totalFood;
        private double totalShopping;
        private double totalEmission;

        //constructor
        public CalculatedEmissions(double totalTranspo, double totalFood, double totalShopping) {
            this.totalTranspo = totalTranspo;
            this.totalFood = totalFood;
            this.totalShopping = totalShopping;
            this.totalEmission = totalTranspo + totalFood + totalShopping; // Calculate total emissions
        }

        public CalculatedEmissions() {
        }

        //getters and setters
        public double getTotalTranspo() { return totalTranspo; }
        public void setTotalTranspo(double totalTranspo) { this.totalTranspo = totalTranspo; }
        public double getTotalFood() { return totalFood; }
        public void setTotalFood(double totalFood) { this.totalFood = totalFood; }
        public double getTotalShopping() { return totalShopping; }
        public void setTotalShopping(double totalShopping) { this.totalShopping = totalShopping; }
        public double getTotalEmission() { return totalEmission; }
        public void setTotalEmission(double totalEmission) { this.totalEmission = totalEmission; }

    }
}
