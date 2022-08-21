package com.brightlightshop.client4.types;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String _id;
    private int rentalDuration;
    private boolean returned;
    private DateTime createdAt;
    private ArrayList<OrderDetail> orderDetails;

    public Order(String _id, int rentalDuration, boolean returned, DateTime createdAt, ArrayList<OrderDetail> orderDetails) {
        this._id = _id;
        this.rentalDuration = rentalDuration;
        this.returned = returned;
        this.createdAt = createdAt;
        this.orderDetails = orderDetails;
    }

    public Order(String _id, int rentalDuration, boolean returned, DateTime createdAt) {
        this._id = _id;
        this.rentalDuration = rentalDuration;
        this.returned = returned;
        this.createdAt = createdAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getTotalValue() {
        double totalValue = 0;
        for (OrderDetail orderDetail: orderDetails) {
            totalValue += orderDetail.getQuantity() * orderDetail.getItem().getRentalFee();
        }

        return totalValue;
    }

    public boolean isLateReturned() {
        return createdAt.getMillis() + (long) rentalDuration * 24 * 60 * 60 * 1000 < System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Order{" +
                "_id='" + _id + '\'' +
                ", rentalDuration=" + rentalDuration +
                ", returned=" + returned +
                ", createdAt=" + createdAt +
                ", orderDetails=" + orderDetails +
                '}';
    }

    public static final String COMPLETED = "Completed";
    public static final String RENTING = "Renting";

    public String getOrderStatus() {
        if (!returned) {
            return RENTING;
        }

        return COMPLETED;
    }


}
