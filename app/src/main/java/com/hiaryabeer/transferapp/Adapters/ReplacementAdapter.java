package com.hiaryabeer.transferapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.transferapp.Activities.Login;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import java.util.List;

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
        holder.from.setText(list.get(position).getFromName());
        holder.to.setText(list.get(position).getToName());
        holder.itemname.setText(list.get(position).getItemname());
        holder.TransferNo.setText(list.get(position).getTransNumber());
        //   holder.zone.setText(list.get(position).getZone());
        holder.itemcode.setText(list.get(position).getItemcode());
        //    Log.e("onBindViewHolder202020",""+list.get(position).getRecQty());
        holder.qty.setText(list.get(position).getRecQty());
//        holder.itemcode.setTag(position);
//        holder.tvRemove.setTag(position);
        //   holder.itemqty.setText(list.get(position).getQty());
        holder.itemqty.setEnabled(false);

//        holder.qty.setEnabled(Login.serialsActive == 0);

        if (Login.serialsActive == 1) {

            List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(list.get(position).getItemcode());

            if (hasSerial.get(0).equals("1")) {
                holder.qty.setEnabled(false);
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
            } else {

                holder.qty.setEnabled(true);
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


                                if (!newqty.trim().equals("0")) {

                                    list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                    my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                            list.get(holder.getAdapterPosition()).getRecQty(), list.get(holder.getAdapterPosition()).getTransNumber());

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
                });

            }

        } else {
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

                                        list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                        my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                                list.get(holder.getAdapterPosition()).getRecQty(), list.get(holder.getAdapterPosition()).getTransNumber());
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

                                    list.get(holder.getAdapterPosition()).setRecQty(newqty);
                                    my_dataBase.replacementDao().updateQTY(list.get(holder.getAdapterPosition()).getItemcode(),
                                            list.get(holder.getAdapterPosition()).getRecQty(), list.get(holder.getAdapterPosition()).getTransNumber());

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
            holder.tvEdit.setVisibility(View.INVISIBLE);
        } else {
            List<String> hasSerial = my_dataBase.itemDao().itemHasSerial(list.get(position).getItemcode());

            if (hasSerial.get(0).equals("1")) {
                holder.tvEdit.setVisibility(View.VISIBLE);
                holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemcode.setText(list.get(holder.getAdapterPosition()).getItemcode());
                    }
                });
            } else
                holder.tvEdit.setVisibility(View.INVISIBLE);

        }

        Log.e("pos===", position + "highligtedItemPosition=== " + highligtedItemPosition + "highligtedItemPosition2==" + highligtedItemPosition2);
        if (position == highligtedItemPosition2) {

            if (Login.serialsActive == 0) {
                holder.qty.setEnabled(true);
                holder.qty.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
            }
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.yellow2));

        } else {

            Log.e("dddd", "dddd");
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.white));
        }


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

        EditText qty;

        LinearLayout linearLayoutColoring, linearEdit;

        Button btnAddSerial;

        RecyclerView rvEditSerials;

        public replacementViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutColoring = itemView.findViewById(R.id.row);
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
