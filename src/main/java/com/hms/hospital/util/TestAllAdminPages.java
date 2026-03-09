package com.hms.hospital.util;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestAllAdminPages {
    public static void main(String[] args) throws Exception {
        CookieManager cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        java.net.CookieHandler.setDefault(cm);
        
        // Login first
        test("login page", "http://localhost:8081/login", "GET");
        
        // POST login
        URI postUri = URI.create("http://localhost:8081/login");
        URL postUrl = postUri.toURL();
        HttpURLConnection post = (HttpURLConnection) postUrl.openConnection();
        post.setRequestMethod("POST");
        post.setDoOutput(true);
        String data = "username=admin&password=admin123";
        post.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        post.connect();
        int code = post.getResponseCode();
        System.out.println("Login POST: " + code);
        post.disconnect();
        
        // Test all admin pages
        String[] pages = {"appointments", "departments", "doctors", "patients", "reports"};
        for (String page : pages) {
            test(page, "http://localhost:8081/admin/" + page, "GET");
        }
    }
    
    private static void test(String name, String urlStr, String method) throws Exception {
        try {
            URI uri = URI.create(urlStr);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.connect();
            int code = conn.getResponseCode();
            System.out.println(String.format("%-30s: %d %s", name, code, code >= 400 ? "ERROR" : "OK"));
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(String.format("%-30s: EXCEPTION - %s", name, e.getMessage()));
        }
    }
}
