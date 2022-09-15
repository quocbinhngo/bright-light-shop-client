package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.OrderDetailComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class ViewOrderPageController {
    private Order order;
    private OkHttpClient client = new OkHttpClient();

    @FXML
    private Label accountTypeLabel;

    @FXML
    private VBox infoContainer;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox orderDetailContainer;

    @FXML
    private Button returnButton;

    @FXML
    private Label totalValueLabel;

    @FXML
    private HBox navigationBar;


    @FXML
    void onReturnButtonClick(ActionEvent event) throws Exception {
        String response = returnOrderRequest();

        // Setup message
        messageLabel.setVisible(true);
        messageLabel.setText(response);

        // Update user and announce them their current account type
        UserModel.update();
        accountTypeLabel.setText(UserModel.getCurrentUser().getAccountType());

        // Update the is returned UI
        returnButton.setDisable(false);
    }

    public void setData(Order order) throws IOException {
        this.order = order;

        if (order.isReturned()) {
            setIsReturnedUI();
            return;
        }

        setUI();
    }

    private void setIsReturnedUI() throws IOException {
        setOrderDetailUI();
        addNavigationBar();
        infoContainer.setVisible(false);
        returnButton.setDisable(true);
        totalValueLabel.setText(String.valueOf(getTotalValue()));
    }

    public void setUI() throws IOException {
        addNavigationBar();
        totalValueLabel.setText(String.valueOf(getTotalValue()));
        setOrderDetailUI();
        switch (UserModel.getCurrentUser().getAccountType()) {
            case "guest" -> {
                accountTypeLabel.setText("Guest");
                balanceLabel.setText(String.valueOf(((Guest) UserModel.getCurrentUser()).getBalance()));
            }

            case "regular" -> {
                accountTypeLabel.setText("Regular");
                balanceLabel.setText(String.valueOf(((Regular) UserModel.getCurrentUser()).getBalance()));
            }

            case "vip" -> {
                accountTypeLabel.setText("Vip");
                balanceLabel.setText(String.valueOf(((Vip) UserModel.getCurrentUser()).getBalance()));
            }
        }
    }

    private void setOrderDetailUI() throws IOException {
        for (OrderDetail orderDetail: order.getOrderDetails()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath.getOrderDetailComponentPath()));
            HBox hBox = loader.load();

            OrderDetailComponentController controller = loader.getController();
            controller.setData(orderDetail);

            orderDetailContainer.getChildren().add(hBox);
        }
    }


    public double getTotalValue() {
        // Check whether this is late return, if yes, add $10 for fine fee
        if (order.isLateReturned()) {
            messageLabel.setText("You return late so your fine fee is $10");
            return order.getTotalValue() + 10;
        }

        // Hide the message label
        messageLabel.setVisible(false);
        return order.getTotalValue();
    }

    private String returnOrderRequest() throws Exception {
        Request request = new Request.Builder()
                .url(UrlConstant.returnOrder(order.get_id()))
                .patch(getReturnOrderBody())
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private RequestBody getReturnOrderBody() {
        JSONObject jsonObject = new JSONObject();
        return RequestBody.create(jsonObject.toString(), JsonParser.JSON);
    }

    //Add navigation bar
    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource(FXMLPath.getNavigationBarComponentPath()));
            AnchorPane hbox = navigationBarFXMLLoader.load();
            //put navigation bar into navigationbar container
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void returnButtonEnteredViewOrderPage() {
        returnButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void returnButtonExitedViewOrderPage() {
        returnButton.setStyle("-fx-background-color: #f1ab2c");
    }
}


