package com.brightlightshop.client4.models;

import com.brightlightshop.client4.types.OrderDetail;

import java.util.ArrayList;

public class CartModel {
    private ArrayList<OrderDetail> orderDetails;

    public CartModel() {
        this.orderDetails = new ArrayList<>();
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "orderDetails=" + orderDetails +
                '}';
    }
}
