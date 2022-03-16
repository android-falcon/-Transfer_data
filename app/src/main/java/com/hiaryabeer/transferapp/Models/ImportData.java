package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hiaryabeer.transferapp.Activities.Login;
import com.hiaryabeer.transferapp.Activities.MainActivity;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.Store;
import com.hiaryabeer.transferapp.ZoneModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.Activities.Login.getListCom;
import static com.hiaryabeer.transferapp.Activities.MainActivity.itemcode;
import static com.hiaryabeer.transferapp.Activities.MainActivity.itemrespons;
import static com.hiaryabeer.transferapp.Activities.MainActivity.zone;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;

//zamel: 92.253.93.250:80 / 295
public class ImportData {
    public static ArrayList<ZoneModel> listAllZone = new ArrayList<>();
    public static int posize;
    public static String itemn;
    public static String item_name = "";
    public static String poqty;
    private Context context;
    public String ipAddress = "", CONO = "", headerDll = "", link = "", portIp = "";
    public RoomAllData my_dataBase;
    public static String zonetype;
    public static List<Store> Storelist = new ArrayList<>();
    public static ArrayList<String> BoxNolist = new ArrayList<>();
    public static ArrayList<String> PoNolist = new ArrayList<>();
    public static List<AllItems> AllImportItemlist = new ArrayList<>();
    public static List<ZoneModel> listQtyZone = new ArrayList<>();
    public static ArrayList<CompanyInfo> companyInList = new ArrayList<>();
    public static String barcode = "";
    public static SweetAlertDialog pdRepla, pdRepla2;
    public JSONArray jsonArrayPo;
    public JSONObject stringNoObject;


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
        try {
            getIpAddress();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.fillIpAndComNo), Toast.LENGTH_LONG).show();
        }

//        headerDll = "/Falcons/VAN.Dll/";
    }

    public void getAllItems() {
        AllImportItemlist.clear();
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla.setTitleText(context.getString(R.string.importData));
        pdRepla.setCancelable(false);
        pdRepla.show();
        if (!ipAddress.equals(""))
            new JSONTask_getAllItems().execute();
        else
            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
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
        ipAddress = my_dataBase.settingDao().getIpAddress().trim();
        CONO = my_dataBase.settingDao().getCono().trim();
        portIp = my_dataBase.settingDao().getPort().trim();
        ipAddress = ipAddress + ":" + portIp;
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

    public void getStore() {
        pdRepla2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla2.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla2.setTitleText(context.getString(R.string.getStore));
        pdRepla2.setCancelable(false);
        pdRepla2.show();
        if (!ipAddress.equals(""))
            new JSONTask_getAllStoreData().execute();
        else
            Toast.makeText(context, context.getString(R.string.fillIp), Toast.LENGTH_SHORT).show();
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
                    http:
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
                                allItems.setItemName(jsonObject1.getString("ItemNameA"));
                                allItems.setBarCode(jsonObject1.getString("ItemNCode"));
                                if (Login.serialsActive == 1)
                                    allItems.setHasSerial(jsonObject1.getString("ITEMHASSERIAL"));
                                else
                                    allItems.setHasSerial("");

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
        dialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
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


}
