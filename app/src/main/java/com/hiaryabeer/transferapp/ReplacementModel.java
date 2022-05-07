package com.hiaryabeer.transferapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "REPLACEMENT_TABLE")
public class ReplacementModel implements Parcelable {

    @ColumnInfo(name = "UserNO")
    String UserNO;

    protected ReplacementModel(Parcel in) {
        UserNO = in.readString();
        From = in.readString();
        To = in.readString();
        Zone = in.readString();
        Itemcode = in.readString();
        IsPosted = in.readString();
        ReplacementDate = in.readString();
        itemname = in.readString();
        transNumber = in.readString();
        availableQty = in.readString();
        deviceId = in.readString();
        recQty = in.readString();
        SERIALZONE = in.readInt();
        ToName = in.readString();
        FromName = in.readString();
    }

    public static final Creator<ReplacementModel> CREATOR = new Creator<ReplacementModel>() {
        @Override
        public ReplacementModel createFromParcel(Parcel in) {
            return new ReplacementModel(in);
        }

        @Override
        public ReplacementModel[] newArray(int size) {
            return new ReplacementModel[size];
        }
    };

    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    @ColumnInfo(name = "FROMSTORE")
    String From;
    @ColumnInfo(name = "TOSTORE")
    String To;
    @ColumnInfo(name = "ZONECODE")
    String Zone;
    @ColumnInfo(name = "ITEMCODE")
    String Itemcode;
    @ColumnInfo(name = "ISPOSTED")
    String IsPosted;
    @ColumnInfo(name = "REPLACEMENTDATE")
    String ReplacementDate;

    @ColumnInfo(name = "ITEMNAME")
    String itemname;
    @ColumnInfo(name = "TransNumber")
    String transNumber;

    @ColumnInfo(name = "availableQty")
    String availableQty;

    public String getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(String qty) {
        availableQty = qty;
    }


    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    @ColumnInfo(name = "DEVICEID")
    String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRecQty() {
        return recQty;
    }

    public void setRecQty(String recQty) {
        this.recQty = recQty;
    }

    @ColumnInfo(name = "RECQTY")
    private String recQty;
    @PrimaryKey(autoGenerate = true)
    int SERIALZONE;

    public ReplacementModel(String from, String to, String zone, String itemcode, String availableQty) {
        From = from;
        To = to;
        Zone = zone;
        Itemcode = itemcode;
        this.availableQty = availableQty;
    }
    public ReplacementModel(String from, String to, String itemcode, String recQty) {
        From = from;
        To = to;
        Itemcode = itemcode;
        this.recQty = recQty;
    }

    String ToName;

    String FromName;

    public String getToName() {
        return ToName;
    }

    public void setToName(String toName) {
        ToName = toName;
    }

    public String getFromName() {
        return FromName;
    }

    public void setFromName(String fromName) {
        FromName = fromName;
    }

    public String getIsPosted() {
        return IsPosted;
    }

    public void setIsPosted(String isPosted) {
        IsPosted = isPosted;
    }

    public String getReplacementDate() {
        return ReplacementDate;
    }

    public void setReplacementDate(String replacementDate) {
        ReplacementDate = replacementDate;
    }

    public int getSERIALZONE() {
        return SERIALZONE;
    }

    public void setSERIALZONE(int SERIALZONE) {
        this.SERIALZONE = SERIALZONE;
    }

    public ReplacementModel() {
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
    }


    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("FROMSTR", From);
            obj.put("TOSTR", To);
            obj.put("ZONE", Zone);
            obj.put("ITEMCODE", Itemcode);
            obj.put("QTY", recQty);
            obj.put("DEVICEID", deviceId);
            obj.put("VHFNO", transNumber);

        } catch (JSONException e) {
            Log.e("Tag", "JSONException");
        }
        return obj;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(UserNO);
        dest.writeString(From);
        dest.writeString(To);
        dest.writeString(Zone);
        dest.writeString(Itemcode);
        dest.writeString(IsPosted);

        dest.writeString(ReplacementDate);
        dest.writeString(itemname);
        dest.writeString(transNumber);
        dest.writeString(availableQty);
        dest.writeString(deviceId);
        dest.writeString(recQty);

        dest.writeInt(SERIALZONE);
        dest.writeString(ToName);
        dest.writeString(FromName);

    }
}

