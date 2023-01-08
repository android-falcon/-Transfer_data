package com.hiaryabeer.transferapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ITEM_TABLE")
public class AllItems {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    @ColumnInfo(name = "ITEMNAME")
    String ItemNameA;

    @SerializedName("ItemOCode")
    @ColumnInfo(name = "ITEMOCODE")
    String ItemOcode;

    public String getItemOcode() {
        return ItemOcode;
    }

    public void setItemOcode(String itemOcode) {
        ItemOcode = itemOcode;
    }

    @ColumnInfo(name = "BARCODE")
    String ItemNCode;
    @ColumnInfo(name = "Has_Serial")
    String ITEMHASSERIAL;
    @ColumnInfo(name = "Item_Category")
    String ItemG;
    @ColumnInfo(name = "Item_Kind")
    String ItemK;

    public String getItemG() {
        return ItemG;
    }

    public void setItemG(String itemG) {
        this.ItemG = itemG;
    }

    public String getItemK() {
        return ItemK;
    }

    public void setItemK(String itemK) {
        this.ItemK = itemK;
    }

    public String getITEMHASSERIAL() {
        return ITEMHASSERIAL;
    }

    public void setITEMHASSERIAL(String ITEMHASSERIAL) {
        this.ITEMHASSERIAL = ITEMHASSERIAL;
    }

    public String getItemNCode() {
        return ItemNCode;
    }

    public void setItemNCode(String itemNCode) {
        this.ItemNCode = itemNCode;
    }

    public String getItemNameA() {
        return ItemNameA;
    }

    public void setItemNameA(String itemNameA) {
        ItemNameA = itemNameA;
    }


}
