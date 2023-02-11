package com.hiaryabeer.transferapp.Activities;

import static android.view.View.LAYOUT_DIRECTION_RTL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hiaryabeer.transferapp.Adapters.Adapterr;
import com.hiaryabeer.transferapp.Adapters.ReplacementAdapter;
import com.hiaryabeer.transferapp.Adapters.SerialsAdapter;
import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.ExportData;
import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.hiaryabeer.transferapp.Models.ImportData;
import com.hiaryabeer.transferapp.Models.ItemSerialTransfer;
import com.hiaryabeer.transferapp.Models.ItemSwitch;
import com.hiaryabeer.transferapp.Models.ItemsUnit;
import com.hiaryabeer.transferapp.Models.KeyboardUtil;
import com.hiaryabeer.transferapp.Models.SerialsModel;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.Models.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.Store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.widget.PopupMenu;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.Activities.Login.serialsActive;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.convertToEnglish;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;
import static com.hiaryabeer.transferapp.Models.ImportData.AllImportItemlist;
import static com.hiaryabeer.transferapp.Models.ImportData.Storelist;
import static com.hiaryabeer.transferapp.Models.ImportData.listAllItemSwitch;
import static com.hiaryabeer.transferapp.Models.ImportData.listAllZone;
import static com.hiaryabeer.transferapp.Models.ImportData.listQtyZone;
import static com.hiaryabeer.transferapp.Models.ImportData.pdRepla2;
import static com.hiaryabeer.transferapp.Models.ImportData.voucherlist;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    int saved = 4;
    int position;
    public static  String item_num="";
    public static  TextView iraqswitch;
    public static int actvityflage = 1;
    public String UserNo;
    public static TextView respon, qtyrespons, exportAllState;
    GeneralMethod generalMethod;
    public static Spinner fromSpinner, toSpinner;
    ExportData exportData;
    ImportData importData;
    public static EditText poststateRE, DZRE_ZONEcode;
    public static EditText zone, itemcode;
    public static TextView qty;
    public String deviceId = "";
    public static TextView DZRE_zonecodeshow, DZRE_qtyshow;
    public static List<ReplacementModel> DB_replist = new ArrayList<>();
    private ImageButton btnShow;
    public static List<ReplacementModel> DB_replistcopy = new ArrayList<>();
    public static List<ItemSwitch> DB_itemswitch = new ArrayList<>();
    public static List<ReplacementModel> reducedqtyitemlist = new ArrayList<>();
    public static Dialog Re_searchdialog;
        TextView recqty,fromdate,todate;
    TextView search,scanItemCode;
    public static Button save;
    public int indexZone = -1;
    public int indexDBZone = 0, indexDBitem = 0, indexOfReduceditem = 0;
    public static RoomAllData my_dataBase;
    String From, To, Zone, Itemcode, Qty;
    String FromNo, ToNo;
    ReplacementModel replacement;
    ReplacementModel replacementModel;
    public static ReplacementAdapter adapter;
    public static boolean validItem = false, validateKind = false;
    public static RecyclerView replacmentRecycler;
    public static final int REQUEST_Camera_Barcode = 1;
    List<com.hiaryabeer.transferapp.appSettings> appSettings;
    public static Dialog dialog1;
    List<ReplacementModel> deleted_DBzone;
    private Dialog authenticationdialog;
    List<String> spinnerArray = new ArrayList<>();
    public static TextView itemrespons;
    public static Spinner spinner, spinner2;
    public static ArrayList<ReplacementModel> replacementlist = new ArrayList<>();
    public List<ReplacementModel> UnPostedreplacementlist = new ArrayList<>();
    public List<ReplacementModel> Allreplacementlist1 = new ArrayList<>();
    public List<ReplacementModel> Allreplacementlist2 = new ArrayList<>();
    public static ArrayList<Store> Storelistt = new ArrayList<>();
    public static Button DZRE_delete;
    public static TextView DIRE_close_btn,
            DIRE_zoneSearch2,
            DIRE_preQTY,
            DIRE_itemcodeshow,
            DIRE_zoneshow,
            DIRE_qtyshow;
    List<String> DB_store;
    List<String> DB_zone;
    EditText UsNa;
    public static List<AllItems> AllItemDBlist = new ArrayList<>();
    public static EditText DIRE_ZONEcode, DIRE_itemcode;
    public Button export;
    public static List<AllItems> AllItemstest = new ArrayList<>();
    private int pos;
    static Dialog ItemSwitchdialog,ItemUnitsdialog;
    String maxTrans = "";
    public static int max = 0;
    public static int saveflage;
    private int TransferNo;
    String maxVochNum;
    private String minVo;
    private String MaxVo;
    public static TextView colorlastrow, colorData, getResponce;
    Calendar myCalendar;

    public static int highligtedItemPosition = -1;
    public static int highligtedItemPosition2 = -1;

    public static EditText etSerial;
    private ArrayList<ItemSerialTransfer> serialTransfers;
    private RecyclerView rvSerialTransfers;
    private SerialsAdapter serialsAdapter;
    private int repPosition;
    public static TextView tvTotal;
    private List<ItemSerialTransfer> allTransSerials;
    private int transNo;
    String codeScanned;
    List<SerialsModel> allItemSerials = new ArrayList<>();
    private Button saveBtn, cancelBtn;
    private ImageButton btnRefresh,internalOrder;
    ItemSwitch itemSwitch;
    AllItems Item;
    TextView itemUnit_text;
    public static List<String> voucher_no_list= new ArrayList<>();
   // public List<Item_Unit_Details> allUnitDetails;
    //    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.goToReports: {
//                Intent i = new Intent(MainActivity.this, TransferReports.class);
//                startActivity(i);
//                return true;
//            }
//            case R.id.menuImport: {
//                if (Login.serialsActive == 1) {
//                    int d = my_dataBase.serialsDao().deleteAllSerials();
//                    Log.e("DeleteSERIALS", d + "");
//                    importData.getAllSerials(new ImportData.GetSerialsCallBack() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            Log.e("responseLength", response.length() + "");
//                            allItemSerials.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    allItemSerials.add(new SerialsModel(
//                                            response.getJSONObject(i).getString("STORENO"),
//                                            response.getJSONObject(i).getString("ITEMOCODE"),
//                                            response.getJSONObject(i).getString("SERIALCODE")));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.e("allItemSerialsLength", allItemSerials.size() + "");
//                            my_dataBase.serialsDao().insertAll(allItemSerials);
//                        }
//
//                        @Override
//                        public void onError(String error) {
//
//                        }
//                    });
//                }
//                return true;
//            }
//            case R.id.menuExport: {
//                Log.e("export", "clicked");
//
//                // UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();
//                exportAllData();
//                maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
//                if (maxVochNum != null) {
//                    Log.e(" maxVochNum", maxVochNum);
//                    max = Integer.parseInt(maxVochNum) + 1;
//                }
//
//                zone.setEnabled(true);
//                zone.requestFocus();
//
//                zone.setText("");
//                itemcode.setText("");
//                return true;     }
//            default:
//                return false;
//        }
//    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());

        popup.getMenu().findItem(R.id.menuImport).setVisible(serialsActive == 1);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked

                switch (menuItem.getItemId()) {

                    case R.id.goToReports: {
                        finish();
                        Intent i = new Intent(MainActivity.this, TransferReports.class);
                        startActivity(i);
                        return true;
                    }
                    case R.id.menuImport: {
                        if (serialsActive == 1) {
                            int d = my_dataBase.serialsDao().deleteAllSerials();
                            Log.e("DeleteSERIALS", d + "");
                            importData.getAllSerials(new ImportData.GetSerialsCallBack() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.e("responseLength", response.length() + "");
                                    allItemSerials.clear();
                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            allItemSerials.add(new SerialsModel(
                                                    response.getJSONObject(i).getString("STORENO"),
                                                    response.getJSONObject(i).getString("ITEMOCODE"),
                                                    response.getJSONObject(i).getString("SERIALCODE")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Log.e("allItemSerialsLength", allItemSerials.size() + "");
                                    my_dataBase.serialsDao().insertAll(allItemSerials);
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                        }
                        return true;
                    }
                    case R.id.menuExport: {
                        Log.e("export", "clicked");

                        // UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();
                        exportAllData();
                        maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
                        if (maxVochNum != null) {
                            Log.e(" maxVochNum", maxVochNum);
                            max = Integer.parseInt(maxVochNum) + 1;
                        }

                        zone.setEnabled(true);
                        zone.requestFocus();

                        zone.setText("");
                        itemcode.setText("");
                        return true;
                    }
                    case R.id. menugetitemswitch: {

                        if(Login.iraqFlage==1)
                            Iraq_getitemswitch();
                        else
                            importData. getItemSwitch();
                        return true;
                    }
                    default:
                        return false;
                }

            }
        });
        popup.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  importData = new ImportData(MainActivity.this);

        AllItemDBlist.clear();
        DB_itemswitch.clear();

        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);
         init();

        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL)
            btnShow.setRotationY(180);
        itemcode.setText("");
        minVo = my_dataBase.replacementDao().getMinVocherNo();
        Log.e("init-minVo==", minVo + "");
        //  my_dataBase.replacementDao().deleteALL();
        maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();

        if (maxVochNum != null) {
            Log.e(" maxVochNum", maxVochNum);
            max = Integer.parseInt(maxVochNum) + 1;
        } else
            max = 1;


        new KeyboardUtil(this, replacmentRecycler);
        AllItemDBlist.addAll(my_dataBase.itemDao().getAll());
        //DB_itemswitch.addAll(my_dataBase.itemSwitchDao().getAll());
//        if(AllItemDBlist .size()==0)
//            importData.getAllItems();
//      my_dataBase.storeDao().deleteall();
        Storelist.clear();
        Storelist = my_dataBase.storeDao().getall();

        if (serialsActive == 1) {
            List<SerialsModel> allSerialsList = my_dataBase.serialsDao().getAllSerials();
            if (allSerialsList.size() == 0) {
                importData.getAllSerials(new ImportData.GetSerialsCallBack() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("responseLength", response.length() + "");
                        allItemSerials.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                allItemSerials.add(new SerialsModel(
                                        response.getJSONObject(i).getString("STORENO"),
                                        response.getJSONObject(i).getString("ITEMOCODE"),
                                        response.getJSONObject(i).getString("SERIALCODE")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("allItemSerialsLength", allItemSerials.size() + "");
                        my_dataBase.serialsDao().insertAll(allItemSerials);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }

//      my_dataBase.replacementDao().deleteALL();

        spinnerArray.clear();

        if (Storelist.size() > 0) {
            Log.e("sss", "sss");
            for (int i = 0; i < Storelist.size(); i++) {
                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());

            }
            fillSp();
        } else {
            getStors();
            Log.e("sss4", "sss4");
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appSettings.get(0).getCheckQty().equals("1")) {
                    //////Qty Checker Activated

                    if (!isSaved()) {
                        SweetAlertDialog dataNotSaved = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setContentText(getString(R.string.unsaved_data))
                                .setConfirmText(getResources().getString(R.string.save))
                                .setConfirmClickListener(sweetAlertDialog -> {

                                    exportAllData();
                                  /*  maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
                                    if (maxVochNum != null) {
                                        Log.e(" maxVochNum", maxVochNum);
                                        max = Integer.parseInt(maxVochNum) + 1;
                                    }*/

                                    sweetAlertDialog.dismissWithAnimation();

                                });
                        dataNotSaved.show();

                    } else {
                        String sss = "";
                        if ((AllItemDBlist.size()) > 0)
                            opensearchDailog();
                        else {
                            AllItemDBlist.addAll(my_dataBase.itemDao().getAll());
                            if ((AllItemDBlist.size()) > 0) {
                                opensearchDailog();
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.Empty), Toast.LENGTH_LONG).show();
                                itemcode.setText("");
                            }
                        }
                    }
                } else
                { ////Qty Checker Inactivated
                    if ((AllItemDBlist.size()) > 0) {
                        opensearchDailog();
                    } else {
                        AllItemDBlist.addAll(my_dataBase.itemDao().getAll());
                        if ((AllItemDBlist.size()) > 0) {
                            opensearchDailog();
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.Empty), Toast.LENGTH_LONG).show();
                            itemcode.setText("");
                        }
                    }
                }

            }
        });


        zone.setEnabled(true);
        zone.requestFocus();
        itemcode.setEnabled(true);
        recqty.setEnabled(false);
        save.setEnabled(false);

        UserNo = my_dataBase.settingDao().getUserNo();
//my_dataBase.replacementDao().deleteALL();
      /*  if(Storelistt.size()==0) {
            itemcode.setEnabled(false);
            zone.setEnabled(false);

        }*/
        //testcode
