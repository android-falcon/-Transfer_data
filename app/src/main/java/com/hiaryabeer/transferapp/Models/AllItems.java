package com.hiaryabeer.transferapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    String ItemName;
    @ColumnInfo(name = "ITEMOCODE")
    String ItemOcode;
    @ColumnInfo(name = "BARCODE")
    String barCode;
    @ColumnInfo(name = "Has_Serial")
    String hasSerial;
    @ColumnInfo(name = "Item_Category")
    String category;
    @ColumnInfo(name = "Item_Kind")
    String kind;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getHasSerial() {
        return hasSerial;
    }

    public void setHasSerial(String hasSerial) {
        this.hasSerial = hasSerial;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemOcode() {
        return ItemOcode;
    }

    public void setItemOcode(String itemOcode) {
        ItemOcode = itemOcode;
    }
}
