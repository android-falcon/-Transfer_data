package com.hiaryabeer.transferapp.Activities;

import static com.hiaryabeer.transferapp.Models.ImportData.Storelist;
import static com.itextpdf.text.Element.ALIGN_BASELINE;
import static com.itextpdf.text.Element.ALIGN_CENTER;

import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hiaryabeer.transferapp.Adapters.TransReportsAdapter;
import com.hiaryabeer.transferapp.BuildConfig;
import com.hiaryabeer.transferapp.Models.GeneralMethod;

import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class TransferReports extends AppCompatActivity {
    public static TextView etPickDate;
    private ImageButton backBtn;
    private EditText etSearchItems;
    public static RecyclerView rvTransferReports;

    public static List<ReplacementModel> allReports = new ArrayList<>();
    private ArrayList<ReplacementModel> searchList = new ArrayList<>();
    public static TransReportsAdapter transReportsAdapter;

    private Calendar calendar;
    private int year, month, day;

    private RoomAllData myDB;

    private Spinner spinnerToStore;
    private ArrayList<String> storeArray = new ArrayList<>();
    private ArrayList<String> transArray = new ArrayList<>();

    private ImageView imgEmpty;
    private TextView tvEmpty;

    private FloatingActionButton fabConvert, fabPDF, fabExcel, fabShare;
    private Animation fromBottom, toBottom;
    private boolean clicked = false;

    private TextView tvVochNo;

    private static final String TAG = MainActivity.class.getSimpleName();

    private Spinner spinnerTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_reports);

//        Toolbar myToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        initViews();
        setVisibility(clicked);

        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL)
            backBtn.setRotationY(180);

        //Initialize Views
        myDB = RoomAllData.getInstanceDataBase(this);

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
                initTransSpinner();
                spinnerToStore.setSelection(0);
                etSearchItems.setText("");
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

/********* TransNo Filter *******/

        initTransSpinner();

        spinnerTrans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                filter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



/////////

/********* Initialize ToStore Spinner ********/

        storeArray.clear();
        Storelist.clear();
        Storelist = myDB.storeDao().getall();

        storeArray.add(getString(R.string.allStores));
        for (int r = 0; r < Storelist.size(); r++) {
            storeArray.add(Storelist.get(r).getSTORENAME());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, storeArray);
        spinnerToStore.setAdapter(spinnerAdapter);
        spinnerToStore.setSelection(0);

/////////

/********* Search Items By ToStore *******/

        spinnerToStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                filter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/////////


/********** Search Items ***********/
        etSearchItems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter();

            }
        });
/////////

/********** Convert into PDF or Excel *******/

        if (Login.serialsActive == 1) {
            fabConvert.setVisibility(View.VISIBLE);
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
//                    try {
//                        if (Build.VERSION.SDK_INT >= 23) {
//                            if (TransferReports.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                                    && (TransferReports.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
//                                createPdf2();
//                                Log.v("", "Permission is granted");
//                            } else {
//
//                                Log.v("", "Permission is revoked");
//                                ActivityCompat.requestPermissions(
//                                        TransferReports.this,
//                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
//                                        1);
//                            }
//                        } else { // permission is automatically granted on sdk<23 upon
//                            // installation
//                            createPdf2();
//                            Log.v("", "Permission is granted");
//                        }
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//                    convertToPdf();
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (TransferReports.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                                && (TransferReports.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                            try {
                                createPdf();
                            } catch (DocumentException | IOException e) {
                                e.printStackTrace();
                            }
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
                        try {
                            createPdf();
                        } catch (DocumentException | IOException e) {
                            e.printStackTrace();
                        }
                        Log.v("", "Permission is granted");
                    }


                }
            });

            fabExcel.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (TransferReports.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && (TransferReports.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

                        convertToExcel(true);

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

                    convertToExcel(true);

                    Log.v("", "Permission is granted");
                }

            });

            fabShare.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (TransferReports.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && (TransferReports.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

                        convertToExcel(false);

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

                    convertToExcel(false);

                    Log.v("", "Permission is granted");
                }

            });

        } else {
            fabConvert.setVisibility(View.GONE);
        }

        backBtn.setOnClickListener(v -> finish());

    }


