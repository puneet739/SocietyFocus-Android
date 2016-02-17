package com.zircon.app.utils;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 17/02/16.
 */
public abstract class AuthCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Response<T> response) {
        if (response.code() == 401){
            onAuthError();
        }else if (response.isSuccess()){
            parseSuccessResponse(response);
        }
    }

    protected abstract void onAuthError();

    protected abstract void parseSuccessResponse(Response<T> response);



}
