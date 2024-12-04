package com.example.b07project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

// If you are testing, please make sure that you have registered with the app and make your own
// account, so I won't get bombarded with "reset your password" emails :)
// - Josh

// We will test with:
// joshualiu0307@gmail.com - mockitotest
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTesting {
    @Mock
    LoginFragment loginView;
    @Mock
    ForgotPasswordFragment resetView;
    @Mock
    FirebaseAuthHandler model;
    @InjectMocks
    LoginPresenter loginPresenter;
    @InjectMocks
    ForgetPresenter forgetPresenter;

    @Before
    public void setup() {
        // set up behaviour when model tries to log in
        doAnswer(invocation -> {
            String email = invocation.getArgument(0);
            String password = invocation.getArgument(1);

            if (email.equals("joshualiu0307@gmail.com") && password.equals("mockitotest")) {
                // Simulating a successful login
                loginView.success();
            }
            else if (!(email.equals("joshualiu0307@gmail.com"))) {
                // Simulating a failed login
                loginView.failure();
            }
            else if (!(password.equals("mockitotest"))) {
                loginView.failure();
            }
            return null;
        }).when(model).login(anyString(), anyString(), any());

        doAnswer(invocation -> {
            String email = invocation.getArgument(0);

            if (email.equals("joshualiu0307@gmail.com")) {
                resetView.success();
            }
            else {
                resetView.failure();
            }
            return null;
        }).when(model).resetPasswd(anyString(), any());
    }

    @Test
    public void testGoodLogin() {
        // Set up the input parameters (valid credentials)
        String email = "joshualiu0307@gmail.com";
        String password = "mockitotest";
        // Call the method
        loginPresenter.setEmail(email);
        loginPresenter.setPasswd(password);
        loginPresenter.beginAuthenticate();
        // Assert: Verify that the view's success() method is called
        verify(loginView).success();
    }

    @Test
    public void testBadPasswdLogin() {
        // Set up the input parameters (valid credentials)
        String email = "joshualiu0307@gmail.com";
        String password = "mockitotest1111";
        // Call the method
        loginPresenter.setEmail(email);
        loginPresenter.setPasswd(password);
        loginPresenter.beginAuthenticate();
        // Assert: Verify that the view's success() method is called
        verify(loginView).failure();
    }

    @Test
    public void testBadEmailLogin() {
        // Set up the input parameters (valid credentials)
        String email = "bromode";
        String password = "mockitotest";
        // Call the method
        loginPresenter.setEmail(email);
        loginPresenter.setPasswd(password);
        loginPresenter.beginAuthenticate();
        // Assert: Verify that the view's success() method is called
        verify(loginView).failure();
    }

    @Test
    public void testBadEmailPasswordLogin() {
        // Set up the input parameters (valid credentials)
        String email = "bromode";
        String password = "bruh";
        // Call the method
        loginPresenter.setEmail(email);
        loginPresenter.setPasswd(password);
        loginPresenter.beginAuthenticate();
        // Assert: Verify that the view's success() method is called
        verify(loginView).failure();
    }

    @Test
    public void testGoodResetPasswd() {
        String email = "joshualiu0307@gmail.com";
        forgetPresenter.setEmail(email);
        forgetPresenter.beginReset();
        verify(resetView).success();
    }

    @Test
    public void testBadResetPasswd() {
        String email = "bruh";
        forgetPresenter.setEmail(email);
        forgetPresenter.beginReset();
        verify(resetView).failure();
    }
}