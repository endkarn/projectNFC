package com.karnjang.firebasedemo.models;

import java.util.List;

/**
 * Created by ssppy on 19-Jan-18.
 */

public class Store {
    private String storeID ;
    private String storeName ;
    private Task TASK;
    private List<Item> ITEMS;

    public Store() {

    }


    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Task getTASK() {
        return TASK;
    }

    public void setTASK(Task TASK) {
        this.TASK = TASK;
    }

    public List<Item> getITEMS() {
        return ITEMS;
    }

    public void setITEMS(List<Item> ITEMS) {
        this.ITEMS = ITEMS;
    }
}
