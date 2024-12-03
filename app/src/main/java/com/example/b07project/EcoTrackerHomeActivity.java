package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * EcoTrackerHomeActivity serves as the main screen for the Planetze app after user login.
 *
 * It allows the user to:
 * - Select a date from the calendar.
 * - Log activities for the selected date.
 * - View details of previously logged activities.
 * - View emissions summary for the current day
 *
 * The activity fetches and displays the user's total emissions (transportation, food, shopping)
 * and energy bills from the Firebase database.
 *
 * @see LogActivitiesActivity
 * @see DetailPageActivity
 */

public class EcoTrackerHomeActivity extends AppCompatActivity {

    public static String userId;
    private DatabaseReference databaseReference;
    private long selectedDate;
    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecotrackerhome_fragment);

        // get reference to the CalendarView
        CalendarView calendarView = findViewById(R.id.calendar_view);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // set listener to get the selected date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Month is 0-based, so add 1 to the month
                month++;
                Toast.makeText(EcoTrackerHomeActivity.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1); // subtract 1 to convert back to 0-based month
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                selectedDate = calendar.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // set time zone
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                selectedDay = sdf.format(new Date(selectedDate));
            }
        });

        // initialize buttons
        Button buttonLog = findViewById(R.id.log_activity_button);
        Button buttonDetails = findViewById(R.id.detail_activity_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();  // get the current authenticated user

        // get the user's id
        if (user != null) {
            userId = user.getUid();
        }

        // log button click listener
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDay != null && !selectedDay.isEmpty()) {
                    // check if activities have already been logged for this date
                    checkActivitiesForDate(selectedDay, new OnActivitiesCheckListener() {
                        @Override
                        public void onActivitiesChecked(boolean hasActivities) {
                            if (!hasActivities) {
                                // if no activities logged for this date, proceed to log
                                Intent intent = new Intent(EcoTrackerHomeActivity.this, LogActivitiesActivity.class);
                                intent.putExtra("selectedDate", selectedDay);
                                intent.putExtra("user_id", userId);
                                startActivity(intent);
                            } else {
                                // activities already exist for this date, show toast
                                Toast.makeText(EcoTrackerHomeActivity.this, "Activities history found! Please go directly to detail page", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // if no date selected
                    Toast.makeText(EcoTrackerHomeActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // details button click listener
        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDay != null && !selectedDay.isEmpty()) {
                    // check if activities exist for this date before viewing details
                    checkActivitiesForDate(selectedDay, new OnActivitiesCheckListener() {
                        @Override
                        public void onActivitiesChecked(boolean hasActivities) {
                            // if activities logged before, open the details page
                            if (hasActivities) {
                                Intent intent = new Intent(EcoTrackerHomeActivity.this, DetailPageActivity.class);
                                intent.putExtra("selectedDate", selectedDay);
                                intent.putExtra("user_id", userId);
                                startActivity(intent);
                            } else {
                                // if no activities logged for this date, show toast
                                Toast.makeText(EcoTrackerHomeActivity.this, "No activities history found! Please log activities first", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // no date selected
                    Toast.makeText(EcoTrackerHomeActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // retrieve and display the totals of emissions and energy bills
        retrieveAndDisplayTotalsTraditional();

    }

    /**
     * Interface to handle the callback after checking whether activities are logged for a specific date.
     */
    private interface OnActivitiesCheckListener {
        void onActivitiesChecked(boolean hasActivities);
    }

    /**
     * Checks if there are any activities logged for the selected date.
     *
     * @param selectedDay - the date to check for logged activities
     * @param listener - callback listener to handle the result of the check
     */
    private void checkActivitiesForDate(String selectedDay, OnActivitiesCheckListener listener) {
        // reference to the user's activities for the specific date
        DatabaseReference userActivitiesRef = databaseReference
                .child("users")
                .child(userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDay));

        userActivitiesRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        listener.onActivitiesChecked(true);
                    } else {
                        listener.onActivitiesChecked(false);
                    }
                } else {
                    // handles potential errors
                    Toast.makeText(EcoTrackerHomeActivity.this, "Error checking activities", Toast.LENGTH_SHORT).show();
                    listener.onActivitiesChecked(false);
                }
            }
        });
    }

    /**
     * Retrieves and displays the user's total emissions and energy bills from the system date or "today"
     */
    private void retrieveAndDisplayTotalsTraditional() {

        long systemDate = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // set the time zone
        String dateToday = sdf.format(new Date(systemDate));


        // reference to the "rawInputs" node
        DatabaseReference ref = databaseReference
                .child("users")
                .child(userId)
                .child("ecotracker")
                .child(String.valueOf(dateToday));

        // retrieve totalTranspo
        ref.child("calculatedEmissions").child("totalTranspo").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    long totalTranspo = task.getResult().getValue(Long.class);
                    TextView transpoTextView = findViewById(R.id.transport_emissions);
                    transpoTextView.setText("Total Transportation Emissions: " + totalTranspo + "kg");
                } else {
                    TextView transpoTextView = findViewById(R.id.transport_emissions);
                    transpoTextView.setText("Total Transportation Emissions: Not logged yet");
                }
            }
        });

        // retrieve totalFood
        ref.child("calculatedEmissions").child("totalFood").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    long totalFood = task.getResult().getValue(Long.class);
                    TextView foodTextView = findViewById(R.id.food_emissions);
                    foodTextView.setText("Total Food Emissions: " + totalFood + "kg" );
                } else {
                    TextView foodTextView = findViewById(R.id.food_emissions);
                    foodTextView.setText("Total Food Emissions: Not logged yet");
                }
            }
        });

        // retrieve totalShopping
        ref.child("calculatedEmissions").child("totalShopping").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    long totalShopping = task.getResult().getValue(Long.class);
                    TextView shoppingTextView = findViewById(R.id.shopping_emissions);
                    shoppingTextView.setText("Total Shopping Emissions: " + totalShopping + "kg");
                } else {
                    TextView shoppingTextView = findViewById(R.id.shopping_emissions);
                    shoppingTextView.setText("Total Shopping Emissions: Not logged yet");
                }
            }
        });

        // retrieve totalBills
        ref.child("rawInputs").child("billAmount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    long totalBills = task.getResult().getValue(Long.class);
                    TextView billsTextView = findViewById(R.id.energy_bills);
                    billsTextView.setText("Total Bills: " + totalBills + "$");
                } else {
                    TextView billsTextView = findViewById(R.id.energy_bills);
                    billsTextView.setText("Total Bills: Not logged yet");
                }
            }
        });
    }

    /**
     * onCreateOptionsMenu- creates the options menu for navigating to EcoGauge and HabitsMenu.
     *
     * @param menu - the options menu
     * @return true if the menu is created successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    /**
     * onOptionsItemSelected - handles item selection from the options menu.
     *
     * @param item - the selected menu item
     * @return true if the item was handled, otherwise passes to the superclass
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ecogauge) {
            Intent intent = new Intent(EcoTrackerHomeActivity.this, EcoGauge.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.habit) {
            Intent intent = new Intent(EcoTrackerHomeActivity.this, HabitsMenu.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
