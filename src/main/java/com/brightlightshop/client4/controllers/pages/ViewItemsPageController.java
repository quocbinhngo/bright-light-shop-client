package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.ItemBoxComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
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

public class ViewItemsPageController implements Initializable {
    @FXML
    private ScrollPane allItemContainer;

    @FXML
    private RadioButton availableStatusRadioButton;

    @FXML
    private BorderPane categoryComboBox;

    @FXML
    private Button clearButton;

    @FXML
    private RadioButton dvdRentalTypeRadioButton;

    @FXML
    private RadioButton gameRentalTypeRadioButton;

    @FXML
    private Label genreType;

    @FXML
    private GridPane girdPaneAllIteam;

    @FXML
    private RadioButton idAscendingSortByRadioButton;

    @FXML
    private RadioButton idDescendingSortByRadioButton;

    @FXML
    private HBox navigationBar;

    @FXML
    private RadioButton nonAvaiableStatusRadioButton;

    @FXML
    private TextField pageTextField;

    @FXML
    private RadioButton recordRentalTypeRadioButton;

    @FXML
    private ToggleGroup rentalType;

    @FXML
    private Button searchButton;

    @FXML
    private ToggleGroup sortBy;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton titleAscendingRadioButton;

    @FXML
    private RadioButton titleDescendingRadioButton;

    @FXML
    void onSearchButtonClick(ActionEvent event) throws Exception {
        getItems();
    }

    @FXML
    void onClearButtonClick(ActionEvent event) throws Exception {

        // Clear toggle group
        rentalTypeToggleGroup.selectToggle(null);
        sortByToggleGroup.selectToggle(null);
        statusToggleGroup.selectToggle(null);

        // reset value
        rentalTypeValue = null;
        statusValue = null;
        sortByValue = null;
        descValue = false;

        // Reset the page text field value to 0
        pageTextField.setText("1");
    }

    private ToggleGroup rentalTypeToggleGroup = new ToggleGroup();
    private ToggleGroup statusToggleGroup = new ToggleGroup();
    private ToggleGroup sortByToggleGroup = new ToggleGroup();
    private String rentalTypeValue = null;
    private String statusValue = null;
    private String sortByValue = null;
    private boolean descValue = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList <Item> items;

    private final String userId = "62f0b052ee88e366757bc752";

    private final OkHttpClient client = new OkHttpClient();

    public void handleImgLogo(ActionEvent event) throws IOException {
        String path ="/com/brightlightshop/client4/images/logo-social.png";
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setupRentalTypeToggleGroup() {
        dvdRentalTypeRadioButton.setToggleGroup(rentalTypeToggleGroup);
        gameRentalTypeRadioButton.setToggleGroup(rentalTypeToggleGroup);
        recordRentalTypeRadioButton.setToggleGroup(rentalTypeToggleGroup);

        rentalTypeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)rentalTypeToggleGroup.getSelectedToggle();

                if (rb != null) {
                    rentalTypeValue = rb.getText().toLowerCase();
                }
            }
        });
    }

    private void setupStatusToggleGroup() {
        availableStatusRadioButton.setToggleGroup(statusToggleGroup);
        nonAvaiableStatusRadioButton.setToggleGroup(statusToggleGroup);

        statusToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton)statusToggleGroup.getSelectedToggle();

                if (rb != null) {
                    if (rb.equals(nonAvaiableStatusRadioButton)) {
                        statusValue = "non-available";
                    } else {
                        statusValue = "available";
                    }
                }
            }
        });
    }

    private void setupSortByToggleGroup() {
        idAscendingSortByRadioButton.setToggleGroup(sortByToggleGroup);
        idDescendingSortByRadioButton.setToggleGroup(sortByToggleGroup);
        titleAscendingRadioButton.setToggleGroup(sortByToggleGroup);
        titleDescendingRadioButton.setToggleGroup(sortByToggleGroup);

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
                    } else if (rb.equals(titleAscendingRadioButton)) {
                        sortByValue = "title";
                        descValue = false;
                    } else {
                        sortByValue = "title";
                        descValue = true;
                    }
                }
            }
        });
    }

    private void setupToggleGroup() {
        setupRentalTypeToggleGroup();
        setupStatusToggleGroup();
        setupSortByToggleGroup();
    }

    private void handleError() {
    }


    private String getItemsRequest() throws Exception{
        Request request = new Request.Builder()
                .url(getUrl())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try(Response response = client.newCall(request).execute()) {

            if (String.valueOf(response.code()).charAt(0) != '2') {
                handleError();
                System.out.println(response.code());
                return "";
            }

            return response.body().string();
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

    private void setupTextField() {
        Component.numericTextField(pageTextField);
        pageTextField.setText("1");
    }

    public void getItems() throws Exception {
        String itemsResponse = getItemsRequest();
        System.out.println("Item res: " + itemsResponse);
        items = JsonParser.getItems(new JSONArray(itemsResponse));
        updateItemsToGrid();
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        updateItemsToGrid();
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

    private String getUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlConstant.getItems()).newBuilder();

        if (rentalTypeValue != null) {
            urlBuilder.addQueryParameter("rentalType", rentalTypeValue);
        }

        if (sortByValue != null) {
            urlBuilder.addQueryParameter("sortBy", sortByValue);
        }

        if (statusValue != null) {
            urlBuilder.addQueryParameter("status", statusValue);
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


    public void updateItemsToGrid() {
        girdPaneAllIteam.getChildren().clear();

        int column = 0;
        int row =1;

        try {
            for (Item item : items) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/ItemBoxComponent.fxml"));

                AnchorPane temp = fxmlLoader.load();
                ItemBoxComponentController itemBoxComponentController = fxmlLoader.getController();
                itemBoxComponentController.setData(item);

                if (column == 1){
                    column =0;
                    row++;
                }

                girdPaneAllIteam.add(temp, column++ ,row);
                GridPane.setMargin(temp, new Insets(1));
            }
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void searchButtonEnteredViewItemsPage() {
        searchButton.setStyle("-fx-background-color: #e4c444; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

    @FXML
    void searchButtonExitedViewItemsPage() {
        searchButton.setStyle("-fx-background-color: #f3d74b; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

    @FXML
    void clearButtonEnteredViewItemsPage() {
        clearButton.setStyle("-fx-background-color: #e4c444; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }

    @FXML
    void clearButtonExitedViewItemsPage() {
        clearButton.setStyle("-fx-background-color: #f3d74b; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: BLACK");
    }
}
