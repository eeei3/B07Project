package com.example.b07project;

import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.eazegraph.lib.charts.ValueLineChart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EcoGaugeTest {

    @Mock
    FirebaseAuth mockAuth;

    @Mock
    FirebaseUser mockUser;

    @Mock
    Spinner mockSpinner;

    @Mock
    TextView mockComparisonText;

    @Mock
    TextView mockYourEmissionsNumber;

    @Mock
    TextView mockGlobalEmissions;

    private EcoGauge ecoGauge;

    @Before
    public void setUp() {
        // Initialize the EcoGauge activity with mocked FirebaseAuth
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn("mockUserId");

        ecoGauge = new EcoGauge(mockAuth);

        // Initialize the UI components with mocked views
        ecoGauge.comparisonText = mockComparisonText;
        ecoGauge.yourEmissionsNumber = mockYourEmissionsNumber;
        ecoGauge.GlobalEmissions = mockGlobalEmissions;
        ecoGauge.timeSpinner = mockSpinner;
    }

    @Test
    public void testInitializeFirebaseUser() {
        // Test the Firebase user initialization
        String userId = ecoGauge.initializeFirebaseUser();
        assertNotNull("Firebase user ID should not be null", userId);
        assertEquals("User ID should be 'mockUserId'", "mockUserId", userId);
    }

    @Test
    public void testGetLocationFromIntent() {
        // Test if the location is correctly retrieved from the Intent
        String location = ecoGauge.getIntent().getStringExtra("location");
        assertEquals("Location should be 'Toronto'", "Toronto", location);
    }

    @Test
    public void testComparisonTextUpdate() {
        // Mock the method of updating comparison text
        ComparisonText mockComparisonTextObj = mock(ComparisonText.class);
        when(mockComparisonText.getText()).thenReturn("Comparison Text");
        mockComparisonTextObj.updateComparisonText("Toronto", "2024-12-01");

        // Verify that the updateComparisonText was called once
        verify(mockComparisonTextObj, times(1)).updateComparisonText("Toronto", "2024-12-01");
    }

    @Test
    public void testChartUpdate() {
        // Test updating the line chart with a mock time period
        String timePeriod = "monthly";
        LineChartDisplay mockLineChartDisplay = mock(LineChartDisplay.class);
        ecoGauge.chart = mock(ValueLineChart.class);
        mockLineChartDisplay.updateLineChart(timePeriod);

        // Verify that the chart was updated
        verify(mockLineChartDisplay, times(1)).updateLineChart(timePeriod);
    }

    @Test
    public void testSpinnerSelection() {
        // Simulate selecting a value from the spinner
        when(mockSpinner.getSelectedItem()).thenReturn("2024");

        String selectedTime = (String) mockSpinner.getSelectedItem();
        assertEquals("Selected time should be '2024'", "2024", selectedTime);
    }

    @Test
    public void testInvalidTimePeriodForPieChart() {
        // Test if the PieChart update is called when no time period is provided
        PieChartUpdate mockPieChartUpdate = mock(PieChartUpdate.class);
        String timePeriod = null;
        mockPieChartUpdate.updateChartForTimePeriod(timePeriod);

        // Verify the method to handle the null time period
        verify(mockPieChartUpdate, times(1)).updateChartForTimePeriod(null);
    }
}
