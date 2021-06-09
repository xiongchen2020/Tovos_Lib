package com.example.djilib.dji.component.aircraft.flightAssistant;

import dji.common.error.DJIError;
import dji.common.flightcontroller.flightassistant.ObstacleAvoidanceSensorState;
import dji.common.flightcontroller.flightassistant.PerceptionInformation;

public interface CustomFlightAssistantListener {

    void setDownwardAvoidanceEnabled(boolean isable);

    void setUpwardAvoidanceEnabled(boolean isable);

    void setLandingProtectionEnabled(boolean isable);

    void setActiveObstacleAvoidanceEnabled(boolean isable);

    void setMaxPerceptionDistance(float var, PerceptionInformation.DJIFlightAssistantObstacleSensingDirection flightAssistantObstacleSensingDirection);

    void setObstacleAvoidanceSensorState(ObstacleAvoidanceSensorState obstacleAvoidanceSensorState);

    void getObstaclesAvoidanceDistance(float var, PerceptionInformation.DJIFlightAssistantObstacleSensingDirection flightAssistantObstacleSensingDirection);

    void setCollisionAvoidanceEnabled(DJIError djiError);

    void getCollisionAvoidanceEnabled(Boolean aBoolean);

    void setVisionAssistedPositioningEnabled(DJIError djiError);

    void getVisionAssistedPositioningEnabled(Boolean aBoolean);

    void setRTHObstacleAvoidanceEnabled(DJIError djiError);

    void getRTHObstacleAvoidanceEnabled(Boolean aBoolean);

    void getFlightAssistantFailure(DJIError djiError);

    void getPrecisionLandingEnabled(Boolean aBoolean);

    void getPrecisionLandingEnabledFailure(DJIError djiError);

    void setPrecisionLandingEnabled(DJIError djiError);
}
