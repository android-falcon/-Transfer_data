package com.hiaryabeer.transferapp.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hiaryabeer.transferapp.Login;
import com.hiaryabeer.transferapp.MainActivity;
import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.hiaryabeer.transferapp.Models.ItemSerialTransfer;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.ScanActivity;

import java.util.ArrayList;
import java.util.List;

import static com.hiaryabeer.transferapp.MainActivity.colorData;
import static com.hiaryabeer.transferapp.MainActivity.colorlastrow;
import static com.hiaryabeer.transferapp.MainActivity.dialog1;
import static com.hiaryabeer.transferapp.MainActivity.itemcode;
import static com.hiaryabeer.transferapp.MainActivity.max;
import static com.hiaryabeer.transferapp.MainActivity.replacementlist;
import static com.hiaryabeer.transferapp.MainActivity.replacmentRecycler;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.convertToEnglish;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Adapterr extends BaseAdapter {
    private Context context; //context
    private List<AllItems> items; //data source of the list adapter
    public static final int REQUEST_Camera_Barcode = 1;
//    public static EditText etSerial;
//    private ArrayList<ItemSerialTransfer> serialTransfers;
//    private RoomAllData my_database;
//    private RecyclerView rvSerialTransfers;
//    private SerialsAdapter serialsAdapter;
//    private int repPosition;
//    public static TextView tvTotal;
//    private List<ItemSerialTransfer> allTransSerials;
//    private int transNo;
//    private String deviceId;

    //public constructor
    public Adapterr(Context context, List<AllItems> items) {
        this.context = context;
        this.items = items;
//        this.my_database = RoomAllData.getInstanceDataBase(context);
//        this.serialTransfers = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.searchrec, parent, false);
        }

        // get current item to be displayed
        AllItems currentItem = (AllItems) getItem(position);


        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.itemname);
        LinearLayout linearLayout = convertView.findViewById(R.id.linear);
