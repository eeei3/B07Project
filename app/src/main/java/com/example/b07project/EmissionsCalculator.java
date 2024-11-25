package com.example.b07project;

import java.util.Arrays;


/**
 * EmissionsCalculator class containing methods pertaining to calculating user's emissions based
 * from certain parameters
 */
public class EmissionsCalculator extends EmissionsData{

    /**
     * getTransportationEmissions - Calculates the total emissions emitted by the user caused by
     * reasons relating to travel, such as car usage.
     * @param selectedCarUse - Does the user use a personal vehicle
     * @param selectedCarType - Type of car user uses
     * @param selectedDistance - Distance user drives
     * @param selectedPTUse - Does the user use public transport
     * @param selectedPTTime - How much time the user spends on public transport
     * @param selectedShortFlights - How many short flights the user has taken
     * @param selectedLongFlights - How many long flights the user has taken
     * @return - Total travel related emissions
     */
    public double getTransportationEmissions(String selectedCarUse,
                                             String selectedCarType,
                                             String selectedDistance,
                                             String selectedPTUse,
                                             String selectedPTTime,
                                             String selectedShortFlights,
                                             String selectedLongFlights) {
        double emissions = 0;
        int i = -1;
        int j = -1;

        if (selectedCarUse.equals("No")) {
            emissions = 0;
        } else {
            i = Arrays.asList(annualKmDriven).indexOf(selectedDistance);
            j = Arrays.asList(carTypes).indexOf(selectedCarType);
            if (i != -1 && j != -1) {
                emissions += carEmission[i][j];
            }
        }

        i = Arrays.asList(publicTransportTime).indexOf(selectedPTTime);
        j = Arrays.asList(publicTransportFrequency).indexOf(selectedPTUse);
        if (i != -1 && j != -1) {
            emissions += publictransportEmission[i][j];
        }

        // Find the index for selected short haul flights frequency using indexOf
        i = Arrays.asList(shortHaulFlights).indexOf(selectedShortFlights);
        if (i != -1) {
            emissions += shortflightEmission[i];
        }

        // Find the index for selected long haul flights frequency using indexOf
        i = Arrays.asList(longHaulFlights).indexOf(selectedLongFlights);
        if (i != -1) {
            emissions += longflightEmission[i];
        }

        // If the user has below zero emissions
        if (emissions <= 0) {
            return 0;
        }

        return emissions;
    }

    /**
     *  getFoodEmissions - Calculates the total emissions emitted by the user caused by
     *  reasons relating to diet
     * @param selectedDietType - Type of meals the user consumes
     * @param selectedBeefFrequency - How often the user eats beef
     * @param selectedPorkFrequency - How often the user eats pork
     * @param selectedChickenFrequency - How often the user eats chicken
     * @param selectedFishFrequency - How often the user eats fish
     * @param selectedFoodWasteFrequency - How often the user wasts food
     * @return - Total diet related emissions
     */
    public double getFoodEmissions(String selectedDietType,
                                   String selectedBeefFrequency,
                                   String selectedPorkFrequency,
                                   String selectedChickenFrequency,
                                   String selectedFishFrequency,
                                   String selectedFoodWasteFrequency) {

        double emissions = 0;

        // Define the mappings for diet type
        int dietIndex = Arrays.asList(dietTypes).indexOf(selectedDietType);

        // If diet is not meat-based, use the single value emissions
        // Use the corresponding emissions for vegetarian, vegan, or pescatarian
        if (dietIndex >= 0 && dietIndex <= 2) {
            emissions += foodEmission[dietIndex][0][0];
        }

        // Handle meat-based diets
        if (dietIndex == 3) {
            String[] selectedFrequencies = {selectedBeefFrequency,
                                            selectedPorkFrequency,
                                            selectedChickenFrequency,
                                            selectedFishFrequency};

            for (int meatIndex = 0; meatIndex < meatFrequencies.length; meatIndex++) {
                int frequencyIndex = Arrays.asList(meatFrequencies[meatIndex])
                        .indexOf(selectedFrequencies[meatIndex]);

                if (frequencyIndex != -1) {
                    emissions += foodEmission[dietIndex][meatIndex][frequencyIndex];
                }
            }
        }

        // Find the index for food waste frequency
        int foodWasteIndex = Arrays.asList(foodWasteFrequency).indexOf(selectedFoodWasteFrequency);
        if (foodWasteIndex != -1) {
            emissions += foodwasteEmission[foodWasteIndex];
        }

        // If the user has below zero emissions
        if (emissions <= 0) {
            return 0;
        }

        return emissions;
    }

