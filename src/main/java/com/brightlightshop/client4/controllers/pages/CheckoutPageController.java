package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.OrderConstant;
import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.OrderDetailCheckoutComponentController;
import com.brightlightshop.client4.models.CartModel;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Guest;
import com.brightlightshop.client4.types.OrderDetail;
import com.brightlightshop.client4.types.Regular;
import com.brightlightshop.client4.types.Vip;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutPageController implements Initializable {

    private int rentalDurationValue;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    @FXML
    private Label balanceLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox orderDetailContainer;

    @FXML
    private Button rentWithRewardPointButton;

    @FXML
    private ToggleGroup rentalDurationToggleGroup;

    @FXML
    private HBox navigationBar;

    @FXML
    private ImageView spinnerImageView;

    @FXML
    private Label rewardPointLabel;

    @FXML
    private Pane rewardPointLabelContainer;

    @FXML
    private RadioButton sevenDayRadioButton;

    @FXML
    private Label totalValueLabel;

    @FXML
    private RadioButton twoDayRadioButton;

    @FXML
    private Button rentWithCashButton;

    @FXML
    void onRentButtonClick(ActionEvent event) throws IOException {
        if (CartModel.getOrderDetails().isEmpty()) {
            notifySubscriber("There must at least one item.");
            return;
        }

        // Show the spinner
        spinnerImageView.setVisible(true);

        // Create request for order
        String response = createOrderRequest();

        // Notify subscriber
        notifySubscriber(response);
    }

    @FXML
    void onRentWithRewardPointButtonClick(ActionEvent event) throws IOException {
        if (CartModel.getOrderDetails().isEmpty()) {
            notifySubscriber("There must at least one item.");
            return;
        }

        // Show the spinner
        spinnerImageView.setVisible(true);

        // Call request
        String response = createOrderWithRewardPointRequest();

        // Notify subscriber
        notifySubscriber(response);
    }

    private void notifySubscriber(String message) throws IOException {
        // Set the message label
        messageLabel.setWrapText(true);
        messageLabel.setVisible(true);
        messageLabel.setText(message);

        // Update the current user
        UserModel.update();

        // Update label
        setupLabel(true);

        // Turn of the label
        spinnerImageView.setVisible(false);
    }

    private String createOrderRequest() {
        RequestBody body = getCreateOrderBody();
        Request request = new Request.Builder()
                .url(UrlConstant.createOrder())
                .post(body)
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) == '2') {
                CartModel.clearAllItems();
                update();
            }

            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createOrderWithRewardPointRequest() {
        Request request = new Request.Builder()
                .url(UrlConstant.createOrderWithRewardPoint())
                .post(getCreateOrderBody())
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) == '2') {
                CartModel.clearAllItems();
                update();
            }

            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestBody getCreateOrderBody() {
        JSONObject json = new JSONObject();
        json.put("rentalDuration", rentalDurationValue);

        // Get the order details
        JSONArray orderDetails = new JSONArray();
        for (OrderDetail orderDetail: CartModel.getOrderDetails()) {
            JSONObject orderDetailObj = new JSONObject();
            orderDetailObj.put("itemId", orderDetail.getItem().get_id());
            orderDetailObj.put("quantity", orderDetail.getQuantity());
            orderDetails.put(orderDetailObj);
        }

        json.put("orderDetails", orderDetails);
        return RequestBody.create(json.toString(), JSON);
    }


    private void setTotalValueLabel() {
        double totalValue = 0;
        for (OrderDetail orderDetail: CartModel.getOrderDetails()) {
            totalValue += orderDetail.getQuantity() * orderDetail.getItem().getRentalFee();
        }

        totalValueLabel.setText(String.valueOf(totalValue));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserModel.update();
            addNavigationBar();
            setupToggleGroup();
            setupLabel(false);
            setupButton();

            // Hide the spinner
            spinnerImageView.setVisible(false);

            update();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupToggleGroup() {
        twoDayRadioButton.setToggleGroup(rentalDurationToggleGroup);
        sevenDayRadioButton.setToggleGroup(rentalDurationToggleGroup);
        rentalDurationValue = OrderConstant.getDefaultRentalDuration();
        rentalDurationToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)rentalDurationToggleGroup.getSelectedToggle();

                if (rb != null) {
                    // Get the first value of the text
                     rentalDurationValue = Integer.parseInt(String.valueOf(rb.getText().toLowerCase().charAt(0)));
                     setTotalValueLabel();
                }
            }
        });
    }

    private void setupLabel(boolean showMessageLabel) {
        // invisible the messageLabel
        messageLabel.setVisible(showMessageLabel);

        // Not Vip - not have the reward point label
        if (!(UserModel.getCurrentUser() instanceof Vip)) {
            rewardPointLabelContainer.setVisible(false);
        }

        // Guest user
        if (UserModel.getCurrentUser() instanceof Guest) {
            balanceLabel.setText(String.valueOf(((Guest) UserModel.getCurrentUser()).getBalance()));
            return;
        }

        // Regular user
        if (UserModel.getCurrentUser() instanceof Regular) {
            balanceLabel.setText(String.valueOf(((Regular) UserModel.getCurrentUser()).getBalance()));
            return;
        }

        // Vip user - have the reward point label
        balanceLabel.setText(String.valueOf(((Vip) UserModel.getCurrentUser()).getBalance()));
        rewardPointLabel.setText(String.valueOf(((Vip) UserModel.getCurrentUser()).getRewardPoint()));
        System.out.println(((Vip) UserModel.getCurrentUser()));
        System.out.println(((Vip) UserModel.getCurrentUser()).getRewardPoint());
    }

    private void setupButton() {
        if (!(UserModel.getCurrentUser() instanceof  Vip)) {
            rentWithRewardPointButton.setDisable(true);
        }
    }

    public void update() throws IOException {
        // Clear all components
        orderDetailContainer.getChildren().clear();

        // Render the order detail list
        for (OrderDetail orderDetail: CartModel.getOrderDetails()) {
            String path = FXMLPath.getOrderDetailCheckoutComponentPath();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            HBox hbox = fxmlLoader.load();

            OrderDetailCheckoutComponentController orderDetailComponentController = fxmlLoader.getController();
            orderDetailComponentController.setData(orderDetail);
            orderDetailComponentController.setSubscriber(this);

            orderDetailContainer.getChildren().add(hbox);
        }

        // Change the total value label
        setTotalValueLabel();
    }

    //Add navigation bar
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

    @FXML
    protected void rentWithCashButtonOnEnteredCheckOutPage() {
        rentWithCashButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void rentWithCashButtonOnExitedCheckOutPage() {
        rentWithCashButton.setStyle("-fx-background-color:  #f1ab2c");
    }

    @FXML
    protected void rentWithRewardPointButtonEnteredCheckoutPage() {
        rentWithRewardPointButton.setStyle("-fx-background-color:  #e4c444");
    }

    @FXML
    protected void rentWithRewardPointButtonExitedCheckoutPage() {
        rentWithRewardPointButton.setStyle("-fx-background-color: #f3d74b");
    }
}
