package com.example.rodri.letsworkout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.AutoLogin;
import com.example.rodri.letsworkout.model.User;
import com.example.rodri.letsworkout.util.Util;

/**
 * Created by rodri on 8/19/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String MyPREFERENCES = "com.example.rodri.letsworkout";
    private static final String AUTOLOGIN = "com.example.rodri.letsworkout.autologin";
    private static final String ID = "com.example.rodri.letsworkout.id";

    private SharedPreferences sharedPreferences;

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btSignUp;
    private CheckBox checkRememberMe;
    private boolean isChecked = false;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniViews();
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        checkAutoLogin();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Authentication.getInstance().init(username, password, getApplicationContext());
                boolean authenticated = Authentication.getInstance().login();


                if (authenticated) {
                    if (isChecked) {
                        dataSource.open();
                        AutoLogin autoLogin = dataSource.createAutoLogin(username, password);
                        dataSource.close();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(AUTOLOGIN, true);
                        editor.putLong(ID, autoLogin.getId());
                        editor.apply();
                    }
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(i, 1);
            }
        });

        checkRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                isChecked = checked;
            }
        });
    }

    public void iniViews() {
        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btSignUp = (Button) findViewById(R.id.btSignUp);
        checkRememberMe = (CheckBox) findViewById(R.id.activityLogin_checkRememberMe);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                User user = (User) data.getSerializableExtra("user");
                Authentication.getInstance().init(user.getLogin(), user.getPassword(), getApplicationContext());
                boolean res = Authentication.getInstance().login();
                System.out.println("user name:" + user.getName() + " res: " + res);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        }
    }

    public void checkAutoLogin() {
        isChecked = sharedPreferences.getBoolean(AUTOLOGIN, false);
        if (isChecked) {
            Toast.makeText(getApplicationContext(), "IT WORKED!", Toast.LENGTH_SHORT).show();
        }
    }
}
