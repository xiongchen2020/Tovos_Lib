package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;

public class TeamGetModel implements Serializable {


        private int id;
        private int groupId;
        private String teamName;
        private int role;
        private int memberCount;
        private int deviceCount;
        private List<TeamGetModel> subTeams;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(int memberCount) {
            this.memberCount = memberCount;
        }

        public int getDeviceCount() {
            return deviceCount;
        }

        public void setDeviceCount(int deviceCount) {
            this.deviceCount = deviceCount;
        }
//
        public List<TeamGetModel> getSubTeams() {
            return subTeams;
        }

        public void setSubTeams(List<TeamGetModel> subTeams) {
            this.subTeams = subTeams;
        }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
