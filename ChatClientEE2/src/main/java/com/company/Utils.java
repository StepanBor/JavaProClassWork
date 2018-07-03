package com.company;

public class Utils {
    private static final String URL = "http://192.168.1.83";
    private static final int PORT = 8080;

    public static String getURL() {
        return URL + ":" + PORT;
    }
}