/*ReplacementModel replacementModel=new ReplacementModel();
        replacementModel.setRecQty("1");
        replacementModel.setFrom("1234");
        replacementModel.setTo("122222222222");
        replacementModel.setItemcode("tttttttttt");
        replacementModel.setQty("1");
        replacementModel.setZone("fffff");
        for (int i=0;i<50;i++)
      replacementlist.add(replacementModel);
        fillAdapter();*/


        findViewById(R.id.nextZone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zone.setText("");
                zone.setEnabled(true);
                zone.requestFocus();
                itemcode.setText("");
                if (replacementlist != null)
                    if (replacementlist.size() > 0) replacementlist.clear();
                if (adapter != null) adapter.notifyDataSetChanged();
            }
        });

        try {

            fromSpinner.setSelection(0);
            toSpinner.setSelection(1);
            toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code her

                    if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.samestore), "");
                        if (fromSpinner.getSelectedItemPosition() != 1) toSpinner.setSelection(1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code her

                    if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.samestore), "");
                        if (toSpinner.getSelectedItemPosition() != 0) fromSpinner.setSelection(0);
                        //else
                        //   fromSP.setSelection(1);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        } catch (Exception e) {
        }


        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("export", "clicked");

                // UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();
                exportAllData();
                maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
                if (maxVochNum != null) {

                    Log.e(" maxVochNum", maxVochNum);
                    max = Integer.parseInt(maxVochNum) + 1;


                }

                zone.setEnabled(true);
                zone.requestFocus();

                zone.setText("");
                itemcode.setText("");

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromSpinner.setEnabled(true);
                toSpinner.setEnabled(true);

                exportAllData();

                maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
                if (maxVochNum != null) {
                    Log.e(" maxVochNum", maxVochNum);
                    max = Integer.parseInt(maxVochNum) + 1;
                }
                zone.setEnabled(true);
                zone.requestFocus();

                zone.setText("");
                itemcode.setText("");
                //     qty.setText("");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportAllData();
                maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
                if (maxVochNum != null) {
                    Log.e(" maxVochNum", maxVochNum);
                    max = Integer.parseInt(maxVochNum) + 1;
                }

                zone.setEnabled(true);
                zone.requestFocus();

                zone.setText("");
                itemcode.setText("");

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(R.string.cancel_dialog_msg);

                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (replacementlist.size() > 0) {

                            my_dataBase.replacementDao().deleteAllinTransfer(replacementlist.get(0).getTransNumber());

                            if (serialsActive == 1)
                                my_dataBase.serialTransfersDao().deleteAllinTransfer(replacementlist.get(0).getTransNumber());

                        }

                        replacementlist.clear();

                        fillAdapter();

                        itemcode.setText("");
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);

                        dialog.cancel();

                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importAll();
            }
        });


        findViewById(R.id.Re_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (replacementlist.size() > 0) {

                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.messageExit2))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                    replacementlist.clear();
                                                    adapter.notifyDataSetChanged();
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    finish();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            }).show();
                                } else {
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }
                            }
                        }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();


            }
        });


    }

    private void importAll() {

        Storelist.clear();
        AllItemDBlist.clear();
        spinnerArray.clear();
        allItemSerials.clear();

        int d = my_dataBase.serialsDao().deleteAllSerials();

        my_dataBase.storeDao().deleteall();

        if (serialsActive == 1) {

            importData.getAllSerials(new ImportData.GetSerialsCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("responseLength", response.length() + "");
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            allItemSerials.add(new SerialsModel(
                                    response.getJSONObject(i).getString("STORENO"),
                                    response.getJSONObject(i).getString("ITEMOCODE"),
                                    response.getJSONObject(i).getString("SERIALCODE")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("allItemSerialsLength", allItemSerials.size() + "");
                    my_dataBase.serialsDao().insertAll(allItemSerials);

                    getStors();


                }

                @Override
                public void onError(String error) {
                    getStors();


                }
            });

        } else {
            getStors();


        }
    }


    public boolean isSaved() {

        boolean saved = true;
        for (int i = 0; i < my_dataBase.replacementDao().getAllReplacements(String.valueOf(max)).size(); i++) {
            if (my_dataBase.replacementDao().getAllReplacements(String.valueOf(max)).get(i).getIsPosted().equals("0")) {
                saved = false;
                break;
            }

        }
        return saved;
    }

    private void opensearchDailog() {
        AllItemstest.clear();
        dialog1 = new Dialog(MainActivity.this);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.searchdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = 500;
        lp.height = 700;
        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();
        Log.e("size", AllItemDBlist.size() + "");
        final ListView listView = dialog1.findViewById(R.id.Rec);
        final EditText search = dialog1.findViewById(R.id.search);
        final Spinner categorySpinner = dialog1.findViewById(R.id.categorySpinner);
        final Spinner kindSpinner = dialog1.findViewById(R.id.kindSpinner);

        List<String> categories = new ArrayList<>();
        List<String> kinds = new ArrayList<>();

        categories.add(0, getString(R.string.category));
        kinds.add(0, getString(R.string.kind));

        for (int i = 0; i < AllItemDBlist.size(); i++) {

            if (AllItemDBlist.get(i).getItemG() != null && AllItemDBlist.get(i).getItemK() != null) {

                if (!categories.contains(AllItemDBlist.get(i).getItemG()) && !AllItemDBlist.get(i).getItemG().equals(""))
                    categories.add(AllItemDBlist.get(i).getItemG());

                if (!kinds.contains(AllItemDBlist.get(i).getItemK()) && !AllItemDBlist.get(i).getItemK().equals(""))
                    kinds.add(AllItemDBlist.get(i).getItemK());

            }

        }

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, categories);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(0);

        ArrayAdapter<String> kindAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, kinds);
        kindSpinner.setAdapter(kindAdapter);
        kindSpinner.setSelection(0);

        Adapterr adapter1 = new Adapterr(this, AllItemDBlist);
        listView.setAdapter(adapter1);

//        search.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_START = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_END = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    int end = v.getResources().getConfiguration().getLayoutDirection() == LAYOUT_DIRECTION_RTL ? search.getLeft() : search.getRight();
//
//                    if (event.getRawX() >= (end - search.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
//
//                        search.setText("");
//
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (categorySpinner.getSelectedItemPosition() == 0 && kindSpinner.getSelectedItemPosition() == 0 && editable.toString().trim().equals("")) {

                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemDBlist);
                    listView.setAdapter(adapter1);

                } else {

                    searchItems(categorySpinner.getSelectedItemPosition(), categorySpinner.getSelectedItem().toString(),
                            kindSpinner.getSelectedItemPosition(), kindSpinner.getSelectedItem().toString(),
                            editable.toString());

                    Adapterr adapter2 = new Adapterr(MainActivity.this, AllItemstest);
                    listView.setAdapter(adapter2);

                }

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (categorySpinner.getSelectedItemPosition() == 0 && kindSpinner.getSelectedItemPosition() == 0 && search.getText().toString().trim().equals("")) {

                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemDBlist);
                    listView.setAdapter(adapter1);

                } else {

                    searchItems(position, categorySpinner.getSelectedItem().toString(),
                            kindSpinner.getSelectedItemPosition(), kindSpinner.getSelectedItem().toString(),
                            search.getText().toString());

                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemstest);
                    listView.setAdapter(adapter1);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        kindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (categorySpinner.getSelectedItemPosition() == 0 && kindSpinner.getSelectedItemPosition() == 0 && search.getText().toString().trim().equals("")) {

                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemDBlist);
                    listView.setAdapter(adapter1);

                } else {

                    searchItems(categorySpinner.getSelectedItemPosition(), categorySpinner.getSelectedItem().toString(),
                            position, kindSpinner.getSelectedItem().toString(),
                            search.getText().toString());

                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemstest);
                    listView.setAdapter(adapter1);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void searchItems(int catPosition, String category, int kindPos, String kind, String search) {

        AllItemstest.clear();

        if (catPosition != 0) {

            for (int i = 0; i < AllItemDBlist.size(); i++) {

                if (AllItemDBlist.get(i).getItemG().equals(category)) {

                    if (kindPos == 0 && search.trim().equals("")) {

                        AllItemstest.add(AllItemDBlist.get(i));

                    } else if (kindPos != 0 && search.trim().equals("")) {

                        if (AllItemDBlist.get(i).getItemK().equals(kind))
                            AllItemstest.add(AllItemDBlist.get(i));

                    } else if (kindPos == 0 && !search.trim().equals("")) {

                        if (AllItemDBlist.get(i).getItemNameA().toLowerCase().trim().contains(search.trim().toLowerCase()) ||
                                AllItemDBlist.get(i).getItemOcode().trim().contains(search.trim()))
                            AllItemstest.add(AllItemDBlist.get(i));

                    } else if (kindPos != 0 && !search.trim().equals("")) {

                        if (AllItemDBlist.get(i).getItemK().equals(kind) && (
                                AllItemDBlist.get(i).getItemNameA().toLowerCase().trim().contains(search.trim().toLowerCase()) ||
                                        AllItemDBlist.get(i).getItemOcode().trim().contains(search.trim())))
                            AllItemstest.add(AllItemDBlist.get(i));

                    }

                }

            }

        } else {

            if (kindPos == 0 && search.trim().equals("")) {

                AllItemstest = AllItemDBlist;


            } else {

                for (int i = 0; i < AllItemDBlist.size(); i++) {

                    if (kindPos != 0 && search.trim().equals("")) {

                        if (AllItemDBlist.get(i).getItemK().equals(kind))
                            AllItemstest.add(AllItemDBlist.get(i));

                    } else if (kindPos == 0 && !search.trim().equals("")) {

                        if (AllItemDBlist.get(i).getItemNameA().toLowerCase().trim().contains(search.trim().toLowerCase()) ||
                                AllItemDBlist.get(i).getItemOcode().trim().contains(search.trim()))
                            AllItemstest.add(AllItemDBlist.get(i));

                    } else if (kindPos != 0 && !search.trim().equals("")) {

                        if (AllItemDBlist.get(i).getItemK().equals(kind) && (
                                AllItemDBlist.get(i).getItemNameA().toLowerCase().trim().contains(search.trim().toLowerCase()) ||
                                        AllItemDBlist.get(i).getItemOcode().trim().contains(search.trim())))
                            AllItemstest.add(AllItemDBlist.get(i));

                    }

                }

            }

        }

    }


    private boolean isExistsInReducedlist() {
        boolean f = false;


        for (int x = 0; x < reducedqtyitemlist.size(); x++)
            if (reducedqtyitemlist.get(x).getZone().equals(DIRE_ZONEcode.getText().toString().trim()) &&
                    reducedqtyitemlist.get(x).getTo().equals(spinner2.getSelectedItem().toString())
                    && reducedqtyitemlist.get(x).getItemcode().equals(DIRE_itemcodeshow.getText().toString().trim())) {
                f = true;
                indexOfReduceditem = x;
            } else {
                f = false;
                continue;
            }

        return f;
    }

    private boolean isExists(int flage, String zone, String store, String itemcode) {
        boolean f = false;
        if (flage == 1) {
            for (int i = 0; i < DB_replist.size(); i++)
                if (DB_replist.get(i).getZone().equals(zone) &&
                        DB_replist.get(i).getTo().equals(store)) {
                    f = true;
                    indexDBZone = i;
                    break;
                } else {
                    f = false;
                    continue;
                }

        }
        if (flage == 2) {
            for (int i = 0; i < DB_replist.size(); i++)
                if (DB_replist.get(i).getZone().equals(zone) &&
                        DB_replist.get(i).getTo().equals(store)
                        && DB_replist.get(i).getItemcode().equals(itemcode)) {
                    f = true;
                    indexDBitem = i;
                    break;
                } else {
                    f = false;
                    continue;
                }
        }
        return f;
    }

    public static void getqtyofDBzone() {
        int sum = 0;
        for (int x = 0; x < DB_replist.size(); x++)
            if (DB_replist.get(x).getTo().equals(spinner.getSelectedItem().toString()) &&
                    DB_replist.get(x).getZone().equals(DZRE_ZONEcode.getText().toString().trim()))
                sum += Integer.parseInt(DB_replist.get(x).getRecQty());
        DZRE_qtyshow.setText(sum + "");
    }


    public boolean ExistsInLocallist(ReplacementModel replacement) {
        boolean flag = false;

        if (replacementlist.size() != 0) {
            for (int i = 0; i < replacementlist.size(); i++) {
                if (
                        convertToEnglish(replacementlist.get(i).getItemcode()).equals(convertToEnglish(replacement.getItemcode()))

                                ||
                                convertToEnglish(replacementlist.get(i).getItemcode()).equals(convertToEnglish(my_dataBase.itemSwitchDao().getitemocode(replacement.getItemcode())))

                ) {

                    position = i;
                    flag = true;
                    break;

                }
            }
        }

        if (!flag) position = 0;

        return flag;

    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (replacementlist.size() > 0) {

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.messageExit2))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            replacementlist.clear();
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
                        } else {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    }
                }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

    private void getStors() {
        actvityflage = 1;
        ImportData importData=new ImportData(MainActivity.this);
        importData.getStore();

    }
    private void Iraq_getitemswitch(){
        Log.e("getitemswitch","getitemswitch");
        ImportData importData=new ImportData(MainActivity.this);

        ItemSwitchdialog = new Dialog(MainActivity.this);
        ItemSwitchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ItemSwitchdialog.setCancelable(true);
        ItemSwitchdialog.setContentView(R.layout.itemswitchdailog);

        fromdate= ItemSwitchdialog.findViewById(R.id.fromdate);
        todate= ItemSwitchdialog.findViewById(R.id.todate);

        myCalendar = Calendar.getInstance();
        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String today = df.format(currentTimeAndDate);
        fromdate.setText(today);
        todate.setText(today);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, openDatePickerDialog(1), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ItemSwitchdialog.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemSwitchdialog.dismiss();
                importData.getIraqItemSwitch( fromdate.getText().toString().trim(), todate.getText().toString().trim());
            }
        });

        ItemSwitchdialog.show();
    }

