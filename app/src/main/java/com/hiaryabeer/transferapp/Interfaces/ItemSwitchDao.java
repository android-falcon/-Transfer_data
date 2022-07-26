package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.ItemSwitch;

import java.util.List;
@Dao
public interface ItemSwitchDao {
    @Insert
    public long[] insertAll(List<ItemSwitch> allItems);

    @Query("SELECT * FROM ItemSwitch_TABLE")
    List<ItemSwitch> getAll();

    @Query("DELETE FROM ItemSwitch_TABLE")
    void dELETEAll();
    @Query("SELECT item_OCODE FROM ItemSwitch_TABLE where item_NCODE =:ncode")
    String getitemocode(String ncode);

}
