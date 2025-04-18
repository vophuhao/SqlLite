package com.example.sql_lite.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sql_lite.R;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailView, mPasswordView;
    private CheckBox  checkBoxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Button btn_share = findViewById(R.id.btn_home);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText)findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == R.id.login || id == EditorInfo.IME_NULL){
                    attemptLogin();
                    return true;
                }

                return false;
            }
        });

        Button mEmailSignIn = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        checkBoxRememberMe = (CheckBox)findViewById(R.id.checkBoxRememberMe);
        if(!new PreManager(this).isUserLoggedOut()){
            startNewActivity();
        }

        btn_share.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
    private void attemptLogin()
    {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View forcusView = null;
        if(!TextUtils.isEmpty(password) && !isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invail_password));
            forcusView = mPasswordView;
            cancel = true;
        }
        if(TextUtils.isEmpty(email)){
            mEmailView.setError(getString(R.string.error_field_requied));
            forcusView =mEmailView;
            cancel =true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            forcusView = mEmailView;
            cancel = true;
        }
        if(cancel){
            forcusView.requestFocus();

        }else{
            if(checkBoxRememberMe.isChecked())
                saveLoginDetails(email, password);
            startNewActivity();
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length()>4;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void saveLoginDetails(String email, String password) {
        new PreManager(this).saveLoginDetails(email, password);
    }

    private void startNewActivity() {
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
        finish();
    }

}