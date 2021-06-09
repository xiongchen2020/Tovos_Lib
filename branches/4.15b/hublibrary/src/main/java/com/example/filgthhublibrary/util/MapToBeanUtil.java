package com.example.filgthhublibrary.util;

import com.example.filgthhublibrary.network.bean.ResDrones;

import com.example.filgthhublibrary.network.bean.ResFlightRecords;
import com.example.filgthhublibrary.network.bean.ResMediaList;
import com.example.filgthhublibrary.network.bean.ResOnLineDrone;
import com.example.filgthhublibrary.network.bean.ResRecordDetail;
import com.example.filgthhublibrary.network.bean.ResRecordPoint;
import com.example.filgthhublibrary.network.bean.ResStatisticsRecords;
import com.example.filgthhublibrary.network.bean.ResSummary;
import com.example.filgthhublibrary.network.bean.ResTeamMember;
import com.example.filgthhublibrary.network.bean.ResUserInfo;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class MapToBeanUtil {
    public static  void toOnLineDrone( ResOnLineDrone onLineDrone,Object o){
        LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>> map = (LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>>)o;
        ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps = map.get("list");
        if (linkedTreeMaps!=null&&linkedTreeMaps.size()>0){
            List<ResOnLineDrone.DroneOnlineModel> droneOnlineModels = new ArrayList<>();
            for (int i = 0; i < linkedTreeMaps.size(); i++) {
                ResOnLineDrone.DroneOnlineModel droneOnlineModel = new ResOnLineDrone.DroneOnlineModel();
                String sn = (String) linkedTreeMaps.get(i).get("sn");
                String name = (String) linkedTreeMaps.get(i).get("name");
                String type = (String) linkedTreeMaps.get(i).get("type");
                Double teamId = (Double) linkedTreeMaps.get(i).get("teamId");
                Double date = (Double) linkedTreeMaps.get(i).get("date");
                Double lat = (Double) linkedTreeMaps.get(i).get("lat");
                Double lng = (Double) linkedTreeMaps.get(i).get("lng");
                Double altitude = (Double) linkedTreeMaps.get(i).get("altitude");
                Double yaw = (Double) linkedTreeMaps.get(i).get("yaw");
                Double batteryLevel = (Double) linkedTreeMaps.get(i).get("batteryLevel");
                Double homeLat = (Double) linkedTreeMaps.get(i).get("homeLat");
                Double homeLng = (Double) linkedTreeMaps.get(i).get("homeLng");
                String account = (String) linkedTreeMaps.get(i).get("account");
                Double liveStatus = (Double) linkedTreeMaps.get(i).get("liveStatus");

                droneOnlineModel.setSn(sn);
                droneOnlineModel.setName(name);
                droneOnlineModel.setType(type);

                if ( teamId!=null&&teamId != Double.NaN) {
                    droneOnlineModel.setTeamId((Double.valueOf(teamId)).intValue());
                }

                if (date!=null&&date != Double.NaN ) {
                    droneOnlineModel.setDate((Double.valueOf(date)).intValue());
                }
                if (date!=null&&lat != Double.NaN ) {
                    droneOnlineModel.setLat((Double.valueOf(lat)).intValue());
                }
                if (lng!=null&&lng != Double.NaN ) {
                    droneOnlineModel.setLng((Double.valueOf(lng)).intValue());
                }

                if (altitude!=null&&altitude != Double.NaN ) {
                    droneOnlineModel.setAltitude((Double.valueOf(altitude)).intValue());
                }
                if (yaw!=null&&yaw != Double.NaN ) {
                    droneOnlineModel.setYaw((Double.valueOf(yaw)).intValue());
                }
                if (batteryLevel!=null&&batteryLevel != Double.NaN ) {
                    droneOnlineModel.setBatteryLevel((Double.valueOf(batteryLevel)).intValue());
                }
                if (homeLat!=null&&homeLat != Double.NaN ) {
                    droneOnlineModel.setHomeLat((Double.valueOf(homeLat)).intValue());
                }
                if (homeLng!=null&&homeLng != Double.NaN ) {
                    droneOnlineModel.setHomeLng((Double.valueOf(homeLng)).intValue());
                }

                droneOnlineModel.setAccount(account);

                if (liveStatus!=null&&liveStatus != Double.NaN ) {
                    droneOnlineModel.setLiveStatus((Double.valueOf(liveStatus)).intValue());
                }

                droneOnlineModels.add(droneOnlineModel);

            }
            onLineDrone.setList(droneOnlineModels);
        }

    }
    public static  void toDrones(ResDrones drones, Object o){
        LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>> map = (LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>>)o;
        ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps = map.get("list");
        if (linkedTreeMaps!=null&&linkedTreeMaps.size()>0){
            List<ResDrones.DroneGetModel> droneGetModels = new ArrayList<>();
            for (int i = 0; i < linkedTreeMaps.size(); i++) {
                ResDrones.DroneGetModel droneGetModel = new  ResDrones.DroneGetModel();
                String sn = (String) linkedTreeMaps.get(i).get("sn");
                String name = (String) linkedTreeMaps.get(i).get("name");
                String type = (String) linkedTreeMaps.get(i).get("droneType");
                Double teamId = (Double) linkedTreeMaps.get(i).get("teamId");


                droneGetModel.setSn(sn);
                droneGetModel.setName(name);
                droneGetModel.setDroneType(type);

                if ( teamId!=null&&teamId != Double.NaN) {
                    droneGetModel.setTeamId((Double.valueOf(teamId)).intValue());
                }


                droneGetModels.add(droneGetModel);

            }
            drones.setDroneGetModels(droneGetModels);
        }

    }



    public static  void toStatisticsRecords(ResStatisticsRecords resStatisticsRecords, Object o){
        LinkedTreeMap<String, LinkedTreeMap<String, Object>> map = (LinkedTreeMap<String, LinkedTreeMap<String, Object>>) o;
        LinkedTreeMap<String, Object> linkedTreeMaps = map.get("statistics");

        List<String> names = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        Double totalDuration = (Double) linkedTreeMaps.get("totalDuration");
        Double avgDuration = (Double) linkedTreeMaps.get("avgDuration");
        LinkedTreeMap<String,Double> durationDistributed = (LinkedTreeMap<String, Double>) linkedTreeMaps.get("durationDistributed");

        Double v1 = (Double) durationDistributed.get("0-5");
        Double v2 = (Double) durationDistributed.get("5-10");
        Double v3 = (Double) durationDistributed.get("10-15");
        Double v4 = (Double) durationDistributed.get("15-20");
        Double v5 = (Double) durationDistributed.get("20+");
        names.add("0-5");
        names.add("5-10");
        names.add("10-15");
        names.add("15-20");
        names.add("20+");
        values.add(v1!=null&&v1!= Double.NaN? (Double.valueOf(v1)).intValue():0.0);
        values.add(v2!=null&&v2!= Double.NaN? (Double.valueOf(v2)).intValue():0.0);
        values.add(v3!=null&&v3!= Double.NaN? (Double.valueOf(v3)).intValue():0.0);
        values.add(v4!=null&&v4!= Double.NaN? (Double.valueOf(v4)).intValue():0.0);
        values.add(v5!=null&&v5!= Double.NaN? (Double.valueOf(v5)).intValue():0.0);

       resStatisticsRecords.setAvgDuration((Double.valueOf(avgDuration)).intValue());
       resStatisticsRecords.setTotalDuration((Double.valueOf(totalDuration)).intValue());
       resStatisticsRecords.setNames(names);
       resStatisticsRecords.setValues(values);
    }
    public static  void toFlightRecorda(ResFlightRecords resFlightRecords, Object o){
        LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) o;
        LinkedTreeMap<String,Object> linkedTreeMap = (LinkedTreeMap<String, Object>) map.get("pagination");
        Double pages = (Double) linkedTreeMap.get("pages");
        Double total = (Double) linkedTreeMap.get("total");
        if ( pages!=null&&pages != Double.NaN) {
            resFlightRecords.setPages((Double.valueOf(pages)).intValue());
        }
        if ( total!=null&&total != Double.NaN) {
            resFlightRecords.setTotal((Double.valueOf(total)).intValue());
        }

        ArrayList<LinkedTreeMap<String, Object>> linkedTreeMapArrayList = (ArrayList<LinkedTreeMap<String, Object>>) map.get("list");
        if (linkedTreeMapArrayList!=null&&linkedTreeMapArrayList.size()>0){
            List<ResFlightRecords.FlightGetModel> flightGetModels = new ArrayList<>();
            for (int i = 0; i < linkedTreeMapArrayList.size(); i++) {
                ResFlightRecords.FlightGetModel flightGetModel = new ResFlightRecords.FlightGetModel();
                String account = (String) linkedTreeMapArrayList.get(i).get("account");
                Double teamId = (Double) linkedTreeMapArrayList.get(i).get("teamId");
                String droneSn = (String) linkedTreeMapArrayList.get(i).get("droneSn");
                String droneType = (String) linkedTreeMapArrayList.get(i).get("droneType");
                Double duration = (Double) linkedTreeMapArrayList.get(i).get("duration");
                Double distance = (Double) linkedTreeMapArrayList.get(i).get("distance");
                Double maxHeight = (Double) linkedTreeMapArrayList.get(i).get("maxHeight");
                Double takeoffLongitude = (Double) linkedTreeMapArrayList.get(i).get("takeoffLongitude");
                Double takeoffLatitude = (Double) linkedTreeMapArrayList.get(i).get("takeoffLatitude");
                String id = (String) linkedTreeMapArrayList.get(i).get("id");

                flightGetModel.setAccount(account);
                flightGetModel.setDroneSN(droneSn);
                flightGetModel.setDroneType(droneType);
                flightGetModel.setId(id);

                if ( teamId!=null&&teamId != Double.NaN) {
                    flightGetModel.setTeamId((Double.valueOf(teamId)).intValue());
                }

                if (duration!=null&&duration != Double.NaN ) {
                    flightGetModel.setDuration(duration);
                }
                if (distance!=null&&distance != Double.NaN ) {
                    flightGetModel.setDistance(distance);
                }
                if (maxHeight!=null&&maxHeight != Double.NaN ) {
                    flightGetModel.setMaxHeight(maxHeight);
                }
                if (takeoffLongitude!=null&&takeoffLongitude != Double.NaN ) {
                    flightGetModel.setMaxHeight(takeoffLongitude);
                }
                if (takeoffLatitude!=null&&maxHeight != takeoffLatitude.NaN ) {
                    flightGetModel.setMaxHeight(takeoffLatitude);
                }

                flightGetModels.add(flightGetModel);

            }
            resFlightRecords.setFlightGetModelList(flightGetModels);
        }


    }

    public static  void toMediaList(ResMediaList resMediaList, Object o) {
        LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) o;
        LinkedTreeMap<String,Object> linkedTreeMap = (LinkedTreeMap<String, Object>) map.get("pagination");
        Double pages = (Double) linkedTreeMap.get("pages");
        Double total = (Double) linkedTreeMap.get("total");
        if ( pages!=null&&pages != Double.NaN) {
            resMediaList.setPages((Double.valueOf(pages)).intValue());
        }
        if ( total!=null&&total != Double.NaN) {
            resMediaList.setTotal((Double.valueOf(total)).intValue());
        }

        ArrayList<LinkedTreeMap<String, Object>> linkedTreeMapArrayList = (ArrayList<LinkedTreeMap<String, Object>>) map.get("list");
        if (linkedTreeMapArrayList!=null&&linkedTreeMapArrayList.size()>0){
            List<ResMediaList.FileListModel> fileListModelList = new ArrayList<>();
            for (int i = 0; i < linkedTreeMapArrayList.size(); i++) {
                ResMediaList.FileListModel fileListModel = new ResMediaList.FileListModel();
                String name = (String) linkedTreeMapArrayList.get(i).get("name");
                String path = (String) linkedTreeMapArrayList.get(i).get("path");
                String category = (String) linkedTreeMapArrayList.get(i).get("category");
                Double size = (Double) linkedTreeMapArrayList.get(i).get("size");
                Double date = (Double) linkedTreeMapArrayList.get(i).get("date");
                String thumbnail = (String) linkedTreeMapArrayList.get(i).get("thumbnail");
                String metadataPath = (String) linkedTreeMapArrayList.get(i).get("metadataPath");
                String contentType = (String) linkedTreeMapArrayList.get(i).get("contentType");
                String sn = (String) linkedTreeMapArrayList.get(i).get("sn");
                String account = (String) linkedTreeMapArrayList.get(i).get("account");

                fileListModel.setName(name);
                fileListModel.setPath(path);
                fileListModel.setCategory(category);
                fileListModel.setThumbnail(thumbnail);
                fileListModel.setMetadataPath(metadataPath);
                fileListModel.setContentType(contentType);
                fileListModel.setSn(sn);
                fileListModel.setAccount(account);

                if (size != null && size != Double.NaN) {
                    fileListModel.setSize(size);
                }

                if (date != null && date != Double.NaN) {
                    fileListModel.setDate(date);
                }


                fileListModelList.add(fileListModel);

            }
            resMediaList.setFlightGetModelList(fileListModelList);
        }



    }

    public static  void toTeamMember(List<ResTeamMember> resTeamMemberList, Object o){
        LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>> map = (LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>>)o;
        ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps = map.get("members");
        if (linkedTreeMaps!=null&&linkedTreeMaps.size()>0){
            for (int i = 0; i < linkedTreeMaps.size(); i++) {
                ResTeamMember resTeamMember = new ResTeamMember();
                Double userId = (Double) linkedTreeMaps.get(i).get("userId");
                String name = (String) linkedTreeMaps.get(i).get("name");
                String account = (String) linkedTreeMaps.get(i).get("account");
                Double role = (Double) linkedTreeMaps.get(i).get("role");

                resTeamMember.setName(name);
                resTeamMember.setAccount(account);

                if ( userId!=null&&userId != Double.NaN) {
                    resTeamMember.setUserId((Double.valueOf(userId)).intValue());
                }
                if ( role!=null&&role != Double.NaN) {
                    resTeamMember.setUserId((Double.valueOf(role)).intValue());
                }

                resTeamMemberList.add(resTeamMember);

            }
        }


    }


    public static  void toAllUser(List<ResUserInfo> resUserInfoList, Object o){
        LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>> map = (LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>>)o;
        ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps = map.get("list");
        if (linkedTreeMaps!=null&&linkedTreeMaps.size()>0){
            for (int i = 0; i < linkedTreeMaps.size(); i++) {
                ResUserInfo resUserInfo = new ResUserInfo();
                Double id = (Double) linkedTreeMaps.get(i).get("id");
                String name = (String) linkedTreeMaps.get(i).get("name");
                String account = (String) linkedTreeMaps.get(i).get("account");
                ArrayList<LinkedTreeMap<String, Object>> treeMaps = (ArrayList<LinkedTreeMap<String, Object>>) linkedTreeMaps.get(i).get("teams");
                if (treeMaps!=null&&treeMaps.size()>0){
                    ResUserInfo.TeamInfo teamInfo = new ResUserInfo.TeamInfo();
                    String nickName = (String) treeMaps.get(0).get("nickName");
                    Double level = (Double) treeMaps.get(0).get("level");
                    String teamName = (String) treeMaps.get(0).get("teamName");
                    Double teamId = (Double) treeMaps.get(0).get("teamId");

                    if ( teamId!=null&&teamId != Double.NaN) {
                        teamInfo.setTeamId((Double.valueOf(teamId)).intValue());
                    }
                    if (level!=null&&level != Double.NaN ) {
                        teamInfo.setLevel(level);
                    }
                    teamInfo.setNickName(nickName);
                    teamInfo.setTeamName(teamName);
                    resUserInfo.setTeamInfo(teamInfo);

                }
                if ( id!=null&&id != Double.NaN) {
                    resUserInfo.setId((Double.valueOf(id)).intValue());
                }
                resUserInfo.setName(name);
                resUserInfo.setAccount(account);


                resUserInfoList.add(resUserInfo);

            }
        }


    }



    public static  void toRecordDetail(ResRecordDetail resRecordDetail, Object o){
        LinkedTreeMap<String, Object> linkedTreeMapLinkedTreeMap = (LinkedTreeMap<String, Object>) o;
        LinkedTreeMap<String,Object> linkedTreeMap = (LinkedTreeMap<String, Object>) linkedTreeMapLinkedTreeMap.get("summary");
        ResSummary resSummary = new ResSummary();
        Double teamId = (Double) linkedTreeMap.get("teamId");
        String account = (String) linkedTreeMap.get("account");
        String sn = (String) linkedTreeMap.get("sn");
        String droneType = (String) linkedTreeMap.get("droneType");
        Double maxHeight = (Double) linkedTreeMap.get("maxHeight");
        Double duration = (Double) linkedTreeMap.get("duration");
        Double distance = (Double) linkedTreeMap.get("distance");
        Double takeoffLongitude = (Double) linkedTreeMap.get("takeoffLongitude");
        Double takeoffLatitude = (Double) linkedTreeMap.get("takeoffLatitude");

        if ( teamId!=null&&teamId != Double.NaN) {
            resSummary.setTeamId((Double.valueOf(teamId)).intValue()+"");
        }
        if (maxHeight!=null&&maxHeight != Double.NaN ) {
            resSummary.setMaxHeight(maxHeight);
        }
        if (duration!=null&&duration != Double.NaN ) {
            resSummary.setDuration(duration);
        }
        if (distance!=null&&distance != Double.NaN ) {
            resSummary.setDistance(distance);
        }
        if (takeoffLongitude!=null&&takeoffLongitude != Double.NaN ) {
            resSummary.setMaxHeight(takeoffLongitude);
        }
        if (takeoffLatitude!=null&&maxHeight != takeoffLatitude.NaN ) {
            resSummary.setMaxHeight(takeoffLatitude);
        }
        resSummary.setAccount(account);
        resSummary.setSn(sn);
        resSummary.setDroneType(droneType);

        resRecordDetail.setSummary(resSummary);

        ArrayList<LinkedTreeMap<String, Object>> mapArrayList = (ArrayList<LinkedTreeMap<String, Object>>) linkedTreeMapLinkedTreeMap.get("recordPoints");
      if (mapArrayList!=null&&mapArrayList.size()>0){

          List<ResRecordPoint> resRecordPointList = new ArrayList<>();
          //  ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps = map.get("list");
          for (int i = 0; i < mapArrayList.size(); i++) {
              ResRecordPoint resRecordPoint = new ResRecordPoint();
              Double date = (Double) mapArrayList.get(i).get("date");
              Double flightTime = (Double) mapArrayList.get(i).get("flightTime");
              Double lat = (Double) mapArrayList.get(i).get("lat");
              Double lng = (Double) mapArrayList.get(i).get("lng");
              Double altitude = (Double) mapArrayList.get(i).get("altitude");
              Double speed = (Double) mapArrayList.get(i).get("speed");
              Double yaw = (Double) mapArrayList.get(i).get("yaw");

              if ( date!=null&&date != Double.NaN) {
                  resRecordPoint.setDate(date);
              }

              if ( flightTime!=null&&flightTime != Double.NaN) {
                  resRecordPoint.setFlightTime(flightTime);
              }
              if ( lat!=null&&lat != Double.NaN) {
                  resRecordPoint.setLat(lat);
              }

              if ( lng!=null&&lng != Double.NaN) {
                  resRecordPoint.setLng(lng);
              }
              if ( altitude!=null&&altitude != Double.NaN) {
                  resRecordPoint.setAltitude(altitude);
              }
              if ( speed!=null&&speed != Double.NaN) {
                  resRecordPoint.setSpeed(speed);
              }
              if ( yaw!=null&&yaw != Double.NaN) {
                  resRecordPoint.setYaw(yaw);
              }
              resRecordPointList.add(resRecordPoint);

          }
          resRecordDetail.setRecordPoints(resRecordPointList);
      }
    }
}
