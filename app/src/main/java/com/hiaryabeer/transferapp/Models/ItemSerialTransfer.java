package com.hiaryabeer.transferapp.Models;

import android.util.Log;


import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "ITEM_SERIAL_TRANSFERS")
public class ItemSerialTransfer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Voucher_No")
    private String voucherNo;

    @ColumnInfo(name = "Device_Id")
    private String deviceId;

    @ColumnInfo(name = "Item_Code")
    private String itemCode;

    @ColumnInfo(name = "Serial_No")
    private String serialNo;

    @ColumnInfo(name = "Qty")
    private String qty;

    @ColumnInfo(name = "Added_To_Rep")
    private String added;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "From_Store")
    private String fromStore;

    @ColumnInfo(name = "To_Store")
    private String toStore;

    @ColumnInfo(name = "Posted")
    private String isPosted;

    @ColumnInfo(name = "vSerial", defaultValue = "0")
    private int vSerial;

    public ItemSerialTransfer(String voucherNo, String deviceId, String itemCode, String serialNo,
                              String date, String fromStore, String toStore) {
        this.voucherNo = voucherNo;
        this.deviceId = deviceId;
        this.itemCode = itemCode;
        this.serialNo = serialNo;
        this.added = "1";
        this.isPosted = "0";
        this.date = date;
        this.fromStore = fromStore;
        this.toStore = toStore;
        this.qty = "1";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromStore() {
        return fromStore;
    }

    public String getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(String isPosted) {
        this.isPosted = isPosted;
    }

    public void setFromStore(String fromStore) {
        this.fromStore = fromStore;
    }

    public String getToStore() {
        return toStore;
    }

    public void setToStore(String toStore) {
        this.toStore = toStore;
    }

    public int getVSerial() {
        return vSerial;
    }

    public void setVSerial(int vSerial) {
        this.vSerial = vSerial;
    }

    public JSONObject getJSONObjectSerials() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("VHFNO", voucherNo);
            obj.put("ITEMCODE", itemCode);
            obj.put("ITEMSERIAL", serialNo);
            obj.put("QTY", qty);
            obj.put("FROMSTR", fromStore);
            obj.put("VSERIAL", vSerial);

        } catch (JSONException e) {
            Log.e("Tag", "JSONException");
        }
        return obj;
    }
}
