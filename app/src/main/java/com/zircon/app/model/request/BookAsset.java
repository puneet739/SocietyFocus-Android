package com.zircon.app.model.request;

/**
 * Created by jikoobaruah on 22/02/16.
 */
public class BookAsset {

    public BookAsset(String assetid, String startTime, boolean prepaid, String description, int status) {
        this.assetid = assetid;
        this.startTime = startTime;
        this.prepaid = prepaid;
        this.description = description;
        this.status = status;
    }

    public String assetid;

    public String startTime;

    public boolean prepaid =false;

    public String description;

    public int status;

    public interface STATUS {
        int ACTIVE = 1;
    }
}
