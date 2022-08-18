package com.brightlightshop.client4.utils;

import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.types.Record;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonParser {

    public static User getUser(JSONObject json) {
        return null;
    }

    public static Item getItem(JSONObject json) {
        String _id = json.getString("_id");
        int itemCode = json.getInt("itemCode");
        int publishedYear = json.getInt("publishedYear");
        String title = json.getString("title");
        String rentalType = json.getString("rentalType");
        double rentalFee = json.getDouble("rentalFee");
        String genre = json.has("genre") ? json.getString("genre") : null;
        String imageUrl = json.getString("imageUrl");
        int copiesNumber = json.getInt("copiesNumber");
        int availableNumber = json.getInt("availableNumber");

        return switch (rentalType) {
            case "dvd" ->
                    new Dvd(_id, itemCode, publishedYear, title, rentalType, imageUrl, rentalFee, copiesNumber, availableNumber, genre);
            case "record" ->
                    new Record(_id, itemCode, publishedYear, title, rentalType, imageUrl, rentalFee, copiesNumber, availableNumber, genre);
            case "game" ->
                    new Game(_id, itemCode, publishedYear, title, rentalType, imageUrl, rentalFee, copiesNumber, availableNumber);
            default -> null;
        };
    }

    public static Order getOrder(JSONObject json) {
        String _id = json.getString("_id");
        int rentalDuration = json.getInt("rentalDuration");
        boolean returned  = json.getBoolean("returned");
        DateTime createdAt = new DateTime(json.getString("createdAt"));

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("orderDetails").length(); i++) {
            OrderDetail orderDetail = getOrderDetail(json.getJSONArray("orderDetails").getJSONObject(i));
            orderDetails.add(orderDetail);
        }

        return new Order(_id, rentalDuration, returned, createdAt, orderDetails);
    }

    public static OrderDetail getOrderDetail(JSONObject json) {
        Item item = getItem(json.getJSONObject("item"));
        int quantity = json.getInt("quantity");
        return new OrderDetail(item, quantity);
    }

    public static ArrayList<Order> getOrders(JSONArray json) {
        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            orders.add(getOrder(json.getJSONObject(i)));
        }

        return orders;
    }

    public static ArrayList<Item> getItems(JSONArray json) {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            items.add(getItem(json.getJSONObject(i)));
        }

        return items;
    }


}
