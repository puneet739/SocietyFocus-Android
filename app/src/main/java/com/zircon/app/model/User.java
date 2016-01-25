package com.zircon.app.model;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class User {

    public String userid;
    public String firstname;
//    public String password;
    public String email;
    public ArrayList<UserRole> userRoles;

    public static class UserRole {
        public String id;
        public String userRole;

    }
}