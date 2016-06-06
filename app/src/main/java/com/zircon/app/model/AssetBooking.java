package com.zircon.app.model;

/**
 * Created by jikoobaruah on 23/02/16.
 */
public class AssetBooking {

    public String id;
    public String assetid;
    public String startTime;
    public boolean prepaid;
    public int status;

    @Override
    public boolean equals(Object o) {
        return (((AssetBooking)o).id).equals(this.id);
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(id);
    }
}
