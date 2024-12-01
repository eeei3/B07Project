package com.example.b07project;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.PieChart;
import java.util.ArrayList;

public class ChartHelper {
    public static void updatePieChart(PieChart pieChart, double totalTranspo, double totalFood, double totalShopping) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) totalTranspo, "Transportation"));
        entries.add(new PieEntry((float) totalFood, "Food Consumption"));
        entries.add(new PieEntry((float) totalShopping, "Shopping"));

        PieDataSet dataSet = new PieDataSet(entries, "Emissions");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Set pie slice colors
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart
    }
}

