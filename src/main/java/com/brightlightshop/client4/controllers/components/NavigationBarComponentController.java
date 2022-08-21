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
    private Button goToCartButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button logoButton;

    @FXML
    private TextField searchBarTextField;

    @FXML
    private Button shopButton;

    @FXML
    private Button userInformationButton;

    @FXML
    private Label usernameNavigationBar;

    @FXML
    void handleImgLogo(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("com/brightlightshop/client4/images/logo-social.png")));
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

    // Change to view order page
    @FXML
    void onOrderButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewOrdersPagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
