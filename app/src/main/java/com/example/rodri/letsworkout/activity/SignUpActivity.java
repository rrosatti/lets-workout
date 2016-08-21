package com.example.rodri.letsworkout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.User;
import com.example.rodri.letsworkout.util.Util;

/**
 * Created by rodri on 8/20/2016.
 */
public class SignUpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etName;
    private EditText etLogin;
    private EditText etPassword;
    private Button btConfirm;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_name_field_empty, Toast.LENGTH_SHORT).show();
                } else if (login.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_login_field_empty, Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_password_field_empty, Toast.LENGTH_SHORT).show();
                } else {
                    // 1 - Make a "password rule" (min num of digits) and cryptographer it
                    // 2 - Verify if username/login already exists

                    dataSource = new MyDataSource(SignUpActivity.this);
                    try {
                        dataSource.open();

                        User user = dataSource.createUser(name, login, password);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("user", user);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                        dataSource.close();
                    }
                }
            }
        });
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSignUp);
        etName = (EditText) findViewById(R.id.signUp_etName);
        etLogin = (EditText) findViewById(R.id.signUp_etLogin);
        etPassword = (EditText) findViewById(R.id.signUp_etPassword);
        btConfirm = (Button) findViewById(R.id.signUp_btConfirm);
    }
}
