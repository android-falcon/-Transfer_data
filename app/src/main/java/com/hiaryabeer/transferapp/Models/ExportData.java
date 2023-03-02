package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.hiaryabeer.transferapp.appSettings;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hiaryabeer.transferapp.Activities.Login;
import com.hiaryabeer.transferapp.Activities.MainActivity;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.RoomAllData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.TELECOM_SERVICE;
import static com.hiaryabeer.transferapp.Activities.MainActivity.New_replacementlist;
import static com.hiaryabeer.transferapp.Activities.MainActivity.New_saverespone;
import static com.hiaryabeer.transferapp.Activities.MainActivity.exportAllState;
import static com.hiaryabeer.transferapp.Activities.MainActivity.replacementlist;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;

public class ExportData {
    private Context context;
    public String ipAddress = "", CONO = "", headerDll = "", link = "", PONO = "", portIp = "";
    public RoomAllData my_dataBase;
    public static SweetAlertDialog pdVoucher, pdshipmant, pdRepla, pdstock, pdzone;
    JSONObject vouchersObject;
    JSONObject ShipmentObject;
    JSONObject ReplacmentObject, serialsObject, StockObject, ZoneRepObject;
    private JSONArray jsonArrayShipment;
    private JSONArray jsonArrayReplacement, jsonArraySerials, jsonArrayStock, jsonArrayZoneRep;
    private JSONArray jsonArrayVouchers;
    ImportData importData;
    List<SerialsModel> allItemSerials = new ArrayList<>();
    SweetAlertDialog savingDialog3;
    public ArrayList<ReplacementModel> listAllReplacment = new ArrayList<>();
    int typeExportZone = 0;
    int typeExportShipment = 0;
    int typeExportReplacement = 0;
    boolean reExport = false;
    Set<String> setVoucher=new HashSet<>();
    List<appSettings>   appSettings=new ArrayList<>();
    public ExportData(Context context) {
        this.context = context;
        my_dataBase = RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.fillIpAndComNo), Toast.LENGTH_SHORT).show();
        }
