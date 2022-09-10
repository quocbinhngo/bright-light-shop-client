package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Dvd;
import com.brightlightshop.client4.types.Game;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.types.Record;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ViewItemPageCustomerController implements Initializable  {
    public HBox navigationBar;

    private final String getItemByIdGetUrl = "http://localhost:8000/api/items";
    private String itemId;
    private final OkHttpClient client = new OkHttpClient();
    private Item item;

    @FXML
    private Label availableNumberLabel;

    @FXML
    private HBox genreHbox;

    @FXML
    private Label genreLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label itemIdentifierLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label rentalTypeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox detailVbox;

    @FXML
    void onAddToCartButtonClick(ActionEvent event) {
        int rentalQuantity = Integer.parseInt(quantityTextField.getText());

        if (rentalQuantity == 0) {
            messageLabel.setText("Your rental quantity must be larger than 0");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        if (!CartModel.addToCart(item, rentalQuantity)) {
            messageLabel.setText("Your rental quantity is larger than available number");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        System.out.println(CartModel.getOrderDetails().size());
        messageLabel.setText("Add to cart successfully");
        messageLabel.setTextFill(Color.GREEN);

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

    private void setImageFromUrl(ImageView imageView, String urlStr)  {
        Image image = new Image(urlStr, true);
        imageView.setImage(image);
    }

    private void handleError() {

    }

    public void setData(String _id) {
        try {
            itemId = _id;
            String itemResponse = getItemByIdRequest();

            if (itemResponse.equals("")) {
                return;
            }

            JSONObject itemJsonObject = new JSONObject(itemResponse);
            item = JsonParser.getItem(itemJsonObject);

            setImageFromUrl(imageView, item.getImageUrl());

            if (item instanceof Dvd) {
                genreLabel.setText(((Dvd) item).getGenre().toUpperCase());
            }

            if (item instanceof Record) {
                genreLabel.setText(((Record) item).getGenre().toUpperCase());
            }

            if (item instanceof Game){
                detailVbox.getChildren().remove(genreHbox);
                //genreLabel.setText("None");
            }

            titleLabel.setText(String.format(item.getTitle()));
            itemIdentifierLabel.setText(item.getItemIdentifier());
            rentalTypeLabel.setText(item.getRentalType().toUpperCase());
            rentalFeeLabel.setText(String.valueOf(item.getRentalFee()));
            availableNumberLabel.setText(String.valueOf(item.getAvailableNumber()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getItemByIdRequest() throws Exception {
        Request request = new Request.Builder()
                .url(UrlConstant.getItemById(itemId))
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) == '4') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Component.numericTextField(quantityTextField);
        addNavigationBar();
    }

    //Add navigation bar
    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource(FXMLPath.getNavigationBarComponentPath()));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at homepage
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void addToCartButtonEnteredViewItemPageCus() {
        addToCartButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void addToCartButtonExitedViewItemPageCus() {
        addToCartButton.setStyle("-fx-background-color: #ffbd73");
    }

    @FXML
    protected void backButtonEnteredViewPageCus() {
        backButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void backButtonExitedViewPageCus() {
        backButton.setStyle("-fx-background-color: #ffbd73");
    }
}

