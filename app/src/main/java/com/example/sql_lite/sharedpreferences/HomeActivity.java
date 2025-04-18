package com.example.sql_lite.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sql_lite.R;

public class HomeActivity extends AppCompatActivity {

    Button buttonTxt;
    EditText edt_username, edt_password;
    CheckBox cbRemeberMe;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home2);
        anhxa();


        buttonTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if(username.equals("admin")&& password.equals("admin")){
                    Toast.makeText(HomeActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    if(cbRemeberMe.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", password);
                        editor.putBoolean("trang thai", true);
                        editor.commit();
                    }
                    else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("trangthai");
                        editor.commit();
                    }
                }
                else{
                    Toast.makeText(HomeActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        edt_username.setText(sharedPreferences.getString("taikhoan", ""));
        edt_password.setText(sharedPreferences.getString("matkhau",""));
        cbRemeberMe.setChecked(sharedPreferences.getBoolean("trangthai", false));

    }
    private void anhxa()
    {
        buttonTxt = (Button) findViewById(R.id.buttonTxt);
        edt_password = (EditText) findViewById(R.id.passwordTxt);
        edt_username = (EditText) findViewById(R.id.usernameTxt);
        cbRemeberMe = (CheckBox) findViewById(R.id.cbmemberme);

    }

}