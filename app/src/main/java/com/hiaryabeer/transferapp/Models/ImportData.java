package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hiaryabeer.transferapp.Activities.Login;
import com.hiaryabeer.transferapp.Activities.MainActivity;
import com.hiaryabeer.transferapp.Interfaces.ApiService;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.Store;
import com.hiaryabeer.transferapp.ZoneModel;
import com.hiaryabeer.transferapp.appSettings;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


import static android.content.Context.TELECOM_SERVICE;
import static com.hiaryabeer.transferapp.Activities.Login.getListCom;
import static com.hiaryabeer.transferapp.Activities.MainActivity.iraqswitch;
import static com.hiaryabeer.transferapp.Activities.MainActivity.itemcode;
import static com.hiaryabeer.transferapp.Activities.MainActivity.itemrespons;
import static com.hiaryabeer.transferapp.Activities.MainActivity.zone;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;

//zamel: 92.253.93.250:80 / 295
public class ImportData {
    public static ArrayList<ZoneModel> listAllZone = new ArrayList<>();
    public static ArrayList<ItemSwitch> listAllItemSwitch = new ArrayList<>();
    public static ArrayList<ItemsUnit> listAllItemsUnit = new ArrayList<>();
    public static int posize;
    public static String itemn;
    public static String item_name = "";
    public static String poqty;
    private Context context;
    public String ipAddress = "", CONO = "", headerDll = "", link = "", portIp = "";
    public RoomAllData my_dataBase;
    public static String zonetype;
    public static List<Store> Storelist = new ArrayList<>();
    public static List<ReplacementModel> voucherlist = new ArrayList<>();

    public static ArrayList<String> BoxNolist = new ArrayList<>();
    public static ArrayList<String> PoNolist = new ArrayList<>();
    public static List<AllItems> AllImportItemlist = new ArrayList<>();
    public static List<ZoneModel> listQtyZone = new ArrayList<>();
    public static ArrayList<CompanyInfo> companyInList = new ArrayList<>();
    public static String barcode = "";
    public static SweetAlertDialog pdRepla, pdRepla2,pdRepla3,pdRepla4,pdRepla5, pDialog3;
    public JSONArray jsonArrayPo;
    public JSONObject stringNoObject;
    public  ApiService myAPI;
    private String TAG;

    /******** Bara' *********/

    public interface GetItemQtyCallBack {
        void onResponse(String qty);

        void onError(String error);
    }

    public void getItemQty(String itemCode, String storeNo, GetItemQtyCallBack getItemQtyCallBack) {
//        RequestQueue queue = RequestQueueSingleton.getInstance(context.getApplicationContext()).
//                getRequestQueue();

        String myQty = "";

        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/GetItemQtyInStore?CONO=" + CONO.trim() + "&strno=" + storeNo + "&ITEMCODE=" + itemCode;
        Log.e(" url", url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);
                    getItemQtyCallBack.onResponse(object.getString("QTY"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getItemQtyCallBack.onError(error.getMessage());
            }
        });

        arrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(arrayRequest);
    }

    /*************/

    public ImportData(Context context) {
        this.context = context;
        my_dataBase = RoomAllData.getInstanceDataBase(context);
//headerDll = "/Falcons/VAN.Dll/";
try {


    getIpAddress();
    link = "http://" + ipAddress.trim() + headerDll.trim();
    //link = "http://" +"10.0.0.22:8085" + headerDll.trim();
    Log.e("Link====", "" + link.toString());
    Retrofit retrofit = RetrofitInstance.getInstance(link);
    Log.e("retrofit====", "" + retrofit.toString());
    myAPI = retrofit.create(ApiService.class);
}catch (Exception e){

}
    }

