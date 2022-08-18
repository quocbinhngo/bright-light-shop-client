package com.brightlightshop.client4.types;

public class Dvd extends Item {
    private String genre;

    public Dvd(String _id, int itemCode, int publishedYear, String title, String rentalType, String imageUrl, double rentalFee, int copiesNumber, int availableNumber, String genre) {
        super(_id, itemCode, publishedYear, title, rentalType, imageUrl, rentalFee, copiesNumber, availableNumber);
        this.genre = genre;
    }

    public Dvd(String genre) {
        super();
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Dvd{" +
                "genre='" + genre + '\'' +
                ", _id='" + _id + '\'' +
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
