package com.hiaryabeer.transferapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.transferapp.MainActivity;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.hiaryabeer.transferapp.MainActivity.colorData;
import static com.hiaryabeer.transferapp.MainActivity.highligtedItemPosition;
import static com.hiaryabeer.transferapp.MainActivity.highligtedItemPosition2;
import static com.hiaryabeer.transferapp.MainActivity.replacmentRecycler;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;
import static com.hiaryabeer.transferapp.MainActivity.replacementlist;
import static com.hiaryabeer.transferapp.Models.ImportData.listQtyZone;


public class ReplacementAdapter extends RecyclerView.Adapter<ReplacementAdapter.replacementViewHolder> {
    private List<ReplacementModel> list;
    Context context;
    String newqty;
    public RoomAllData my_dataBase;

    private int recyclerViewCurrentScrolledPosition;

    public ReplacementAdapter(List<ReplacementModel> list, Context context) {
        this.list = list;
        this.context = context;


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
        holder.qty.setEnabled(true);

        Log.e("pos===", position + "highligtedItemPosition=== " + highligtedItemPosition + "highligtedItemPosition2==" + highligtedItemPosition2);


     /*   if(position == highligtedItemPosition ){
            Log.e("fff","fffff");
        if (position == highligtedItemPosition) {
            Log.e("fff", "fffff");
            holder.qty.requestFocus();
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.yelow));

            highligtedItemPosition2 = -5;

        } else {
            Log.e("aaaa", "aaaa");
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.white));


        }
        if (position == highligtedItemPosition2) {
            Log.e("bbbb", "bbbbb");
        }*/
        if(position == highligtedItemPosition2 ){
            Log.e("bbbb","bbbbb");
            holder.qty.requestFocus();
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.yellow2));

            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
       /* else {
            //if(position != highligtedItemPosition ) {
                Log.e("dddd", "dddd");
                holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.white));
      //      }
        } else
            if (highligtedItemPosition2 != -5) {

            Log.e("dddd", "dddd");
            holder.linearLayoutColoring.setBackgroundColor(context.getResources().getColor(R.color.white));

        }*/


    }

    public void removeItem(int position) {
        if (position < list.size()) {
            int f = my_dataBase.replacementDao().deleteReplacement(list.get(position).getItemcode(),
                    list.get(position).getFrom(), list.get(position).getTo(), list.get(position).getTransNumber());
            Log.e("f===",f+"");
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class replacementViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, from, to, zone, itemcode, tvRemove, itemqty, TransferNo;

        EditText qty;

        LinearLayout linearLayoutColoring;

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

            qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.toString().length() != 0) {
                        try {
//                            int pos = Integer.parseInt(itemcode.getTag().toString());

                            int totAvailableQty = Integer.parseInt(replacementlist.get(getAdapterPosition()).getAvailableQty()) + Integer.parseInt(replacementlist.get(getAdapterPosition()).getRecQty());

                            Log.e("Total Available Qty:", String.valueOf(totAvailableQty));

                            newqty = editable.toString().trim();

                            if ((Integer.parseInt(newqty)) <= (totAvailableQty)) {
                                if (!newqty.trim().equals("0")) {

                                    list.get(getAdapterPosition()).setRecQty(newqty);
                                    int s = my_dataBase.replacementDao().updateQTY(list.get(getAdapterPosition()).getTransNumber(),
                                            list.get(getAdapterPosition()).getItemcode(), list.get(getAdapterPosition()).getRecQty());
                                    list.get(getAdapterPosition()).setAvailableQty(String.valueOf(totAvailableQty - Integer.parseInt(newqty)));
                                    my_dataBase.replacementDao().updateAvailableQTY(list.get(getAdapterPosition()).getTransNumber(),
                                            list.get(getAdapterPosition()).getItemcode(), list.get(getAdapterPosition()).getAvailableQty());

                                    Log.e("AvailableAfter", String.valueOf(totAvailableQty - Integer.parseInt(newqty)));
                                    Log.e("case1===", s + " pos=== " + getAdapterPosition());

                                } else {
//                                    replacementlist.get(pos).setRecQty(replacementlist.get(pos).getRecQty());
//                                    MainActivity.updateAdpapter();
                                    qty.setError(context.getResources().getString(R.string.qtyerror3));
                                    Log.e("case1===", "");
                                }
                            } else {
                                int availableQty = Integer.parseInt(replacementlist.get(getAdapterPosition()).getAvailableQty());
                                int enteredQty = Integer.parseInt(replacementlist.get(getAdapterPosition()).getRecQty());
                                int totAvailable = availableQty + enteredQty;
                                qty.setError(context.getString(R.string.notEnoughQuantity) +
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
                        qty.setError(context.getResources().getString(R.string.qtyerror3));

                    }
                }
            });



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


        EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_NULL) {
                    switch (textView.getId()) {
                        case R.id.tblqty:

                            newqty = qty.getText().toString();
                            String zone = replacementlist.get(Integer.parseInt(qty.getTag().toString())).getZone();
                            String itemcode = replacementlist.get(Integer.parseInt(qty.getTag().toString())).getItemcode();
                            if (checkQtyValidateinRow(newqty, itemcode, zone)) {
                                // updateQTYOfZoneinRow(newqty,zone,itemcode);
                                replacementlist.get(Integer.parseInt(qty.getTag().toString())).setRecQty(newqty);
                            } else
                                showSweetDialog(context, 3, "", context.getResources().getString(R.string.notvaildqty));
                            break;
                    }

                }

                return true;
            }
        };
    }

    public boolean checkQtyValidateinRow(String newQty, String itemco, String zonecode) {
        Log.e("checkQtyValidateinRow", "heckQtyValidate");
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemco.trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zonecode.trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                if (Integer.parseInt(newQty) <= Integer.parseInt(listQtyZone.get(i).getQty())) {
                    Log.e("checkQtyValidateinRow", listQtyZone.get(i).getQty() + " " + newQty);
                    return true;

                } else {
                    return false;


                }
            }
        }

        return false;
    }


}
