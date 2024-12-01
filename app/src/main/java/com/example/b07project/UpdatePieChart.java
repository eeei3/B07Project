package com.example.b07project;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UpdatePieChart extends EcoGauge{
    // Method to update the UI with fetched emission values
    void updateUI(double totalTranspo, double totalFood, double totalShopping, double totalEmission) {
        transportation.setText(Integer.toString((int) totalTranspo));
        foodConsumption.setText(Integer.toString((int) totalFood));
        shopping.setText(Integer.toString((int) totalShopping));
        totalEmissionsText.setText(String.format("Carbon Footprint: %.2f tons of CO₂e", totalEmission));
    }

    // Method to update the pie chart with emission data
    void updatePieChart(double totalTranspo, double totalFood, double totalShopping) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) totalTranspo, "Transportation"));
        entries.add(new PieEntry((float) totalFood, "Food Consumption"));
        entries.add(new PieEntry((float) totalShopping, "Shopping"));

        PieDataSet dataSet = new PieDataSet(entries, "Emissions");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

}
