package com.example.djilib.dji.map;

import android.app.Activity;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.dji.mapkit.core.camera.DJICameraUpdate;
import com.dji.mapkit.core.camera.DJICameraUpdateFactory;
import com.dji.mapkit.core.maps.DJIMap;
import com.dji.mapkit.core.models.DJIBitmapDescriptorFactory;
import com.dji.mapkit.core.models.DJICameraPosition;
import com.dji.mapkit.core.models.DJILatLng;
import com.dji.mapkit.core.models.annotations.DJIMarker;
import com.dji.mapkit.core.models.annotations.DJIMarkerOptions;
import com.dji.mapkit.core.models.annotations.DJIPolyline;
import com.dji.mapkit.core.models.annotations.DJIPolylineOptions;
import com.example.djilib.R;
import com.example.djilib.dji.util.GCJ2WGSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dji.ux.widget.MapWidget;

public class MyMapManager {

    private MapWidget mapWidget;
    private boolean isAdd = false;
    private boolean isFlyZoneVisable = false;
    private final Map<Integer, DJIMarker> mMarkers = new ConcurrentHashMap<Integer, DJIMarker>();
    private final Map<Integer, DJIPolyline> mPloyLines = new ConcurrentHashMap<Integer, DJIPolyline>();
    private List<DJILatLng> latLngList = new ArrayList<>();
    private MyMapListener listener;
    private DJIMarker selectedMarker;
    private DJIPolyline djiPolyline;
    private DJIPolylineOptions djiPolylineOptions;
    private double droneLocationLat = 181, droneLocationLng = 181;
    private Activity appCompatActivity;
    DJIMarker rcMarker;
    DJIMarker homeMarker;
    public MyMapManager(MapWidget mapWidget, MyMapListener listener, Activity appCompatActivity) {
        this.mapWidget = mapWidget;
        this.listener = listener;
        this.appCompatActivity = appCompatActivity;
        setListenter();

        selectedMarker = null;
    }

