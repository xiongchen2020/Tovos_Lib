package com.example.filgthhublibrary.network;

import com.example.filgthhublibrary.util.FHSignUtil;
import com.google.gson.Gson;

import com.example.filgthhublibrary.network.bean.BaseResponse;
import com.example.filgthhublibrary.network.bean.ReqBindDrone;
import com.example.filgthhublibrary.network.bean.ReqLogin;
import com.example.filgthhublibrary.network.bean.ReqFlightevents;
import com.example.filgthhublibrary.network.bean.ResLogin;
import com.example.filgthhublibrary.network.bean.ResPlay;
import com.example.filgthhublibrary.network.bean.ResPublish;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class FlightHubModel {

    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    private String token = "";
    private String signKey = "";


    public FlightHubModel() {
        retrofit = APIManager.getInstance().initRetrofit();

    }


    public void removeToken() {
        token = "";
    }


    public void removeSignKey() {
        signKey = "";
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getSignKey() {
        return signKey;
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     */
    public Observable<BaseResponse<ResLogin>> Login(String account, String password) {

        ReqLogin req = new ReqLogin();
        req.setAccount(account);
        req.setPassword(password);
        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);

        APIService service = retrofit.create(APIService.class);

        return service.login("req1", requestBody);

    }


    /**
     * 获取团队
     */
    public Observable<BaseResponse> teams() {

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req3", "");

        if (headers == null) {
            return null;
        }
        Observable<BaseResponse> observable = service.teams(headers);

        return observable;
    }

    /**
     * 获取飞机
     */
    public Observable<BaseResponse> drones() {

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req11", "");
        if (headers == null) {
            return null;
        }
        Observable<BaseResponse> observable = service.drones(headers);

        return observable;
    }

    /**
     * 绑定飞机
     *
     * @param teamId
     */
    public Observable<BaseResponse> bindDroneByIndex(int teamId, String type, String sn, int index) {

        String url = "teams/" + teamId + "/drones";

        ReqBindDrone req = new ReqBindDrone();
        req.setTeamId(teamId);
        req.setDroneType(type);
        req.setSn(sn);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req4", content);

        if (headers == null) {
            return null;
        }
        Observable<BaseResponse> observable = service.bindDrone(headers, url, requestBody);

        return observable;


    }


    /**
     * 绑定飞机
     *
     * @param teamId
     */
    public Observable<BaseResponse> bindDrone(int teamId, String type, String sn) {

        String url = "teams/" + teamId + "/drones";

        ReqBindDrone req = new ReqBindDrone();
        req.setTeamId(teamId);
        req.setDroneType(type);
        req.setSn(sn);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req4", content);

        if (headers == null) {
            return null;
        }
        Observable<BaseResponse> observable = service.bindDrone(headers, url, requestBody);

        return observable;

    }

    /**
     * 飞行记录
     */
    public Observable<BaseResponse> flightevents(ReqFlightevents req) {


        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);

        Map<String, String> headers = addHeaders("req5", content);
        if (headers == null) {
            return null;
        }

        APIService service = retrofit.create(APIService.class);
        Observable<BaseResponse> observable = service.flightevents(headers, requestBody);
        return observable;

    }


    /**
     * 获取发布实时流的地址
     *
     * @param sn
     */
    public Observable<BaseResponse<ResPublish>> publish(String sn) {

        String url = "drones/" + sn + "/stream/publish";
        TreeMap<String, String> snMap = new TreeMap<>();
        snMap.put("sn", sn);

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req6", "");
        if (headers == null) {
            return null;
        }
        Observable<BaseResponse<ResPublish>> observable = service.publishLive(headers, url, snMap);

        return observable;
    }


    /**
     * 获取播放实时流的地址
     *
     * @param sn
     */
    public Observable<BaseResponse<ResPlay>> play(String sn) {
        String url = "drones/" + sn + "/stream/play";

        TreeMap<String, String> snMap = new TreeMap<>();
        snMap.put("sn", sn);


        //  APIService service = manager.initRetrofit("req7","").create(APIService.class);
        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req7", "");
        if (headers == null) {
            return null;
        }
        Observable<BaseResponse<ResPlay>> observable = service.play(headers, url, snMap);

        return observable;
    }


    /**
     * 获取在线无人机
     */
    public Observable<BaseResponse> onLineDrones() {

        //  APIService service = manager.initRetrofit("req12","").create(APIService.class);
        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req12", "");
        if (headers == null) {
            return null;
        }
        Observable<BaseResponse> observable = service.onLineDrones(headers);

        return observable;
    }

    /**
     * 上传媒体文件
     */
    public Observable<BaseResponse> uploadObject(String category, String contentType, byte[] file, String filename, String recordId, int teamId) {

        //1.创建MultipartBody.Builder对象
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //2.获取图片，创建请求体
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
        builder.addFormDataPart("category", category);
        builder.addFormDataPart("contentType", contentType);
        builder.addFormDataPart("file", filename, body);
        builder.addFormDataPart("filename", filename);
        builder.addFormDataPart("recordId", recordId);
        builder.addFormDataPart("teamId", String.valueOf(teamId));

        List<MultipartBody.Part> parts = builder.build().parts();

        Gson gson = new Gson();
        String content = gson.toJson(parts);

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req13", content);
        if (headers == null) {
            return null;
        }
        Observable<BaseResponse> observable = service.uploadObject(headers, parts);

        return observable;
    }


    /**
     * 获取正在直播的飞机列表
     */
    public Observable<BaseResponse> streams() {
        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req15", "");
        if (headers == null) {
            return null;
        }

        return service.streams(headers);
    }


    /**
     * 获取团队成员
     */
    public Observable<BaseResponse> teamMembers(int teamId) {
        String url = "teams/" + teamId + "/members";

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req16", "");
        if (headers == null) {
            return null;
        }

        TreeMap<String, String> teamIdMap = new TreeMap<>();
        teamIdMap.put("id", teamId + "");


        return service.teamMembers(headers, url, teamIdMap);
    }

    /**
     * 获取飞行总记录
     */
    public Observable<BaseResponse> getStatisticsRecords(int teamId, int userId, String droneSn, long startTs, long endTs) {

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req17", "");
        if (headers == null) {
            return null;
        }

        TreeMap<String, Object> teamIdMap = new TreeMap<>();
        teamIdMap.put("teamId", teamId);
        teamIdMap.put("userId", userId);
        teamIdMap.put("droneSn", droneSn);
        teamIdMap.put("startTs", startTs);
        teamIdMap.put("endTs", endTs);

        return service.getStatisticsRecords(headers, teamIdMap);
    }


    /**
     * 获取飞行记录
     */
    public Observable<BaseResponse> getFlightRecords(int teamId, int userId, String droneSn, long startTs, long endTs, int offset, int limit) {

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req18", "");
        if (headers == null) {
            return null;
        }

        TreeMap<String, Object> teamIdMap = new TreeMap<>();
        teamIdMap.put("teamId", teamId);
        // teamIdMap.put("userId", userId);
        teamIdMap.put("sn", droneSn);
        teamIdMap.put("startTs", startTs);
        teamIdMap.put("endTs", endTs);
        teamIdMap.put("offset", offset);
        teamIdMap.put("limit", limit);


        return service.getFilghtRecords(headers, teamIdMap);
    }

    /**
     * 获取飞行记录
     */
    public Observable<BaseResponse> getFlightRecordById(String id) {
        String url = "flightrecords/" + id;
        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req20", "");
        if (headers == null) {
            return null;
        }

        TreeMap<String, Object> teamIdMap = new TreeMap<>();
        teamIdMap.put("id", id);


        return service.getFilghtRecordById(headers, url, teamIdMap);
    }


    /**
     * 获取媒体列表
     */
    public Observable<BaseResponse> getMediaList(String category, int teamId, String account, String droneSn, long startTs, long endTs, int offset, int limit) {

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req19", "");
        if (headers == null) {
            return null;
        }

        TreeMap<String, Object> teamIdMap = new TreeMap<>();
        teamIdMap.put("category", category);
        teamIdMap.put("teamId", teamId);
        teamIdMap.put("account", account);
        teamIdMap.put("sn", droneSn);
        teamIdMap.put("startTs", startTs);
        teamIdMap.put("endTs", endTs);
        teamIdMap.put("offset", offset);
        teamIdMap.put("limit", limit);
        return service.getMediaList(headers, teamIdMap);


    }


    /**
     * 获取object
     */
    public Observable<ResponseBody> downloadMedia(String category, String id) {
        String url = "objects/" + category + "/" + id;

        APIService service = retrofit.create(APIService.class);

        Map<String, String> headers = addHeaders("req17", "");
        if (headers == null) {
            return null;
        }

        TreeMap<String, String> teamIdMap = new TreeMap<>();
        teamIdMap.put("category", category);
        teamIdMap.put("id", id);
        return service.downloadMedia(headers, url, teamIdMap);


    }

    public void onUnsubscribe() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();

        }
    }


    public void addSubscription(Disposable subscriber) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();

        }
        compositeDisposable.add(subscriber);
    }


    private Map<String, String> addHeaders(String reqId, String body) {

        if (token == "" || signKey == "") {
            return null;
        }
        long ts = System.currentTimeMillis();

        String sign = FHSignUtil.getSign(token, signKey, ts, body);
        TreeMap<String, String> headerMap = new TreeMap<>();
        headerMap.put("FH-AppId", token);
        headerMap.put("FH-ReqId", reqId);
        headerMap.put("FH-Ts", ts + "");
        headerMap.put("FH-Sign", sign);

        return headerMap;
    }

}
