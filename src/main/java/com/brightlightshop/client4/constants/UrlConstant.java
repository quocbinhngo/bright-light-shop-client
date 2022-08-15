package com.brightlightshop.client4.constants;

public class UrlConstant {

    private static final String endpoint = "http://localhost:8000/api";

    public static String getItems() {
        return endpoint + "/items";
    }
}
