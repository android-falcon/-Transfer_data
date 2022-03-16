package com.hiaryabeer.transferapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.transferapp.R;

import java.util.List;

public class SerialsReportAdapter extends RecyclerView.Adapter<SerialsReportAdapter.ViewHolder>{

    private Context context;
    private List<String> serialsList;

    public SerialsReportAdapter(Context context, List<String> serialsList) {
        this.context = context;
        this.serialsList = serialsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_serials_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSerialCode.setText(serialsList.get(position));
    }

    @Override
    public int getItemCount() {
        return serialsList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSerialCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSerialCode = itemView.findViewById(R.id.tvSerialCode);
        }
    }
}
