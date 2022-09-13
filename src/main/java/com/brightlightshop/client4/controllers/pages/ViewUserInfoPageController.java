package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.utils.Component;
import javafx.event.ActionEvent;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewUserInfoPageController implements Initializable {
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private Customer customer;
    private Admin admin;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label cusInfoAccountType;

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
    private TextField addBalanceTextField;

    @FXML
    private Button addBalanceButton;

    @FXML
    private Button backToHomepageButton;

    @FXML
    private Label addBalanceLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox purchaseHistory;

    @FXML
    private Label rewardPointLabel;

    @FXML
    private Label rewardPointLabel1;

    private ArrayList<Order> orders;

    public void handleError(){
    }

    private String addBalanceRequest() throws Exception {
        RequestBody body = RequestBody.create(createAddBalanceJson(), JSON);
        Request request = new Request.Builder()
                .url(UrlConstant.addBalance())
                .patch(body)
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();
        try(Response response = client.newCall(request).execute()){
            if (String.valueOf(response.code()).charAt(0) == '4'){
                System.out.println(response.body().string());
                handleError();
                return "";
            }

            return response.body().string();
        }
    }
    public void setLabel() throws IOException {
        UserModel.update();
        rewardPointLabel.setVisible(false);
        rewardPointLabel1.setVisible(false);

        if (UserModel.getCurrentUser() instanceof Customer) {
            customer = (Customer) UserModel.getCurrentUser();
            titleLabel.setText("Customer Information");
            customer = (Customer) UserModel.getCurrentUser();
            cusInfoFirstName.setText(customer.getFirstName());
            cusInfoLastName.setText(customer.getLastName());
            cusInfoPhone.setText(customer.getPhone());
            cusInfoAddress.setText(customer.getAddress());
            cusInfoUsername.setText(customer.getUsername());
            cusInfoBalance.setText(String.valueOf(customer.getBalance()));
            addBalanceTextField.setDisable(false);
            addBalanceButton.setDisable(false);
            if (customer instanceof Guest) {
                cusInfoAccountType.setText("Guest");
            }
            if (customer instanceof Regular) {
                cusInfoAccountType.setText("Regular");
            }
            if (customer instanceof Vip) {
                cusInfoAccountType.setText("VIP");
                rewardPointLabel.setVisible(true);
                rewardPointLabel1.setVisible(true);
                rewardPointLabel.setText(String.valueOf(((Vip) UserModel.getCurrentUser()).getRewardPoint()));

            }
            addBalanceTextField.setVisible(true);
            addBalanceButton.setVisible(true);
            return;
        }

        admin = (Admin) UserModel.getCurrentUser();
        titleLabel.setText("Admin Information");
        cusInfoFirstName.setText(admin.getFirstName());
        cusInfoLastName.setText(admin.getLastName());
        cusInfoPhone.setText(admin.getPhone());
        cusInfoAddress.setText(admin.getAddress());
        cusInfoUsername.setText(admin.getUsername());
        cusInfoBalance.setText("");
        cusInfoAccountType.setText("Admin");
        addBalanceTextField.setVisible(false);
        addBalanceButton.setVisible(false);
    }

    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource(FXMLPath.getNavigationBarComponentPath()));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at homepage
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createAddBalanceJson(){
        JSONObject addBalance = new JSONObject();
        addBalance.put("amount", Integer.parseInt(addBalanceTextField.getText()));
        return addBalance.toString();
    }

    @FXML
    public void onAddBalanceButtonClick() throws Exception {
        String response = addBalanceRequest();
        System.out.println(response);
        addBalanceTextField.clear();
        addBalanceLabel.setVisible(true);
        addBalanceLabel.setText("Add balance successful!");

        setLabel();
    }

    @FXML
    public void onBackToHomepageButtonClick(ActionEvent event) throws IOException {
        String path ="/com/brightlightshop/client4/HomePage.fxml";
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 880);
        stage.setScene(scene);
        stage.show();
    }


    public void setUser(){
        if (UserModel.getCurrentUser() instanceof Admin){
            admin = (Admin) UserModel.getCurrentUser();
        } else {
            customer = (Customer) UserModel.getCurrentUser();
        }
    }
    /*
        private String getOrdersRequest(String userId) throws Exception {
            Request request = new Request.Builder()
                    .url(UrlConstant.getOrders())
                    .get()
                    .addHeader("user-id", UserModel.getCurrentUser().get_id())
                    .build();
            try(Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

        private void setOrdersFromJson(JSONArray ordersJson) {
            orders = JsonParser.getOrders(ordersJson);
        }

        public void setPurchaseHistory() throws Exception {
            setOrdersFromJson(new JSONArray(getOrdersRequest(UserModel.getCurrentUser().get_id())));
            for (Order order: orders) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/OrderComponentNoDetail.fxml"));

                VBox vBox = fxmlLoader.load();
                OrderComponentNoDetailController orderComponentNoDetailController = fxmlLoader.getController();
                orderComponentNoDetailController.setData(order);
                purchaseHistory.getChildren().add(vBox);

            }
        }
        */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserModel.update();
            addNavigationBar();
            setUser();
            setLabel();
            //setPurchaseHistory();

            // Hide the addBalance label
            addBalanceLabel.setVisible(false);

            Component.numericTextField(addBalanceTextField);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void addBalanceEnter(MouseEvent event) {
        addBalanceButton.setStyle("-fx-background-color:  #e08e35");
    }

    @FXML
    void addBalanceExit(MouseEvent event) {
        addBalanceButton.setStyle("-fx-background-color: #ffbd73");
    }

    @FXML
    void backToHomePageEnter(MouseEvent event) {
        backToHomepageButton.setStyle("-fx-background-color:  #e08e35");
    }

    @FXML
    void backToHomePageExit(MouseEvent event) {
        backToHomepageButton.setStyle("-fx-background-color: #ffbd73");
    }
}
