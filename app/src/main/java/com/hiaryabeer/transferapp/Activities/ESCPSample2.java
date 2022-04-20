package com.hiaryabeer.transferapp.Activities;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.R;
import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.command.ESCPOSConst;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ESCPSample2 {
    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;


    DecimalFormat decimalFormat;

    List<AllItems> itemList;

    Context context;

    public ESCPSample2(Context context) {
        posPtr = new ESCPOSPrinter();

        decimalFormat = new DecimalFormat("#.000");
        this.context = context;
//		posPtr = new ESCPOSPrinter("EUC-KR"); // Korean.
//		posPtr = new ESCPOSPrinter("BIG5"); // Big5
    }



    public void printMultilingualFontEsc3(int count, Voucher voucherforPrint, List<Item> itemforPrint) throws UnsupportedEncodingException {

        int nLineWidth = 550;// 550
        int alignment = 0;
        String line = "";

        String headerVoucher = "";
        if (printerType == 6) {
            alignment = ESCPOSConst.LK_ALIGNMENT_LEFT;
            nLineWidth = 370;
            headerVoucher = "العدد | " + "\t" + " السعر | " + " الخصم  | " + "\t" + " المجموع" + "\n";
            line = "-------------------------------------------------------";
        } else {
            nLineWidth = 550;
//			headerVoucher=" السلعة            " + "العدد " + "\t\t" + "السعر "+"\t\t"+"الخصم " + "\t\t" + "المجموع" + "\n" ;
            headerVoucher = " العدد    " + "\t\t" + "  السعر " + "\t\t" + "     الخصم     " + "\t\t" + "       المجموع " + "\n";
            alignment = ESCPOSConst.LK_ALIGNMENT_LEFT;
            line = "--------------------------------------------------------------------------------";
//			line="---------------------------------------------------------------";
        }


        double total_Qty = 0, itemDiscount = 0;

        try {

            String voucherTyp = "سند تحويل";


            posPtr.setAsync(false);

//			posPtr.printBitmap(companyInfo.getLogo(), ESCPOSConst.LK_ALIGNMENT_CENTER, 200);

//		 posPtr.printAndroidFont(null, true, "رقم الفاتورة : " + voucherforPrint.getVoucherNumber() + "\t\t" + voucherTyp + "\t\t" + (voucherforPrint.getPayMethod() == 0 ? "ذمم" : "نقدا") + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_CENTER);

            //posPtr.printAndroidFont(null, true,  voucherTyp +"\t\t"+(voucherforPrint.getPayMethod() == 0 ? "ذمم" : "نقدا") + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);

//				posPtr.printAndroidFont(null, true, line + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_CENTER);


//			posPtr.printAndroidFont(null, true, "اسم  المندوب    : " + salesmanName + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);
//			posPtr.printAndroidFont(null, true,  voucherTyp +"\t\t"+(voucherforPrint.getPayMethod() == 0 ? "ذمم" : "نقدا") + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);

            //posPtr.printAndroidFont(null, true, "نوع الفاتورة : " + voucherTyp + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);
            //posPtr.printAndroidFont(null, true, "طريقة الدفع  : " + (voucherforPrint.getPayMethod() == 0 ? "ذمم" : "نقدا") + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_LEFT);

            posPtr.printAndroidFont(null, true, line + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_CENTER);

            total_Qty = 0;
            itemDiscount = 0;

            posPtr.printAndroidFont(null, true, headerVoucher, nLineWidth, 25, alignment);
            posPtr.printAndroidFont(null, true, line + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_CENTER);

//				for (int i = 0; i < itemforPrint.size(); i++) {
//					if ((voucherforPrint.getVoucherNumber() == itemforPrint.get(i).getVoucherNumber()) && (itemforPrint.get(i).getVoucherType() == voucherforPrint.getVoucherType())) {
//						total_Qty += itemforPrint.get(i).getQty();
//
//						itemDiscount += itemforPrint.get(i).getDisc();
//						String amount = "" + (itemforPrint.get(i).getQty() * itemforPrint.get(i).getPrice() - itemforPrint.get(i).getDisc());
//						posPtr.printBitmap(itemPrint(itemforPrint.get(i).getPrice() + "", convertToEnglish(decimalFormat.format(Double.valueOf(convertToEnglish(amount)))), itemforPrint.get(i).getQty() + "", itemforPrint.get(i).getItemName(), itemforPrint.get(i).getDisc()), ESCPOSConst.LK_ALIGNMENT_CENTER, nLineWidth);
//
//					}
//				}


            double totalItemsDiscount = 0;
            try {
                totalItemsDiscount = itemDiscount;
            } catch (Exception e) {
                Log.e("getException", "totalItemsDiscount===");
            }
            //Log.e("getTotalVoucherDiscount",""+totalItemsDiscount+"\t"+(voucherforPrint.getSubTotal() + totalItemsDiscount));


            posPtr.printAndroidFont(null, true, line + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_CENTER);

            posPtr.printAndroidFont(null, true, "اجمالي الكمية  : " + convertToEnglish(decimalFormat.format(total_Qty)) + "\n", nLineWidth, 26, alignment);


            posPtr.printAndroidFont(null, true, line + "\n", nLineWidth, 24, ESCPOSConst.LK_ALIGNMENT_CENTER);

            posPtr.lineFeed(4);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    Bitmap itemPrint(String prices, String totals, String qtys, String items, float discount) {


        final Dialog dialogs = new Dialog(context);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setCancelable(true);
        dialogs.setContentView(R.layout.tabres);
        int textSize = 16;
        if (printerType == 6) {
            textSize = 20;
        }

        TextView price, total, qty, item, item_largeName, item_discount;

        price = (TextView) dialogs.findViewById(R.id.price);
        total = (TextView) dialogs.findViewById(R.id.total);
        qty = (TextView) dialogs.findViewById(R.id.qty);
        item = (TextView) dialogs.findViewById(R.id.ittem);
        item_discount = (TextView) dialogs.findViewById(R.id.item_discount);
        item_largeName = (TextView) dialogs.findViewById(R.id.ittem_largeName);
        LinearLayout linearView = (LinearLayout) dialogs.findViewById(R.id.tab);

//	prices=convertToEnglish(decimalFormat.format(prices));
//		totals=convertToEnglish(decimalFormat.format(totals));
        String discountStr = convertToEnglish(decimalFormat.format(discount));
//		prices=convertToEnglish(decimalFormat.format(prices));
        price.setText(prices);
        price.setTextSize(textSize);
        total.setText(totals);
        total.setTextSize(textSize);
        qty.setText(qtys);
        qty.setTextSize(textSize);
        Log.e("item_discount", "" + discount);

        Log.e("itemPrint", "" + items.length() + "\t items=" + items);
//	if(items.length()>19||printerType==6)
//	{

        item_largeName.setVisibility(View.VISIBLE);
        if (items.contains("(bonus)")) {
            item_largeName.setText("مجاني");
        } else
            item_largeName.setText(items);
        item_largeName.setTextSize(textSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            item_largeName.setTextDirection(View.TEXT_DIRECTION_LTR);
        }
        item.setText("item name");
        item.setVisibility(View.GONE);
        item_discount.setText(discountStr);
        item_discount.setTextSize(textSize);
        Log.e("itemPrint", "" + items.length() + items);
//	}
//	else {
//		if(items.contains("(bonus)"))
//		{
//			item.setText("مجاني");
//			item_largeName.setVisibility(View.GONE);
//			item.setVisibility(View.VISIBLE);
//			item.setTextSize(textSize);
//			item_discount.setText(discountStr);
//			item_discount.setTextSize(textSize);
//		}else {
//			item_largeName.setVisibility(View.GONE);
//			item.setVisibility(View.VISIBLE);
//			item.setText(items);
//			item.setTextSize(textSize);
//			item_discount.setText(discountStr);
//			item_discount.setTextSize(textSize);
//		}
//
//	}

//		item.setText(items);
        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(linearView.getWidth(), linearView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = linearView.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        linearView.draw(canvas);
        return bitmap;

    }

    public String getCurentTimeDate(int flag) {
        String dateCurent, timeCurrent, dateTime = "";
        Date currentTimeAndDate;
        SimpleDateFormat dateFormat, timeformat;
        currentTimeAndDate = Calendar.getInstance().getTime();
        if (flag == 1)// return date
        {

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateCurent = dateFormat.format(currentTimeAndDate);
            dateTime = convertToEnglish(dateCurent);

        } else {
            if (flag == 2)// return time
            {
                timeformat = new SimpleDateFormat("hh:mm");
                dateCurent = timeformat.format(currentTimeAndDate);
                dateTime = convertToEnglish(dateCurent);
            }
        }
        return dateTime;

    }


    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }


    public boolean ifEnglish(char value) {

        for (int i = 0; i < 28; i++) {
            if (((97 + i) == ((int) value)) || ((65 + i) == ((int) value))) {
                return true;
            }
        }
        return false;
    }


}
