package com.example.djilib.dji.djierror;

import dji.common.healthmanager.WarningLevel;

public enum  CustomHealthType {
    NONE(WarningLevel.NONE,"无警告"),
    NOTICE(WarningLevel.NOTICE,"注意"),
    CAUTION(WarningLevel.CAUTION,"警示"),
    WARNING(WarningLevel.WARNING,"警告"),
    SERIOUS_WARNING(WarningLevel.SERIOUS_WARNING,"严重警告"),
    UNKNOWN(WarningLevel.UNKNOWN,"未知");

    WarningLevel code;
    String error;

    CustomHealthType(WarningLevel code, String error) {
        this.code = code;
        this.error = error;
    }
    public static  String getError(WarningLevel code) {

        for (CustomHealthType e : CustomHealthType.values()) {
            if (e.code == code) {
                return e.error;
            }
        }
        return "RTK未连接";

    }
}
