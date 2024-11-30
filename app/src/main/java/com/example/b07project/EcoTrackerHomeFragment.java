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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EcoTrackerHomeFragment extends Fragment {
    public static String userId;

    private long selectedDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ecotrackerhome_fragment, container, false);

        // Get a reference to the CalendarView
        CalendarView calendarView = view.findViewById(R.id.calendar_view);


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


        //if user clicks Log button
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate > 0) {
                    System.out.println(userId);
                    System.out.println("hello");

                    Intent intent = new Intent(getActivity(), LogActivitiesActivity.class);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);

                } else {
                    //if no date is selected, show to user
                    Toast.makeText(getActivity(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate > 0) {

                    Intent intent = new Intent(getActivity(), DetailPageActivity.class);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);

                } else {
                    //if no date is selected, show to user
                    Toast.makeText(getActivity(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}