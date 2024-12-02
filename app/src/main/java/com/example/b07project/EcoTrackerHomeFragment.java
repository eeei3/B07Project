package com.example.b07project;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EcoTrackerHomeFragment extends AppCompatActivity {

    private long selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecotrackerhome_fragment);

        CalendarView calendarView = findViewById(R.id.calendar_view);


        // Set a listener to get the selected date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Month is 0-based, so add 1 to the month
                month++;
                Toast.makeText(EcoTrackerHomeFragment.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1); // Subtract 1 to convert back to 0-based month
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                selectedDate = calendar.getTimeInMillis();
            }
        });

        //buttons
        Button buttonLog = findViewById(R.id.log_activity_button);
        Button buttonDetails = findViewById(R.id.detail_activity_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user

        //get the user's id
        String userId = user.getUid();

        //if user clicks Log button
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate > 0) {

                    Intent intent = new Intent(EcoTrackerHomeFragment.this, LogActivitiesActivity.class);
//                    long currentTimeMillis = System.currentTimeMillis();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    String today = sdf.format(new Date(currentTimeMillis));
                    intent.putExtra("selectedDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date(EcoTrackerHomeFragment.this.selectedDate)));
                    intent.putExtra("user_id", userId);
                    startActivity(intent);

                } else {
                    //if no date is selected, show to user
                    Toast.makeText(EcoTrackerHomeFragment.this, "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


        // Get a reference to the CalendarView




//        buttonDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

        //if no info --> toast: please log activity

        //take the userID
        //take the date selected
        //in order take the user from the database
        //start the DetailPageActivity


//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseUser user = auth.getCurrentUser();

//                if (selectedDate > 0) {
//                    String userId = user.getUid();
//                    Intent intent = new Intent(getActivity(), ActivityBreakdownActivity.class);
//
//                    Pass the selected date as an extra
//                    intent.putExtra("selectedDate", selectedDate);
//
//                    intent.putExtra("user_id", userId);
//
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), "Please select a date", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


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
            Intent intent = new Intent(EcoTrackerHomeFragment.this, EcoGauge.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.habit) {
            // Handle ecotracker action
            Intent intent = new Intent(EcoTrackerHomeFragment.this, HabitsMenu.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
