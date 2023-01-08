package com.hiaryabeer.transferapp.Interfaces;


import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.ItemSwitch;
import com.hiaryabeer.transferapp.Models.ItemsUnit;
import com.hiaryabeer.transferapp.Models.Switch;
import com.hiaryabeer.transferapp.Store;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


 @GET("Getsore")
 retrofit2.Call<List<Store>> gatStorsDetail(@Query("CONO") String ComNo);

 @GET("IrGetAllItems")
 Call<List<AllItems>> gatItemInfoDetail(@Query("CONO") String ComNo);
 @GET("GetJRDItemSwich")
 Call<List<ItemSwitch>> gatItemSwitchDetail(@Query("FROM_DATE") String FROM_DATE,@Query("TO_DATE") String TO_DATE  , @Query("CONO") String ComNo);
 @GET("GetJRDItemSwich")
 Call<List<Switch>> gatItemSwitchDetail2(@Query("FROM_DATE") String FROM_DATE, @Query("TO_DATE") String TO_DATE  , @Query("CONO") String ComNo);
 @GET("EXPORTTRANS")
 Call<String> EXPORTTRANS(@Query("CONO") String ComNo);


 @GET("GetJrdItemUnit")
 Call<List<ItemsUnit>> GetJrdItemUnit(@Query("CONO") String ComNo,@Query("FROM_DATE") String FROM_DATE, @Query("TO_DATE") String TO_DATE  );

}
