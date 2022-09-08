package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.OrderComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Order;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import com.cloudinary.Url;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class ViewOrdersPageController implements Initializable {
    @FXML
    private VBox orderContainer;

    @FXML
    private HBox navigationBar;

    @FXML
    private TextField pageTextField;

    @FXML
    private Label messageLabel;

    @FXML
    private void onSearchButtonClick() throws Exception {
        setOrdersFromJson(new JSONArray(getOrdersRequest()));
    }

    private final OkHttpClient client = new OkHttpClient();

    private ArrayList<Order> orders;

    private void setOrdersFromJson(JSONArray ordersJson) throws IOException {
        orders = JsonParser.getOrders(ordersJson);
        for (Order order: orders) {
            if (!order.getOrderDetails().equals("")){
                orderContainer.getChildren().remove(messageLabel);
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/OrderComponent.fxml"));

            VBox vBox = fxmlLoader.load();
            OrderComponentController orderComponentController = fxmlLoader.getController();
            orderComponentController.setData(order);
            orderContainer.getChildren().add(vBox);
        }
    }

    private String getOrdersRequest() throws Exception {
        Request request = new Request.Builder()
                .url(getUrl())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();
        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private String getUrl() {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(UrlConstant.getOrders())).newBuilder();

        if (pageTextField.getText() == null || pageTextField.getText().equals("")) {
            urlBuilder.addQueryParameter("page", "1");
        } else {
            urlBuilder.addQueryParameter("page", pageTextField.getText());
        }

        return urlBuilder.build().toString();
    }

    private void setupTextField() {
        Component.numericTextField(pageTextField);
        pageTextField.setText("1");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setOrdersFromJson(new JSONArray(getOrdersRequest()));
            addNavigationBar();
            setupTextField();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
