package com.brightlightshop.client4.controllers.components;

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
    public void handleImgLogo(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("com/brightlightshop/client4/images/logo-social.png")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 880);
        stage.setScene(scene);
        stage.show();
    }



}
