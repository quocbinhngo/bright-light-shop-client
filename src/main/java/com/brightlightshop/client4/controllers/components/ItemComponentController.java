package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.types.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ItemComponentController {
    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    public void setData(Item item){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(item.getImageUrl())));
        productImage.setImage(image);
        productName.setText(item.getTitle());
        productPrice.setText(String.valueOf(item.getRentalFee()));
    }
}
