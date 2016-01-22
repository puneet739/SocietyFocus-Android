package com.zircon.app.utils;

import com.zircon.app.model.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public interface API {

//    String SERVER_URL = "http://192.168.0.108:8080";
    String SERVER_URL = "http://10.0.2.2:8080/";
    String API_PATH_PATTERN = "/zircon/services/";

    interface ILoginHeaderParams{
        String SOCIETY = "X-Society";
        String USERNAME = "X-Username";
        String PASSWORD = "X-Password";

    }

    @POST(API_PATH_PATTERN+"access/login")
    public Call<LoginResponse> login(@Header(ILoginHeaderParams.SOCIETY) String society, @Header(ILoginHeaderParams.USERNAME) String username, @Header(ILoginHeaderParams.PASSWORD) String password);
}
