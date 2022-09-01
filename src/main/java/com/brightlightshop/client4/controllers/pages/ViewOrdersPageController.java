package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.OrderComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Order;
import com.brightlightshop.client4.utils.JsonParser;
import com.cloudinary.Url;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewOrdersPageController implements Initializable {
    @FXML
    private VBox orderContainer;

    @FXML
    private HBox navigationBar;

    private final OkHttpClient client = new OkHttpClient();

    private ArrayList<Order> orders;

    private void setOrdersFromJson(JSONArray ordersJson) {
        orders = JsonParser.getOrders(ordersJson);
    }

    private String getOrdersRequest() throws Exception {
        Request request = new Request.Builder()
                .url(UrlConstant.getOrders())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();
        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setOrdersFromJson(new JSONArray(getOrdersRequest()));
            addNavigationBar();
            for (Order order: orders) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/OrderComponent.fxml"));

                VBox vBox = fxmlLoader.load();
                OrderComponentController orderComponentController = fxmlLoader.getController();
                orderComponentController.setData(order);
                orderContainer.getChildren().add(vBox);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Add navigation bar
    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/NavigationBarCustomerComponent.fxml"));
            AnchorPane hbox = navigationBarFXMLLoader.load();
            //put navigation bar into navigationbar container
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
