package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.controllers.components.ItemComponentController;
import com.brightlightshop.client4.types.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewItemsPageController implements Initializable {
    public ScrollPane allItemContainer;
    public GridPane girdPaneAllIteam;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private BorderPane categoryComboBox;

    @FXML
    private CheckBox conditionNewCheckBox;

    @FXML
    private CheckBox conditionRenewedCheckBox;

    @FXML
    private CheckBox conditionUsedCheckBox;

    @FXML
    private CheckBox genreDvdCheckBox;

    @FXML
    private CheckBox genreGameCheckBox;

    @FXML
    private CheckBox genreRecordCheckBox;

    @FXML
    private Label genreType;


    @FXML
    private HBox navigationBar;

    @FXML
    private CheckBox price100kTo200kCheckBox;

    @FXML
    private CheckBox price25kTo50kCheckBox;

    @FXML
    private CheckBox priceGreaterThan200kCheckBox;

    @FXML
    private TextField priceMaxTextField;

    @FXML
    private TextField priceMinTextField;

    @FXML
    private CheckBox priceUnder25kCheckBox;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> sortTimeComboBox;

    @FXML
    private CheckBox statusAvaillable;

    @FXML
    private CheckBox statusOutOfStock;

    ArrayList <Item> items;

    ObservableList<String> comboboxChoices = FXCollections.observableArrayList("Sort by: Featured", "Sort by: Price: Low to High",
            "Sort by: Price: High to Low");


    public void handleImgLogo(ActionEvent event) throws IOException {
        String path ="/com/example/brightlightshop/home.fxml";
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private ArrayList<Item> getProducts(){
        ArrayList<Item> items = new ArrayList<>();

        for (int i =0;i < 30; i++){
//            Item product = new Item();
//            product.setName("Jurassic Park");
//            product.setPrice("50,000 VNĐ");
//            product.setImg("/com/example/brightlightshop/image/thebatman.png");
//            products.add(product);
        }

        return items;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNavigationBar();
        addProductToGird();
        addChoicesToComboBoxAllItemPage();
    }

    //Add navigation bar
    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource("/com/example/brightlightshop/navigationBar.fxml"));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at homepage
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addProductToGird(){
        items = new ArrayList<>(getProducts());
        int girdColumn =0;
        int girdRow =1;
        try {
            //records
            for (Item item : items) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/brightlightshop/component.fxml"));

                AnchorPane temp = fxmlLoader.load();
                ItemComponentController itemController = fxmlLoader.getController();
                itemController.setData(item);

                if (girdColumn == 4){
                    girdColumn =0;
                    girdRow++;
                }
                girdPaneAllIteam.add(temp, girdColumn++,girdRow);
                GridPane.setMargin(temp, new Insets(5));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addChoicesToComboBoxAllItemPage(){
        sortTimeComboBox.setItems(comboboxChoices);
    }
}