//    public void exportData() {
//        try {
//            saveflage = 1;
//            exportData.exportReplacementList(replacementlist);
//        } catch (Exception e) {
//
//            // test
//        }
//
//    }

    public void exportAllData() {
        Allreplacementlist1 = my_dataBase.replacementDao().getallReplacement();
        boolean posted = true;
        for (int i = 0; i < Allreplacementlist1.size(); i++) {
            if (Allreplacementlist1.get(i).getIsPosted().equals("0")) {
                posted = false;
                break;
            }
        }
        exportData.exportReplacementList(Allreplacementlist1);
//        if (!posted) {
//            exportData.exportReplacementList(Allreplacementlist1);
////            if (Login.serialsActive == 1) {
////                int d = my_dataBase.serialsDao().deleteAllSerials();
////                Log.e("DeleteSERIALS", d + "");
////                importData.getAllSerials(new ImportData.GetSerialsCallBack() {
////                    @Override
////                    public void onResponse(JSONArray response) {
////                        Log.e("responseLength", response.length() + "");
////                        allItemSerials.clear();
////                        for (int i = 0; i < response.length(); i++) {
////                            try {
////                                allItemSerials.add(new SerialsModel(
////                                        response.getJSONObject(i).getString("STORENO"),
////                                        response.getJSONObject(i).getString("ITEMOCODE"),
////                                        response.getJSONObject(i).getString("SERIALCODE")));
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                        Log.e("allItemSerialsLength", allItemSerials.size() + "");
////                        my_dataBase.serialsDao().insertAll(allItemSerials);
////                    }
////
////                    @Override
////                    public void onError(String error) {
////
////                    }
////                });
////            }
//        } else
//            showSweetDialog(MainActivity.this, 3, getString(R.string.noUnsavedData), "");

  /*      if (saved == 1) {
            showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
        } else if (saved == 3) {
            showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.msg3), "");

        } else if (saved == 0) {
            Log.e("ggggggg", "gggggg");
            showSweetDialog(MainActivity.this, 0, getString(R.string.checkConnection), "");
            //   MaxVo=my_dataBase.replacementDao().getMaxVocherNo();
    /*    saveflage = 2;


        minVo = my_dataBase.replacementDao().getMinVocherNo();
        Log.e("minVo", minVo + "");
        if (minVo != null) {
            Allreplacementlist1 = my_dataBase.replacementDao().getReplacements(minVo);
            exportData.exportReplacementList(Allreplacementlist1);
        } else {
            Log.e("ggggg2", "gggg2");
            if (saved == 1) {
                showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
            } else if (saved == 3) {
                showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.msg3), "");

            } else if (saved == 0) {
                Log.e("ggggggg", "gggggg");
                showSweetDialog(MainActivity.this, 0, getString(R.string.checkConnection), "");

            }
        }*/


    }

    private void colorlastrow(int pos) {

        Log.e("colorlastrow", "" + pos);

        highligtedItemPosition2 = pos;

        if (adapter != null) adapter.notifyDataSetChanged();
        //    replacmentRecycler.scrollToPosition(pos);
        replacmentRecycler.smoothScrollToPosition(pos);

    }

    private void colorRecycle(int pos) {

        Log.e("colorRecycle", "" + pos);
        highligtedItemPosition2 = -5;
        highligtedItemPosition = pos;

        adapter.notifyDataSetChanged();
        //     replacmentRecycler.scrollToPosition(pos);
        replacmentRecycler.smoothScrollToPosition(pos);

    }

    private void init() {
        replacementlist.clear();
        iraqswitch=findViewById(R.id.iraqswitch);
        iraqswitch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged====","afterTextChanged");
                if (!editable.toString().equals("")) {

                    if (editable.toString().equals("ItemOCode")) {
                        showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                        MainActivity.AddNewSwitchDATA();

                    }else{
                        showSweetDialog(MainActivity.this, 0, getResources().getString(R.string.savednotSuccsesfule), "");

                    }
                }


            }
        });
        colorData = findViewById(R.id.colorData);
        colorData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() != 0) {

                    Log.e("colorlastrow", "" + editable.toString().trim());
                    int position = Integer.parseInt(editable.toString().trim());
                    colorlastrow((position));
                }

            }
        });

        colorlastrow = findViewById(R.id.colorlastrow);
        colorlastrow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() != 0) {
                    Log.e("colorlastrow", "" + editable.toString().trim());
                    int position = Integer.parseInt(editable.toString().trim());
                    colorlastrow((position));
                }

            }
        });
        export = findViewById(R.id.export);
        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);
        replacementlist.clear();
        poststateRE = findViewById(R.id.poststatRE);
        exportAllState = findViewById(R.id.exportAllState);
        itemrespons = findViewById(R.id.itemrespons);
        internalOrder= findViewById(R.id.internalOrder);
        internalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setEnabled(false);
                scanItemCode.setEnabled(false);
                openOrderDialog();
            }
        });
        exportData = new ExportData(MainActivity.this);
        importData = new ImportData(MainActivity.this);
        listAllZone.clear();
//        importData.getAllZones();
        listQtyZone.clear();
        appSettings = new ArrayList();
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
        }
        fromSpinner = findViewById(R.id.fromspinner);
        toSpinner = findViewById(R.id.tospinner);
        zone = findViewById(R.id.zoneedt);
        itemcode = findViewById(R.id.itemcodeedt);
        qty = findViewById(R.id.qty);
        recqty = findViewById(R.id.qtyedt);
        replacmentRecycler = findViewById(R.id.replacmentRec);
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        save = findViewById(R.id.save);
        btnShow = findViewById(R.id.btnShow);
        respon = findViewById(R.id.respons);
        qtyrespons = findViewById(R.id.qtyrespon);
        //  recqty.setOnEditorActionListener(onEditAction);
      //  itemcode.setOnEditorActionListener(onEditAction);
        zone.setOnEditorActionListener(onEditAction);
        generalMethod = new GeneralMethod(MainActivity.this);

        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        btnRefresh = findViewById(R.id.btnRefresh);

      // zone.setOnKeyListener(onKeyListener);
       itemcode.setOnKeyListener(onKeyListener);
        search = findViewById(R.id.searchitemCode);
        scanItemCode= findViewById(R.id.scanItemCode);
        maxTrans = my_dataBase.replacementDao().getMaxReplacementNo();
        Log.e("maxTrans", "" + maxTrans);


        itemrespons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().equals("ItemOCode")) {
                        Log.e("herea", "aaaaa");
                        my_dataBase.itemDao().dELETEAll();

                        my_dataBase.itemDao().insertAll(AllImportItemlist);
                        ImportData.pdRepla.dismissWithAnimation();
                        showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                        getitemUnit();

                        // my_dataBase.itemSwitchDao().dELETEAll();

//                        my_dataBase.itemSwitchDao().dELETEAll();
//                       getitemswitch();

                    } else if (editable.toString().equals("nodata")) {
                        ImportData.pdRepla.dismissWithAnimation();
                        showSweetDialog(MainActivity.this, 0, getResources().getString(R.string.netWorkError), "");

                    }


                }
            }
        });


        //importData.getAllZones();
        respon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {

                    if (editable.toString().equals("no data")) {

                        respon.setText("");
                    pdRepla2.dismiss();
                    } else {
                        if (editable.toString().equals("fill")) {
                            for (int i = 0; i < Storelist.size(); i++) {
                                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                                my_dataBase.storeDao().insert(Storelist.get(i));
                            }


                       pdRepla2.dismiss();
                        }
                        fillSp();

                        // zone.requestFocus();
                        Log.e("afterTextChanged", "" + editable.toString());

                    }

                }

            }
        });
        exportAllState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    Log.e("editable.toString()+++", editable.toString() + "");
                    if (editable.toString().trim().equals("exported")) {
                        {
                            saveData(1);
                            //  for(int i=0;i<replacementlist.size();i++)
                            saved = 1;
                            Log.e("max+++", max + "");
//                            Log.e("TransferNo===", TransferNo + "");
                            //        my_dataBase.replacementDao().updatepostState(minVo);

                            //    exportAllData();

                            //   if(MaxVo.equals(TransferNo))  showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                            replacementlist.clear();
                            fillAdapter();
                            adapter.notifyDataSetChanged();
                            zone.setEnabled(true);
                            zone.requestFocus();

                            fromSpinner.setEnabled(true);
                            toSpinner.setEnabled(true);


//                            showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                            save.setEnabled(false);
                        }
                    } else if (editable.toString().trim().equals("not")) {//no internet
                        saved = 0;
                        //saveData(0);
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();

                        save.setEnabled(false);
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);
                        Log.e("editable.to", editable.toString() + "");
                        showSweetDialog(MainActivity.this, 0, getString(R.string.checkConnection), "");
                    } else if (editable.toString().trim().equals("server error")) {
                        saved = 5;
                        //saveData(0);
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();

                        save.setEnabled(false);
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);
                        showSweetDialog(MainActivity.this, 0, "", "Internal server error");


                    } else if (editable.toString().trim().contains("unique constraint")) {
                        saved = 5;
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();

                        save.setEnabled(false);
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);
                        showSweetDialog(MainActivity.this, 0, "Unique Constraint", editable.toString().trim().substring(17));

                    } else {
                        Log.e("editable.t", editable.toString() + "");
                        saved = 0;
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);
                        // showSweetDialog(MainActivity.this, 0, "check connection", "");
                        save.setEnabled(false);
                    }
                }
            }
        });
        poststateRE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    if (editable.toString().trim().equals("exported")) {
                        { //saveData(1);
                            //  for(int i=0;i<replacementlist.size();i++)
                            Log.e("max+++", max + "");
                            //   my_dataBase.replacementDao().updatepostState(String.valueOf(max));
                            showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                            my_dataBase.replacementDao().updateReplashmentPosted();
                            replacementlist.clear();
                            fillAdapter();
                            adapter.notifyDataSetChanged();
                            zone.setEnabled(true);
                            zone.requestFocus();

                            fromSpinner.setEnabled(true);
                            toSpinner.setEnabled(true);


                            save.setEnabled(false);
                        }
                    } else if (editable.toString().trim().equals("not")) {

                        //saveData(0);
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();

                        save.setEnabled(false);
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);


                    } else {
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);

                        save.setEnabled(false);


                    }
                }
            }
        });

        findViewById(R.id.ic_clear).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        itemcode.setText("");
    }
});

        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }
        itemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                Log.e("setOnKeyactionId==", i+"KeyEvent=="+keyEvent.getAction());


                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
//                            switch (view.getId()) {
//
//                                case R.id.item_Qty:

//                            }
                            return true;
                        default:
                            break;
                    }
                }


                return false;
            }
        });