    public void setMap(boolean ismax) {
        mapWidget.getMap().getUiSettings().setMyLocationButtonEnabled(ismax);
        mapWidget.getMap().getUiSettings().setMapToolbarEnabled(ismax);
        mapWidget.getMap().getUiSettings().setCompassEnabled(ismax);
        mapWidget.getMap().getUiSettings().setZoomControlsEnabled(false);
    }

//声明AMapLocationClient类对象
public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                LatLonPoint latLonPoint= GCJ2WGSUtils.toWGS84Point(amapLocation.getLatitude(),amapLocation.getLongitude());
                markRcGPS(new DJILatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude()));
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    public void getLocation(){
//初始化定位
        mLocationClient = new AMapLocationClient(appCompatActivity);
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
       // mLocationOption.setInterval(1000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    public void changeMapType(DJIMap.MapType mapType) {
        mapWidget.getMap().setMapType(mapType);
    }


    public void setListenter() {

        mapWidget.getMap().setOnMarkerClickListener(new DJIMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(DJIMarker djiMarker) {
                listener.selectedMarkerItem(djiMarker);
                return true;
            }
        });
        mapWidget.getMap().setOnMapLongClickListener(new DJIMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(DJILatLng djiLatLng) {
                markWaypoint(djiLatLng);
                listener.addItem(djiLatLng);
            }
        });
    }
    /**
     * 画点位
     *
     * @param point
     */
    public void markWaypoint(DJILatLng point) {
        //Create MarkerOptions object
//        DJIMarkerOptions markerOptions = new DJIMarkerOptions();
//        markerOptions.position(point);
//        //markerOptions.icon(DJIBitmapDescriptorFactory.fromResource(R.drawable.mapbox_marker_icon_default));
//        DJIMarker marker = mapWidget.getMap().addMarker(markerOptions);
//        mMarkers.put(mMarkers.size(), marker);
        latLngList.add(point);
    }

    public int getLatLngSize(){
        return latLngList.size();
    }

    /**
     * 画遥控器位置
     *
     * @param point
     */
    public void markRcGPS(DJILatLng point) {
        if (rcMarker!=null){
            rcMarker.remove();
        }
        //Create MarkerOptions object
        DJIMarkerOptions markerOptions = new DJIMarkerOptions();
        markerOptions.position(point);
        markerOptions.icon(DJIBitmapDescriptorFactory.fromResource(R.drawable.iv_rc));
        rcMarker = mapWidget.getMap().addMarker(markerOptions);
        rcMarker.setTag("rc");



    }
    /**
     * 画home位置
     *
     * @param point
     */
    public void markhomePoint(DJILatLng point) {
        if (homeMarker!=null){
            homeMarker.remove();
        }
        //Create MarkerOptions object
        DJIMarkerOptions markerOptions = new DJIMarkerOptions();
        markerOptions.position(point);
        markerOptions.icon(DJIBitmapDescriptorFactory.fromResource(R.drawable.iv_home));
        homeMarker = mapWidget.getMap().addMarker(markerOptions);
        homeMarker.setTag("home");



    }
    public DJIMarker getSelectedPoint() {
        return selectedMarker;
    }

    /**
     * 删除某个点位
     */
    public void remoceWaypoint() {
        if (selectedMarker != null) {
            selectedMarker.remove();
        }
        selectedMarker = null;
    }


    public void cleanMap() {
       // mapWidget.getMap().clear();
        if (mMarkers.size() > 0) {
            for (int i = 0; i < mMarkers.size(); i++) {
                mMarkers.get(i).remove();
            }
            djiPolyline.remove();
            latLngList.clear();
        }
    }


    /**
     * 划线
     */
    public void PolyLine() {
        djiPolylineOptions = (new DJIPolylineOptions())
                .addAll(latLngList)
                .width(10)
                .color(Color.RED);
        djiPolyline = mapWidget.getMap().addPolyline(djiPolylineOptions);
    }

    public void removeLine() {

    }

    public void updateDroneLocation(double lat, double lng) {

        DJILatLng pos = new DJILatLng(droneLocationLat, droneLocationLng);
        //Create MarkerOptions object
        final DJIMarkerOptions markerOptions = new DJIMarkerOptions();
        markerOptions.position(pos);
        markerOptions.icon(DJIBitmapDescriptorFactory.fromResource(R.drawable.ic_compass_aircraft));

//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (droneMarker != null) {
//                    droneMarker.remove();
//                }
//
//                if (checkGpsCoordination(droneLocationLat, droneLocationLng)) {
//                    droneMarker = mapWidget.getMap().addMarker(markerOptions);
//                }
//            }
//        });
    }

    public void cameraUpdate(double lat, double lng) {
        DJILatLng pos = new DJILatLng(lat, lng);
        float zoomlevel = (float) 20.0;
        DJICameraPosition cameraPosition = DJICameraPosition.fromLatLngZoom(pos, zoomlevel);
        DJICameraUpdate cu = DJICameraUpdateFactory.newCameraPosition(cameraPosition);

        mapWidget.getMap().moveCamera(cu);
    }

    public static boolean checkGpsCoordination(double latitude, double longitude) {
        return (latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) && (latitude != 0f && longitude != 0f);
    }

    public void setFlightPathVisible(boolean isVisible) {
        mapWidget.setFlightPathVisible(isVisible);
    }

    public boolean getFlightPathVisible() {
        return mapWidget.isFlightPathVisible();
    }

    public void showFlyZones(){
        mapWidget.showAllFlyZones();
        isFlyZoneVisable = true;
    }

    public void hideAllFlyZones(){
        mapWidget.hideAllFlyZones();
        isFlyZoneVisable = false;
    }

    public boolean isFlyZoneVisable() {
        return isFlyZoneVisable;
    }

    public void setFlyZoneVisable(boolean flyZoneVisable) {
        isFlyZoneVisable = flyZoneVisable;
    }
}
