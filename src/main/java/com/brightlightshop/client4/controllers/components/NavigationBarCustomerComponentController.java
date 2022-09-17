package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.pages.ViewItemsPageController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NavigationBarCustomerComponentController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final OkHttpClient client = new OkHttpClient();

    @FXML
    private Button bookingButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button logoButton;

    @FXML
    private TextField searchBarTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button shopButton;

    @FXML
    private Button userInformationButton;

    @FXML
    private Button cartButton;

    @FXML
    private Label usernameNavigationBar;


    @FXML
    void onBookingButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewOrdersPagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void handleImgLogo(ActionEvent event) throws IOException {
        String path ="/com/brightlightshop/client4/HomePage.fxml";
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 880);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onCartButtonClick(ActionEvent event) throws IOException {
        String path = "/com/brightlightshop/client4/CheckoutPage.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //change scene to ViewItemsPage
    @FXML
    void onShopButtonClick(ActionEvent event) throws Exception {
        moveViewItemsPage(event, false, null);
    }

    // Change to view order page
    @FXML
    void onOrderButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewOrdersPagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onUserInfoButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getUserInfoPagePath()));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) throws Exception {
        String response = searchItemRequest();
        if (response.equals("")) {
            moveViewItemsPage(event, true, null);
            return;
        }

        // Get the item
        ArrayList<Item> items = JsonParser.getItems(new JSONArray(response));

        // move to view items page
        moveViewItemsPage(event, true, items);
    }

    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException {
        UserModel.setCurrentUser(null);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getAuthPagePath()));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void moveViewItemsPage(ActionEvent event, boolean isSearch, ArrayList<Item> items) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewItemsPagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        ViewItemsPageController controller = fxmlLoader.getController();

        if (items != null) {
            controller.setItems(items);
        } else {
            if (isSearch) {
                controller.showNoResult();
            } else {
                controller.getItems();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public String searchItemRequest() throws IOException {
        Request request = new Request.Builder()
                .url(getUrl())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try(Response response = client.newCall(request).execute()) {

            if (String.valueOf(response.code()).charAt(0) != '2') {
                return "";
            }

            return response.body().string();
        }
    }

    private void handleError() {

    }

    private String getUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlConstant.searchItems()).newBuilder();

        if (searchBarTextField.getText() == null) {
            urlBuilder.addQueryParameter("search", "");
        } else {
            urlBuilder.addQueryParameter("search", searchBarTextField.getText());
        }

        return urlBuilder.build().toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameNavigationBar.setText(UserModel.getCurrentUser().getFirstName() + " " + UserModel.getCurrentUser().getLastName());
    }

    @FXML
    protected void onShopButtonEnteredNavBar() {
        shopButton.setStyle("-fx-border-radius: 5;-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void onShopButtonExitedNavBar() {
        shopButton.setStyle("-fx-border-color: #151920; -fx-background-color: transparent");
    }

    @FXML
    protected void usernameButtonEnteredNavigationBar() {
        userInformationButton.setStyle("-fx-border-radius: 5;-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void usernameButtonExitedNavigationBar() {
        userInformationButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }

    @FXML
    protected void bookingButtonEnteredNavBar() {
        bookingButton.setStyle("-fx-border-radius: 5;-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void bookingButtonExitedNavBar() {
        bookingButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }


    @FXML
    protected void cartButtonEnteredNavBar() {
        cartButton.setStyle("-fx-border-radius: 5;-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void cartButtonExitedNavBar() {
        cartButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }

    @FXML
    protected void logOutButtonEnteredNavBar() {
        logOutButton.setStyle("-fx-border-radius: 5;-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void logOutButtonExitedNavBar() {
        logOutButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }


    @FXML
    protected void searchButtonEnteredNavBar() {
        searchButton.setStyle("-fx-background-color: #e08e35; -fx-background-radius: 0 5 5 0");
    }

    @FXML
    protected void searchButtonExitedNavBar() {
        searchButton.setStyle("-fx-background-color: #ffbd73; -fx-background-radius: 0 5 5 0");
    }



}
