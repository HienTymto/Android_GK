package com.Vu_Van_Hien.doan_gk.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Vu_Van_Hien.doan_gk.R;
import com.Vu_Van_Hien.doan_gk.model.PhanLoai;

import java.util.List;

public class PhanLoaiAdapter extends ArrayAdapter<PhanLoai> {
    Activity context;
    int resource;
    List<PhanLoai> object;
    public PhanLoaiAdapter(@NonNull Activity context, int resource, @NonNull List<PhanLoai> objects) {
        super(context, resource, objects);
        this.context=context;
        this.object=objects;
        this.resource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item;
        LayoutInflater inflater=this.context.getLayoutInflater();
        item=inflater.inflate(R.layout.item_category,null);
        TextView tvMaLoai=item.findViewById(R.id.tvMaLoai);
        TextView tvTenLoai=item.findViewById(R.id.tvTenLoai);
        PhanLoai phanLoai= this.object.get(position);
        tvMaLoai.setText(phanLoai.getMaLoai()+"");
        tvTenLoai.setText(phanLoai.getTenPhanLoai());
        return item;
    }
}