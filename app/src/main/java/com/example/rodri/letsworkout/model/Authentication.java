package com.example.rodri.letsworkout.model;

import android.content.Context;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;

/**
 * Created by rodri on 8/19/2016.
 */
public class Authentication {

    private String userName;
    private String password;
    private boolean isConnected;
    private MyDataSource dataSource;
    private Context context;

    public Authentication(String userName, String password, Context context) {
        this.userName = userName;
        this.password = password;
        this.isConnected = false;
        this.context = context;
        dataSource = new MyDataSource(context);
    }

    public String getUserName() {
        return userName;
    }

    public boolean login() {
        if (dataSource.getUser(userName, password) != null) {
            Toast.makeText(context, R.string.toast_login_success, Toast.LENGTH_SHORT).show();
            isConnected = true;
            return true;
        } else {
            Toast.makeText(context, R.string.toast_login_failure, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

}
