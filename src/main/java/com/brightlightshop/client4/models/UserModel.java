package com.brightlightshop.client4.models;

import com.brightlightshop.client4.constants.UrlConstant;
import com.brightlightshop.client4.types.User;
import com.brightlightshop.client4.utils.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class UserModel {
    private static User currentUser;
    private static final OkHttpClient client = new OkHttpClient();


    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void update() throws IOException {
        String response = getUserRequest();
        setCurrentUser(JsonParser.getUser(new JSONObject(response)));
    }

    private static String getUserRequest() throws IOException {
        Request request = new Request.Builder()
                .url(UrlConstant.getUser())
                .get()
                .addHeader("user-id", UserModel.getCurrentUser().get_id())
                .build();

        try(Response response = client.newCall(request).execute()) {
            String statusCode = String.valueOf(response.code());
            if (statusCode.charAt(0) == '4' || statusCode.charAt(0) == 5) {
                return "";
            }

            return response.body().string();
        }
    }
}
