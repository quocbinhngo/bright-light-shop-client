package com.brightlightshop.client4.types;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;

public class OrderDetail {
    private Item item;
    private int quantity;

    public OrderDetail(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
