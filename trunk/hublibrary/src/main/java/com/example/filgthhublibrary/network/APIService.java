package com.example.filgthhublibrary.network;



import com.example.filgthhublibrary.network.bean.BaseResponse;
import com.example.filgthhublibrary.network.bean.ResLogin;
import com.example.filgthhublibrary.network.bean.ResPlay;
import com.example.filgthhublibrary.network.bean.ResPlayStatus;
import com.example.filgthhublibrary.network.bean.ResPublish;
import com.example.filgthhublibrary.network.bean.ResRegisterUser;
import com.example.filgthhublibrary.network.bean.ResDrones;
import com.example.filgthhublibrary.network.bean.ResTeamMember;
import com.example.filgthhublibrary.network.bean.ResToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface APIService {

    @POST("auth/login")
    Observable<BaseResponse<ResLogin>> login(@Header("FH-ReqId") String reqId, @Body RequestBody requestBody);

    @POST("auth/updatetoken")
    Observable<BaseResponse<ResToken>> updatetoken(@HeaderMap Map<String, String> headers);

    @POST("users")
    Observable<BaseResponse<ResRegisterUser>> createUser(@HeaderMap Map<String, String> headers,@Body RequestBody requestBody);

    @GET
    Observable<BaseResponse<ResToken>> getUserInformation(@HeaderMap Map<String, String> headers, @Url String url, @QueryMap Map<String,String> params);

    @GET("drones")
    Observable<BaseResponse> drones(@HeaderMap Map<String, String> headers);

    @POST
    Observable<BaseResponse> addMember(@HeaderMap Map<String, String> headers,@Url String url, @Body RequestBody requestBody);

    @GET("teams")
    Observable<BaseResponse> teams(@HeaderMap Map<String, String> headers);

    @POST
    Observable<BaseResponse> bindDrone(@HeaderMap Map<String, String> headers,@Url String url, @Body RequestBody requestBody);

    @POST("online/flightevents")
    Observable<BaseResponse> flightevents(@HeaderMap Map<String, String> headers,@Body RequestBody requestBody);

    @GET("streams")
    Observable<BaseResponse> streams(@HeaderMap Map<String, String> headers);

    @GET
    Observable<BaseResponse<ResPublish>> publishLive(@HeaderMap Map<String, String> headers,@Url String url,  @QueryMap Map<String,String> params);

    @GET
    Observable<BaseResponse<ResPlay>> play(@HeaderMap Map<String, String> headers,@Url String url,  @QueryMap Map<String,String> params);

    @GET
    Observable<BaseResponse<ResPlayStatus>> playStatus(@HeaderMap Map<String, String> headers,@Url String url, @QueryMap Map<String,String> params);


    @GET("online/drones")
    Observable<BaseResponse> onLineDrones(@HeaderMap Map<String, String> headers);

    @Multipart
    @POST("objects")
    Observable<BaseResponse> uploadObject(@HeaderMap Map<String, String> headers,@Part List<MultipartBody.Part> partList);

    @GET("users")
    Observable<BaseResponse> getAllUsers(@HeaderMap Map<String, String> headers);


    @GET
    Observable<BaseResponse> teamMembers(@HeaderMap Map<String, String> headers, @Url String url, @QueryMap Map<String,String> params);

    @GET("statistics/flightrecords")
    Observable<BaseResponse> getStatisticsRecords(@HeaderMap Map<String, String> headers, @QueryMap Map<String,Object> params);

    @GET("flightrecords")
    Observable<BaseResponse> getFilghtRecords(@HeaderMap Map<String, String> headers, @QueryMap Map<String,Object> params);

    @GET
    Observable<BaseResponse> getFilghtRecordById(@HeaderMap Map<String, String> headers, @Url String url, @QueryMap Map<String,Object> params);


    @GET("objects")
    Observable<BaseResponse> getMediaList(@HeaderMap Map<String, String> headers,@QueryMap Map<String,Object> params);

    @Streaming
    @GET
    Observable<ResponseBody> downloadMedia(@HeaderMap Map<String, String> headers, @Url String url, @QueryMap Map<String,String> params);

    @GET
    Observable<ResponseBody> getDronesByTeamId(@HeaderMap Map<String, String> headers, @Url String url, @QueryMap Map<String,Object> params);
}
