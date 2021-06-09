package com.example.amaplibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;

import com.amap.api.maps.AMap;
import com.example.amaplibrary.view.CustomAMap;
import com.example.commonlib.utils.LogUtil;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MovePointActivity extends AppCompatActivity {

    public Toolbar toolbar;
    protected MediaController mediaController;
    protected CustomAMap customAMap;


    public void initMoveView(Bundle savedInstanceState){
        setContentView(R.layout.move_layout);
        toolbar= findViewById(R.id.toolbar);
        initToolbar("飞行轨迹回放", "");
        //initMapView(savedInstanceState);
        mediaController = findViewById(R.id.mp);
        customAMap = findViewById(R.id.customMap);
        customAMap.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        customAMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        customAMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        customAMap.onPause();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        customAMap.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.clear();

        getMenuInflater().inflate(R.menu.map_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();//当点击一个条目时，不显示另外一个
        if (itemId == R.id.wx_type) {
            customAMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        } else if (itemId == R.id.num_type) {
            customAMap.setMapType(AMap.MAP_TYPE_NORMAL);
        } else if (itemId == R.id.night_type) {
            customAMap.setMapType(AMap.MAP_TYPE_NIGHT);
        }
        return true;
    }



    public void initToolbar(String title, String Sub) {
        //标题+颜色
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.YELLOW);
        //子标题+颜色
        toolbar.setSubtitle(Sub);
        toolbar.setSubtitleTextColor(Color.GREEN);

        setSupportActionBar(toolbar);
    }

}