/////////


    @Override
    public void onBackPressed() {
        finish();
    }

    /***************** METHODS *****************/


    public void createPdf() throws DocumentException, IOException {
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/Transfers_" + etPickDate.getText().toString().substring(0, 2) + "_" + etPickDate.getText().toString().substring(3, 5) + ".pdf");
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter writer = com.itextpdf.text.pdf.PdfWriter.getInstance(
                document, new FileOutputStream(filePath));
        writer.setRunDirection(com.itextpdf.text.pdf.PdfWriter.RUN_DIRECTION_RTL);

        document.setPageSize(PageSize.A3);
        document.open();

        BaseFont base = null;
        try {
            base = BaseFont.createFont("/assets/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        Font font0 = new Font(base, 14f, Font.NORMAL, new BaseColor(0, 0, 0));
        Font font1 = new Font(base, 15f, Font.NORMAL, new BaseColor(255, 255, 255));
        Font font2 = new Font(base, 17f, Font.NORMAL, new BaseColor(128, 128, 128));
        Font font3 = new Font(base, 18f, Font.BOLD, new BaseColor(0, 0, 0));
        Font font4 = new Font(base, 14f, Font.NORMAL, BaseColor.LIGHT_GRAY);

        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.transfer_logo);
        assert drawable != null;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(bitmapData);
        image.setAlignment(Element.ALIGN_CENTER);
        image.scaleAbsolute(100, 100);
        document.add(image);

//        com.itextpdf.text.Paragraph paragraph1 = new com.itextpdf.text.Paragraph(
//                getString(R.string.TransfersReportTitle),
//                font3);
//        paragraph1.setAlignment(Element.ALIGN_CENTER);
//
//        com.itextpdf.text.Paragraph paragraph2 = new com.itextpdf.text.Paragraph(
//                "\n\n" + getString(R.string.transactionDate) + ":  " + etPickDate.getText().toString(),
//                font2);
//
//        com.itextpdf.text.Paragraph paragraph3 = new com.itextpdf.text.Paragraph(
//                getString(R.string.trsnferNo) + ":  " + spinnerTrans.getSelectedItem() + "\n\n",
//                font2);   pHRAS
        Phrase phrase = new Phrase("\n");

        PdfPTable pdfPTableHeader = new PdfPTable(1);
        pdfPTableHeader.setWidthPercentage(100f);
        pdfPTableHeader.setSpacingAfter(20);

        PdfPTable pdfPTableHeader2 = new PdfPTable(1);
        pdfPTableHeader2.setWidthPercentage(100f);
        pdfPTableHeader2.setSpacingAfter(20);


        pdfPTableHeader.addCell(pdfCell(getString(R.string.TransfersReportTitle),
                ALIGN_CENTER, 1, font3, BaseColor.GRAY, false, false, null, true));

        document.add(phrase);
        document.add(pdfPTableHeader);
        document.add(phrase);

        pdfPTableHeader2.addCell(pdfCell(getString(R.string.transfer_date_) + " " + etPickDate.getText().toString(),
                ALIGN_BASELINE, 1, font2, BaseColor.BLACK, false, false, null, true));

        pdfPTableHeader2.addCell(pdfCell(getString(R.string.trsnferNo) + " : " + spinnerTrans.getSelectedItem(),
                ALIGN_BASELINE, 1, font2, BaseColor.BLACK, false, false, null, true));


        document.add(pdfPTableHeader2);

        float[] columnWidth = {100f, 100f, 100f, 150f, 150f, 100f, 150f};

        PdfPTable pdfPTableHeader3 = new PdfPTable(columnWidth);
        pdfPTableHeader3.setWidthPercentage(100);
        pdfPTableHeader3.setSpacingBefore(0f);
        pdfPTableHeader3.setSpacingAfter(0f);

        if (!Locale.getDefault().getLanguage().equals("ar")) {
            pdfPTableHeader3.addCell(pdfCell(getString(R.string.trsnferNo),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.fromStore),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.toStore),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.Itemname),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.itemCode),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.Qty),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.serials),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            document.add(pdfPTableHeader3);

            PdfPTable pdfPTableBody = new PdfPTable(columnWidth);
            pdfPTableBody.setWidthPercentage(100);
            pdfPTableBody.setSpacingBefore(0f);
            pdfPTableBody.setSpacingAfter(0f);

            List<String> serialCodes = new ArrayList<>();
            for (int i = 0; i < allReports.size(); i++) {
                serialCodes.clear();
                serialCodes = myDB.serialTransfersDao().getSerialCodes(allReports.get(i).getTransNumber(), allReports.get(i).getItemcode());
                Log.e("Serials Size", serialCodes.size() + "");
                if (allReports.get(i).getIsPosted().equals("0")) {

//                table.addCell(new Cell(serialCodes.size(), 1).setBackgroundColor(new DeviceRgb(214, 214, 214)).add(new Paragraph(String.valueOf(reportsList.get(i).getReplacementDate()))));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getTransNumber()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getFromName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getToName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemname()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemcode()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getRecQty()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));


                    if (serialCodes.size() > 0) {
                        for (int j = 0; j < serialCodes.size(); j++) {
                            Log.e("size > 0", "size > 0");
                            pdfPTableBody.addCell(pdfCell(String.valueOf(serialCodes.get(j)),
                                    ALIGN_CENTER, 1, font0, BaseColor.BLACK, true, true,
                                    new BaseColor(214, 214, 214), false));
                        }
                    } else {
                        Log.e("size = 0", "size = 0");
                        pdfPTableBody.addCell(pdfCell("   ",
                                ALIGN_CENTER, 1, font4, BaseColor.BLACK, true, true,
                                new BaseColor(214, 214, 214), false));
                    }


                } else {
//                table.addCell(new Cell(serialCodes.size(), 1).setBackgroundColor(new DeviceRgb(213, 235, 255)).add(new Paragraph(String.valueOf(reportsList.get(i).getReplacementDate()))));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getTransNumber()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getFromName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getToName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemname()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemcode()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getRecQty()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

                    if (serialCodes.size() > 0) {
                        for (int j = 0; j < serialCodes.size(); j++) {
                            Log.e("size > 0", "size > 0");
                            pdfPTableBody.addCell(pdfCell(String.valueOf(serialCodes.get(j)),
                                    ALIGN_CENTER, 1, font0, BaseColor.BLACK, true, true,
                                    new BaseColor(255, 255, 255), false));
                        }
                    } else {
                        Log.e("size = 0", "size = 0");
                        pdfPTableBody.addCell(pdfCell("   ",
                                ALIGN_CENTER, 1, font4, BaseColor.BLACK, true, true,
                                new BaseColor(255, 255, 255), false));
                    }

                }

            }

            document.add(pdfPTableBody);
        } else {

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.serials),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.Qty),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.itemCode),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.Itemname),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.toStore),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.fromStore),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            pdfPTableHeader3.addCell(pdfCell(getString(R.string.trsnferNo),
                    ALIGN_CENTER, 1, font1, BaseColor.BLACK, true, true,
                    new BaseColor(240, 123, 82), false));

            document.add(pdfPTableHeader3);

            PdfPTable pdfPTableBody = new PdfPTable(columnWidth);
            pdfPTableBody.setWidthPercentage(100);
            pdfPTableBody.setSpacingBefore(0f);
            pdfPTableBody.setSpacingAfter(0f);

            List<String> serialCodes = new ArrayList<>();
            for (int i = 0; i < allReports.size(); i++) {
                serialCodes.clear();
                serialCodes = myDB.serialTransfersDao().getSerialCodes(allReports.get(i).getTransNumber(), allReports.get(i).getItemcode());
                Log.e("Serials Size", serialCodes.size() + "");

                if (allReports.get(i).getIsPosted().equals("0")) {
                    if (serialCodes.size() > 0) {
                        Log.e("size > 0", "size > 0");
                        pdfPTableBody.addCell(pdfCell(String.valueOf(serialCodes.get(0)),
                                ALIGN_CENTER, 1, font0, BaseColor.BLACK, true, true,
                                new BaseColor(214, 214, 214), false));
                    } else {
                        Log.e("size = 0", "size = 0");
                        pdfPTableBody.addCell(pdfCell("   ",
                                ALIGN_CENTER, 1, font4, BaseColor.BLACK, true, true,
                                new BaseColor(214, 214, 214), false));
                    }

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getRecQty()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemcode()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemname()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getToName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getFromName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));

