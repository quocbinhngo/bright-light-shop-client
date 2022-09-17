package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.CustomerBoxComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
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
    private Button sortButton;

    @FXML
    private RadioButton nameAscendingRadioButton;

    @FXML
    private RadioButton nameDescendingRadioButton;

    @FXML
    private TextField searchCustomerTextField;

    @FXML
    private Button searchCustomerButton;

    @FXML
    private TextField pageTextField;

    @FXML
    private ImageView spinner;

    @FXML
    private Label firstMessage;

    @FXML
    private Label secondMessage;

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

    private String userId = "62ec74b4f13a1bbf8d94f560";

    public void handleImgLogo(ActionEvent event) throws IOException {
        String path = "/com/brightlightshop/client4/images/logo-social.png";
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

        customerTypeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton) customerTypeToggleGroup.getSelectedToggle();

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

        sortByToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
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

    private void setupTextField() {
        Component.numericTextField(pageTextField);
        pageTextField.setText("1");
    }
    private void handleError() {
    }

    private String getCustomersRequest() throws Exception {
        Request request = new Request.Builder()
                .url(getUrl())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (String.valueOf(response.code()).charAt(0) == '4') {
                handleError();
                return "";
            }

            return response.body().string();
        }
    }

    private String getUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlConstant.getCustomers()).newBuilder();

        if (customerTypeValue != null) {
            urlBuilder.addQueryParameter("accountType", customerTypeValue);
        }

        if (sortByValue != null) {
            urlBuilder.addQueryParameter("sortBy", sortByValue);
        }

        if (descValue) {
            urlBuilder.addQueryParameter("desc", String.valueOf(true));
        }

        if (pageTextField.getText() == null || pageTextField.getText().equals("")) {
            urlBuilder.addQueryParameter("page", "1");
        } else {
            urlBuilder.addQueryParameter("page", pageTextField.getText());
        }

        return urlBuilder.build().toString();
    }
/*
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

 */

    public void updateCustomersToGrid() {
        gridPaneAllCustomer.getChildren().clear();

        int column = 0;
        int row = 1;

        if (customers.isEmpty()) {
            showNoResult();
            return;
        }

        try {
            for (Customer customer : customers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/CustomerBoxComponent.fxml"));

                AnchorPane temp = fxmlLoader.load();
                CustomerBoxComponentController customerBoxComponentController = fxmlLoader.getController();
                customerBoxComponentController.setData(customer);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                gridPaneAllCustomer.add(temp, column++, row);
                GridPane.setMargin(temp, new Insets(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCustomers() throws Exception {
        gridPaneAllCustomer.getChildren().clear();
        spinner.setVisible(true);

        Thread t = new Thread(()->{
            String customersResponse = null;
            try
            {
                customersResponse = getCustomersRequest();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Cus res: " + customersResponse);

            customers = JsonParser.getCustomers(new JSONArray(customersResponse));

            String finalCustomersResponse = customersResponse;
            Platform.runLater(()->{
                spinner.setVisible(false);
                if (finalCustomersResponse.equals("[]") ){
                    showNoResult();
                    return;
                }
                updateCustomersToGrid();
            });
        });
        t.start();
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
        updateCustomersToGrid();
    }

    private String getSearchUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlConstant.searchCustomer()).newBuilder();

        if (searchCustomerTextField.getText() == null) {
            urlBuilder.addQueryParameter("search", "");
        } else {
            urlBuilder.addQueryParameter("search", searchCustomerTextField.getText());
        }

        return urlBuilder.build().toString();
    }

    private String searchCustomerRequest() throws IOException {
        Request request = new Request.Builder()
                .url(getSearchUrl())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (String.valueOf(response.code()).charAt(0) != '2') {
                return "";
            }

            return response.body().string();
        }
    }

    @FXML
    private void onSearchCustomerButtonClick() throws IOException {
        String response = searchCustomerRequest();
        if (response.equals("")) {
            handleError();
        }

        // Get the item
        customers = JsonParser.getCustomers(new JSONArray(response));
        updateCustomersToGrid();
    }

    public void addNavigationBar() {
        try {
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource(FXMLPath.getNavigationBarComponentPath()));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at homepage
            navigationBar.getChildren().add(hbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addNavigationBar();
            setupToggleGroup();
            setupTextField();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onSortButtonClick(ActionEvent event) throws Exception {
        firstMessage.setText("");
        secondMessage.setText("");
        spinner.setVisible(true);
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

        //Set page to 1
        pageTextField.setText("1");
    }

    private void showNoResult() {
        firstMessage.setText("No Results");
        secondMessage.setText("Please try again");
        spinner.setVisible(false);
    }

    @FXML
    void sortButtonEnter() {
        sortButton.setStyle("-fx-background-color:  #e08e35; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

    @FXML
    void sortButtonExit() {
        sortButton.setStyle("-fx-background-color:  #ffbd73; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

    @FXML
    void searchCustomerEnter() {
        searchCustomerButton.setStyle("-fx-background-color:  #e08e35");

    }

    @FXML
    void searchCustomerExit() {
        searchCustomerButton.setStyle("-fx-background-color:  #ffbd73");

    }
    @FXML
    void clearButtonEnter() {
        clearButton.setStyle("-fx-background-color:  #e08e35; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

    @FXML
    void clearButtonExit() {
        clearButton.setStyle("-fx-background-color:  #ffbd73; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

}

