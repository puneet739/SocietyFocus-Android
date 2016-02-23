package com.zircon.app.model.response;

import com.zircon.app.utils.API;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by jikoobaruah on 22/01/16.
 */
public class BaseResponse {

    public int status;

    public String message;

    public static SimpleDateFormat API_SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
    public static SimpleDateFormat API_SDF_2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    static {
        API_SDF.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
}
