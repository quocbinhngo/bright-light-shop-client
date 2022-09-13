package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.ViewCustomerPageController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Customer;
import com.brightlightshop.client4.types.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerBoxComponentController {

    private CartModel cartModel;
    private UserModel userModel;

    private Customer customer;

    @FXML
    private Label customerTypeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label customerIdLabel;

    @FXML
    private Button customerBoxClick;

    @FXML
    void onCustomerBoxClick(ActionEvent event) throws Exception {
        String path = "/com/brightlightshop/client4/ViewCustomerPage.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());

        ViewCustomerPageController viewCustomerPageController = fxmlLoader.getController();
        viewCustomerPageController.setData(customer.get_id());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setData(Customer customer){
        this.customer = customer;

        // Set data for JFX
        nameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        customerTypeLabel.setText(customer.getAccountType());
        customerIdLabel.setText(customer.getCustomerIdentifier());
    }

    @FXML
    protected void customerBoxClickEnteredCustomerBoxComponentPage() {
        customerBoxClick.setStyle("-fx-text-fill: RED");
    }

    @FXML
    protected void customerBoxClickExitedCustomerBoxComponentPage() {
        customerBoxClick.setStyle("-fx-text-fill: BLACK");
    }


}
