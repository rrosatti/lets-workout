package com.example.rodri.letsworkout.model;

import java.io.Serializable;

/**
 * Created by rodri on 8/18/2016.
 */
public class User implements Serializable{

    private long id;
    private String name;
    private String login;
    private String password;

    public User() {}

    public User(long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

}
