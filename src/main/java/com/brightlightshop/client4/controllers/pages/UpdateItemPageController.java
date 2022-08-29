package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.ItemConstant;
import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Dvd;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.types.Record;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateItemPageController implements Initializable {
    private String itemId;
    private Item item;

    private final OkHttpClient client = new OkHttpClient();

    @FXML
    private Button addMoreItemButton;

    @FXML
    private Button backButton;

    @FXML
    private Label copiesNumberLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label messageLabel;

    @FXML
    private HBox navigationBar;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label rentalTypeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void onAddMoreItemButtonClick(ActionEvent event) {

    }

    @FXML
    void onBackButtonClick(ActionEvent event) {

    }


    private String getItemByIdRequest() throws Exception {
        Request request = new Request.Builder()
                .url(UrlConstant.getItemById(itemId))
                .get()
                .addHeader("user-id", "62ec74b4f13a1bbf8d94f560")
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) != '2') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    private void handleError() {

    }

    private void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private void updateUI() {
        // Set the value for text field
        titleLabel.setText(item.getTitle());
        copiesNumberLabel.setText(String.valueOf(item.getCopiesNumber()));
        rentalFeeLabel.setText(String.valueOf(item.getRentalFee()));

        // Set the value for choice box
        rentalTypeLabel.setText(StringUtils.capitalize(item.getRentalType()));

        // Set the genre for record and dvd
        switch (item.getRentalType()) {
            case "record" -> {
                genreLabel.setText(StringUtils.capitalize(((Record) item).getGenre()));
            }
            case "dvd" -> {
                genreLabel.setText(StringUtils.capitalize(((Dvd) item).getGenre()));
            }

        }
    }

    public void setData(String itemId) throws Exception {
        setItemId(itemId);
        String response = getItemByIdRequest();
        item = JsonParser.getItem(new JSONObject(response));
        updateUI();
    }

    private void setupTextField() {
        Component.numericTextField(quantityTextField);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTextField();
    }
}
