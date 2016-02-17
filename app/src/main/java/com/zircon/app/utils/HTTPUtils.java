package com.zircon.app.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jikoobaruah on 17/02/16.
 */
public class HTTPUtils {
    public static boolean isValidUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
