package com.brightlightshop.client4;

import com.brightlightshop.client4.controllers.pages.UpdateItemPageController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrightLightShopApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getUpdateItemPagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        UpdateItemPageController controller = fxmlLoader.getController();
        controller.setData("630c21de1449fd5b5a9b3b0d");

        stage.setTitle("Bright Light Shop - Buy in brilliant way");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}