//        itemcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT
//                        || actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_NULL) {
//
//                }
//          return false;  }
//        });
        if (serialsActive == 0) {
            itemcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Log.e("afterTextChanged","getCheckQty"+appSettings.get(0).getCheckQty());

                    if (appSettings.get(0).getCheckQty().equals("1")) { ///Qty Checker Active //////B
                        if (editable.toString().length() != 0) {


                            Log.e("itemcode===", "aaaaaa");
                            Log.e("itemcode===", editable.toString());
                            From = fromSpinner.getSelectedItem().toString();
                            To = toSpinner.getSelectedItem().toString();
                            FromNo = From.substring(0, From.indexOf(" "));
                            ToNo = To.substring(0, To.indexOf(" "));

                            ReplacementModel replacementModel = new ReplacementModel();

                            replacementModel.setTransNumber(max + "");
                            replacementModel.setFromName(From);
                            replacementModel.setIsPosted("0");
                            replacementModel.setToName(To);
                            replacementModel.setFrom(FromNo);
                            replacementModel.setDeviceId(deviceId);
                            replacementModel.setTo(ToNo);
                            replacementModel.setZone("");
                            replacementModel.setReplacementDate(generalMethod.getCurentTimeDate(1));
                            replacementModel.setItemcode(itemcode.getText().toString());
                            ItemsUnit itemsUnit=my_dataBase.itemsUnitDao().getItemUnit2(itemcode.getText().toString());
                            if(itemsUnit!=null){
                                replacementModel.setItemcode(itemsUnit.getITEMOCODE());
                                replacementModel.setUNITBARCODE(itemsUnit.getITEMBARCODE());

                            }

//                        ReplacementModel replacementModel1 = null;
//                        List<ReplacementModel> replacementModels1 = my_dataBase.replacementDao().isItemExist(itemcode.getText().toString());
//                        if (replacementModels1.size() != 0) {
//                            replacementModel1 = replacementModels1.get(replacementModels1.size() - 1);
//                        }

                            if (ExistsInLocallist(replacementModel)) {

                                itemcode.setError(null);
                               //55555555555555555555
                                Log.e(" Case1 ", "Exists in Local List:");
                                if ((Integer.parseInt(replacementlist.get(position).getAvailableQty())) > 0) {

                                    int sum = Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");

                                    replacementlist.get(position).setAvailableQty(String.valueOf((Integer.parseInt(replacementlist.get(position).getAvailableQty())) - 1));
                                    replacementModel.setAvailableQty(String.valueOf((Integer.parseInt(replacementlist.get(position).getAvailableQty())) - 1));

                                    replacementlist.get(position).setRecQty(sum + "");
                                    replacementModel.setRecQty(sum + "");

                                 String numifitem=   getCountOfItems(   replacementlist.get(position).getItemcode(), replacementlist.get(position).getUnitID());
                                    replacementlist.get(position).setCal_Qty((Double.parseDouble( replacementlist.get(position).getRecQty())*Double.parseDouble(numifitem))+"");



                                    my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty(), max + "", replacementlist.get(position).getCal_Qty());
                                    my_dataBase.replacementDao().updateAvailableQTY(max + "", replacementlist.get(position).getItemcode(), replacementlist.get(position).getAvailableQty());
                                    colorlastrow.setText(position + "");
                                    fillAdapter();
                                    Log.e("case1", "case1,qty enough");
                                    save.setEnabled(true);


                                } else {
                                    showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.no_enough_amount), "");
                                    itemcode.setText("");
                                }


//                            if (ExistsInLocallist(replacementModel)) {
//                                int sum = Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
//                                replacementlist.get(position).setRecQty(sum + "");
//                                my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty(), replacementlist.get(position).getTransNumber());
//
//                                Log.e("hereposition===", position + "");
//                                colorlastrow.setText(position + "");
//                                //  colorData.setText(position+"");
//
//                                fillAdapter();
//                                itemcode.setText("");
//                                Log.e("case1", "case1");
//                                save.setEnabled(true);
//                            }

                            } else {
                                replacementModel.setItemcode(itemcode.getText().toString());
                                ItemsUnit itemsUnit1=my_dataBase.itemsUnitDao().getItemUnit2(itemcode.getText().toString());
                                if(itemsUnit1!=null){
                                    replacementModel.setItemcode(itemsUnit1.getITEMOCODE());
                                    replacementModel.setUNITBARCODE(itemsUnit1.getITEMBARCODE());

                                }
                                if (ExsitsInItemlist(replacementModel.getItemcode())) {

                                    itemcode.setError(null);
                                    Log.e(" Case3 ", "Not in local but in ItemList");

                                    importData.getItemQty(editable.toString(), FromNo, new ImportData.GetItemQtyCallBack() {
                                        @Override
                                        public void onResponse(String qty) {
                                            Log.e("QTY Response ", qty);
                                      //  qty = "40";
                                            replacementModel.setAvailableQty(qty);
                                            double qtyInt=1;
                                            try {
                                                qtyInt=Double.parseDouble(qty);

                                                if (qtyInt > 0)
                                                {
                                                    Log.e("case3+++", "qtyInt>0");
                                                    replacementModel.setAvailableQty(((int)qtyInt - 1)+"");
                                                    Log.e("getItemNameA", Item.getItemNameA()+"");
                                                    Log.e("getItemOcode", Item.getItemOcode()+"");
                                                    Log.e("getItemNCode", Item.getItemNCode()+"");

                                                    replacementModel.setItemname(Item.getItemNameA());
                                                    replacementModel.setRecQty("1");
                                                    replacementModel.setCal_Qty("1");
                                                    replacementModel.setUnitID("One Unit");
                                                    replacementlist.add(0, replacementModel);
                                                    SaveRow(replacementModel);
                                                    colorlastrow.setText("0");
                                                    fillAdapter();
                                                    Log.e("case3", "case3");
                                                    save.setEnabled(true);

                                                    fromSpinner.setEnabled(false);
                                                    toSpinner.setEnabled(false);


                                                } else {
                                                    Log.e("case3+++", "caseqty<0");
                                                    showSweetDialog(MainActivity.this, 0, getResources().getString(R.string.no_enough_amount), "");
                                                    itemcode.setText("");
                                                }
                                            }catch (Exception e){
                                                Log.e("getItemQty",""+e.getMessage()+"\t"+qtyInt);

                                            }



                                        }

                                        @Override
                                        public void onError(String error) {
                                            showSweetDialog(MainActivity.this, 3, "Error!", getString(R.string.checkConnection));
                                            itemcode.setText("");
                                        }
                                    });


                                } else {
                                    //new edit by aya
                                    if (Exsitsin_itemswitchlist(itemcode.getText().toString())){
                                        itemcode.setError(null);
                                        Log.e("Exsitsin_itemswitchlist", "yes");
                                        importData.getItemQty(editable.toString(), FromNo, new ImportData.GetItemQtyCallBack() {
                                            @Override
                                            public void onResponse(String qty) {
                                                Log.e("case4++", "case4");
                                                Log.e("QTY Response ", qty);
                                  //      qty = "50";
                                                replacementModel.setAvailableQty(qty);
                                                replacementModel.setItemcode(  itemSwitch.getItemOCode());
                                                ItemsUnit itemsUnit=my_dataBase.itemsUnitDao().getItemUnit2(itemcode.getText().toString());
                                                if(itemsUnit!=null){
                                                    replacementModel.setItemcode(itemsUnit.getITEMOCODE());
                                                    replacementModel.setUNITBARCODE(itemsUnit.getITEMBARCODE());

                                                }
                                                double qtyInt=1;
                                                try {
                                                    qtyInt=Double.parseDouble(qty);

                                                    if (qtyInt > 0)
                                                    {
                                                        Log.e("case4+++", "case qtyInt > 0");
                                                        replacementModel.setAvailableQty(((int)qtyInt - 1)+"");
                                                        Log.e("NAMEA", itemSwitch.getItemNameA()+"");
                                                        Log.e("OCODE", itemSwitch.getItemOCode()+"");
                                                        Log.e("NCODE", itemSwitch.getItemNCode()+"");
                                                        String itemname=  my_dataBase.itemDao().getitemname( itemSwitch.getItemOCode());
                                                     if(itemname!=null)   replacementModel.setItemname(itemname);
                                                     else
                                                         replacementModel.setItemname("");
                                                        replacementModel.setRecQty("1");
                                                        replacementModel.setUnitID("One Unit");
                                                        String numifitem=   getCountOfItems(   replacementModel.getItemcode(), replacementModel.getUnitID());
                                                        replacementModel.setCal_Qty(""+Double.parseDouble(replacementModel.getRecQty())*Double.parseDouble(numifitem));



                                                        replacementlist.add(0, replacementModel);
                                                        SaveRow(replacementModel);
                                                        colorlastrow.setText("0");
                                                        fillAdapter();
                                                        Log.e("case4", "case4");
                                                        save.setEnabled(true);

                                                        fromSpinner.setEnabled(false);
                                                        toSpinner.setEnabled(false);


                                                    } else {
                                                        Log.e("case4+++", "case qtyInt < 0");
                                                        showSweetDialog(MainActivity.this, 0, getResources().getString(R.string.no_enough_amount), "");
                                                        itemcode.setText("");
                                                    }
                                                }catch (Exception e){
                                                    Log.e("getItemQty",""+e.getMessage()+"\t"+qtyInt);

                                                }



                                            }

                                            @Override
                                            public void onError(String error) {
                                                showSweetDialog(MainActivity.this, 3, "Error!", getString(R.string.checkConnection));
                                                itemcode.setText("");
                                            }
                                        });
                                    }else {
                                        Log.e(" Case5 ", "Not Exist in ItemList and not in itemswitch, Invalid code!");
                                        itemcode.setError("Invalid Code");
                                        showErrorCode(itemcode.getText().toString());
                                        itemcode.setText("");}
                                }

//                            Log.e("case4", "case4");


                            }

                        }
                    } else { ///Qty Checker Inactive  //////B

                        if (editable.toString().length() != 0) {

                            {
                                Log.e("case6+++", "case");
                                Log.e("itemcode===", "aaaaaa");
                                Log.e("itemcode===", editable.toString());
                                From = fromSpinner.getSelectedItem().toString();
                                To = toSpinner.getSelectedItem().toString();
                                FromNo = From.substring(0, From.indexOf(" "));
                                ToNo = To.substring(0, To.indexOf(" "));

                                ReplacementModel replacementModel = new ReplacementModel();

                                replacementModel.setTransNumber(max + "");
                                replacementModel.setFromName(From);
                                replacementModel.setIsPosted("0");
                                replacementModel.setToName(To);
                                replacementModel.setFrom(FromNo);
                                replacementModel.setDeviceId(deviceId);
                                replacementModel.setTo(ToNo);
                                replacementModel.setZone("");
                                replacementModel.setReplacementDate(generalMethod.getCurentTimeDate(1));
                                replacementModel.setItemcode(itemcode.getText().toString());
                                ItemsUnit itemsUnit=my_dataBase.itemsUnitDao().getItemUnit2(itemcode.getText().toString());
                                if(itemsUnit!=null){
                                    replacementModel.setItemcode(itemsUnit.getITEMOCODE());
                                    replacementModel.setUNITBARCODE(itemsUnit.getITEMBARCODE());

                                }

//                        ReplacementModel replacementModel1 = null;
//                        List<ReplacementModel> replacementModels1 = my_dataBase.replacementDao().isItemExist(itemcode.getText().toString());
//                        if (replacementModels1.size() != 0) {
//                            replacementModel1 = replacementModels1.get(replacementModels1.size() - 1);
//                        }

                                if (ExistsInLocallist(replacementModel)) {
                                    //new edit by ayah 4/8/2022

                                    itemcode.setError(null);
                                    if(appSettings.get(0).getRawahneh_add_item().equals("1")) {

                                        Dialog qtyDialog = new Dialog(MainActivity.this);
                                        qtyDialog.setContentView(R.layout.add_qty_dialog);
                                        qtyDialog.setCancelable(true);
                                        qtyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                        DisplayMetrics dm = new DisplayMetrics();
                                        MainActivity.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                        int width = dm.widthPixels;

                                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                        layoutParams.copyFrom(qtyDialog.getWindow().getAttributes());
                                        layoutParams.width = (int) (width / 1.5);
                                        qtyDialog.getWindow().setAttributes(layoutParams);

                                        EditText qtyEdt = qtyDialog.findViewById(R.id.qtyEdt);
                                        Button confirmBtn = qtyDialog.findViewById(R.id.confirmBtn);
                                        Button cancelBtn = qtyDialog.findViewById(R.id.cancelBtn);

                                        qtyDialog.show();

                                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                qtyDialog.dismiss();
                                            }
                                        });
                                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String qty = qtyEdt.getText().toString().trim();
                                                if (qty.equals("0") || qty.equals(""))
                                                    qtyEdt.setError("Invalid number");

                                                itemcode.setError(null);

                                                Log.e(" Case6 ", "Exists in Local List:");

                                                int sum = Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt(qty);

                                                replacementlist.get(position).setRecQty(sum + "");
                                                replacementModel.setRecQty(sum + "");

                                                String numifitem=   getCountOfItems( replacementlist.get(position).getItemcode(), replacementlist.get(position).getUnitID());
                                                replacementlist.get(position).setCal_Qty(""+Double.parseDouble( replacementlist.get(position).getRecQty())*Double.parseDouble(numifitem));


                                                my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty(), max + "", replacementlist.get(position).getCal_Qty());
                                                colorlastrow.setText(position + "");
                                                fillAdapter();
                                                Log.e("case1", "case1");
                                                save.setEnabled(true);
                                                qtyDialog.dismiss();
                                            }
                                        });
                                    }

                                   else {
                                        itemcode.setError(null);

                                        Log.e(" Case6 ", "rawqty==0,Exists in Local List:");

                                        int sum = Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");

                                        replacementlist.get(position).setRecQty(sum + "");
                                        replacementModel.setRecQty(sum + "");
                                        String numifitem=   getCountOfItems( replacementlist.get(position).getItemcode(), replacementlist.get(position).getUnitID());
                                        replacementlist.get(position).setCal_Qty(""+Double.parseDouble( replacementlist.get(position).getRecQty())*Double.parseDouble(numifitem));


                                        my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty(), max + "",replacementlist.get(position).getCal_Qty());
                                        colorlastrow.setText(position + "");
                                        fillAdapter();

                                        save.setEnabled(true);
                                    }

                                } else {
                                    replacementModel.setItemcode(itemcode.getText().toString());
                                    ItemsUnit itemsUnit1=my_dataBase.itemsUnitDao().getItemUnit2(itemcode.getText().toString());
                                    if(itemsUnit1!=null){
                                        replacementModel.setItemcode(itemsUnit1.getITEMOCODE());
                                        replacementModel.setUNITBARCODE(itemsUnit1.getITEMBARCODE());

                                    }

                                    if (ExsitsInItemlist(replacementModel.getItemcode())) {
                                         //new edit by ayah 4/8/2022
                                        itemcode.setError(null);
                                        Log.e(" Case7 ", "Exists in item List");
                                        if(appSettings.get(0).getRawahneh_add_item().equals("1")) {

                                            Log.e(" Case7 ", "rawqrt==1 Exists in item List");
                                            Dialog qtyDialog = new Dialog(MainActivity.this);
                                            qtyDialog.setContentView(R.layout.add_qty_dialog);
                                            qtyDialog.setCancelable(true);
                                            qtyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            DisplayMetrics dm = new DisplayMetrics();
                                            MainActivity.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                            int width = dm.widthPixels;

                                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                            layoutParams.copyFrom(qtyDialog.getWindow().getAttributes());
                                            layoutParams.width = (int) (width / 1.5);
                                            qtyDialog.getWindow().setAttributes(layoutParams);

                                            EditText qtyEdt = qtyDialog.findViewById(R.id.qtyEdt);
                                            Button confirmBtn = qtyDialog.findViewById(R.id.confirmBtn);
                                            Button cancelBtn = qtyDialog.findViewById(R.id.cancelBtn);

                                            qtyDialog.show();

                                            cancelBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    qtyDialog.dismiss();
                                                }
                                            });
                                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String qty = qtyEdt.getText().toString().trim();
                                                    if (qty.equals("0") || qty.equals(""))
                                                        qtyEdt.setError("Invalid number");


                                                    itemcode.setError(null);
                                                    Log.e(" Case7 ", "Not in local but in ItemList");
                                                    replacementModel.setUnitID("One Unit");
                                                    replacementModel.setItemname(Item.getItemNameA());
                                                    replacementModel.setRecQty(qty);

                                                    String numifitem=   getCountOfItems( replacementModel.getItemcode(), replacementModel.getUnitID());
                                                    replacementModel.setCal_Qty(""+Double.parseDouble( replacementModel.getRecQty())*Double.parseDouble(numifitem));


                                                    replacementlist.add(0, replacementModel);
                                                    SaveRow(replacementModel);
                                                    colorlastrow.setText("0");
                                                    fillAdapter();
                                                    Log.e("case3", "case3");
                                                    save.setEnabled(true);

                                                    fromSpinner.setEnabled(false);
                                                    toSpinner.setEnabled(false);

                                                    qtyDialog.dismiss();
                                                }
                                            });
                                        }
                                        else
                                        {     Log.e(" Case7 ", "rawqrt==0 Exists in item List");
                                            replacementModel.setItemname(Item.getItemNameA());
                                            replacementModel.setRecQty("1");
                                            replacementModel.setUnitID("One Unit");
                                            String numifitem=   getCountOfItems( replacementModel.getItemcode(), replacementModel.getUnitID());
                                            replacementModel.setCal_Qty(""+Double.parseDouble( replacementModel.getRecQty())*Double.parseDouble(numifitem));


                                            replacementlist.add(0, replacementModel);
                                            SaveRow(replacementModel);
                                            colorlastrow.setText("0");
                                            fillAdapter();

                                            save.setEnabled(true);

                                            fromSpinner.setEnabled(false);
                                            toSpinner.setEnabled(false);
                                        }

                                    } else {

                                        //new edit by aya
                                        if (Exsitsin_itemswitchlist(replacementModel.getItemcode()))
                                        {            Log.e(" Case8", " Exists in itemswitch List");
                                            itemcode.setError(null);

                                            replacementModel.setItemcode(itemSwitch.getItemOCode());
                                            ItemsUnit itemsUnit2=my_dataBase.itemsUnitDao().getItemUnit2(itemcode.getText().toString());
                                            if(itemsUnit2!=null){
                                                replacementModel.setItemcode(itemsUnit2.getITEMOCODE());
                                                replacementModel.setUNITBARCODE(itemsUnit2.getITEMBARCODE());

                                            }
                                            String itemname=  my_dataBase.itemDao().getitemname( itemSwitch.getItemOCode());

                                            if(itemname!=null)   replacementModel.setItemname(itemname);
                                            else
                                                replacementModel.setItemname("");
                                            replacementModel.setRecQty("1");
                                            replacementModel.setUnitID("One Unit");
                                            String numifitem=   getCountOfItems( replacementModel.getItemcode(), replacementModel.getUnitID());
                                            replacementModel.setCal_Qty(""+Double.parseDouble( replacementModel.getRecQty())*Double.parseDouble(numifitem));

                                            replacementlist.add(0, replacementModel);
                                            SaveRow(replacementModel);
                                            colorlastrow.setText("0");
                                            fillAdapter();

                                            save.setEnabled(true);

                                            fromSpinner.setEnabled(false);
                                            toSpinner.setEnabled(false);
                                        }else{
                                            Log.e(" Case9", "Not Exist in ItemList, Invalid code!");
                                            itemcode.setError("InValid Code");
                                            showErrorCode(itemcode.getText().toString());
                                            itemcode.setText("");
                                        }
                                    }

                                }

                            }

                        }

                    }


                }

            });
        }
        else { /////Serials

            itemcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().trim().length() != 0) {

                        {
                          item_num=itemcode.getText().toString().trim();
                            Log.e("itemcode===", "aaaaaa");
                            Log.e("itemcode===", s.toString());

                            if (ExsitsInItemlist(itemcode.getText().toString().trim())) {

                                itemcode.setError(null);
                                Log.e(" Case1 ", "Exists in ItemList");

                                transNo = max;
                                deviceId = my_dataBase.settingDao().getallsetting().get(0).getDeviceId();

                                if (item_has_serial(itemcode.getText().toString().trim())) {

                                    Dialog dialog = new Dialog(MainActivity.this);
                                    dialog.setContentView(R.layout.item_serials_layout);
                                    dialog.setCancelable(true);
                                  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    DisplayMetrics dm = new DisplayMetrics();
                                    MainActivity.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                    int width = dm.widthPixels;

                                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                                    layoutParams.width = (int) (width / 1.24);
                                    dialog.getWindow().setAttributes(layoutParams);

                                    TextView icClose = dialog.findViewById(R.id.icClose);
                                    TextView tvItemName = dialog.findViewById(R.id.tvItemName);
                                    TextView tvItemCode = dialog.findViewById(R.id.tvItemCode);
                                    etSerial = dialog.findViewById(R.id.etSerial);
                                    TextView icScan = dialog.findViewById(R.id.icScan);
                                    TextView icEdit = dialog.findViewById(R.id.icEdit);
                                    rvSerialTransfers = dialog.findViewById(R.id.rvSerialTransfers);
                                    Button btnAdd = dialog.findViewById(R.id.btnAdd);
                                    tvTotal = dialog.findViewById(R.id.tvTotal);
                                    TextView icDeleteAll = dialog.findViewById(R.id.icDeleteAll);

                                    dialog.show();


                                    serialTransfers = (ArrayList<ItemSerialTransfer>) my_dataBase.serialTransfersDao().getAllAdded(s.toString().trim(), String.valueOf(transNo));
                                    updateAdapter();

                                    etSerial.requestFocus();
                                    etSerial.setEnabled(false);
                                    etSerial.setClickable(true);
                                    etSerial.setOnEditorActionListener((v17, actionId, event) -> {
                                        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                                                || actionId == EditorInfo.IME_NULL) {

                                            if (etSerial.getText().toString().trim().equals(""))

                                                etSerial.requestFocus();

                                        }

                                        return true;
                                    });

                                    icClose.setOnClickListener(v1 -> dialog.dismiss()
                                    );
                                    tvItemName.setText(Item.getItemNameA());
                                    tvItemCode.setText(itemcode.getText().toString().trim());

                                    icScan.setOnClickListener(v14 -> openSmallCapture(6));

                                    icEdit.setOnClickListener(v13 -> openEditDialog());


                                    etSerial.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {
                                            if (!s.toString().trim().equals("")) {

                                                String code = s.toString().replaceAll("\\s+", "");
                                                Log.e("SerialScanned", code);
                                                if (!isRepeated(code)) {

                                                    int serialValidation = existInItemSerialList(item_num, code);

                                                    if (serialValidation == 1) {
                                                        Log.e("case1--", "case1");
                                                        ItemSerialTransfer serialTransfer =
                                                                new ItemSerialTransfer(String.valueOf(transNo),
                                                                        deviceId, itemcode.getText().toString().trim(), code.trim(),
                                                                        (new GeneralMethod(MainActivity.this)).getCurentTimeDate(1),
                                                                        fromSpinner.getSelectedItem().toString().substring(0, (fromSpinner.getSelectedItem().toString().indexOf(" "))),
                                                                        toSpinner.getSelectedItem().toString().substring(0, (toSpinner.getSelectedItem().toString().indexOf(" ")))
                                                                );

                                                        if (ExistsInRepList(itemcode.getText().toString()))
                                                        {       serialTransfer.setVSerial((replacementlist.size() - (repPosition + 1)) + 1);

                                                            Log.e("case2--", "case2");
                                                        }
                                                        else {
                                                            Log.e("case3--", "case3");
                                                            if (replacementlist.size() == 0)
                                                                serialTransfer.setVSerial(1);
                                                            else
                                                                serialTransfer.setVSerial(replacementlist.size() + 1);
                                                        }

                                                        etSerial.setError(null);
                                                        serialTransfers.add(serialTransfer);
                                                        my_dataBase.serialTransfersDao().insert(serialTransfer);
                                                        updateAdapter();


//                                                my_dataBase.serialsDao().addToRep(transNo + "", deviceId, s.toString().trim());
                                                        final Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if (ExistsInRepList(itemcode.getText().toString())) {
                                                                    Log.e("case4--", "case4");
                                                                    int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + 1;
                                                                    replacementlist.get(repPosition).setRecQty(String.valueOf(sumQty));

                                                                    String numifitem=   getCountOfItems( replacementlist.get(repPosition).getItemcode(), replacementlist.get(repPosition).getUnitID());
                                                                    replacementlist.get(repPosition).setCal_Qty("" +Double.parseDouble(replacementlist.get(repPosition).getRecQty())*Double.parseDouble(numifitem));


                                                                    my_dataBase.replacementDao().updateQTY(itemcode.getText().toString().trim(), String.valueOf(sumQty), String.valueOf(transNo),replacementlist.get(repPosition).getCal_Qty());

                                                                    fillAdapter();

                                                                    replacmentRecycler.smoothScrollToPosition(repPosition);
                                                                    colorlastrow.setText(repPosition + "");

//                                                    for (int i = 0; i < serialTransfers.size(); i++) {
//                                                        serialTransfers.get(i).setAdded("1");
//                                                    }


                                                                } else {
                                                                    Log.e("case5--", "case5");
                                                                    ReplacementModel replacementModel = new ReplacementModel();

                                                                    replacementModel.setFrom(fromSpinner.getSelectedItem().toString().substring(0, (fromSpinner.getSelectedItem().toString().indexOf(" "))));
                                                                    replacementModel.setTo(toSpinner.getSelectedItem().toString().substring(0, (toSpinner.getSelectedItem().toString().indexOf(" "))));
                                                                    replacementModel.setItemcode(itemcode.getText().toString());
                                                                    replacementModel.setIsPosted("0");
                                                                    replacementModel.setReplacementDate((new GeneralMethod(MainActivity.this)).getCurentTimeDate(1));
                                                                    replacementModel.setItemname(Item.getItemNameA());
                                                                    replacementModel.setTransNumber(transNo + "");
                                                                    replacementModel.setDeviceId(deviceId);
                                                                    replacementModel.setRecQty(1 + "");
                                                                    replacementModel.setUnitID("One Unit");
                                                                    String numifitem=   getCountOfItems( replacementModel.getItemcode(), replacementModel.getUnitID());
                                                                    replacementModel.setCal_Qty("" +Double.parseDouble( replacementModel.getRecQty())*Double.parseDouble(numifitem));

                                                                    replacementModel.setFromName(fromSpinner.getSelectedItem().toString());
                                                                    replacementModel.setToName(toSpinner.getSelectedItem().toString());
                                                                    replacementModel.setZone("");

                                                                    replacementlist.add(0, replacementModel);
                                                                    my_dataBase.replacementDao().insert(replacementModel);

                                                                    fillAdapter();

                                                                    //    replacmentRecycler.smoothScrollToPosition(0);
                                                                    colorlastrow.setText("0");

                                                                    save.setEnabled(true);

                                                                    fromSpinner.setEnabled(false);
                                                                    toSpinner.setEnabled(false);

//                                                    for (int i = 0; i < serialTransfers.size(); i++) {
//                                                        serialTransfers.get(i).setAdded("1");
//                                                    }

                                                                }
                                                                openSmallCapture(6);
                                                            }

                                                        }, 500);

                                                    } else if (serialValidation == 0) {

                                                        showSweetDialog(MainActivity.this, 3, getString(R.string.invalid_serial), getString(R.string.serialNotFound));
                                                    }
                                                } else {
                                                    showSweetDialog(MainActivity.this, 3, getString(R.string.uniqueSerial), getString(R.string.uniqueSerialMsg));
                                                }
                                                etSerial.setText("");
                                            }

                                        }
                                    });

