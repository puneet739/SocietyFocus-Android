package com.zircon.app.utils;

import com.zircon.app.model.request.Complaint;
import com.zircon.app.model.response.AssetsResponse;
import com.zircon.app.model.response.AssetSlotResponse;
import com.zircon.app.model.response.ComplaintCommentResponse;
import com.zircon.app.model.response.ComplaintListResponse;
import com.zircon.app.model.response.ComplaintResponse;
import com.zircon.app.model.response.LoginResponse;
import com.zircon.app.model.response.MembersResponse;
import com.zircon.app.model.response.PanelResponse;
import com.zircon.app.model.response.SocietyListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public interface API {

//    String SERVER_URL = "http://192.168.11.150:8080";
//    String SERVER_URL = "http://10.0.2.2:8080/";
    String SERVER_URL = "http://52.76.71.5:8090/";
    String API_PATH_PATTERN = "/zircon/services/";

    interface ILoginHeaderParams{
        String SOCIETY = "X-Society";
        String USERNAME = "X-Username";
        String PASSWORD = "X-Password";
        String DEVICE_ID = "X-DeviceID";

    }

    interface IPostLoginHeaderParams{
        String AUTH_TOKEN = "X-Auth-Token";

    }

    interface IAssetParams{
        String ID = "id";
    }

    interface IEventParams{
        String MONTH ="month";
        String YEAR ="year";
    }

    @POST(API_PATH_PATTERN+"access/login")
    public Call<LoginResponse> login(@Header(ILoginHeaderParams.SOCIETY) String society, @Header(ILoginHeaderParams.USERNAME) String username, @Header(ILoginHeaderParams.PASSWORD) String password, @Header(ILoginHeaderParams.DEVICE_ID) String deviceID);

    @GET(API_PATH_PATTERN+"user/getalluser")
    public Call<MembersResponse> getAllUsers(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN+"society/asset/getall")
    public Call<AssetsResponse> getAllSocietyAssets(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN+"society/panel")
    public Call<PanelResponse> getSocietyPanel(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN+"society/asset/getAssetCalendar")
    public Call<AssetSlotResponse> getAssetCalendar(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken,@Header(IAssetParams.ID) String assetID,@Header(IEventParams.MONTH) int month,@Header(IEventParams.YEAR) int year);

    @GET(API_PATH_PATTERN+"society")
    public Call<SocietyListResponse> getSocietyList();

    @POST(API_PATH_PATTERN+"v1/complaint/save")
    public Call<ComplaintResponse> saveComplaint(@Header(IPostLoginHeaderParams.AUTH_TOKEN)String authToken , @Body Complaint complaint);

    @GET(API_PATH_PATTERN+"v1/complaint/getusercomplaint")
    public Call<ComplaintListResponse> getUserComplaints(@Header(IPostLoginHeaderParams.AUTH_TOKEN) String authToken);

    @GET(API_PATH_PATTERN+"v1/complaint/get/{id}")
    public Call<ComplaintCommentResponse> getComplaintDetails(@Header(IPostLoginHeaderParams.AUTH_TOKEN)String authToken,@Path("id") String complaintID);




}
