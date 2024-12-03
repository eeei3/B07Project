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

public class EcoTrackerHomeActivity extends AppCompatActivity {

    public static String userId;
    private DatabaseReference databaseReference;
    private long selectedDate;
    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecotrackerhome_fragment);

        // Get a reference to the CalendarView
        CalendarView calendarView = findViewById(R.id.calendar_view);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Set a listener to get the selected date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Month is 0-based, so add 1 to the month
                month++;
                Toast.makeText(EcoTrackerHomeActivity.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1); // Subtract 1 to convert back to 0-based month
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                selectedDate = calendar.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // Set the time zone
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                selectedDay = sdf.format(new Date(selectedDate));
            }
        });

        // Initialize buttons
        Button buttonLog = findViewById(R.id.log_activity_button);
        Button buttonDetails = findViewById(R.id.detail_activity_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user

        // Get the user's id
        if (user != null) {
            userId = user.getUid();
        }

        // Log button click listener
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedDay.isEmpty()) {
                    // Check if activities have already been logged for this date
                    checkActivitiesForDate(selectedDay, new OnActivitiesCheckListener() {
                        @Override
                        public void onActivitiesChecked(boolean hasActivities) {
                            if (!hasActivities) {
                                // No activities logged for this date, proceed to log
                                Intent intent = new Intent(EcoTrackerHomeActivity.this, LogActivitiesActivity.class);
                                intent.putExtra("selectedDate", selectedDay);
                                intent.putExtra("user_id", userId);
                                startActivity(intent);
                            } else {
                                // Activities already exist for this date
                                Toast.makeText(EcoTrackerHomeActivity.this, "Activities history found! Please go directly to detail page", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // No date selected
                    Toast.makeText(EcoTrackerHomeActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Details button click listener
        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedDay.isEmpty()) {
                    // Check if activities exist for this date before viewing details
                    checkActivitiesForDate(selectedDay, new OnActivitiesCheckListener() {
                        @Override
                        public void onActivitiesChecked(boolean hasActivities) {
                            // If there are activities logged before, open the details page
                            if (hasActivities) {
                                Intent intent = new Intent(EcoTrackerHomeActivity.this, DetailPageActivity.class);
                                intent.putExtra("selectedDate", selectedDay);
                                intent.putExtra("user_id", userId);
                                startActivity(intent);
                            } else {
                                // No activities logged for this date, show a toast
                                Toast.makeText(EcoTrackerHomeActivity.this, "No activities history found! Please log activities first", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // No date selected
                    Toast.makeText(EcoTrackerHomeActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        retrieveAndDisplayTotalsTraditional();

    }

    // Interface to handle activities check callback
    private interface OnActivitiesCheckListener {
        void onActivitiesChecked(boolean hasActivities);
    }

    // To check if there are activities logged on the selected date
    private void checkActivitiesForDate(String selectedDay, OnActivitiesCheckListener listener) {
        // Reference to the user's activities for the specific date
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
                    // Handle potential errors
                    Toast.makeText(EcoTrackerHomeActivity.this, "Error checking activities", Toast.LENGTH_SHORT).show();
                    listener.onActivitiesChecked(false);
                }
            }
        });
    }

    private void retrieveAndDisplayTotalsTraditional() {

        long systemDate = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //set the time zone
        String dateToday = sdf.format(new Date(systemDate));


        // Reference to the "rawInputs" node
        DatabaseReference ref = databaseReference
                .child("users")
                .child(userId)
                .child("ecotracker")
                .child(String.valueOf(dateToday));

        // Retrieve totalTranspo
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

        // Retrieve totalFood
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

        // Retrieve totalShopping
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

        // Retrieve totalBills
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

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
