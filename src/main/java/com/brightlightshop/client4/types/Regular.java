package com.brightlightshop.client4.types;

public class Regular extends Customer {
    public Regular(String _id, String firstName, String lastName, String username, String address, String phone, String password, String accountType, double balance, int customerCode) {
        super(_id, firstName, lastName, username, address, phone, password, accountType, balance, customerCode);
    }

    @Override
    public String toString() {
        return "Regular{" +
                "balance=" + balance +
                ", customerCode=" + customerCode +
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
