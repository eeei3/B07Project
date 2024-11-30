package com.example.b07project;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLineSeries;

public class LineChartDisplay {
    private ValueLineChart chart;
    //constructor
    public LineChartDisplay(ValueLineChart chart) {
        this.chart = chart;
    }

    /*
     * Updates the line chart by adding the series for daily, monthly, and annual emissions.
     * This method is called when all calls for data are done
     */
    void updateLineChart() {
        LineChartData data = new LineChartData();
        // Clear existing data to refresh the chart
        chart.clearChart();
        ValueLineSeries dailySeries = data.getDailyDataForChart();
        ValueLineSeries monthlySeries = data.getMonthlyDataForChart();
        ValueLineSeries annualSeries = data.getYearlyDataForChart();

        // Add each series to the chart
        if (!dailySeries.getSeries().isEmpty()) {
            chart.addSeries(dailySeries); // Add daily emissions line
        }

        if (!monthlySeries.getSeries().isEmpty()) {
            chart.addSeries(monthlySeries); // Add monthly emissions line
        }

        if (!annualSeries.getSeries().isEmpty()) {
            chart.addSeries(annualSeries); // Add yearly emissions line
        }

        // Refresh the chart to display the new data
        chart.startAnimation();
    }
}
