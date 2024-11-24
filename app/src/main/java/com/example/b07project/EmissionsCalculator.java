package com.example.b07project;
import java.util.Arrays;

public class EmissionsCalculator extends EmissionsData{
    public double getTransportationEmissions(String selectedCarUse, String selectedCarType, String selectedDistance, String selectedPTUse, String selectedPTTime, String selectedShortFlights, String selectedLongFlights) {
        double emissions = 0;
        int i = -1;
        int j = -1;

        if (selectedCarUse.equals("No")) {
            emissions = 0;
        } else {
            i = Arrays.asList(annualKmDriven).indexOf(selectedDistance);
            j = Arrays.asList(carTypes).indexOf(selectedCarType);
            System.out.println("Car Type Index: " + i + ", Distance Index: " + j);
            if (i != -1 && j != -1) emissions += carEmission[i][j];
        }

        i = Arrays.asList(publicTransportTime).indexOf(selectedPTTime);
        j = Arrays.asList(publicTransportFrequency).indexOf(selectedPTUse);
        System.out.println("Public Transport Frequency Index: " + i + ", Time Index: " + j);
        if (i != -1 && j != -1) emissions += publictransportEmission[i][j];

        // Find the index for selected short haul flights frequency using indexOf
        i = Arrays.asList(shortHaulFlights).indexOf(selectedShortFlights);
        System.out.println("Short Flights Index: " + i);
        if (i != -1) emissions += shortflightEmission[i];

        // Find the index for selected long haul flights frequency using indexOf
        i = Arrays.asList(longHaulFlights).indexOf(selectedLongFlights);
        System.out.println("Long Flights Index: " + i);
        if (i != -1) emissions += longflightEmission[i];

        if (emissions <= 0) return 0;
        return emissions;
    }
    public double getFoodEmissions(String selectedDietType, String selectedBeefFrequency, String selectedPorkFrequency, String selectedChickenFrequency, String selectedFishFrequency, String selectedFoodWasteFrequency) {
        double emissions = 0;

        // Define the mappings for diet type
        int dietIndex = Arrays.asList(dietTypes).indexOf(selectedDietType);
        System.out.println("Diet Type Index: " + dietIndex);

        // If diet is not meat-based, use the single value emissions
        if (dietIndex >= 0 && dietIndex <= 2) emissions += foodEmission[dietIndex][0][0]; // Use the corresponding emissions for vegetarian, vegan, or pescatarian

        // Handle meat-based diets
        if (dietIndex == 3) {
            String[] selectedFrequencies = {selectedBeefFrequency, selectedPorkFrequency, selectedChickenFrequency, selectedFishFrequency};

            for (int meatIndex = 0; meatIndex < meatFrequencies.length; meatIndex++) {
                int frequencyIndex = Arrays.asList(meatFrequencies[meatIndex]).indexOf(selectedFrequencies[meatIndex]);
                System.out.println("Meat Type " + meatIndex + " Frequency Index: " + frequencyIndex);
                if (frequencyIndex != -1) emissions += foodEmission[dietIndex][meatIndex][frequencyIndex];
            }
        }

        // Find the index for food waste frequency
        int foodWasteIndex = Arrays.asList(foodWasteFrequency).indexOf(selectedFoodWasteFrequency);
        System.out.println("Food Waste Frequency Index: " + foodWasteIndex);
        if (foodWasteIndex != -1) emissions += foodwasteEmission[foodWasteIndex];

        if (emissions <= 0) return 0;
        return emissions;
    }
    public double getHousingEmissions(String homeType, String homeSize, String householdSize, String heatingHomeType, String heatingWaterType, String electricityBill, String Renewable) {
        double emissions = 0;
        // Map the user selections to corresponding indices
        int homeTypeIndex = Arrays.asList(homeTypes).indexOf(homeType);
        int householdSizeIndex = Arrays.asList(householdSizes).indexOf(householdSize);
        int homeSizeIndex = Arrays.asList(housingSizes).indexOf(homeSize);
        int heatingTypeIndex = Arrays.asList(heatingTypes).indexOf(heatingHomeType);
        int electricityBillIndex = Arrays.asList(electricityBills).indexOf(electricityBill);

        System.out.println("Home Type Index: " + homeTypeIndex);
        System.out.println("Home Size Index: " + homeSizeIndex);
        System.out.println("Household Size Index: " + householdSizeIndex);
        System.out.println("Heating Type Index: " + heatingTypeIndex);
        System.out.println("Electricity Bill Index: " + electricityBillIndex);

        // Check if the indexes are valid
        if (homeTypeIndex == -1 || householdSizeIndex == -1 || homeSizeIndex == -1 || heatingTypeIndex == -1 || electricityBillIndex == -1) {
            throw new IllegalArgumentException("Invalid selection for one or more categories.");
        }

        emissions += housingEmission[homeTypeIndex][homeSizeIndex][electricityBillIndex][householdSizeIndex][heatingTypeIndex];

        if (!heatingHomeType.equals(heatingWaterType)){
            emissions += 233;
        }

        if (Renewable.equals("Yes, primarily")) emissions -= 6000;
        if (Renewable.equals("Yes, partially")) emissions -= 4000;

        if (emissions <= 0) return 0;
        return emissions;
    }
    public double getConsumptionEmissions(String clothingPurchase, String secondHandPurchase, String electronicDevices, String recyclingFrequencyValue){
        double emissions = 0;
        // Map the user selections to corresponding indices
        int clothingPurchaseIndex = Arrays.asList(clothingPurchaseFrequency).indexOf(clothingPurchase);
        int secondHandPurchaseIndex = Arrays.asList(secondHandPurchaseFrequency).indexOf(secondHandPurchase);
        int electronicDevicesIndex = Arrays.asList(electronicDevicesPurchased).indexOf(electronicDevices);
        int recyclingIndex = Arrays.asList(recyclingFrequency).indexOf(recyclingFrequencyValue);

        System.out.println("Clothing Purchase Index: " + clothingPurchaseIndex);
        System.out.println("Second-Hand Purchase Index: " + secondHandPurchaseIndex);
        System.out.println("Electronic Devices Index: " + electronicDevicesIndex);
        System.out.println("Recycling Frequency Index: " + recyclingIndex);

        // Check if the indices are valid
        if (clothingPurchaseIndex == -1 || secondHandPurchaseIndex == -1 || electronicDevicesIndex == -1 || recyclingIndex == -1) {
            throw new IllegalArgumentException("Invalid selection for one or more categories.");
        }

        double totalClothingEmission = clothesEmission[secondHandPurchaseIndex][clothingPurchaseIndex];
        double totalElectronicsEmission = electronicEmission[electronicDevicesIndex];
        double totalRecyclingEmission = recyclingClothesEmission[recyclingIndex][clothingPurchaseIndex] + recyclingElectronicEmission[recyclingIndex][electronicDevicesIndex];

        emissions = totalClothingEmission + totalElectronicsEmission + totalRecyclingEmission;

        if (emissions <= 0) return 0;
        return emissions;
    }
}