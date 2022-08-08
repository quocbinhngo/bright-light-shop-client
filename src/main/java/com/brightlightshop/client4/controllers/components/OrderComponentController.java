package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.types.Order;
import com.brightlightshop.client4.types.OrderDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class OrderComponentController {

    @FXML
    private Button viewInDetailButton;
    @FXML
    private VBox orderDetailContainer;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalValueLabel;

    private ArrayList<OrderDetail> orderDetails;


    public void setData(Order order) throws IOException {

        orderDetails = order.getOrderDetails();
        totalValueLabel.setText(String.valueOf(order.getTotalValue()));
        statusLabel.setText(Order.getOrderStatus(order.isReturned()));

        for (OrderDetail orderDetail: orderDetails) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/OrderDetailComponent.fxml"));

            HBox hBox = fxmlLoader.load();
            OrderDetailComponentController orderDetailComponentController = fxmlLoader.getController();

            orderDetailComponentController.setData(orderDetail);

            this.orderDetailContainer.getChildren().add(hBox);
        }

    }


    @FXML
    private void onViewInDetailButtonClick(ActionEvent actionEvent) {

    }
}
