package com.hiaryabeer.transferapp;

import static com.hiaryabeer.transferapp.Models.ImportData.Storelist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiaryabeer.transferapp.Adapters.TransReportsAdapter;
import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransferReports extends AppCompatActivity {

    private EditText etPickDate, etSearchByName, etSearchByNo;
    private Button btnPreview;
    private RecyclerView rvTransferReports;

    private List<ReplacementModel> reportsList = new ArrayList<>();
    private ArrayList<ReplacementModel> searchList = new ArrayList<>();
    private TransReportsAdapter transReportsAdapter;

    private Calendar calendar;
    private int year, month, day;

    private RoomAllData myDB;

    private TextView postedLabel, notPostedLabel;
    private Spinner spinnerToStore;
    private ArrayList<String> spinnerArray = new ArrayList<>();

    private ImageView imgEmpty;
    private TextView tvEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_reports);
        setTitle(R.string.TransfersReportTitle);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ActionBar ab = getSupportActionBar();
//        // Enable the Up button
//        ab.setDisplayHomeAsUpEnabled(true);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        toolbar.setTitle(getString(R.string.TransfersReportTitle));
//        getSupportActionBar().

        initViews();  //Initialize Views

        String p = "  " + getString(R.string.posted_items);
        String f = "  " + getString(R.string.not_posted_items);
        postedLabel.setText(p);
        notPostedLabel.setText(f);

        GeneralMethod generalMethod = new GeneralMethod(TransferReports.this);

/********* Initialize Date Picker **********/
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

//        Date currentDate = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = generalMethod.getCurentTimeDate(1);

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etPickDate.setText(currentDate);
        etPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TransferReports.this, dateListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
/*************/


/****** View Data on Recycler View ******/
        initRecView();


/***** Update Recycler View Based On Transaction Date Change ******/
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportsList = myDB.replacementDao().getReplacementsByDate(etPickDate.getText().toString());
                transReportsAdapter = new TransReportsAdapter(TransferReports.this, reportsList);
                rvTransferReports.setAdapter(transReportsAdapter);
                rvTransferReports.setLayoutManager(new LinearLayoutManager(TransferReports.this));

                if (transReportsAdapter.getItemCount() == 0) {
                    imgEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    imgEmpty.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.GONE);
                }

            }
        });
/************/

/********* Initialize ToStore Spinner ********/

        spinnerArray.clear();
        Storelist.clear();
        Storelist = myDB.storeDao().getall();

        spinnerArray.add(getString(R.string.allStores));
        for (int r = 0; r < Storelist.size(); r++) {
            spinnerArray.add(Storelist.get(r).getSTORENAME());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray);
        spinnerToStore.setAdapter(spinnerAdapter);
        spinnerToStore.setSelection(0);

/***********/

/********* Search Items By ToStore *******/

        spinnerToStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String c = etSearchByNo.getText().toString().trim();
                String n = etSearchByName.getText().toString().trim();

                if (position != 0) {

                    if ((n.length() == 0) && (c.length() == 0)) {
                        searchByToStore();
                    }
                    //
                    if ((n.length() != 0) && (c.length() == 0)) {
                        searchByNameAndStore();
                    }
                    //
                    if ((n.length() == 0 && (c.length() != 0))) {
                        searchByCodeAndStore();
                    }
                    //
                    if ((n.length() != 0 && (c.length() != 0))) {
                        searchByAll();
                    }
                } else {
                    if ((n.length() == 0) && (c.length() == 0)) {
                        initRecView();
                    }
                    //
                    if ((n.length() != 0) && (c.length() == 0)) {
                        searchByName();
                    }
                    //
                    if ((n.length() == 0 && (c.length() != 0))) {
                        searchByCode();
                    }
                    //
                    if ((n.length() != 0 && (c.length() != 0))) {
                        searchByNameAndCode();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/***********/


/********** Search Items By Name ***********/
        etSearchByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String n = s.toString().trim();
                String c = etSearchByNo.getText().toString().trim();
                int position = spinnerToStore.getSelectedItemPosition();

                if (n.length() != 0) {

                    if ((c.length() == 0) && (position == 0)) {
                        searchByName();
                    }
                    //
                    if ((c.length() != 0) && (position == 0)) {
                        searchByNameAndCode();
                    }
                    //
                    if ((c.length() == 0 && (position != 0))) {
                        searchByNameAndStore();
                    }
                    //
                    if ((c.length() != 0 && (position != 0))) {
                        searchByAll();
                    }
                } else {
                    if ((c.length() == 0) && (position == 0)) {
                        initRecView();
                    }
                    //
                    if ((c.length() != 0) && (position == 0)) {
                        searchByCode();
                    }
                    //
                    if ((c.length() == 0 && (position != 0))) {
                        searchByToStore();
                    }
                    //
                    if ((c.length() != 0 && (position != 0))) {
                        searchByCodeAndStore();
                    }
                }
            }
        });
/***********/

