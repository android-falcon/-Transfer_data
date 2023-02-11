package com.hiaryabeer.transferapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.transferapp.Activities.Login;
import com.hiaryabeer.transferapp.Activities.MainActivity;
import com.hiaryabeer.transferapp.Models.ItemsUnit;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.Models.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import java.util.ArrayList;
import java.util.List;

import static com.hiaryabeer.transferapp.Activities.MainActivity.New_replacementlist;
import static com.hiaryabeer.transferapp.Activities.MainActivity.adapter;
import static com.hiaryabeer.transferapp.Activities.Login.serialsActive;
import static com.hiaryabeer.transferapp.Activities.MainActivity.highligtedItemPosition;
import static com.hiaryabeer.transferapp.Activities.MainActivity.highligtedItemPosition2;
import static com.hiaryabeer.transferapp.Activities.MainActivity.itemcode;
import static com.hiaryabeer.transferapp.Activities.MainActivity.save;
import static com.hiaryabeer.transferapp.Activities.MainActivity.replacementlist;


public class ReplacementAdapter extends RecyclerView.Adapter<ReplacementAdapter.replacementViewHolder> {
    public static List<ReplacementModel> list;
    Context context;
    String newqty;
    public RoomAllData my_dataBase;
    List<String> itemUnits = new ArrayList<>();
    private int recyclerViewCurrentScrolledPosition;

//    List<ItemSerialTransfer> serialsList;
//    EditSerialAdapter editSerialAdapter;

    public ReplacementAdapter(List<ReplacementModel> list, Context context) {
        ReplacementAdapter.list = list;
        this.context = context;

        save.setEnabled(list.size() != 0);

    }

    @NonNull
    @Override
    public replacementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacmentrecycler, parent, false);
        return new replacementViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull replacementViewHolder holder, int position) {

        // Gets the layout params that will allow you to resize the layout
        itemUnits.clear();
        itemUnits.add("One Unit");
        itemUnits.addAll(my_dataBase.itemsUnitDao().getItemUnits(list.get(position).getItemcode()));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemUnits);
        holder.unitSpinner.setAdapter(arrayAdapter);

        holder.from.setText(list.get(position).getFromName());
        holder.to.setText(list.get(position).getToName());
        holder.itemname.setText(list.get(position).getItemname());
        holder.TransferNo.setText(list.get(position).getTransNumber());
        //   holder.zone.setText(list.get(position).getZone());
          holder.itemcode.setText(list.get(position).getItemcode());
        //    Log.e("onBindViewHolder202020",""+list.get(position).getRecQty());
        holder.qty.setText(list.get(position).getRecQty());

        if (Login.serialsActive == 0) {
            holder.qty.setEnabled(true);

        }else
        {
            List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(list.get(holder.getAdapterPosition()).getItemcode());
          Log.e("hasSerial==",hasSerial.size()+"");

                if(hasSerial.get(0).equals("0")) holder.qty.setEnabled(true);

            else
                holder.qty.setEnabled(false);


        }
        if(MainActivity.internalOrderFalge==1)
        {   holder.unitSpinner.setEnabled(false);
        holder.qty.setEnabled(false);
            holder.tvRemove.setVisibility(View.GONE);
        holder.updatedQTY.setText(list.get(position).getUpdatedQty());
        holder.updatedQTY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    if(!s.equals("0"))
                    list.get(holder.getAdapterPosition()).setUpdatedQty(holder.updatedQTY.getText().toString());
             else {
                        holder.updatedQTY.setText(New_replacementlist.get(holder.getAdapterPosition()).getRecQty());
                        holder.qty.setError(context.getResources().getString(R.string.qtyerror3));

                    }  }
            }
        });}
//        holder.itemcode.setTag(position);
//        holder.tvRemove.setTag(position);
        //   holder.itemqty.setText(list.get(position).getQty());
        holder.itemqty.setEnabled(false);

       // if(!list.get(position).getUNITBARCODE().equals(""))  holder.unitSpinner.setSelection(1);
