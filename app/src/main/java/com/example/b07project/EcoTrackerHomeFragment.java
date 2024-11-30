package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EcoTrackerHomeFragment extends Fragment {
    public static String userId;
    private DatabaseReference databaseReference;
    private long selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ecotrackerhome_fragment, container, false);

        // Get a reference to the CalendarView
        CalendarView calendarView = view.findViewById(R.id.calendar_view);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Set a listener to get the selected date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Month is 0-based, so add 1 to the month
                month++;
                Toast.makeText(requireContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
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
        Button buttonLog = view.findViewById(R.id.log_activity_button);
        Button buttonDetails = view.findViewById(R.id.detail_activity_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user

        //get the user's id
        if (user != null) {
            userId = user.getUid();
        }


        // Log button click listener
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate > 0) {
                    // Check if activities have already been logged for this date
                    checkActivitiesForDate(selectedDate, new OnActivitiesCheckListener() {
                        @Override
                        public void onActivitiesChecked(boolean hasActivities) {
                            if (!hasActivities) {
                                // No activities logged for this date, proceed to log
                                Intent intent = new Intent(getActivity(), LogActivitiesActivity.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("user_id", userId);
                                startActivity(intent);
                            } else {
                                // Activities already exist for this date
                                Toast.makeText(getActivity(), "Activities history found! Please go directly to detail page", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // No date selected
                    Toast.makeText(getActivity(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Details button click listener
        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate > 0) {
                    // Check if activities exist for this date before viewing details
                    checkActivitiesForDate(selectedDate, new OnActivitiesCheckListener() {
                        @Override
                        public void onActivitiesChecked(boolean hasActivities) {
                            //if there are activities logged before, inflate the detail page
                            if (hasActivities) {
                                // Activities exist, proceed to view details
                                Intent intent = new Intent(getActivity(), DetailPageActivity.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("user_id", userId);
                                startActivity(intent);
                            } else {
                                // No activities logged for this date, toast a message
                                Toast.makeText(getActivity(), "No activities history found! Please log activities first", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // No date selected
                    Toast.makeText(getActivity(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    // Interface to handle activities check callback
    private interface OnActivitiesCheckListener {
        void onActivitiesChecked(boolean hasActivities);
    }

    //To check if there are activities exist in the selected date
    private void checkActivitiesForDate(long selectedDate, OnActivitiesCheckListener listener) {
        // Reference to the user's activities for the specific date
        DatabaseReference userActivitiesRef = databaseReference
                .child("users")
                .child(userId)
                .child("ecotracker")
                .child(String.valueOf(selectedDate));

        userActivitiesRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        // Date exists in the database
                        listener.onActivitiesChecked(true);
                    } else {
                        // Date does not exist in the database
                        listener.onActivitiesChecked(false);
                    }
                } else {
                    // Handle potential errors
                    Toast.makeText(getActivity(), "Error checking activities", Toast.LENGTH_SHORT).show();
                    listener.onActivitiesChecked(false);
                }
            }
        });
    }
}
