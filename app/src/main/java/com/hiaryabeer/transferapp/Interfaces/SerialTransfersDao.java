package com.hiaryabeer.transferapp.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hiaryabeer.transferapp.Models.ItemSerialTransfer;

import java.util.List;

@Dao
public interface SerialTransfersDao {

    @Insert
    void insert(ItemSerialTransfer serialTransfer);

    @Query("SELECT * FROM ITEM_SERIAL_TRANSFERS WHERE Voucher_No = :transNo AND Device_Id = :deviceId")
    List<ItemSerialTransfer> getAllIntrans(String transNo, String deviceId);

    @Query("SELECT Serial_No FROM ITEM_SERIAL_TRANSFERS WHERE Item_Code != :itemCode")
    List<String> getSerialsForOther(String itemCode);

    @Query("SELECT * FROM ITEM_SERIAL_TRANSFERS")
    List<ItemSerialTransfer> getAll();

    @Query("DELETE FROM ITEM_SERIAL_TRANSFERS WHERE Serial_No = :serialNo AND Voucher_No = :transNo AND Device_Id = :deviceId AND Item_Code = :itemCode ")
    int delete(String serialNo, String transNo, String deviceId, String itemCode);

    @Query("DELETE FROM ITEM_SERIAL_TRANSFERS WHERE (Added_To_Rep = '0' OR Added_To_Rep = null) AND Voucher_No != :transNo")
    void deleteNotAddedPrev(String transNo);

    @Query("SELECT * FROM ITEM_SERIAL_TRANSFERS WHERE Voucher_No = :transNo AND Device_Id = :deviceId AND Item_Code = :itemCode AND Added_To_Rep = '0'")
    List<ItemSerialTransfer> getAllNotAdded(String transNo, String deviceId, String itemCode);

    @Query("UPDATE ITEM_SERIAL_TRANSFERS SET Added_To_Rep = '1' WHERE Voucher_No = :transNo AND Device_Id = :deviceId AND Item_Code = :itemCode AND Added_To_Rep = '0'")
    void addToRep(String transNo, String deviceId, String itemCode);

    @Query("DELETE FROM ITEM_SERIAL_TRANSFERS WHERE Added_To_Rep = '1' AND Item_Code = :itemCode AND Voucher_No = :transNo")
    void deleteAllAdded(String itemCode, String transNo);

    @Query("Select * FROM ITEM_SERIAL_TRANSFERS WHERE Added_To_Rep = '1' AND Item_Code = :itemCode AND Voucher_No = :transNo")
    List<ItemSerialTransfer> getAllAdded(String itemCode, String transNo);

    @Query("DELETE FROM ITEM_SERIAL_TRANSFERS WHERE Voucher_No = :transNo AND Device_Id = :deviceId AND Item_Code = :itemCode AND Added_To_Rep = '0'")
    void deleteNotAddedForItem(String transNo, String deviceId, String itemCode);

    @Query("SELECT Serial_No FROM ITEM_SERIAL_TRANSFERS WHERE Voucher_No = :transNo AND Item_Code = :itemCode AND Added_To_Rep = '1'")
    List<String> getSerialCodes(String transNo, String itemCode);

    @Query("UPDATE ITEM_SERIAL_TRANSFERS SET Posted = '1' WHERE Added_To_Rep = '1' AND Posted = '0'")
    void postSerials();

    @Query("UPDATE ITEM_SERIAL_TRANSFERS SET Posted = '1' WHERE Added_To_Rep = '1' AND Posted = '0' " +
            "AND Item_Code = :itemCode AND Voucher_No = :transNo")
    void postSerialsFor(String itemCode, String transNo);

    @Query("SELECT * FROM ITEM_SERIAL_TRANSFERS WHERE Added_To_Rep = '1' AND Posted = '0'")
    List<ItemSerialTransfer> getUnPosted();
}
