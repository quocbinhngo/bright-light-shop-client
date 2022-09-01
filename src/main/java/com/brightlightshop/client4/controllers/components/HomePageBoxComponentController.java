package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.ViewItemPageCustomerController;
import com.brightlightshop.client4.types.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageBoxComponentController {

    private Item item;
    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private Button homepageComponentButton;

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

        Image image = new Image(item.getImageUrl());
        itemImage.setImage(image);
        itemName.setText(item.getTitle());
        itemPrice.setText(String.valueOf(item.getRentalFee()));

    }

    @FXML
    protected void itemBoxClickEntered() {
        homepageComponentButton.setStyle("-fx-background-color: #c3c3c3");
    }

    @FXML
    protected void itemBoxClickExited() {
        homepageComponentButton.setStyle("-fx-background-color: #dbdbdb");
    }
}
