package com.brightlightshop.client4.controllers.components;

import com.brightlightshop.client4.controllers.pages.UpdateItemPageController;
import com.brightlightshop.client4.controllers.pages.ViewItemPageCustomerController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ItemBoxComponentController {

    private CartModel cartModel;
    private UserModel userModel;

    private Item item;

    @FXML
    private ImageView imageView;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label rentalTypeLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button itemBoxClick;

    @FXML
    private Label itemIdentifierLabel;

    @FXML
    void onItemBoxClick(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getViewItemPagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        if (UserModel.getCurrentUser().getAccountType().equals("admin")) {
            UpdateItemPageController controller = fxmlLoader.getController();
            controller.setData(item.get_id());
        } else {
            ViewItemPageCustomerController viewItemPageCustomerController = fxmlLoader.getController();
            viewItemPageCustomerController.setData(item.get_id());
        }


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setData(Item item){
        this.item = item;

        // Set data for JFX
        Image image = new Image(item.getImageUrl());


        //Bo goc
        imageView.setImage(image);
        Rectangle clip = new Rectangle();
        clip.setWidth(175.0f);
        clip.setHeight(175.0f);

        clip.setArcHeight(10);
        clip.setArcWidth(10);
        clip.setStroke(Color.BLACK);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        WritableImage temp = imageView.snapshot(parameters,null);
        imageView.setClip(null);
        imageView.setImage(temp);
        //


        titleLabel.setText(item.getTitle());
        itemIdentifierLabel.setText(item.getItemIdentifier());
        rentalTypeLabel.setText(StringUtils.capitalize(item.getRentalType()));
        rentalFeeLabel.setText(String.valueOf(item.getRentalFee()));
        statusLabel.setText(item.getAvailableNumber() > 0 ? "Available" : "Out of stock");

        if (statusLabel.getText().equals("Available")){
            statusLabel.setTextFill(Color.GREEN);
        }
        else{
            statusLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    protected void itemBoxClickEntered() {
        itemBoxClick.setStyle("-fx-background-color: #c3c3c3");
    }

    @FXML
    protected void itemBoxClickExited() {
        itemBoxClick.setStyle("-fx-background-color: #dbdbdb");
    }

}