//    public void getAllItems() {
//        AllImportItemlist.clear();
//        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
//        pdRepla.setTitleText(context.getString(R.string.importData));
//        pdRepla.setCancelable(false);
//        pdRepla.show();
//        if (!ipAddress.equals(""))
//            new JSONTask_getAllItems().execute();
//        else
//            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
//    }
public void getAllItems() {
    Log.e("importgetAllItems", "" + "getAllItems");
            AllImportItemlist.clear();
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla.setTitleText(context.getString(R.string.importData));
        pdRepla.setCancelable(false);
        pdRepla.show();
    fetchItemDetailData();

    }
    public void getAllZones() {
        if (!ipAddress.equals("")) {
            new JSONTask_getAllZoneCode().execute();
        } else {
            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
        }

    }

    private void getIpAddress() {
//        headerDll="";
      List<appSettings>  appSettings = my_dataBase.settingDao().getallsetting();
        if (appSettings.size() != 0) {
                ipAddress = my_dataBase.settingDao().getIpAddress().trim();
            CONO = my_dataBase.settingDao().getCono().trim();
            portIp = my_dataBase.settingDao().getPort().trim();
            ipAddress = ipAddress + ":" + portIp;
        }
//        Log.e("getIpAddress","1"+ipAddress+"port="+portIp);


    }

    public void getCompanyInfo() {
        if (!ipAddress.equals("")) {
            try {
                new JSONTask_getCompanyInfo().execute();
            } catch (Exception e) {
            }

        } else {

            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
        }
    }

    public void getQty() {
        listQtyZone.clear();
        new JSONTask_getQTYOFZone().execute();

    }

//    public void getStore() {
//        pdRepla2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//        pdRepla2.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
//        pdRepla2.setTitleText(context.getString(R.string.getStore));
//        pdRepla2.setCancelable(false);
//        pdRepla2.show();
//        if (!ipAddress.equals(""))
//            new JSONTask_getAllStoreData().execute();
//        else
//            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
//    }
public void getStore() {
    Log.e("importgetStore", "" + "getgetStore");
        pdRepla2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla2.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla2.setTitleText(context.getString(R.string.getStore));
        pdRepla2.setCancelable(false);
        pdRepla2.show();
    fetchStoreData();



    }
    public List<ReplacementModel>  getVouchers() {
        List<ReplacementModel> alldata = new ArrayList<>();
        alldata= fetchVoucherData();
        Log.e("getVouchers","3"+alldata.size());
        return  alldata;
    }
    public void getUnitData(String from,String to) {
        listAllItemsUnit.clear();

        Log.e("importgetUnitData", "" + "getUnitData");
        pdRepla5 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla5.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla5.setTitleText(context.getString(R.string.getitemunit));
        pdRepla5.setCancelable(false);
        pdRepla5.show();
        fetchItemsUnitData(from,to);



    }

    public void getIraqItemSwitch(String from,String to) {
        Log.e("importgetItemSwitch", "" + "getItemSwitch");
        pdRepla4 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla4.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla4.setTitleText("Get ItemSwitch");
        pdRepla4.setCancelable(false);
        pdRepla4.show();
        listAllItemSwitch.clear();
        fetchItemSwitchData( from, to);
                  }
//    public void getIraqItemSwitch(String from,String to) {
//
//        fetchItemSwitchData2( from, to);
//                  }





