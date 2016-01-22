package com.zircon.app.model;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class LoginCredentials {

    public String societyId;

    public String userName;

    public String password;

    public LoginCredentials(){}

    public LoginCredentials(String societyId, String userName, String password) {
        this.societyId = societyId;
        this.userName = userName;
        this.password = password;
    }
}
