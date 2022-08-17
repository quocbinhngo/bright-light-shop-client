package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.User;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import okhttp3.*;
import org.json.*;

import java.io.IOException;

public class AuthPage {
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String createUserPostUrl = "http://localhost:8000/api/users/customers";
    private final String createSessionPostUrl = "http://localhost:8000/api/sessions/";
    private final String userId = "62ec74b4f13a1bbf8d94f560";
    private final OkHttpClient client = new OkHttpClient();
    @FXML
    private TextField signInUsernameTextField;
    @FXML
    private PasswordField signInPasswordField;
    @FXML
    private Button signInButton;
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

    private String createUserPostJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", registerFirstNameTextField.getText());
        jsonObject.put("lastName", registerLastNameTextField.getText());
        jsonObject.put("username", registerUsernameTextField.getText());
        jsonObject.put("address", registerAddressTextField.getText());
        jsonObject.put("phone", registerPhoneNumberTextField.getText());
        jsonObject.put("password", registerPasswordField.getText());
        return jsonObject.toString();
    }

    public void clearAll(){
        registerFirstNameTextField.clear();
        registerLastNameTextField.clear();
        registerUsernameTextField.clear();
        registerAddressTextField.clear();
        registerPhoneNumberTextField.clear();
        registerPasswordField.clear();
        registerConfirmPasswordField.clear();
        signInUsernameTextField.clear();
        signInPasswordField.clear();
    }

    private String createSessionPostJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", signInUsernameTextField.getText());
        jsonObject.put("password", signInPasswordField.getText());
        return jsonObject.toString();
    }

    private String createUserPostRequest() throws IOException {
        RequestBody body = RequestBody.create(createUserPostJson(), JSON);
        Request request = new Request.Builder()
                .url(createUserPostUrl)
                .post(body)
                .addHeader("user-id", userId)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private String createSessionPostRequest() throws IOException {
        RequestBody body = RequestBody.create(createSessionPostJson(), JSON);
        Request request = new Request.Builder()
                .url(createSessionPostUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public boolean registerAccount(ActionEvent e) throws IOException {
        if (!registerPasswordField.getText().equals(registerConfirmPasswordField.getText())){
            System.out.println("Please confirm your password");
            return false;
        }
        String response = createUserPostRequest();
        clearAll();
        System.out.println(response);
        return true;
    }

    public boolean signIn(ActionEvent e) throws IOException{
        String response = createSessionPostRequest();
        clearAll();
        JSONObject userInfo = new JSONObject(response);
        System.out.println(userInfo);
        UserModel.setUser(JsonParser.getUser(userInfo));
        System.out.println(UserModel.getCurrentUser().toString());
        return true;
    }
}