//        TextView icAddSerial = convertView.findViewById(R.id.icAddSerial);
        LinearLayout parentLinear = convertView.findViewById(R.id.parentLinear);

        if (Login.serialsActive == 0) {
//            icAddSerial.setVisibility(View.GONE);
            linearLayout.setOnClickListener(view -> {

                itemcode.setText(items.get(position).getItemOcode());
                Log.e("position6===", position + "");
                //colorData.setText(position+"");

                dialog1.dismiss();

            });
        } else {
//            icAddSerial.setVisibility(View.VISIBLE);
            parentLinear.setOnClickListener(v -> {
//                my_database.serialsDao().deleteNotAddedPrev(max + "");
//                Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.item_serials_layout);
//                dialog.setCancelable(true);
//
//                TextView icClose = dialog.findViewById(R.id.icClose);
//                TextView tvItemName = dialog.findViewById(R.id.tvItemName);
//                TextView tvItemCode = dialog.findViewById(R.id.tvItemCode);
//                etSerial = dialog.findViewById(R.id.etSerial);
//                TextView icScan = dialog.findViewById(R.id.icScan);
//                TextView icEdit = dialog.findViewById(R.id.icEdit);
//                rvSerialTransfers = dialog.findViewById(R.id.rvSerialTransfers);
//                Button btnAdd = dialog.findViewById(R.id.btnAdd);
//                tvTotal = dialog.findViewById(R.id.tvTotal);
//                TextView icDeleteAll = dialog.findViewById(R.id.icDeleteAll);
//
//                dialog.show();
//
//                transNo = MainActivity.max;
//                deviceId = my_database.settingDao().getallsetting().get(0).getDeviceId();
//
//                serialTransfers = (ArrayList<ItemSerialTransfer>) my_database.serialsDao().getAllNotAdded(String.valueOf(transNo), deviceId, items.get(position).getItemOcode());
//                updateAdapter();
//
//                etSerial.requestFocus();
//                etSerial.setEnabled(false);
//                etSerial.setClickable(true);
//                etSerial.setOnEditorActionListener((v17, actionId, event) -> {
//                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
//                            || actionId == EditorInfo.IME_NULL) {
//
//                        if (etSerial.getText().toString().trim().equals(""))
//
//                            etSerial.requestFocus();
//
//                    }
//
//                    return true;
//                });
//
//                icClose.setOnClickListener(v1 -> dialog.dismiss()
//                );
//                tvItemName.setText(items.get(position).getItemName());
//                tvItemCode.setText(items.get(position).getItemOcode());
//
//                icScan.setOnClickListener(v14 -> openSmallCapture());
//
//                icEdit.setOnClickListener(v13 -> openEditDialog());
//
//
//                etSerial.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        if (!s.toString().trim().equals("")) {
//
//                            if (!isExist(s.toString().trim())) {
//                                ItemSerialTransfer serialTransfer =
//                                        new ItemSerialTransfer(String.valueOf(transNo),
//                                                deviceId, items.get(position).getItemOcode(), s.toString().trim()
//                                        );
//                                etSerial.setError(null);
//                                serialTransfers.add(serialTransfer);
//                                my_database.serialsDao().insert(serialTransfer);
//                                updateAdapter();
//                                etSerial.setText("");
//                            } else {
//                                showSweetDialog(context, 3, context.getString(R.string.uniqueSerial), context.getString(R.string.uniqueSerialMsg));
//                                etSerial.setText("");
//                            }
//                        }
//
//                    }
//                });
//
//                etSerial.setOnKeyListener((v15, keyCode, event) -> {
////                        if (keyCode == KeyEvent.KEYCODE_BACK) {
////                            onBackPressed();
////                        }
//                    if (keyCode != KeyEvent.KEYCODE_ENTER) {
//
//                        {
//                            if (event.getAction() == KeyEvent.ACTION_UP)
//                                if (etSerial.getText().toString().equals(""))
//                                    etSerial.requestFocus();
//
//                            return true;
//                        }
//                    }
//                    return false;
//                });
//
//                icDeleteAll.setOnClickListener(v16 -> {
//
//                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText(context.getString(R.string.deleteAll))
//                            .setContentText(context.getString(R.string.deleteAllMsg))
//                            .setConfirmButton(context.getString(R.string.yes), sweetAlertDialog -> {
//                                if (serialTransfers.size() > 0) {
//                                    serialTransfers.clear();
//                                    my_database.serialsDao().deleteNotAddedForItem(
//                                            transNo + "",
//                                            deviceId,
//                                            items.get(position).getItemOcode()
//                                    );
//                                    updateAdapter();
//                                }
//                                sweetAlertDialog.dismissWithAnimation();
//                            })
//                            .setCancelButton(context.getString(R.string.no), SweetAlertDialog::dismissWithAnimation)
//                            .show();
//                });
//
//
//                btnAdd.setOnClickListener(v12 -> {
//                    if (serialTransfers.size() > 0) {
//                        if (ExistsInRepList(items.get(position).getItemOcode())) {
//
//                            int sumQty = Integer.parseInt(replacementlist.get(repPosition).getRecQty()) + serialTransfers.size();
//                            MainActivity.replacementlist.get(repPosition).setRecQty(String.valueOf(sumQty));
//                            my_database.replacementDao().updateQTY(items.get(position).getItemOcode(), String.valueOf(sumQty), String.valueOf(transNo));
//
//                            replacmentRecycler.setLayoutManager(new LinearLayoutManager(context));
//                            ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, context);
//                            replacmentRecycler.setAdapter(adapter);
//
//                            replacmentRecycler.smoothScrollToPosition(repPosition);
//                            colorlastrow.setText(repPosition + "");
//
//                            for (int i = 0; i < serialTransfers.size(); i++) {
//                                serialTransfers.get(i).setAdded("1");
//                            }
//
//
//                        } else {
//                            ReplacementModel replacementModel = new ReplacementModel();
//
//                            replacementModel.setFrom(MainActivity.fromSpinner.getSelectedItem().toString().substring(0, (MainActivity.fromSpinner.getSelectedItem().toString().indexOf(" "))));
//                            replacementModel.setTo(MainActivity.toSpinner.getSelectedItem().toString().substring(0, (MainActivity.toSpinner.getSelectedItem().toString().indexOf(" "))));
//                            replacementModel.setItemcode(items.get(position).getItemOcode());
//                            replacementModel.setIsPosted("0");
//                            replacementModel.setReplacementDate((new GeneralMethod(context)).getCurentTimeDate(1));
//                            replacementModel.setItemname(items.get(position).getItemName());
//                            replacementModel.setTransNumber(transNo + "");
//                            replacementModel.setDeviceId(deviceId);
//                            replacementModel.setRecQty(serialTransfers.size() + "");
//                            replacementModel.setFromName(MainActivity.fromSpinner.getSelectedItem().toString());
//                            replacementModel.setToName(MainActivity.toSpinner.getSelectedItem().toString());
//
//                            replacementlist.add(0, replacementModel);
//                            my_database.replacementDao().insert(replacementModel);
//
//                            replacmentRecycler.setLayoutManager(new LinearLayoutManager(context));
//                            ReplacementAdapter adapter = new ReplacementAdapter(replacementlist, context);
//                            replacmentRecycler.setAdapter(adapter);
//
//                            replacmentRecycler.smoothScrollToPosition(0);
//                            colorlastrow.setText("0");
//
//                            for (int i = 0; i < serialTransfers.size(); i++) {
//                                serialTransfers.get(i).setAdded("1");
//                            }
//
//                        }
//                        my_database.serialsDao().addToRep(transNo + "", deviceId, items.get(position).getItemOcode());
//
//                        dialog.dismiss();
//                        dialog1.dismiss();
//                    }
//
//                });
                itemcode.setText(items.get(position).getItemOcode());
            });
        }

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getItemName());
        //linearLayout.setBackgroundColor(context.getResources().getColor(R.color.yelow));

        // returns the view for the current row
        return convertView;
    }