//                                etSerial.setOnKeyListener((v15, keyCode, event) -> {
////                        if (keyCode == KeyEvent.KEYCODE_BACK) {
////                            onBackPressed();
////                        }
//                                    if (keyCode != KeyEvent.KEYCODE_ENTER) {
//
//                                        {
//                                            if (event.getAction() == KeyEvent.ACTION_UP)
//                                                if (etSerial.getText().toString().equals(""))
//                                                    etSerial.requestFocus();
//
//                                            return true;
//                                        }
//                                    }
//                                    return false;
//                                });

                                    icDeleteAll.setOnClickListener(v16 -> {

                                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText(getString(R.string.deleteAll))
                                                .setContentText(getString(R.string.deleteAllMsg))
                                                .setConfirmButton(getString(R.string.yes), sweetAlertDialog -> {
                                                    if (serialTransfers.size() > 0) {
                                                        serialTransfers.clear();
                                                        my_dataBase.serialTransfersDao().deleteAllAdded(
                                                                itemcode.getText().toString().trim(),
                                                                transNo + ""
                                                        );
                                                        updateAdapter();
                                                        my_dataBase.replacementDao().deleteReplacement(itemcode.getText().toString(),
                                                                fromSpinner.getSelectedItem().toString().substring(0, fromSpinner.getSelectedItem().toString().indexOf(" ")), toSpinner.getSelectedItem().toString().substring(0, toSpinner.getSelectedItem().toString().indexOf(" ")), max + "");
                                                        if (ExistsInRepList(itemcode.getText().toString())) {
                                                            replacementlist.remove(repPosition);
                                                        }
                                                        fillAdapter();
                                                        updateAdpapter();

                                                    }
                                                    sweetAlertDialog.dismissWithAnimation();
                                                })
                                                .setCancelButton(getString(R.string.no), SweetAlertDialog::dismissWithAnimation)
                                                .show();
                                    });


                                    btnAdd.setOnClickListener(v12 -> {
//                                    if (serialTransfers.size() > 0) {
//                                        if (ExistsInRepList(s.toString().trim())) {
//
//                                            int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + serialTransfers.size();
//                                            replacementlist.get(repPosition).setRecQty(String.valueOf(sumQty));
//                                            my_dataBase.replacementDao().updateQTY(s.toString().trim(), String.valueOf(sumQty), String.valueOf(transNo));
//
//                                            replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                                            ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
//                                            replacmentRecycler.setAdapter(adapter);
//
//                                            replacmentRecycler.smoothScrollToPosition(repPosition);
//                                            colorlastrow.setText(repPosition + "");
//
//                                            for (int i = 0; i < serialTransfers.size(); i++) {
//                                                serialTransfers.get(i).setAdded("1");
//                                            }
//
//
//                                        } else {
//                                            ReplacementModel replacementModel = new ReplacementModel();
//
//                                            replacementModel.setFrom(fromSpinner.getSelectedItem().toString().substring(0, (fromSpinner.getSelectedItem().toString().indexOf(" "))));
//                                            replacementModel.setTo(toSpinner.getSelectedItem().toString().substring(0, (toSpinner.getSelectedItem().toString().indexOf(" "))));
//                                            replacementModel.setItemcode(s.toString().trim());
//                                            replacementModel.setIsPosted("0");
//                                            replacementModel.setReplacementDate((new GeneralMethod(MainActivity.this)).getCurentTimeDate(1));
//                                            replacementModel.setItemname(AllItemDBlist.get(pos).getItemName());
//                                            replacementModel.setTransNumber(transNo + "");
//                                            replacementModel.setDeviceId(deviceId);
//                                            replacementModel.setRecQty(serialTransfers.size() + "");
//                                            replacementModel.setFromName(fromSpinner.getSelectedItem().toString());
//                                            replacementModel.setToName(toSpinner.getSelectedItem().toString());
//                                            replacementModel.setZone("");
//
//                                            replacementlist.add(0, replacementModel);
//                                            my_dataBase.replacementDao().insert(replacementModel);
//
//                                            replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                                            ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
//                                            replacmentRecycler.setAdapter(adapter);
//
//                                            replacmentRecycler.smoothScrollToPosition(0);
//                                            colorlastrow.setText("0");
//
//                                            for (int i = 0; i < serialTransfers.size(); i++) {
//                                                serialTransfers.get(i).setAdded("1");
//                                            }
//
//                                        }
//                                        my_dataBase.serialsDao().addToRep(transNo + "", deviceId, s.toString().trim());

                                        dialog.dismiss();
//                                    dialog1.dismiss();
                                        save.setEnabled(true);

                                        fromSpinner.setEnabled(false);
                                        toSpinner.setEnabled(false);
                                        fillAdapter();
//                                    }

                                    });

                                } else {
                                    String rawahneh_add_item = my_dataBase.settingDao().getallsetting().get(0).getRawahneh_add_item();

                                    if (rawahneh_add_item == null) {
                                        rawahneh_add_item = "1";
                                    }
                                    Log.e("rawahneh_add_item",rawahneh_add_item+"");
                                    if (rawahneh_add_item.equals("0")) {
                                        if (ExistsInRepList(itemcode.getText().toString())) {

                                            int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + 1;
                                            replacementlist.get(repPosition).setRecQty(String.valueOf(sumQty));
                                            String numifitem=   getCountOfItems(replacementlist.get(repPosition).getItemcode(), replacementlist.get(repPosition).getUnitID());
                                            replacementlist.get(repPosition).setCal_Qty(""+Double.parseDouble( replacementlist.get(repPosition).getRecQty())*Double.parseDouble(numifitem));

                                            my_dataBase.replacementDao().updateQTY(itemcode.getText().toString().trim(), String.valueOf(sumQty), String.valueOf(transNo),replacementlist.get(repPosition).getCal_Qty());
                                            fillAdapter();
                                            replacmentRecycler.smoothScrollToPosition(repPosition);
                                            colorlastrow.setText(repPosition + "");

//                                                    for (int i = 0; i < serialTransfers.size(); i++) {
//                                                        serialTransfers.get(i).setAdded("1");
//                                                    }


                                        } else {

                                            ReplacementModel replacementModel = new ReplacementModel();

                                            replacementModel.setFrom(fromSpinner.getSelectedItem().toString().substring(0, (fromSpinner.getSelectedItem().toString().indexOf(" "))));
                                            replacementModel.setTo(toSpinner.getSelectedItem().toString().substring(0, (toSpinner.getSelectedItem().toString().indexOf(" "))));
                                            replacementModel.setItemcode(itemcode.getText().toString());
                                            replacementModel.setIsPosted("0");
                                            replacementModel.setReplacementDate((new GeneralMethod(MainActivity.this)).getCurentTimeDate(1));
                                            replacementModel.setItemname(Item.getItemNameA());
                                            replacementModel.setTransNumber(transNo + "");
                                            replacementModel.setDeviceId(deviceId);
                                            replacementModel.setRecQty(1 + "");
                                            replacementModel.setUnitID("One Unit");
                                            String numifitem=   getCountOfItems( replacementModel.getItemcode(), replacementModel.getUnitID());
                                            replacementModel.setCal_Qty(""+Double.parseDouble( replacementModel.getRecQty())*Double.parseDouble(numifitem));


                                            replacementModel.setFromName(fromSpinner.getSelectedItem().toString());
                                            replacementModel.setToName(toSpinner.getSelectedItem().toString());
                                            replacementModel.setZone("");

                                            replacementlist.add(0, replacementModel);
                                            my_dataBase.replacementDao().insert(replacementModel);

                                            fillAdapter();

                                            //       replacmentRecycler.smoothScrollToPosition(0);
                                            colorlastrow.setText("0");


                                            save.setEnabled(true);

                                            fromSpinner.setEnabled(false);
                                            toSpinner.setEnabled(false);
                                            Log.e("endd","end");
                                            fillAdapter();//5555
                                        }
                                        if(dialog1!=null)        dialog1.dismiss();
                                    }
                                    else {
                               Log.e("add_qty_dialog","add_qty_dialog");
                                        Dialog qtyDialog = new Dialog(MainActivity.this);
                                        qtyDialog.setContentView(R.layout.add_qty_dialog);
                                        qtyDialog.setCancelable(true);
                                        qtyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                        DisplayMetrics dm = new DisplayMetrics();
                                        MainActivity.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                        int width = dm.widthPixels;

                                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                        layoutParams.copyFrom(qtyDialog.getWindow().getAttributes());
                                        layoutParams.width = (int) (width / 1.5);
                                        qtyDialog.getWindow().setAttributes(layoutParams);

                                        EditText qtyEdt = qtyDialog.findViewById(R.id.qtyEdt);
                                        Button confirmBtn = qtyDialog.findViewById(R.id.confirmBtn);
                                        Button cancelBtn = qtyDialog.findViewById(R.id.cancelBtn);

                                        qtyDialog.show();

                                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                qtyDialog.dismiss();
                                            }
                                        });

                                        if (ExistsInRepList(itemcode.getText().toString())) {

//                                                int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + 1;

                                            qtyEdt.setText(replacementlist.get(repPosition).getRecQty());


                                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Log.e("onClickconfirm==","case1");
                                                    String qty = qtyEdt.getText().toString().trim();
                                                    if (qty.equals("0") || qty.equals(""))
                                                        qtyEdt.setError("Invalid number");

                                                    else {

                                                        replacementlist.get(repPosition).setRecQty(qty);

                                                        String numifitem=   getCountOfItems( replacementlist.get(repPosition).getItemcode(), replacementlist.get(repPosition).getUnitID());
                                                        replacementlist.get(repPosition).setCal_Qty(""+Double.parseDouble( replacementlist.get(repPosition).getRecQty())*Double.parseDouble(numifitem));

                                                        my_dataBase.replacementDao().updateQTY(itemcode.getText().toString().trim(), qty, String.valueOf(transNo),  replacementlist.get(repPosition).getCal_Qty());

                                                        fillAdapter();

                                                        replacmentRecycler.smoothScrollToPosition(repPosition);
                                                        colorlastrow.setText(repPosition + "");
                                                        Log.e("onClickconfirm==","case2");
                                                        qtyDialog.dismiss();
                                                        fillAdapter();//5555
                                                    }

                                                }
                                            });

                                        } else {

                                            qtyEdt.setText("1");

                                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    String qty = qtyEdt.getText().toString().trim();
                                                    if (qty.equals("0") || qty.equals(""))
                                                        qtyEdt.setError("Invalid number");

                                                    else {
                                                        Log.e("onClickconfirm==","case3");
                                                        ReplacementModel replacementModel = new ReplacementModel();

                                                        replacementModel.setFrom(fromSpinner.getSelectedItem().toString().substring(0, (fromSpinner.getSelectedItem().toString().indexOf(" "))));
                                                        replacementModel.setTo(toSpinner.getSelectedItem().toString().substring(0, (toSpinner.getSelectedItem().toString().indexOf(" "))));
                                                        replacementModel.setItemcode(itemcode.getText().toString());
                                                        replacementModel.setIsPosted("0");
                                                        replacementModel.setReplacementDate((new GeneralMethod(MainActivity.this)).getCurentTimeDate(1));
                                                        replacementModel.setItemname(Item.getItemNameA());
                                                        replacementModel.setTransNumber(transNo + "");
                                                        replacementModel.setDeviceId(deviceId);
                                                        replacementModel.setRecQty(qty);
                                                        replacementModel.setUnitID("One Unit");
                                                        String numifitem=   getCountOfItems(   replacementModel.getItemcode(), replacementModel.getUnitID());
                                                        replacementModel.setCal_Qty(""+Double.parseDouble( replacementModel.getRecQty())*Double.parseDouble(numifitem));


                                                        replacementModel.setFromName(fromSpinner.getSelectedItem().toString());
                                                        replacementModel.setToName(toSpinner.getSelectedItem().toString());
                                                        replacementModel.setZone("");

                                                        replacementlist.add(0, replacementModel);
                                                        my_dataBase.replacementDao().insert(replacementModel);

//                                                        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                                                        ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
//                                                        replacmentRecycler.setAdapter(adapter);
                                                        fillAdapter();

                                                        Log.e("replacementlist=====",replacementlist.size()+"");

                                                        //            replacmentRecycler.smoothScrollToPosition(0);
                                                        colorlastrow.setText("0");


                                                        save.setEnabled(true);

                                                        fromSpinner.setEnabled(false);
                                                        toSpinner.setEnabled(false);

                                                        qtyDialog.dismiss();
                                                        fillAdapter();//5555
                                                    }

                                                }
                                            });

                                        }

                                    }

                                }

                            }
                            else if (existsInSerialsList(itemcode.getText().toString().trim())) {
                                Log.e(" Case2 ", "Exist in serial list");

                            } else if (existBarcode(itemcode.getText().toString().trim())) {
                                Log.e(" Case3 ", "Exist in barcode list");

                            } else {
                                Log.e(" Case4 ", "Not Exist in any list, Invalid code!");
                                itemcode.setError("InValid Code");
                                showErrorCode(itemcode.getText().toString());
                                itemcode.setText("");
                            }

                        }

                    }
                }
            });

        }

        itemUnit_text=findViewById(R.id.itemUnit_text);
        if(serialsActive==1){
            itemUnit_text.setVisibility(View.GONE);
        }
    }
    ArrayAdapter<String> adapter_voucher;
    private void openOrderDialog() {

            final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.select_voucher);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());

        ProgressBar progressVoucher=dialog.findViewById(R.id.progressVoucher);
        ListView listview_area = dialog.findViewById(R.id.listview_area);
         getResponce=dialog.findViewById(R.id.getResponce);
        Set<String> setVoucher=new HashSet<>();
        List<String >listVoucherNo=new ArrayList<>();
        adapter_voucher = new ArrayAdapter<String>(getBaseContext(),
                R.layout.text_custom,listVoucherNo);

        listview_area.setAdapter(adapter_voucher);

        getResponce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if(editable.toString().length()!=0){
                    if(editable.toString().equals("fill")){
                        listVoucherNo.clear();
                        setVoucher.clear();
                        Log.e("setVoucher","fill_getResponce="+setVoucher.size()+"\t"+voucherlist.size());
                        for (int i=0;i< voucherlist.size();i++){

                           setVoucher.add(voucherlist.get(i).getTransNumber());

//                            voucher_no_list.add(setVoucher);
                        }
                        Iterator<String> it = setVoucher.iterator();
                        while(it.hasNext()) {
                            String value = it.next();
                            listVoucherNo.add(value);
                            System.out.println(value);
                        }
                        adapter_voucher = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_list_item_single_choice,listVoucherNo);

                        listview_area.setAdapter(adapter_voucher);
                        listview_area.setVisibility(View.VISIBLE);
                        progressVoucher.setVisibility(View.GONE);

                    }
                }
            }
        });
        listview_area.setVisibility(View.GONE);
        progressVoucher.setVisibility(View.VISIBLE);

        ImportData importData=new ImportData(MainActivity.this);
        importData.getVouchers();
            listview_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Log.e("onItemClick","="+position);
                    String voucherNo = (String) (listview_area.getItemAtPosition(position));
                    Log.e("onItemClick",""+voucherNo);
                    fillTransferModel(voucherNo);
                    dialog.dismiss();

                }
            });


            lp.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(lp);
            Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
            TextView cancelButton = (TextView) dialog.findViewById(R.id.cancel);


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    filterListLocation();

                    dialog.dismiss();


                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();


        }

    private void fillTransferModel(String voucherNo) {
       List<ReplacementModel> replacinmentlist = new ArrayList<>();
        for(int i=0;i<voucherlist.size();i++){
            if(voucherlist.get(i).getTransNumber().equals(voucherNo))
            {
                replacinmentlist.add(voucherlist.get(i));
            }
        }
        Log.e("fillTransferModel",""+replacinmentlist.size());
    }


    private boolean item_has_serial(String itemCode) {
        List<String> hasSerials = my_dataBase.itemDao().itemHasSerial(itemCode);
        Log.e("ItemHasSerial", itemCode + ": " + hasSerials.get(0));
        return hasSerials.get(0).equals("1");
    }

    private boolean existBarcode(String code) {

        List<AllItems> allItemsList = my_dataBase.itemDao().getAll();

        boolean flag = false;

        for (int t = 0; t < allItemsList.size(); t++) {

            if (allItemsList.get(t).getItemNCode().replaceAll("\\s+", "").equals(code)) {
                codeScanned = allItemsList.get(t).getItemOcode();
                itemcode.setText(codeScanned);
                flag = true;
                break;
            }

        }

        return flag;

    }

    private boolean existsInSerialsList(String code) {

        String fromNo = fromSpinner.getSelectedItem().toString().substring(0, fromSpinner.getSelectedItem().toString().indexOf(" "));
        List<SerialsModel> serialsModelList = my_dataBase.serialsDao().getSerialsInStore(fromNo);


        boolean flag = false;

        for (int s = 0; s < serialsModelList.size(); s++) {

            if (serialsModelList.get(s).getSerialNo().replaceAll("\\s+", "").trim().equals(code.trim())) {
                codeScanned = serialsModelList.get(s).getItemNo();
                itemcode.setText(codeScanned);
                flag = true;
                break;
            }

        }

        if (!flag) {

            List<SerialsModel> serials = my_dataBase.serialsDao().getAllSerials();

            for (int s = 0; s < serials.size(); s++) {

                if (serials.get(s).getSerialNo().replaceAll("\\s+", "").trim().equals(code.trim())) {
                    if (!serials.get(s).getStore().equals(fromNo)) {

                        showSweetDialog(MainActivity.this, 3, getString(R.string.this_serial_), "(" + getString(R.string.store_no_) + " " + serials.get(s).getStore() + ")");

                    }
                    break;
                }

            }

        }

        return flag;

    }


    private boolean ExsitsInItemlist(String itemcode) {

//        Log.e("ExsitsInItemlist==", "ExsitsInItemlist");
//        boolean flage = false;
//        for (int x1 = 0; x1 < AllItemDBlist.size(); x1++) {
//            if (AllItemDBlist.get(x1).getItemOcode().replaceAll("\\s+", "").trim().equals(itemcode.trim())) {
//                pos = x1;
//                Log.e("ExsitsInItemlist,,==",AllItemDBlist.get(x1).getItemOcode());
//                flage = true;
//                break;
//            } else {
//                flage = false;
//            }
//
//
//        }
//        return flage;

    Item=   my_dataBase.itemDao(). getItem(itemcode);
        if(Item==null)return false;
        return true;
    }
    private boolean Exsitsin_itemswitchlist(String itemcode) {

        Log.e("Exsitsin_itemswit", "Exsitsin_itemswitchlist="+DB_itemswitch.size());
        Log.e("Exsitsin_itemswit", "itemcode="+itemcode);
//        boolean flage = false;
//        for (int x1 = 0; x1 < DB_itemswitch.size(); x1++) {
//            //     Log.e("itemcode==", itemcode+  "    "+ DB_itemswitch.get(x1).getItem_NCODE());
//            if   (DB_itemswitch.get(x1).getItemNCode().replaceAll("\\s+", "").trim().
//                    equals(itemcode.trim()
//
//                    )) {
//                pos = x1;
//
//                flage = true;
//                break;
//            } else {
//                flage = false;
//            }
//
//
//        }
//        Log.e("Exsitsin_itemswit", "flage="+flage);
//        return flage;



        itemSwitch=my_dataBase.itemSwitchDao().getitemSwitchByNcocd(itemcode);
        if(itemSwitch==null)return false;
        return true;

    }

    private void fillSp() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);

        if (AllItemDBlist.size() == 0)
            importData.getAllItems();

    }


    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.scanZoneCode:
                readBarcode(4);

                break;
            case R.id.scanItemCode:
                readBarcode(5);


                break;
        }
    }


    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()", keyEvent.getAction() + "");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

            }

            if (i != KeyEvent.KEYCODE_ENTER) {

                {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                        switch (view.getId()) {
                      /*  case R.id.zoneedt:
                            Log.e("ZONEHERE",zone.getText().toString()+"");
                            if(!zone.getText().toString().equals(""))
                                if(searchZone(zone.getText().toString().trim()))
                                {
                                    Log.e("zone",zone.getText().toString());
                                }
                                else {
                                    zone.setError("Invalid");
                                    Log.e("elsezone",zone.getText().toString());
                                    zone.setText("");}
                            else
                                zone.requestFocus();
                            break;*/
                            case R.id.itemcodeedt:

                                if (!itemcode.getText().toString().equals("")) {

                                    //varify();


                                } else
                                    itemcode.requestFocus();
                                break;
                        }
                    return true;
                }
            }
            return false;

        }
    };

