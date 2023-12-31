package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hiaryabeer.transferapp.RoomAllData;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "REPLACEMENT_TABLE")
public class ReplacementModel implements Parcelable {
    @ColumnInfo(name = "RMQTY",defaultValue = "1")
    String RMQTY;
    @SerializedName(  "RCVQTY")
    @ColumnInfo(name = "Ser_RCVQTY",defaultValue = "1")
    String    Ser_RCVQTY;

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
    @SerializedName("FROMSTR")
    @ColumnInfo(name = "FROMSTORE")
    String From;
    @SerializedName("TOSTR")
    @ColumnInfo(name = "TOSTORE")
    String To;


    @SerializedName("STORENAME")
    @ColumnInfo(name = "STORENAME",defaultValue="1")
    String STORENAME;

    public String getSTORENAME() {
        return STORENAME;
    }

    public void setSTORENAME(String STORENAME) {
        this.STORENAME = STORENAME;
    }

    @ColumnInfo(name = "ZONECODE")
    String Zone;

    @SerializedName("ITEMCODE")
    @ColumnInfo(name = "ITEMCODE")
    String Itemcode;
    @ColumnInfo(name = "ISPOSTED")
    String IsPosted;
    @ColumnInfo(name = "REPLACEMENTDATE")
    String ReplacementDate;

    @SerializedName("ITEMNAME")
    @ColumnInfo(name = "ITEMNAME")
    String itemname;
    @SerializedName("VHFNO")
    @ColumnInfo(name = "TransNumber")
    String transNumber;

    @ColumnInfo(name = "availableQty")
    String availableQty;

    @ColumnInfo(name = "Cal_Qty")
    String Cal_Qty;

    @ColumnInfo(name = "UnitID")
    String UnitID;

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public String getCal_Qty() {
        return Cal_Qty;
    }

    public void setCal_Qty(String cal_Qty) {
        Cal_Qty = cal_Qty;
    }

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
@SerializedName("ORGQTY")
    @ColumnInfo(name = "RECQTY")
    private String recQty;
    @PrimaryKey(autoGenerate = true)
    int SERIALZONE;

    @ColumnInfo(name = "ISDONE")
    private String ISDONE;

    public String getISDONE() {
        return ISDONE;
    }

    public void setISDONE(String ISDONE) {
        this.ISDONE = ISDONE;
    }

    ////
    @ColumnInfo(name = "WHICHUNIT")
   String WHICHUNIT;
            @ColumnInfo(name = "WHICHUNITSTR")
            String  WHICHUNITSTR;
    @ColumnInfo(name = "WHICHUQTY")
    String WHICHUQTY;
    @ColumnInfo(name = "UNITBARCODE")
    String      UNITBARCODE;

    @ColumnInfo(name = "CALCQTY")
    String CALCQTY;
    @ColumnInfo(name = "ENTERQTY")
    String  ENTERQTY;

    @ColumnInfo(name = "ENTERPRICE")
    String  ENTERPRICE;

    @ColumnInfo(name = "UpdatedQty")
    String  UpdatedQty;


    public String getRMQTY() {
        return RMQTY;
    }

    public void setRMQTY(String RMQTY) {
        this.RMQTY = RMQTY;
    }

    public String getSer_RCVQTY() {
        return Ser_RCVQTY;
    }

    public void setSer_RCVQTY(String ser_RCVQTY) {
        Ser_RCVQTY = ser_RCVQTY;
    }

    public String getUpdatedQty() {
        return UpdatedQty;
    }

    public void setUpdatedQty(String updatedQty) {
        UpdatedQty = updatedQty;
    }

    public String getWHICHUNIT() {
        return WHICHUNIT;
    }

    public void setWHICHUNIT(String WHICHUNIT) {
        this.WHICHUNIT = WHICHUNIT;
    }

    public String getWHICHUNITSTR() {
        return WHICHUNITSTR;
    }

    public void setWHICHUNITSTR(String WHICHUNITSTR) {
        this.WHICHUNITSTR = WHICHUNITSTR;
    }

    public String getWHICHUQTY() {
        return WHICHUQTY;
    }

    public void setWHICHUQTY(String WHICHUQTY) {
        this.WHICHUQTY = WHICHUQTY;
    }

    public String getUNITBARCODE() {
        return UNITBARCODE;
    }

    public void setUNITBARCODE(String UNITBARCODE) {
        this.UNITBARCODE = UNITBARCODE;
    }

    public String getCALCQTY() {
        return CALCQTY;
    }

    public void setCALCQTY(String CALCQTY) {
        this.CALCQTY = CALCQTY;
    }

    public String getENTERQTY() {
        return ENTERQTY;
    }

    public void setENTERQTY(String ENTERQTY) {
        this.ENTERQTY = ENTERQTY;
    }

    public String getENTERPRICE() {
        return ENTERPRICE;
    }

    public void setENTERPRICE(String ENTERPRICE) {
        this.ENTERPRICE = ENTERPRICE;
    }

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
        //CONO=295&VHFNO=1&ITEMCODE=SP0101019&FROMSTR=1&TOSTR=2&QTY=6
        try {
            obj.put("FROMSTR", From);
            obj.put("TOSTR", To);
            obj.put("ZONE", Zone);
            obj.put("ITEMCODE", Itemcode);
            if(CALCQTY!=null)
                if(!CALCQTY.equals(""))
                    obj.put("QTY", Double.parseDouble(recQty)*Double.parseDouble(CALCQTY));
                else    obj.put("QTY", recQty);
          else  obj.put("QTY", recQty);
            obj.put("DEVICEID", deviceId);
            obj.put("VHFNO", transNumber);


          if(WHICHUNIT!=null)  obj.put("WHICHUNIT", WHICHUNIT);
          else
              obj.put("WHICHUNIT", "");

            if(WHICHUNITSTR!=null)   obj.put("WHICHUNITSTR", WHICHUNITSTR);
            else   obj.put("WHICHUNITSTR", "");

            if(WHICHUQTY!=null)   obj.put("WHICHUQTY", WHICHUQTY);
            else obj.put("WHICHUQTY", "");

            if(UNITBARCODE!=null)    obj.put("UNITBARCODE", UNITBARCODE);
            else  obj.put("UNITBARCODE", "");

            if(ENTERPRICE!=null)      obj.put("ENTERPRICE", ENTERPRICE);
            else  obj.put("ENTERPRICE", "");

            if(CALCQTY!=null)    obj.put("CALCQTY", CALCQTY);
            else   obj.put("CALCQTY", "");

            if(ENTERQTY!=null)      obj.put("ENTERQTY", recQty);
            else  obj.put("ENTERQTY", "");


        } catch (JSONException e) {
            Log.e("Tag", "JSONException");
        }
        return obj;
    }
    public JSONObject New_getJSONObjectDelphi(Context context) {
        JSONObject obj = new JSONObject();
        //CONO=295&VHFNO=1&ITEMCODE=SP0101019&FROMSTR=1&TOSTR=2&QTY=6
        try {
             RoomAllData my_dataBase=RoomAllData.getInstanceDataBase(context);
         String   CONO = my_dataBase.settingDao().getCono().trim();
            obj.put("CONO", CONO);
            obj.put("VHFNO",transNumber );
            obj.put("ITEMCODE", Itemcode);
            obj.put("FROMSTR", From);
            obj.put("TOSTR", To);
            obj.put("QTY", UpdatedQty);
            obj.put("ORGQTY", recQty);
            obj.put("ISDONE", ISDONE);
//            obj.put("RCVQTY", Ser_RCVQTY);
//            obj.put("RMQTY", RMQTY);

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