    /**
     * getHousingEmissions Calculates the total emissions emitted by the user cause by
     * reasons relating to residences, such as residential water usage
     * @param homeType - Type of home user resides in
     * @param homeSize - The size of the user's residence
     * @param householdSize - The size of the user's household
     * @param heatingHomeType - Heating system installed in user's residence
     * @param heatingWaterType - Water Heating system installed in user's home
     * @param electricityBill - User's electricity bill
     * @param Renewable - Does the user use renewable energy sources
     * @return - Total residential related emissions
     */
    public double getHousingEmissions(String homeType,
                                      String homeSize,
                                      String householdSize,
                                      String heatingHomeType,
                                      String heatingWaterType,
                                      String electricityBill,
                                      String Renewable) {

        double emissions = 0;
        
        // Map the user selections to corresponding indices
        int homeTypeIndex = Arrays.asList(homeTypes).indexOf(homeType);
        int householdSizeIndex = Arrays.asList(householdSizes).indexOf(householdSize);
        int homeSizeIndex = Arrays.asList(housingSizes).indexOf(homeSize);
        int heatingTypeIndex = Arrays.asList(heatingTypes).indexOf(heatingHomeType);
        int electricityBillIndex = Arrays.asList(electricityBills).indexOf(electricityBill);

        // Check if the indexes are valid
        if (homeTypeIndex == -1
                || householdSizeIndex == -1
                || homeSizeIndex == -1
                || heatingTypeIndex == -1
                || electricityBillIndex == -1) {
            throw new IllegalArgumentException("Invalid selection for one or more categories.");
        }

        emissions += housingEmission[homeTypeIndex][homeSizeIndex][electricityBillIndex][householdSizeIndex][heatingTypeIndex];

        // User does not use a water heater at home
        if (!heatingHomeType.equals(heatingWaterType)){
            emissions += 233;
        }

        // User primarily uses renewable energy sources
        if (Renewable.equals("Yes, primarily")) {
            emissions -= 6000;
        }

        // User sometimes uses renewable energy sources
        if (Renewable.equals("Yes, partially")) {
            emissions -= 4000;
        }

        // If the user has below zero emissions
        if (emissions <= 0) {
            return 0;
        }

        return emissions;
    }

    /**
     * getConsumptionEmissions Calculates the total emissions emitted by the user cause by
     * reasons relating to consumer consumption.
     * @param clothingPurchase - How often the user purchases new clothes
     * @param secondHandPurchase - How often the user purchases second-hand or eco-friendly items
     * @param electronicDevices - How many electronics the user has purchased in the past year
     * @param recyclingFrequencyValue - How often does the user recycle
     * @return - Total consumer consumption related emissions
     */
    public double getConsumptionEmissions(String clothingPurchase,
                                          String secondHandPurchase,
                                          String electronicDevices,
                                          String recyclingFrequencyValue){

        double emissions = 0;

        // Map the user selections to corresponding indices
        int clothingPurchaseIndex = Arrays
                .asList(clothingPurchaseFrequency).indexOf(clothingPurchase);
        int secondHandPurchaseIndex = Arrays
                .asList(secondHandPurchaseFrequency).indexOf(secondHandPurchase);
        int electronicDevicesIndex = Arrays
                .asList(electronicDevicesPurchased).indexOf(electronicDevices);
        int recyclingIndex = Arrays
                .asList(recyclingFrequency).indexOf(recyclingFrequencyValue);

        // Check if the indices are valid
        if (clothingPurchaseIndex == -1
                || secondHandPurchaseIndex == -1
                || electronicDevicesIndex == -1
                || recyclingIndex == -1) {
            throw new IllegalArgumentException("Invalid selection for one or more categories.");
        }

        double totalClothingEmission = clothesEmission[secondHandPurchaseIndex][clothingPurchaseIndex];
        double totalElectronicsEmission = electronicEmission[electronicDevicesIndex];
        double totalRecyclingEmission = recyclingClothesEmission[recyclingIndex][clothingPurchaseIndex]
                + recyclingElectronicEmission[recyclingIndex][electronicDevicesIndex];

        emissions = totalClothingEmission + totalElectronicsEmission + totalRecyclingEmission;

        // If the user has below zero emissions
        if (emissions <= 0) {
            return 0;
        }

        return emissions;
    }
}
