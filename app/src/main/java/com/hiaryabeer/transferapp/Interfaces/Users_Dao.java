package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.transferapp.Models.User;

import java.util.List;

@Dao
public interface Users_Dao {

    @Query("SELECT * FROM Users_Table")
    List<User> getAllUsers();

    @Insert
    void insertAllUsers(User... users);

    @Insert
    void addAll(List<User> users);

    @Query("DELETE FROM Users_Table")
    void deleteAll();

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);




    @Query("SELECT * FROM Users_Table WHERE User_Name = :username")
    List<User> getSameUsers(String username);
    @Query("SELECT User_Name FROM Users_Table WHERE User_ID = :userno")
   String getUserName(String userno);



}
