package com.example.demo;


public enum ExternalApiStatus {
    SUCCESS("1001"),
    FAILURE("7004");


    public final String label;

    private ExternalApiStatus(String label) {
        this.label = label;
    }
}
