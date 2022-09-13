package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.OrderComponentController;
import com.brightlightshop.client4.controllers.components.OrderComponentNoDetailController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewCustomerPageController implements Initializable {

    private String userId;
    private final OkHttpClient client = new OkHttpClient();
    private Customer customer;

    @FXML
    private Label cusInfoAccountType;

    @FXML
    private Label cusInfoIDLabel;

    @FXML
    private Label cusInfoAddress;

    @FXML
    private Label cusInfoBalance;

    @FXML
    private Label cusInfoFirstName;

    @FXML
    private Label cusInfoLastName;

    @FXML
    private Label cusInfoPhone;

    @FXML
    private Label cusInfoUsername;

    @FXML
    private HBox navigationBar;

    @FXML
    private VBox purchaseHistory;

    @FXML
    private Button backCustomerListButton;

    @FXML
    private TextField pageTextField;

    @FXML
    private ImageView spinner;

    @FXML
    private Label messageLabel;

    @FXML
    private Button goButton;

    private ArrayList<Order> orders;

    public void handleError(){

    }

    private String getUserByIdRequest() throws Exception {
        Request request = new Request.Builder()
                .url(UrlConstant.getCustomers() + String.format("/%s", userId))
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())// switch to current user id
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) == '4') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    private String getOrdersRequest(String userId) throws Exception {
        Request request = new Request.Builder()
                .url(getUrl())
                .get()
                .addHeader("user-id", userId)
                .build();
        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private String getUrl(){
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(UrlConstant.getOrders())).newBuilder();

        if (pageTextField.getText() == null || pageTextField.getText().equals("")) {
            urlBuilder.addQueryParameter("page", "1");
        } else {
            urlBuilder.addQueryParameter("page", pageTextField.getText());
        }

        return urlBuilder.build().toString();
    }

    private void setOrdersFromJson(JSONArray ordersJson) throws IOException {
        purchaseHistory.getChildren().clear();
        orders = JsonParser.getOrders(ordersJson);
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
            purchaseHistory.getChildren().add(vBox);
        }
    }

    public void setData(String _id) {
        try {
            userId = _id;
            String customerResponse = getUserByIdRequest();

            if (customerResponse.equals("")) {
                return;
            }

            JSONObject customerJsonObject = new JSONObject(customerResponse);
            customer = (Customer)JsonParser.getUser(customerJsonObject);

            setLabel();
            setPurchaseHistory();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void setLabel() {
        cusInfoIDLabel.setText(customer.getCustomerIdentifier());
        cusInfoFirstName.setText(customer.getFirstName());
        cusInfoLastName.setText(customer.getLastName());
        cusInfoPhone.setText(customer.getPhone());
        cusInfoAddress.setText(customer.getAddress());
        cusInfoUsername.setText(customer.getUsername());
        cusInfoBalance.setText(String.valueOf(customer.getBalance()));
        if (customer instanceof Guest) {
            cusInfoAccountType.setText("Guest");
        }

        if (customer instanceof Regular) {
            cusInfoAccountType.setText("Regular");
        }

        if (customer instanceof Vip) {
            cusInfoAccountType.setText("VIP");
        }
    }

    public void setPurchaseHistory() throws Exception {
        Thread setOrdersThread = new Thread(()-> {
            try {
                String response = getOrdersRequest(userId);
                Platform.runLater(()->{
                    try {
                        setOrdersFromJson(new JSONArray(response));
                        spinner.setVisible(false);
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

    @FXML
    private void onGoButtonClick(ActionEvent event) throws Exception {
        spinner.setVisible(true);
        messageLabel.setVisible(false);
        setPurchaseHistory();
    }

    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/NavigationBarAdminComponent.fxml"));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at homepage
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addNavigationBar();
            Component.numericTextField(pageTextField);
            pageTextField.setText("1");
            spinner.setVisible(true);
            messageLabel.setText("No orders");
            messageLabel.setVisible(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void backCustomerListButtonEnteredViewCustomerPage() {
        backCustomerListButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void backCustomerListButtonExitedViewCustomerPage() {
        backCustomerListButton.setStyle("-fx-background-color: #ffbd73");
    }



    @FXML
    void onBackButtonClick(ActionEvent event) throws Exception {
        String path = "/com/brightlightshop/client4/ViewCustomersPage.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());

        ViewCustomersPageController viewCustomersPageController = fxmlLoader.getController();
        viewCustomersPageController.getCustomers();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

