package com.brightlightshop.client4.utils;

import com.brightlightshop.client4.models.UserModel;

public class FXMLPath {
    public static String root = "/com/brightlightshop/client4/";

    public static String getAuthPagePath() {
        return root + "AuthPage.fxml";
    }

    public static String getNavigationBarComponentPath() {
        if (UserModel.getCurrentUser().getAccountType().equals("admin")) {
            return root + "NavigationBarAdminComponent.fxml";
        }

        return root + "NavigationBarCustomerComponent.fxml";
    }

    public static String getViewCustomersPagePath() {
        return root + "ViewCustomersPage.fxml";
    }

    public static String getOrderDetailCheckoutComponentPath() {
        return root + "OrderDetailCheckoutComponent.fxml";
    }

    public static String getOrderComponentPath() {
        return root + "OrderComponent.fxml";
    }

    public static String getOrderDetailComponentPath() {
        return root + "OrderDetailComponent.fxml";
    }

    public static String getViewItemsPagePath() {
        return root + "ViewItemsPage.fxml";
    }

    public static String getViewItemCustomerPagePath() {
        return root + "ViewItemPageCustomer.fxml";
    }

    public static String getViewItemPagePath() {
        if (UserModel.getCurrentUser().getAccountType().equals("admin")) {
            return getUpdateItemPagePath();
        }

        return getViewItemCustomerPagePath();
    }

    public static String getViewOrdersPagePath() {
        return root + "ViewOrdersPage.fxml";
    }

    public static String getViewOrderPagePath() {
        return root + "ViewOrderPage.fxml";
    }

    public static String getUpdateItemPagePath() {
        return root + "UpdateItemPage.fxml";
    }

    public static String getHomePagePath() {
        return root + "HomePage.fxml";
    }

    public static String getUserInfoPagePath() {
        return root + "ViewUserInfoPage.fxml";
    }

    public static String getCreateItemPagePath() {
        return root + "CreateItemPage.fxml";
    }

    public  static String getLogoIcon(){return root + "images/logoIcon.png";}
}