/********** Search Items By Item No. ***********/
        etSearchByNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String c = s.toString().trim();
                String n = etSearchByName.getText().toString().trim();
                int position = spinnerToStore.getSelectedItemPosition();

                if (c.length() != 0) {

                    if ((n.length() == 0) && (position == 0)) {
                        searchByCode();
                    }
                    //
                    if ((n.length() != 0) && (position == 0)) {
                        searchByNameAndCode();
                    }
                    //
                    if ((n.length() == 0 && (position != 0))) {
                        searchByCodeAndStore();
                    }
                    //
                    if ((n.length() != 0 && (position != 0))) {
                        searchByAll();
                    }
                } else {
                    if ((n.length() == 0) && (position == 0)) {
                        initRecView();
                    }
                    //
                    if ((n.length() != 0) && (position == 0)) {
                        searchByName();
                    }
                    //
                    if ((n.length() == 0 && (position != 0))) {
                        searchByToStore();
                    }
                    //
                    if ((n.length() != 0 && (position != 0))) {
                        searchByNameAndStore();
                    }
                }
            }
        });
/***************/

    }


    /***************** METHODS *****************/


    private void searchByAll() {

        searchList.clear();
        String name = etSearchByName.getText().toString().toLowerCase().trim();
        String code = etSearchByNo.getText().toString().toLowerCase().trim();
        String toStore = spinnerToStore.getSelectedItem().toString();

        for (int i = 0; i < reportsList.size(); i++) {
            if (reportsList.get(i).getToName().contains(toStore) &&
                    reportsList.get(i).getItemcode().toLowerCase().contains(code) &&
                    reportsList.get(i).getItemname().toLowerCase().contains(name)) {
                searchList.add(reportsList.get(i));
            }
        }

        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }

    }

    private void searchByCodeAndStore() {
        searchList.clear();
        String code = etSearchByNo.getText().toString().toLowerCase().trim();
        String toStore = spinnerToStore.getSelectedItem().toString();

        for (int i = 0; i < reportsList.size(); i++) {
            if (reportsList.get(i).getToName().contains(toStore) &&
                    reportsList.get(i).getItemcode().toLowerCase().contains(code)) {
                searchList.add(reportsList.get(i));
            }
        }

        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }
    }

    private void searchByNameAndStore() {
        searchList.clear();
        String name = etSearchByName.getText().toString().toLowerCase().trim();
        String toStore = spinnerToStore.getSelectedItem().toString();

        for (int i = 0; i < reportsList.size(); i++) {
            if (reportsList.get(i).getToName().contains(toStore) &&
                    reportsList.get(i).getItemname().toLowerCase().contains(name)) {
                searchList.add(reportsList.get(i));
            }
        }

        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }
    }

    private void searchByNameAndCode() {

        searchList.clear();
        String code = etSearchByNo.getText().toString().toLowerCase().trim();
        String name = etSearchByName.getText().toString().toLowerCase().trim();

        for (int i = 0; i < reportsList.size(); i++) {

            if (reportsList.get(i).getItemcode().toLowerCase().contains(code) &&
                    reportsList.get(i).getItemname().toLowerCase().contains(name)) {
                searchList.add(reportsList.get(i));
            }

        }

        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }
    }

    private void searchByToStore() {


        searchList.clear();

        for (int s = 0; s < reportsList.size(); s++) {

            if (reportsList.get(s).getToName().contains(spinnerToStore.getSelectedItem().toString())) {
                searchList.add(reportsList.get(s));
            }

        }
        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }

    }

    private void searchByCode() {
        searchList.clear();
        String code = etSearchByNo.getText().toString().toLowerCase().trim();

        for (int i = 0; i < reportsList.size(); i++) {

            if (reportsList.get(i).getItemcode().toLowerCase().contains(code)) {
                searchList.add(reportsList.get(i));
            }

        }

        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }
    }

    private void searchByName() {
        searchList.clear();
        String name = etSearchByName.getText().toString().toLowerCase().trim();

        for (int i = 0; i < reportsList.size(); i++) {

            if (reportsList.get(i).getItemname().toLowerCase().contains(name)) {
                searchList.add(reportsList.get(i));
            }

        }

        transReportsAdapter = new TransReportsAdapter(this, searchList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }

    }

    private void initRecView() {
        myDB = RoomAllData.getInstanceDataBase(this);
        String date = etPickDate.getText().toString().trim();
        reportsList = myDB.replacementDao().getReplacementsByDate(date);

        transReportsAdapter = new TransReportsAdapter(this, reportsList);
        rvTransferReports.setAdapter(transReportsAdapter);
        rvTransferReports.setLayoutManager(new LinearLayoutManager(this));

        if (transReportsAdapter.getItemCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            imgEmpty.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);

        }

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etPickDate.setText(sdf.format(calendar.getTime()));
    }

    private void initViews() {

        /////Edit Texts
        etPickDate = findViewById(R.id.etPickDate);
        etSearchByName = findViewById(R.id.etSearchByName);
        etSearchByNo = findViewById(R.id.etSearchByNo);

        /////Buttons
        btnPreview = findViewById(R.id.btnPreview);

        /////Recycler View
        rvTransferReports = findViewById(R.id.rvTransferReports);

        /////Text Views
        postedLabel = findViewById(R.id.postedLabel);
        notPostedLabel = findViewById(R.id.notPostedLabel);
        tvEmpty = findViewById(R.id.tvEmpty);

        ////Spinner
        spinnerToStore = findViewById(R.id.spinnerToStore);

        ////ImageView
        imgEmpty = findViewById(R.id.imgEmpty);

    }


}