package com.brightlightshop.client4.constants;

public class UrlConstant {

    private static final String endpoint = "http://localhost:8000/api";

    public static String createCustomerAccount() {
       return endpoint + "/users/customers";
    }

<<<<<<< HEAD
=======
    public static String createItem() {
        return endpoint + "/items";
    }
>>>>>>> 36a3b9e (help na)
    public static String getItems() {
        return endpoint + "/items";
    }
}