//    public void getIraqItemSwitch(String from,String to) {
//        pdRepla4 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//        pdRepla4.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
//        pdRepla4.setTitleText("Get ItemSwitch");
//        pdRepla4.setCancelable(false);
//        pdRepla4.show();
//        listAllItemSwitch.clear();
//        if (!ipAddress.equals(""))
//            new JSONTaskIraqGetItemSwitch(from,to).execute();
//        else
//            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
//    }
    public void getItemSwitch() {
        Log.e("importgetItemSwitch", "" + "getItemSwitch");
        pdRepla3 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla3.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla3.setTitleText("Get ItemSwitch");
        pdRepla3.setCancelable(false);
        pdRepla3.show();
        if (!ipAddress.equals(""))
            new JSONTaskGetItemSwitch().execute();
        else
            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
    }

    private class JSONTaskGetItemSwitch extends AsyncTask<String, String,  List<ItemSwitch> > {



        @Override
        protected void onPreExecute() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.onPreExecute();

        }

        @Override
        protected List<ItemSwitch>  doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {

                try {

                    //+custId



                    link = "http://" + ipAddress.trim() +headerDll.trim() + "/GetVanAllData?STRNO=" + "1" + "&CONO=" + CONO;

//                        } else {
//                            URL_TO_HIT = "http://" + ipAddress.trim() + ":" + ipWithPort.trim() + headerDll.trim() + "/GetVanAllData?STRNO=" + SalesManLogin + "&CONO=" + CONO;
//
//                        }

                    Log.e("URL_TO_HIT", "" + link);

                } catch (Exception e) {

                }



                URL url = new URL(link);

                //*************************************

                String JsonResponse = null;
                StringBuffer sb = new StringBuffer("");
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));


                HttpResponse response = null;

                try {
                    response = client.execute(request);
                } catch (Exception e) {
                    // Log.e("response",""+response.toString());
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            pdRepla3.dismiss();

                        }
                    });
                }


                try {
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));


                    String line = "";
                    // Log.e("finalJson***Import", sb.toString());

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }

                    in.close();
                } catch (Exception e) {
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            pdRepla3.dismiss();

                        }
                    });
                }


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                JSONObject parentObject = new JSONObject(finalJson);




                try {
                    //   my_dataBase.itemSwitchDao().dELETEAll();
                    listAllItemSwitch.clear();
                    JSONArray parentArrayItem_Switch = parentObject.getJSONArray("item_swich");

                    for (int i = 0; i < parentArrayItem_Switch.length(); i++) {
                        JSONObject finalObject = parentArrayItem_Switch.getJSONObject(i);

                        ItemSwitch item = new ItemSwitch();
                        item.setItemNameA(finalObject.getString("ITEMNAMEA"));
                        item.setItemOCode(finalObject.getString("ITEMOCODE"));
                        item.setItemNCode(finalObject.getString("ITEMNCODE"));

                        listAllItemSwitch.add(item);
                    }
                } catch (JSONException e) {
                    Log.e("Import Data", e.getMessage().toString());
                }





                Log.e("listAllItemSwitch",""+listAllItemSwitch.size());
            } catch (MalformedURLException e) {
                pdRepla3.dismiss();
                e.printStackTrace();
            } catch (IOException e) {

                pdRepla3.dismiss();

                e.printStackTrace();

            } catch (JSONException e) {

                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } finally {

                pdRepla3.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        if (listAllItemSwitch.size() == 0) {
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("check Connection")
                                    .show();
                        }


                    }
                });

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    pdRepla3.dismiss();
                }
            }
            return listAllItemSwitch;
        }


        @Override
        protected void onPostExecute(final List<ItemSwitch> result) {
            super.onPostExecute(result);
            pdRepla3.dismiss();

            if (result != null && result.size() != 0) {
                Log.e("Customerr", "*****************" + listAllItemSwitch.size());

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        my_dataBase.itemSwitchDao().insertAll(result);

                        showSweetDialog(context, 1, context.getString(R.string.app_done), "");



                    }
                });
            } else {

            }
        }
    }

    private class JSONTask_getQTYOFZone extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetZoneDatInfo?CONO=" + CONO.trim() + "&ZONENO=" + zone.getText().toString().trim() + "&ITEMCODE=" + itemcode.getText().toString().trim();


                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                // JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);
            String d = "";
            JSONObject jsonObject1 = null;

            if (array != null) {
                if (array.contains("QTY")) {

                    if (array.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);

                            for (int i = 0; i < requestArray.length(); i++) {

                                ZoneModel zoneModel = new ZoneModel();
                                jsonObject1 = requestArray.getJSONObject(i);
                                zoneModel.setZoneCode(jsonObject1.getString("ZONENO"));
                                zoneModel.setItemCode(jsonObject1.getString("ITEMCODE"));
                                zoneModel.setQty(jsonObject1.getString("QTY"));
                                d = jsonObject1.getString("QTY");
                                listQtyZone.add(zoneModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    MainActivity.qty.setText(d);
                    MainActivity.qtyrespons.setText("QTY");


                } else {

                    MainActivity.qtyrespons.setText("nodata");


                }

            } else {

                MainActivity.qtyrespons.setText("nodata");

            }
        }

    }

    public class JSONTask_getCompanyInfo extends AsyncTask<String, String, String> {

        private String itemNo = "", JsonResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetCoYear

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetCoYear";
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", "Company" + finalJson);


                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                // {
                //    "CoNo": "200",
                //    "CoYear": "2021",
                //    "CoNameA": "Al Rayyan Plastic Factory 2017"
                //  },

                if (result.contains("CoNo")) {
                    try {
                        CompanyInfo requestDetail = new CompanyInfo();
                        JSONArray requestArray = null;
                        requestArray = new JSONArray(result);
                        companyInList = new ArrayList<>();


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new CompanyInfo();
                            requestDetail.setCoNo(infoDetail.get("CoNo").toString());
                            requestDetail.setCoYear(infoDetail.get("CoYear").toString());
                            requestDetail.setCoNameA(infoDetail.get("CoNameA").toString());

                            companyInList.add(requestDetail);
                        }
                        if (companyInList.size() != 0) {
                            getListCom.setText("fill");
                        }


//                            itemKintText.setText(requestDetail.getZONETYPE());


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }


            } else {

                //   itemKintText1.setText("NOTEXIST");
            }


        }
    }

    private class JSONTask_getAllStoreData extends AsyncTask<String, String, String> {


        Store store;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {

//http://10.0.0.22:8082/Getsore?CONO=304

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/Getsore?CONO=" + CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());
                pdRepla2.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdRepla2.dismiss();
                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                pdRepla2.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {
                            Toast.makeText(context, "The target server failed to respond", Toast.LENGTH_SHORT).show();
                        } catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }
                    }
                });
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);

            JSONObject jsonObject1 = null;
            if (array != null) {
                if (array.contains("STORENO")) {

                    if (array.length() != 0) {
                        try {
                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);
                            Storelist.clear();

                            for (int i = 0; i < requestArray.length(); i++) {
                                store = new Store();
                                jsonObject1 = requestArray.getJSONObject(i);
                                store.setSTORENO(jsonObject1.getString("STORENO"));
                                store.setSTORENAME(jsonObject1.getString("STORENAME"));

                                Storelist.add(store);
                            }
                            MainActivity.respon.setText("fill");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }
            } else {

                MainActivity.respon.setText("nodata");


            }
        }


    }
    private class JSONTask_savetrans extends AsyncTask<String, String, String> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {



                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS?CONO=" + CONO.trim();

                    Log.e("link===", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());
                pdRepla2.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdRepla2.dismiss();
                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                pdRepla2.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {
                            Toast.makeText(context, "The target server failed to respond", Toast.LENGTH_SHORT).show();
                        } catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }
                    }
                });
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);

            JSONObject jsonObject1 = null;
            if (array != null) {
                Log.e("array===",array+"");
                if (array.contains("Successfully")) {

                 Log.e("ssaved","ssaved");

                }else {

                    Log.e("not ssaved1","not ssaved1");


                }
            } else {

                Log.e("not ssaved","not ssaved");


            }
        }


    }
    private class JSONTaskIraqGetItemSwitch extends AsyncTask<String, String, String> {
        String fromdate,todate;

        public JSONTaskIraqGetItemSwitch(String fromdate, String todate) {
            this.fromdate = fromdate;
            this.todate = todate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {

                    http://10.0.0.22:8085/GetJRDITEMS?FROM_DATE=01%2F12%2F2021&TO_DATE=27%2F06%2F2022&CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetJRDItemSwich?FROM_DATE="+fromdate+"&TO_DATE="+todate+"&CONO="+ CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());
                pdRepla4.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdRepla4.dismiss();
                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                pdRepla4.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {

                            Toast.makeText(context, "The target server failed to respond", Toast.LENGTH_SHORT).show();
                        } catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }
                    }
                });
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);

            JSONObject jsonObject1 = null;
            if (array != null) {
                Log.e("array====",array+"");

                if (array.contains("ItemOCode")) {

                    if (array.length() != 0) {
                        try {
                            Log.e("here====","here");
                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);
                            listAllItemSwitch.clear();

                            for (int i = 0; i < requestArray.length(); i++) {
                                Log.e("here2====","here");
                                jsonObject1 = requestArray.getJSONObject(i);
                                ItemSwitch item = new ItemSwitch();
                                item.setItemNameA(jsonObject1.getString("ItemNameA"));
                                item.setItemOCode(jsonObject1.getString("ItemOCode"));
                                item.setItemNCode(jsonObject1.getString("ItemNCode"));
                                Log.e("item====","item"+item.getItemNCode());
                                listAllItemSwitch.add(item);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }



                }
                pdRepla4.dismissWithAnimation();
            MainActivity. iraqswitch.setText("ItemOCode");
            } else {

             MainActivity. iraqswitch.setText("else");
                pdRepla4.dismissWithAnimation();

            }
        }


    }



    private class JSONTask_getAllZoneCode extends AsyncTask<String, String, JSONArray> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetAllZone?CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllZone?CONO=" + CONO.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                JSONArray parentObject = new JSONArray(finalJson);

                return parentObject;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);

            JSONObject result = null;


            if (array != null) {
                if (array.length() != 0) {


                    for (int i = 0; i < array.length(); i++) {
                        try {
                            result = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ZoneModel itemZone = new ZoneModel();
                        try {
                            itemZone.setZoneCode(result.getString("ZONENO"));
                            itemZone.setZONENAME(result.getString("ZONENAME"));
                            itemZone.setZONETYPE(result.getString("ZONETYPE"));

                            listAllZone.add(itemZone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        }
    }

    private class JSONTask_getAllItems extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllItems?CONO=" + CONO.trim();

                    Log.e("link", "" + link);

                }
            } catch (Exception e) {
                Log.e("Exception", "" + e.getMessage());
                pdRepla.dismissWithAnimation();
            }


            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


//                JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "The target server failed to respond", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);
            String d = "";
            JSONObject jsonObject1 = null;

            if (respon != null) {
                if (respon.contains("ItemOCode")) {

                    if (respon.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(respon);

                            for (int i = 0; i < requestArray.length(); i++) {

                                AllItems allItems = new AllItems();
                                jsonObject1 = requestArray.getJSONObject(i);
                                allItems.setItemOcode(jsonObject1.getString("ItemOCode"));
                                allItems.setItemNameA(jsonObject1.getString("ItemNameA"));
                                allItems.setItemNCode(jsonObject1.getString("ItemNCode"));
                                allItems.setItemG(jsonObject1.getString("ItemG"));
                                allItems.setItemK(jsonObject1.getString("ItemK"));
                                if (Login.serialsActive == 1)
                                    allItems.setITEMHASSERIAL(jsonObject1.getString("ITEMHASSERIAL"));
                                else
                                    allItems.setITEMHASSERIAL("");

                                AllImportItemlist.add(allItems);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    itemrespons.setText("ItemOCode");

                    Log.e("itemrespons", itemrespons.getText().toString() + d);

                    Log.e("itemrespons", itemrespons.getText().toString() + d);

                } else {

                    itemrespons.setText("nodata");

                }

            } else {
                itemrespons.setText("nodata");
            }
        }


    }

    /******** Bara' *********/

    public interface GetSerialsCallBack {

        void onResponse(JSONArray response);

        void onError(String error);

    }

    public void getAllSerials(GetSerialsCallBack getSerialsCallBack) {

//        List<SerialsModel> allSerialsList = my_dataBase.serialsDao().getAllSerials();
//        if (allSerialsList.size() == 0) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        dialog.setTitleText(context.getString(R.string.storeSerials));
        dialog.setCancelable(false);
        dialog.show();

        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/GetAllITEMSERIAL?CONO=" + CONO.trim();
        Log.e("Serials url", url);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        dialog.dismissWithAnimation();
                    }
                });
                Log.e("GetSerialsResp", response.toString().substring(0, 100));
                getSerialsCallBack.onResponse(response);

                showSweetDialog(context, 1, context.getString(R.string.app_done), "");