//    private boolean ExistsInRepList(String code) {
//        boolean flag = false;
//
//        if (MainActivity.replacementlist.size() != 0)
//            for (int i = 0; i < MainActivity.replacementlist.size(); i++) {
//                if (convertToEnglish(MainActivity.replacementlist.get(i).getItemcode()).equals(code)) {
//
//                    repPosition = i;
//                    flag = true;
//                    break;
//
//                }
//            }
//
//        return flag;
//    }
//
//    private void updateAdapter() {
//        serialsAdapter = new SerialsAdapter(context, serialTransfers);
//        rvSerialTransfers.setLayoutManager(new LinearLayoutManager(context));
//        rvSerialTransfers.setAdapter(serialsAdapter);
//        //  colorlastrow.setText(position + "");
//        //    colorlastrow.setText((0)+"");
//        if (serialTransfers.size() > 1) {
//            rvSerialTransfers.smoothScrollToPosition(serialTransfers.size() - 1);
//        }
//
//        tvTotal.setText(serialTransfers.size() + "");
//
//    }
//
//    private boolean isExist(String serial) {
//        allTransSerials = my_database.serialsDao().getAll(String.valueOf(transNo), deviceId);
//        for (int i = 0; i < allTransSerials.size(); i++) {
//
//            if (allTransSerials.get(i).getSerialNo().equals(serial)) {
//                return true;
//            }
//
//        }
//        return false;
//    }
//
//    private void openEditDialog() {
//        Dialog editDialog = new Dialog(context);
//        editDialog.setContentView(R.layout.enter_serial_layout);
//        editDialog.setCancelable(true);
//
//        EditText editText = editDialog.findViewById(R.id.editText);
//        TextView icClose = editDialog.findViewById(R.id.icClose);
//        Button okButton = editDialog.findViewById(R.id.okButton);
//
//        editDialog.show();
////        editText.setText(etSerial.getText());
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                etSerial.setText(editText.getText().toString().trim());
//                editDialog.dismiss();
//            }
//        });
//        icClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editDialog.dismiss();
//            }
//        });
//
//
//    }
//
//    private void openSmallCapture() {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
//                Log.e("requestresult", "PERMISSION_GRANTED");
//                Intent i = new Intent(context, ScanActivity.class);
//                i.putExtra("key", "6");
//                context.startActivity(i);
//                // searchByBarcodeNo(s + "");
//            }
//        } else {
//            Intent i = new Intent(context, ScanActivity.class);
//
//            i.putExtra("key", "6");
//
//            context.startActivity(i);
//            //  searchByBarcodeNo(s + "");
//        }
//    }
}