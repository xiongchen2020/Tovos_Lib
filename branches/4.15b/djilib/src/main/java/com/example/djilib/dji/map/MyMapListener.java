package com.example.djilib.dji.map;

import com.dji.mapkit.core.models.DJILatLng;
import com.dji.mapkit.core.models.annotations.DJIMarker;

public interface MyMapListener {

    void selectedMarkerItem(DJIMarker djiMarker);

    void addItem(DJILatLng djiLatLng);
}