//headerDll = "/Falcons/VAN.Dll/";


      appSettings=my_dataBase.settingDao().getallsetting();
    }

    private void getIpAddress() {
//
        ipAddress = my_dataBase.settingDao().getIpAddress().trim();
        CONO = my_dataBase.settingDao().getCono().trim();
        portIp = my_dataBase.settingDao().getPort().trim();
        ipAddress = ipAddress + ":" + portIp;
//        Log.e("getIpAddress", "1" + ipAddress + "port=" + portIp);
//        Log.e("getIpAddress",""+ipAddress);

    }

    public void exportReplacementList(List<ReplacementModel> replacementlist) {
        getReplacmentObject(replacementlist);
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla.setTitleText(context.getString(R.string.exportRep));
        pdRepla.setCancelable(false);
        pdRepla.show();

//        this.reExport = reExport;

        new JSONTask_AddReplacment(replacementlist).execute();
    }
    public void NEW_exportReplacementList(List<ReplacementModel> replacementlist) {
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla.setTitleText(context.getString(R.string.exportRep));
        pdRepla.setCancelable(false);
        pdRepla.show();
        try {
            for (int i=0;i<replacementlist.size();i++)
            {
                Log.e("replacementlist45==",replacementlist.get(i).getRMQTY()+"   "+replacementlist.get(i).getSer_RCVQTY()+"   "+replacementlist.get(i).getUpdatedQty()+"  "+replacementlist.get(i).getRecQty());
//                if(replacementlist.get(i).getRMQTY().equals("0")&&replacementlist.get(i).getUpdatedQty().equals("0"))
//                    replacementlist.get(i).setISDONE("0");
//                else
                {
                    if (Double.parseDouble(replacementlist.get(i).getRMQTY())==0 &&
                            Double.parseDouble(replacementlist.get(i).getSer_RCVQTY())==0 &&
                            ( Double.parseDouble(replacementlist.get(i).getUpdatedQty())==Double.parseDouble(replacementlist.get(i).getRecQty())))
                    {
                        Log.e("v1","v1");
                        replacementlist.get(i).setISDONE("1");

                    }else if(Double.parseDouble(replacementlist.get(i).getRMQTY())==Double.parseDouble(replacementlist.get(i).getUpdatedQty()))
                    {
                        Log.e("v2","v2");
                        replacementlist.get(i).setISDONE("1");
                    }
                    else
                    {     Log.e("v3","v3");
                        replacementlist.get(i).setISDONE("0");
                    }
//                    if(Double.parseDouble(replacementlist.get(i).getRMQTY())==Double.parseDouble(replacementlist.get(i).getUpdatedQty())||
//                            (Double.parseDouble(replacementlist.get(i).getRMQTY())==0
//                                    &&Double.parseDouble(replacementlist.get(i).getSer_RCVQTY())==
//                                    Double.parseDouble(replacementlist.get(i).getUpdatedQty()) )   )
//                        replacementlist.get(i).setISDONE("1");
//                    else
//                        replacementlist.get(i).setISDONE("0");

                }}
        }catch (Exception exception){

        }
        NEW_getReplacmentObject(replacementlist);



        new JSONTask_UPdateReplacment(replacementlist).execute();
    }

    public void exportTrans(int flage) {


       if( appSettings.size()!=0)
           if( appSettings.get(0).getInternal_replanshment().equals("0")) {
Log.e("cas1,==","1");
                   savingDialog3 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                   savingDialog3.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
                   savingDialog3.setTitleText(context.getString(R.string.saving));
                   savingDialog3.setCancelable(false);
                   savingDialog3.show();
               //new JSONTask_savetrans().execute();

               setVoucher.clear();
               for (int i = 0; i < MainActivity.Allreplacementlist1.size(); i++)
                   setVoucher.add(MainActivity.Allreplacementlist1.get(i).getTransNumber());



               Iterator<String> it = setVoucher.iterator();
               while(it.hasNext()) {
                   String value = it.next();
                   System.out.println(value);
                   Log.e("ithasNext?=",it.hasNext()+"");
                   if(it.hasNext())
                       new JSONTask_savetrans2(1,value).execute();
                  else new JSONTask_savetrans2(0,value).execute();
               }

               }
               else {
                   if(flage ==0)
                   {

                       Log.e("cas2,==","2");
                   }
                   else if(flage ==1)
                   {Log.e("cas3,==","3");
                       savingDialog3 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                       savingDialog3.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
                       savingDialog3.setTitleText(context.getString(R.string.saving3));
                       savingDialog3.setCancelable(false);
                       savingDialog3.show();

                       new JSONTask_savetrans().execute();
                   }

           }

    }

    private void NEW_getReplacmentObject(List<ReplacementModel> replacementlist) {
        jsonArrayReplacement = new JSONArray();
        for (int i = 0; i < replacementlist.size(); i++) {

            jsonArrayReplacement.put(replacementlist.get(i).New_getJSONObjectDelphi(context));

        }
        try {
            ReplacmentObject = new JSONObject();
            ReplacmentObject.put("JSN", jsonArrayReplacement);
//            Log.e("vouchersObject",""+ReplacmentObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getReplacmentObject(List<ReplacementModel> replacementlist) {
        jsonArrayReplacement = new JSONArray();
         Log.e("SIZE===",""+replacementlist.size()+"");
        for (int i = 0; i < replacementlist.size(); i++) {

            jsonArrayReplacement.put(replacementlist.get(i).getJSONObjectDelphi());

        }
        try {
            ReplacmentObject = new JSONObject();
            ReplacmentObject.put("JSN", jsonArrayReplacement);
//            Log.e("vouchersObject",""+ReplacmentObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSerialsObject(List<ItemSerialTransfer> serialsList) {
        jsonArraySerials = new JSONArray();
        for (int i = 0; i < serialsList.size(); i++) {

            jsonArraySerials.put(serialsList.get(i).getJSONObjectSerials());

        }
        try {
            serialsObject = new JSONObject();
            serialsObject.put("JSN", jsonArraySerials);
//            Log.e("vouchersObject",""+ReplacmentObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class JSONTask_AddReplacment extends AsyncTask<String, String, String> {
        private String JsonResponse = null;

        List<ReplacementModel> replacementList;


        public JSONTask_AddReplacment(List<ReplacementModel> replacementList) {
            this.replacementList = replacementList;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {


            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    http:
//localhost:8082/IrTransFer?CONO=290&JSONSTR={"JSN":[{"ITEMCODE":"4032900116167","FROMSTR":"1","TOSTR":"2","QTY":"10","ZONE":"50"},{"ITEMCODE":"7614900001130","FROMSTR":"1","TOSTR":"2","QTY":"30","ZONE":"51"}]}
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrTransFer";


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                pdRepla.dismissWithAnimation();

            }

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentObject.toString().trim()));
                Log.e("JSONSTR", ReplacmentObject.toString());
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("JsonResponse", "Export Replacement" + JsonResponse);


            } catch (Exception e) {
            }
            return JsonResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
           Log.e("JSONTaskAddReplacment", "" + result);
            pdRepla.dismissWithAnimation();

            if (result != null && !result.equals("")) {
                Log.e("IrTransFer,result===", result+"");
                if (result.contains("Internal server error")) {
                    exportAllState.setText("server error");

                    if( appSettings.get(0).getInternal_replanshment().equals("1"))
                        showSweetDialog(context, 0, context.getResources().getString(R.string.serverError), "");

                    exportTrans(0);
                } else if (result.contains("unique constraint")) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String res = jsonObject.getString("ErrorDesc");
                        exportAllState.setText("unique constraint" + res);
                        if( appSettings.get(0).getInternal_replanshment().equals("1"))
                            showSweetDialog(context, 0, context.getResources().getString(R.string.serverError), "");
                        exportTrans(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (result.contains("table or view does not exist")) {
                    exportAllState.setText("server error");
                    if( appSettings.size()!=0)
                        if( appSettings.get(0).getInternal_replanshment().equals("1"))
                            showSweetDialog(context, 0, context.getResources().getString(R.string.serverError), "");
                    exportTrans(0);
                } else {
                    if (result.contains("Saved Successfully")) {
                        //  poststateRE.setText("exported");

//                        exportAllState.setText("exported");
                        for (int i = 0; i < replacementList.size(); i++) {
                            my_dataBase.replacementDao().postFor(
                                    replacementList.get(i).getTransNumber(),
                                    replacementList.get(i).getItemcode());
                        }

//                        my_dataBase.replacementDao().updateReplashmentPosted();

                        if( appSettings.size()!=0)
                            if( appSettings.get(0).getInternal_replanshment().equals("1"))
                                showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");
                        exportAllState.setText("exported");


                        if (Login.serialsActive == 1) {

                            SweetAlertDialog savingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                            savingDialog.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
                            savingDialog.setTitleText(context.getString(R.string.savingSerials));
                            savingDialog.setCancelable(false);
                            savingDialog.show();
                            JSONTask_ExportSerials(savingDialog, replacementList);

                        } else {

//                            SweetAlertDialog savingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//                            savingDialog.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
//                            savingDialog.setTitleText(context.getString(R.string.saving));
//                            savingDialog.setCancelable(false);
//                            savingDialog.show();
                         //   JSONTask_ExportTrans(savingDialog);

                            exportTrans(0);

                        }


                    } else {

                        Log.e("aaaaaaa1===", "aaaaaaa");
                        exportAllState.setText("err");

                        if( appSettings.size()!=0)
                            if( appSettings.get(0).getInternal_replanshment().equals("1"))
                                showSweetDialog(context, 0, context.getResources().getString(R.string.checkConnection), "");
                        exportTrans(0);
                    }
                }


            } else {

                Log.e("aaaaaaa2===", "aaaaaaa");
//                Log.e("aaaaaaa2===","aaaaaaa");
                exportAllState.setText("not");
                if( appSettings.get(0).getInternal_replanshment().equals("1"))
                    showSweetDialog(context, 0, context.getResources().getString(R.string.checkConnection), "");
                exportTrans(0);
            }


        }

    }
    public class JSONTask_UPdateReplacment extends AsyncTask<String, String, String> {
        private String JsonResponse = null;

        List<ReplacementModel> replacementList1;

        public JSONTask_UPdateReplacment(List<ReplacementModel> replacementList) {
            this.replacementList1 = replacementList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {


            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    http:

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/UpdateTransfer";


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                pdRepla.dismissWithAnimation();

            }

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentObject.toString().trim()));

                Log.e("JSONSTR", ReplacmentObject.toString());
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("JsonResponse", "Export Replacement" + JsonResponse);


            } catch (Exception e) {
            }
            return JsonResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);

            Log.e("JSONTaskAddReplacment", "" + result);
            pdRepla.dismissWithAnimation();
            if (result != null && !result.equals("")) {
                Log.e("IrTransFer,result===", result+"");
                if (result.contains("Internal server error")) {
                    New_saverespone.setText("Internal server error");
                    showSweetDialog(context, 0, "Internal server error", "");
                } else if (result.contains("unique constraint")) {
                    New_saverespone.setText("unique constraint");
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String res = jsonObject.getString("ErrorDesc");
                        showSweetDialog(context, 0, "unique constraint", "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (result.contains("table or view does not exist")) {
                    New_saverespone.setText("table or view does not exist");
                    showSweetDialog(context, 0, "table or view does not exist", "");
                } else {
                    if (result.contains("Saved Successfully")) {
                        Log.e("checkallqty33==","here");

                        exportTrans(1);
                    //    new JSONTask_gettotrans().execute();

//                     if(checkallqty(New_replacementlist))
//                     {
////                         if( MainActivity.counter>0 )
////                             showSweetDialog(context, 1, context.getString(R.string.savedSuccsesfule), "");
////                             else
////                     exportTrans(1);
//
//                     }

//                     else
//                         showSweetDialog(context, 1, context.getString(R.string.savedSuccsesfule), "");

                        New_saverespone.setText("saved");

                    } else {
                        showSweetDialog(context, 0, context.getString(R.string.serverError), "");
                        New_saverespone.setText("not");
                    }
                }


            } else {
                New_saverespone.setText("not");
                showSweetDialog(context, 0, context.getString(R.string.checkConnection), "");

            }


        }

    }
