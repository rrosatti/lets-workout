package com.example.rodri.letsworkout.model;

import android.content.Context;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;

/**
 * Created by rodri on 8/19/2016.
 */
public class Authentication {

    private static Authentication inst = null;

    private String userName;
    private String password;
    private User user;
    private boolean isConnected;
    private MyDataSource dataSource;
    private Context context;

    private Authentication(){
        userName = "";
        password = "";
        context = null;
        isConnected = false;
    }

    public static Authentication getInstance() {
        if (inst == null ){
            inst = new Authentication();
        }
        return inst;
    }

    public void init(String userName, String password, Context context) {
        this.userName = userName;
        this.password = password;
        this.context = context;
        dataSource = new MyDataSource(context);
    }
    /**
    public Authentication(String userName, String password, Context context) {
        this.userName = userName;
        this.password = password;
        this.isConnected = false;
        this.context = context;
        dataSource = new MyDataSource(context);
    }*/

    public String getUserName() {
        return userName;
    }

    public User getUser() {
        return user;
    }

    public boolean login() {
        try {
            dataSource.open();

            user = dataSource.getUser(userName, password);
            dataSource.close();
            if (user != null) {
                Toast.makeText(context, R.string.toast_login_success, Toast.LENGTH_SHORT).show();
                isConnected = true;
                return true;
            } else {
                Toast.makeText(context, R.string.toast_login_failure, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return false;
        }

    }

    public boolean isConnected() {
        return isConnected;
    }

}
