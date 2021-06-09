package com.example.djilib.dji.healthinfomation;

public class HealthInfo {
    private String alarmId;
    private String tipEn;
    private String tipCn;

    public HealthInfo() {
    }

    public HealthInfo(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getTipEn() {
        return tipEn;
    }

    public void setTipEn(String tipEn) {
        this.tipEn = tipEn;
    }

    public String getTipCn() {
        return tipCn;
    }

    public void setTipCn(String tipCn) {
        this.tipCn = tipCn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HealthInfo)) return false;

        HealthInfo that = (HealthInfo) o;

        return alarmId != null ? alarmId.equals(that.alarmId) : that.alarmId == null;
    }

    @Override
    public int hashCode() {
        return alarmId != null ? alarmId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HealthInfo{" +
                "alarmId='" + alarmId + '\'' +
                ", tipEn='" + tipEn + '\'' +
                ", tipCn='" + tipCn + '\'' +
                '}';
    }
}
