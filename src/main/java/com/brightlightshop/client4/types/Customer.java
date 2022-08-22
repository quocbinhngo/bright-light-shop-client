package com.brightlightshop.client4.types;

public abstract class Customer extends User {
    protected double balance;
    protected int customerCode;

    public Customer(String _id, String firstName, String lastName, String username, String address, String phone, String password, String accountType, double balance) {
        super(_id, firstName, lastName, username, address, phone, password, accountType);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
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
