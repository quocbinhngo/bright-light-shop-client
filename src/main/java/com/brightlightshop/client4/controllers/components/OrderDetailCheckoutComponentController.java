package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.types.OrderDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrderDetailCheckoutComponentController {
    private OrderDetail orderDetail;

    @FXML
    private ImageView imageView;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void onAddButtonClick(ActionEvent event) {
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
        setFXML();
    }

    @FXML
    void onMinusButtonClick(ActionEvent event) {
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
        setFXML();
    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) {

    }

    public void setData(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        setFXML();
    }

    public void setFXML() {
        Image image = new Image(orderDetail.getItem().getImageUrl());
        imageView.setImage(image);
        quantityLabel.setText(String.valueOf(orderDetail.getQuantity()));
        titleLabel.setText(orderDetail.getItem().getTitle());
        rentalFeeLabel.setText(String.valueOf(orderDetail.getItem().getRentalFee()));
    }


}
