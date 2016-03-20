package com.zircon.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class User  {

    public String userid;
    public String firstname;
    public String lastname;
    public String occupation;
    public String email;
    public String description;

    @SerializedName("contact_no")
    public String contactNumber;

    @SerializedName("profile_pic")
    public String profilePic;

    public ArrayList<UserRole> userRoles;

    public String address;

    public static class UserRole {
        public String id;
        public String userRole;

    }
}