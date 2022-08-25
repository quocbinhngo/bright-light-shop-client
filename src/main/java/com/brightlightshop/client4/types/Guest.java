package com.brightlightshop.client4.types;

public class Guest extends Customer {

    public Guest(String _id, String firstName, String lastName, String username, String address, String phone, String password, String accountType, double balance, int customerCode) {
        super(_id, firstName, lastName, username, address, phone, password, accountType, balance, customerCode);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "balance=" + balance +
                ", _id='" + _id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
