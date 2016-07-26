package com.zircon.app.utils;

import com.zircon.app.model.User;
import com.zircon.app.model.request.BookAsset;
import com.zircon.app.model.request.Complaint;
import com.zircon.app.model.response.AssetsResponse;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.model.response.BookAssetListResponse;
import com.zircon.app.model.response.BookAssetResponse;
import com.zircon.app.model.response.ComplaintCommentResponse;
import com.zircon.app.model.response.ComplaintListResponse;
import com.zircon.app.model.response.ComplaintResponse;
import com.zircon.app.model.response.GraphPhotoResponse;
import com.zircon.app.model.response.LoginResponse;
import com.zircon.app.model.response.MembersResponse;
import com.zircon.app.model.response.PanelResponse;
import com.zircon.app.model.response.SocietyListResponse;
import com.zircon.app.model.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public interface API {

    //    String SERVER_URL = "http://192.168.11.150:8080";
//    String SERVER_URL = "http://10.0.2.2:8080/";
    String SERVER_URL = "http://52.76.71.5:8090/";
    String API_PATH_PATTERN = "/zircon/services/";

    interface ILoginHeaderParams {
        String SOCIETY = "X-Society";
        String USERNAME = "X-Username";
        String PASSWORD = "X-Password";
        String DEVICE_ID = "X-DeviceID";
        String DEVICE_IDOld = "X-DeviceIDOld";
    }

    interface IPostLoginHeaderParams {
        String AUTH_TOKEN = "X-Auth-Token";

    }

    interface IAssetParams {
        String ID = "id";
    }

    interface IEventParams {
        String MONTH = "month";
        String YEAR = "year";
    }

/*
    @GET(API_PATH_PATTERN+"social/fblogin")
    public Call<LoginResponse>
    fblogin(@Header(ILoginHeaderParams.DEVICE_ID) String deviceID,@Header(ILoginHeaderParams.DEVICE_IDOld) String deviceIDOld,@Query("accesstoken") String fbtoken);
*/

    @GET
    public Call<GraphPhotoResponse> graphcall(@Url String url);

    @GET(API_PATH_PATTERN + "social/fblogin")
    public Call<LoginResponse>
    fblogin(@Query("accesstoken") String fbtoken);

    @POST(API_PATH_PATTERN + "access/login")
    public Call<LoginResponse>
    login(@Header(ILoginHeaderParams.SOCIETY) String society,
          @Header(ILoginHeaderParams.USERNAME) String username,
          @Header(ILoginHeaderParams.PASSWORD) String password,
          @Header(ILoginHeaderParams.DEVICE_ID) String deviceID,
          @Header(ILoginHeaderParams.DEVICE_IDOld) String deviceIDOld);

   /* @POST(API_PATH_PATTERN+"access/login")
    public Call<LoginResponse>
    login(@Header(ILoginHeaderParams.SOCIETY) String society,
          @Header(ILoginHeaderParams.USERNAME) String username,
          @Header(ILoginHeaderParams.PASSWORD) String password,
          @Header(ILoginHeaderParams.DEVICE_IDOld) String deviceIDOld);
*/
    @GET(API_PATH_PATTERN + "user/getalluser")
    public Call<MembersResponse> getAllUsers(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN + "society/asset/getall")
    public Call<AssetsResponse> getAllSocietyAssets(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN + "society/panel")
    public Call<PanelResponse> getSocietyPanel(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN + "society")
    public Call<SocietyListResponse> getSocietyList();

    @POST(API_PATH_PATTERN + "v1/complaint/save")
    public Call<ComplaintResponse> saveComplaint(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken, @Body Complaint complaint);

    @GET(API_PATH_PATTERN + "v1/complaint/getusercomplaint")
    public Call<ComplaintListResponse> getUserComplaints(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN + "v1/complaint/get/{id}")
    public Call<ComplaintCommentResponse> getComplaintDetails(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken, @Path("id") String complaintID);

    @POST(API_PATH_PATTERN + "user/modifyuser")
    public Call<UserResponse> modifyUser(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken, @Body User user);

    @GET(API_PATH_PATTERN + "user/modify/oldpass/{oldpassword}/newpass/{newpassword}/email/{email}")
    public Call<BaseResponse> modifyPassword(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken, @Path("oldpassword") String oldpassword, @Path("newpassword") String newpassword, @Path("email") String email);

    @POST(API_PATH_PATTERN + "society/asset/book")
    public Call<BookAssetResponse> saveAssetBooking(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken, @Body BookAsset bookAsset);

    @POST(API_PATH_PATTERN + "society/asset/getassetbyuser")
    public Call<BookAssetListResponse> getAssetBooking(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);
}
