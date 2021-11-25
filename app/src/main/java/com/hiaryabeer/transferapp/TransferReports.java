package com.hiaryabeer.transferapp;

import static com.hiaryabeer.transferapp.Models.ImportData.Storelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hiaryabeer.transferapp.Adapters.TransReportsAdapter;
import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class TransferReports extends AppCompatActivity {
    TextView etPickDate;
    private EditText etSearchByName, etSearchByNo;
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

    private FloatingActionButton fabConvert, fabPDF, fabExcel;
    private Animation fromBottom, toBottom;
    private boolean clicked = false;

    private static final String TAG = MainActivity.class.getSimpleName();
//    private FabSpeedDial fabSpeedDial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_reports);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
/////////


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
/////////

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

/////////

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

/////////


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
/////////

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
/////////

/********** Convert into PDF or Excel *******/
        fabConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = !clicked;
                setVisibility(clicked);
                setAnimation(clicked);
            }
        });

        fabPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (Build.VERSION.SDK_INT >= 23) {
                        if (TransferReports.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                                && (TransferReports.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                            createPdf2();
                            Log.v("", "Permission is granted");
                        } else {

                            Log.v("", "Permission is revoked");
                            ActivityCompat.requestPermissions(
                                    TransferReports.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        }
                    } else { // permission is automatically granted on sdk<23 upon
                        // installation
                        createPdf2();
                        Log.v("", "Permission is granted");
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        fabExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                convertToExcel();
            }
        });

    }
/////////


    /***************** METHODS *****************/

//    private void convertToExcel() {
//        try {
//            ExportToExcel exportToExcel = new ExportToExcel();
//            exportToExcel.createExcelFile(PaymentDetailsReport.this, "PaymentReport.xls", 2, payMentReportList);
//
//        }catch (Exception e){
//            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
//        }
//    }
    private void createPdf2() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, "Transfers_Report.pdf");

        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = new PdfWriter(file);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);

        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.translogo);

        assert drawable != null;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        image.setHeight(100);
        image.setWidth(100);

        Paragraph paragraph1 = new Paragraph("Transfers Report").setBold();
        Paragraph paragraph2 = new Paragraph("Date: " + etPickDate.getText().toString());
        Paragraph paragraph3 = new Paragraph("Transfers made To Store: " +
                "" + spinnerToStore.getSelectedItem().toString());


//        Text text1 = new Text("Transfers Report").setBold();
//
//        Text text2 = new Text("Date: " + etPickDate.getText().toString());
//
//        Text text3 = new Text("Transfers made To Store: " +
//                "" + spinnerToStore.getSelectedItem().toString());
//
//        paragraph.add(text1)
//                .add(text2)
//                .add(text3);

        float columnWidth[] = {100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f};
        Table table = new Table(columnWidth);

        table.addCell(new Cell().add(new Paragraph(getString(R.string.transactionDate))));
        table.addCell(new Cell().add(new Paragraph(getString(R.string.trsnferNo))));
        table.addCell(new Cell().add(new Paragraph(getString(R.string.fromStore))));
        table.addCell(new Cell().add(new Paragraph(getString(R.string.toStore))));
        table.addCell(new Cell().add(new Paragraph(getString(R.string.Itemname))));
        table.addCell(new Cell().add(new Paragraph(getString(R.string.itemCode))));
        table.addCell(new Cell().add(new Paragraph(getString(R.string.Qty))));
        table.addCell(getString(R.string.serials));

        List<String> serialCodes;

        for (int i = 0; i < reportsList.size(); i++) {
            serialCodes = myDB.serialsDao().getSerialCodes(reportsList.get(i).getTransNumber(), reportsList.get(i).getItemcode());
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getReplacementDate()))));
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getTransNumber()))));
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getFromName()))));
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getToName()))));
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getItemname()))));
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getItemcode()))));
            table.addCell(new Cell(serialCodes.size(), 1).add(new Paragraph(String.valueOf(reportsList.get(i).getRecQty()))));
            for (int j = 0; j < serialCodes.size(); j++) {
                table.addCell(serialCodes.get(j));
            }

        }


        document.add(image)
                .add(paragraph1)
                .add(paragraph2)
                .add(paragraph3)
                .add(table);

        document.close();

        Toast.makeText(this, "PDF Created", Toast.LENGTH_LONG).show();
    }

