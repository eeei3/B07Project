package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// We will test with:
// joshualiu0307@gmail.com - mockitotest
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTesting {

    @Mock
    LoginFragment view;


    @Test
    public void testGoodLogin() {
        when(view.getEmail()).thenReturn("joshualiu0307@gmail.com");
        when(view.getPassword()).thenReturn("mockitotest");
        LoginPresenter presenter = new LoginPresenter(view);
        SuccessListener watcher = new SuccessListener();
        presenter.beginAuthenticate(watcher);
        verify(view).success();
    }

    @Test
    public void testBadLogin() {
        when(view.getEmail()).thenReturn("ihatejava@java.com");
        when(view.getPassword()).thenReturn("bruh");
        LoginPresenter presenter = new LoginPresenter(view);
        SuccessListener watcher = new SuccessListener();
        presenter.beginAuthenticate(watcher);
        verify(view).failure();
    }

//    @Test
//    public void testGoodPasswdReset() {
//
//    }
//
//    @Test
//    public void testBadPasswdReset() {
//
//    }
}
