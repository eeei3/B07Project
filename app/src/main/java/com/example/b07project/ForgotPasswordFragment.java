package com.example.b07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ForgotPasswordFragment extends Fragment {
    private EditText editTextUserEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password, container, false);

        editTextUserEmail = view.findViewById(R.id.emailForResetPass);
        Button btnResetPassword = view.findViewById(R.id.loadingButton);
        ProgressBar btnProgressReset = view.findViewById(R.id.loadingProgress);
        ImageButton imageBtnBack = view.findViewById(R.id.backFromForgotPass);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnResetPassword.setVisibility(View.GONE);
                btnProgressReset.setVisibility(View.VISIBLE);
                // Retrieve the email the User entered
                String email = editTextUserEmail.getText().toString().trim();
                // Create communication channel with the Presenter
                LoginPresenter socket = new LoginPresenter(email);
                // Create object to hold if operation is successful or not
                SuccessListener watcher = new SuccessListener();
                // Create Listener to check if password reset successful or not.
                socket.setViewPipe(new LoginPresenter.PresenterViewPipe() {
                    @Override
                    public void onObjectReady(SuccessListener watcher) {
                        if (watcher.success) {
                            Toast.makeText(getContext(), "Email sent", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // Reset password
                socket.beginReset(watcher);
                finishLoading(btnResetPassword, btnProgressReset);
            }
        });
        imageBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
    private void finishLoading(Button btn, ProgressBar pgb) {
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                // After task is done, hide the progress bar and show the button again
                btn.setVisibility(View.VISIBLE);
                pgb.setVisibility(View.GONE);
            }}, 5000);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
