package com.brightlightshop.client4.controllers.pages;


import com.brightlightshop.client4.constants.ItemConstant;
import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.utils.CloudinaryUploader;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.Generator;
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
import javafx.stage.FileChooser;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class AddItemPageAdminController implements Initializable {

    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String createItemPostUrl = "http://localhost:8000/api/items";
    private final String userId = "62ec74b4f13a1bbf8d94f560";
    private final OkHttpClient client = new OkHttpClient();
    private final FileChooser fileChooser = new FileChooser();
    private CloudinaryUploader cloudinaryUploader = new CloudinaryUploader();

    private File imageFile;


    @FXML
    private TextField copiesNumberTextField;

    @FXML
    private ChoiceBox<String> genreChoiceBox;

    @FXML
    private ImageView imageView;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField publishedYearTextField;

    @FXML
    private TextField rentalFeeTextField;

    @FXML
    private ChoiceBox<String> rentalTypeChoiceBox;

    @FXML
    private Button submitButton;

    @FXML
    private TextField titleTextField;

    @FXML
    void onSubmitButtonClick(ActionEvent event) throws IOException {
        // Validation
        if (!validateInput()) {
            return;
        }

        // Upload image to cloudinary
        setLoading(true);
        System.out.println("Uploading");
        String url = cloudinaryUploader.uploadImage(imageFile, "item/" + getRentalTypeValue() + "/" + Generator.id());

        String response = createItemPostRequest();
        setLoading(false);

        clearInput();
    }



    @FXML
    void onUploadButtonClick(ActionEvent event) {
        // Set up file chooser
        fileChooser.setTitle("Choose Item Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif", "*.jpg"));
        File tempImageFile = fileChooser.showOpenDialog(null);

        // Check whether the file is invalid
        if (tempImageFile == null) {
            setMessageLabel("Uploaded image is invalid");
            return;
        }

        // Update the image view
        setImageFile(tempImageFile);
    }

    private String getRentalTypeValue() {
        return rentalTypeChoiceBox.getValue().toLowerCase();
    }

    private String getGenreValue() {
        return genreChoiceBox.getValue().toLowerCase();
    }
    private boolean validateInput() {
        if (imageFile == null) {
            setMessageLabel("Image is required");
            return false;
        }

        if (Objects.equals(publishedYearTextField.getText(), "")) {
            setMessageLabel("Published year is required");
            return false;
        }

        if (Objects.equals(titleTextField.getText(), "")) {
            setMessageLabel("Title is required");
            return false;
        }

        if (rentalTypeChoiceBox.getValue() == null) {
            setMessageLabel("Rental type is required");
            return false;
        }

        if (!Objects.equals(getRentalTypeValue(), "game") && genreChoiceBox.getValue() == null) {
            setMessageLabel("Genre is required");
            return false;
        }

        if (Integer.parseInt(copiesNumberTextField.getText()) == 0) {
            setMessageLabel("Copies number must be larger than 0");
            return false;
        }

        if (Integer.parseInt(rentalFeeTextField.getText()) == 0) {
            setMessageLabel("Rental fee must be larger than 0");
            return false;
        }

        return true;
    }

    private RequestBody getCreateItemBody() {
        String rentalTypeValue = getRentalTypeValue();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publishedYear", Integer.parseInt(publishedYearTextField.getText()));
        jsonObject.put("title", titleTextField.getText());
        jsonObject.put("rentalType", rentalTypeValue);
        jsonObject.put("copiesNumber", Integer.parseInt(copiesNumberTextField.getText()));
        jsonObject.put("rentalFee", Integer.parseInt(rentalFeeTextField.getText()));

        System.out.println(rentalTypeValue);

        if (!(rentalTypeValue.equals("game"))) {
            System.out.println("Get genre");
            jsonObject.put("genre", getGenreValue());
        }

        return RequestBody.create(jsonObject.toString(), JSON);
    }

    private String createItemPostRequest() throws IOException {
        RequestBody body = getCreateItemBody();
        Request request = new Request.Builder()
                .url(UrlConstant.createItem())
                .post(body)
                .addHeader("user-id", userId)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private void clearInput() {
        publishedYearTextField.setText("");
        titleTextField.setText("");
        rentalTypeChoiceBox.setValue(null);
        genreChoiceBox.setValue(null);
        copiesNumberTextField.setText("0");
        rentalFeeTextField.setText("0");
        setImageFile(null);
    }

    private void clearLabel() {
        messageLabel.setVisible(false);
        messageLabel.setText("");
    }

    private void setImageFile(File file) {
        imageFile = file;

        if (imageFile != null) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        }
    }


    private void setMessageLabel(String str) {
        messageLabel.setVisible(true);
        messageLabel.setText(str);
    }

    private void setupTextField() {
        Component.numericTextField(copiesNumberTextField);
        Component.numericTextField(rentalFeeTextField);
        copiesNumberTextField.setText(String.valueOf(0));
        rentalFeeTextField.setText(String.valueOf(0));
    }

    private void setupChoiceBox() {
        rentalTypeChoiceBox.getItems().addAll(ItemConstant.rentalTypes);
        genreChoiceBox.getItems().addAll(ItemConstant.genres);
        rentalTypeChoiceBox.setOnAction(this::onRentalTypeChoiceBoxClick);
    }

    private void onRentalTypeChoiceBoxClick(ActionEvent event) {
        String rentalTypeValue = rentalTypeChoiceBox.getValue().toLowerCase();

        // Divisible the genreChoiceBox
        if (rentalTypeValue.equals(ItemConstant.game())) {
            Component.setDisableChoiceBox(genreChoiceBox);
            return;
        }

        // Enable choice box to choose genre
        Component.setEnableChoiceBox(genreChoiceBox);
    }

    private void setLoading(Boolean loading) {
        if (loading) {
            setMessageLabel("Adding item...");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearLabel();
        setupTextField();
        setupChoiceBox();
    }
}
