package com.brightlightshop.client4.types;

import com.brightlightshop.client4.utils.JsonParser;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class Item {
    private String _id;
    private int itemCode;
    private int publishedYear;
    private String title;
    private String rentalType;
    private String genre;
    private String imageUrl;
    private double rentalFee;
    private int copiesNumber;
    private int availableNumber;



    public Item(String _id, int itemCode, int publishedYear, String title, String rentalType, String genre, String imageUrl, double rentalFee, int copiesNumber, int availableNumber) {
        this._id = _id;
        this.itemCode = itemCode;
        this.publishedYear = publishedYear;
        this.title = title;
        this.rentalType = rentalType;
        this.genre = genre;
        this.imageUrl = imageUrl;
        this.rentalFee = rentalFee;
        this.copiesNumber = copiesNumber;
        this.availableNumber = availableNumber;
    }

    public Item() {

    }

    public static Item createItemFromJson(JSONObject json) {
        return JsonParser.getItem(json);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCopiesNumber() {
        return copiesNumber;
    }

    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    @Override
    public String toString() {
        return "Item{" +
                "_id='" + _id + '\'' +
                ", itemCode=" + itemCode +
                ", publishedYear=" + publishedYear +
                ", title='" + title + '\'' +
                ", rentalType='" + rentalType + '\'' +
                ", genre='" + genre + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rentalFee=" + rentalFee +
                ", copiesNumber=" + copiesNumber +
                ", availableNumber=" + availableNumber +
                '}';
    }
}