//    private void varify() {
//        {
//
//
//            fromSpinner.setEnabled(false);
//            toSpinner.setEnabled(false);
//            Log.e( "itemcodeedt ",itemcode.getText().toString()+"");
//
//            From = fromSpinner.getSelectedItem().toString();
//            To = toSpinner.getSelectedItem().toString();
//            FromNo=From.substring(0,From.indexOf(" "));
//            ToNo=To.substring(0,To.indexOf(" "));
//            Zone = convertToEnglish(zone.getText().toString().trim());
//            Itemcode = convertToEnglish(itemcode.getText().toString().trim());
//
//
//            replacement = new ReplacementModel();
//            replacement.setFrom( FromNo);
//            replacement.setTo(ToNo);
//            replacement.setZone(Zone);
//            replacement.setItemcode(Itemcode);
//            replacement.setFromName(From);
//            replacement.setToName(To);
//            replacement.setDeviceId(deviceId);
//
//            zone.setEnabled(false);
//            ReplacementModel replacementModel=my_dataBase.replacementDao().getReplacement(Itemcode,Zone, FromNo,ToNo);
//            if(replacementModel!=null) {
//                if (!CaseDuplicates(replacementModel))
//                    replacementlist.add(replacementModel);
//                save.setEnabled(true);
//            }
//
//
//            if (CaseDuplicates(replacement)) {
//                Log.e("replacementFrom",replacement.getTo());
//                Log.e("AddInCaseDuplicates","AddInCaseDuplicates");
//                //update qty in Duplicate case
//                int sum=Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
//                Log.e("aaasum ",sum+"");
//
//                if(sum<=Integer.parseInt(replacementlist.get(position).getQty()))
//                {
//                    replacementlist.get(position).setRecQty((sum+""));
//                    my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(),replacementlist.get(position).getRecQty());
//
//                    zone.setEnabled(false);
//                    itemcode.setText("");
//                    itemcode.requestFocus();
//                    fillAdapter();
//                    Log.e("heree","here2");
//                }
//                else
//                {   showSweetDialog(MainActivity.this, 3, "", getResources().getString(R.string.notvaildqty));
//
//                    fillAdapter();
//                    zone.setEnabled(false);
//                    itemcode.setText("");
//                    itemcode.requestFocus();
//                }
//
//
//            }
//
//
//            else
//            {
//                Log.e("not in db","not in db");
//
//                importData.getQty();
//
//            }
//        }
//    }


    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {


                    case R.id.zoneedt:
                        Log.e("ZONEHERE", zone.getText().toString() + "");
                        if (!zone.getText().toString().equals(""))
                            if (searchZone(zone.getText().toString().trim())) {
                                Log.e("zone", zone.getText().toString());
                            } else {
                                zone.setError("Invalid");
                                Log.e("elsezone", zone.getText().toString());
                                zone.setText("");
                            }
                        else
                            zone.requestFocus();
                        break;
                    case R.id.itemcodeedt:

                        if (!itemcode.getText().toString().equals("")) {

                            //varify();
                        } else
                            itemcode.requestFocus();
                        break;

                }


            }

            return true;
        }
    };


    /* private boolean searchZone(String codeZone) {
         Log.e("search", " searchZone1");
         Log.e("listAllZone", "" + listAllZone.size());
         zone.setError(null);
         for (int i = 0; i < listAllZone.size(); i++) {
             if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                 Log.e(" searchZone", " searchZone");
                return true;

             }
         }
    return false; }*/
    private boolean searchZone(String codeZone) {
        Log.e("search", codeZone);
        Log.e("search", " searchZone1");
        Log.e("listAllZone", "" + listAllZone.size());
        zone.setError(null);
        for (int i = 0; i < listAllZone.size(); i++) {

            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                itemcode.setEnabled(true);
                itemcode.requestFocus();
                zone.setEnabled(false);
                return true;

            }
        }

        return false;

    }