///////ayah
        if(Login.serialsActive == 0)
        {
            if(list.get(position).getUNITBARCODE()!=null)
        {if(list.get(position).getUNITBARCODE().equals(""))
        { List<String> itemUnits1 = new ArrayList<>();

            itemUnits1.add("One Unit");
            itemUnits1.addAll(my_dataBase.itemsUnitDao().getItemUnits(list.get(position).getItemcode()));

            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemUnits1);

            holder.unitSpinner.setAdapter(arrayAdapter1);
            try {
                for(int i=0;i<itemUnits1.size();i++)
                    if(itemUnits1.get(i).equals(list.get(position).getUnitID()))
                        holder.unitSpinner.setSelection(i);

            }catch (Exception e){
                Log.e("Exception",e.getMessage());
            }
        }
        else {

            List<String> itemUnits2 = new ArrayList<>();

            // itemUnits.add("One Unit");
            ItemsUnit itemsUnit2=my_dataBase.itemsUnitDao().getItemUnit2(list.get(position).getUNITBARCODE());
            itemUnits2.add(itemsUnit2.getITEMU());

            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemUnits2);

            holder.unitSpinner.setAdapter(arrayAdapter2);

        }}else
        {
            List<String> itemUnits3 = new ArrayList<>();

            itemUnits3.add("One Unit");
            itemUnits3.addAll(my_dataBase.itemsUnitDao().getItemUnits(list.get(position).getItemcode()));

            ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemUnits3);

            holder.unitSpinner.setAdapter(arrayAdapter3);
            try {
                for(int i=0;i<itemUnits.size();i++)
                    if(itemUnits.get(i).equals(list.get(position).getUnitID()))
                        holder.unitSpinner.setSelection(i);

            }catch (Exception e){
                Log.e("Exception",e.getMessage());
            }
        }}
        //////ayah
        if(Login.serialsActive == 0)
        holder. unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String CountOfItems=my_dataBase.itemsUnitDao().getConvRate(list.get(holder.getAdapterPosition()).getItemcode(), holder.unitSpinner.getSelectedItem().toString());
                Log.e("CountOfItems",CountOfItems+"");
                ItemsUnit itemsUnit= my_dataBase.itemsUnitDao(). getItemUnit(list.get(holder.getAdapterPosition()).getItemcode(), holder.unitSpinner.getSelectedItem().toString());
               if(pos!=0)
                   list.get(holder.getAdapterPosition()).setCal_Qty(String.valueOf( Double.parseDouble(list.get(holder.getAdapterPosition()).getRecQty())*Double.parseDouble(CountOfItems)));
               else
                   list.get(holder.getAdapterPosition()).setCal_Qty(list.get(holder.getAdapterPosition()).getRecQty());



               list.get(holder.getAdapterPosition()).setUnitID(holder.unitSpinner.getSelectedItem().toString());
                my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                        list.get(holder.getAdapterPosition()).getRecQty(),
                        list.get(holder.getAdapterPosition()).getTransNumber(),
                        list.get(holder.getAdapterPosition()).getCal_Qty()    );



             int y=   my_dataBase.replacementDao().UpdateUnitId(list.get(holder.getAdapterPosition()).getItemcode(),list.get(holder.getAdapterPosition()).getTransNumber()
                , list.get(holder.getAdapterPosition()).getUnitID());
                Log.e("y==",y+"");

                if(!holder.unitSpinner.getSelectedItem().toString().equals("One Unit"))
                    my_dataBase.replacementDao().updateUnitSetting(list.get(holder.getAdapterPosition()).getTransNumber(),list.get(holder.getAdapterPosition()).getItemcode(),list.get(holder.getAdapterPosition()).getRecQty(),CountOfItems,itemsUnit.getSALEPRICE(),itemsUnit.getITEMBARCODE(),itemsUnit.getUSERIAL(),itemsUnit.getITEMU());
                Log.e("getSelectedItem",holder.unitSpinner.getSelectedItem().toString()+"");
                Log.e("Itemcode",list.get(holder.getAdapterPosition()).getItemcode()+"");
                Log.e("CountOfItems",CountOfItems+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




//        holder.qty.setEnabled(Login.serialsActive == 0);

        if (Login.serialsActive == 1) {
            List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(MainActivity.itemcode.getText().toString().trim());





            Log.e("itemUnits,itemUnits",""+itemUnits.size());
            // List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(list.get(position).getItemcode());
Log.e("getItemcode,hasSerial",list.get(position).getItemcode()+""+hasSerial.size());
         if(hasSerial.size()!=0)
             if (hasSerial.get(0).equals("1")) {
              //  holder.qty.setEnabled(false);
                holder.qty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (s.toString().equals("0")) {
                            my_dataBase.replacementDao().deleteReplacement(list.get(holder.getAdapterPosition()).getItemcode(),
                                    list.get(holder.getAdapterPosition()).getFrom(), list.get(holder.getAdapterPosition()).getTo(), list.get(holder.getAdapterPosition()).getTransNumber());
                            list.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                        }

                    }
                });
            } else
            {

                //holder.qty.setEnabled(true);
                holder.qty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (s.toString().length() != 0) {
                            try {
//                            int pos = Integer.parseInt(itemcode.getTag().toString());

                                newqty = s.toString().trim();

                                Log.e("Cal_Qty1==",""+list.get(holder.getAdapterPosition()).getCal_Qty());
                                if (!newqty.trim().equals("0")) {
                                    String CountOfItems=my_dataBase.itemsUnitDao().getConvRate(list.get(holder.getAdapterPosition()).getItemcode(), holder.unitSpinner.getSelectedItem().toString());
                                      if(!holder.unitSpinner.getSelectedItem().toString().equals("One Unit")) {
                                          list.get(holder.getAdapterPosition()).setCal_Qty(String.valueOf(Double.parseDouble(newqty) * Double.parseDouble(CountOfItems)));

                                      }

                                    else {
                                          list.get(holder.getAdapterPosition()).setCal_Qty(newqty);

                                      }
                                    list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                    Log.e("Cal_Qty==",""+list.get(holder.getAdapterPosition()).getCal_Qty());
                                 int z=  my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                            list.get(holder.getAdapterPosition()).getRecQty(), list.get(holder.getAdapterPosition()).getTransNumber(),
                                            list.get(holder.getAdapterPosition()).getCal_Qty()    );
                                    Log.e("z==",""+z);
//                                    Log.e("case1===", s + " pos=== " + getAdapterPosition());

                                } else {
//                                    replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                                    MainActivity.updateAdpapter();
                                    holder.qty.setError(context.getResources().getString(R.string.qtyerror3));
                                    Log.e("case1===", "");
                                }


                            } catch (Exception e) {
                                Log.e("Exception2===", e.getMessage()+"");
                            }

                            //newqty = qty.getText().toString();

                        } else {
//                        int pos = Integer.parseInt(itemcode.getTag().toString());
//                        replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                        MainActivity.updateAdpapter();
                            holder.qty.setError(context.getResources().getString(R.string.qtyerror3));

                        }

                    }
                });

            }else {
           //  holder.qty.setEnabled(true);
             holder.qty.addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                 }

                 @Override
                 public void onTextChanged(CharSequence s, int start, int before, int count) {

                 }

                 @Override
                 public void afterTextChanged(Editable s) {

                     if (s.toString().length() != 0) {

//                            int pos = Integer.parseInt(itemcode.getTag().toString());

                             newqty = s.toString().trim();

                             Log.e("Cal_Qty1==",""+list.get(holder.getAdapterPosition()).getCal_Qty());
                             if (!newqty.trim().equals("0")) {
                                 String CountOfItems;
                                if(holder.unitSpinner.getSelectedItem()!=null)
                                          CountOfItems=my_dataBase.itemsUnitDao().getConvRate(list.get(holder.getAdapterPosition()).getItemcode(), holder.unitSpinner.getSelectedItem().toString());
                               else   CountOfItems="1";
                                 if(!holder.unitSpinner.getSelectedItem().toString().equals("One Unit")) {
                                     list.get(holder.getAdapterPosition()).setCal_Qty(String.valueOf(Double.parseDouble(newqty) * Double.parseDouble(CountOfItems)));

                                 }

                                 else {
                                     list.get(holder.getAdapterPosition()).setCal_Qty(newqty);

                                 }
                                 list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                 Log.e("Cal_Qty==",""+list.get(holder.getAdapterPosition()).getCal_Qty());
                                 int z=  my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                         list.get(holder.getAdapterPosition()).getRecQty(), list.get(holder.getAdapterPosition()).getTransNumber(),
                                         list.get(holder.getAdapterPosition()).getCal_Qty()    );
                                 Log.e("z==",""+z);
//                                    Log.e("case1===", s + " pos=== " + getAdapterPosition());

                             } else {
//                                    replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                                    MainActivity.updateAdpapter();
                                 holder.qty.setError(context.getResources().getString(R.string.qtyerror3));
                                 Log.e("case1===", "");
                             }




                         //newqty = qty.getText().toString();

                     } else {
//                        int pos = Integer.parseInt(itemcode.getTag().toString());
//                        replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                        MainActivity.updateAdpapter();
                         holder.qty.setError(context.getResources().getString(R.string.qtyerror3));

                     }

                 }
             });
         }

        }
        else {
//            holder.qty.setEnabled(true);
//            holder.qty.requestFocus();
//            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
            holder.qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if (my_dataBase.settingDao().getallsetting().get(0).getCheckQty().equals("1")) { ///Qty Checker Active

                        if (editable.toString().length() != 0) {
                            try {
//                            int pos = Integer.parseInt(itemcode.getTag().toString());

                                int totAvailableQty = Integer.parseInt(replacementlist.get(holder.getAdapterPosition()).getAvailableQty()) + Integer.parseInt(replacementlist.get(holder.getAdapterPosition()).getRecQty());

                                Log.e("Total Available Qty:", String.valueOf(totAvailableQty));

                                newqty = editable.toString().trim();

                                if ((Integer.parseInt(newqty)) <= (totAvailableQty)) {
                                    if (!newqty.trim().equals("0")) {
                                        String CountOfItems=my_dataBase.itemsUnitDao().getConvRate(list.get(holder.getAdapterPosition()).getItemcode(), holder.unitSpinner.getSelectedItem().toString());
                                        if(!holder.unitSpinner.getSelectedItem().toString().equals("One Unit")) {
                                            list.get(holder.getAdapterPosition()).setCal_Qty(String.valueOf(Double.parseDouble(newqty) * Double.parseDouble(CountOfItems)));

                                        }

                                        else {
                                            list.get(holder.getAdapterPosition()).setCal_Qty(newqty);

                                        }

                                        list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                        my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                                list.get(holder.getAdapterPosition()).getRecQty(),
                                                list.get(holder.getAdapterPosition()).getTransNumber(),
                                                list.get(holder.getAdapterPosition()).getCal_Qty()
                                        );
                                        list.get(holder.getAdapterPosition()).setAvailableQty(String.valueOf(totAvailableQty - Integer.parseInt(newqty)));
                                        my_dataBase.replacementDao().updateAvailableQTY(list.get(holder.getAdapterPosition()).getTransNumber(),
                                                list.get(holder.getAdapterPosition()).getItemcode(), list.get(holder.getAdapterPosition()).getAvailableQty());

                                        Log.e("AvailableAfter", String.valueOf(totAvailableQty - Integer.parseInt(newqty)));
//                                    Log.e("case1===", s + " pos=== " + getAdapterPosition());

                                    } else {
//                                    replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                                    MainActivity.updateAdpapter();
                                        holder.qty.setError(context.getResources().getString(R.string.qtyerror3));
                                        Log.e("case1===", "");
                                    }
                                } else {
                                    int availableQty = Integer.parseInt(replacementlist.get(holder.getAdapterPosition()).getAvailableQty());
                                    int enteredQty = Integer.parseInt(replacementlist.get(holder.getAdapterPosition()).getRecQty());
                                    int totAvailable = availableQty + enteredQty;
                                    holder.qty.setError(context.getString(R.string.notEnoughQuantity) +
                                            totAvailable);
                                    //Maximum quantity available for this item is ...
                                }

                            } catch (Exception e) {
                            }

                            //newqty = qty.getText().toString();

                        } else {
//                        int pos = Integer.parseInt(itemcode.getTag().toString());
//                        replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                        MainActivity.updateAdpapter();
                            holder.qty.setError(context.getResources().getString(R.string.qtyerror3));

                        }
                    } else { ///Qty Checker Inactive

                        if (editable.toString().length() != 0) {
                            try {
//                            int pos = Integer.parseInt(itemcode.getTag().toString());

                                newqty = editable.toString().trim();


                                if (!newqty.trim().equals("0")) {

                                        String CountOfItems=my_dataBase.itemsUnitDao().getConvRate(list.get(holder.getAdapterPosition()).getItemcode(), holder.unitSpinner.getSelectedItem().toString());
                                        if(!holder.unitSpinner.getSelectedItem().toString().equals("One Unit")) {
                                            list.get(holder.getAdapterPosition()).setCal_Qty(String.valueOf(Double.parseDouble(newqty) * Double.parseDouble(CountOfItems)));

                                        }

                                        else {
                                            list.get(holder.getAdapterPosition()).setCal_Qty(newqty);

                                        }
                                    list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                    my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                            list.get(holder.getAdapterPosition()).getRecQty(), list.get(holder.getAdapterPosition()).getTransNumber(),
                                            list.get(holder.getAdapterPosition()).getCal_Qty() );

//                                    Log.e("case1===", s + " pos=== " + getAdapterPosition());

                                } else {
//                                    replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                                    MainActivity.updateAdpapter();
                                    holder.qty.setError(context.getResources().getString(R.string.qtyerror3));
                                    Log.e("case1===", "");
                                }


                            } catch (Exception e) {
                            }

                            //newqty = qty.getText().toString();

                        } else {
//                        int pos = Integer.parseInt(itemcode.getTag().toString());
//                        replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                        MainActivity.updateAdpapter();
                            holder.qty.setError(context.getResources().getString(R.string.qtyerror3));

                        }

                    }
                }
            });
        }

        if (Login.serialsActive == 0) {
            Log.e("ca1", "CCCC");
            holder.tvEdit.setVisibility(View.INVISIBLE);
        }
        else {
            Log.e("ca2", "CCCC");
      //      List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(list.get(position).getItemcode());
            List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(MainActivity.itemcode.getText().toString().trim());

            Log.e("ca2", "CCCC");
         if(hasSerial.size()!=0)
             if (hasSerial.get(0).equals("1")) {
                Log.e("ca3", "CCCC");
                holder.tvEdit.setVisibility(View.VISIBLE);
                holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemcode.setText(list.get(holder.getAdapterPosition()).getItemcode());
                    }
                });
            } else {
                Log.e("ca4", "CCCC");
                Log.e("itemcode", holder.itemcode.getText()+"");
                holder.tvEdit.setVisibility(View.INVISIBLE);
            }else {
             Log.e("ca4", "CCCC");
             Log.e("itemcode", holder.itemcode.getText()+"");
             holder.tvEdit.setVisibility(View.INVISIBLE);
         }
        }

        Log.e("pos===", position + "highligtedItemPosition=== " + highligtedItemPosition + "highligtedItemPosition2==" + highligtedItemPosition2);
        if (position == highligtedItemPosition2) {
            Log.e("AAAAA", "AAAAAA");
        if (Login.serialsActive == 0) {
                Log.e("CCCCC", "CCCC");
               // holder.qty.setEnabled(true);
                holder.qty.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
           }

            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.layer2));


        } else {

            Log.e("dddd", "dddd");
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

///
//        if(Login.serialsActive == 0)
//        {
//
//            if(list.get(position).getUNITBARCODE()!=null)
//        {
//            if(!list.get(position).getUNITBARCODE().equals(""))
//        {
//            Log.e("UNITBARCODE33==",list.get(position).getUNITBARCODE()+" id=="+list.get(position).getUnitID());
//            try {
//                for(int i=0;i<itemUnits.size();i++) {
//                    Log.e("UNITBARCODE34==",itemUnits.get(i)+" id== "+list.get(position).getUnitID());
//                    if (itemUnits.get(i).equals(list.get(position).getUnitID()))
//                        holder.unitSpinner.setSelection(i);
//                }
//            }catch (Exception e){
//                Log.e("Exception",e.getMessage());
//            }
//        }
//
//
//        }}
    }













    public void removeItem(int position) {
        if (position < list.size()) {
            if (Login.serialsActive == 0) {
                Log.e("position===", position + "");
                int f = my_dataBase.replacementDao().deleteReplacement(list.get(position).getItemcode(),
                        list.get(position).getFrom(), list.get(position).getTo(), list.get(position).getTransNumber());
                Log.e("f===", f + "");
                list.remove(position);
                // notifyItemRemoved(position);
                notifyDataSetChanged();
            } else {
                Log.e("position===", position + "");
                int f = my_dataBase.replacementDao().deleteReplacement(list.get(position).getItemcode(),
                        list.get(position).getFrom(), list.get(position).getTo(), list.get(position).getTransNumber());
                Log.e("f===", f + "");

                my_dataBase.serialTransfersDao().deleteAllAdded(list.get(position).getItemcode(), list.get(position).getTransNumber());
                list.remove(position);

                notifyDataSetChanged();
            }
            save.setEnabled(list.size() != 0);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class replacementViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, from, to, zone, itemcode, tvRemove, itemqty, TransferNo, tvEdit;

        EditText qty,updatedQTY;

        LinearLayout linearLayoutColoring, linearEdit;

        Button btnAddSerial;

        RecyclerView rvEditSerials;
        Spinner  unitSpinner;

        public replacementViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutColoring = itemView.findViewById(R.id.row);

            updatedQTY= itemView.findViewById(R.id.updatedQTY);
            unitSpinner= itemView.findViewById(R.id.unitspinner);
            if(serialsActive==1)unitSpinner.setVisibility(View.GONE);
            TransferNo = itemView.findViewById(R.id.trsnferNo);
            itemname = itemView.findViewById(R.id.itemname);
            my_dataBase = RoomAllData.getInstanceDataBase(context);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            zone = itemView.findViewById(R.id.zone);
            itemcode = (TextView) itemView.findViewById(R.id.itemcode);
            qty = itemView.findViewById(R.id.tblqty);
            // qty.setOnEditorActionListener(onEditAction);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            itemqty = itemView.findViewById(R.id.itemqty);
            linearEdit = itemView.findViewById(R.id.linearEdit);
            btnAddSerial = itemView.findViewById(R.id.btnAddSerial);
            rvEditSerials = itemView.findViewById(R.id.rvEditSerials);
            tvEdit = itemView.findViewById(R.id.tvEdit);


            tvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    final String tag = tvRemove.getTag().toString();

                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_entry);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("removeItemposition", getAdapterPosition() + "");
                            removeItem(getAdapterPosition());
                            dialog.dismiss();

                        }
                    });
                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);

                }
            });


        }


