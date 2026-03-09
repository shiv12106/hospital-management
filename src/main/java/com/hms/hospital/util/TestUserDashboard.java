package com.hms.hospital.util;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestUserDashboard {
    public static void main(String[] args) throws Exception {
        CookieManager cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        java.net.CookieHandler.setDefault(cm);
        
        // Test login page
        test("login page", "http://localhost:8081/login", "GET");
        
        // Login as user
        URI postUri = URI.create("http://localhost:8081/login");
        URL postUrl = postUri.toURL();
        HttpURLConnection post = (HttpURLConnection) postUrl.openConnection();
        post.setRequestMethod("POST");
        post.setDoOutput(true);
        String data = "username=user&password=user123";
        post.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        post.connect();
        int code = post.getResponseCode();
        System.out.println("User login POST: " + code);
        post.disconnect();
        
        // Give session time to establish
        Thread.sleep(500);
        
        // Test all user pages
        String[] pages = {"dashboard", "appointments", "prescriptions", "alerts", "records", "settings", "doctors", "profile"};
        System.out.println("\n=== USER DASHBOARD PAGES ===");
        for (String page : pages) {
            test(page, "http://localhost:8081/user/" + page, "GET");
        }
    }
    
    private static void test(String name, String urlStr, String method) throws Exception {
        try {
            URI uri = URI.create(urlStr);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setConnectTimeout(5000);
            conn.connect();
            int code = conn.getResponseCode();
            String status = code >= 500 ? "ERROR" : code >= 400 ? "NOT FOUND" : "OK";
            String symbol = code >= 500 ? "✗" : code >= 400 ? "?" : "✓";
            System.out.println(String.format("%s %-20s: %d %s", symbol, name, code, status));
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(String.format("✗ %-20s: EXCEPTION - %s", name, e.getMessage()));
        }
    }
}
