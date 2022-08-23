package com.brightlightshop.client4.controllers.pages;

import com.brightlightshop.client4.models.UserModel;
import com.brightlightshop.client4.types.*;
import com.brightlightshop.client4.types.Record;
import com.brightlightshop.client4.utils.JsonParser;
import javafx.scene.control.Label;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class ViewCustomerPageController {

    private String userId = "62ec74b4f13a1bbf8d94f560";
    private final String getUserByIdGetUrl = "http://localhost:8000/api/users/customers";
    private final OkHttpClient client = new OkHttpClient();
    private Customer customer;

    private Label typeLabel;

    public void handleError(){

    }

    private String getUserByIdRequest() throws Exception {
        Request request = new Request.Builder()
                .url(getUserByIdGetUrl + String.format("/%s", userId))
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
    public void setData(String _id) {
        try {
            userId = _id;
            String customerResponse = getUserByIdRequest();

            if (customerResponse.equals("")) {
                return;
            }

            JSONObject customerJsonObject = new JSONObject(customerResponse);
            customer = (Customer)JsonParser.getUser(customerJsonObject);

            if (customer instanceof Guest) {
                typeLabel.setText("Guest");
            }

            if (customer instanceof Regular) {
                typeLabel.setText("Regular");
            }

            if (customer instanceof Vip) {
                typeLabel.setText("VIP");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

