package com.example.amaplibrary.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.overlay.MovingPointOverlay;
import com.example.amaplibrary.manager.MapSmoothMoveManager;
import com.example.amaplibrary.R;
import com.example.commonlib.utils.LogUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAMap extends LinearLayout implements AMap.OnCameraChangeListener,MovingPointOverlay.MoveListener{

    public MapView mapView;
    public AMap aMap;
    public float zoomLeve = 15.0f;
    public float bearing = 30.0f;
    public float titl = 0.0f;
    private Context context;
    private Polyline polyline;

    protected MapSmoothMoveManager mapSmoothMoveManager;

    public CustomAMap(Context context) {
        super(context);
        this.context = context;
        initMapView();
    }

    public CustomAMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initMapView();
    }

    public CustomAMap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initMapView();
    }

    public CustomAMap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initMapView();
    }



    public void initMapView(){
        LayoutInflater.from(context).inflate(R.layout.map_layout, this);
        mapView = (MapView) findViewById(R.id.map);

    }

    public void onCreate(Bundle savedInstanceState){
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setOnCameraChangeListener(this);
        mapSmoothMoveManager = new MapSmoothMoveManager(context,aMap);
        mapSmoothMoveManager.setListener(this);
    }


    public void onDestroy() {
        mapView.onDestroy();
    }


    public void onResume() {
        mapView.onResume();
    }


    public void onPause() {
        mapView.onPause();
    }



    public void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 移动地图显示区域
     * @param latLng  中心点位置
     * @param zoom     放缩级别
     * @param tilt     倾斜度
     * @param bearing  偏向角
     */
    public void moveCamera(LatLng latLng, float zoom, float tilt, float bearing){
        zoomLeve = zoom;
        this.titl = tilt;
        this.bearing = bearing;
        CameraPosition cameraPosition = new CameraPosition(latLng, zoom, tilt, bearing);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.moveCamera(cameraUpdate);
    }

    /**
     * 地图添加线
     * @param latLngs
     * @param color
     */
    public void setPolyline(List<LatLng> latLngs, int color){
        polyline = aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(color));
    }

    /**
     * 清理线
     */
    public void clearPolyline(){
        polyline.remove();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    /**
     * 地图放缩监听
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        zoomLeve = cameraPosition.zoom;
        bearing = cameraPosition.bearing;
        titl =  cameraPosition.tilt;
    }

    /**
     * 设置地图模式
     * @param mapType
     */
    public void setMapType(int mapType){
        aMap.setMapType(mapType);
    }

    /**
     * 初始化地图滑动
     * @param latLngs
     * @param times
     */
    public void initMove(List<LatLng> latLngs,int times){
        mapSmoothMoveManager.getMove(latLngs,times);

        mapSmoothMoveManager.startMove();
    }

    public void stopMove(){
        mapSmoothMoveManager.stopMove();
    }

    /**
     * 地图滑动监听
     * @param v
     */
    @Override
    public void move(double v) {
        LogUtil.d("MovePointActivity:"+v);
        moveCamera(mapSmoothMoveManager.getPosition(),zoomLeve,titl,bearing);
    }
}
