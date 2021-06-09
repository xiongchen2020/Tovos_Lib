package com.example.filgthhublibrary.listener;

import com.example.filgthhublibrary.network.bean.ResDrones;
import com.example.filgthhublibrary.network.bean.ResOnLineDrone;
import com.example.filgthhublibrary.network.bean.TeamGetModel;

import java.util.List;

public interface HubTeamListener {

    void getTeams(List<TeamGetModel> teamGetModels);
}
