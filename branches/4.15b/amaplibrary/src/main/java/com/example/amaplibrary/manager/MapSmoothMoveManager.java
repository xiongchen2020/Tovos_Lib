package com.example.amaplibrary.manager;

import android.content.Context;
import android.util.Pair;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.MovingPointOverlay;

import java.util.List;

public class MapSmoothMoveManager {

    private AMap mAMap;
    private Marker marker;
    private Context context;
    private MovingPointOverlay movingPointOverlay;
    private MovingPointOverlay.MoveListener listener;

    public MapSmoothMoveManager(Context context, AMap aMap){
        mAMap = aMap;
        this.context = context;
       // marker = new Marker()
    }

    public void setListener(MovingPointOverlay.MoveListener listener) {
        this.listener = listener;
    }

    private void initIcon(LatLng point){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        markerOptions.title("当前位置");
        markerOptions.visible(true);
//        BitmapDescriptor bitmapDescriptor =
//                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.landoff));
//        markerOptions.icon(bitmapDescriptor);
        marker = mAMap.addMarker(markerOptions);
    }

    public void getMove(List<LatLng> points ,int times){
        // 获取轨迹坐标点

//        LatLngBounds bounds = new LatLngBounds(points.get(0), points.get(points.size() - 2));
//        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        initIcon(points.get(0));
        movingPointOverlay = new MovingPointOverlay(mAMap, marker);

        // 设置滑动的图标
        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());
        // 设置滑动的轨迹左边点
        movingPointOverlay.setPoints(subList);
        // 设置滑动的总时间
        movingPointOverlay.setTotalDuration(times);

        movingPointOverlay.setMoveListener(listener);
        // 开始滑动
        //smoothMarker.startSmoothMove();
    }

    public void startMove(){
        movingPointOverlay.startSmoothMove();
    }

    public void stopMove(){
        movingPointOverlay.stopMove();
    }

    public void desotry(){
        movingPointOverlay.destroy();
    }

    public LatLng getPosition(){
       return movingPointOverlay.getPosition();
    }
}
