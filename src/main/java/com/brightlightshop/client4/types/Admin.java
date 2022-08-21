package com.brightlightshop.client4.types;

public class Admin extends User {

    public Admin(String _id, String firstName, String lastName, String username, String address, String phone, String password, String accountType) {
        super(_id, firstName, lastName, username, address, phone, password, accountType);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "_id='" + _id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
