package com.brightlightshop.client4.constants;

public class UrlConstant {

    private static final String endpoint = "http://localhost:8000/api";

    public static String getUser() {
        return endpoint + "/users";
    }

    public static String getCustomers(){ return endpoint + "/users/customers";}

    public static String createCustomerAccount() {
       return endpoint + "/users/customers";
    }



    public static String createItem() {
        return endpoint + "/items";
    }
    public static String getItems() {
        return endpoint + "/items";
    }

    public static String createOrder() {
        return endpoint + "/orders";
    }

    public static String returnOrder(String id) {
        return endpoint + "/orders/" + id;
    }

}
