package com.example.b07project;

/**
 * UserEmissionData holds both the raw input data provided by the user and the calculated emission
 * data for various activities.
 * The class consists of two main components:
 * - **RawInputs**: Stores the raw data related to the user's activities such as distance driven,
 *   flights taken, meals consumed, and shopping purchases.
 * - **CalculatedEmissions**: Contains the calculated emissions for transportation, food, shopping,
 *   and the total carbon footprint.
 * This class helps in tracking, packaging, and storing user input into the Firebase database
 *
 * @see RawInputs
 * @see CalculatedEmissions
 */

public class UserEmissionData {
    private RawInputs rawInputs;
    private CalculatedEmissions calculatedEmissions;

    /**
     * Constructor for UserEmissionData
     *
     * @param rawInputs the user's raw inputs
     * @param calculatedEmissions the calculated emissions
     */
    public UserEmissionData(RawInputs rawInputs, CalculatedEmissions calculatedEmissions) {
        this.rawInputs = rawInputs;
        this.calculatedEmissions = calculatedEmissions;
    }

    /**
     * getRawInputs method returns the raw inputs.
     *
     * @return the raw inputs
     */
    public RawInputs getRawInputs() {
        return rawInputs;
    }

    /**
     * getRawInputs method returns the calculated emissions.
     *
     * @return the calculated emissions
     */
    public CalculatedEmissions getCalculatedEmissions() {
        return calculatedEmissions;
    }


    /**
     * RawInputs is a nested class used to store the raw inputs.
     *
     */
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


        /**
         * An empty constructor of RawInputs
         *
         */
        public RawInputs() {
        }


        /**
         * A constructor of RawInputs with the necessary parameters.
         *
         * @param distanceDriven the distance driven
         * @param vehicleType the type of vehicle
         * @param transportType the mode of transport
         * @param cyclingTime the time cycled
         * @param numFlights the number of flights taken
         * @param flightType the type of flights taken
         * @param mealType the type of meals had
         * @param numServings the number of servings
         * @param numClothes the number of clothes bought
         * @param deviceType the type of device had
         * @param numDevices the number of devices bought
         * @param purchaseType the type of other purchases
         * @param numOtherPurchases the number of other purchases
         * @param billAmount the amount of the bill
         * @param billType the type of the bill
         */
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

        /**
         * getDistanceDriven returns the distance driven.
         *
         * @return the distance driven
         */
        public double getDistanceDriven() { return distanceDriven; }

        /**
         * setDistanceDriven sets the distance driven.
         *
         * @param distanceDriven the distance driven to be set to
         */
        public void setDistanceDriven(double distanceDriven) { this.distanceDriven = distanceDriven; }

        /**
         * getVehicleType method returns the type of vehicle.
         *
         * @return the type of vehicle
         */
        public String getVehicleType() { return vehicleType; }

        /**
         * setVehicleType method sets the type of vehicle
         *
         * @param vehicleType the type of vehicle to be set to.
         */
        public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
        /**
         * getTransportType method returns the type of transport.
         *
         * @return the type of transport
         */
        public String getTransportType() { return transportType; }

        /**
         * setTransportType method sets the type of transport.
         *
         * @param transportType the type of transport to be set
         */
        public void setTransportType(String transportType) { this.transportType = transportType; }

        /**
         * getCyclingTime method returns the cycling time.
         *
         * @return the cycling time
         */
        public double getCyclingTime() { return cyclingTime; }

        /**
         * setCyclingTime method sets the cycling time.
         *
         * @param cyclingTime the cycling time to be set
         */
        public void setCyclingTime(double cyclingTime) { this.cyclingTime = cyclingTime; }

        /**
         * getNumFlights method returns the number of flights.
         *
         * @return the number of flights
         */
        public int getNumFlights() { return numFlights; }

        /**
         * setNumFlights method sets the number of flights.
         *
         * @param numFlights the number of flights to be set
         */
        public void setNumFlights(int numFlights) { this.numFlights = numFlights; }

        /**
         * getFlightType method returns the type of flight.
         *
         * @return the type of flight
         */
        public String getFlightType() { return flightType; }

        /**
         * setFlightType method sets the type of flight.
         *
         * @param flightType the type of flight to be set
         */
        public void setFlightType(String flightType) { this.flightType = flightType; }

        /**
         * getMealType method returns the type of meal.
         *
         * @return the type of meal
         */
        public String getMealType() { return mealType; }

        /**
         * setMealType method sets the type of meal.
         *
         * @param mealType the type of meal to be set
         */
        public void setMealType(String mealType) { this.mealType = mealType; }

        /**
         * getNumServings method returns the number of servings.
         *
         * @return the number of servings
         */
        public int getNumServings() { return numServings; }

