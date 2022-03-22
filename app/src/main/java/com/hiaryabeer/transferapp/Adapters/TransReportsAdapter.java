package com.hiaryabeer.transferapp.Adapters;

import static com.hiaryabeer.transferapp.Activities.Login.serialsActive;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hiaryabeer.transferapp.Models.ExportData;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import java.util.ArrayList;
import java.util.List;

public class TransReportsAdapter extends RecyclerView.Adapter<TransReportsAdapter.ReportsViewHolder> {

    private Context context;
    private List<ReplacementModel> reportsList;

    private SerialsReportAdapter serialsReportAdapter;
    public static List<String> serialsList;

    private RoomAllData database;

    public TransReportsAdapter(Context context, List<ReplacementModel> reportsList) {
        this.context = context;
        this.reportsList = reportsList;
        this.database = RoomAllData.getInstanceDataBase(context);
    }

    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.transfer_report_rv_layout, parent, false);

        return new ReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsViewHolder holder, int position) {

        holder.tvTransNo.setText(reportsList.get(position).getTransNumber());
        holder.tvFromStore.setText(reportsList.get(position).getFromName());
        holder.tvToStore.setText(reportsList.get(position).getToName());

        holder.tvItemName.setText(reportsList.get(position).getItemname());
        holder.tvItemCode.setText(reportsList.get(position).getItemcode());
        holder.tvQty.setText(reportsList.get(position).getRecQty());

        if (reportsList.get(position).getIsPosted().equals("1")) {
            holder.bodyRowParent.setBackgroundResource(R.color.postedBackground);
        } else {
            holder.bodyRowParent.setBackgroundResource(R.color.notPostedBackground);
        }

        if (serialsActive == 1) {
            holder.downArrow.setVisibility(View.VISIBLE);

            serialsList = database.serialTransfersDao().getSerialCodes(reportsList.get(position).getTransNumber(),
                    reportsList.get(position).getItemcode());


            this.serialsReportAdapter = new SerialsReportAdapter(context, serialsList);

            holder.RVSerialsReport.setAdapter(serialsReportAdapter);

            holder.bodyRowParent.setOnClickListener(v -> {
                if (holder.serialsLayout.getVisibility() == View.VISIBLE) {
                    holder.serialsLayout.setVisibility(View.GONE);
                    holder.downArrow.setBackgroundResource(R.drawable.ic_expand_more);
                } else {
                    holder.serialsLayout.setVisibility(View.VISIBLE);
                    holder.downArrow.setBackgroundResource(R.drawable.ic_expand_less);
                }
            });
        } else {
            holder.downArrow.setVisibility(View.GONE);
        }

        if (serialsActive == 1)
            holder.reExport.setVisibility(View.GONE);
        else {
            holder.reExport.setVisibility(View.VISIBLE);
            holder.reExport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog passwordDialog = new Dialog(context);

                    View view = LayoutInflater.from(context).inflate(R.layout.passworddailog, null);

                    passwordDialog.setContentView(view);
                    passwordDialog.setCancelable(false);
                    passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    TextView closeBtn = passwordDialog.findViewById(R.id.cancel);
                    EditText passwordEt = passwordDialog.findViewById(R.id.passwordd);
                    Button okBtn = passwordDialog.findViewById(R.id.done);

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            passwordDialog.dismiss();
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (passwordEt.getText().toString().trim().equals("")) {

                                passwordEt.requestFocus();
                                passwordEt.setError(context.getString(R.string.required));

                            } else {

                                if (passwordEt.getText().toString().trim().equals("2022000")) {

                                    List<ReplacementModel> replacements = new ArrayList<>();
                                    ReplacementModel replacement = reportsList.get(holder.getAdapterPosition());

                                    replacements.add(replacement);
                                    ExportData exportData = new ExportData(context);
                                    exportData.exportReplacementList(replacements);

                                    holder.bodyRowParent.setBackgroundResource(R.color.postedBackground);

//                                    transReportsAdapter = new TransReportsAdapter(context, reportsList);
//                                    rvTransferReports.setAdapter(transReportsAdapter);
//                                    transReportsAdapter.notifyDataSetChanged();

                                } else {

                                    showSweetDialog(context, 3, context.getString(R.string.wrong_password), context.getString(R.string.wrong_password_msg));

                                }
                                passwordDialog.dismiss();

                            }

                        }
                    });

                    passwordDialog.show();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTransNo, tvFromStore, tvToStore,
                tvItemName, tvItemCode, tvQty;

        private TableRow bodyRowParent;

        private LinearLayout serialsLayout;
        private RecyclerView RVSerialsReport;

        private TextView downArrow, reExport, serials;

        public ReportsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTransNo = itemView.findViewById(R.id.tvTransNo);
            tvFromStore = itemView.findViewById(R.id.tvFromStore);
            tvToStore = itemView.findViewById(R.id.tvToStore);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemCode = itemView.findViewById(R.id.tvItemCode);
            tvQty = itemView.findViewById(R.id.tvQty);

            bodyRowParent = itemView.findViewById(R.id.bodyRowParent);

            serialsLayout = itemView.findViewById(R.id.serialsLayout);
            RVSerialsReport = itemView.findViewById(R.id.RVSerialsReport);

            downArrow = itemView.findViewById(R.id.downArrow);
            reExport = itemView.findViewById(R.id.reExport);
            serials = itemView.findViewById(R.id.serials);

        }
    }

}
