package com.zircon.app.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jikoobaruah on 15/02/16.
 */
public class Complaint {

    public String title;
    public String description;

    @SerializedName("name")
    public String complainerName;
    @SerializedName("contactno")
    public String complainerContactNo;
    @SerializedName("email")
    public String complainerEmail;
}