//        EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
//                        || i == EditorInfo.IME_NULL) {
//                    switch (textView.getId()) {
//                        case R.id.tblqty:
//
//                            newqty = qty.getText().toString();
//                            String zone = replacementlist.get(Integer.parseInt(qty.getTag().toString())).getZone();
//                            String itemcode = replacementlist.get(Integer.parseInt(qty.getTag().toString())).getItemcode();
//                            if (checkQtyValidateinRow(newqty, itemcode, zone)) {
//                                // updateQTYOfZoneinRow(newqty,zone,itemcode);
//                                replacementlist.get(Integer.parseInt(qty.getTag().toString())).setRecQty(newqty);
//                            } else
//                                showSweetDialog(context, 3, "", context.getResources().getString(R.string.notvaildqty));
//                            break;
//                    }
//
//                }
//
//                return true;
//            }
//        };
//    }
//
//    public boolean checkQtyValidateinRow(String newQty, String itemco, String zonecode) {
//        Log.e("checkQtyValidateinRow", "heckQtyValidate");
//        for (int i = 0; i < listQtyZone.size(); i++) {
//            if (itemco.trim().equals(listQtyZone.get(i).getItemCode().trim())
//                    && zonecode.trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
//                if (Integer.parseInt(newQty) <= Integer.parseInt(listQtyZone.get(i).getQty())) {
//                    Log.e("checkQtyValidateinRow", listQtyZone.get(i).getQty() + " " + newQty);
//                    return true;
//
//                } else {
//                    return false;
//
//
//                }
//            }
//        }
//
//        return false;
    }


}
