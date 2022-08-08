package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.types.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;


import java.net.URL;
import java.util.ResourceBundle;


public class ViewItemPageCustomerController implements Initializable  {

    @FXML
    private ImageView imageView;

    @FXML
    private Label genreLabel;

    @FXML
    private Label copiesNumberLabel;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label rentalTypeLabel;

    @FXML
    private Label titleLabel;

    private void setImageFromUrl(ImageView imageView, String urlStr)  {
        Image image = new Image(urlStr, true);
        imageView.setImage(image);
    }

    private final String getItemByIdGetUrl = "http://localhost:8000/api/items";
    private final String userId = "62ec74b4f13a1bbf8d94f560";
    private final String itemId = "62edd857bfadc5dd4a20416b";
    private final OkHttpClient client = new OkHttpClient();

    private String getItemByIdGetRequest() throws Exception {
        Request request = new Request.Builder()
                .url(getItemByIdGetUrl + String.format("/%s", itemId))
                .get()
                .addHeader("user-id", userId)
                .build();

        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            String itemResponse = getItemByIdGetRequest();
            JSONObject itemJsonObject = new JSONObject(itemResponse);
            Item item = Item.createItemFromJson(itemJsonObject);

            setImageFromUrl(imageView, item.getImageUrl());
            genreLabel.setText(item.getGenre());
            titleLabel.setText(item.getTitle());
            rentalTypeLabel.setText(item.getRentalType());
            rentalFeeLabel.setText(String.valueOf(item.getRentalFee()));
            copiesNumberLabel.setText(String.valueOf(item.getCopiesNumber()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

