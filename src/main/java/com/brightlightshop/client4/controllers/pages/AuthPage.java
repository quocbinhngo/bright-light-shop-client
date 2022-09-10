package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.User;
import com.brightlightshop.client4.utils.Component;
import com.brightlightshop.client4.utils.FXMLPath;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthPage implements Initializable {
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String userId = "62ec74b4f13a1bbf8d94f560";
    private final OkHttpClient client = new OkHttpClient();
    @FXML
    private Button createAccountButton;

    @FXML
    private HBox hBox;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField registerAddressTextField;

    @FXML
    private PasswordField registerConfirmPasswordField;

    @FXML
    private TextField registerFirstNameTextField;

    @FXML
    private TextField registerLastNameTextField;

    @FXML
    private Label registerMessageLabel;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private TextField registerPhoneNumberTextField;

    @FXML
    private TextField registerUsernameTextField;

    @FXML
    private Button signInButton;

    @FXML
    private PasswordField signInPasswordField;

    @FXML
    private TextField signInUsernameTextField;

    @FXML
    private VBox vBoxLeft;

    @FXML
    private VBox vBoxRight;

    @FXML
    private ImageView loadingImageView;

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
                .url(UrlConstant.createUser())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) != '2') {
                handleRegisterError(response.body().string());
                return "";
            }

            return response.body().string();
        }
    }

    private void handleLoginError(String error) {
        loginMessageLabel.setText(error);
        loginMessageLabel.setTextFill(Color.RED);
    }

    private void handleRegisterError(String error) {
        registerMessageLabel.setText(error);
    }

    private String createSessionPostRequest() throws IOException {
        RequestBody body = RequestBody.create(createSessionPostJson()
                , JSON);
        Request request = new Request.Builder()
                .url(UrlConstant.createSession())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (String.valueOf(response.code()).charAt(0) != '2') {
                handleLoginError(response.body().string());
                return "";
            }

            return response.body().string();
        }
    }

    public boolean registerAccount(ActionEvent e) throws IOException {
        if (!registerPasswordField.getText().equals(registerConfirmPasswordField.getText())){
            registerMessageLabel.setText("Please confirm your password");
            registerMessageLabel.setTextFill(Color.RED);
            return false;
        }

        loadingImageView.setVisible(true);
        String response = createUserPostRequest();

        if (response.equals("")) {
            loadingImageView.setVisible(false);
            return false;
        }


        clearAll();
        registerMessageLabel.setText("Successfully registered!");
        registerMessageLabel.setTextFill(Color.BLACK);
        loadingImageView.setVisible(false);
        return true;
    }

    public boolean signIn(ActionEvent e) throws IOException{
        loadingImageView.setVisible(true);
        String response = createSessionPostRequest();

        if (response.equals("")) {
            loadingImageView.setVisible(false);
            return false;
        }
        clearAll();
        JSONObject userInfo = new JSONObject(response);
        UserModel.setCurrentUser(JsonParser.getUser(userInfo));
        moveToHomePage(e);
        loadingImageView.setVisible(false);
        return true;
    }

    private void moveToHomePage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.getHomePagePath()));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void signInButtonEnteredAuthPage() {
        signInButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void signInButtonExitedAuthPage() {
        signInButton.setStyle("-fx-background-color: #ffbd73");
    }

    @FXML
    protected void createAccountButtonEnteredAuthPage() {
        createAccountButton.setStyle("-fx-background-color: #e08e35");
    }

    @FXML
    protected void createAccountButtonExitedAuthPage() {
        createAccountButton.setStyle("-fx-background-color: #ffbd73");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingImageView.setVisible(false);
        setupTextField();
    }

    private void setupTextField() {
        Component.numericTextField(registerPhoneNumberTextField);
    }
}
