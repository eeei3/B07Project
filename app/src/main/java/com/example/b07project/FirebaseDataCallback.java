package com.example.b07project;

import java.util.Map;

public interface FirebaseDataCallback {
    void onDataReceived(Map<String, Double> emissionsData);
    void onDataNotFound();
    void onDataFetchFailed();
}
