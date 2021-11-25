package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hiaryabeer.transferapp.Login;
import com.hiaryabeer.transferapp.MainActivity;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.MainActivity.exportAllState;
import static com.hiaryabeer.transferapp.MainActivity.poststateRE;
import static com.hiaryabeer.transferapp.MainActivity.saveflage;
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

    public ArrayList<ReplacementModel> listAllReplacment = new ArrayList<>();
    int typeExportZone = 0;
    int typeExportShipment = 0;
    int typeExportReplacement = 0;

    public ExportData(Context context) {
        this.context = context;
        my_dataBase = RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.fillIpAndComNo), Toast.LENGTH_SHORT).show();
        }
//        headerDll = "/Falcons/VAN.Dll/";
//        headerDll = "";

    }

    private void getIpAddress() {
//
        ipAddress = my_dataBase.settingDao().getIpAddress().trim();
        CONO = my_dataBase.settingDao().getCono().trim();
        portIp = my_dataBase.settingDao().getPort().trim();
        ipAddress = ipAddress + ":" + portIp;
        Log.e("getIpAddress", "1" + ipAddress + "port=" + portIp);
//        Log.e("getIpAddress",""+ipAddress);


    }

    public void exportReplacementList(List<ReplacementModel> replacementlist) {
        getReplacmentObject(replacementlist);
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla.setTitleText(context.getString(R.string.exportRep));
        pdRepla.setCancelable(false);
        pdRepla.show();

        new JSONTask_AddReplacment(replacementlist).execute();
    }

    private void getReplacmentObject(List<ReplacementModel> replacementlist) {
        jsonArrayReplacement = new JSONArray();
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

        List<ReplacementModel> replacementList = new ArrayList<>();

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
//            Log.e("JSONTaskAddReplacment", "" + result);
            pdRepla.dismissWithAnimation();

            if (result != null && !result.equals("")) {
                if (result.contains("Internal server error")) {
                    exportAllState.setText("server error");
                } else if (result.contains("unique constraint")) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String res = jsonObject.getString("ErrorDesc");
                        exportAllState.setText("unique constraint" + res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (result.contains("table or view does not exist")) {
                    exportAllState.setText("server error");
                } else {
                    if (result.contains("Saved Successfully")) {
                        //  poststateRE.setText("exported");

//                        exportAllState.setText("exported");
                        my_dataBase.replacementDao().updateReplashmentPosted();
                        if (Login.serialsActive == 1) {

                            SweetAlertDialog savingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                            savingDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                            savingDialog.setTitleText(context.getString(R.string.savingSerials));
                            savingDialog.setCancelable(false);
                            savingDialog.show();
                            JSONTask_ExportSerials(savingDialog);

                        } else {
                            SweetAlertDialog savingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                            savingDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                            savingDialog.setTitleText(context.getString(R.string.saving));
                            savingDialog.setCancelable(false);
                            savingDialog.show();
                            JSONTask_ExportTrans(savingDialog);
                        }


                    } else {

                        Log.e("aaaaaaa1===", "aaaaaaa");
                        exportAllState.setText("err");

                    }
                }


            } else {

                Log.e("aaaaaaa2===", "aaaaaaa");
//                Log.e("aaaaaaa2===","aaaaaaa");
                exportAllState.setText("not");


            }


        }

    }

    ////////B
    public void JSONTask_ExportSerials(SweetAlertDialog savingDialog) {
        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/SaveSerialTransfer";
        Log.e("Export Serials URL ", url);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ExportSerials Response", response);
                if (response.toString().contains("Saved Successfully")) {
                    savingDialog.dismissWithAnimation();
                    my_dataBase.serialsDao().postSerials();
                    SweetAlertDialog savingDialog2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    savingDialog2.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                    savingDialog2.setTitleText(context.getString(R.string.saving));
                    savingDialog2.setCancelable(false);
                    savingDialog2.show();
                    JSONTask_ExportTrans(savingDialog2);
                } else if (response.toString().contains("server error")) {
                    savingDialog.dismissWithAnimation();
                    showSweetDialog(context, 0, "Internal server error", "");
                } else if (response.toString().contains("unique constraint")) {
                    savingDialog.dismissWithAnimation();
                    Log.e("unique response", response.toString() + "");
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
                List<ItemSerialTransfer> serialTransfers = my_dataBase.serialsDao().getUnPosted();
                getSerialsObject(serialTransfers);
//                my_dataBase.serialsDao().postSerials();
                params.put("CONO", CONO.trim());
                params.put("JSONSTR", serialsObject.toString());

                Log.e("JSONSTRSer",serialsObject.toString());
                // at last we are
                // returning our params.
                return params;
            }
        };

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    ////////B
    public void JSONTask_ExportTrans(SweetAlertDialog savingDialog) {
        String url = "http://" + ipAddress.trim() + headerDll.trim() + "/EXPORTTRANS" + "?CONO=" + CONO;
        Log.e("Export Trans URL ", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("ExportTrans Response", response.toString());
                if (response.toString().contains("Saved Successfully")) {
                    savingDialog.dismissWithAnimation();
                    exportAllState.setText("exported");
                } else if (response.toString().contains("server error")) {
                    savingDialog.dismissWithAnimation();
                    showSweetDialog(context, 0, "Internal server error", "");
                } else if (response.toString().contains("unique constraint")) {
                    savingDialog.dismissWithAnimation();
                    Log.e("unique response", response.toString() + "");
                    try {
                        String res = response.getString("ErrorDesc");
                        showSweetDialog(context, 0, "Unique Constraint", res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                savingDialog.dismissWithAnimation();
                showSweetDialog(context, 0, context.getString(R.string.checkConnection), "");

                Log.e("ExportTrans Error ", error.getMessage() + "");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("CONO", CONO);

                // at last we are
                // returning our params.
                return params;
            }
        };

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

}
