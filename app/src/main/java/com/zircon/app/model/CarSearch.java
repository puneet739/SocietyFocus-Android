package com.zircon.app.model;

import com.google.gson.annotations.SerializedName;
import com.zircon.app.model.User;
import com.zircon.app.model.response.BaseResponse;

/**
 * Created by Cbc-03 on 10/24/16.
 */
public class CarSearch {
    @SerializedName("vehicleid")
    public int VehicleId;

    public User user;

    @SerializedName("vehiclenumber")
    public String VehicleNumber;

    @SerializedName("type")
    public int Type;

    @SerializedName("status")
    public int Status;

    @SerializedName("parkingslot")
    public String ParkingSlot;
}
