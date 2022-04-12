package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.transferapp.Models.AllItems;

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

}
