package com.hms.hospital.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TestAdminPage {
    public static void main(String[] args) throws Exception {
        CookieManager cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        java.net.CookieHandler.setDefault(cm);
        
        // GET login page to obtain JSESSIONID
        URI loginUri = URI.create("http://localhost:8081/login");
        URL loginUrl = loginUri.toURL();
        HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        read(conn);
        conn.disconnect();
        
        // submit credentials
        URI postUri = URI.create("http://localhost:8081/login");
        URL postUrl = postUri.toURL();
        HttpURLConnection post = (HttpURLConnection) postUrl.openConnection();
        post.setRequestMethod("POST");
        post.setDoOutput(true);
        String data = "username=" + URLEncoder.encode("admin", "UTF-8")
                    + "&password=" + URLEncoder.encode("admin123", "UTF-8");
        post.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        post.connect();
        int code = post.getResponseCode();
        System.out.println("Login response code: " + code);
        read(post);
        post.disconnect();
        
        // access admin appointments
        URI adminUri = URI.create("http://localhost:8081/admin/appointments");
        URL adminUrl = adminUri.toURL();
        HttpURLConnection admin = (HttpURLConnection) adminUrl.openConnection();
        admin.setRequestMethod("GET");
        admin.connect();
        int adminCode = admin.getResponseCode();
        System.out.println("Admin page response code: " + adminCode);
        if (adminCode >= 400) {
            BufferedReader errIn = new BufferedReader(new InputStreamReader(admin.getErrorStream()));
            String line;
            System.out.println("--- error response ---");
            while ((line = errIn.readLine()) != null) {
                System.out.println(line);
            }
            errIn.close();
        } else {
            read(admin);
        }
        admin.disconnect();
    }
    private static void read(HttpURLConnection conn) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            // limit output
        }
        in.close();
    }
}
