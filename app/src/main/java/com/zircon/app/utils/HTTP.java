package com.zircon.app.utils;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class HTTP {

    private static HTTP mInstance;

    private HTTP(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApi = retrofit.create(API.class);
    }

    private API mApi;

    public static API getAPI(){
        if (mInstance ==null)
            mInstance = new HTTP();

        return mInstance.mApi;
    }
}
