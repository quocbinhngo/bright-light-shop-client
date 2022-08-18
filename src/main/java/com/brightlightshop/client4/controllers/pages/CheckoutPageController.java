package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.controllers.components.OrderDetailCheckoutComponentController;
import com.brightlightshop.client4.controllers.components.OrderDetailComponentController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.types.OrderDetail;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutPageController implements Initializable {

    @FXML
    private VBox orderDetailContainer;

    @FXML
    private Button returnButton;

    @FXML
    private Label totalValueLabel;

    private void setTotalValueLabel() {
        double totalValue = 0;
        for (OrderDetail orderDetail: CartModel.getOrderDetails()) {
            totalValue += orderDetail.getItem().getRentalFee() * orderDetail.getQuantity();
        }
        totalValueLabel.setText(String.valueOf(totalValue));
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (OrderDetail orderDetail: CartModel.getOrderDetails()) {
                String path = FXMLPath.getOrderDetailCheckoutComponentPath();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
                System.out.println(getClass().getResource(path));
                HBox hbox = fxmlLoader.load();

                OrderDetailCheckoutComponentController orderDetailComponentController = fxmlLoader.getController();
                orderDetailComponentController.setData(orderDetail);

                orderDetailContainer.getChildren().add(hbox);
            }

            setTotalValueLabel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
