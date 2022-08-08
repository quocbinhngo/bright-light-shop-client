package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.controllers.components.ItemComponentController;
import com.brightlightshop.client4.types.Item;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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


    ArrayList <Item> records;
    ArrayList <Item> dvds;
    ArrayList<Item> games;

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
        records = new ArrayList<>(getRecord());
        dvds = new ArrayList<>(getDvds());
        games = new ArrayList<>(getGames());

        try{
            addNavigationBar();

            //add records to homepage
            for (Item record: records){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/brightlightshop/component.fxml"));

                AnchorPane temp = fxmlLoader.load();
                ItemComponentController itemController = fxmlLoader.getController();
                itemController.setData(record);
                recordComponentContainer.getChildren().add(temp);
            }

            //add dvds to homepage
            for (Item dvd: dvds){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/brightlightshop/component.fxml"));

                AnchorPane temp = fxmlLoader.load();
                ItemComponentController itemController = fxmlLoader.getController();
                itemController.setData(dvd);
                dvdComponentContainer.getChildren().add(temp);
            }

            //add games to homepage
            for (Item game: games){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/brightlightshop/component.fxml"));

                AnchorPane vBox = fxmlLoader.load();
                ItemComponentController itemController = fxmlLoader.getController();
                itemController.setData(game);
                gameComponentContainer.getChildren().add(vBox);
            }
        } catch (IOException e){
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


    private ArrayList<Item> getRecord(){ //1030x210
        ArrayList<Item> records = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Item record = new Item();
            record.setTitle("Jurassic Park");
            record.setRentalFee(50);
            record.setImageUrl("/com/example/brightlightshop/image/jurassicpark.jpg");
            records.add(record);
        }

        return records;
    }

    // DvDs
    private ArrayList<Item> getDvds(){
        ArrayList<Item> dvds = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Item dvd = new Item();
            dvd.setTitle("Jurassic Park");
            dvd.setRentalFee(50);
            dvd.setImageUrl("/com/example/brightlightshop/image/thebatman.png");
            dvds.add(dvd);
        }

        return dvds;
    }

    //Games
    private ArrayList<Item> getGames(){
        ArrayList<Item> games = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Item game = new Item();
            game.setTitle("Jurassic Park");
            game.setRentalFee(50);
            game.setImageUrl("/com/example/brightlightshop/image/thebatman.png");
            games.add(game);
        }

        return games;
    }

    //Add navigation bar
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


}
