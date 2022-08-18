package com.brightlightshop.client4.controllers.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthPage {
    @FXML
    private TextField signInUsernameTextField;
    @FXML
    private PasswordField signInPasswordField;
    @FXML
    private Button signInButton;
    @FXML
    private TextField registerEmailTextField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private Button createAccountButton;
    @FXML
    private TextField registerFirstNameTextField;
    @FXML
    private TextField registerLastNameTextField;
    @FXML
    private TextField registerPhoneNumberTextField;
    @FXML
    private TextField registerAddressTextField;
    @FXML
    private TextField registerUsernameTextField;
    @FXML
    private PasswordField registerConfirmPasswordField;

    String username, password, firstName, lastName, phoneNumber, address, email, confirmPassword;
    public boolean signIn(ActionEvent event){
        //check auth
        username = signInUsernameTextField.getText();
        password = signInPasswordField.getText();
        return true;
    }

    public boolean registerAccount(ActionEvent event){
        //check auth
        firstName = registerFirstNameTextField.getText();
        lastName = registerLastNameTextField.getText();
        address = registerAddressTextField.getText();
        phoneNumber = registerPhoneNumberTextField.getText();
        email = registerEmailTextField.getText();
        username = registerEmailTextField.getText();
        password = registerPasswordField.getText();
        return true;
    }
}
