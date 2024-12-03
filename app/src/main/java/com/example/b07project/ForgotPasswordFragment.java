package com.example.b07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * ForgotPasswordFragment is the fragment displayed when the user clicks Forgot Password.
 */
public class ForgotPasswordFragment extends Fragment {
    private EditText editTextUserEmail;

    ForgetPresenter presenter;

    /**
     * onCreateView in the method responsible for inflating the view and binding the UI components.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return                   view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password, container, false);
        presenter = new ForgetPresenter(this);

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
                presenter.setEmail(email);
                // Reset password
                presenter.beginReset();
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

    /**
     * finishLoading is the method handling the UI behaviour when clicked Reset.
     *
     * @param btn reset button
     * @param pgb progress bar to show loading
     */
    private void finishLoading(Button btn, ProgressBar pgb) {
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                // After task is done, hide the progress bar and show the button again
                btn.setVisibility(View.VISIBLE);
                pgb.setVisibility(View.GONE);
            }}, 5000);
    }

    /**
     * success is the method to notify the user that password is reset successfully, also sends
     * the user to the login fragment.
     */
    public void success() {
        Toast.makeText(getContext(), "Email Sent", Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * failure is the method to notify the user that password is reset unsuccessfully.
     */
    public void failure() {
        Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
    }
}
