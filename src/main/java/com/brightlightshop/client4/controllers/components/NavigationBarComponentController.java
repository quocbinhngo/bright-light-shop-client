package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.CheckoutPageController;
import com.brightlightshop.client4.controllers.pages.ViewItemPageCustomerController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigationBarComponentController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    void onShopButtonClick(ActionEvent event) throws IOException {
        String path = "/com/brightlightshop/client4/ViewItemsPage.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
    protected void onShopButtonEnteredNavBar() {
        shopButton.setStyle("-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void onShopButtonExitedNavBar() {
        shopButton.setStyle("-fx-border-color: #151920; -fx-background-color: transparent");
    }

    @FXML
    protected void usernameButtonEnteredNavigationBar() {
        userInformationButton.setStyle("-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void usernameButtonExitedNavigationBar() {
        userInformationButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }

    @FXML
    protected void bookingButtonEnteredNavBar() {
        bookingButton.setStyle("-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void bookingButtonExitedNavBar() {
        bookingButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }


    @FXML
    protected void cartButtonEnteredNavBar() {
        cartButton.setStyle("-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void cartButtonExitedNavBar() {
        cartButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }

    @FXML
    protected void logOutButtonEnteredNavBar() {
        logOutButton.setStyle("-fx-border-color: #FFFFFF;-fx-background-color: transparent");
    }

    @FXML
    protected void logOutButtonExitedNavBar() {
        logOutButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent");
    }


    @FXML
    protected void searchButtonEnteredNavBar() {
        searchButton.setStyle("-fx-background-color: #f3d74b; -fx-background-radius: 0 5 5 0");
    }

    @FXML
    protected void searchButtonExitedNavBar() {
        searchButton.setStyle("-fx-background-color: #f1ab2c; -fx-background-radius: 0 5 5 0");
    }


}
