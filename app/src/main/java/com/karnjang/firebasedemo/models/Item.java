package com.karnjang.firebasedemo.models;

/**
 * Created by ssppy on 19-Jan-18.
 */

public class Item {

    private String itemId;
    private String itemName;
    private int itemPrice;
    private int itemPicture;

    public Item (String itemId, String itemName, int itemPrice, int itemPicture){

        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemPicture = itemPicture;


    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(int itemPicture) {
        this.itemPicture = itemPicture;
    }
}
