package com.hiaryabeer.transferapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.hiaryabeer.transferapp.Models.KeyboardUtil;
import com.hiaryabeer.transferapp.Models.SerialsModel;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.Store;

import java.util.ArrayList;
import java.util.List;
import android.widget.PopupMenu;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.Models.GeneralMethod.convertToEnglish;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;
import static com.hiaryabeer.transferapp.Models.ImportData.AllImportItemlist;
import static com.hiaryabeer.transferapp.Models.ImportData.Storelist;
import static com.hiaryabeer.transferapp.Models.ImportData.listAllZone;
import static com.hiaryabeer.transferapp.Models.ImportData.listQtyZone;
import static com.hiaryabeer.transferapp.Models.ImportData.pdRepla2;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    int saved = 4;
    int position;
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
    ;
    public static List<ReplacementModel> DB_replistcopy = new ArrayList<>();
    public static List<ReplacementModel> reducedqtyitemlist = new ArrayList<>();
    public static Dialog Re_searchdialog;
    EditText recqty;
    TextView search;
    public static Button save;
    public int indexZone = -1;
    public int indexDBZone = 0, indexDBitem = 0, indexOfReduceditem = 0;
    public RoomAllData my_dataBase;
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
    public static ArrayList<AllItems> AllItemstest = new ArrayList<>();
    private int pos;
    String maxTrans = "";
    public static int max = 0;
    public static int saveflage;
    private int TransferNo;
    String maxVochNum;
    private String minVo;
    private String MaxVo;
    public static TextView colorlastrow, colorData;


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
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked

                switch (menuItem.getItemId()) {
                    case R.id.goToReports: {
                        Intent i = new Intent(MainActivity.this, TransferReports.class);
                        startActivity(i);
                        return true;
                    }
                    case R.id.menuImport: {
                        if (Login.serialsActive == 1) {
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
                        return true;     }
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
        AllItemDBlist.clear();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);
        init();
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
//        if(AllItemDBlist .size()==0)
//            importData.getAllItems();
//      my_dataBase.storeDao().deleteall();
        Storelist.clear();
        Storelist = my_dataBase.storeDao().getall();

        importData = new ImportData(MainActivity.this);
        if (Login.serialsActive == 1) {
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
        } else if (Storelist.size() == 0) {
            getStors();
            Log.e("sss4", "sss4");
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appSettings.get(0).getCheckQty().equals("1")) {  //////Qty Checker Activated

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
                } else { ////Qty Checker Inactivated
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
        dialog1.show();
        Log.e("size", AllItemDBlist.size() + "");
        final ListView listView = dialog1.findViewById(R.id.Rec);
        final EditText search = dialog1.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    AllItemstest.clear();
                    for (int i = 0; i < AllItemDBlist.size(); i++)
                        if (AllItemDBlist.get(i).getItemName().toLowerCase().contains(editable.toString().toLowerCase()))
                            AllItemstest.add(AllItemDBlist.get(i));
                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemstest);
                    listView.setAdapter(adapter1);
                } else {
                    Adapterr adapter1 = new Adapterr(MainActivity.this, AllItemDBlist);
                    listView.setAdapter(adapter1);
                }
            }
        });


        Adapterr adapter1 = new Adapterr(this, AllItemDBlist);
        listView.setAdapter(adapter1);
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
                if (convertToEnglish(replacementlist.get(i).getItemcode()).equals(convertToEnglish(replacement.getItemcode()))) {

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

    private void getStors() {
        actvityflage = 1;
        importData.getStore();
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
        if (!posted) {
            exportData.exportReplacementList(Allreplacementlist1);
//            if (Login.serialsActive == 1) {
//                int d = my_dataBase.serialsDao().deleteAllSerials();
//                Log.e("DeleteSERIALS", d + "");
//                importData.getAllSerials(new ImportData.GetSerialsCallBack() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.e("responseLength", response.length() + "");
//                        allItemSerials.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                allItemSerials.add(new SerialsModel(
//                                        response.getJSONObject(i).getString("STORENO"),
//                                        response.getJSONObject(i).getString("ITEMOCODE"),
//                                        response.getJSONObject(i).getString("SERIALCODE")));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Log.e("allItemSerialsLength", allItemSerials.size() + "");
//                        my_dataBase.serialsDao().insertAll(allItemSerials);
//                    }
//
//                    @Override
//                    public void onError(String error) {
//
//                    }
//                });
//            }
        } else
            showSweetDialog(MainActivity.this, 3, getString(R.string.noUnsavedData), "");

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
        replacmentRecycler.scrollToPosition(pos);


    }

    private void colorRecycle(int pos) {

        Log.e("colorRecycle", "" + pos);
        highligtedItemPosition2 = -5;
        highligtedItemPosition = pos;

        adapter.notifyDataSetChanged();
        replacmentRecycler.scrollToPosition(pos);


    }

    private void init() {
        replacementlist.clear();
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
        save = findViewById(R.id.save);
        respon = findViewById(R.id.respons);
        qtyrespons = findViewById(R.id.qtyrespon);
        //  recqty.setOnEditorActionListener(onEditAction);
        itemcode.setOnEditorActionListener(onEditAction);
        zone.setOnEditorActionListener(onEditAction);
        generalMethod = new GeneralMethod(MainActivity.this);


        zone.setOnKeyListener(onKeyListener);
        itemcode.setOnKeyListener(onKeyListener);
        search = findViewById(R.id.searchitemCode);
        maxTrans = my_dataBase.replacementDao().getMaxReplacementNo();
        Log.e("maxTrans", "" + maxTrans);

        if (Login.serialsActive == 0) {
            itemcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
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

//                        ReplacementModel replacementModel1 = null;
//                        List<ReplacementModel> replacementModels1 = my_dataBase.replacementDao().isItemExist(itemcode.getText().toString());
//                        if (replacementModels1.size() != 0) {
//                            replacementModel1 = replacementModels1.get(replacementModels1.size() - 1);
//                        }

                            if (ExistsInLocallist(replacementModel)) {

                                itemcode.setError(null);

                                Log.e(" Case1 ", "Exists in Local List:");
                                if ((Integer.parseInt(replacementlist.get(position).getAvailableQty())) > 0) {

                                    int sum = Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");

                                    replacementlist.get(position).setAvailableQty(String.valueOf((Integer.parseInt(replacementlist.get(position).getAvailableQty())) - 1));
                                    replacementModel.setAvailableQty(String.valueOf((Integer.parseInt(replacementlist.get(position).getAvailableQty())) - 1));

                                    replacementlist.get(position).setRecQty(sum + "");
                                    replacementModel.setRecQty(sum + "");

                                    my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty(), max + "");
                                    my_dataBase.replacementDao().updateAvailableQTY(max + "", replacementlist.get(position).getItemcode(), replacementlist.get(position).getAvailableQty());
                                    colorlastrow.setText(position + "");
                                    fillAdapter();
                                    Log.e("case1", "case1");
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
                                if (ExsitsInItemlist(itemcode.getText().toString())) {

                                    itemcode.setError(null);
                                    Log.e(" Case3 ", "Not in local but in ItemList");

                                    importData.getItemQty(editable.toString(), FromNo, new ImportData.GetItemQtyCallBack() {
                                        @Override
                                        public void onResponse(String qty) {
                                            Log.e("QTY Response ", qty);
//                                        qty = "20";
                                            replacementModel.setAvailableQty(qty);
                                            if ((Integer.parseInt(qty)) > 0) {
                                                replacementModel.setAvailableQty(String.valueOf(Integer.parseInt(qty) - 1));
                                                replacementModel.setItemname(AllItemDBlist.get(pos).getItemName());
                                                replacementModel.setRecQty("1");
                                                replacementlist.add(0, replacementModel);
                                                SaveRow(replacementModel);
                                                colorlastrow.setText("0");
                                                fillAdapter();
                                                Log.e("case3", "case3");
                                                save.setEnabled(true);

                                                fromSpinner.setEnabled(false);
                                                toSpinner.setEnabled(false);


                                            } else {
                                                showSweetDialog(MainActivity.this, 0, getResources().getString(R.string.no_enough_amount), "");
                                                itemcode.setText("");
                                            }


                                        }

                                        @Override
                                        public void onError(String error) {
                                            showSweetDialog(MainActivity.this, 3, "Error!", getString(R.string.checkConnection));
                                            itemcode.setText("");
                                        }
                                    });


                                } else {
                                    Log.e(" Case4 ", "Not Exist in ItemList, Invalid code!");
                                    itemcode.setError("InValid Code");
                                    itemcode.setText("");
                                }

//                            Log.e("case4", "case4");


                            }

                        }
                    } else { ///Qty Checker Inactive  //////B

                        if (editable.toString().length() != 0) {

                            {
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

//                        ReplacementModel replacementModel1 = null;
//                        List<ReplacementModel> replacementModels1 = my_dataBase.replacementDao().isItemExist(itemcode.getText().toString());
//                        if (replacementModels1.size() != 0) {
//                            replacementModel1 = replacementModels1.get(replacementModels1.size() - 1);
//                        }

                                if (ExistsInLocallist(replacementModel)) {

                                    itemcode.setError(null);

                                    Log.e(" Case1 ", "Exists in Local List:");

                                    int sum = Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");

                                    replacementlist.get(position).setRecQty(sum + "");
                                    replacementModel.setRecQty(sum + "");

                                    my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(), replacementlist.get(position).getRecQty(), max + "");
                                    colorlastrow.setText(position + "");
                                    fillAdapter();
                                    Log.e("case1", "case1");
                                    save.setEnabled(true);


                                } else {
                                    if (ExsitsInItemlist(itemcode.getText().toString())) {

                                        itemcode.setError(null);
                                        Log.e(" Case3 ", "Not in local but in ItemList");

                                        replacementModel.setItemname(AllItemDBlist.get(pos).getItemName());
                                        replacementModel.setRecQty("1");
                                        replacementlist.add(0, replacementModel);
                                        SaveRow(replacementModel);
                                        colorlastrow.setText("0");
                                        fillAdapter();
                                        Log.e("case3", "case3");
                                        save.setEnabled(true);

                                        fromSpinner.setEnabled(false);
                                        toSpinner.setEnabled(false);


                                    } else {
                                        Log.e(" Case4 ", "Not Exist in ItemList, Invalid code!");
                                        itemcode.setError("InValid Code");
                                        itemcode.setText("");
                                    }

                                }

                            }

                        }

                    }


                }

            });
        } else { /////Serials
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

                                    DisplayMetrics dm = new DisplayMetrics();
                                    MainActivity.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                    int width = dm.widthPixels;

                                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                                    layoutParams.width = (int) (width / 1.3);
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
                                    tvItemName.setText(AllItemDBlist.get(pos).getItemName());
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

                                                    if (existInItemSerialList(itemcode.getText().toString().trim(), code)) {

                                                        ItemSerialTransfer serialTransfer =
                                                                new ItemSerialTransfer(String.valueOf(transNo),
                                                                        deviceId, itemcode.getText().toString().trim(), code,
                                                                        (new GeneralMethod(MainActivity.this)).getCurentTimeDate(1),
                                                                        fromSpinner.getSelectedItem().toString().substring(0, (fromSpinner.getSelectedItem().toString().indexOf(" "))),
                                                                        toSpinner.getSelectedItem().toString().substring(0, (toSpinner.getSelectedItem().toString().indexOf(" ")))
                                                                );
                                                        etSerial.setError(null);
                                                        serialTransfers.add(serialTransfer);
                                                        my_dataBase.serialTransfersDao().insert(serialTransfer);
                                                        updateAdapter();

                                                        if (ExistsInRepList(itemcode.getText().toString())) {

                                                            int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + 1;
                                                            replacementlist.get(repPosition).setRecQty(String.valueOf(sumQty));
                                                            my_dataBase.replacementDao().updateQTY(itemcode.getText().toString().trim(), String.valueOf(sumQty), String.valueOf(transNo));

                                                            replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                                            ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
                                                            replacmentRecycler.setAdapter(adapter);

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
                                                            replacementModel.setItemname(AllItemDBlist.get(pos).getItemName());
                                                            replacementModel.setTransNumber(transNo + "");
                                                            replacementModel.setDeviceId(deviceId);
                                                            replacementModel.setRecQty(1 + "");
                                                            replacementModel.setFromName(fromSpinner.getSelectedItem().toString());
                                                            replacementModel.setToName(toSpinner.getSelectedItem().toString());
                                                            replacementModel.setZone("");

                                                            replacementlist.add(0, replacementModel);
                                                            my_dataBase.replacementDao().insert(replacementModel);

                                                            replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                                            ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
                                                            replacmentRecycler.setAdapter(adapter);

                                                            replacmentRecycler.smoothScrollToPosition(0);
                                                            colorlastrow.setText("0");

                                                            save.setEnabled(true);

                                                            fromSpinner.setEnabled(false);
                                                            toSpinner.setEnabled(false);

//                                                    for (int i = 0; i < serialTransfers.size(); i++) {
//                                                        serialTransfers.get(i).setAdded("1");
//                                                    }

                                                        }
//                                                my_dataBase.serialsDao().addToRep(transNo + "", deviceId, s.toString().trim());


                                                        openSmallCapture(6);
                                                    } else {
                                                        showSweetDialog(MainActivity.this, 3, getString(R.string.serialNotFound), getString(R.string.notFoundMsg));
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
//                                    }

                                    });

                                } else {
                                    if (ExistsInRepList(itemcode.getText().toString())) {

                                        int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + 1;
                                        replacementlist.get(repPosition).setRecQty(String.valueOf(sumQty));
                                        my_dataBase.replacementDao().updateQTY(itemcode.getText().toString().trim(), String.valueOf(sumQty), String.valueOf(transNo));

                                        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
                                        replacmentRecycler.setAdapter(adapter);

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
                                        replacementModel.setItemname(AllItemDBlist.get(pos).getItemName());
                                        replacementModel.setTransNumber(transNo + "");
                                        replacementModel.setDeviceId(deviceId);
                                        replacementModel.setRecQty(1 + "");
                                        replacementModel.setFromName(fromSpinner.getSelectedItem().toString());
                                        replacementModel.setToName(toSpinner.getSelectedItem().toString());
                                        replacementModel.setZone("");

                                        replacementlist.add(0, replacementModel);
                                        my_dataBase.replacementDao().insert(replacementModel);

                                        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
                                        replacmentRecycler.setAdapter(adapter);

                                        replacmentRecycler.smoothScrollToPosition(0);
                                        colorlastrow.setText("0");


                                        save.setEnabled(true);

                                        fromSpinner.setEnabled(false);
                                        toSpinner.setEnabled(false);

                                    }
                                    dialog1.dismiss();
                                }

                            } else if (existsInSerialsList(itemcode.getText().toString().trim())) {
                                Log.e(" Case2 ", "Exist in serial list");

                            } else if (existBarcode(itemcode.getText().toString().trim())) {
                                Log.e(" Case3 ", "Exist in barcode list");

                            } else {
                                Log.e(" Case4 ", "Not Exist in any list, Invalid code!");
                                itemcode.setError("InValid Code");
                                itemcode.setText("");
                            }

                        }

                    }
                }
            });
        }


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
                        Toast.makeText(MainActivity.this, getString(R.string.getAllData), Toast.LENGTH_SHORT).show();
                        ImportData.pdRepla.dismissWithAnimation();

                    } else if (editable.toString().equals("nodata")) {
                        Toast.makeText(MainActivity.this, getString(R.string.netWorkError), Toast.LENGTH_SHORT).show();

                        ImportData.pdRepla.dismissWithAnimation();
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
                            Log.e("TransferNo===", TransferNo + "");
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


        if (appSettings.size() != 0) {
            deviceId = appSettings.get(0).getDeviceId();
            Log.e("appSettings", "+" + deviceId);

        }

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

            if (allItemsList.get(t).getBarCode().equals(code)) {
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

            if (serialsModelList.get(s).getSerialNo().trim().equals(code.trim())) {
                codeScanned = serialsModelList.get(s).getItemNo();
                itemcode.setText(codeScanned);
                flag = true;
                break;
            }

        }

        return flag;

    }


    private boolean ExsitsInItemlist(String itemcode) {

        Log.e("ExsitsInItemlist==", "ExsitsInItemlist");
        boolean flage = false;
        for (int x1 = 0; x1 < AllItemDBlist.size(); x1++) {
            if (AllItemDBlist.get(x1).getItemOcode().trim().equals(itemcode.trim())) {
                pos = x1;

                flage = true;
                break;
            } else {
                flage = false;
            }


        }
        return flage;
    }

    private void fillSp() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);
        Log.e("sss1", "sss1");
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
        Log.e(" fillAdapter", " fillAdapter");
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new ReplacementAdapter(replacementlist, MainActivity.this);
        replacmentRecycler.setAdapter(adapter);
        //  colorlastrow.setText(position + "");
        //    colorlastrow.setText((0)+"");
        if (replacementlist.size() > 1) {
            Log.e("position=====", position + "");
            replacmentRecycler.smoothScrollToPosition(position);

        }


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
                i.putExtra("key", "1");
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

            if (allTransSerials.get(i).getSerialNo().equals(serial)) {
                return true;
            }

        }

        List<String> s = my_dataBase.serialTransfersDao().getSerialsForOther(itemcode.getText().toString());
        for (int i = 0; i < s.size(); i++) {

            if (s.get(i).equals(serial)) {
                return true;
            }

        }

        return false;
    }

    public boolean existInItemSerialList(String itemNo, String serialNo) {

        boolean valid = false;

        String fromNo = fromSpinner.getSelectedItem().toString().substring(0, fromSpinner.getSelectedItem().toString().indexOf(" "));
        List<String> itemSerialsInStore = my_dataBase.serialsDao().getItemSerialsInStore(fromNo, itemNo);
        Log.e("itemSerialsInStore", itemSerialsInStore.toString() + "");

        for (int i = 0; i < itemSerialsInStore.size(); i++) {

            if (itemSerialsInStore.get(i).equals(serialNo))
                valid = true;

        }

        if (valid) {
            List<ItemSerialTransfer> serialTransfers1 = my_dataBase.serialTransfersDao().getAll();
            if (serialTransfers1.size() != 0) {
                for (int i = (serialTransfers1.size() - 1); i >= 0; i--) {
                    if (serialTransfers1.get(i).getSerialNo().equals(serialNo)) {
                        valid = serialTransfers1.get(i).getToStore().equals(fromNo);
                        break;
                    }
                }
            }
        }

        Log.e("VALID", valid + "");
        return valid;

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
}