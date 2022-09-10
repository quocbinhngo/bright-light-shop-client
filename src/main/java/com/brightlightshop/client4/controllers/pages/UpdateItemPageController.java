package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.ItemConstant;
import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Dvd;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.types.Record;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import com.cloudinary.Url;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.IOException;
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
    private ImageView loadingImageView;

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
    void onAddMoreItemButtonClick(ActionEvent event) throws IOException {
        if (!validation()) {
            setMessageLabel("Quantity is invalid");
            return;
        }

        setMessageLabel("Adding more item...");
        String response = addItemQuantityRequest();
        if (response.equals("")) {
            return;
        }

        item = JsonParser.getItem(new JSONObject(response));
        updateUI();
        setMessageLabel("Add more item successfully");
    }

    @FXML
    void onBackButtonClick(ActionEvent event) throws Exception {
        String path = "/com/brightlightshop/client4/ViewItemsPage.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());

        ViewItemsPageController viewItemsPageController = fxmlLoader.getController();
        viewItemsPageController.getItems();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean validation() {
        return (quantityTextField.getText() != null && !quantityTextField.getText().equals(""));
    }

    private String getItemByIdRequest() throws Exception {
        Request request = new Request.Builder()
                .url(UrlConstant.getItemById(itemId))
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) != '2') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    private RequestBody getAddItemQuantityBody() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", Integer.parseInt(quantityTextField.getText()));
        return RequestBody.create(jsonObject.toString(), JsonParser.JSON);
    }

    private String addItemQuantityRequest() throws IOException {
        Request request = new Request.Builder()
                .url(UrlConstant.addItemQuantity(item.get_id()))
                .post(getAddItemQuantityBody())
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
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

    private void setMessageLabel(String message) {
        if (message == null) {
            messageLabel.setVisible(false);
            return;
        }

        messageLabel.setVisible(true);
        messageLabel.setText(message);
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

        // Set the image view
        imageView.setImage(new Image(item.getImageUrl()));
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

    public void addNavigationBar() {
        try {
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource(FXMLPath.getNavigationBarComponentPath()));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at UpdateItemPage
            navigationBar.getChildren().add(hbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNavigationBar();
        setupTextField();
        setMessageLabel(null);
    }
}
