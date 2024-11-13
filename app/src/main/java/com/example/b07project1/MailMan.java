package com.example.b07project1;

import com.google.common.util.concurrent.Service;

public class MailMan {
    boolean success;

    public MailMan() {
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
