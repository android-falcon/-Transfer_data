package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.transferapp.Models.ItemsUnit;

import java.util.List;
@Dao
public interface ItemsUnitDao {
 @Insert
 public long[] insertAll(List<ItemsUnit> itemsUnits);

 @Query("SELECT DISTINCT ITEMU FROM ItemsUnit_TABLE WHERE ITEMOCODE = :itemNo AND ITEMU <> ''")
 List<String> getItemUnits(String itemNo);
 @Query("SELECT UQTY FROM ItemsUnit_TABLE WHERE ITEMOCODE = :itemNo AND ITEMU = :unitId")
 String getConvRate(String itemNo, String unitId);
 @Query("DELETE FROM ItemsUnit_TABLE")
 void dELETEAll();
}
