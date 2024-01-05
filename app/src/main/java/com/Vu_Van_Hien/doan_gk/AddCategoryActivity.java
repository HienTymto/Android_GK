package com.Vu_Van_Hien.doan_gk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Vu_Van_Hien.doan_gk.model.PhanLoai;
import com.Vu_Van_Hien.doan_gk.util.DatabaseHelper;

public class AddCategoryActivity extends AppCompatActivity {
    EditText etTen,etMa;
    Button btnThem;
    PhanLoai tl = null;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        addControls();
        getDataFromIntent();
        addEvents();
    }
    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tl==null)
                    xuLyThem();
                else
                    xuLySua();
            }
        });
    }
    private void addControls() {
        etTen = findViewById(R.id.etTen);
        btnThem = findViewById(R.id.btnThemDanhMuc);
        etMa = findViewById(R.id.etMaDM);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("CHON")) {
            tl = (PhanLoai) intent.getSerializableExtra("CHON");
            etTen.setText(tl.getTenPhanLoai());
            etMa.setText(tl.getMaLoai()+"");
            etMa.setEnabled(false);
        }
    }
    private void xuLyThem() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        PhanLoai theLoai = new PhanLoai(ma,ten);

        try {
            ContentValues values = new ContentValues();
            values.put("id",theLoai.getMaLoai()+"");
            values.put("tenloai",theLoai.getTenPhanLoai());
            if(databaseHelper.getReadableDatabase().insert("phanloai",null,values)!=-1)
            {
                Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT);
                intent.putExtra("THEM", theLoai);

            }
            databaseHelper.close();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }catch (Exception ex)
        {
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG);
        }

    }
    private void xuLySua() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        tl.setMaLoai(ma);
        tl.setTenPhanLoai(ten);
        ContentValues values = new ContentValues();
        values.put("tenloai", ten);
        databaseHelper.getReadableDatabase().update("phanloai",values,"id =?",new String[]{ma+""});
        intent.putExtra("SUA", tl);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_GioiThieu:
                Intent intent = new Intent(AddCategoryActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.mnu_Thoat:
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
