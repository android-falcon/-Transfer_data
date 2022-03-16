package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hiaryabeer.transferapp.Models.SerialsModel;

import java.util.List;


@Dao
public interface SerialsDao {

    @Insert
    long insert(SerialsModel serialsModel);

    @Insert
    long[] insertAll(List<SerialsModel> serialsModels);

    @Query("SELECT * FROM SERIALS_TABLE")
    List<SerialsModel> getAllSerials();

    @Query("SELECT * FROM SERIALS_TABLE WHERE Store = :store")
    List<SerialsModel> getSerialsInStore(String store);

    @Query("SELECT Serial_No FROM SERIALS_TABLE WHERE Store = :store AND Item_No = :itemNo")
    List<String> getItemSerialsInStore(String store, String itemNo);

    @Query("SELECT Item_No FROM SERIALS_TABLE WHERE Serial_No = :serialNo")
    List<String> getItemNo(String serialNo);

    @Query("DELETE FROM SERIALS_TABLE")
    int deleteAllSerials();

}
