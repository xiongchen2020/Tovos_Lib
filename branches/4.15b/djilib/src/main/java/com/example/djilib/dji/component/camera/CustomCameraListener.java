package com.example.djilib.dji.component.camera;

import dji.common.camera.CameraVideoStreamSource;
import dji.common.camera.SettingsDefinitions;

public interface CustomCameraListener {
    void getCameraViedoStreamSource(CameraVideoStreamSource cameraVideoStreamSource);

    void getCameraModel(SettingsDefinitions.DisplayMode displayMode);
}
