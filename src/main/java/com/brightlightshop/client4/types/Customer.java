package com.brightlightshop.client4.types;

import org.apache.commons.lang3.StringUtils;

public abstract class Customer extends User {
    protected double balance;
    protected int customerCode;

    public Customer(String _id, String firstName, String lastName, String username, String address, String phone, String password, String accountType, double balance, int customerCode) {
        super(_id, firstName, lastName, username, address, phone, password, accountType);
        this.balance = balance;
        this.customerCode = customerCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance+10000000;
    }

    public String getCustomerIdentifier() {
        return String.format("C%s", StringUtils.leftPad(String.valueOf(customerCode), 3, "0"));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "balance=" + balance +
                ", customerCode=" + customerCode +
                ", _id='" + _id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
