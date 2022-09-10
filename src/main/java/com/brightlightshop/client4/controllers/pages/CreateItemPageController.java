package com.brightlightshop.client4.controllers.pages;


import com.brightlightshop.client4.constants.ItemConstant;
import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.utils.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class CreateItemPageController implements Initializable {

    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String createItemPostUrl = "http://localhost:8000/api/items";
    private final String userId = "62ec74b4f13a1bbf8d94f560";
    private final OkHttpClient client = new OkHttpClient();
    private final FileChooser fileChooser = new FileChooser();
    private CloudinaryUploader cloudinaryUploader = new CloudinaryUploader();

    private boolean createItemSuccess = false;

    private File imageFile;
    private String imageUrl;
    private Item item;

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
    private Button uploadButton;

    @FXML
    private HBox navigationBar;

    @FXML
    void onSubmitButtonClick(ActionEvent event) throws Exception {
        // Validation
        if (!validateInput()) {
            return;
        }

        setMessageLabel("Adding item");
        imageUrl = cloudinaryUploader.uploadImage(imageFile, "item/" + getRentalTypeValue() + "/" + Generator.id());
        String response = createItemPostRequest();

        if (!createItemSuccess) {
            return;
        }

        item = JsonParser.getItem(new JSONObject(response));

        moveToUpdateItemPageAdmin(event);

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
        jsonObject.put("imageUrl", imageUrl);

        if (!(rentalTypeValue.equals("game"))) {
            jsonObject.put("genre", getGenreValue());
        }

        return RequestBody.create(jsonObject.toString(), JSON);
    }

    private String createItemPostRequest() throws IOException {
        RequestBody body = getCreateItemBody();
        Request request = new Request.Builder()
                .url(UrlConstant.createItem())
                .post(body)
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) != '2') {
                handleError(response.body().string());
            }

            createItemSuccess = true;
            return response.body().string();
        }
    }

    private void handleError(String message) {
        setMessageLabel(message);
        createItemSuccess = false;
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
        Component.numericTextField(publishedYearTextField);
        copiesNumberTextField.setText(String.valueOf(0));
        rentalFeeTextField.setText(String.valueOf(0));
    }

    private void setupChoiceBox() {
        rentalTypeChoiceBox.getItems().addAll(ItemConstant.rentalTypes);
        genreChoiceBox.getItems().addAll(ItemConstant.genres);

        // Set the usable of genre choice box based on rental type choice box
        rentalTypeChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String rentalTypeValue) -> {
                    rentalTypeValue = rentalTypeValue.toLowerCase();

                    // Divisible the genreChoiceBox
                    if (rentalTypeValue.equals(ItemConstant.game())) {
                        Component.setDisableChoiceBox(genreChoiceBox);
                        return;
                    }

                    // Enable choice box to choose genre
                    Component.setEnableChoiceBox(genreChoiceBox);
                } );
    }

    private void moveToUpdateItemPageAdmin(ActionEvent event) throws Exception {
        setMessageLabel("Create item successfully");
        disableInput();

        // Wait for 2 conds before move to the next page
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getUpdateItemPagePath()));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                UpdateItemPageController controller = fxmlLoader.getController();
                try {
                    controller.setData(item.get_id());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

                System.out.println("Change scene success");
            }
        }) , new KeyFrame(Duration.seconds(2)));

        timeline.playFromStart();
    }

    private void disableInput() throws InterruptedException {
        // Disable two button
        uploadButton.setDisable(true);
        submitButton.setDisable(true);

        // Disable all text fields and others
        publishedYearTextField.setDisable(true);
        titleTextField.setDisable(true);
        rentalTypeChoiceBox.setDisable(true);
        genreChoiceBox.setDisable(true);
        copiesNumberTextField.setDisable(true);
        rentalFeeTextField.setDisable(true);
    }

    public void addNavigationBar() {
        try {
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource(FXMLPath.getNavigationBarComponentPath()));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at CreateItemPage
            navigationBar.getChildren().add(hbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearLabel();
        setupTextField();
        setupChoiceBox();
        addNavigationBar();
    }

    @FXML
    void submitButtonEnteredCreateItemPage() {
        submitButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    void submitButtonExitedCreateItemPage() {
        submitButton.setStyle("-fx-background-color: #f1ab2c");
    }

    @FXML
    void uploadButtonEnteredCreateItemPage() {
        uploadButton.setStyle("-fx-background-color: #ececec, -fx-border-width: 1, -fx-border-color: BLACK");
    }

    @FXML
    void uploadButtonExitedCreateItemPage() {
        uploadButton.setStyle("-fx-background-color:  #d6d6d6, -fx-border-width: 1, -fx-border-color: BLACK");
    }
}
