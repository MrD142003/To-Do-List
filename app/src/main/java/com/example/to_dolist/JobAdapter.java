package com.example.to_dolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class JobAdapter extends ArrayAdapter<Job> {
    public JobAdapter(@NonNull Context context, int resource, @NonNull List<Job> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.job_adapter,null);
        }

        Job job = getItem(position);
        if(job != null){
            TextView txtMaCV = (TextView) v.findViewById(R.id.txtMaCV);
            TextView txtTenCV = (TextView) v.findViewById(R.id.txtTenCV);
            TextView txtTenNV = (TextView) v.findViewById(R.id.txtTenNV);
            TextView txtSDT = (TextView) v.findViewById(R.id.txtSDT);
            txtMaCV.setText(job.getMaCV());
            txtTenCV.setText(job.getTenCV());
            txtTenNV.setText(job.getTenNV());
            txtSDT.setText(job.getSdt());
        }
        return v;
    }
}
