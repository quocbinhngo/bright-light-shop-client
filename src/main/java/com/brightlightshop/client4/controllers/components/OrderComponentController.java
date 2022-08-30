package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.ViewOrderPageController;
import com.brightlightshop.client4.types.Order;
import com.brightlightshop.client4.types.OrderDetail;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderComponentController implements Initializable {
    private Order order;

    @FXML
    private VBox orderDetailContainer;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalValueLabel;

    @FXML
    private Button viewInDetailButton;


    @FXML
    private void onViewInDetailButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewOrderPagePath()));
            Scene scene = new Scene(fxmlLoader.load());

            ViewOrderPageController controller = fxmlLoader.getController();
            controller.setData(order);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }

    }

    public void setData(Order order) throws IOException {
        this.order = order;
        double totalValue = order.getTotalValue();

        totalValueLabel.setText(String.valueOf(totalValue));
        statusLabel.setText(order.getOrderStatus());

        for (OrderDetail orderDetail: order.getOrderDetails()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getOrderDetailComponentPath()));

            HBox hBox = fxmlLoader.load();
            OrderDetailComponentController orderDetailComponentController = fxmlLoader.getController();

            orderDetailComponentController.setData(orderDetail);

            this.orderDetailContainer.getChildren().add(hBox);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void viewInDetailButtonEnteredOrderComponentPage() {
        viewInDetailButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void viewInDetailButtonExitedOrderComponentPage() {
        viewInDetailButton.setStyle("-fx-background-color:  #f1ab2c");
    }
}
