package com.zircon.app.utils.datapasser;

import com.zircon.app.model.User;

/**
 * Created by jyotishman on 20/03/16.
 */
public class UserPasser {

    private static UserPasser instance;

    private UserPasser(){

    }

    private User user;

    public static UserPasser getInstance(){
        if (instance == null)
            instance = new UserPasser();
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void clear(){
        user = null;
    }
}
