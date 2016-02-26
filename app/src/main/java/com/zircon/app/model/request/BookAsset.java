package com.zircon.app.model.request;

import com.zircon.app.model.Asset;

/**
 * Created by jikoobaruah on 22/02/16.
 */
public class BookAsset {

    public BookAsset(Asset asset, String startTime, boolean prepaid, String description, int status) {
        this.asset = asset;
        this.startTime = startTime;
        this.prepaid = prepaid;
        this.description = description;
        this.status = status;
    }

    public BookAsset(String assetid, String startTime, boolean prepaid, String description, int status) {
        this.assetid = assetid;
        this.startTime = startTime;
        this.prepaid = prepaid;
        this.description = description;
        this.status = status;
    }

    public Asset asset;

    public String assetid;

    public String startTime;

    public boolean prepaid =false;

    public String description;

    public int status;

    public interface STATUS {
        int ACTIVE = 1;
    }
}
