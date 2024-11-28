package com.example.b07project;
import android.widget.TextView;
import java.util.Arrays;

public class ComparisonText {
    private TextView comparisonText;
    private TextView yourEmissionsNumber;
    private TextView GlobalEmissions;

    public ComparisonText(TextView comparisonText, TextView yourEmissionsNumber, TextView GlobalEmissions) {
        this.comparisonText = comparisonText;
        this.yourEmissionsNumber = yourEmissionsNumber;
        this.GlobalEmissions = GlobalEmissions;
    }

    public void updateComparisonText(String location, double totalEmissions) {
        int i = Arrays.asList(EmissionsData.countries).indexOf(location);
        if (i == -1) {
            comparisonText.setText("Location not found in emissions data.");
            return;
        }

        double nationalAverage = EmissionsData.globalAverages[i];
        double nationalComparison = totalEmissions - nationalAverage;

        // Update UI components
        yourEmissionsNumber.setText(String.format("%.2f", totalEmissions));
        GlobalEmissions.setText(String.format("%.2f", nationalAverage));

        // Update comparison text
        if (nationalComparison < 0) {
            comparisonText.setText(String.format("Your emissions are %.2f lower than the national average", Math.abs(nationalComparison)));
        } else if (nationalComparison == 0) {
            comparisonText.setText("Your emissions are the same as the national average");
        } else {
            comparisonText.setText(String.format("Your emissions are %.2f higher than the national average", nationalComparison));
        }
    }
}
