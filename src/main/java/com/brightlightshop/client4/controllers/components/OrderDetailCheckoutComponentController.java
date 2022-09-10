package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.CheckoutPageController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.types.OrderDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class OrderDetailCheckoutComponentController {

    @FXML
    private Label totalPerProduct;
    private OrderDetail orderDetail;

    private CheckoutPageController subscriber;

    @FXML
    private ImageView imageView;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button removeButton;

//    @FXML
//    private Label totalPerProduct;


    @FXML
    void onAddButtonClick(ActionEvent event) throws IOException {
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
        notifySubscriber();
    }

    @FXML
    void onMinusButtonClick(ActionEvent event) throws IOException {
        orderDetail.setQuantity(orderDetail.getQuantity() - 1);

        if (orderDetail.getQuantity() == 0) {
            removeOrderDetail();
        }

        notifySubscriber();
    }


    @FXML
    protected void removeButtonOnEnteredCheckOutPage() {
        removeButton.setStyle("-fx-border-color: RED;-fx-background-color: transparent;-fx-text-fill: RED");
    }
    @FXML
    void removeButtonOnExitedCheckOutPage() {
        removeButton.setStyle("-fx-border-color: #c3c3c3;-fx-background-color: transparent;-fx-text-fill: BLACK");
    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) throws IOException {
        removeOrderDetail();
        notifySubscriber();
    }

    public void setData(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        setFXML();

    }

    public void setFXML() {
        double total = orderDetail.getItem().getRentalFee() * orderDetail.getQuantity();
        Image image = new Image(orderDetail.getItem().getImageUrl());
        imageView.setImage(image);
        quantityLabel.setText(String.valueOf(orderDetail.getQuantity()));
        totalPerProduct.setText(String.valueOf(total));
        titleLabel.setText(orderDetail.getItem().getTitle());
        rentalFeeLabel.setText(String.valueOf(orderDetail.getItem().getRentalFee()));
        totalPerProduct.setText(String.valueOf(orderDetail.getItem().getRentalFee() * orderDetail.getQuantity()));
    }

    public void setSubscriber(CheckoutPageController checkoutPageController) {
        subscriber = checkoutPageController;
    }

    public void notifySubscriber() throws IOException {
        subscriber.update();
    }

    public void removeOrderDetail() {
        CartModel.getOrderDetails().removeIf(curOrderDetail -> Objects.equals(curOrderDetail.getItem().get_id(), orderDetail.getItem().get_id()));
    }
}