//                table.addCell(new Cell(serialCodes.size(), 1).setBackgroundColor(new DeviceRgb(214, 214, 214)).add(new Paragraph(String.valueOf(reportsList.get(i).getReplacementDate()))));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getTransNumber()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(214, 214, 214), false));

                    if (serialCodes.size() > 0) {
                        for (int j = 1; j < serialCodes.size(); j++) {
                            pdfPTableBody.addCell(pdfCell(String.valueOf(serialCodes.get(j)),
                                    ALIGN_CENTER, 1, font0, BaseColor.BLACK, true, true,
                                    new BaseColor(214, 214, 214), false));
                        }
                    }


                } else {
                    if (serialCodes.size() > 0) {

                        Log.e("size > 0", "size > 0");
                        pdfPTableBody.addCell(pdfCell(String.valueOf(serialCodes.get(0)),
                                ALIGN_CENTER, 1, font0, BaseColor.BLACK, true, true,
                                new BaseColor(255, 255, 255), false));

                    } else {
                        Log.e("size = 0", "size = 0");
                        pdfPTableBody.addCell(pdfCell("   ",
                                ALIGN_CENTER, 1, font4, BaseColor.BLACK, true, true,
                                new BaseColor(255, 255, 255), false));
                    }

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getRecQty()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemcode()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getItemname()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getToName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getFromName()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

//                table.addCell(new Cell(serialCodes.size(), 1).setBackgroundColor(new DeviceRgb(213, 235, 255)).add(new Paragraph(String.valueOf(reportsList.get(i).getReplacementDate()))));
                    pdfPTableBody.addCell(pdfCell(String.valueOf(allReports.get(i).getTransNumber()),
                            ALIGN_CENTER, serialCodes.size(), font0, BaseColor.BLACK, true, true,
                            new BaseColor(255, 255, 255), false));

                    if (serialCodes.size() > 0) {
                        for (int j = 1; j < serialCodes.size(); j++) {
                            pdfPTableBody.addCell(pdfCell(String.valueOf(serialCodes.get(j)),
                                    ALIGN_CENTER, 1, font0, BaseColor.BLACK, true, true,
                                    new BaseColor(255, 255, 255), false));
                        }
                    }

                }

            }

            document.add(pdfPTableBody);

        }

        document.close();
        Log.e("PDF Created", filePath + "");
        Toast.makeText(this, "PDF Created", Toast.LENGTH_LONG).show();

        try {
            Log.e("startViewPdf", "PDF");
            Uri path = FileProvider.getUriForFile(TransferReports.this, getApplicationContext().getPackageName() + ".provider", filePath);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
            objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            objIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                TransferReports.this.startActivity(objIntent);
            } catch (Exception e) {
                Toast.makeText(TransferReports.this, "Pdf program needed!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ExceptionPdf", "" + e.getMessage());
        }
    }

    public PdfPCell pdfCell(String text, int align, int rowSpan, Font font, BaseColor borderColor,
                            boolean bor, boolean background, BaseColor backColor, boolean isHeader) {

        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(align);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(align);
        cell.setRowspan(rowSpan);

        if (bor) {
            cell.setBorderColor(borderColor);
        } else
            cell.setBorder(Rectangle.NO_BORDER);

        if (background)
            cell.setBackgroundColor(backColor);

        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(15f);
        }
        if (!isHeader)
            cell.setMinimumHeight(40);

        if (Locale.getDefault().getLanguage().equals("ar"))
            cell.setRunDirection(com.itextpdf.text.pdf.PdfWriter.RUN_DIRECTION_RTL);
        else
            cell.setRunDirection(com.itextpdf.text.pdf.PdfWriter.RUN_DIRECTION_LTR);

        return cell;
    }

