package com.brightlightshop.client4.constants;

public class UrlConstant {

    private static final String endpoint = "http://localhost:8000/api";

    public static String createCustomerAccount() {
       return endpoint + "/users/customers";
    }

    public static String createItem() {
        return endpoint + "/items";
    }
    public static String getItems() {
        return endpoint + "/items";
    }
}
