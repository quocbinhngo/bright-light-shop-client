package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.ViewItemPageCustomerController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

public class ItemBoxComponentController {

    private CartModel cartModel;
    private UserModel userModel;

    private Item item;

    @FXML
    private ImageView imageView;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label rentalTypeLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void onItemBoxClick(ActionEvent event) throws IOException {
        String path = "/com/brightlightshop/client4/ViewItemPageCustomer.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());

        ViewItemPageCustomerController viewItemPageCustomerController = fxmlLoader.getController();
        viewItemPageCustomerController.setData(item.get_id());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setData(Item item){
        this.item = item;

        // Set data for JFX
        Image image = new Image(item.getImageUrl());
        imageView.setImage(image);
        titleLabel.setText(item.getTitle());
        rentalTypeLabel.setText(StringUtils.capitalize(item.getRentalType()));
        rentalFeeLabel.setText(String.valueOf(item.getRentalFee()));
        statusLabel.setText(item.getAvailableNumber() > 0 ? "Available" : "Out of stock");
    }




}
