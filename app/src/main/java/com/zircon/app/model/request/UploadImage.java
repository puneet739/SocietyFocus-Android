package com.zircon.app.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cbc-03 on 08/03/16.
 */
public class UploadImage {

    @SerializedName("base64")
    String base64;
    @SerializedName("filename")
    String filename;

    public UploadImage(String base64, String filename) {
        this.base64 = base64;
        this.filename = filename;
    }
}
