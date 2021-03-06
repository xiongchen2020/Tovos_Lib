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

//??????AMapLocationClient?????????
public AMapLocationClient mLocationClient = null;
    //???????????????????????????
    public AMapLocationListener mLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation.getErrorCode() == 0) {
//??????????????????amapLocation?????????????????????
                LatLonPoint latLonPoint= GCJ2WGSUtils.toWGS84Point(amapLocation.getLatitude(),amapLocation.getLongitude());
                markRcGPS(new DJILatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude()));
            }
        }
    };
    //??????AMapLocationClientOption??????
    public AMapLocationClientOption mLocationOption = null;


    public void getLocation(){
//???????????????
        mLocationClient = new AMapLocationClient(appCompatActivity);
//????????????????????????
        mLocationClient.setLocationListener(mLocationListener);
        //?????????AMapLocationClientOption??????
        mLocationOption = new AMapLocationClientOption();
        //?????????????????????AMapLocationMode.Hight_Accuracy?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//?????????????????????AMapLocationMode.Battery_Saving?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //?????????????????????AMapLocationMode.Device_Sensors?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        //??????????????????,????????????,?????????2000ms?????????1000ms???
       // mLocationOption.setInterval(1000);
        //??????????????????????????????????????????
        mLocationClient.setLocationOption(mLocationOption);
        //????????????
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
     * ?????????
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
     * ??????????????????
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
     * ???home??????
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
     * ??????????????????
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
     * ??????
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
