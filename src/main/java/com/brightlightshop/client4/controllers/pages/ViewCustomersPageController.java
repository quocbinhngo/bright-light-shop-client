package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.CustomerBoxComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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

public class ViewCustomersPageController implements Initializable {

    private final String getCustomerByIdGetUrl = "http://localhost:8000/api/users/customers";
    private Customer customer;
    @FXML
    private RadioButton VIPCustomerTypeRadioButton;

    @FXML
    private ScrollPane allCustomerContainer;

    @FXML
    private BorderPane categoryComboBox;

    @FXML
    private Button clearButton;

    @FXML
    private Label genreType;

    @FXML
    private Label genreType1;

    @FXML
    private GridPane gridPaneAllCustomer;

    @FXML
    private RadioButton guestCustomerTypeRadioButton;

    @FXML
    private RadioButton idAscendingSortByRadioButton;

    @FXML
    private RadioButton idDescendingSortByRadioButton;

    @FXML
    private HBox navigationBar;

    @FXML
    private RadioButton regularCustomerTypeRadioButton;

    @FXML
    private Button searchButton;

    @FXML
    private RadioButton nameAscendingRadioButton;

    @FXML
    private RadioButton nameDescendingRadioButton;

    private ToggleGroup customerTypeToggleGroup = new ToggleGroup();
    private ToggleGroup sortByToggleGroup = new ToggleGroup();
    private String customerTypeValue = null;
    private String sortByValue = null;
    private boolean descValue = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<Customer> customers;

    private final OkHttpClient client = new OkHttpClient();

    private String userId = "62f0b052ee88e366757bc752";

    public void handleImgLogo(ActionEvent event) throws IOException {
        String path ="/com/brightlightshop/client4/images/logo-social.png";
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setupCustomerTypeToggleGroup() {
        guestCustomerTypeRadioButton.setToggleGroup(customerTypeToggleGroup);
        regularCustomerTypeRadioButton.setToggleGroup(customerTypeToggleGroup);
        VIPCustomerTypeRadioButton.setToggleGroup(customerTypeToggleGroup);

        customerTypeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)customerTypeToggleGroup.getSelectedToggle();

                if (rb != null) {
                    customerTypeValue = rb.getText().toLowerCase();
                }
            }
        });
    }

    private void setupSortByToggleGroup() {
        idAscendingSortByRadioButton.setToggleGroup(sortByToggleGroup);
        idDescendingSortByRadioButton.setToggleGroup(sortByToggleGroup);
        nameAscendingRadioButton.setToggleGroup(sortByToggleGroup);
        nameDescendingRadioButton.setToggleGroup(sortByToggleGroup);

        sortByToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton) sortByToggleGroup.getSelectedToggle();

                if (rb != null) {
                    if (rb.equals(idAscendingSortByRadioButton)) {
                        sortByValue = "_id";
                        descValue = false;
                    } else if (rb.equals(idDescendingSortByRadioButton)) {
                        sortByValue = "_id";
                        descValue = true;
                    } else if (rb.equals(nameAscendingRadioButton)) {
                        sortByValue = "firstName";
                        descValue = false;
                    } else {
                        sortByValue = "firstName";
                        descValue = true;
                    }
                }
            }
        });
    }

    private void setupToggleGroup() {
        setupCustomerTypeToggleGroup();
        setupSortByToggleGroup();
    }

    private void handleError() {
    }

    private String getCustomersRequest() throws Exception{
        Request request = new Request.Builder()
                .url(getUrl())
                .get()
                .addHeader("user-id", userId)
                .build();

        try(Response response = client.newCall(request).execute()) {

            if (String.valueOf(response.code()).charAt(0) == '4') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    private String getUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlConstant.getItems()).newBuilder();

        if (customerTypeValue != null) {
            urlBuilder.addQueryParameter("customerType", customerTypeValue);
        }

        if (sortByValue != null) {
            urlBuilder.addQueryParameter("sortBy", sortByValue);
        }

        if (descValue) {
            urlBuilder.addQueryParameter("desc", String.valueOf(true));
        }

        return urlBuilder.build().toString();
    }

    private String getCustomerByIdRequest() throws Exception {
        Request request = new Request.Builder()
                .url(getCustomerByIdGetUrl + String.format("/%s", userId))
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) == '4') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    public void updateCustomersToGrid() throws Exception {
        gridPaneAllCustomer.getChildren().clear();

        int column = 0;
        int row =1;

        try {
            for (Customer customer : customers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/CustomerBoxComponent.fxml"));

                AnchorPane temp = fxmlLoader.load();
                CustomerBoxComponentController customerBoxComponentController = fxmlLoader.getController();
                customerBoxComponentController.setData(customer);

                if (column == 1){
                    column =0;
                    row++;
                }


                gridPaneAllCustomer.add(temp, column++ ,row);
                GridPane.setMargin(temp, new Insets(1));
            }
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void getCustomers() throws Exception {
        String customersResponse = getCustomersRequest();
        customers = JsonParser.getCustomers(new JSONArray(customersResponse));
        updateCustomersToGrid();
    }

    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/NavigationBarComponent.fxml"));
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
            setupToggleGroup();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onSearchButtonClick(ActionEvent event) throws Exception {
        getCustomers();
    }

    @FXML
    void onClearButtonClick(ActionEvent event) throws Exception {

        // Clear toggle group
        customerTypeToggleGroup.selectToggle(null);
        sortByToggleGroup.selectToggle(null);

        // reset value
        customerTypeValue = null;
        sortByValue = null;
        descValue = false;
    }


}

