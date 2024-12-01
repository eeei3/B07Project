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
    void updateLineChart(String timePeriod) {

        LineChartData data = new LineChartData();
        // Clear existing data to refresh the chart
        chart.clearChart();
        ValueLineSeries dailySeries = data.getDailyDataForChart();
        ValueLineSeries monthlySeries = data.getMonthlyDataForChart();
        ValueLineSeries annualSeries = data.getYearlyDataForChart();

        // Add each series to the chart
        switch (timePeriod) {
            case ("Daily"):
                chart.addSeries(dailySeries); // Add daily emissions line
                break;
            case ("Monthly"):
                chart.addSeries(monthlySeries); // Add monthly emissions line
                break;
            case ("Yearly"):
                chart.addSeries(annualSeries); // Add yearly emissions line
                break;
            default:
                chart.addSeries(dailySeries); // Add daily emissions line
                break;
    }
        chart.startAnimation();
    }
}
