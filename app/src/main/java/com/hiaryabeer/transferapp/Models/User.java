package com.hiaryabeer.transferapp.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "Users_Table")
public class User {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SERIAL")
    private int serial;

    @ColumnInfo(name = "User_ID")
    private String userId;

    @ColumnInfo(name = "User_Name")
    private String userName;

    @ColumnInfo(name = "User_Password")
    private String userPassword;

    public User() {
    }

    public User(String userId, String userName, String userPassword) {

        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }



    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }




}
