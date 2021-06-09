package com.example.djilib.dji.component.camera;

import dji.common.error.DJIError;

public interface MyCameraInterface {

    void setStartShootState();

    void setEndShootState(int position);

    void setCameraMode(DJIError djiError);

    void getPicturesInfoSuccess();

    void getPicturesInfoFaild();
}
