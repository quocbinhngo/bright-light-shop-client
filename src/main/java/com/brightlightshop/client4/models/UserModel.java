package com.brightlightshop.client4.models;

import com.brightlightshop.client4.types.User;

public class UserModel {
    private static User currentUser;

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