//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        my_dataBase.serialsDao().insert(new SerialsModel(
//                                response.getJSONObject(i).getString("STORENO"),
//                                response.getJSONObject(i).getString("ITEMOCODE"),
//                                response.getJSONObject(i).getString("SERIALCODE")));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        dialog.dismissWithAnimation();
                    }
                });
                getSerialsCallBack.onError(error.getMessage() + "");
                Toast.makeText(context, context.getString(R.string.errorSavingSerials), Toast.LENGTH_SHORT).show();
                Log.e("GetSerialsErr", error.getMessage() + "");
            }
        });
        arrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(arrayRequest);
//        }
    }

    /*************/
    public List<ReplacementModel> fetchVoucherData() {
//        Log.e("myAPI", "fetchVoucherData=" + myAPI+"\tco="+CONO);
        Observable<List<ReplacementModel>> myData = myAPI.gatVoucherApi(CONO);
        List<ReplacementModel> alldata = new ArrayList<>();
        myData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).map(issues -> issues)
                .subscribe(this::handleResults, this::handleError);//get issues and map to issues lis

        return  alldata;
    }
    private void handleResults(List<ReplacementModel> dataList) {
        Log.e("accept","2handleResults="+dataList.size());
        if (dataList != null && dataList.size() != 0) {
            voucherlist.clear();
                    voucherlist.addAll(dataList);
                    Log.e("fetchSto", "voucherlist=" +voucherlist.size());
                    MainActivity.getResponce.setText("fill");




        } else {
            Log.e("fetchSto", "Else+voucherlist=" +dataList.size());
            MainActivity.getResponce.setText("noData");

        }
    }

    private void handleError(Throwable throwable) {
        Log.e("fetchSto", "throwable+voucherlist=" +throwable.getMessage());
        MainActivity.getResponce.setText("noData");
                    if (throwable.getMessage() != null) {
                        if (throwable.getMessage().toString().contains("Failed to connect to"))
                            showSweetDialog(context, 3, "Not Internet Connection", "");
                    } else showSweetDialog(context, 3, "Not Connected To DB", "");
    }
    public void fetchStoreData() {
        Log.e("myAPI", "myAPI=" + myAPI+"\tco="+CONO);
        Call<List<Store>> myData = myAPI.gatStorsDetail(CONO);
        Log.e("fetchStoreData", "myData=" + myData.isExecuted());
        myData.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, retrofit2.Response<List<Store>> response) {
                if (!response.isSuccessful()) {
                    Log.e("fetchStoreDataonResponse", "not=" + response.message());
                    pdRepla2.dismiss();
                } else {
                    Storelist.clear();

                    Storelist.addAll(response.body());
                    MainActivity.respon.setText("fill");
                    pdRepla2.dismiss();
                    Log.e("fetchStoreDataonResponse", "fetchStoreData=" + response.body().size());

                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable throwable) {
                try {
                    Log.e("fetchStoreDataonFailure", "=" + throwable.getMessage());

                    MainActivity.respon.setText("no data");
                    if (throwable.getMessage() != null) {
                        if (throwable.getMessage().toString().contains("Failed to connect to"))
                            showSweetDialog(context, 3, "Not Internet Connection", "");
                    } else showSweetDialog(context, 3, "Not Connected To DB", "");
                    pdRepla2.dismiss();
                    //  Toast.makeText(context, "throwable"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
                catch(Exception exception){

                }
            }

        });
    }
    public void fetchItemDetailData() {
        Log.e("fetchItemDetailData", "fetchItemDetailData" );
        Call<List<AllItems>> myData = myAPI.gatItemInfoDetail(CONO);

        myData.enqueue(new Callback<List<AllItems>>() {
            @Override
            public void onResponse(Call<List<AllItems>> call, retrofit2.Response<List<AllItems>> response) {
                Log.e("fetchItemDetailData=", "onResponse"+ call.request());
                pdRepla.dismiss();
                if (!response.isSuccessful()) {
                    pdRepla.dismiss();
                    itemrespons.setText("nodata");
                    Log.e("fetchItemDetailDataonResponse", "not=" + response.message());
                } else {
                    AllImportItemlist.clear();
                    AllImportItemlist.addAll(response.body());
                    Log.e("OCODE==", "aaaaa"+AllImportItemlist.get(0).getItemOcode());
                    pdRepla.dismiss();
                    Log.e("fetchItemDetailDataonResponse", "fetchItemDetailData=" + response.body().size());
                    itemrespons.setText("ItemOCode");
                }
            }

            @Override
            public void onFailure(Call<List<AllItems>> call, Throwable throwable) {
                Log.e("onFailure", "=" + throwable.getMessage()+call.request());
                itemrespons.setText("nodata");
                pdRepla.dismiss();
                          }
        });
    }
    public void fetchItemsUnitData(String from,String to){
        Call<List<ItemsUnit>> myData    = myAPI. GetJrdItemUnit(CONO,from, to);
        myData.enqueue(new Callback<List<ItemsUnit>>() {
            @Override
            public void onResponse(Call<List<ItemsUnit>> call, retrofit2.Response<List<ItemsUnit>> response) {
                if (!response.isSuccessful()) {

                    Log.e("fetchItemsUnitData", "not=" + response.message()+call.request());

                    pdRepla5.dismiss();


                } else {
                    Log.e("fetchItemsUnitData", "onResponse=" + response.message()+"call="+call.request());

                    listAllItemsUnit.addAll(response.body());
                    showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");
                    my_dataBase.itemsUnitDao().insertAll(listAllItemsUnit);
                    pdRepla5.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<ItemsUnit>> call, Throwable t) {
                Log.e("fetchItemsUnitDataFailure", "=" + t.getMessage()+call.request());
                showSweetDialog(context, 0, context.getResources().getString(R.string.netWorkError), "");



                pdRepla5.dismiss();
            }
        });
    }
    public void fetchItemSwitchData(String from,String to) {

        Call<List<ItemSwitch>> myData = myAPI.gatItemSwitchDetail(from, to,CONO);

        myData.enqueue(new Callback<List<ItemSwitch>>() {

            @Override
            public void onResponse(Call<List<ItemSwitch>> call, retrofit2.Response<List<ItemSwitch>> response) {

                if (!response.isSuccessful()) {

                    Log.e("fetchItemDetailDataonResponse", "not=" + response.message());
                    iraqswitch.setText("else");


                } else {
                    Log.e("fetchItemDetailDataonResponse", "onResponse=" + response.message());

                    listAllItemSwitch.addAll(response.body());

                    iraqswitch.setText("ItemOCode");
                    pdRepla4.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<ItemSwitch>> call, Throwable t) {
                Log.e("fetchItemDetailDataonFailure", "=" + t.getMessage());
                pdRepla4.dismiss();
            }
        });
    }
    public void fetchEXPORTTRANS() {
        Log.e("fetchEXPORTTRANS", "fetchEXPORTTRANS=");


        if (!ipAddress.equals(""))
            new JSONTask_savetrans().execute();
        else
            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();

        Call <String> myData = myAPI.EXPORTTRANS(CONO);
        myData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.e("fetchItemDetailDataonResponse", "not=" + response.message()+"  "+call.request());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fetchItemDetailDataonResponse", "" + t.getMessage()+"  "+call.request());
            }
        });

