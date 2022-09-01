package com.brightlightshop.client4;

import com.brightlightshop.client4.controllers.pages.UpdateItemPageController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Admin;

import com.brightlightshop.client4.utils.FXMLPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrightLightShopApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Admin testAdmin = new Admin("631025834b3765dcc683d447", "Binh", "Ngo", "binhadmin", "abcdefxg", "0123456789", null, "admin");
        UserModel.setCurrentUser(testAdmin);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewCustomersPagePath()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bright Light Shop - Buy in brilliant way");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}