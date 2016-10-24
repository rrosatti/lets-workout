package com.example.rodri.letsworkout.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.AutoLogin;

/**
 * Created by rodri on 10/19/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static final String MyPREFERENCES = "com.example.rodri.letsworkout";
    private static final String AUTOLOGIN = "com.example.rodri.letsworkout.autologin";
    private static final String ID = "com.example.rodri.letsworkout.id";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Intent i;
        if (checkAutoLogin()) {
            //i = new Intent(this, MainActivity.class);
            long id = sharedPreferences.getLong(ID, -1);

            MyDataSource dataSource = new MyDataSource(SplashScreenActivity.this);
            dataSource.open();
            AutoLogin autoLogin = dataSource.getAutoLogin(id);
            System.out.println("Auto login: username -> " + autoLogin.getLogin());
            dataSource.close();

            Authentication.getInstance().init(autoLogin.getLogin(), autoLogin.getPassword(), getApplicationContext());
            boolean authenticated = Authentication.getInstance().login();

            if (authenticated) {
                i = new Intent(this, MainActivity.class);
            } else {
                i = new Intent(this, LoginActivity.class);
                System.out.println("Auto login FAILED");
            }

        } else {
            i = new Intent(this, LoginActivity.class);
        }

        startActivity(i);
        finish();

    }

    public boolean checkAutoLogin() {
        return sharedPreferences.getBoolean(AUTOLOGIN, false);
    }

}
