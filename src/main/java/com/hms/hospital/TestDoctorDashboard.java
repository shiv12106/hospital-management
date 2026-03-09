package com.hms.hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class TestDoctorDashboard {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8081";
        String[] doctorPages = {
            "/doctor/dashboard",
            "/doctor/patients",
            "/doctor/appointments",
            "/doctor/reports"
        };

        String doctorAuth = Base64.getEncoder().encodeToString("doctor:doctor123".getBytes());

        System.out.println("=== DOCTOR DASHBOARD TEST ===\n");

        for (String page : doctorPages) {
            int statusCode = getStatusCode(baseUrl + page, doctorAuth);
            String result = statusCode == 200 ? "✓ OK" : "✗ FAIL";
            System.out.println(result + " " + page + " (" + statusCode + ")");
        }

        System.out.println("\nTesting patient details endpoint:");
        int statusCode = getStatusCode(baseUrl + "/doctor/patients/1", doctorAuth);
        String result = statusCode == 200 ? "✓ OK" : "✗ FAIL";
        System.out.println(result + " /doctor/patients/1 (" + statusCode + ")");

        System.out.println("\n=== TEST COMPLETE ===");
    }

    private static int getStatusCode(String urlString, String authHeader) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + authHeader);
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int statusCode = connection.getResponseCode();
            connection.disconnect();
            return statusCode;
        } catch (IOException e) {
            return -1;
        }
    }
}
