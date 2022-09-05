package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.utils.FXMLPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewUserInfoPageController implements Initializable {

    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private Customer customer = (Customer) UserModel.getCurrentUser();

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
    private Label addBalanceLabel;

    public void handleError(){

    }

    private String addBalanceRequest() throws Exception {
        RequestBody body = RequestBody.create(createAddBalanceJson(), JSON);
        Request request = new Request.Builder()
                .url(UrlConstant.addBalance())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();
        try(Response response = client.newCall(request).execute()){
            if (String.valueOf(response.code()).charAt(0) == '4'){
                handleError();
                return "";
            }

            return response.body().string();
        }
    }
    public void setLabel() {
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

    public void onAddBalanceButtonClick() throws Exception {
        addBalanceRequest();
        addBalanceTextField.clear();
        addBalanceLabel.setText("Add balance successful!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addNavigationBar();
            setLabel();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

