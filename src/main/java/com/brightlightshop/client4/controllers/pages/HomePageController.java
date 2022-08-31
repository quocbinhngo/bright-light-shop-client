package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.controllers.components.HomePageBoxComponentController;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.Item;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable  {
    //////////////////////
    private Stage stage;
    private Scene scene;
    private Parent root;
    //////////////////////


    @FXML
    private Label bannerNumberCarousel;

    @FXML
    private AnchorPane carouselPanel1;

    @FXML
    private AnchorPane carouselPanel2;

    @FXML
    private AnchorPane carouselPanel3;

    @FXML
    private HBox dvdComponentContainer;

    @FXML
    private HBox gameComponentContainer;

    @FXML
    private HBox recordComponentContainer;

    @FXML
    private HBox navigationBar;

    @FXML
    private GridPane gridPaneRecord;
    @FXML
    private GridPane gridPaneDvd;
    @FXML
    private GridPane gridPaneGame;


    ArrayList <Item> records;
    ArrayList <Item> dvds;
    ArrayList<Item> games;

    private final OkHttpClient client = new OkHttpClient();

    public HomePageController() {
    }

    //SLIDER
    public void translateAnimation(double timeToProcess, Node node, double width){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(timeToProcess), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translateAnimation(0.5,carouselPanel2,1200);
        translateAnimation(0.5,carouselPanel3,1200);

        //Records, DVDs



        try{
            getItems();
            addNavigationBar();
            updateItemsToGrid();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateItemsToGrid(){
        gridPaneDvd.getChildren().clear();
        gridPaneRecord.getChildren().clear();
        gridPaneGame.getChildren().clear();

        try {
            int column = 1;
            int row = 0;

            //add records to homepage
            for (Item record: records){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/HomePageBoxComponent.fxml"));

                AnchorPane temp = fxmlLoader.load();
                HomePageBoxComponentController itemController = fxmlLoader.getController();
                itemController.setData(record);
                if (row == 1){
                    row = 0;
                    column++;
                }
                gridPaneRecord.add(temp, column ,row++);
                GridPane.setMargin(temp, new Insets(1));
            }

            //add dvds to homepage
            for (Item dvd: dvds){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/HomePageBoxComponent.fxml"));

                AnchorPane temp = fxmlLoader.load();
                HomePageBoxComponentController itemController = fxmlLoader.getController();
                itemController.setData(dvd);
                if (row == 1){
                    row = 0;
                    column++;
                }
                gridPaneDvd.add(temp, column ,row++);
                GridPane.setMargin(temp, new Insets(1));
            }

            //add games to homepage
            for (Item game: games){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/HomePageBoxComponent.fxml"));

                AnchorPane temp = fxmlLoader.load();
                HomePageBoxComponentController itemController = fxmlLoader.getController();
                itemController.setData(game);
                if (row == 1){
                    row = 0;
                    column++;
                }
                gridPaneRecord.add(temp, column ,row++);
                GridPane.setMargin(temp, new Insets(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int current_banner = 1;
    @FXML
    void nextBanner(ActionEvent event){
        if (current_banner ==1){
            translateAnimation(0.5,carouselPanel2,-1200);
            current_banner++;
            bannerNumberCarousel.setText("2 / 3");
        } else if (current_banner == 2) {
            translateAnimation(0.5,carouselPanel3,-1200);
            current_banner++;
            bannerNumberCarousel.setText("3 / 3");
        }
    }
    @FXML
    void backBanner(ActionEvent event){
        if (current_banner == 2){
            translateAnimation(0.5,carouselPanel2, 1200);
            current_banner--;
            bannerNumberCarousel.setText("1 / 3");
        } else if (current_banner == 3) {
            translateAnimation(0.5,carouselPanel3,1200);
            current_banner--;
            bannerNumberCarousel.setText("2 / 3");
        }
    }

    private String getUrl(String rentalType){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UrlConstant.getItems()).newBuilder();
        urlBuilder.addQueryParameter("rentalType", rentalType);
        return urlBuilder.build().toString();
    }

    private String getItemsRequest(String rentalType) throws IOException {
        Request request = new Request.Builder()
                .url(getUrl(rentalType))
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

    private void handleError() {
    }


    private void  getRecords() throws IOException { //1030x210
        String itemsResponse = getItemsRequest("record");
        records = JsonParser.getItems(new JSONArray(itemsResponse));
    }

    // DvDs
    private void getDvds() throws IOException {
        String itemsResponse = getItemsRequest("dvd");
        dvds = JsonParser.getItems(new JSONArray(itemsResponse));
    }

    //Games
    private void getGames() throws IOException {
        String itemsResponse = getItemsRequest("game");
        games = JsonParser.getItems(new JSONArray(itemsResponse));
    }

    private void getItems() throws IOException {
        getDvds();
        getRecords();
        getGames();
    }

    //Add navigation bar
    public void addNavigationBar(){
        try{
            FXMLLoader navigationBarFXMLLoader = new FXMLLoader();
            navigationBarFXMLLoader.setLocation(getClass().getResource("/com/brightlightshop/client4/NavigationBarCustomerComponent.fxml"));
            AnchorPane hbox = navigationBarFXMLLoader.load();

            //put navigation bar into navigationbar container at homepage
            navigationBar.getChildren().add(hbox);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