//    public class JSONTask_SetTransDone extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//
//
//String VHFNO,ITEMCODE,FROMSTR,TOSTR;
//        List<ReplacementModel> replacementList1;
//
//        public JSONTask_SetTransDone(String VHFNO, String ITEMCODE, String FROMSTR, String TOSTR) {
//            this.VHFNO = VHFNO;
//            this.ITEMCODE = ITEMCODE;
//            this.FROMSTR = FROMSTR;
//            this.TOSTR = TOSTR;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//
//            URLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                if (!ipAddress.equals("")) {
//
//                    http:
//
//                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/settransdone?CONO"+CONO+"&VHFNO"+VHFNO+"&ITEMCODE="+ITEMCODE+"&FROMSTR"+FROMSTR+"&TOSTR="+TOSTR;
//
//
//                    Log.e("URL_TO_HIT", "" + link);
//                }
//            } catch (Exception e) {
//                //progressDialog.dismiss();
//                pdRepla.dismissWithAnimation();
//
//            }
//
//            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpPost request = new HttpPost();
//                try {
//                    request.setURI(new URI(link));
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//
//
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
//                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentObject.toString().trim()));
//
//                Log.e("JSONSTR", ReplacmentObject.toString());
//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//                HttpResponse response = client.execute(request);
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                in.close();
//
//
//                JsonResponse = sb.toString();
//                Log.e("JsonResponse", "Export Replacement" + JsonResponse);
//
//
//            } catch (Exception e) {
//            }
//            return JsonResponse;
//        }
//
//        @Override
//        protected void onPostExecute(final String result) {
//            super.onPostExecute(result);
//
//            Log.e("JSONTaskAddReplacment", "" + result);
//            pdRepla.dismissWithAnimation();
//            if (result != null && !result.equals("")) {
//                Log.e("IrTransFer,result===", result+"");
//                if (result.contains("Internal server error")) {
//
//                } else if (result.contains("unique constraint")) {
//
//
//                } else if (result.contains("table or view does not exist")) {
//
//               //     showSweetDialog(context, 0, "table or view does not exist", "");
//                } else {
//                    if (result.contains("Saved Successfully")) {
//
//
//                    } else {
////                        showSweetDialog(context, 0, context.getString(R.string.serverError), "");
//
//                    }
//                }
//
//
//            } else {
//
////                showSweetDialog(context, 0, context.getString(R.string.checkConnection), "");
//
//            }
//
//
//        }
//
//    }
//    public class JSONTask_UPdateReplacment extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//
//        List<ReplacementModel> replacementList;
//
//        public JSONTask_AddReplacment(List<ReplacementModel> replacementList) {
//            this.replacementList = replacementList;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//
//            URLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                if (!ipAddress.equals("")) {
//
//                    http:
//
//                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/UpdateTransfer";
//
//
//                    Log.e("URL_TO_HIT", "" + link);
//                }
//            } catch (Exception e) {
//                //progressDialog.dismiss();
//                pdRepla.dismissWithAnimation();
//
//            }
//
//            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpPost request = new HttpPost();
//                try {
//                    request.setURI(new URI(link));
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//
//
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
//                nameValuePairs.add(new BasicNameValuePair("VHFNO", New_replacementlist.get(0)));
//                nameValuePairs.add(new BasicNameValuePair("ITEMCODE", CONO.trim()));
//                nameValuePairs.add(new BasicNameValuePair("FROMSTR", CONO.trim()));
//                nameValuePairs.add(new BasicNameValuePair("TOSTR", CONO.trim()));
//                nameValuePairs.add(new BasicNameValuePair("QTY", CONO.trim()));
//                nameValuePairs.add(new BasicNameValuePair("ORJQTY", CONO.trim()));
////                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentObject.toString().trim()));
//                Log.e("JSONSTR", ReplacmentObject.toString());
//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//                HttpResponse response = client.execute(request);
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                in.close();
//
//
//                JsonResponse = sb.toString();
//                Log.e("JsonResponse", "Export Replacement" + JsonResponse);
//
//
//            } catch (Exception e) {
//            }
//            return JsonResponse;
//        }
//
//        @Override
//        protected void onPostExecute(final String result) {
//            super.onPostExecute(result);
//
//            Log.e("JSONTaskAddReplacment", "" + result);
//            pdRepla.dismissWithAnimation();
//            exportTrans();
//            if (result != null && !result.equals("")) {
//                Log.e("IrTransFer,result===", result+"");
//                if (result.contains("Internal server error")) {
//
//
//                } else if (result.contains("unique constraint")) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        String res = jsonObject.getString("ErrorDesc");
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } else if (result.contains("table or view does not exist")) {
//
//
//                } else {
//                    if (result.contains("Saved Successfully")) {
//
//
//
//
//
//                    } else {
//
//
//                    }
//                }
//
//
//            } else {
//
//
//
//            }
//
//
//        }
//
//    }
    ////////B
    public void JSONTask_ExportSerials(SweetAlertDialog savingDialog, List<ReplacementModel> replacements) {
        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/SaveSerialTransfer";
        Log.e("Export Serials URL ", url);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ExportSerials Response", response);
                if (response.contains("Saved Successfully")) {
                    savingDialog.dismissWithAnimation();
                    for (int r = 0; r < replacements.size(); r++) {

                        my_dataBase.serialTransfersDao().postSerialsFor(
                                replacements.get(r).getItemcode(), replacements.get(r).getTransNumber());

                    }
//                    SweetAlertDialog savingDialog2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//                    savingDialog2.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
//                    savingDialog2.setTitleText(context.getString(R.string.saving));
//                    savingDialog2.setCancelable(false);
//                    savingDialog2.show();
                  //  JSONTask_ExportTrans(savingDialog2);
                    exportTrans(0);
                } else if (response.contains("server error")) {
                    savingDialog.dismissWithAnimation();
                    exportTrans(0);
                    showSweetDialog(context, 0, "Internal server error", "");
                } else if (response.contains("unique constraint")) {
                    savingDialog.dismissWithAnimation();
                    Log.e("unique response", response.toString() + "");
                    exportTrans(0);
                    try {
                        showSweetDialog(context, 0, "Unique Constraint", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                savingDialog.dismissWithAnimation();
                showSweetDialog(context, 0, context.getString(R.string.checkConnection), "");
                exportTrans(0);
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<>();

                // on below line we are passing our key
                // and value pair to our parameters.
                List<ItemSerialTransfer> serialTransfers = new ArrayList<>();
                for (int r = 0; r < replacements.size(); r++) {

                    serialTransfers.addAll(my_dataBase.serialTransfersDao().getAllAdded(
                            replacements.get(r).getItemcode(), replacements.get(r).getTransNumber()
                    ));


                }
//                serialTransfers = my_dataBase.serialTransfersDao().getUnPosted();
                getSerialsObject(serialTransfers);
//                my_dataBase.serialsDao().postSerials();
                params.put("CONO", CONO.trim());
                params.put("JSONSTR", serialsObject.toString());

                Log.e("JSONSTRSer", serialsObject.toString());
                // at last we are
                // returning our params.
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    ////////B
    public void JSONTask_ExportTrans(SweetAlertDialog savingDialog) {
        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS" + "?CONO=" + CONO;
        Log.e("Export Trans URL ", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("ExportTrans Response", response.toString());
                if (response.toString().contains("Saved Successfully")) {

                    savingDialog.dismissWithAnimation();
                    exportAllState.setText("exported");

//                    reportsList = my_dataBase.replacementDao().getReplacementsByDate(etPickDate.getText().toString());
//                    transReportsAdapter = new TransReportsAdapter(context, reportsList);
//                    rvTransferReports.setAdapter(transReportsAdapter);
//                    rvTransferReports.setLayoutManager(new LinearLayoutManager(context));

                    showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");


//                    ((Activity)context).recreate();

//                    if (Login.serialsActive == 1) {
//                        int d = my_dataBase.serialsDao().deleteAllSerials();
//                        Log.e("DeleteSERIALS", d + "");
//                        importData.getAllSerials(new ImportData.GetSerialsCallBack() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//                                Log.e("responseLength", response.length() + "");
//                                allItemSerials.clear();
//                                for (int i = 0; i < response.length(); i++) {
//                                    try {
//                                        allItemSerials.add(new SerialsModel(
//                                                response.getJSONObject(i).getString("STORENO"),
//                                                response.getJSONObject(i).getString("ITEMOCODE"),
//                                                response.getJSONObject(i).getString("SERIALCODE")));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                Log.e("allItemSerialsLength", allItemSerials.size() + "");
//                                my_dataBase.serialsDao().insertAll(allItemSerials);
//                            }
//
//                            @Override
//                            public void onError(String error) {
//
//                            }
//                        });
//                    }
                } else if (response.toString().contains("server error")) {
                    savingDialog.dismissWithAnimation();
                    showSweetDialog(context, 0, "Internal server error", "");
                } else if (response.toString().contains("unique constraint")) {
                    savingDialog.dismissWithAnimation();
                    Log.e("unique response", response.toString() + "");
                    showSweetDialog(context, 0, "Unique Constraint", "");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                savingDialog.dismissWithAnimation();

                if ((error.getMessage() + "").contains("value too large for column \"A2021_295\""))
                    showSweetDialog(context, 0, "Server Error!", "Value too large for column \"A2021_295\"");
                else
                    showSweetDialog(context, 0, context.getString(R.string.checkConnection), "");

                Log.e("ExportTrans Error ", error.getMessage() + "");
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 3;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

//    public void JSONTask_ExportTransTest(SweetAlertDialog savingDialog) {
//        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS" + "?";
//        Log.e("Export Trans URL ", url);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("ExportTrans Response", response + "");
//                if (response.toString().contains("Saved Successfully")) {
//                    savingDialog.dismissWithAnimation();
//                    exportAllState.setText("exported");
//
//                } else if ((response + "").contains("server error")) {
//                    savingDialog.dismissWithAnimation();
//                    showSweetDialog(context, 0, "Internal server error", "");
//                } else if ((response + "").contains("unique constraint")) {
//                    savingDialog.dismissWithAnimation();
//                    Log.e("unique response", response + "");
//                    showSweetDialog(context, 0, "Unique Constraint", "");
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                savingDialog.dismissWithAnimation();
//
//                if ((error.getMessage() + "").contains("value too large for column \"A2021_295\""))
//                    showSweetDialog(context, 0, "Server Error!", "Value too large for column \"A2021_295\"");
//                else
//                    showSweetDialog(context, 0, context.getString(R.string.checkConnection), "");
//
//                Log.e("ExportTrans Error ", error.getMessage() + "");
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("CONO", CONO);
//
//                return params;
//
//            }
//
//        };
//
//
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 30000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 3;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//
//        RequestQueueSingleton.getInstance(context.getApplicationContext()).
//                addToRequestQueue(stringRequest);
//
//    }
private class JSONTask_savetrans extends AsyncTask<String, String, String> {
    String  VHFNO="";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String do_ = "my";

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            if (!ipAddress.equals("")) {

//
//                if(replacementlist.size()!=0)
//                    VHFNO=replacementlist.get(0).getTransNumber();
                link = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS?CONO=" + CONO.trim()+"&VHFNO=" + MainActivity.VHFNO;

                Log.e("link===", "" + link);
            }
        } catch (Exception e) {
            Log.e("getAllSto", e.getMessage());
        savingDialog3 .dismiss();
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
                    savingDialog3.dismiss();
                    Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                }
            });


            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + e.getMessage());
            savingDialog3.dismiss();
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
        savingDialog3.dismiss();
        if (array != null) {
            Log.e("EXPORTTRANSresult===",array+"");
            if (array.toString().trim().contains("Successfully")) {
                showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");
                savingDialog3.dismiss();



            }else {
                Log.e("not ssaved1","not ssaved1");
                savingDialog3.dismiss();
                showSweetDialog(context, 0, context.getResources().getString(R.string.Failedexported), "");





            }
        } else {

            Log.e("not ssaved","not ssaved");
            showSweetDialog(context, 0, "Server Error!", "");

            savingDialog3.dismiss();
        }
    }


}
    private class JSONTask_savetrans2 extends AsyncTask<String, String, String> {

       String VHFNO="";
        int flage;
        public JSONTask_savetrans2(int flage,String VHFNO) {
            this.flage= flage;
            this.VHFNO = VHFNO;
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

//
//                if(replacementlist.size()!=0)
//                    VHFNO=replacementlist.get(0).getTransNumber();
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS?CONO=" + CONO.trim()+"&VHFNO=" +VHFNO;

                    Log.e("link===", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());
                savingDialog3 .dismiss();
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
                        savingDialog3.dismiss();
                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                savingDialog3.dismiss();
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
            savingDialog3.dismiss();
            if (array != null) {
                Log.e("EXPORTTRANSresult===",array+""+flage);
                if (array.toString().trim().contains("Successfully")) {
                  if(flage==0) {
                      showSweetDialog(context, 1, context.getResources().getString(R.string.savedSuccsesfule), "");
                      savingDialog3.dismiss();
                  }


                }else {
                    Log.e("not ssaved1","not ssaved1");

                    if(flage==0) {
                        showSweetDialog(context, 0, context.getResources().getString(R.string.Failedexported), "");

                        savingDialog3.dismiss();

                    }

                }
            } else {

                Log.e("not ssaved","not ssaved");
                if(flage==0)    { showSweetDialog(context, 0, "Server Error!", "");

                savingDialog3.dismiss();}
            }
        }


    }
    boolean checkallqty(List<ReplacementModel>replacementlist){
        boolean flage=false;
        try{
        for(int i=0;i<New_replacementlist.size();i++)
        {     Log.e("checkallqty22==",New_replacementlist.get(i).getRecQty()+"  "+New_replacementlist.get(i).getUpdatedQty());
        if(Double.parseDouble(New_replacementlist.get(i).getRecQty())>=Double.parseDouble(New_replacementlist.get(i).getUpdatedQty()))
            flage=true;

        else
        {
            flage=false;
            break;
        }}}
        catch (Exception e){
            flage=false;
        }
Log.e("checkallqty==",flage+"");
        New_replacementlist.clear();
        return flage;
    }
    private class JSONTask_gettotrans extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/gettotrans?CONO=" + CONO.trim()+"&VHFNO=" + MainActivity.VHFNO;

                    Log.e("link===", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());

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

                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());

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

            if (array != null) {
                Log.e("gettotransclass===",array);
                if (array.toString().trim().contains("VAL")) {
                    try {
                        JSONObject jsonObject1 = null;
                        JSONArray requestArray = null;
                        requestArray = new JSONArray(array);

                        for (int i = 0; i < requestArray.length(); i++) {


                            jsonObject1 = requestArray.getJSONObject(i);
                            String VAL = jsonObject1.getString("VAL");
                            if (VAL == null) VAL = "0";
                            if (VAL.equals("")) VAL = "0";

                            Log.e("VAL==", VAL);
                            if (Integer.parseInt(VAL) >= 0)
                                exportTrans(1);
                            else
                                exportTrans(1);

                                //showSweetDialog(context, 1, context.getString(R.string.savedSuccsesfule), "");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
               else if (array.toString().trim().contains("Successfully")) {



                }else {
                    Log.e("not ssaved34","not ssaved1");



                }
            } else {

                Log.e("not ssaved44","not ssaved");

            }
        }


    }
}
