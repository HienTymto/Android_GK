package com.Vu_Van_Hien.doan_gk;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.Vu_Van_Hien.doan_gk.adapter.PetAdapter;
import com.Vu_Van_Hien.doan_gk.model.Pet;
import com.Vu_Van_Hien.doan_gk.model.PhanLoai;
import com.Vu_Van_Hien.doan_gk.util.DatabaseHelper;

import java.util.ArrayList;

public class PetActivity extends AppCompatActivity {
    ListView lvSach;
    Pet pet = null;
    ArrayList<Pet> dspet;
    ArrayList<PhanLoai> dspl;
    ArrayAdapter<Pet> adapter ;
    DatabaseHelper databaseHelper =new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }
    private void addControls() {
        lvSach = findViewById(R.id.lvSach);
        dspet = new ArrayList<>();
        dspl = new ArrayList<>();
        try {
            Cursor cursor1 = databaseHelper.getReadableDatabase().rawQuery("select * From phanloai",null);
            while (cursor1.moveToNext()) {
                PhanLoai a = new PhanLoai();
                a.setMaLoai(cursor1.getInt(0));
                a.setTenPhanLoai(cursor1.getString(1));
                dspl.add(a);
            }
            Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * From pet",null);
            while (cursor.moveToNext()) {
                Pet a = new Pet();
                a.setMasp(cursor.getInt(0));
                a.setTensp(cursor.getString(1));
                a.setMoTa(cursor.getString(2));
                a.setGia(cursor.getInt(3));
                a.setHinh(cursor.getBlob(4));
                PhanLoai phanLoai =timPhanLoai(dspl,cursor.getInt(5));
                a.setPhanLoai(phanLoai);
                dspet.add(a);
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage(String.valueOf(a.getTensp()));
            }

        }catch (Exception ex)
        {
            Toast.makeText(this,"Lỗi", Toast.LENGTH_LONG);
        }
        adapter = new PetAdapter(this, R.layout.item_pet, dspet);
        lvSach.setAdapter(adapter);
    }

    private void addEvents() {
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyXoa(i);
                return true;
            }
        });
        lvSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLySua(i);
            }
        });

    }
    private PhanLoai timPhanLoai(ArrayList<PhanLoai> dspl, int maTheLoai) {
        PhanLoai a = new PhanLoai();
        for (PhanLoai tl: dspl) {
            if (tl.getMaLoai() == maTheLoai){
                return tl;
            }
        }
        return a;
    }


    private void xuLyXoa(int index) {
        pet= dspet.get(index);

        if (databaseHelper.getReadableDatabase().delete("pet", "id = ?", new String[]{pet.getMasp() + ""}) != -1) {
            Toast.makeText(this, "Xóa thành công"  + pet.getTensp(), Toast.LENGTH_SHORT);
        }
        databaseHelper.close();
        Toast.makeText(PetActivity.this, "Xóa thành công " + pet.getTensp(), Toast.LENGTH_SHORT).show();

        dspet.remove(pet);
        adapter.notifyDataSetChanged();
    }

    private void xuLySua(int i) {
        pet=dspet.get(i);
        Intent intent = new Intent(PetActivity.this, AddPetActivity.class);
        intent.putExtra("CHON",pet);
        resultLauncher.launch(intent);

    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().hasExtra("THEM")) {
                            pet = (Pet) result.getData().getSerializableExtra("THEM");
                            dspet.add(pet);
                            adapter.notifyDataSetChanged();
                        } else if (result.getData().hasExtra("SUA")) {
                            Pet tl = (Pet) result.getData().getSerializableExtra("SUA");
                            pet.setMasp(tl.getMasp());
                            pet.setTensp(tl.getTensp());
                            pet.setMoTa(tl.getMoTa());
                            pet.setGia(tl.getGia());
                            pet.setHinh(tl.getHinh());
                            pet.setPhanLoai(tl.getPhanLoai());
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            });



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.petlist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_DanhMuc:
                Intent intent3 = new Intent(PetActivity.this,CategoryActivity.class);
                resultLauncher.launch(intent3);
                break;
            case R.id.item_themDM:
                Intent intent = new Intent(PetActivity.this,AddPetActivity.class);
                resultLauncher.launch(intent);
                break;
            case R.id.item_About:
                Intent intent2 = new Intent(PetActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.item_Thoat:
                //Khoi tao lai Activity main
                Intent intent1 = new Intent(getApplicationContext(), SinginActivity.class);
                startActivity(intent1);

                // Tao su kien ket thuc app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
