package com.brightlightshop.client4.controllers.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewCustomersPageController implements Initializable {

    /*public void switchToLoginScreen(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SignInPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        signInPage = new Scene(root);
        stage.setScene(signInPage);
        stage.show();
    }

    public void switchToCustomerManagement(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ViewCustomersPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        customerManagement = new Scene(root);
        stage.setScene(customerManagement);
        stage.show();
    }
    */
    @FXML
    private ListView<String> customerList;
    @FXML
    private ChoiceBox<String> accountType;
    private String[] customerType = {"Guest", "Regular", "VIP"};

    public void getCustomerType(ActionEvent event){
        customerList.getItems().clear();
        switch (accountType.getValue()){
            case "Guest":
                customerList.getItems().addAll(customerGuest);
                break;
            case "Regular":
                customerList.getItems().addAll(customerRegular);
                break;
            case "VIP":
                customerList.getItems().addAll(customerVIP);
                break;
        }
    }
    @FXML
    private Label selectedCustomer;
    String[] customerGuest = {"Guest1", "Guest2", "Guest3"};
    String[] customerRegular = {"Regular1", "Regular2", "Regular3"};
    String[] customerVIP = {"VIP1", "VIP2", "VIP3"};

    String selected;

    public void initialize(URL arg0, ResourceBundle arg1){
        accountType.getItems().addAll(customerType);
        accountType.setOnAction(this::getCustomerType);

        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selected = customerList.getSelectionModel().getSelectedItem();
                selectedCustomer.setText(selected);
            }
        });
    }




}
