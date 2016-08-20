package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
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
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSignUp);
        etName = (EditText) findViewById(R.id.signUp_etName);
        etLogin = (EditText) findViewById(R.id.signUp_etLogin);
        etPassword = (EditText) findViewById(R.id.signUp_etPassword);
    }
}