//    private File convertToPdf() {
//        PdfConverter pdf = new PdfConverter(TransferReports.this);
//        File file = pdf.exportListToPdf(reportsList, "Transfers Report", etPickDate.getText().toString());
//        return file;
//    }

    private void initTransSpinner() {
        transArray.clear();
        transArray.add(getString(R.string.allTransfers));
        transArray.addAll(myDB.replacementDao().getTranByDate(etPickDate.getText().toString().trim()));


        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<>(
                this, R.layout.spinner_item, transArray);
        spinnerTrans.setAdapter(spinnerAdapter1);
        spinnerTrans.setSelection(0);
    }

    private void convertToExcel(boolean open) {
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/Transfers_" + etPickDate.getText().toString().substring(0, 2) + "_" + etPickDate.getText().toString().substring(3, 5) + ".xls");
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());


        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        org.apache.poi.ss.usermodel.Font defaultFont = workbook.createFont();
        defaultFont.setFontName("Arial");
        defaultFont.setColor(IndexedColors.BLACK.getIndex());
        defaultFont.setBold(true);
        defaultFont.setItalic(false);

        Row row = sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(0);
        cell.setCellValue(getString(R.string.transfer_date_) + " " + etPickDate.getText().toString());
        CellStyle ccs = workbook.createCellStyle();
        ccs.setAlignment(CellStyle.ALIGN_CENTER);
        ccs.setVerticalAlignment(VERTICAL_CENTER);
        ccs.setBorderRight(CellStyle.BORDER_THIN);
        ccs.setRightBorderColor(IndexedColors.BLUE.getIndex());
        ccs.setBorderLeft(CellStyle.BORDER_THIN);
        ccs.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        ccs.setBorderTop(CellStyle.BORDER_THIN);
        ccs.setTopBorderColor(IndexedColors.BLUE.getIndex());
        ccs.setFont(defaultFont);
        cell.setCellStyle(ccs);

        // Create a cellRangeAddress to select a range to merge.
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);

        // Merge the selected cells.
        sheet.addMergedRegion(cellRangeAddress);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(getString(R.string.trsnferNo) + " : " + spinnerTrans.getSelectedItem().toString());

        CellStyle ccs1 = workbook.createCellStyle();
        ccs1.setAlignment(CellStyle.ALIGN_CENTER);
        ccs1.setVerticalAlignment(VERTICAL_CENTER);
        ccs1.setBorderRight(CellStyle.BORDER_THIN);
        ccs1.setRightBorderColor(IndexedColors.BLUE.getIndex());
        ccs1.setBorderLeft(CellStyle.BORDER_THIN);
        ccs1.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        ccs1.setFont(defaultFont);
        cell.setCellStyle(ccs1);

        // Create a cellRangeAddress to select a range to merge.
        cellRangeAddress = new CellRangeAddress(1, 1, 0, 6);

        // Merge the selected cells.
        sheet.addMergedRegion(cellRangeAddress);

        row = sheet.createRow(2);


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(VERTICAL_CENTER);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(defaultFont);

        org.apache.poi.ss.usermodel.Cell cell1 = null;

        if (!Locale.getDefault().getLanguage().equals("ar")) {

            cell1 = row.createCell(0);
            cell1.setCellValue(getString(R.string.trsnferNo));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(1);
            cell1.setCellValue(getString(R.string.fromStore));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(2);
            cell1.setCellValue(getString(R.string.toStore));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(3);
            cell1.setCellValue(getString(R.string.Itemname));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(4);
            cell1.setCellValue(getString(R.string.itemCode));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(5);
            cell1.setCellValue(getString(R.string.Qty));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(6);
            cell1.setCellValue(getString(R.string.serials));
            cell1.setCellStyle(cellStyle);

            List<String> serialCodes = new ArrayList<>();

            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle1.setVerticalAlignment(VERTICAL_CENTER);
            cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle1.setRightBorderColor(IndexedColors.BLUE.getIndex());
            cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle1.setLeftBorderColor(IndexedColors.BLUE.getIndex());
            cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle1.setTopBorderColor(IndexedColors.BLUE.getIndex());
            cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle1.setBottomBorderColor(IndexedColors.BLUE.getIndex());

            for (int r = 3; r <= allReports.size() + 2; r++) {

                row = sheet.createRow(r);
                serialCodes.clear();
                serialCodes = myDB.serialTransfersDao().getSerialCodes(allReports.get(r - 3).getTransNumber(), allReports.get(r - 3).getItemcode());

                row.setHeightInPoints(((serialCodes.size() + 2) * sheet.getDefaultRowHeightInPoints()));

                cell1 = row.createCell(0);
                cell1.setCellValue(allReports.get(r - 3).getTransNumber());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(1);
                cell1.setCellValue(allReports.get(r - 3).getFromName());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(2);
                cell1.setCellValue(allReports.get(r - 3).getToName());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(3);
                cell1.setCellValue(allReports.get(r - 3).getItemname());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(4);
                cell1.setCellValue(allReports.get(r - 3).getItemcode());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(5);
                cell1.setCellValue(allReports.get(r - 3).getRecQty());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(6);

                Log.e("Serials_Size ", serialCodes.size() + "");
                if (serialCodes.size() > 0) {
                    StringBuilder s = new StringBuilder();
                    for (int j = 0; j < serialCodes.size(); j++) {
                        Log.e("size > 0", "size > 0");
                        if (j == (serialCodes.size() - 1))
                            s.append(serialCodes.get(j));
                        else
                            s.append(serialCodes.get(j)).append("\n");
                    }
                    cell1.setCellValue(s.toString());
                } else {
                    Log.e("size = 0", "size = 0");
                    cell1.setCellValue("   ");
                }
                CellStyle cs = workbook.createCellStyle();
                cs.setAlignment(CellStyle.ALIGN_CENTER);
                cs.setWrapText(true);
                cs.setAlignment(CellStyle.ALIGN_CENTER);
                cs.setVerticalAlignment(VERTICAL_CENTER);
                cs.setBorderRight(CellStyle.BORDER_THIN);
                cs.setRightBorderColor(IndexedColors.BLUE.getIndex());
                cs.setBorderLeft(CellStyle.BORDER_THIN);
                cs.setLeftBorderColor(IndexedColors.BLUE.getIndex());
                cs.setBorderTop(CellStyle.BORDER_THIN);
                cs.setTopBorderColor(IndexedColors.BLUE.getIndex());
                cs.setBorderBottom(CellStyle.BORDER_THIN);
                cs.setBottomBorderColor(IndexedColors.BLUE.getIndex());
                cell1.setCellStyle(cs);

            }
//        sheet.autoSizeColumn(0);
//        sheet.autoSizeColumn(1);
//        sheet.autoSizeColumn(2);
//        sheet.autoSizeColumn(3);
//        sheet.autoSizeColumn(4);
//        sheet.autoSizeColumn(5);
//        sheet.autoSizeColumn(6);
            sheet.setColumnWidth(0, 5030);
            sheet.setColumnWidth(1, 5030);
            sheet.setColumnWidth(2, 5030);
            sheet.setColumnWidth(3, 5050);
            sheet.setColumnWidth(4, 5030);
            sheet.setColumnWidth(5, 5030);
            sheet.setColumnWidth(6, 5100);
        } else {

            cell1 = row.createCell(0);
            cell1.setCellValue(getString(R.string.serials));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(1);
            cell1.setCellValue(getString(R.string.Qty));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(2);
            cell1.setCellValue(getString(R.string.itemCode));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(3);
            cell1.setCellValue(getString(R.string.Itemname));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(4);
            cell1.setCellValue(getString(R.string.toStore));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(5);
            cell1.setCellValue(getString(R.string.fromStore));
            cell1.setCellStyle(cellStyle);

            cell1 = row.createCell(6);
            cell1.setCellValue(getString(R.string.trsnferNo));
            cell1.setCellStyle(cellStyle);


            List<String> serialCodes = new ArrayList<>();

            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle1.setVerticalAlignment(VERTICAL_CENTER);
            cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle1.setRightBorderColor(IndexedColors.BLUE.getIndex());
            cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle1.setLeftBorderColor(IndexedColors.BLUE.getIndex());
            cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle1.setTopBorderColor(IndexedColors.BLUE.getIndex());
            cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle1.setBottomBorderColor(IndexedColors.BLUE.getIndex());

            for (int r = 3; r <= allReports.size() + 2; r++) {

                row = sheet.createRow(r);
                serialCodes.clear();
                serialCodes = myDB.serialTransfersDao().getSerialCodes(allReports.get(r - 3).getTransNumber(), allReports.get(r - 3).getItemcode());

                row.setHeightInPoints(((serialCodes.size() + 2) * sheet.getDefaultRowHeightInPoints()));

                cell1 = row.createCell(0);

                Log.e("Serials_Size ", serialCodes.size() + "");
                if (serialCodes.size() > 0) {
                    StringBuilder s = new StringBuilder();
                    for (int j = 0; j < serialCodes.size(); j++) {
                        Log.e("size > 0", "size > 0");
                        if (j == (serialCodes.size() - 1))
                            s.append(serialCodes.get(j));
                        else
                            s.append(serialCodes.get(j)).append("\n");
                    }
                    cell1.setCellValue(s.toString());
                } else {
                    Log.e("size = 0", "size = 0");
                    cell1.setCellValue("   ");
                }
                CellStyle cs = workbook.createCellStyle();
                cs.setAlignment(CellStyle.ALIGN_CENTER);
                cs.setWrapText(true);
                cs.setVerticalAlignment(VERTICAL_CENTER);
                cs.setBorderRight(CellStyle.BORDER_THIN);
                cs.setRightBorderColor(IndexedColors.BLUE.getIndex());
                cs.setBorderLeft(CellStyle.BORDER_THIN);
                cs.setLeftBorderColor(IndexedColors.BLUE.getIndex());
                cs.setBorderTop(CellStyle.BORDER_THIN);
                cs.setTopBorderColor(IndexedColors.BLUE.getIndex());
                cs.setBorderBottom(CellStyle.BORDER_THIN);
                cs.setBottomBorderColor(IndexedColors.BLUE.getIndex());
                cell1.setCellStyle(cs);

                cell1 = row.createCell(1);
                cell1.setCellValue(allReports.get(r - 3).getRecQty());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(2);
                cell1.setCellValue(allReports.get(r - 3).getItemcode());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(3);
                cell1.setCellValue(allReports.get(r - 3).getItemname());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(4);
                cell1.setCellValue(allReports.get(r - 3).getToName());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(5);
                cell1.setCellValue(allReports.get(r - 3).getFromName());
                cell1.setCellStyle(cellStyle1);

                cell1 = row.createCell(6);
                cell1.setCellValue(allReports.get(r - 3).getTransNumber());
                cell1.setCellStyle(cellStyle1);

            }
//        sheet.autoSizeColumn(0);
//        sheet.autoSizeColumn(1);
//        sheet.autoSizeColumn(2);
//        sheet.autoSizeColumn(3);
//        sheet.autoSizeColumn(4);
//        sheet.autoSizeColumn(5);
//        sheet.autoSizeColumn(6);
            sheet.setColumnWidth(0, 5100);
            sheet.setColumnWidth(1, 5030);
            sheet.setColumnWidth(2, 5030);
            sheet.setColumnWidth(3, 5050);
            sheet.setColumnWidth(4, 5030);
            sheet.setColumnWidth(5, 5030);
            sheet.setColumnWidth(6, 5030);
        }


        try {
            if (!filePath.exists()) {
                filePath.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);

            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("Excel File Created", filePath + "");

        if (open) {
            Toast.makeText(this, "Excel File Created", Toast.LENGTH_LONG).show();
            try {
                Log.e("startViewExcel", "Excel");
                Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri uri = FileProvider.getUriForFile(TransferReports.this, BuildConfig.APPLICATION_ID + ".provider", filePath);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "application/vnd.ms-excel");
                try {
                    TransferReports.this.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(TransferReports.this, "Excel program needed!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("ExceptionExc", "" + e.getMessage());
            }
        } else {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            Uri uri = FileProvider.getUriForFile(TransferReports.this, BuildConfig.APPLICATION_ID + ".provider", filePath);
            intentShare.setType("application/vnd.ms-excel");
            intentShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.TransfersReportTitle));
            intentShare.putExtra(Intent.EXTRA_STREAM, uri);

            TransferReports.this.startActivity(Intent.createChooser(intentShare, getString(R.string.shareTo)));
        }
    }


    void setVisibility(boolean clicked) {

        if (clicked) {
            fabPDF.setVisibility(View.VISIBLE);
            fabExcel.setVisibility(View.VISIBLE);
            fabShare.setVisibility(View.VISIBLE);

        } else {
            fabPDF.setVisibility(View.INVISIBLE);
            fabExcel.setVisibility(View.INVISIBLE);
            fabShare.setVisibility(View.INVISIBLE);
        }

    }

    void setAnimation(boolean clicked) {

        if (clicked) {
            fabPDF.startAnimation(fromBottom);
            fabExcel.startAnimation(fromBottom);
            fabShare.startAnimation(fromBottom);
        } else {
            fabPDF.startAnimation(toBottom);
            fabExcel.startAnimation(toBottom);
            fabShare.startAnimation(toBottom);
        }

    }

    private void filter() {

        String item = etSearchItems.getText().toString().trim();
        int storePosition = spinnerToStore.getSelectedItemPosition();
        int transPosition = spinnerTrans.getSelectedItemPosition();


        if (storePosition != 0) {

            if (transPosition == 0 && item.length() == 0)
                searchByToStore();
            else if (transPosition != 0 && item.length() == 0)
                searchByTransAndStore();
            else if (transPosition == 0)
                searchByItemAndStore();
            else {
                searchByAll();
            }

        } else {

            if (transPosition == 0 && item.length() == 0)
                initRecView();
            else if (transPosition != 0 && item.length() == 0)
                searchByTransNo();
            else if (transPosition == 0)
                searchByItem();
            else {
                searchByItemAnTrans();
            }

        }

    }

    private void searchByAll() {

        String item = etSearchItems.getText().toString().toLowerCase().trim();

        searchList.clear();

        for (int s = 0; s < allReports.size(); s++) {

            if (allReports.get(s).getTransNumber().equals(spinnerTrans.getSelectedItem().toString()) &&
                    (allReports.get(s).getItemname().toLowerCase().contains(item) ||
                            allReports.get(s).getItemcode().toLowerCase().contains(item)) &&
                    allReports.get(s).getToName().contains(spinnerToStore.getSelectedItem().toString())) {

                searchList.add(allReports.get(s));
            }

        }

        updateRecView(searchList);

    }

    private void searchByItemAnTrans() {

        String item = etSearchItems.getText().toString().toLowerCase().trim();

        searchList.clear();

        for (int s = 0; s < allReports.size(); s++) {

            if (allReports.get(s).getTransNumber().equals(spinnerTrans.getSelectedItem().toString()) &&
                    (allReports.get(s).getItemname().toLowerCase().contains(item) ||
                            allReports.get(s).getItemcode().toLowerCase().contains(item))) {
                searchList.add(allReports.get(s));
            }

        }

        updateRecView(searchList);

    }

    private void searchByTransAndStore() {

        searchList.clear();

        for (int s = 0; s < allReports.size(); s++) {

            if (allReports.get(s).getTransNumber().equals(spinnerTrans.getSelectedItem().toString()) &&
                    allReports.get(s).getToName().contains(spinnerToStore.getSelectedItem().toString())) {
                searchList.add(allReports.get(s));
            }

        }

        updateRecView(searchList);

    }

    private void searchByTransNo() {

        searchList.clear();

        for (int s = 0; s < allReports.size(); s++) {

            if (allReports.get(s).getTransNumber().equals(spinnerTrans.getSelectedItem().toString())) {
                searchList.add(allReports.get(s));
            }

        }

        updateRecView(searchList);

    }

    private void searchByItemAndStore() {

        searchList.clear();
        String item = etSearchItems.getText().toString().toLowerCase().trim();
        String toStore = spinnerToStore.getSelectedItem().toString();

        for (int i = 0; i < allReports.size(); i++) {
            if (allReports.get(i).getToName().contains(toStore) &&
                    (allReports.get(i).getItemname().toLowerCase().contains(item) ||
                            allReports.get(i).getItemcode().toLowerCase().contains(item))) {
                searchList.add(allReports.get(i));
            }
        }

        updateRecView(searchList);

    }

    private void searchByToStore() {

        searchList.clear();

        for (int s = 0; s < allReports.size(); s++) {

            if (allReports.get(s).getToName().contains(spinnerToStore.getSelectedItem().toString())) {
                searchList.add(allReports.get(s));
            }

        }

        updateRecView(searchList);

    }

    private void searchByItem() {
        searchList.clear();
        String item = etSearchItems.getText().toString().toLowerCase().trim();

        for (int i = 0; i < allReports.size(); i++) {

            if (allReports.get(i).getItemname().toLowerCase().contains(item) ||
                    allReports.get(i).getItemcode().toLowerCase().contains(item)) {
                searchList.add(allReports.get(i));
            }

        }

        updateRecView(searchList);

    }

    private void initRecView() {
        String date = etPickDate.getText().toString().trim();

        allReports = myDB.replacementDao().getReplacementsByDate(date);

        transReportsAdapter = new TransReportsAdapter(this, allReports);
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

    private void updateRecView(List<ReplacementModel> newList) {

        transReportsAdapter = new TransReportsAdapter(this, newList);
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
        etSearchItems = findViewById(R.id.etSearchItems);

        /////Buttons
        fabConvert = findViewById(R.id.fabConvert);
        fabPDF = findViewById(R.id.fabPDF);
        fabExcel = findViewById(R.id.fabExcel);
        fabShare = findViewById(R.id.fabShare);

        /////Recycler View
        rvTransferReports = findViewById(R.id.rvTransferReports);

        /////Text Views
        tvEmpty = findViewById(R.id.tvEmpty);

        ////Spinner
        spinnerToStore = findViewById(R.id.spinnerToStore);
        spinnerTrans = findViewById(R.id.spinnerTrans);

        ////ImageView
        imgEmpty = findViewById(R.id.imgEmpty);
        backBtn = findViewById(R.id.backBtn);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

    }


}