package com.Vu_Van_Hien.doan_gk;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
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
import androidx.appcompat.app.AppCompatActivity;

import com.Vu_Van_Hien.doan_gk.adapter.PhanLoaiAdapter;
import com.Vu_Van_Hien.doan_gk.model.PhanLoai;
import com.Vu_Van_Hien.doan_gk.util.DatabaseHelper;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ListView lvDanhMuc;
    PhanLoai phanLoai = null;
    ArrayList<PhanLoai> dspl;
    ArrayAdapter<PhanLoai> adapter ;
    DatabaseHelper databaseHelper =new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        addControls();

        addEvents();
    }

    private void addControls() {
        lvDanhMuc = findViewById(R.id.lvDanhmuc);
        dspl = new ArrayList<>();
        try {
            Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * From phanloai",null);
            while (cursor.moveToNext()) {
                PhanLoai a = new PhanLoai();
                a.setMaLoai(cursor.getInt(0));
                a.setTenPhanLoai(cursor.getString(1));
                dspl.add(a);
            }
        }catch (Exception ex)
        {
            Toast.makeText(this,"Loi", Toast.LENGTH_LONG);
        }
        adapter = new PhanLoaiAdapter(this, R.layout.item_category, dspl);
        lvDanhMuc.setAdapter(adapter);
    }

    private void addEvents() {
        lvDanhMuc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyXoa(i);
                return true;
            }
        });
        lvDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLySua(i);
            }
        });
    }

    private void xuLySua(int i) {
        phanLoai=dspl.get(i);
        Intent intent= new Intent(CategoryActivity.this, AddCategoryActivity.class);
        intent.putExtra("CHON",phanLoai);
        resultLauncher.launch(intent);
    }

    private void xuLyXoa(int index) {
        phanLoai= dspl.get(index);

        if (databaseHelper.getReadableDatabase().delete("phanloai", "id = ?", new String[]{phanLoai.getMaLoai() + ""}) != -1) {
            Toast.makeText(this, "Xóa thành công"  + phanLoai.getTenPhanLoai(), Toast.LENGTH_SHORT);
        }
        databaseHelper.close();
        Toast.makeText(CategoryActivity.this, "Xóa thành công " + phanLoai.getTenPhanLoai(), Toast.LENGTH_SHORT).show();

        dspl.remove(phanLoai);
        adapter.notifyDataSetChanged();

    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().hasExtra("THEM")) {
                            phanLoai = (PhanLoai) result.getData().getSerializableExtra("THEM");
                            dspl.add(phanLoai);
                            adapter.notifyDataSetChanged();
                        } else if (result.getData().hasExtra("SUA")) {
                            PhanLoai tl = (PhanLoai) result.getData().getSerializableExtra("SUA");
                            phanLoai.setMaLoai(tl.getMaLoai());
                            phanLoai.setTenPhanLoai(tl.getTenPhanLoai());
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            });
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_themDM:
                Intent intent = new Intent(CategoryActivity.this,AddCategoryActivity.class);
                resultLauncher.launch(intent);
                break;
            case R.id.item_About:
                Intent intent2 = new Intent(CategoryActivity.this,AboutActivity.class);
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