package com.brightlightshop.client4.types;

import com.brightlightshop.client4.utils.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public abstract class Item {
    protected String _id;
    protected int itemCode;
    protected int publishedYear;
    protected String title;
    protected String rentalType;
    protected String imageUrl;
    protected double rentalFee;
    protected int copiesNumber;
    protected int availableNumber;



    public Item(String _id, int itemCode, int publishedYear, String title, String rentalType, String imageUrl, double rentalFee, int copiesNumber, int availableNumber) {
        this._id = _id;
        this.itemCode = itemCode;
        this.publishedYear = publishedYear;
        this.title = title;
        this.rentalType = rentalType;
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

    public String getItemIdentifier() {
        return String.format("I%s-%d", StringUtils.leftPad(String.valueOf(itemCode), 3, "0"), publishedYear);
    }

    @Override
    public String toString() {
        return "Item{" +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(_id, item._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }

    public String capitalizeFistLetter(String str){
        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        return cap;
    }
}