//        myData.enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
//                Log.e("fetchItemDetailDataonResponse", "not=" + response.message());
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//
//            }
//        });
    }
    public interface GetItemsCallBack {

        void onResponse(JSONObject response);

        void onError(String error);

    }
    public void getAllItems(GetItemsCallBack getItemsCallBack, String ipAddress, String ipPort, String coNo) {


        //http://10.0.0.22:8085/GetVanAllData?STRNO=1&CONO=295
        if (!ipAddress.equals("") || !ipPort.equals("") || !coNo.equals(""))
            link = "http://" + ipAddress + ":" + ipPort + headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNo;
//        else
//            link = "http://" + ipEdt.getText().toString().trim() + ":" +
//                    portEdt.getText().toString().trim() +
//                    headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNoEdt.getText().toString().trim();

        Log.e("getItems_Link", link);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(link, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                getItemsCallBack.onResponse(response);

//                GeneralMethod.showSweetDialog(context, 1, "Items Saved", null);
                Log.e("getItems_Response", response + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getItemsCallBack.onError(error.getMessage() + "");

                if ((error.getMessage() + "").contains("SSLHandshakeException") || (error.getMessage() + "").equals("null")) {

                    GeneralMethod.showSweetDialog(context, 0, null, "Connection to server failed");

                } else if ((error.getMessage() + "").contains("ConnectException")) {

                    GeneralMethod.showSweetDialog(context, 0, "Connection Failed", null);

                } else if ((error.getMessage() + "").contains("NoRouteToHostException")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "Please check the entered IP info");

                } else if ((error.getMessage() + "").contains("No Data Found")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "No Items Found");

                }
                Log.e("getItems_Error", error.getMessage() + "");

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }
    public interface GetUsersCallBack {

        void onResponse(JSONArray response);

        void onError(String error);

    }
    public void getAllUsers(GetUsersCallBack getUsersCallBack) {

        pDialog3 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);

        pDialog3.getProgressHelper().setBarColor(Color.parseColor("#115571"));
        pDialog3.setTitleText("getting Users");
        pDialog3.setCancelable(false);
        pDialog3.show();
        if (!ipAddress.equals("")  || !CONO.equals(""))
            link = "http://" + ipAddress + headerDll.trim() + "/GetUSERS?CONO=" + CONO;

        Log.e("getUsers_Link", link);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                getUsersCallBack.onResponse(response);
                pDialog3.dismissWithAnimation();
                //  GeneralMethod.showSweetDialog(context, 1, "Data Saved", null);
                Log.e("getUsers_Response", response + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getUsersCallBack.onError(error.getMessage() + "");
                pDialog3.dismissWithAnimation();
                if ((error.getMessage() + "").contains("SSLHandshakeException") || (error.getMessage() + "").equals("null")) {

                    GeneralMethod.showSweetDialog(context, 0, null, "Connection to server failed");

                } else if ((error.getMessage() + "").contains("ConnectException")) {

                    GeneralMethod.showSweetDialog(context, 0, "Connection Failed", null);

                } else if ((error.getMessage() + "").contains("NoRouteToHostException")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "Please check the entered IP info");

                } else if ((error.getMessage() + "").contains("No Data Found")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "No Users Found");
                    my_dataBase.usersDao().insertUser(new User("010101", "admin", "2022"));

                }
                Log.e("getUsers_Error", error.getMessage() + "");

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }
}
