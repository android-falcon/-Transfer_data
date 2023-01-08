package com.hiaryabeer.transferapp.Interfaces;

import android.content.ClipData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.ItemSwitch;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    public long[] insertAll(List<AllItems> allItems);

    @Query("SELECT * FROM ITEM_TABLE")
    List<AllItems> getAll();

    @Query("DELETE FROM ITEM_TABLE")
    void dELETEAll();

    @Query("SELECT Has_Serial FROM ITEM_TABLE WHERE ITEMOCODE = :itemCode")
    List<String> itemHasSerial(String itemCode);

    @Query("SELECT DISTINCT Item_Kind FROM ITEM_TABLE WHERE Item_Category = :category AND TRIM(Item_Kind) <> '' ")
    List<String> getKinds(String category);

    @Query("SELECT DISTINCT Item_Kind FROM ITEM_TABLE WHERE Item_Kind IS NOT NULL AND TRIM(Item_Kind) <> '' ")
    List<String> getAllKinds();
    @Query("SELECT ITEMNAME FROM ITEM_TABLE WHERE ITEMOCODE = :itemcode")
    String getitemname(String itemcode);


    @Query("SELECT * FROM ITEM_TABLE where ITEMOCODE =:code")
    AllItems getItem(String code);

}
