package com.brightlightshop.client4.types;

public class Game extends Item {
    public Game(String _id, int itemCode, int publishedYear, String title, String rentalType, String imageUrl, double rentalFee, int copiesNumber, int availableNumber) {
        super(_id, itemCode, publishedYear, title, rentalType, imageUrl, rentalFee, copiesNumber, availableNumber);
    }

    public Game() {
        super();
    }

    @Override
    public String toString() {
        return "Game{" +
                "_id='" + _id + '\'' +
                ", itemCode=" + itemCode +
                ", publishedYear=" + publishedYear +
                ", title='" + title + '\'' +
                ", rentalType='" + rentalType + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rentalFee=" + rentalFee +
                ", copiesNumber=" + copiesNumber +
                ", availableNumber=" + availableNumber +
                '}';
    }
}
