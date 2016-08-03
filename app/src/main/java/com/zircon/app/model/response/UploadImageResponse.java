package com.zircon.app.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cbc-03 on 08/03/16.
 */
public class UploadImageResponse {

    @SerializedName("body")
    String body;

    @SerializedName("status")
    int status;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
