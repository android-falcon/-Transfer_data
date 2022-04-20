package com.hiaryabeer.transferapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SETTINGS_TABLE")
public class appSettings {
    @ColumnInfo(name = "IP_ADDRESS")
    String IP;
    @ColumnInfo(name = "COMPANYNO")
    String CompanyNum;
    @ColumnInfo(name = "YEARS")
    String years;
    @ColumnInfo(name = "UPDATEQTY")
    String UpdateQTY;
    @ColumnInfo(name = "USERNO")
    String userNumber;
    @PrimaryKey(autoGenerate = true)
    public int SERIALZONE;

    @ColumnInfo(name = "DEVICEID")
    String deviceId;
    @ColumnInfo(name = "PORT")
    String port;

    //////B
    @ColumnInfo(name = "Check_Qty")
    private String checkQty;

    @ColumnInfo(name = "Rawahneh_Add_Item")
    private String rawahneh_add_item;

    @ColumnInfo(name = "Print_Option") // 0 -- Wi-Fi _ 1 -- Bluetooth
    private Integer print_option;

    public Integer getPrint_option() {
        return print_option;
    }

    public void setPrint_option(Integer print_option) {
        this.print_option = print_option;
    }

    public String getRawahneh_add_item() {
        return rawahneh_add_item;
    }

    public void setRawahneh_add_item(String rawahneh_add_item) {
        this.rawahneh_add_item = rawahneh_add_item;
    }

    public String getCheckQty() {
        return checkQty;
    }

    public void setCheckQty(String checkQty) {
        this.checkQty = checkQty;
    }
    /////

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public appSettings(String IP, String companyNum, String years, String updateQTY, String userNumber, String checkQty) {
        this.IP = IP;
        CompanyNum = companyNum;
        this.years = years;
        UpdateQTY = updateQTY;
        this.userNumber = userNumber;
        ///////B
        this.checkQty = checkQty;

    }

    public appSettings() {

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getCompanyNum() {
        return CompanyNum;
    }

    public void setCompanyNum(String companyNum) {
        CompanyNum = companyNum;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getUpdateQTY() {
        return UpdateQTY;
    }

    public void setUpdateQTY(String updateQTY) {
        UpdateQTY = updateQTY;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
