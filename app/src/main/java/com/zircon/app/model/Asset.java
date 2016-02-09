package com.zircon.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jikoobaruah on 25/01/16.
 */
public class Asset {

    public String id;
    public String description;
    public String contactno;
    public String email;
    public String charges;
    public String category;
    public String status;
    public String duration;
    @SerializedName("img_url")
    public String img;
}
