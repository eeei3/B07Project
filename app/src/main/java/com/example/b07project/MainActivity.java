package com.example.b07project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_upd);

        if (savedInstanceState == null) {
            loadFragment(new LoginFragment());
        }

//        Button loadingButton = findViewById(R.id.loadingButton);
//        ProgressBar loadingProgress = findViewById(R.id.loadingProgress);
//
//        loadingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Hide the button and show the progress bar
//                loadingButton.setVisibility(View.GONE);
//                loadingProgress.setVisibility(View.VISIBLE);
//
//                // Simulate loading or a network operation
//                performLoadingTask(loadingButton, loadingProgress);
//            }
//        });
//    }

// Simulate a loading task
//    private void performLoadingTask(Button btn, ProgressBar pgb) {
//        // For demo purposes, we just delay for 3 seconds
//        btn.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // After task is done, hide the progress bar and show the button again
//                btn.setVisibility(View.GONE);
//                pgb.setVisibility(View.VISIBLE);
//            }
//        }, 3000);  // 3 second delay
//    }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
