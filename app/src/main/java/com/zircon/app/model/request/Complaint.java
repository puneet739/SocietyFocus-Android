package com.zircon.app.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jikoobaruah on 15/02/16.
 */
public class Complaint {


    public Complaint(String title, String description, String complainerName, String complainerContactNo, String complainerEmail) {
        this.title = title;
        this.description = description;
        this.complainerName = complainerName;
        this.complainerContactNo = complainerContactNo;
        this.complainerEmail = complainerEmail;
    }

    public String title;
    public String description;

    @SerializedName("name")
    public String complainerName;
    @SerializedName("contactno")
    public String complainerContactNo;
    @SerializedName("email")
    public String complainerEmail;
}
