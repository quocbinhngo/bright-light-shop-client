package com.brightlightshop.client4.types;

public class Vip extends Customer {
    private int rewardPoint;

    public Vip(String _id, String firstName, String lastName, String username, String address, String phone, String password, String accountType, double balance, int customerCode, int rewardPoint) {
        super(_id, firstName, lastName, username, address, phone, password, accountType, balance, customerCode);
        this.rewardPoint = rewardPoint;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    @Override
    public String toString() {
        return "Vip{" +
                "rewardPoint=" + rewardPoint +
                ", balance=" + balance +
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