//    private String getusernum() {
//        return "";
//    }
//    private void filldata() {
//
//        Qty = recqty.getText().toString().trim();
//        if (Zone.toString().trim().equals("")) zone.setError("required");
//
//        else if (Itemcode.toString().trim().equals("")) itemcode.setError("required");
//
//
//        else if (Qty.toString().trim().equals(""))
//            recqty.setError("required");
//        else {
//
//
//            replacement.setRecQty(Qty);
//            replacement.setUserNO(UserNo);
//            Log.e("replacement","1=="+qty.getText().toString());
//            replacement.setQty( listQtyZone.get(0).getQty());
//            Log.e("replacement","2=="+replacement.getQty());
//            replacement.setIsPosted("0");
//            replacement.setUserNO(getusernum());
//            replacement.setDeviceId(deviceId);
//            replacement.setReplacementDate(generalMethod.getCurentTimeDate(1) + "");
//            Log.e("else","AddInCaseDuplicates");
//            Log.e("replacementlist.size", replacementlist.size()+"");
//            replacementlist.add(replacement);
//            Log.e("replacementlist.size", replacementlist.size()+""+replacementlist.get(0).getFrom());
//            fillAdapter();
//            SaveRow(replacement);
//        }
//
//
//
//
//    }

    private void SaveRow(ReplacementModel replacement) {
        Log.e("SaveRow", "replacement" + replacement.getDeviceId());
        my_dataBase.replacementDao().insert(replacement);
    }

    private void updateQTYOfZone() {
        String d = "";
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                {
                    listQtyZone.get(i).setQty(Integer.parseInt(listQtyZone.get(i).getQty()) - Integer.parseInt(Qty) + "");
                    d = listQtyZone.get(i).getQty();
                }
            }
        }
        Log.e(" updateQTYOfZone()", d);
    }

    public void fillAdapter() {
        Log.e(" fillAdapter", " fillAdapter"+replacementlist.size());
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
        replacmentRecycler.setAdapter(adapter);
        itemcode.setText("");
        itemcode.requestFocus();
        //  colorlastrow.setText(position + "");
        //    colorlastrow.setText((0)+"");
//        if (replacementlist.size() > 1) {
//            Log.e("position=====", position + "");
//            replacmentRecycler.smoothScrollToPosition(position);
//
//        }


    }


    public void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }

    private void openEditDialog() {
        Dialog editDialog = new Dialog(MainActivity.this);
        editDialog.setContentView(R.layout.enter_serial_layout);
        editDialog.setCancelable(true);

        EditText editText = editDialog.findViewById(R.id.editText);
        TextView icClose = editDialog.findViewById(R.id.icClose);
        Button okButton = editDialog.findViewById(R.id.okButton);

        editDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editDialog.show();
//        editText.setText(etSerial.getText());
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSerial.setText(editText.getText().toString().trim());
                editDialog.dismiss();
            }
        });
        icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });


    }

    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                i.putExtra("key", type);
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(MainActivity.this, ScanActivity.class);
            i.putExtra("key", type + "");

            startActivity(i);
            //  searchByBarcodeNo(s + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(MainActivity.this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveData(int isposted) {
        if (isposted == 1)
            for (int i = 0; i < replacementlist.size(); i++)
                replacementlist.get(i).setIsPosted("1");
        ////UNCOMMENT
