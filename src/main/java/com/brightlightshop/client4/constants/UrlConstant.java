package com.brightlightshop.client4.constants;

import okhttp3.HttpUrl;

public class UrlConstant {

    private static final String endpoint = "https://bright-light-shop-oop.herokuapp.com/api";
//    private static final String endpoint = "http://localhost:8000/api";

    public static String createUser() {
        return endpoint + "/users/customers";
    }
    public static String createSession() {
        return endpoint + "/sessions";
    }

    public static String getUser() {
        return endpoint + "/users";
    }

    public static String getCustomers(){ return endpoint + "/users/customers";}

    public static String createCustomerAccount() {
       return endpoint + "/users/customers";
    }

    public static String searchCustomer(){ return endpoint + "/users/customers/searches"; }

    public static String addBalance() {
        return endpoint + "/users/customers/balance";
    }


    public static String createItem() {
        return endpoint + "/items";
    }
    public static String getItems() {
        return endpoint + "/items";
    }

    public static String searchItems() {
        return String.format("%s/items/searches", endpoint);
    }

    public static String getItemById(String itemId) {
        return endpoint + "/items/" + itemId;
    }

    public static String addItemQuantity(String itemId) {
        return String.format("%s/items/%s/quantity", endpoint, itemId);
    }

    public static String createOrder() {
        return endpoint + "/orders";
    }

    public static String getOrders() {
        return endpoint + "/orders";
    }

    public static String createOrderWithRewardPoint() {
        return endpoint + "/orders/reward";
    }

    public static String returnOrder(String id) {
        return endpoint + "/orders/" + id;
    }

}