        /**
         * setNumServings method sets the number of servings.
         *
         * @param numServings the number of servings to be set
         */
        public void setNumServings(int numServings) { this.numServings = numServings; }

        /**
         * getNumClothes method returns the number of clothes.
         *
         * @return the number of clothes
         */
        public int getNumClothes() { return numClothes; }

        /**
         * setNumClothes method sets the number of clothes.
         *
         * @param numClothes the number of clothes to be set
         */
        public void setNumClothes(int numClothes) { this.numClothes = numClothes; }

        /**
         * getDeviceType method returns the type of device.
         *
         * @return the type of device
         */
        public String getDeviceType() { return deviceType; }

        /**
         * setDeviceType method sets the type of device.
         *
         * @param deviceType the type of device to be set
         */
        public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

        /**
         * getNumDevices method returns the number of devices.
         *
         * @return the number of devices
         */
        public int getNumDevices() { return numDevices; }

        /**
         * setNumDevices method sets the number of devices.
         *
         * @param numDevices the number of devices to be set
         */
        public void setNumDevices(int numDevices) { this.numDevices = numDevices; }

        /**
         * getPurchaseType method returns the type of purchase.
         *
         * @return the type of purchase
         */
        public String getPurchaseType() { return purchaseType; }

        /**
         * setPurchaseType method sets the type of purchase.
         *
         * @param purchaseType the type of purchase to be set
         */
        public void setPurchaseType(String purchaseType) { this.purchaseType = purchaseType; }

        /**
         * getNumOtherPurchases method returns the number of other purchases.
         *
         * @return the number of other purchases
         */
        public int getNumOtherPurchases() { return numOtherPurchases; }

        /**
         * setNumOtherPurchases method sets the number of other purchases.
         *
         * @param numOtherPurchases the number of other purchases to be set
         */
        public void setNumOtherPurchases(int numOtherPurchases) { this.numOtherPurchases = numOtherPurchases; }

        /**
         * getBillAmount method returns the bill amount.
         *
         * @return the bill amount
         */
        public double getBillAmount() { return billAmount; }

        /**
         * setBillAmount method sets the bill amount.
         *
         * @param billAmount the bill amount to be set
         */
        public void setBillAmount(double billAmount) { this.billAmount = billAmount; }

        /**
         * getBillType method returns the type of bill.
         *
         * @return the type of bill
         */
        public String getBillType() { return billType; }

        /**
         * setBillType method sets the type of bill.
         *
         * @param billType the type of bill to be set
         */
        public void setBillType(String billType) { this.billType = billType; }

    }


    /**
     * CalculatedEmissions is a nested class to calculate emissions.
     */
    public static class CalculatedEmissions {
        private double totalTranspo;
        private double totalFood;
        private double totalShopping;
        private double totalEmission;

        /**
         * Constructor for CalculatedEmissions
         *
         * @param totalTranspo total transportation emissions
         * @param totalFood total food emissions
         * @param totalShopping total shopping emissions
         */
        //constructor
        public CalculatedEmissions(double totalTranspo, double totalFood, double totalShopping) {
            this.totalTranspo = totalTranspo;
            this.totalFood = totalFood;
            this.totalShopping = totalShopping;
            this.totalEmission = totalTranspo + totalFood + totalShopping; // Calculate total emissions
        }

        /**
         * Empty constructor for CalculatedEmissions
         *
         */
        public CalculatedEmissions() {
        }

        /**
         * getTotalTranspo method returns the total transportation emissions.
         *
         * @return the total transportation emissions
         */
        public double getTotalTranspo() { return totalTranspo; }

        /**
         * getTotalFood method returns the total food emissions.
         *
         * @return the total food emissions
         */
        public double getTotalFood() { return totalFood; }

        /**
         * setTotalFood method sets the total food emissions.
         *
         * @param totalFood the total food emissions to be set
         */
        public void setTotalFood(double totalFood) { this.totalFood = totalFood; }

        /**
         * getTotalShopping method returns the total shopping emissions.
         *
         * @return the total shopping emissions
         */
        public double getTotalShopping() { return totalShopping; }

        /**
         * setTotalShopping method sets the total shopping emissions.
         *
         * @param totalShopping the total shopping emissions to be set
         */
        public void setTotalShopping(double totalShopping) { this.totalShopping = totalShopping; }

        /**
         * getTotalEmission method returns the total emissions.
         *
         * @return the total emissions
         */
        public double getTotalEmission() { return totalEmission; }

        /**
         * setTotalEmission method sets the total emissions.
         *
         * @param totalEmission the total emissions to be set
         */
        public void setTotalEmission(double totalEmission) { this.totalEmission = totalEmission; }

    }
}
