package com.karnjang.firebasedemo.models;

import java.util.List;

/**
 * Created by ssppy on 19-Jan-18.
 */

public class Store {
    private String storeID ;
    private String storeName ;
    private String storeDesc ;
    private String storeType ;
    private String storeLocation ;
    private Task TASK;
    private List<Item> ITEMS;


    public Store( ) {

    }


    public String getStoreID() {
        return storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public Task getTASK() {
        return TASK;
    }

    public List<Item> getITEMS() {
        return ITEMS;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setTASK(Task TASK) {
        this.TASK = TASK;
    }

    public void setITEMS(List<Item> ITEMS) {
        this.ITEMS = ITEMS;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }
}
