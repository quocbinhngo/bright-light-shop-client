package com.brightlightshop.client4.controllers.pages;


import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;


public class ViewItemPageAdminController {

    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String createItemPostUrl = "http://localhost:8000/api/items";
    private final String userId = "62ec74b4f13a1bbf8d94f560";
    private final OkHttpClient client = new OkHttpClient();

    private RequestBody getCreateItemBody() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publishedYear", Integer.parseInt(publishedYearTextField.getText()));
        jsonObject.put("title", titleTextField.getText());
        jsonObject.put("rentalType", rentalTypeTextField.getText());
        jsonObject.put("copiesNumber", Integer.parseInt(copiesNumberTextField.getText()));
        jsonObject.put("rentalFee", Integer.parseInt(rentalFeeTextField.getText()));
        jsonObject.put("genre", genreTextField.getText());
        return RequestBody.create(jsonObject.toString(), JSON);
    }

    private String createItemPostRequest() throws IOException {
        RequestBody body = getCreateItemBody();
        Request request = new Request.Builder()
                .url(UrlConstant.createItem())
                .post(body)
                .addHeader("user-id", UserModel.getUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @FXML
    private TextField copiesNumberTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField imageUrlTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField publishedYearTextField;

    @FXML
    private TextField rentalFeeTextField;

    @FXML
    private TextField rentalTypeTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private Button submitButton;



    @FXML
    private void onSubmitButtonClick() throws IOException {
        System.out.println("Hello");

        String response = createItemPostRequest();
        System.out.println(response);


    }





}
