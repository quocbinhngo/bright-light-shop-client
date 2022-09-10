package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.types.OrderDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrderDetailComponentController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label totalPrice;

    public void setData(OrderDetail orderDetail) {
        double total = orderDetail.getItem().getRentalFee() * orderDetail.getQuantity();
        Image image = new Image(orderDetail.getItem().getImageUrl());
        imageView.setImage(image);
        quantityLabel.setText(String.valueOf(orderDetail.getQuantity()));
        titleLabel.setText(orderDetail.getItem().getTitle());
        rentalFeeLabel.setText(String.valueOf(orderDetail.getItem().getRentalFee()));
        totalPrice.setText(String.valueOf(orderDetail.getItem().getRentalFee() * orderDetail.getQuantity()));
    }
}
