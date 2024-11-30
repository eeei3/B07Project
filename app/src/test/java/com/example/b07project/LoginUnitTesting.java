package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// We will test with:
// joshualiu0307@gmail.com - mockitotest
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTesting {

    @Mock
    LoginFragment view1;

    @Mock
    LoginFragment view2;


    @Test
    public void testGoodLogin() {
        when(view1.getEmail()).thenReturn("joshualiu0307@gmail.com");
        when(view1.getPassword()).thenReturn("mockitotest");
        LoginPresenter presenter = new LoginPresenter(view1);
        AsyncAuthComms watcher = new AsyncAuthComms();
        presenter.beginAuthenticate();
        verify(view1).success();
    }

    @Test
    public void testBadLogin() {
        when(view2.getEmail()).thenReturn("ihatejava@java.com");
        when(view2.getPassword()).thenReturn("bruh");
        LoginPresenter presenter = new LoginPresenter(view2);
        AsyncAuthComms watcher = new AsyncAuthComms();
        presenter.beginAuthenticate();
        verify(view2).failure();
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
