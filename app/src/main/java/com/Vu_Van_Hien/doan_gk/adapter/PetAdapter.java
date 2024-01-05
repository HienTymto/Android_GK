package com.Vu_Van_Hien.doan_gk.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Vu_Van_Hien.doan_gk.R;
import com.Vu_Van_Hien.doan_gk.model.Pet;

import java.util.List;

public class PetAdapter extends ArrayAdapter<Pet> {
    Activity context;
    int resource;
    List<Pet> object;
    public PetAdapter(@NonNull Activity context, int resource, @NonNull List<Pet> objects) {
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
        item=inflater.inflate(R.layout.item_pet,null);
        TextView tvMa = item.findViewById(R.id.tvId);

        TextView tvTenPet=item.findViewById(R.id.tvTieuDe);
        TextView tvGia=item.findViewById(R.id.tvGia);
        ImageView ivHinh = item.findViewById(R.id.ivHinh);
        TextView tvloai = item.findViewById(R.id.tvMaLoai);
        Pet pet= this.object.get(position);
        tvTenPet.setText(pet.getTensp());
        tvGia.setText(pet.getGia()+"");
        tvMa.setText(pet.getMasp()+"");
        tvloai.setText(pet.getPhanLoai().getTenPhanLoai());
        Bitmap hinhanh= BitmapFactory.decodeByteArray(pet.getHinh(),0,pet.getHinh().length);
        ivHinh.setImageBitmap(hinhanh);
        return item;
    }
}

