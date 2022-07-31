package com.hiaryabeer.transferapp.Models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ItemSwitch_TABLE")
public class ItemSwitch {
    @ColumnInfo(name = "item_Switch")
    private String  item_Switch  ;
    @ColumnInfo(name = "item_NAMEA")
    private String  item_NAMEA ;
    @ColumnInfo(name = "item_OCODE")
    private String  item_OCODE ;
    @ColumnInfo(name = "item_NCODE")
    private String  item_NCODE  ;
    @PrimaryKey(autoGenerate = true)
    int SERIAL;
    public ItemSwitch() {
    }

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getItem_Switch() {
        return item_Switch;
    }

    public void setItem_Switch(String item_Switch) {
        this.item_Switch = item_Switch;
    }

    public String getItem_NAMEA() {
        return item_NAMEA;
    }

    public void setItem_NAMEA(String item_NAMEA) {
        this.item_NAMEA = item_NAMEA;
    }

    public String getItem_OCODE() {
        return item_OCODE;
    }

    public void setItem_OCODE(String item_OCODE) {
        this.item_OCODE = item_OCODE;
    }

    public String getItem_NCODE() {
        return item_NCODE;
    }

    public void setItem_NCODE(String item_NCODE) {
        this.item_NCODE = item_NCODE;
    }
}