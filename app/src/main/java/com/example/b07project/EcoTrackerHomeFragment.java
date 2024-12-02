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

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EcoTrackerHomeFragment extends Fragment {

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
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Month is 0-based, so add 1 to the month
                selectedDate = view.getDate();
                String selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date(EcoTrackerHomeFragment.this.selectedDate));
                Toast.makeText(getActivity(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        //buttons
        Button buttonLog = view.findViewById(R.id.log_activity_button);
        Button buttonDetails = view.findViewById(R.id.detail_activity_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();  // Get the current authenticated user

        //get the user's id
        String userId = user.getUid();

        //if user clicks Log button
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate > 0) {

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
        return view;
    }
}
