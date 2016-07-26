package com.zircon.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jikoobaruah on 25/01/16.
 */
public class Asset implements Parcelable {

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

    protected Asset(Parcel in) {
        id = in.readString();
        description = in.readString();
        contactno = in.readString();
        email = in.readString();
        charges = in.readString();
        category = in.readString();
        status = in.readString();
        duration = in.readString();
        img = in.readString();
    }

    public static final Creator<Asset> CREATOR = new Creator<Asset>() {
        @Override
        public Asset createFromParcel(Parcel in) {
            return new Asset(in);
        }

        @Override
        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeString(contactno);
        dest.writeString(email);
        dest.writeString(charges);
        dest.writeString(category);
        dest.writeString(status);
        dest.writeString(duration);
        dest.writeString(img);
    }
}
