package com.hiaryabeer.transferapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.ReplacementModel;

import java.util.List;

public class TransReportsAdapter extends RecyclerView.Adapter<TransReportsAdapter.ReportsViewHolder> {

    private Context context;
    private List<ReplacementModel> reportsList;

    public TransReportsAdapter(Context context, List<ReplacementModel> reportsList) {
        this.context = context;
        this.reportsList = reportsList;
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

    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTransNo, tvFromStore, tvToStore,
                tvItemName, tvItemCode, tvQty;

        private TableRow bodyRowParent;

        public ReportsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTransNo = itemView.findViewById(R.id.tvTransNo);
            tvFromStore = itemView.findViewById(R.id.tvFromStore);
            tvToStore = itemView.findViewById(R.id.tvToStore);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemCode = itemView.findViewById(R.id.tvItemCode);
            tvQty = itemView.findViewById(R.id.tvQty);

            bodyRowParent = itemView.findViewById(R.id.bodyRowParent);

        }
    }

}
