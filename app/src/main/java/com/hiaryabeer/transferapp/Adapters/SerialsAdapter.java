package com.hiaryabeer.transferapp.Adapters;

import static com.hiaryabeer.transferapp.Activities.MainActivity.adapter;
import static com.hiaryabeer.transferapp.Activities.MainActivity.replacementlist;
import static com.hiaryabeer.transferapp.Activities.MainActivity.replacmentRecycler;
import static com.hiaryabeer.transferapp.Activities.MainActivity.updateAdpapter;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.convertToEnglish;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.transferapp.Activities.MainActivity;
import com.hiaryabeer.transferapp.Models.ItemSerialTransfer;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.RoomAllData;

import java.util.ArrayList;

public class SerialsAdapter extends RecyclerView.Adapter<SerialsAdapter.SerialsViewHolder> {

    private Context context;
    private ArrayList<ItemSerialTransfer> serialTransfers;
    private RoomAllData my_database;

    public SerialsAdapter(Context context, ArrayList<ItemSerialTransfer> serialTransfers) {
        this.context = context;
        this.serialTransfers = serialTransfers;
        this.my_database = RoomAllData.getInstanceDataBase(context);
    }

    @NonNull
    @Override
    public SerialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_serial_transfer, parent, false);

        return new SerialsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerialsViewHolder holder, int position) {

        holder.tvSerialNo.setText(serialTransfers.get(position).getSerialNo());
//        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                serialTransfers.remove(holder.getAdapterPosition());
//                my_database.serialsDao().delete(holder.getAdapterPosition());
//                notifyDataSetChanged();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return serialTransfers.size();
    }

    public int repPosition(String code) {
        int i = 0;
        if (replacementlist.size() != 0)
            for (i = 0; i < replacementlist.size(); i++) {
                if (convertToEnglish(replacementlist.get(i).getItemcode()).equals(code)) {

                    return i;

                }
            }
        return i;
    }


    public class SerialsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSerialNo, tvDelete;


        public SerialsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSerialNo = itemView.findViewById(R.id.tvSerialNo);
            tvDelete = itemView.findViewById(R.id.tvDelete);

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_entry);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("removeSerialPosition", getAdapterPosition() + "");
                            int u = my_database.serialTransfersDao().delete(serialTransfers.get(getAdapterPosition()).getSerialNo(),
                                    serialTransfers.get(getAdapterPosition()).getVoucherNo(),
                                    serialTransfers.get(getAdapterPosition()).getDeviceId(),
                                    serialTransfers.get(getAdapterPosition()).getItemCode()
                            );

                            Log.e("u===", u + "");

                            int qty = Integer.parseInt(my_database.replacementDao().getQtyForItem(
                                    serialTransfers.get(getAdapterPosition()).getItemCode(),
                                    serialTransfers.get(getAdapterPosition()).getVoucherNo())
                            );
                            my_database.replacementDao().updateQTY(serialTransfers.get(getAdapterPosition()).getItemCode(),
                                    String.valueOf(qty - 1),  serialTransfers.get(getAdapterPosition()).getVoucherNo());

                            int pos = repPosition(serialTransfers.get(getAdapterPosition()).getItemCode());
                            Log.e("ReplacementPosition", pos + "");

                            replacementlist.get(pos).setRecQty(String.valueOf(qty - 1));
                            adapter = new ReplacementAdapter(replacementlist, context);
                            replacmentRecycler.setAdapter(adapter);
                            if ((qty - 1) == 0) {
                                my_database.replacementDao().deleteReplacement(ReplacementAdapter.list.get(pos).getItemcode(),
                                        ReplacementAdapter.list.get(pos).getFrom(), ReplacementAdapter.list.get(pos).getTo(),ReplacementAdapter.list.get(pos).getTransNumber());
                                ReplacementAdapter.list.remove(pos);
                            }
                            updateAdpapter();



                            serialTransfers.remove(getAdapterPosition());
                            MainActivity.tvTotal.setText(serialTransfers.size() + "");
                            notifyDataSetChanged();
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

    }
}
