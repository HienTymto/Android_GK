package com.Vu_Van_Hien.doan_gk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Vu_Van_Hien.doan_gk.model.Pet;
import com.Vu_Van_Hien.doan_gk.model.PhanLoai;
import com.Vu_Van_Hien.doan_gk.util.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddPetActivity extends AppCompatActivity {
    EditText etMa, etTen,etGia, etMota;
    Button btnHinh, btnLuu;
    Spinner spinner;
    ImageView ivHinh;
    Pet pet = null;
    PhanLoai phanLoai = null;
    ArrayList<Pet> dsPet;
    ArrayList<PhanLoai> dsPhanLoai;
    int SELECT_PICTURE = 200;
    ArrayAdapter<PhanLoai> adapter;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpet);
        addControls();
        xulyChonViTriSpinner();
        getDataFromIntent();
        addEvents();
    }
    private void addControls() {
        etMa = findViewById(R.id.etMaSach);
        etTen = findViewById(R.id.etTenSach);
        etGia = findViewById(R.id.etGia);
        etMota = findViewById(R.id.etMota);
        btnHinh = findViewById(R.id.btnChonHinh);
        btnLuu = findViewById(R.id.btnLuuSach);
        spinner = findViewById(R.id.spiner);
        ivHinh = findViewById(R.id.imagedd);
        dsPet = new ArrayList<>();
        dsPhanLoai = new ArrayList<>();
        Cursor cursor1 = databaseHelper.getReadableDatabase().rawQuery("select * From phanloai",null);
        while (cursor1.moveToNext()) {
            PhanLoai a = new PhanLoai();
            a.setMaLoai(cursor1.getInt(0));
            a.setTenPhanLoai(cursor1.getString(1));
            dsPhanLoai.add(a);
        }
        adapter = new ArrayAdapter(AddPetActivity.this, android.R.layout.simple_spinner_item, dsPhanLoai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void addEvents() {
        btnHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyChonHinh();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pet==null)
                    xulyThem();
                else
                    xuLySua();
            }
        });
    }
    private void xuLySua() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        int gia = Integer.parseInt(etGia.getText().toString());
        String mota=etMota.getText().toString();
        byte[] anh=getByteArrayImage(ivHinh);
        pet.setMasp(ma);
        pet.setTensp(ten);
        pet.setGia(gia);
        pet.setMoTa(mota);
        pet.setHinh(anh);
        ContentValues values = new ContentValues();
        values.put("tenpet",ten);
        values.put("mota",mota);
        values.put("gia",gia);

        databaseHelper.getReadableDatabase().update("pet",values,"id =?",new String[]{ma+""});
        intent.putExtra("SUA", pet);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("CHON")) {
            pet = (Pet) intent.getSerializableExtra("CHON");
            etTen.setText(pet.getTensp());
            etMa.setText(pet.getMasp()+"");
            etGia.setText(pet.getGia()+"");
            etMota.setText(pet.getMoTa());
            spinner.setSelection(getPosition(spinner, String.valueOf(pet.getPhanLoai().getMaLoai())));
            Bitmap bitmap = BitmapFactory.decodeByteArray(pet.getHinh(), 0, pet.getHinh().length);
            ivHinh.setImageBitmap(bitmap);
            etMa.setEnabled(false);
        }
    }
    private byte[] getByteArrayImage(ImageView hinhanh) {
        BitmapDrawable drawable = (BitmapDrawable) hinhanh.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void xulyChonHinh() {
        btnHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChoose();
            }
        });
    }
    private void imageChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        launchSomeActivity.launch(intent);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                        try {
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            InputStream i = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(i);

                            ivHinh.setImageBitmap(compress(bitmap));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                }
            });
    public Bitmap compress(Bitmap yourBitmap){
        //converted into webp into lowest quality
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourBitmap.compress(Bitmap.CompressFormat.WEBP,0,stream);//0=lowest, 100=highest quality
        byte[] byteArray = stream.toByteArray();


        //convert your byteArray into bitmap
        Bitmap yourCompressBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        return yourCompressBitmap;
    }
    private int getPosition(Spinner sp, String chuoi) {
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(chuoi)) {
                return i;
            }
        }
        return 0;
    }
    private void xulyChonViTriSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                phanLoai = dsPhanLoai.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void xulyThem() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        int gia = Integer.parseInt(etGia.getText().toString());
        String mota=etMota.getText().toString();
        byte[] anh=getByteArrayImage(ivHinh);
        Pet s = new Pet(ma, ten, mota, gia, anh, phanLoai);
        ContentValues values = new ContentValues();
        values.put("id",s.getMasp());
        values.put("tenpet",s.getTensp());
        values.put("mota",s.getMoTa());
        values.put("gia",s.getGia()+"");
        values.put("hinh",s.getHinh());
        values.put("maloai",s.getPhanLoai().getMaLoai()+"");

        if(databaseHelper.getReadableDatabase().insert("pet",null,values)!=-1)
        {
            Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT);
            intent.putExtra("THEM", s);

        }
        databaseHelper.close();
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
                Intent intent = new Intent(AddPetActivity.this,AboutActivity.class);
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
