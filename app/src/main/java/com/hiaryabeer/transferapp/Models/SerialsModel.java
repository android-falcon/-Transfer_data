package com.hiaryabeer.transferapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Serials_Table")
public class SerialsModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    int id;

    @ColumnInfo(name = "Store")
    String store;

    @ColumnInfo(name = "Item_No")
    String itemNo;

    @ColumnInfo(name = "Serial_No")
    String serialNo;

    public SerialsModel(String store, String itemNo, String serialNo) {
        this.store = store;
        this.itemNo = itemNo;
        this.serialNo = serialNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
