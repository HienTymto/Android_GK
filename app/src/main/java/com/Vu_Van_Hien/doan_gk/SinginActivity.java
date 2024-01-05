package com.Vu_Van_Hien.doan_gk;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Vu_Van_Hien.doan_gk.util.DatabaseHelper;

public class SinginActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        addControls();
        addEvents();
        DatabaseHelper databaseHandler = new DatabaseHelper(this);
        SQLiteDatabase readableDatabase = databaseHandler.getReadableDatabase();
        readableDatabase.close();
    }

    private void addControls() {
        txtUsername=findViewById(R.id.txtusername);
        txtPassword=findViewById(R.id.txtpassword);
        btnLogin=findViewById(R.id.btnlogin);
    }
    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangNhap();
            }
        });
    }

    private void xuLyDangNhap() {
        if(txtUsername.getText().length() != 0 && txtPassword.getText().length() != 0) {

            if (txtUsername.getText().toString().equals("hien") && txtPassword.getText().toString().equals("123")) {
                Intent intent = new Intent(SinginActivity.this, PetActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(
                        SinginActivity.this,
                        "Sai thông tin đăng nhập",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
        else
        {
            Toast.makeText(
                    SinginActivity.this,
                    "Vui lòng nhập đủ thông tin",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
