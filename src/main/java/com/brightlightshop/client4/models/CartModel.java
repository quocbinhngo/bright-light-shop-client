package com.brightlightshop.client4.models;

import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.types.OrderDetail;

import java.util.ArrayList;
import java.util.Objects;

public class CartModel {
    private static ArrayList<OrderDetail> orderDetails = new ArrayList<>();

    public static ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public static void initCart() {
        orderDetails = new ArrayList<>();
    }

    public static boolean addToCart(Item item, int quantity) {
        for (OrderDetail orderDetail: orderDetails) {
            if (Objects.equals(orderDetail.getItem().get_id(), item.get_id())) {
                int newQuantity = orderDetail.getQuantity() + quantity;
                if (newQuantity > item.getAvailableNumber()) {
                    return false;
                }

                orderDetail.setQuantity(newQuantity);
                return true;
            }
        }

        if (quantity > item.getAvailableNumber()) {
            return false;
        }

        OrderDetail orderDetail = new OrderDetail(item, quantity);
        orderDetails.add(orderDetail);
        return true;
    }

    public static void clearAllItems() {
        orderDetails.clear();
    }
}
