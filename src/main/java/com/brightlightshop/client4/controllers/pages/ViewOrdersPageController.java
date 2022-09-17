package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.OrderComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Order;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import com.cloudinary.Url;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Label messageLabel;

    @FXML
    private HBox navigationBar;

    @FXML
    private VBox orderContainer;

    @FXML
    private TextField pageTextField;

    @FXML
    private ImageView spinnerImageView;

    @FXML
    private Button searchButton;


    @FXML
    private void onSearchButtonClick() throws Exception {
        setOrdersFromJson(new JSONArray(getOrdersRequest()));

        spinnerImageView.setVisible(true);
        messageLabel.setVisible(false);

        Thread setOrdersThread = new Thread(()-> {
            try {
                String response = getOrdersRequest();
                Platform.runLater(()->{
                    try {
                        setOrdersFromJson(new JSONArray(response));
                        spinnerImageView.setVisible(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        setOrdersThread.start();
    }

    private final OkHttpClient client = new OkHttpClient();

    private ArrayList<Order> orders;

    private void setOrdersFromJson(JSONArray ordersJson) throws IOException {
        orders = JsonParser.getOrders(ordersJson);

        if (orders.size() == 0) {
            spinnerImageView.setVisible(false);
            messageLabel.setVisible(true);
            messageLabel.setText("Can't find the orders match your requirement");
            return;
        }

        orderContainer.getChildren().clear();
        for (Order order: orders) {
            if (order.getOrderDetails().isEmpty()){
                messageLabel.setVisible(true);
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(FXMLPath.getOrderComponentPath()));

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
            if (String.valueOf(response.code()).charAt(0) != '2') {
                System.out.println(response.body().string());
                return "";
            }

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
            addNavigationBar();
            setupTextField();
            spinnerImageView.setVisible(true);
            messageLabel.setVisible(false);

            Thread setOrdersThread = new Thread(()-> {
                try {
                    String response = getOrdersRequest();
                    Platform.runLater(()->{
                        try {
                            if (response.equals("")) {
                                handleError();
                                return;
                            }
                            setOrdersFromJson(new JSONArray(response));
                            spinnerImageView.setVisible(false);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            setOrdersThread.start();

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

    private void handleError() {
        spinnerImageView.setVisible(false);
        messageLabel.setVisible(true);
        messageLabel.setText("Cannot load orders");

    }


    @FXML
    void searchButtonEnter(MouseEvent event) {
        searchButton.setStyle("-fx-background-color:  #e08e35");
    }

    @FXML
    void searchButtonExit(MouseEvent event) {
        searchButton.setStyle("-fx-background-color: #ffbd73");
    }

}