//    private void createPdf() throws FileNotFoundException, DocumentException {
//
////        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/TransferReports/";
////        File file = new File(directory_path);
////        if (!file.exists()) {
////            file.mkdirs();
////        }
////        String targetPdf = directory_path + "Transfers_Report" + ".pdf";
////
////        File path = new File(targetPdf);
//
//        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOCUMENTS), "Transfers_report");
//        if (!pdfFolder.exists()) {
//            pdfFolder.mkdir();
//            Log.e("LOG_TAG", "Pdf Directory created");
//        }
//        File myFile = new File(pdfFolder + etPickDate.getText().toString() + ".pdf");
//        OutputStream output = new FileOutputStream(myFile);
//
////        Document document = new Document();
//        Rectangle pagesize = new Rectangle(216f, 720f);
////        Document document = new Document(pagesize, 36f, 72f, 108f, 180f);
//        Document document = new Document(PageSize.LETTER);
//        PdfWriter.getInstance(document, output);
//
//        document.open();
//
//        document.add(new Paragraph(etPickDate.getText().toString()));
//        document.add(new Paragraph(spinnerToStore.getSelectedItem().toString()));
//
//        document.close();
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
//    }
//
////    private void viewPdf(){
////        String path = Environment.getExternalStorageDirectory().toString() + "/Sample.pdf";
////        try
////        {
////            PDFUtility.createPdf(TransferReports.this,TransferReports.this,getSampleData(),path,true);
////        }
////        catch (Exception e)
////        {
////            e.printStackTrace();
////            Log.e(TAG,"Error Creating Pdf");
////            Toast.makeText(this,"Error Creating Pdf",Toast.LENGTH_SHORT).show();
////        }
////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
////        {
////            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////            Uri uri = Uri.fromParts("package", getPackageName(), null);
////            intent.setData(uri);
////            startActivity(intent);
////        }
////    }
////
////    @Override
////    public void onPDFDocumentClose(File file)
////    {
////        Toast.makeText(this,"Sample Pdf Created",Toast.LENGTH_SHORT).show();
////    }
////
////    private List<String[]> getSampleData()
////    {
////        int count = 20;
////        if(!TextUtils.isEmpty(rowCount.getText()))
////        {
////            count = Integer.parseInt(rowCount.getText().toString());
////        }
////
////        List<String[]> temp = new ArrayList<>();
////        for (int i = 0; i < count; i++)
////        {
////            temp.add(new String[] {"C1-R"+ (i+1),"C2-R"+ (i+1)});
////        }
////        return  temp;
////    }
////    }
//
//    private File convertToPdf() {
//        File file = null;
//        try {
//            PdfConverter pdf = new PdfConverter(TransferReports.this);
//            file = pdf.exportListToPdf(reportsList, "Transfers Report", etPickDate.getText().toString());
//        } catch (Exception e) {
//            Toast.makeText(this, "Storage Permission", Toast.LENGTH_SHORT).show();
//
//        }
//
//        return file;
//    }

    void setVisibility(boolean clicked) {
        if (clicked) {
            fabPDF.setVisibility(View.VISIBLE);
            fabExcel.setVisibility(View.VISIBLE);
        } else {
            fabPDF.setVisibility(View.INVISIBLE);
            fabExcel.setVisibility(View.INVISIBLE);
        }
    }

    void setAnimation(boolean clicked) {
        if (clicked) {
            fabPDF.startAnimation(fromBottom);
            fabExcel.startAnimation(fromBottom);
        } else {
            fabPDF.startAnimation(toBottom);
            fabExcel.startAnimation(toBottom);
        }
    }


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
        fabConvert = findViewById(R.id.fabConvert);
        fabPDF = findViewById(R.id.fabPDF);
        fabExcel = findViewById(R.id.fabExcel);

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

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

    }


}