//        long result[] = my_dataBase.replacementDao().insertAll(replacementlist);
//
//        if (result.length != 0) {
//        showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
//        }


    }

    private void updateAdapter() {
        serialsAdapter = new SerialsAdapter(MainActivity.this, serialTransfers);
        rvSerialTransfers.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvSerialTransfers.setAdapter(serialsAdapter);
        //  colorlastrow.setText(position + "");
        //    colorlastrow.setText((0)+"");
        if (serialTransfers.size() > 1) {
            rvSerialTransfers.smoothScrollToPosition(serialTransfers.size() - 1);
        }

        tvTotal.setText(serialTransfers.size() + "");

    }

    private boolean isRepeated(String serial) {
        allTransSerials = my_dataBase.serialTransfersDao().getAllIntrans(String.valueOf(transNo), deviceId);
        for (int i = 0; i < allTransSerials.size(); i++) {

            if (allTransSerials.get(i).getSerialNo().trim().equals(serial.replaceAll("\\s+", "").trim())) {
                return true;
            }

        }

        List<String> s = my_dataBase.serialTransfersDao().getSerialsForOther(itemcode.getText().toString());
        for (int i = 0; i < s.size(); i++) {

            if (s.get(i).replaceAll("\\s+", "").trim().equals(serial)) {
                return true;
            }

        }

        return false;
    }

    public int existInItemSerialList(String itemNo, String serialNo) {

        // 0 --> not exist serial
        // 1 --> exist & valid for the item in the selected store
        // 2 --> exist but for another item or another store
        // 3 --> serial transferred to another store

        int result = 0;

        String fromNo = fromSpinner.getSelectedItem().toString().substring(0, fromSpinner.getSelectedItem().toString().indexOf(" "));
        List<String> itemSerialsInStore = my_dataBase.serialsDao().getItemSerialsInStore(fromNo, itemNo);
        Log.e("itemSerialsInStore", itemSerialsInStore.toString() + "");

        for (int i = 0; i < itemSerialsInStore.size(); i++) {

            if (itemSerialsInStore.get(i).replaceAll("\\s+", "").trim().equals(serialNo))
                result = 1;

        }

        if (result == 1) {
            List<ItemSerialTransfer> serialTransfers1 = my_dataBase.serialTransfersDao().getAll();
            if (serialTransfers1.size() != 0) {
                for (int i = (serialTransfers1.size() - 1); i >= 0; i--) {
                    if (serialTransfers1.get(i).getSerialNo().replaceAll("\\s+", "").trim().equals(serialNo)) {
                        if (!serialTransfers1.get(i).getToStore().equals(fromNo)) {

                            result = 3;
                            showSweetDialog(MainActivity.this, 3, getString(R.string.this_serial_transferred), "(" + getString(R.string.store_no_) + " " + serialTransfers1.get(i).getToStore() + ")");

                        }
                        break;
                    }
                }
            }
        }

        if (result != 1 && result != 3) {

            List<SerialsModel> allSerials = my_dataBase.serialsDao().getAllSerials();

            for (int i = 0; i < allSerials.size(); i++) {

                if (allSerials.get(i).getSerialNo().replaceAll("\\s+", "").trim().equals(serialNo)) {

                    if (!allSerials.get(i).getStore().trim().equals(fromNo.trim()) ||
                            !allSerials.get(i).getItemNo().replaceAll("\\s+", "").trim().equals(itemNo)) {

                        result = 2;
                        Log.e("allSerials.get(i)",allSerials.get(i).getSerialNo()+"  "+serialNo+"  "+fromNo+"  "+allSerials.get(i).getStore()+"  "+allSerials.get(i).getItemNo()+"  "+itemNo);
                        showSweetDialog(MainActivity.this, 3, getString(R.string.serial_another_item), "(" + getString(R.string.store_no_) + " " + allSerials.get(i).getStore()
                                + ")\n(" + getString(R.string.itemCode_) + " " + allSerials.get(i).getItemNo() + ")");

                    }

                }

            }

        }


        Log.e("Result_serial", result + "");
        return result;

    }

    public boolean ExistsInRepList(String code) {
        boolean flag = false;

        if (replacementlist.size() != 0)
            for (int i = 0; i < replacementlist.size(); i++) {
                if (convertToEnglish(replacementlist.get(i).getItemcode()).equals(code)) {

                    repPosition = i;
                    flag = true;
                    break;
                }
            }

        return flag;
    }

    public static void updateAdpapter() {
        adapter.notifyDataSetChanged();
    }

    ///////////////B
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        menu.getItem(1).setVisible(Login.serialsActive != 0);
//
//        return super.onCreateOptionsMenu(menu);
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.goToReports: {
//                Intent i = new Intent(MainActivity.this, TransferReports.class);
//                startActivity(i);
//                break;
//            }
//            case R.id.menuImport: {
//                if (Login.serialsActive == 1) {
//                    int d = my_dataBase.serialsDao().deleteAllSerials();
//                    Log.e("DeleteSERIALS", d + "");
//                    importData.getAllSerials(new ImportData.GetSerialsCallBack() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            Log.e("responseLength", response.length() + "");
//                            allItemSerials.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    allItemSerials.add(new SerialsModel(
//                                            response.getJSONObject(i).getString("STORENO"),
//                                            response.getJSONObject(i).getString("ITEMOCODE"),
//                                            response.getJSONObject(i).getString("SERIALCODE")));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.e("allItemSerialsLength", allItemSerials.size() + "");
//                            my_dataBase.serialsDao().insertAll(allItemSerials);
//                        }
//
//                        @Override
//                        public void onError(String error) {
//
//                        }
//                    });
//                }
//                break;
//            }
//            case R.id.menuExport: {
//                Log.e("export", "clicked");
//
//                // UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();
//                exportAllData();
//                maxVochNum = my_dataBase.replacementDao().getMaxReplacementNo();
//                if (maxVochNum != null) {
//                    Log.e(" maxVochNum", maxVochNum);
//                    max = Integer.parseInt(maxVochNum) + 1;
//                }
//
//                zone.setEnabled(true);
//                zone.requestFocus();
//
//                zone.setText("");
//                itemcode.setText("");
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //////////

    public boolean checkQtyValidate(String recqty) {
        Log.e("checkQtyValidate", "heckQtyValidate");
        for (int i = 0; i < listQtyZone.size(); i++) {
            Log.e("checkQtyValidate", "for");
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                if (Integer.parseInt(recqty) <= Integer.parseInt(listQtyZone.get(i).getQty())) {

                    return true;

                } else {

                    return false;


                }
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (pdRepla2 != null && pdRepla2.isShowing())
            pdRepla2.cancel();
    }
    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(flag);
            }

        };
        return date;
    }

    private void updateLabel(int flag) {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (flag == 0)
            fromdate.setText(sdf.format(myCalendar.getTime()));
        else

            todate.setText(sdf.format(myCalendar.getTime()));
    }

    //    @Override
//    public void onRestart() {
//        super.onRestart();
//        finish();
//        startActivity(getIntent());
//    }
    public static void AddNewSwitchDATA() {

           my_dataBase.itemSwitchDao().dELETEAll();
           my_dataBase.itemSwitchDao().insertAll(listAllItemSwitch);
//        Log.e("AddNewSwitchDATA","AddNewSwitchDATA"+      listAllItemSwitch.size());
//        for (int i = 0; i< listAllItemSwitch.size(); i++) {
//            Log.e("getItem_NCODE1==", "" + listAllItemSwitch.get(i).getItemNCode());
//            Log.e("getItem_NCODE2==", "" +    my_dataBase.itemSwitchDao().getitemocode(listAllItemSwitch.get(i).getItemNCode()) +"");
//            if (
//                    my_dataBase.itemSwitchDao().getitemocode(listAllItemSwitch.get(i).getItemNCode()) == null ||
//                            my_dataBase.itemSwitchDao().getitemocode(listAllItemSwitch.get(i).getItemNCode()).equals(""))
//            {
//                my_dataBase.itemSwitchDao().insert(listAllItemSwitch.get(i));
//
//                Log.e("IF_getItem_NCODE==", "" + listAllItemSwitch.get(i).getItemNCode());
//
//            }
//            else {
//                my_dataBase.itemSwitchDao().  deleteitemrecord(listAllItemSwitch.get(i).getItemNCode());
//                my_dataBase.itemSwitchDao().insert(listAllItemSwitch.get(i));
//            }
//
//        }


    }
   void showErrorCode(String barcode){
        showSweetDialog(MainActivity.this,0,"",barcode);
    }







    private void getitemUnit(){
        Log.e("getitemUnit","getitemUnit");
        ImportData importData=new ImportData(MainActivity.this);

        ItemUnitsdialog = new Dialog(MainActivity.this);
        ItemUnitsdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ItemUnitsdialog.setCancelable(true);
        ItemUnitsdialog.setContentView(R.layout.datefor_itemunit);

        fromdate= ItemUnitsdialog.findViewById(R.id.fromdate);
        todate= ItemUnitsdialog.findViewById(R.id.todate);

        myCalendar = Calendar.getInstance();
        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String today = df.format(currentTimeAndDate);
        fromdate.setText(today);
        todate.setText(today);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, openDatePickerDialog(1), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ItemUnitsdialog.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemUnitsdialog.dismiss();
                my_dataBase.itemsUnitDao().dELETEAll();
                importData.getUnitData(fromdate.getText().toString().trim(), todate.getText().toString().trim());

            }
        });

        ItemUnitsdialog.show();
    }
String getCountOfItems(String itemcode,String unitid ){
     String CountOfItems=my_dataBase.itemsUnitDao().getConvRate(itemcode, unitid);
        if(CountOfItems!=null && !CountOfItems.equals(""))
    return CountOfItems;
        else
            return "1";
}



}